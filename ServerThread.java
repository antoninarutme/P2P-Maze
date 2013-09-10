import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

/**
 * @author janathraveendras
 *
 */
public class ServerThread extends Thread {
	
	private Socket socket;
	private GameData globalData;
	private int playerId;
	
	public ServerThread(Socket s, GameData data) {
		socket = s;
		globalData = data;
		playerId = globalData.addPlayer();
	}
	
	public void run() {
		System.out.println("Server Thread Started.");
		BufferedReader in = null;
		PrintWriter out = null;
		String input = null;

		ArrayList<ArrayList<Integer> > gameMap;
		
		try {
			out = new PrintWriter(socket.getOutputStream());
			in = new BufferedReader(new InputStreamReader(
					socket.getInputStream()));
			input = in.readLine();
			System.out.println("Hello player " + playerId);
			
			out.println("Hello player " + playerId);
			out.flush();

			while (!globalData.checkGameEnd()) {
				out.println("Game map:");
				gameMap = globalData.getMap();
				for(int i = 0; i < gameMap.size(); i++){
					for(int j = 0; j < gameMap.get(i).size(); j++){
						out.printf(gameMap.get(i).get(j));
					}
					out.println();
				}
				out.flush();

				input = in.readLine();
				if(input.compareTo("d") == 0){
					globalData.move(playerId, DirectionEnum.MOVE_DOWN);
				}
				if(input.compareTo("l") == 0){
					globalData.move(playerId, DirectionEnum.MOVE_LEFT);
				}
				if(input.compareTo("n") == 0){
					globalData.move(playerId, DirectionEnum.MOVE_NONE);
				}
				if(input.compareTo("r") == 0){
					globalData.move(playerId, DirectionEnum.MOVE_RIGHT);
				}
				if(input.compareTo("u") == 0){
					globalData.move(playerId, DirectionEnum.MOVE_UP);
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
