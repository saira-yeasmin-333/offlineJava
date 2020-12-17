package util;

import java.io.IOException;
import java.util.Scanner;

public class WriteThread implements Runnable {

    private Thread thr;
    private NetworkUtil nc;
    String name;

    public WriteThread(NetworkUtil nc, String name) {
        this.nc = nc;
        this.name = name;
        this.thr = new Thread(this);
        thr.start();
    }

    public void run() {
        try {
            Scanner input = new Scanner(System.in);
            while (true) {
                String s = input.nextLine();
                nc.write(name + ":" + s);
            }
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            try {
                nc.closeConnection();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}



