package Model;


import java.io.Serializable;

public class Car implements Serializable {
    private String registrationNumber,colour,carMake,carModel;
    private int yearMade;
    private int price;

    public Car(String registrationNumber, String colour,  String carMake, String carModel, int yearMade, int price, int quantity) {
        this.registrationNumber = registrationNumber;
        this.colour = colour;
        this.carMake = carMake;
        this.carModel = carModel;
        this.yearMade = yearMade;
        this.price = price;
        this.quantity = quantity;
    }

    private int quantity;

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Car() {

    }


    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public void setRegistrationNumber(String registrationNumber) {
        this.registrationNumber = registrationNumber;
    }

    public String getColour() {
        return colour;
    }

    public void setColour(String colour) {
        this.colour = colour;
    }

    public String getCarMake() {
        return carMake;
    }

    public void setCarMake(String carMake) {
        this.carMake = carMake;
    }

    public String getCarModel() {
        return carModel;
    }

    public void setCarModel(String carModel) {
        this.carModel = carModel;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getYearMade() {
        return yearMade;
    }

    public void setYearMade(int yearMade) {
        this.yearMade = yearMade;
    }

    @Override
    public String toString() {
        return "Car{" +
                "registrationNumber='" + registrationNumber + '\'' +
                ", colour='" + colour + '\'' +
                ", carMake='" + carMake + '\'' +
                ", carModel='" + carModel + '\'' +
                ", yearMade=" + yearMade +
                ", price=" + price +
                ", quantity=" + quantity +
                '}';
    }
}


