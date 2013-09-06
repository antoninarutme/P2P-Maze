import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;


public class ClientNavigateThread extends Thread {
	
	private Socket socket;
	
	public ClientNavigateThread(Socket s) {
		socket = s;		
	}

	public void run() {
		PrintWriter out = null;
		
		while (socket.isConnected()) {
			System.out.println("Client Navigating Thread starting...");

			try {
				out = new PrintWriter(socket.getOutputStream());
				out.println("Somapala");
				out.flush();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			
			BufferedReader stdin = new BufferedReader(new InputStreamReader(
					System.in));
			try {
				String input = "";
				while (true) {
					input = stdin.readLine();
					while (input != null) {
						input = stdin.readLine();
						out.println(input);
						out.flush();
					}
				}

			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		try {
			out.close();
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
