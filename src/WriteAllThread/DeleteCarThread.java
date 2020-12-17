package WriteAllThread;

import org.json.JSONException;
import org.json.JSONObject;
import util.NetworkUtil;

import java.io.IOException;

public class DeleteCarThread implements Runnable {
    private NetworkUtil networkUtil;
    private Thread thread;
    private String reg;

    public DeleteCarThread(NetworkUtil networkUtil,String reg) {
        this.networkUtil = networkUtil;
        this.reg=reg;
        thread=new Thread(this);
        thread.start();
    }

    @Override
    public void run() {
        try{
            JSONObject jsonObject=new JSONObject();
            jsonObject.put("type","deleteCar");
            jsonObject.put("registrationNumber",reg);
            //System.out.println(jsonObject.toString());
            networkUtil.write(jsonObject.toString());
        } catch(JSONException | IOException e){
            e.printStackTrace();

        }
    }
}
