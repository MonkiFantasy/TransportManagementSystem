package monki.study.system_server.entity;
//驾驶员信息
public class DriverInfo {
    private int driverId;
    private String driverName;
    private String driverSex;

    private String driverBirth;

    private String driverPhone;

    private int carId;

    public int getDriverId() {
        return driverId;
    }

    public void setDriverId(int driverId) {
        this.driverId = driverId;
    }

    public String getDriverName() {
        return driverName;
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }

    public String getDriverSex() {
        return driverSex;
    }

    public void setDriverSex(String driverSex) {
        this.driverSex = driverSex;
    }

    public String getDriverBirth() {
        return driverBirth;
    }

    public void setDriverBirth(String driverBirth) {
        this.driverBirth = driverBirth;
    }

    public String getDriverPhone() {
        return driverPhone;
    }

    public void setDriverPhone(String driverPhone) {
        this.driverPhone = driverPhone;
    }

    public int getCarId() {
        return carId;
    }

    @Override
    public String toString() {
        return "DriverInfo{" +
                "driverId=" + driverId +
                ", driverName='" + driverName + '\'' +
                ", driverSex='" + driverSex + '\'' +
                ", driverBirth='" + driverBirth + '\'' +
                ", driverPhone='" + driverPhone + '\'' +
                ", carId='" + carId + '\'' +
                '}';
    }

    public void setCarId(int carId) {
        this.carId = carId;
    }


}