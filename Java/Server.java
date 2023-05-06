import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.io.InputStream;
public class Server {
    public static final int PORT = 10314;
    public static Socket socket;
    public static void main(String[] args){
        try {
            ServerSocket server = new ServerSocket(10314);
            while (true){
                socket = server.accept();

                //instinate the stream objects
                InputStream input = socket.getInputStream();
                OutputStream output = socket.getOutputStream();
                ObjectOutputStream objectOutput = new ObjectOutputStream(output);
                // read the request
                ObjectInputStream objectInput = new ObjectInputStream(input);
                RemoteInput remoteInput = (RemoteInput) objectInput.readObject();
                String method = remoteInput.getMethod();
                Object[] ars = remoteInput.getArguments();
                Object responseBack = null;
                if(method.equals("add")){
                    responseBack = add((int) ars[0], (int) ars[1]);
                }
                RemoteInput serialize = new RemoteInput(method, new Object[]{responseBack});

                objectOutput.writeObject(serialize);

                server.close();



            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    // Do not modify any code below tihs line
    // --------------------------------------
    public static String echo(String message) { 
        return "You said " + message + "!";
    }
    public static int add(int lhs, int rhs) {
        return lhs + rhs;
    }
    public static int divide(int num, int denom) {
        if (denom == 0)
            throw new ArithmeticException();

        return num / denom;
    }
}