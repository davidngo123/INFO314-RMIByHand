import java.io.Serializable;
import java.rmi.RemoteException;

public class RemoteInput implements Serializable {
        private String method;
        private Object[] args;

        public RemoteInput(String methodName, Object[] args) throws RemoteException {
            this.method = methodName;
            this.args = args;
        }

        public String getMethod(){
            return method;
        }

        public Object[] getArguments(){
            return args;
        }
}
