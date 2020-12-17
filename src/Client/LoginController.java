package Client;

import WriteAllThread.LoginThread;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginController  {

    @FXML
    private TextField usernameText;

    @FXML
    private PasswordField passwordText;

    @FXML
    private Button loginButton;
    private Main main;

    public void initialize() {
        loginButton.setOnMouseClicked(event->{
            String s1=usernameText.getText();
            String s2=passwordText.getText();
            if(s1.equals("viewer")&& s2.isEmpty()){
                System.out.println("login by viewer");
                try {
                    main.showViewerPage();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            else{
                try {
                    new LoginThread(main.getNetworkUtil(), s1,s2);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        });
    }
    void setMain(Main main) {
        this.main = main;
    }

    public void isValid(String s) throws Exception {
        if(s.equals("yes")){
            main.showManufacturePage();
        }
    }
}
