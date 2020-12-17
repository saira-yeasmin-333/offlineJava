package WriteAllThread;

import org.json.JSONException;
import org.json.JSONObject;
import util.NetworkUtil;

import java.io.IOException;

public class SearchCarMakeThread implements Runnable{
    private NetworkUtil networkUtil;
    private Thread thread;
    private String s;

    public SearchCarMakeThread(NetworkUtil networkUtil, String s) {
        this.networkUtil = networkUtil;
        this.s=s;
        thread=new Thread(this);
        thread.start();
    }

    @Override
    public void run() {
        try{
            JSONObject jsonObject=new JSONObject();
            jsonObject.put("type","searchCarListByMake");
            jsonObject.put("carMake",s);
            System.out.println(jsonObject.toString()+" in search by car make model ");
            networkUtil.write(jsonObject.toString());
        } catch(JSONException | IOException e){
            e.printStackTrace();
            System.out.println("in login thread exception");
        }
    }
}
