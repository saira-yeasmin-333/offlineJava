package WriteAllThread;

import org.json.JSONException;
import org.json.JSONObject;
import util.NetworkUtil;

import java.io.IOException;

public class BuyCarThread implements Runnable{
    private NetworkUtil networkUtil;
    private Thread thread;
    private String s,s1;

    public BuyCarThread(NetworkUtil networkUtil,String s,String s1) {
        this.networkUtil = networkUtil;
        this.s=s;
        this.s1=s1;
        thread=new Thread(this);
        thread.start();
    }

    @Override
    public void run() {
        try{
            JSONObject jsonObject=new JSONObject();
            jsonObject.put("type","buy");
            jsonObject.put("registrationNumber",s);
            jsonObject.put("quantity",s1);
            //System.out.println(jsonObject.toString());
            networkUtil.write(jsonObject.toString());
        } catch(JSONException | IOException e){
            e.printStackTrace();
            System.out.println("in login thread exception");
        }
    }
}
