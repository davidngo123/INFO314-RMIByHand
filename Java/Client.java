import java.io.*;
import java.net.Socket;

public class Client {
    private static Socket socket;
    private static RemoteInput remote;
    private static OutputStream output;
    private static ObjectOutputStream outputObject;
    private static InputStream input;
    private static ObjectInputStream objectInput;

    /**
     * This method name and parameters must remain as-is
     */
    public static int add(int lhs, int rhs) {
        int answer = 0;
        try {
            remote = new RemoteInput("add", new Object[]{lhs, rhs});
            start();
            RemoteInput rm = (RemoteInput) objectInput.readObject();
            Object[] arr = rm.getArguments();
            answer = (int) arr[0];
            end();


        } catch (IOException e) {
            System.out.println("IO Exception error");
        } catch (Exception e) {
            System.out.println("Connection failed! Server refused to connect");
        }
        return answer;
    }

    /**
     * This method name and parameters must remain as-is
     */
    public static int divide(int num, int denom){
        if (denom == 0) {
            throw new ArithmeticException();
        }
        int answer = 0;
        try {
            remote = new RemoteInput("divide", new Object[]{num, denom});
            start();
            RemoteInput rm = (RemoteInput) objectInput.readObject();
            Object[] arr = rm.getArguments();
            answer = (int) arr[0];
            end();


        } catch (IOException e) {
            System.out.println("IO Exception error");
        } catch (Exception e) {
            System.out.println("Connection failed! Server refused to connect");
        }
        return answer;
    }


    private static void start(){
        try {
            socket = new Socket(server, PORT);
            output = socket.getOutputStream();
            outputObject = new ObjectOutputStream(output);
            outputObject.writeObject(remote);
            input = socket.getInputStream();
            objectInput = new ObjectInputStream(input);
        } catch (IOException e){
            System.out.println("IO Exception error");
        } catch (Exception e) {
            System.out.println("Connection failed! Server refused to connect");
        }


    }

    private static void end(){
        try {
            socket.close();
            input.close();
            output.close();
            outputObject.close();
            objectInput.close();
        } catch (IOException e){
            System.out.println("IO Exception issue in client");
        }


    }
    /**
     * This method name and parameters must remain as-is
     */


    public static String echo(String message) {
        String echo = "";
        try{
            remote = new RemoteInput("echo", new Object[]{message});
            start();
            RemoteInput rm = (RemoteInput) objectInput.readObject();
            Object[] arr = rm.getArguments();
            echo = (String) arr[0];
            end();
        }
        catch (IOException e) {
            System.out.println("IO Exception error");
        }

        catch(Exception e) {

            System.out.println("Connection failed!");
        }
        return echo;
    }

    // Do not modify any code below this line
    // --------------------------------------
    static String server = "localhost";
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

        if (echo("Hello").equals( "You said Hello!"))
            System.out.print(".");
        else
            System.out.print("X");

        System.out.println(" Finished");
    }
}