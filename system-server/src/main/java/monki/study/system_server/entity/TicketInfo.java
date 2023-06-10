package monki.study.system_server.entity;
//车票信息
public class TicketInfo {
    private int ticketId;
    private int passengerId;
    private String ticketPrice;
    private int shiftId;

    private boolean isSold;
    public int getTicketId() {
        return ticketId;
    }

    public void setTicketId(int ticketId) {
        this.ticketId = ticketId;
    }

    public int getPassengerId() {
        return passengerId;
    }

    public void setPassengerId(int passengerId) {
        this.passengerId = passengerId;
    }

    public String getTicketPrice() {
        return ticketPrice;
    }

    public void setTicketPrice(String ticketPrice) {
        this.ticketPrice = ticketPrice;
    }

    public int getShiftId() {
        return shiftId;
    }

    public void setShiftId(int shiftId) {
        this.shiftId = shiftId;
    }

    public boolean isSold() {
        return isSold;
    }

    public void setSold(boolean sold) {
        isSold = sold;
    }

    @Override
    public String toString() {
        return "TicketInfo{" +
                "ticketId=" + ticketId +
                ", passengerId=" + passengerId +
                ", ticketPrice='" + ticketPrice + '\'' +
                ", shiftId=" + shiftId +
                ", isSold=" + isSold +
                '}';
    }
}
