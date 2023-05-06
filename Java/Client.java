import java.net.ConnectException;
import java.net.Socket;
import java.io.*;
import java.rmi.Remote;

public class Client {

    private static Socket socket;
    /**
     * This method name and parameters must remain as-is
     */
    public static int add(int lhs, int rhs) {
        int num = 0;
        try{
            socket = new Socket("localhost", PORT);
            RemoteInput method = new RemoteInput("add", new Object[]{lhs, rhs});
            OutputStream output = socket.getOutputStream();
            ObjectOutputStream outputObject = new ObjectOutputStream(output);
            outputObject.writeObject(method);
            InputStream input = socket.getInputStream();
            ObjectInputStream objectInput = new ObjectInputStream(input);
            RemoteInput rm = (RemoteInput) objectInput.readObject();
            Object[] arr = rm.getArguments();
            num = (int) arr[0];

            socket.close();

        }catch(IOException e){
            System.err.println("IO issues: " + e.getMessage());
            System.exit(1);
        } catch(Exception e){
            System.err.println("Failed to connect in general: " + e.getMessage());
            e.printStackTrace();
        }


        return num;
    }
    /**
     * This method name and parameters must remain as-is
     */




    public static int divide(int num, int denom) {
        return -1;
    }
    /**
     * This method name and parameters must remain as-is
     */
    public static String echo(String message) {
        return "";
    }

    // Do not modify any code below this line
    // --------------------------------------
    String server = "localhost";
    public static final int PORT = 10314;

    public static void main(String... args) {
        // All of the code below this line must be uncommented
        // to be successfully graded.
        System.out.print("Testing... ");

        if (add(2, 4) == 6)
            System.out.print(".");
        else
            System.out.print("X");

        try {
            divide(1, 0);
            System.out.print("X");
        }
        catch (ArithmeticException x) {
            System.out.print(".");
        }

        if (echo("Hello").equals("You said Hello!"))
            System.out.print(".");
        else
            System.out.print("X");
        
        System.out.println(" Finished");
    }
}