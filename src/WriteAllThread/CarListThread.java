package WriteAllThread;

import org.json.JSONException;
import org.json.JSONObject;
import util.NetworkUtil;

import java.io.IOException;

public class CarListThread implements Runnable{
    private NetworkUtil networkUtil;
    private String s1,s2,s3,s4,s5,s6,s7;
    private Thread thread;

    public CarListThread(NetworkUtil networkUtil, String s1, String s2, String s3, String s4, String s5, String s6, String s7) {
        this.networkUtil = networkUtil;
        this.s1 = s1;
        this.s2 = s2;
        this.s3 = s3;
        this.s4 = s4;
        this.s5 = s5;
        this.s6 = s6;
        this.s7 = s7;
        thread=new Thread(this);
        thread.start();
    }

    @Override
    public void run() {
        try{

            JSONObject jsonObject=new JSONObject();
            jsonObject.put("type","CarList");
            jsonObject.put("registrationNumber",s1);
            jsonObject.put("colour",s2);
            jsonObject.put("carMake",s3);
            jsonObject.put("carModel",s4);
            jsonObject.put("yearMade",s5);
            jsonObject.put("price",s6);
            jsonObject.put("quantity",s7);
            System.out.println(jsonObject.toString());
            networkUtil.write(jsonObject.toString());
        }catch(JSONException | IOException e){
            e.printStackTrace();
            System.out.println("in login thread exception");
        }finally {

        }

    }
}
