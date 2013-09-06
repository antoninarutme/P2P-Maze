import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author janathraveendras
 * 
 */

public class ServerListener extends Thread {

	ServerSocket serverSocket;
	Socket s;
	int client_count;

	public static void main(String[] args) {
		new ServerListener().start();
	}

	public void run() {
		System.out.println(" Server thread starting..");

		try {
			serverSocket = new ServerSocket(14444);
			while (true) {
				s = serverSocket.accept();
				client_count++;
				
				System.out.println("Client connected. Count: " + client_count );
				
				new ServerThread(s).start();
				
			}
		} catch (IOException e) {
			e.printStackTrace();
		} 

		try {
			s.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
