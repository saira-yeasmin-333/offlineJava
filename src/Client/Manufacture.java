package Client;

import Model.Car;
import WriteAllThread.CarListThread;
import WriteAllThread.DeleteCarThread;
import WriteAllThread.EditCarReg;
import WriteAllThread.TableCarThread;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.converter.IntegerStringConverter;

import java.util.ArrayList;
import java.util.List;

public class Manufacture {
    public static final String TAG = "Manufature->";
    @FXML
    private TableView <Car>carTableManufacturer;

    public ObservableList<Car>car= FXCollections.observableArrayList();
    @FXML
    private TableColumn<Car,String> regColumn;

    @FXML
    private TableColumn<Car, String> colorColumn;
    @FXML
    private TableColumn<Car,String> carMakeColumn;
    @FXML
    private TableColumn<Car,String> carModelColumn;
    @FXML
    private TableColumn<Car,Integer> yearColumn;
    @FXML
    private TableColumn<Car,Integer> priceColumn;
    @FXML
    private TableColumn<Car,Integer> quantityColumn;

    @FXML
    private TextField registrationField;

    @FXML
    private TextField colorField;

    @FXML
    private TextField carMakeField;
    @FXML
    private TextField carModelField;

    @FXML
    private TextField yearField;
    @FXML
    private TextField priceField;
    @FXML
    private TextField quantityField;

    @FXML
    private Button add;
    private Main main;
    @FXML
    private Button logoutManufacturer;

    @FXML
    private Button delete;

    private List<Car>carList=new ArrayList<>();

    public void initial(){
        new TableCarThread(main.getNetworkUtil());

        //car.setAll(carList);
        regColumn.setCellValueFactory(new PropertyValueFactory<Car,String>("registrationNumber"));
        colorColumn.setCellValueFactory(new PropertyValueFactory<Car,String>("colour"));
        carMakeColumn.setCellValueFactory(new PropertyValueFactory<Car,String>("carMake"));
        carModelColumn.setCellValueFactory(new PropertyValueFactory<Car,String>("carModel"));
        yearColumn.setCellValueFactory(new PropertyValueFactory<Car,Integer>("yearMade"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<Car,Integer>("price"));
        quantityColumn.setCellValueFactory(new PropertyValueFactory<Car,Integer>("quantity"));

        carTableManufacturer.setItems(car);

        carTableManufacturer.setEditable(true);

        colorColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        carMakeColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        carModelColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        System.out.println("for col 1");
        yearColumn.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        System.out.println("for col 2");
        priceColumn.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        quantityColumn.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));

        add.setOnMouseClicked(event->{
            String s1=registrationField.getText();
            String s2=colorField.getText();
            String s3=carMakeField.getText();
            String s4=carModelField.getText();
            String s5=yearField.getText();
            String s6=priceField.getText();
            String s7=quantityField.getText();
            int count=0;
            for(Car c:carList){
                if(c.getRegistrationNumber().equals(s1)){
                    count++;
                }
            }
            if(count==0){
                new CarListThread(main.getNetworkUtil(),s1,s2,s3,s4,s5,s6,s7);
                registrationField.setText("");
                colorField.setText("");
                carMakeField.setText("");
                carModelField.setText("");
                yearField.setText("");
                priceField.setText("");
                quantityField.setText("");
                carList.add(new Car(s1,s2,s3,s4,Integer.parseInt(s5),Integer.parseInt(s6),Integer.parseInt(s7)));
                updateDATA(carList);

            }else{
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Incorrect Credentials");
                alert.setContentText("There exists already a car with this registration number.Please try to enter e new registration number");
                alert.showAndWait();
            }

        });

        delete.setOnMouseClicked(e->{
            Car delCar;
            delCar=  carTableManufacturer.getSelectionModel().getSelectedItem();
            new DeleteCarThread(main.getNetworkUtil(),delCar.getRegistrationNumber());
            carList.remove(delCar);
            carTableManufacturer.refresh();
            updateDATA(carList);

        });

        logoutManufacturer.setOnMouseClicked(e->{
            try {
                main.showLoginPage();
            } catch (Exception er) {
                er.printStackTrace();
            }
        });
    }

    void setMain(Main main) {
        this.main = main;
    }

    public void updateDATA(List<Car> carList){
        this.carList=carList;
        System.out.println(TAG+"  updateData: printing all car data");
        for (Car car :
                carList) {
            System.out.println(car);
        }
        car.setAll(carList);

    }

    public void editCarColour(TableColumn.CellEditEvent<Car, String> carStringCellEditEvent) {
        Car editCarField=  carTableManufacturer.getSelectionModel().getSelectedItem();
        editCarField.setColour(carStringCellEditEvent.getNewValue());
        new EditCarReg(main.getNetworkUtil(),carStringCellEditEvent.getNewValue(),"colour",editCarField.getRegistrationNumber());
    }

    public void editCarMake(TableColumn.CellEditEvent<Car, String> carStringCellEditEvent) {
        Car editCarField=  carTableManufacturer.getSelectionModel().getSelectedItem();
        editCarField.setCarMake(carStringCellEditEvent.getNewValue());
        new EditCarReg(main.getNetworkUtil(),carStringCellEditEvent.getNewValue(),"carMake",editCarField.getRegistrationNumber());
    }

    public void editCarModel(TableColumn.CellEditEvent<Car, String> carStringCellEditEvent) {
        Car editCarField=  carTableManufacturer.getSelectionModel().getSelectedItem();
        editCarField.setCarModel(carStringCellEditEvent.getNewValue());
        new EditCarReg(main.getNetworkUtil(),carStringCellEditEvent.getNewValue(),"carModel",editCarField.getRegistrationNumber());
    }

    public void editYearMade(TableColumn.CellEditEvent<Car, Integer> carIntegerCellEditEvent) {
        Car editCarField=  carTableManufacturer.getSelectionModel().getSelectedItem();
        editCarField.setYearMade(carIntegerCellEditEvent.getNewValue());
        new EditCarReg(main.getNetworkUtil(),carIntegerCellEditEvent.getNewValue().toString(),"year",editCarField.getRegistrationNumber());
    }

    public void editPrice(TableColumn.CellEditEvent<Car, Integer> carIntegerCellEditEvent) {
        Car editCarField=  carTableManufacturer.getSelectionModel().getSelectedItem();
        editCarField.setYearMade(carIntegerCellEditEvent.getNewValue());
        new EditCarReg(main.getNetworkUtil(),carIntegerCellEditEvent.getNewValue().toString(),"price",editCarField.getRegistrationNumber());
    }

    public void editQuantity(TableColumn.CellEditEvent<Car, Integer> carIntegerCellEditEvent) {
        Car editCarField=  carTableManufacturer.getSelectionModel().getSelectedItem();
        editCarField.setYearMade(carIntegerCellEditEvent.getNewValue());
        new EditCarReg(main.getNetworkUtil(),carIntegerCellEditEvent.getNewValue().toString(),"quantity",editCarField.getRegistrationNumber());
    }
}
