import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class ClientThread extends Thread {

	private Socket socket;

	public ClientThread(Socket s) {
		socket = s;
		new ClientNavigateThread(s).start();
	}

	public void run() {
		String response;
		BufferedReader in = null;

		while (socket.isConnected()) {
			System.out.println("Client Thread starting...");

			try {
				
				in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

				while (true) {
					response = in.readLine();
					System.out.println("Res1: " + response);
					while (response != null) {
						response = in.readLine();
						System.out.println("Res2: " + response);
						try {
							Thread.sleep(1000);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				}

			} catch (IOException e) {
				e.printStackTrace();
			} 
		}
		
		try {
			in.close();
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
