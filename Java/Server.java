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
            ServerSocket server = new ServerSocket(PORT);
            boolean keepRunning = true;
            while (keepRunning) {
                socket = server.accept();
                if(socket == null){
                    keepRunning = false;
                }
                System.out.println("Server running on: " + PORT);
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
                if(method.equals("add")) {
                    responseBack = add((int) ars[0], (int) ars[1]);
                } else if(method.equals("divide")){
                    responseBack = divide((int) ars[0], (int) ars[1]);
                } else if(method.equals("echo")){
                    responseBack = echo((String) ars[0]);
                } else {
                    throw new IllegalArgumentException("Must be a valid method name");
                }
                RemoteInput serialize = new RemoteInput(method, new Object[]{
                        responseBack});

                //Close everything (make into util func?)
                objectOutput.writeObject(serialize);
                objectOutput.flush();
                objectOutput.close();
                output.close();
                input.close();
                objectInput.close();

            }
            server.close();
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