package Client;

import Model.Car;
import WriteAllThread.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import java.util.ArrayList;
import java.util.List;

public class Viewer {

    @FXML
    private TableView carTable;

    private Main main;

    @FXML
    private TextField registration;

    @FXML
    private TextField carMake;
    @FXML
    private TextField carModel;
    @FXML
    private Button searchRegistration;

    @FXML
    private Button allCarsView;
    @FXML
    private TableColumn<Car,String> RegColumn;

    @FXML
    private TableColumn<Car, String> ColorColumn;
    @FXML
    private TableColumn<Car,String> CarMakeColumn;
    @FXML
    private TableColumn<Car,String> CarModelColumn;
    @FXML
    private TableColumn<Car,Integer> YearColumn;
    @FXML
    private TableColumn<Car,Integer> PriceColumn;
    @FXML
    private TableColumn<Car,Integer> QuantityColumn;

    @FXML
    private Button searchCarModel;

    @FXML
    private Button searchCarMake;

    @FXML
    private Button buyCar;

    @FXML
    private Button logoutViewer;

    public ObservableList<Car> car= FXCollections.observableArrayList();

    private List<Car> carList=new ArrayList<>();


    public void initialViewer(){
        new TableCarThread(main.getNetworkUtil());

        //car.setAll(carList);
        RegColumn.setCellValueFactory(new PropertyValueFactory<Car,String>("registrationNumber"));
        ColorColumn.setCellValueFactory(new PropertyValueFactory<Car,String>("colour"));
        CarMakeColumn.setCellValueFactory(new PropertyValueFactory<Car,String>("carMake"));
        CarModelColumn.setCellValueFactory(new PropertyValueFactory<Car,String>("carModel"));
        YearColumn.setCellValueFactory(new PropertyValueFactory<Car,Integer>("yearMade"));
        PriceColumn.setCellValueFactory(new PropertyValueFactory<Car,Integer>("price"));
        QuantityColumn.setCellValueFactory(new PropertyValueFactory<Car,Integer>("quantity"));

        carTable.setItems(car);

        searchRegistration.setOnMouseClicked(e->{
            String s=registration.getText();
            registration.setText("");
            new SearchRegistrationThread(main.getNetworkUtil(),s);
        });

        searchCarMake.setOnMouseClicked(e->{
            String s=carMake.getText();
            carMake.setText("");
            System.out.println(s+" in searchCar Make set on mouse clicked");
            new SearchCarMakeThread(main.getNetworkUtil(),s);

        });

        searchCarModel.setOnMouseClicked(e->{
            String s=carModel.getText();
            carModel.setText("");
            System.out.println(s+" in searchCar Model set on mouse clicked");
            new SearchCarModelThread(main.getNetworkUtil(),s);

        });

        allCarsView.setOnMouseClicked(e->{
            new TableCarThread(main.getNetworkUtil());
        });

        buyCar.setOnMouseClicked(e->{
            Car bCar=(Car)carTable.getSelectionModel().getSelectedItem();
            if(bCar.getQuantity()>0)
            {
                new BuyCarThread(main.getNetworkUtil(),bCar.getRegistrationNumber(),String.valueOf(bCar.getQuantity()));
                bCar.setQuantity(bCar.getQuantity()-1);
                carTable.refresh();
                //updateDATA(carList);
            }

            else{
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Sold Out");
                alert.setHeaderText("Car unavailable");
                alert.setContentText("You can not buy this car");
                alert.showAndWait();
            }

        });

        logoutViewer.setOnMouseClicked(e->{
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
        //System.out.println(TAG+"  updateData: printing all car data");
        for (Car car :
                carList) {
            System.out.println(car);
        }
        car.setAll(carList);

    }


}
