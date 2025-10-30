package mainClasses;

public class IncomePerEventSQL {
    private int event_id,vip_income,regular_income,seat_income,total_income;
    private String event_name;
    public String getName_event() {
        return event_name;
    }
    public void setName_event(String name_event) {
        this.event_name = name_event;
    }
    public int getEvent_id() {
        return event_id;
    }
    public void setEvent_id(int event_id) {
        this.event_id = event_id;
    }
    public int getVip_income() {
        return vip_income;
    }
    public void setVip_income(int vip_income) {
        this.vip_income = vip_income;
    }
    public int getRegular_income() {
        return regular_income;
    }
    public void setRegular_income(int regular_income) {
        this.regular_income = regular_income;
    }
    public int getSeat_income() {
        return seat_income;
    }
    public void setSeat_income(int seat_income) {
        this.seat_income = seat_income;
    }
    public int getTotal_income() {
        return total_income;
    }
    public void setTotal_income(int total_income) {
        this.total_income = total_income;
    }

}
