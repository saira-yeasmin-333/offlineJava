package WriteAllThread;

import org.json.JSONException;
import org.json.JSONObject;
import util.NetworkUtil;

import java.io.IOException;

public class TableCarThread implements Runnable{
    private NetworkUtil networkUtil;
    private Thread thread;

    public TableCarThread(NetworkUtil networkUtil) {
        this.networkUtil = networkUtil;
        thread=new Thread(this);
        thread.start();
    }

    @Override
    public void run() {
        try{
            JSONObject jsonObject=new JSONObject();
            jsonObject.put("type","showCar");
            //System.out.println(jsonObject.toString());
            networkUtil.write(jsonObject.toString());
        } catch(JSONException | IOException e){
        e.printStackTrace();
        System.out.println("in login thread exception");
        }
    }
}
