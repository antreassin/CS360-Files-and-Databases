// Get DOM elements
const ticketForm = document.getElementById('ticketForm');
const ticketsList = document.getElementById('ticketsList');
const editModal = document.getElementById('editModal');
const editForm = document.getElementById('editForm');
const closeModal = document.querySelector('.close');

// Load tickets on page load
document.addEventListener('DOMContentLoaded', loadTickets);

// Create ticket
ticketForm.addEventListener('submit', async (e) => {
    e.preventDefault();
    
    const ticket = {
        title: document.getElementById('title').value,
        description: document.getElementById('description').value,
        priority: document.getElementById('priority').value,
        status: document.getElementById('status').value
    };

    try {
        const response = await fetch('/api/tickets', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(ticket)
        });

        if (response.ok) {
            ticketForm.reset();
            loadTickets();
        } else {
            alert('Failed to create ticket');
        }
    } catch (error) {
        console.error('Error:', error);
        alert('Failed to create ticket');
    }
});

// Load all tickets
async function loadTickets() {
    try {
        const response = await fetch('/api/tickets');
        const tickets = await response.json();
        displayTickets(tickets);
    } catch (error) {
        console.error('Error:', error);
        ticketsList.innerHTML = '<p class="empty-state">Failed to load tickets</p>';
    }
}

// Display tickets
function displayTickets(tickets) {
    if (tickets.length === 0) {
        ticketsList.innerHTML = '<div class="empty-state"><p>No tickets yet. Create your first ticket!</p></div>';
        return;
    }

    ticketsList.innerHTML = tickets.map(ticket => `
        <div class="ticket">
            <div class="ticket-header">
                <div>
                    <div class="ticket-title">${escapeHtml(ticket.title)}</div>
                    <div class="ticket-meta">
                        <span class="badge badge-priority-${ticket.priority}">
                            ${ticket.priority.toUpperCase()}
                        </span>
                        <span class="badge badge-status-${ticket.status}">
                            ${ticket.status.replace('-', ' ').toUpperCase()}
                        </span>
                    </div>
                </div>
            </div>
            <div class="ticket-description">${escapeHtml(ticket.description)}</div>
            <div class="ticket-footer">
                <div class="ticket-date">
                    Created: ${new Date(ticket.createdAt).toLocaleString()}
                </div>
                <div class="ticket-actions">
                    <button class="btn btn-secondary" onclick="openEditModal(${ticket.id})">
                        Edit
                    </button>
                    <button class="btn btn-danger" onclick="deleteTicket(${ticket.id})">
                        Delete
                    </button>
                </div>
            </div>
        </div>
    `).join('');
}

// Open edit modal
async function openEditModal(id) {
    try {
        const response = await fetch(`/api/tickets/${id}`);
        const ticket = await response.json();

        document.getElementById('editId').value = ticket.id;
        document.getElementById('editTitle').value = ticket.title;
        document.getElementById('editDescription').value = ticket.description;
        document.getElementById('editPriority').value = ticket.priority;
        document.getElementById('editStatus').value = ticket.status;

        editModal.style.display = 'block';
    } catch (error) {
        console.error('Error:', error);
        alert('Failed to load ticket');
    }
}

// Close modal
closeModal.onclick = function() {
    editModal.style.display = 'none';
}

window.onclick = function(event) {
    if (event.target == editModal) {
        editModal.style.display = 'none';
    }
}

// Update ticket
editForm.addEventListener('submit', async (e) => {
    e.preventDefault();
    
    const id = document.getElementById('editId').value;
    const ticket = {
        title: document.getElementById('editTitle').value,
        description: document.getElementById('editDescription').value,
        priority: document.getElementById('editPriority').value,
        status: document.getElementById('editStatus').value
    };

    try {
        const response = await fetch(`/api/tickets/${id}`, {
            method: 'PUT',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(ticket)
        });

        if (response.ok) {
            editModal.style.display = 'none';
            loadTickets();
        } else {
            alert('Failed to update ticket');
        }
    } catch (error) {
        console.error('Error:', error);
        alert('Failed to update ticket');
    }
});

// Delete ticket
async function deleteTicket(id) {
    if (!confirm('Are you sure you want to delete this ticket?')) {
        return;
    }

    try {
        const response = await fetch(`/api/tickets/${id}`, {
            method: 'DELETE'
        });

        if (response.ok) {
            loadTickets();
        } else {
            alert('Failed to delete ticket');
        }
    } catch (error) {
        console.error('Error:', error);
        alert('Failed to delete ticket');
    }
}

// Escape HTML to prevent XSS
function escapeHtml(text) {
    const div = document.createElement('div');
    div.textContent = text;
    return div.innerHTML;
}
