package Client;

import Model.Car;
import javafx.application.Platform;
import org.json.JSONArray;
import org.json.JSONObject;
import util.NetworkUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ReadServerThread implements Runnable{

    private Thread thread;
    private Main main;
    private List<Car>carList;
    public ReadServerThread(Main main) {

        this.main=main;
        carList=new ArrayList<>();
        thread=new Thread(this);
        thread.start();
    }

    public List<Car> getCarList(){
        return carList;
    }

    @Override
    public void run() {
        try {
            while (true) {
                String serverdata=(String)main.getNetworkUtil().read();
                System.out.println(serverdata);
                JSONObject data = new JSONObject(serverdata);
                System.out.println(data.toString());
                if (data != null) {
                    if(data.getString("type").equals("Authenticated") ) {
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                if (data.getBoolean("status")) {
                                    ;
                                    try {
                                        Main.loginController.isValid("yes");
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                    //System.out.println("Authenticated");
                                }else main.showAlert();
                                //System.out.println(result);
                            }
                        });

                    }
                    else if(data.getString("type").equals("listOfCar") ) {
                        System.out.println("in read server thread" +data.toString());
                        JSONArray jsonArray=data.getJSONArray("list");
                        carList.clear();
                        for(int i=0;i< jsonArray.length();i++){
                            JSONObject jsonObject=(jsonArray.getJSONObject(i));
                            Car c=new Car(jsonObject.getString("registrationNumber"),jsonObject.getString("colour"),
                                    jsonObject.getString("carMake"),jsonObject.getString("carModel"),
                                    Integer.parseInt(jsonObject.getString("yearMade")),Integer.parseInt(jsonObject.getString("price")),
                                    Integer.parseInt(jsonObject.getString("quantity")));
                            carList.add(c);
                        }
                        if(Main.manufacture!=null)
                        Main.manufacture.updateDATA(carList);
                        if(Main.viewer!=null)
                        Main.viewer.updateDATA(carList);
                    }
                    else if(data.getString("type").equals("getCarByReg") ) {
                        System.out.println("in read server thread" +data.toString());
                        carList.clear();
                        Car c=new Car(data.getString("registrationNumber"),data.getString("colour"),
                                data.getString("carMake"),data.getString("carModel"),
                                Integer.parseInt(data.getString("yearMade")),Integer.parseInt(data.getString("price")),
                                Integer.parseInt(data.getString("quantity")));
                        carList.add(c);
                        Main.viewer.updateDATA(carList);
                    }
                    else if(data.getString("type").equals("listOfCarByMake") ) {
                        System.out.println("in read server thread" +data.toString());
                        JSONArray jsonArray=data.getJSONArray("listMake");
                        carList.clear();
                        for(int i=0;i< jsonArray.length();i++){
                            JSONObject jsonObject=(jsonArray.getJSONObject(i));
                            Car c=new Car(jsonObject.getString("registrationNumber"),jsonObject.getString("colour"),
                                    jsonObject.getString("carMake"),jsonObject.getString("carModel"),
                                    Integer.parseInt(jsonObject.getString("yearMade")),Integer.parseInt(jsonObject.getString("price")),
                                    Integer.parseInt(jsonObject.getString("quantity")));
                            carList.add(c);
                        }
                        Main.viewer.updateDATA(carList);
                    }
                    else if(data.getString("type").equals("notFoundCarByMake") ) {
                        System.out.println("in read server thread" +data.toString());
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {

                                Main.AlertCarNotFound("carMake ");
                            }
                        });
                    }

                    else if(data.getString("type").equals("notFoundCarByModel") ) {
                        System.out.println("in read server thread" +data.toString());

                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {

                                Main.AlertCarNotFound("car model ");
                            }
                        });

                    }

                    else if(data.getString("type").equals("notFoundCarByReg") ) {
                        System.out.println("in read server thread" +data.toString());

                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {

                                Main.AlertCarNotFound("registration number ");
                            }
                        });
                    }

                    else if(data.getString("type").equals("listOfCarByModel") ) {
                        System.out.println("in read server thread" +data.toString());
                        JSONArray jsonArray=data.getJSONArray("listModel");
                        carList.clear();
                        for(int i=0;i< jsonArray.length();i++){
                            JSONObject jsonObject=(jsonArray.getJSONObject(i));
                            Car c=new Car(jsonObject.getString("registrationNumber"),jsonObject.getString("colour"),
                                    jsonObject.getString("carMake"),jsonObject.getString("carModel"),
                                    Integer.parseInt(jsonObject.getString("yearMade")),Integer.parseInt(jsonObject.getString("price")),
                                    Integer.parseInt(jsonObject.getString("quantity")));
                            carList.add(c);
                        }
                        Main.viewer.updateDATA(carList);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("in read server exception");
        }finally {
            try {
                main.getNetworkUtil().closeConnection();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
