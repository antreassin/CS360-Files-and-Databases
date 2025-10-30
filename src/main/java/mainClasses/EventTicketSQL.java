package mainClasses;

public class EventTicketSQL {
    private int eventId;
    private int availableTickets;
    private int bookedVip;
    private int bookedRegular;
    private int bookedSeat;

    public int getEventId() {
        return eventId;
    }
    public void setEventId(int eventId) {
        this.eventId = eventId;
    }
    public int getAvailableTickets() {
        return availableTickets;
    }

    public void setAvailableTickets(int availableTickets) {
        this.availableTickets = availableTickets;
    }

    public int getBookedVip() {
        return bookedVip;
    }

    public void setBookedVip(int bookedVip) {
        this.bookedVip = bookedVip;
    }

    public int getBookedRegular() {
        return bookedRegular;
    }

    public void setBookedRegular(int bookedRegular) {
        this.bookedRegular = bookedRegular;
    }

    public int getBookedSeat() {
        return bookedSeat;
    }

    public void setBookedSeat(int bookedSeat) {
        this.bookedSeat = bookedSeat;
    }
}
