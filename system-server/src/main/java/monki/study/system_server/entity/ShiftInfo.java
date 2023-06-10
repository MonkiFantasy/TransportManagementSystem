package monki.study.system_server.entity;
//班次信息
public class ShiftInfo {
    private int shiftId;
    private int carId;
    private int lineNumber;
    private int Seat_solded;
    private boolean isRuning;
    private String setupTime;
    private String arriveTime;

    @Override
    public String toString() {
        return "ShiftInfo{" +
                "shiftId=" + shiftId +
                ", carId=" + carId +
                ", lineNumber=" + lineNumber +
                ", Seat_solded=" + Seat_solded +
                ", isRuning=" + isRuning +
                ", setupTime='" + setupTime + '\'' +
                ", arriveTime='" + arriveTime + '\'' +
                '}';
    }

    public int getShiftId() {
        return shiftId;
    }

    public void setShiftId(int shiftId) {
        this.shiftId = shiftId;
    }

    public int getCarId() {
        return carId;
    }

    public void setCarId(int carId) {
        this.carId = carId;
    }

    public int getLineNumber() {
        return lineNumber;
    }

    public void setLineNumber(int lineNumber) {
        this.lineNumber = lineNumber;
    }

    public int getSeat_solded() {
        return Seat_solded;
    }

    public void setSeat_solded(int seat_solded) {
        Seat_solded = seat_solded;
    }

    public boolean isRuning() {
        return isRuning;
    }

    public void setRuning(boolean runing) {
        isRuning = runing;
    }

    public String getSetupTime() {
        return setupTime;
    }

    public void setSetupTime(String setupTime) {
        this.setupTime = setupTime;
    }

    public String getArriveTime() {
        return arriveTime;
    }

    public void setArriveTime(String arriveTime) {
        this.arriveTime = arriveTime;
    }
}
