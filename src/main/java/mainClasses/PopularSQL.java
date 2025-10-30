package mainClasses;

public class PopularSQL {
    private int event_id;
    private String event_name;
    private String event_date;
    private int total_reservations;
    public int getEventId() {
        return event_id;
    }
    public void setEventId(int event_id) {
        this.event_id = event_id;
    }
    public String getEventName() {
        return event_name;
    }
    public void setEventName(String event_name) {
        this.event_name = event_name;
    }
    public String getEventDate() {
        return event_date;
    }
    public void setEventDate(String event_date) {
        this.event_date = event_date;
    }
    public int getTotalReservations() {
        return total_reservations;
    }
    public void setTotalReservations(int total_reservations) {
        this.total_reservations = total_reservations;
    }
}
