package networking.http;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class SimpleHttpClient
{
    public static void main(String[] args) throws Exception {
        new MyThread(0, "/").start();
        new MyThread(1, "/test1").start();
        new MyThread(11, "/test1/test1").start();
        new MyThread(2, "/test2").start();
        new MyThread(21, "/test2/test1").start();
        new MyThread(3, "/test3").start();
    }
}

class MyThread extends Thread
{

    private volatile int counter;
    private volatile String path;

    public MyThread(int counter, String path) {
        this.counter = counter;
        this.path = path;
    }

    public void run() {
        try {
            HttpURLConnection hc = (HttpURLConnection) new URL("http", "localhost", 8000, path).openConnection();
            hc.setRequestMethod("GET");
            hc.connect();
            BufferedReader br = new BufferedReader(new InputStreamReader(hc.getInputStream()));
            String line = null;
            while ((line = br.readLine()) != null) {
                System.out.println(counter + ":" + line);
            }
            br.close();
            hc.disconnect();
        } catch (Exception ex) {
            ex.printStackTrace(System.out);
        }
    }
}

