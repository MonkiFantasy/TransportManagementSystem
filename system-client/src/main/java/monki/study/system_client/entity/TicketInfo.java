package monki.study.system_client.entity;
//车票信息
public class TicketInfo {
    private int ticketId;
    private int passengerId;
    private float ticketPrice;
    private int shiftId;
    private int lineId;

    private boolean isSold;

    public int getLineId() {
        return lineId;
    }

    public void setLineId(int lineId) {
        this.lineId = lineId;
    }

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

    public float getTicketPrice() {
        return ticketPrice;
    }

    public void setTicketPrice(float ticketPrice) {
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
