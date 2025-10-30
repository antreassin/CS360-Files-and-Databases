const express = require('express');
const fs = require('fs').promises;
const path = require('path');

const app = express();
const PORT = 3000;
const DATA_FILE = path.join(__dirname, 'data', 'tickets.json');

// Middleware
app.use(express.json());
app.use(express.static('public'));

// Initialize data file if it doesn't exist
async function initializeDataFile() {
    try {
        await fs.access(DATA_FILE);
    } catch {
        await fs.writeFile(DATA_FILE, JSON.stringify({ tickets: [], nextId: 1 }));
    }
}

// Read tickets from file
async function readTickets() {
    const data = await fs.readFile(DATA_FILE, 'utf8');
    return JSON.parse(data);
}

// Write tickets to file
async function writeTickets(data) {
    await fs.writeFile(DATA_FILE, JSON.stringify(data, null, 2));
}

// API Routes

// Get all tickets
app.get('/api/tickets', async (req, res) => {
    try {
        const data = await readTickets();
        res.json(data.tickets);
    } catch (error) {
        res.status(500).json({ error: 'Failed to read tickets' });
    }
});

// Get single ticket
app.get('/api/tickets/:id', async (req, res) => {
    try {
        const data = await readTickets();
        const ticket = data.tickets.find(t => t.id === parseInt(req.params.id));
        if (ticket) {
            res.json(ticket);
        } else {
            res.status(404).json({ error: 'Ticket not found' });
        }
    } catch (error) {
        res.status(500).json({ error: 'Failed to read ticket' });
    }
});

// Create new ticket
app.post('/api/tickets', async (req, res) => {
    try {
        const { title, description, priority, status } = req.body;
        
        if (!title || !description) {
            return res.status(400).json({ error: 'Title and description are required' });
        }

        const data = await readTickets();
        const newTicket = {
            id: data.nextId,
            title,
            description,
            priority: priority || 'medium',
            status: status || 'open',
            createdAt: new Date().toISOString(),
            updatedAt: new Date().toISOString()
        };

        data.tickets.push(newTicket);
        data.nextId++;
        await writeTickets(data);

        res.status(201).json(newTicket);
    } catch (error) {
        res.status(500).json({ error: 'Failed to create ticket' });
    }
});

// Update ticket
app.put('/api/tickets/:id', async (req, res) => {
    try {
        const data = await readTickets();
        const index = data.tickets.findIndex(t => t.id === parseInt(req.params.id));
        
        if (index === -1) {
            return res.status(404).json({ error: 'Ticket not found' });
        }

        const { title, description, priority, status } = req.body;
        const updatedTicket = {
            ...data.tickets[index],
            title: title || data.tickets[index].title,
            description: description || data.tickets[index].description,
            priority: priority || data.tickets[index].priority,
            status: status || data.tickets[index].status,
            updatedAt: new Date().toISOString()
        };

        data.tickets[index] = updatedTicket;
        await writeTickets(data);

        res.json(updatedTicket);
    } catch (error) {
        res.status(500).json({ error: 'Failed to update ticket' });
    }
});

// Delete ticket
app.delete('/api/tickets/:id', async (req, res) => {
    try {
        const data = await readTickets();
        const index = data.tickets.findIndex(t => t.id === parseInt(req.params.id));
        
        if (index === -1) {
            return res.status(404).json({ error: 'Ticket not found' });
        }

        data.tickets.splice(index, 1);
        await writeTickets(data);

        res.json({ message: 'Ticket deleted successfully' });
    } catch (error) {
        res.status(500).json({ error: 'Failed to delete ticket' });
    }
});

// Start server
initializeDataFile().then(() => {
    app.listen(PORT, () => {
        console.log(`Ticket Service running on http://localhost:${PORT}`);
    });
});
