package mainClasses;


public class ViewBookingSQL {
    private int total_bookings;
    private String date;
    void setTotal_bookings(int total_bookings) {
        this.total_bookings = total_bookings;
    }
    void setDate(String date) {
        this.date = date;
    }
    int getTotal_bookings() {
        return total_bookings;
    }
    String getDate() {
        return date;
    }
}
