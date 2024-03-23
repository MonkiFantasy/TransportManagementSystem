package monki.study.system_client.entity;
//车辆信息
public class CarInfo {
    private int carId;//车辆号
    private String carCategory;//车种类
    private int carContent;//载客量

    public int getCarId() {
        return carId;
    }

    public void setCarId(int carId) {
        this.carId = carId;
    }

    public String getCarCategory() {
        return carCategory;
    }

    public void setCarCategory(String carCategory) {
        this.carCategory = carCategory;
    }

    public int getCarContent() {
        return carContent;
    }

    public void setCarContent(int carContent) {
        this.carContent = carContent;
    }

    @Override
    public String toString() {
        return "CarInfo{" +
                "carId='" + carId + '\'' +
                ", carCategory='" + carCategory + '\'' +
                ", carContent=" + carContent +
                '}';
    }
}
