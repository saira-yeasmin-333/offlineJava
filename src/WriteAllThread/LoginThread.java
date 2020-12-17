package WriteAllThread;

import org.json.JSONException;
import org.json.JSONObject;
import util.NetworkUtil;

import java.io.IOException;

public class LoginThread  implements Runnable{

    private NetworkUtil networkUtil;
    private String username;
    private String password;
    private Thread thread;

    public LoginThread(NetworkUtil networkUtil, String username, String password) {
        this.networkUtil = networkUtil;
        this.username = username;
        this.password = password;
        thread=new Thread(this);
        thread.start();
    }

    @Override
    public void run() {

        try{

            JSONObject jsonObject=new JSONObject();
            jsonObject.put("type","login");
            jsonObject.put("username",username);
            jsonObject.put("password",password);
            System.out.println(jsonObject.toString());
            networkUtil.write(jsonObject.toString());
        }catch(JSONException | IOException e){
            e.printStackTrace();
            System.out.println("in login thread exception");
        }finally {

        }

    }
}
