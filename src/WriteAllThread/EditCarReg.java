package WriteAllThread;

import org.json.JSONException;
import org.json.JSONObject;
import util.NetworkUtil;

import java.io.IOException;

public class EditCarReg implements Runnable {
    private NetworkUtil networkUtil;
    private Thread thread;
    private String s2,s3,s4;

    public EditCarReg(NetworkUtil networkUtil,   String s2,String s3,String s4) {
        this.networkUtil = networkUtil;
        thread=new Thread(this);
        this.s2 = s2;
        this.s3 = s3;
        this.s4 = s4;
        thread.start();
    }

    @Override
    public void run() {
        try{
            JSONObject jsonObject=new JSONObject();
            jsonObject.put("type","editCar");
            jsonObject.put("newValue",s2);
            jsonObject.put("fieldName",s3);
            jsonObject.put("registrationNumber",s4);
           System.out.println(jsonObject.toString());
            networkUtil.write(jsonObject.toString());
        } catch(JSONException | IOException e){
            e.printStackTrace();

        }
    }
}
