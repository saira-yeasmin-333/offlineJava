package ServerDatabase;

import org.json.JSONArray;
import org.json.JSONObject;
import util.NetworkUtil;

import java.io.IOException;

public class ReadClientThread implements Runnable{
    private Thread thread;
    private NetworkUtil networkUtil;

    public ReadClientThread( NetworkUtil networkUtil) {

        this.networkUtil = networkUtil;
        thread=new Thread(this);
        thread.start();
    }

    @Override
    public void run() {
        try {
            while (true) {
                System.out.println("read client thread start");
                JSONObject jsonObject = new JSONObject((String) networkUtil.read());
                if (jsonObject != null && jsonObject.getString("type").equals("login")) {
                    JSONObject object= Database.getInstance().Login(jsonObject.getString("username"),jsonObject.getString("password"));
                    object.put("type", "Authenticated");
                    System.out.print(object.toString());
                    networkUtil.write(object.toString());
                }
                else  if (jsonObject != null && jsonObject.getString("type").equals("CarList")) {
                    System.out.println(jsonObject.toString());
                    Database.getInstance().insertCar(jsonObject.getString("registrationNumber"),jsonObject.getString("colour"),
                            jsonObject.getString("carMake"),jsonObject.getString("carModel"),jsonObject.getString("yearMade"),
                            jsonObject.getString("price"),jsonObject.getString("quantity"));
                    System.out.println("carlist in server read client thread");

                }
                else  if (jsonObject != null && jsonObject.getString("type").equals("showCar")) {
                    //System.out.println(jsonObject.toString());
                    JSONArray jArray = new JSONArray();
                    jArray=Database.getInstance().showCarList();
//                    for (int i = 0; i < jArray.length(); i++)
//                    {
//                        JSONObject json_data = jArray.getJSONObject(i);
//                        System.out.println(json_data.getString("registrationNumber"));
//                        System.out.println(json_data.getString("quantity"));
//                    }
                    JSONObject object=new JSONObject();
                    object.put("type", "listOfCar");
                    object.put("list",jArray);
                    System.out.println("i need both\n"+object.toString());
                    networkUtil.write(object.toString());
                }

                else if (jsonObject != null && jsonObject.getString("type").equals("deleteCar")) {
                     Database.getInstance().deleteCar(jsonObject.getString("registrationNumber"));

                }
                else if (jsonObject != null && jsonObject.getString("type").equals("editCar")) {
                    System.out.println("in before");
                    Database.getInstance().editCar(jsonObject.getString("newValue"),jsonObject.getString("fieldName"),jsonObject.getString("registrationNumber"));
                    System.out.println("in after");
                }

                else if (jsonObject != null && jsonObject.getString("type").equals("buy")) {
                    System.out.println("in before");
                    Database.getInstance().buyCar(jsonObject.getString("registrationNumber"),jsonObject.getString("quantity"));
                    System.out.println("in after");
                }

                else if (jsonObject != null && jsonObject.getString("type").equals("searchCarByRegistration")) {
                    JSONObject object= Database.getInstance().showCarSearchByReg(jsonObject.getString("registrationNumber"));
                    if(object.isEmpty())object.put("type","notFoundCarByReg");
                    else
                    object.put("type", "getCarByReg");
                    System.out.print(object.toString());
                    networkUtil.write(object.toString());
                }
                else  if (jsonObject != null && jsonObject.getString("type").equals("searchCarListByMake")) {
                    System.out.println(jsonObject.toString()+" in search car list by make in read client thread");
                    JSONArray jArray = new JSONArray();
                    jArray=Database.getInstance().showCarSearchByMake(jsonObject.getString("carMake"));
                    for (int i = 0; i < jArray.length(); i++)
                    {
                        JSONObject json_data = jArray.getJSONObject(i);
                        System.out.println(json_data.getString("registrationNumber"));
                        System.out.println(json_data.getString("quantity"));
                    }
                    JSONObject object=new JSONObject();
                    if(jArray.isEmpty())
                    object.put("type", "notFoundCarByMake");
                    else
                    {
                        object.put("type", "listOfCarByMake");
                        object.put("listMake",jArray);
                        System.out.println("i need both\n"+object.toString());
                    }
                    networkUtil.write(object.toString());
                }
                else  if (jsonObject != null && jsonObject.getString("type").equals("searchCarListByModel")) {
                    System.out.println(jsonObject.toString()+" in read client thread");
                    JSONArray jArray = new JSONArray();
                    jArray=Database.getInstance().showCarSearchByModel(jsonObject.getString("carModel"));
                    for (int i = 0; i < jArray.length(); i++)
                    {
                        JSONObject json_data = jArray.getJSONObject(i);
                        System.out.println(json_data.getString("registrationNumber"));
                        System.out.println(json_data.getString("quantity"));
                    }
                    JSONObject object=new JSONObject();
                    if(jArray.isEmpty())
                        object.put("type", "notFoundCarByModel");
                    else{
                        object.put("type", "listOfCarByModel");
                        object.put("listModel",jArray);
                        System.out.println("i need both\n"+object.toString());
                    }
                    networkUtil.write(object.toString());
                }
            }
        } catch (Exception e) {
            //System.out.println(e);
        } finally {
            try {
                networkUtil.closeConnection();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
