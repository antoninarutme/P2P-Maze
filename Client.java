import java.net.Socket;

/**
 * @author janathraveendras
 *
 */
public class Client {
	
	public static void main(String args[]) {
		
        try {
            Socket socket = new Socket("localhost", 14444);
            Thread.sleep(100);
            new ClientThread(socket).start();
        } 
        catch (Exception e) {
        	throw new RuntimeException(e);
        }  
	}
}
