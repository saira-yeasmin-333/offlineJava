package WriteAllThread;

import org.json.JSONException;
import org.json.JSONObject;
import util.NetworkUtil;

import java.io.IOException;

public class SearchCarModelThread implements Runnable{
    private NetworkUtil networkUtil;
    private Thread thread;
    private String s;

    public SearchCarModelThread(NetworkUtil networkUtil, String s) {
        this.networkUtil = networkUtil;
        this.s=s;
        thread=new Thread(this);
        thread.start();
    }

    @Override
    public void run() {
        try{
            JSONObject jsonObject=new JSONObject();
            jsonObject.put("type","searchCarListByModel");
            jsonObject.put("carModel",s);
            System.out.println(jsonObject.toString()+" in search by car model thread");
            networkUtil.write(jsonObject.toString());
        } catch(JSONException | IOException e){
            e.printStackTrace();
            System.out.println("in login thread exception");
        }
    }
}
