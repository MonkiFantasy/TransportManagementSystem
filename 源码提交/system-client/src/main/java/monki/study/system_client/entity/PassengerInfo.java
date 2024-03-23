package monki.study.system_client.entity;

public class PassengerInfo {
    private int PassengerId;
    private String PassengerName;
    private String PassengerSex;
    private int PassengerAge;
    private String passengerPhone;
    private String passengerPassword;

    @Override
    public String toString() {
        return "PassengerInfo{" +
                "PassengerId=" + PassengerId +
                ", PassengerName='" + PassengerName + '\'' +
                ", PassengerSex='" + PassengerSex + '\'' +
                ", PassengerAge=" + PassengerAge +
                ", passengerPhone='" + passengerPhone + '\'' +
                ", passengerPassword='" + passengerPassword + '\'' +
                '}';
    }

    public int getPassengerId() {
        return PassengerId;
    }

    public void setPassengerId(int passengerId) {
        PassengerId = passengerId;
    }

    public String getPassengerName() {
        return PassengerName;
    }

    public void setPassengerName(String passengerName) {
        PassengerName = passengerName;
    }

    public String getPassengerSex() {
        return PassengerSex;
    }

    public void setPassengerSex(String passengerSex) {
        PassengerSex = passengerSex;
    }

    public int getPassengerAge() {
        return PassengerAge;
    }

    public void setPassengerAge(int passengerAge) {
        PassengerAge = passengerAge;
    }

    public String getPassengerPhone() {
        return passengerPhone;
    }

    public void setPassengerPhone(String passengerPhone) {
        this.passengerPhone = passengerPhone;
    }

    public String getPassengerPassword() {
        return passengerPassword;
    }

    public void setPassengerPassword(String passengerPassword) {
        this.passengerPassword = passengerPassword;
    }
}

