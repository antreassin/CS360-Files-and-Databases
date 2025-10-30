function sendAjaxPost() {
    let form = document.getElementById('user-form');
    let formData = new FormData(form);
    const data = {};
    formData.forEach((value, key) => {
        data[key] = value;
    });
    const jsonData = JSON.stringify(data);
    console.log(jsonData);
    const xhr = new XMLHttpRequest();
    xhr.open('POST', 'AddToDatabase');
    xhr.setRequestHeader('Content-type', 'application/json');
    xhr.send(jsonData);console.log(jsonData);
}
function sendAjaxPostEvent() {
    let form = document.getElementById('event-form');
    let formData = new FormData(form);
    const data = {};
    formData.forEach((value, key) => {
        data[key] = value;
    });
    const jsonData = JSON.stringify(data);
    console.log(jsonData);
    const xhr = new XMLHttpRequest();
    xhr.open('POST', 'AddEvent');
    xhr.setRequestHeader('Content-type', 'application/json');
    xhr.send(jsonData);console.log(jsonData);
}

function searchEventById() {
    const eventId = document.getElementById('event-id-search').value;
    if (!eventId) {
        alert("Please enter an Event ID.");
        return;
    }

    fetch(`SearchEvent?event_id=${eventId}`)
        .then(response => {
            if (!response.ok) {
                throw new Error(`HTTP error! Status: ${response.status}`);
            }
            return response.json();
        })
        .then(event => {
            const ticketsList = document.getElementById('tickets-list');
            ticketsList.innerHTML = '';

            const eventDetails = `
                <div class="card mb-3 ticket-item">
                    <div class="card-body">
                        <h5 class="card-title">Event ID: ${eventId}</h5>
                        <p class="card-text"> ${event.vip}</p>
                        <p class="card-text"> ${event.regular}</p>
                        <p class="card-text"> ${event.seat}</p>
                        <p class="card-text"> ${event.capacity}</p>
                        <div class="reserve-options">
                            
                            <form id="book-form">
                        <div class="sect">
                            <label for="event_id_book">Event ID</label>
                            <input type="text" class="form-control" id="event_id_book" name="event_id" required>
                        </div>
                        <div class="sect">
                            <label for="user_id_book">User ID</label>
                            <input type="text" class="form-control" id="user_id_book" name="user_id" required>
                        </div>
                        <div class="sect">
                            <label for="vip">V.I.P.</label>
                            <input type="text" class="form-control" id="vip_book" name="vip" required>
                        </div>
                        <div class="sect">
                            <label for="regular_book">Regular</label>
                            <input type="text" class="form-control" id="regular_book" name="regular_book" required>
                        </div>
                        <div class="sect">
                            <label for="seat_book">Seat</label>
                            <input type="text" class="form-control" id="seat_book" name="seat_book" required>
                        </div>
                        <button class="btn btn-success mt-2" onclick="reserveTickets(${eventId})">Reserve</button>
                    </form>
                        </div>
                    </div>
                </div>
            `;
            ticketsList.innerHTML = eventDetails;
        })
        .catch(error => console.error('Error fetching event by ID:', error));
}

function reserveTickets(eventId) {
    const form = document.getElementById('book-form');
    const formData = new FormData(form);
    const data = {};
    formData.forEach((value, key) => {
        data[key] = parseInt(value); // Convert to integers
    });
    const jsonData = JSON.stringify(data);

    const xhr = new XMLHttpRequest();
    xhr.open('POST', 'BookTickets');
    xhr.setRequestHeader('Content-type', 'application/json');
    xhr.onload = function () {
        if (xhr.status === 200) {
            console.log('Response:', JSON.parse(xhr.responseText));
        } else {
            console.error('Error:', xhr.responseText);
        }
    };
    xhr.send(jsonData);
}

// document.getElementById('bookButton').addEventListener('click', function () {
//     const form = document.getElementById('book-form');
//     const formData = new FormData(form);
//     const data = {};
//     formData.forEach((value, key) => {
//         data[key] = parseInt(value); // Convert to integers
//     });
//     const jsonData = JSON.stringify(data);
//
//     const xhr = new XMLHttpRequest();
//     xhr.open('POST', 'BookTickets');
//     xhr.setRequestHeader('Content-type', 'application/json');
//     xhr.onload = function () {
//         if (xhr.status === 200) {
//             console.log('Response:', JSON.parse(xhr.responseText));
//         } else {
//             console.error('Error:', xhr.responseText);
//         }
//     };
//     xhr.send(jsonData);
// });

document.getElementById('cancelBookButton').addEventListener('click', function () {
    const form = document.getElementById('cancelBook-form');
    const formData = new FormData(form);
    const data = {};

    formData.forEach((value, key) => {
        data[key] = isNaN(value) ? value : parseInt(value); // Convert to integer if numeric
    });
    const jsonData = JSON.stringify(data);

    const xhr = new XMLHttpRequest();
    xhr.open('POST', 'CancelBooking');
    xhr.setRequestHeader('Content-type', 'application/json');

    xhr.onload = function () {
        try {
            const responseJson = JSON.parse(xhr.responseText);
            if (xhr.status === 200) {
                console.log('Success:', responseJson);
            } else {
                console.error('Error:', responseJson.error || 'Unknown error');
            }
        } catch (e) {
            console.error('Invalid JSON response:', xhr.responseText);
        }
    };

    xhr.onerror = function () {
        console.error('Request failed');
    };

    xhr.send(jsonData);
});

document.getElementById('cancelEventButton').addEventListener('click', function () {
    const form = document.getElementById('cancelEvent-form');
    const formData = new FormData(form);
    const data = {};
    formData.forEach((value, key) => {
        data[key] = parseInt(value);
    });
    const jsonData = JSON.stringify(data);

    const xhr = new XMLHttpRequest();
    xhr.open('POST', 'CancelEvent');
    xhr.setRequestHeader('Content-type', 'application/json');
    xhr.onload = function () {
        if (xhr.status === 200) {
            console.log('Response:', JSON.parse(xhr.responseText));
        } else {
            console.error('Error:', xhr.responseText);
        }
    };
    xhr.send(jsonData);
});
document.getElementById('ticketStatus').addEventListener('click', function () {
    const xhr = new XMLHttpRequest();
    xhr.open('GET', 'EventTicketServlet');
    xhr.onload = function () {
        if (xhr.status === 200) {
            const data = JSON.parse(xhr.responseText);
            updateTicketStatusTable(data);
        } else {
            console.error('Error:', xhr.responseText);
        }
    };
    xhr.onerror = function () {
        console.error('Error: Failed to fetch event ticket status');
    };
    xhr.send();
});
function updateTicketStatusTable(data) {
    const tableBody = document.getElementById('ticket-status-table').getElementsByTagName('tbody')[0];
    const table = document.getElementById('ticket-status-table');
    table.style.display = 'block';
    tableBody.innerHTML = '';
    data.forEach(status => {
        const row = tableBody.insertRow();
        row.innerHTML = `
            <td>${status.eventId}</td>
            <td>${status.availableTickets}</td>
            <td>${status.bookedVip}</td>
            <td>${status.bookedRegular}</td>
            <td>${status.bookedSeat}</td>
        `;
    });
}
document.getElementById('getMoneyYearButton').addEventListener('click', function () {
    const startDate = document.getElementById('startDate').value;
    const endDate = document.getElementById('endDate').value;

    if (!startDate || !endDate) {
        document.getElementById('moneyYearResponse').innerHTML = `<p>Please enter valid start and end dates.</p>`;
        return;
    }

    const xhr = new XMLHttpRequest();
    xhr.open('GET', `MostMoneyYearServlet?startDate=${encodeURIComponent(startDate)}&endDate=${encodeURIComponent(endDate)}`);
    xhr.onload = function () {
        if (xhr.status === 200) {
            try {
                const data = JSON.parse(xhr.responseText);
                document.getElementById('moneyYearResponse').innerHTML = `
                    <h4>Event with Highest Income:</h4>
                    <p><strong>Event Name:</strong> ${data.eventName}</p>
                    <p><strong>Event Date:</strong> ${data.eventDate}</p>
                    <p><strong>Income:</strong> $${data.income.toFixed(2)}</p>
                `;
            } catch (e) {
                console.error('Error parsing JSON:', e, xhr.responseText);
                document.getElementById('moneyYearResponse').innerHTML = `<p>Error: Invalid response from server.</p>`;
            }
        } else {
            console.error('Error:', xhr.responseText);
            document.getElementById('moneyYearResponse').innerHTML = `<p>Error: ${xhr.responseText}</p>`;
        }
    };
    xhr.onerror = function () {
        console.error('Error: Failed to fetch event income data');
        document.getElementById('moneyYearResponse').innerHTML = `<p>Error: Failed to fetch event income data</p>`;
    };
    xhr.send();
});


document.getElementById('viewBookings').addEventListener('click', function () {
    const xhr = new XMLHttpRequest();
    xhr.open('GET', 'ViewBookingServlet');
    xhr.onload = function () {
        if (xhr.status === 200) {
            const data = JSON.parse(xhr.responseText);
            viewBookingTableUpdate(data);
        } else {
            console.error('Error:', xhr.responseText);
        }
    };
    xhr.onerror = function () {
        console.error('Error: Failed to fetch event ticket status');
    };
    xhr.send();
});
function viewBookingTableUpdate(data) {
    const tableBody = document.getElementById('view-bookings-table').getElementsByTagName('tbody')[0];
    const table = document.getElementById('view-bookings-table');
    table.style.display = 'block';
    tableBody.innerHTML = '';
    data.forEach(status => {
        const row = tableBody.insertRow();
        row.innerHTML = `
            <td>${status.date}</td>
            <td>${status.total_bookings}</td>
        `;
    });
}

document.getElementById('viewIncomePerYear').addEventListener('click', function () {
    const xhr = new XMLHttpRequest();
    xhr.open('GET', 'IncomePerEventServlet');
    xhr.onload = function () {
        if (xhr.status === 200) {
            const data = JSON.parse(xhr.responseText);
            viewIncomePerYear(data);
        } else {
            console.error('Error:', xhr.responseText);
        }
    };
    xhr.onerror = function () {
        console.error('Error: Failed to fetch event ticket status');
    };
    xhr.send();
});
function viewIncomePerYear(data) {
    const tableBody = document.getElementById('view-income-table').getElementsByTagName('tbody')[0];
    const table = document.getElementById('view-income-table');
    table.style.display = 'block';
    tableBody.innerHTML = '';
    data.forEach(status => {
        const row = tableBody.insertRow();
        row.innerHTML = `
            <td>${status.event_id}</td>
            <td>${status.event_name}</td>
            <td>${status.vip_income}</td>
            <td>${status.regular_income}</td>
            <td>${status.seat_income}</td>
            <td>${status.total_income}</td>
        `;
    });
}
document.getElementById('viewIncomePerEvent').addEventListener('click', function () {
    const xhr = new XMLHttpRequest();
    xhr.open('GET', 'MoneyServlet');
    xhr.onload = function () {
        if (xhr.status === 200) {
            const data = JSON.parse(xhr.responseText);
            updateViewIncomePerEventTable(data);
        } else {
            console.error('Error:', xhr.responseText);
        }
    };
    xhr.onerror = function () {
        console.error('Error: Failed to fetch event ticket status');
    };
    xhr.send();
});
function updateViewIncomePerEventTable(data) {
    const tableBody = document.getElementById('view-income-perEvent-table').getElementsByTagName('tbody')[0];
    const table = document.getElementById('view-income-perEvent-table');
    table.style.display = 'block';
    tableBody.innerHTML = '';
    data.forEach(status => {
        const row = tableBody.insertRow();
        row.innerHTML = `
            <td>${status.eventId}</td>
            <td>${status.eventName}</td>
            <td>${status.eventDate}</td>
            <td>${status.totalIncome}</td>
        `;
    });
}

document.getElementById('popularStatus').addEventListener('click', function () {
    const xhr = new XMLHttpRequest();
    xhr.open('GET', 'PopularServlet');
    xhr.onload = function () {
        if (xhr.status === 200) {
            const data = JSON.parse(xhr.responseText);
            updatePopular(data);
        } else {
            console.error('Error:', xhr.status, xhr.responseText);
        }
    };
    xhr.onerror = function () {
        console.error('Error: Failed to fetch event ticket status');
    };
    xhr.send();
});
function updatePopular(data) {
    const table = document.getElementById('popular-table');
    const tableBody = table.getElementsByTagName('tbody')[0];
    table.style.display = 'block';
    tableBody.innerHTML = '';

    // Check if data is an array or a single object
    if (!Array.isArray(data)) {
        data = [data]; // Wrap single object in an array
    }

    data.forEach(status => {
        const row = tableBody.insertRow();
        row.innerHTML = `
            <td>${status.event_id}</td>
            <td>${status.event_name}</td>
            <td>${status.event_date}</td>
            <td>${status.total_reservations}</td>
        `;
    });
}
