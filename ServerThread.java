import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * @author janathraveendras
 *
 */
public class ServerThread extends Thread {
	
	private Socket socket;
	
	public ServerThread(Socket s) {
		socket = s;
	}
	
	public void run() {
		System.out.println("Server Thread Started.");
		BufferedReader in = null;
		PrintWriter out = null;
		String input = null;
		
		try {
			out = new PrintWriter(socket.getOutputStream());
			in = new BufferedReader(new InputStreamReader(
					socket.getInputStream()));
			input = in.readLine();
			System.out.println("Name1: " + input );
			
			out.println("Welcome : " + input);
			out.flush();
			
			while (true) {
				input = in.readLine();
				while (input != null) {
					input = in.readLine();
					System.out.println("Name2: " + input );
				}
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
				
		try {
			out.close();
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

}
