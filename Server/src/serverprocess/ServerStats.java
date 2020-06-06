package serverprocess;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerStats {
    private static ServerSocket serverSocket = null;
    private static Socket cl = null;
    public static int connectionNumber = 0;
    private static String stateInfo = new String ();

    public static void setStateInfo(String stateInfo) {
        ServerStats.stateInfo = stateInfo;
    }

    public static String getStateInfo() {
        return stateInfo;
    }

    private ServerStats() {
    }

    public static void launchOptions() {
        setStateInfo ( " Сервер активирован" );
        System.out.println ( "Состояние..." + getStateInfo () );
    }

    public static void createLink() throws IOException {
        try {
            cl = new Socket ();
            cl = serverSocket.accept ();
        } catch (IOException ex) {
            ex.printStackTrace ();
        }
        setStateInfo ( " В ожидании" );
    }

    public static void connectionOptions() {
        System.out.println ( "Включен..." );
        System.out.println (connectionNumber + " клиент(ов) на сервере");
    }

    public static void afterLinking() {
        System.out.println ( "Состояние..." + getStateInfo () );
        connectionNumber++;
        System.out.println ( connectionNumber + " клиент подключился к серверу " + "\n" + "Адрес порта:" + cl.getLocalPort () + "\n" + "ip адрес клиента:" + cl.getInetAddress () );
    }

    public static void start() {
        Thread t = new Thread ( new Work ( cl ) );
        t.start ();
    }

    public static void startServer() throws IOException {
        try {
            launchOptions ();
            serverSocket = new ServerSocket (  8080 );
            while (true) {
                connectionOptions ();
                createLink ();
                afterLinking ();
                start ();
            }
        } catch (IOException ex) {
            ex.printStackTrace ();
        } finally {
            serverSocket.close ();
            cl.close ();
        }
    }
}
