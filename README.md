# CS360-Files-an-Databases

## Ticket Service Web Application

A simple ticket management system built with Node.js, Express, and file-based storage. This project demonstrates file I/O operations and database concepts using JSON file storage.

### Features

- ✨ Create, Read, Update, and Delete (CRUD) tickets
- 📝 Ticket properties: title, description, priority, status
- 💾 File-based storage (JSON)
- 🎨 Modern, responsive web interface
- 🔄 Real-time updates

### Prerequisites

- Node.js (v14 or higher)
- npm (comes with Node.js)

### Installation

1. Clone the repository:
```bash
git clone https://github.com/antreassin/CS360-Files-an-Databases.git
cd CS360-Files-an-Databases
```

2. Install dependencies:
```bash
npm install
```

### Running the Application

Start the server:
```bash
npm start
```

The application will be available at `http://localhost:3000`

### Usage

1. **Create a Ticket**: Fill out the form with title, description, priority, and status
2. **View Tickets**: All tickets are displayed in the tickets section
3. **Edit a Ticket**: Click the "Edit" button on any ticket to modify it
4. **Delete a Ticket**: Click the "Delete" button to remove a ticket

### Project Structure

```
.
├── server.js           # Express server and API endpoints
├── public/            # Frontend files
│   ├── index.html     # Main HTML file
│   ├── style.css      # Styling
│   └── app.js         # Client-side JavaScript
├── data/              # Data storage (auto-generated)
│   └── tickets.json   # Tickets database file
└── package.json       # Project dependencies
```

### API Endpoints

- `GET /api/tickets` - Get all tickets
- `GET /api/tickets/:id` - Get a specific ticket
- `POST /api/tickets` - Create a new ticket
- `PUT /api/tickets/:id` - Update a ticket
- `DELETE /api/tickets/:id` - Delete a ticket

### Technologies Used

- **Backend**: Node.js, Express.js
- **Frontend**: HTML5, CSS3, JavaScript (ES6+)
- **Storage**: File system (JSON)

### License

ISC