package Client;

import WriteAllThread.LoginThread;
import util.NetworkUtil;

import java.io.IOException;

public class ManufactureLogin {
    private static ManufactureLogin instance=null;
    private NetworkUtil networkUtil;
    private ReadServerThread readServerThread;

    private ManufactureLogin() throws IOException {
        networkUtil = new NetworkUtil("127.0.0.1", 44444);
        //readServerThread=new ReadServerThread(networkUtil);
    }

    public static synchronized ManufactureLogin getInstance() throws IOException {
        if(instance==null){
            instance=new ManufactureLogin();
        }
        return instance;
    }
    public void login(String userName,String password){
        try {
            new LoginThread(networkUtil,userName,password);

        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
