package Client;

import Model.Car;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import util.NetworkUtil;

import java.io.File;
import java.net.URL;


public class Main extends Application {
    public static final String TAG = "Main->";
    Stage stage;
    private NetworkUtil networkUtil;
    //private List<Car> carList=new ArrayList<>();
    public static Manufacture manufacture;
    public static LoginController loginController;
    public static Viewer viewer;
    public NetworkUtil getNetworkUtil() {
        return networkUtil;
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        stage = primaryStage;
        networkUtil = new NetworkUtil("127.0.0.1", 33333);
        new ReadServerThread(this);
        showLoginPage();
        //showViewerPage();
    }

    public void showLoginPage() throws Exception {
        // XML Loading using FXMLLoader
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("src\\FxmlAllFiles\\login.fxml"));

        File file = new File("src\\FxmlAllFiles\\login.fxml");
        URL url = new URL("file:\\"+file.getAbsolutePath());
        loader.setLocation(url);
        //Parent loginParent = FXMLLoader.load(url);
        //Scene scene=new Scene(loginParent);
        //fromStage.setScene(scene);

        Parent root = loader.load();

        // Loading the controller
        loginController = loader.getController();
        loginController.setMain(this);
        loginController.initialize();

        // Set the primary stage
        stage.setTitle("Login");
        stage.setScene(new Scene(root, 400, 250));
        stage.show();
    }

    public void showManufacturePage() throws Exception {

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("src\\FxmlAllFiles\\manufacture.fxml"));

        File file = new File("src\\FxmlAllFiles\\manufacture.fxml");
        URL url = new URL("file:\\"+file.getAbsolutePath());
        loader.setLocation(url);
        //Parent loginParent = FXMLLoader.load(url);
        //Scene scene=new Scene(loginParent);
        //fromStage.setScene(scene);

        Parent root = loader.load();

        // Loading the controller
        manufacture  = loader.getController();
        manufacture.setMain(this);
        manufacture.initial();

        // Set the primary stage
        stage.setTitle("Manufacture Home");
        stage.setScene(new Scene(root, 675, 400));
        stage.show();
    }

    public void showViewerPage() throws Exception {

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("src\\FxmlAllFiles\\viewer.fxml"));

        File file = new File("src\\FxmlAllFiles\\viewer.fxml");
        URL url = new URL("file:\\"+file.getAbsolutePath());
        loader.setLocation(url);
        //Parent loginParent = FXMLLoader.load(url);
        //Scene scene=new Scene(loginParent);
        //fromStage.setScene(scene);
        Parent root = loader.load();
        // Loading the controller
        viewer = loader.getController();
        viewer.setMain(this);
        viewer.initialViewer();

        // Set the primary stage
        stage.setTitle("Viewer Home");
        stage.setScene(new Scene(root, 600, 400));
        stage.show();
    }

    public void showAlert() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Incorrect Credentials");
        alert.setHeaderText(null);
        alert.setContentText("The username and password you provided is not correct.");
        alert.showAndWait();
    }

    public static  void AlertCarNotFound(String m) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Not found");
        alert.setHeaderText(null);
        alert.setContentText("No such car with this "+m);
        alert.showAndWait();
    }
    public static void main(String[] args) {
        launch(args);
    }
}
