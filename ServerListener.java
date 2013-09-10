import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author janathraveendras
 * 
 */

public class ServerListener extends Thread {
	final int MAP_WIDTH = 4;
	final int MAP_HEIGHT = 4;
	final int TREASURE_AMOUNT = 5;

	ServerSocket serverSocket;
	Socket s;
	int client_count;

	GameData globalData;

	public static void main(String[] args) {
		new ServerListener().start();
	}

	public void run() {
		System.out.println(" Server thread starting..");

		globalData = new GameData(MAP_WIDTH, MAP_HEIGHT, TREASURE_AMOUNT);

		try {
			serverSocket = new ServerSocket(14444);
			while (true) {
				s = serverSocket.accept();
				client_count++;
				
				System.out.println("Client connected. Count: " + client_count );
				
				new ServerThread(s, globalData).start();
				
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
