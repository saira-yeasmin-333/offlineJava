package ServerDatabase;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import sun.security.jca.GetInstance;

import java.sql.*;

public class Database {

    private static Database instance=null;
    private Connection connect;

    public static synchronized Database getInstance(){
        if(instance==null){
            instance=new Database();
        }
        return instance;
    }

    private Database (){
      connect=Connect();
    }

    public  Connection Connect(){
        Connection con=null;

        try {

            Class.forName("org.sqlite.JDBC");
            con= DriverManager.getConnection("jdbc:sqlite:E:\\documents-e-drive\\SQLDBProject\\users.db");


        }catch (ClassNotFoundException | SQLException e){
            System.out.println(e+"");
            e.printStackTrace();
        }
        return con;

    }

    public JSONObject Login(String userName, String password) throws SQLException, JSONException {
        JSONObject jsonObject=new JSONObject();
        //PreparedStatement ps=null;
        //ResultSet rs=null;
        //System.out.print(userName+" "+password);
        try{
            Statement statement=connect.createStatement();
            ResultSet rs=statement.executeQuery("select * from Clients where username=\""+userName+"\" and password=\""+password+"\"");
            /*String sql="SELECT * FROM Clients where username=? AND password=?";
            ps=connect.prepareStatement(sql);
            ps.setString(1,"\""+userName+"\"");
            ps.setString(2,"\""+password+"\"");
            rs=ps.executeQuery();*/
            if(rs.next()){
                jsonObject.put("status",true);
                System.out.println("hi");
            }
            else{
                jsonObject.put("status",false);
                //System.out.println("bye");

            }
        }catch(SQLException e){
            System.out.println(e.toString());
        }
        return jsonObject;
    }

    public JSONObject showCarSearchByReg(String s) throws SQLException, JSONException {
        PreparedStatement ps=null;
        ResultSet rs=null;
        JSONObject jsonObject=new JSONObject();
        try{
            String sql="SELECT * FROM cars where registrationNumber=?";
            ps=connect.prepareStatement(sql);
            ps.setString(1,s);
            rs=ps.executeQuery();
            if(rs.next()){
                jsonObject.put("registrationNumber",rs.getString("registrationNumber"));
                jsonObject.put("colour",rs.getString("colour"));
                jsonObject.put("carMake",rs.getString("carMake"));
                jsonObject.put("carModel",rs.getString("carModel"));
                jsonObject.put("yearMade",rs.getString("yearMade"));
                jsonObject.put("price",rs.getString("price"));
                jsonObject.put("quantity",rs.getString("quantity"));
            }

        }catch(SQLException e){
            System.out.println(e.toString());
        }
        return jsonObject;
    }

    public JSONArray showCarSearchByMake(String s) throws SQLException, JSONException {
        JSONArray jsonArray=new JSONArray();
        int i=0;
        PreparedStatement ps=null;
        ResultSet rs=null;
        try{
            String sql="SELECT * FROM cars where carMake=?";
            ps=connect.prepareStatement(sql);
            ps.setString(1,s);
            rs=ps.executeQuery();
            while(rs.next()){
                JSONObject jsonObject=new JSONObject();
                jsonObject.put("registrationNumber",rs.getString("registrationNumber"));
                jsonObject.put("colour",rs.getString("colour"));
                jsonObject.put("carMake",rs.getString("carMake"));
                jsonObject.put("carModel",rs.getString("carModel"));
                jsonObject.put("yearMade",rs.getString("yearMade"));
                jsonObject.put("price",rs.getString("price"));
                jsonObject.put("quantity",rs.getString("quantity"));
                jsonArray.put(jsonObject);
            }

        }catch(SQLException e){
            System.out.println(e.toString());
        }
        return jsonArray;
    }

    public JSONArray showCarSearchByModel(String s) throws SQLException, JSONException {
        JSONArray jsonArray=new JSONArray();
        int i=0;
        PreparedStatement ps=null;
        ResultSet rs=null;
        try{
            String sql="SELECT * FROM cars where carModel=?";
            ps=connect.prepareStatement(sql);
            ps.setString(1,s);
            rs=ps.executeQuery();
            while(rs.next()){
                JSONObject jsonObject=new JSONObject();
                jsonObject.put("registrationNumber",rs.getString("registrationNumber"));
                jsonObject.put("colour",rs.getString("colour"));
                jsonObject.put("carMake",rs.getString("carMake"));
                jsonObject.put("carModel",rs.getString("carModel"));
                jsonObject.put("yearMade",rs.getString("yearMade"));
                jsonObject.put("price",rs.getString("price"));
                jsonObject.put("quantity",rs.getString("quantity"));
                jsonArray.put(jsonObject);
            }

        }catch(SQLException e){
            System.out.println(e.toString());
        }
        return jsonArray;
    }

    public JSONArray showCarList() throws SQLException, JSONException {
        JSONArray jsonArray=new JSONArray();
        int i=0;
        PreparedStatement ps=null;
        ResultSet rs=null;
        try{
            String sql="SELECT * FROM cars";
            ps=connect.prepareStatement(sql);
            rs=ps.executeQuery();
            while(rs.next()){
                JSONObject jsonObject=new JSONObject();
                jsonObject.put("registrationNumber",rs.getString("registrationNumber"));
                jsonObject.put("colour",rs.getString("colour"));
                jsonObject.put("carMake",rs.getString("carMake"));
                jsonObject.put("carModel",rs.getString("carModel"));
                jsonObject.put("yearMade",rs.getString("yearMade"));
                jsonObject.put("price",rs.getString("price"));
                jsonObject.put("quantity",rs.getString("quantity"));
                jsonArray.put(jsonObject);
            }

        }catch(SQLException e){
            System.out.println(e.toString());
        }
        return jsonArray;
    }

    public void insertCar(String s1,String s2,String s3,String s4,String s5,String s6,String s7){
        JSONObject jsonObject=new JSONObject();
        PreparedStatement ps=null;
        try {
            String sql="INSERT INTO cars (registrationNumber,colour,carMake,carModel,yearMade,price,quantity) VALUES(?,?,?,?,?,?,?)";
            ps=connect.prepareStatement(sql);
            ps.setString(1,s1);
            ps.setString(2,s2);
            ps.setString(3,s3);
            ps.setString(4,s4);
            ps.setInt(5,Integer.parseInt(s5));
            ps.setInt(6,Integer.parseInt(s6));
            ps.setInt(7,Integer.parseInt(s7));
            ps.execute();

            System.out.println("Data has been inserted");

        }catch (SQLException e){
            System.out.println(e.toString());
        }

    }

    public void deleteCar(String s1){

        PreparedStatement ps = null;
        try {
            String sql = "delete from cars WHERE registrationNumber = ? ";
            ps =connect.prepareStatement(sql);
            ps.setString(1, s1);
            ps.execute();
            System.out.println("Data has been deleted!");
        } catch (Exception e) {
            // TODO: handle exception
            System.out.println(e.toString());
        } finally {
            //always remember to close, am forgetting because this is teaching purposes
        }
    }

    public void editCar(String s1,String s2,String s3){
        PreparedStatement ps = null;
        try {
            if(s2.equals("colour")){
                String sql = "UPDATE cars set colour = ? WHERE registrationNumber = ? ";
                ps = connect.prepareStatement(sql);
                ps.setString(1, s1);
                ps.setString(2, s3);
                ps.execute();
                System.out.println("Data has been updated");

            }
            else if(s2.equals("carMake")){
                String sql = "UPDATE cars set carMake = ? WHERE registrationNumber = ? ";
                ps = connect.prepareStatement(sql);
                ps.setString(1, s1);
                ps.setString(2, s3);
                ps.execute();
                System.out.println("Data has been updated");

            }
            else if(s2.equals("carModel")){
                String sql = "UPDATE cars set carModel = ? WHERE registrationNumber = ? ";
                ps = connect.prepareStatement(sql);
                ps.setString(1, s1);
                ps.setString(2, s3);
                ps.execute();
                System.out.println("Data has been updated");

            }
            else if(s2.equals("year")){
                System.out.println(" database start");
                String sql = "UPDATE cars set yearMade = ? WHERE registrationNumber = ? ";
                ps = connect.prepareStatement(sql);
                ps.setInt(1, Integer.parseInt(s1));
                ps.setString(2, s3);
                ps.execute();
                System.out.println("Data has been updated");

            }
            else if(s2.equals("price")){
                String sql = "UPDATE cars set price = ? WHERE registrationNumber = ? ";
                ps = connect.prepareStatement(sql);
                ps.setInt(1, Integer.parseInt(s1));
                ps.setString(2, s3);
                ps.execute();
                System.out.println("Data has been updated");

            }
            else if(s2.equals("quantity")){
                String sql = "UPDATE cars set quantity = ? WHERE registrationNumber = ? ";
                ps = connect.prepareStatement(sql);
                ps.setInt(1, Integer.parseInt(s1));
                ps.setString(2, s3);
                ps.execute();
                System.out.println("Data has been updated");

            }

        } catch (SQLException e) {
            // TODO: handle exception
            System.out.println(e.toString());
        }
    }

    public void buyCar(String registrationNumber,String s) {

        PreparedStatement ps = null;
        try {
            String sql = "UPDATE cars set quantity = ? WHERE registrationNumber = ? ";
            ps =connect.prepareStatement(sql);
            ps.setInt(1,Integer.parseInt(s)-1);
            ps.setString(2, registrationNumber);
            ps.execute();
            System.out.println("quantity has been updated!");
        } catch (Exception e) {
            // TODO: handle exception
            System.out.println(e.toString());
        }

    }
}

