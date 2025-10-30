package mainClasses;

public class MoneySQL {
    private int eventId;
    private String eventName;
    private String eventDate;
    private int totalIncome;
    public int getEventId() {
        return eventId;
    }
    public void setEventId(int eventId) {
        this.eventId = eventId;
    }
    public String getEventName() {
        return eventName;
    }
    public void setEventName(String eventName) {
        this.eventName = eventName;
    }
    public String getEventDate() {
        return eventDate;
    }
    public void setEventDate(String eventDate) {
        this.eventDate = eventDate;
    }
    public int getTotalIncome() {
        return totalIncome;
    }
    public void setTotalIncome(int totalIncome) {
        this.totalIncome = totalIncome;
    }
}
