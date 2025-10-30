function fetchEvents() {
    fetch('FetchEventsServlet')
        .then(response => {
            if (!response.ok) {
                throw new Error(`HTTP error! Status: ${response.status}`);
            }
            return response.json();
        })
        .then(events => {
            const eventsList = document.getElementById('events-list');
            eventsList.innerHTML = '';
            events.forEach(event => {
                const eventItem = document.createElement('div');
                eventItem.className = 'card mb-3';
                eventItem.innerHTML = `
                    <div class="card-body">
                        <h5 class="card-title">${event.name}</h5>
                        <p class="card-text">Date: ${event.event_date}</p>
                        <p class="card-text">Time: ${event.time}</p>
                        <p class="card-text">Type: ${event.type}</p>
                        <p class="card-text">VIP: ${event.vip}</p>
                        <p class="card-text">Regular Floor: ${event.regular}</p>
                        <p class="card-text">Regular Seats: ${event.seat}</p>
                    </div>
                `;
                eventsList.appendChild(eventItem);
            });
        })
        .catch(error => console.error('Error fetching events:', error));
}