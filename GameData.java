import java.util.ArrayList;
import java.util.Random;

/**
 * @author ivanrnld
 *
 */

public class GameData{
	final int TREASURE_SQUARE = -1;
	final int EMPTY_SQUARE = 0;

	ArrayList<ArrayList<Integer> > map_;
	ArrayList<Integer> playerLocX_, playerLocY_, playerTreasure_;
	int mapWidth_, mapHeight_;
	int treasureLeft_;
	Random rand_ = new Random();

	public GameData(int mazeWidth, int mazeHeight, int treasureAmt){
		map_ = new ArrayList<ArrayList<Integer> >();
		playerLocX_ = new ArrayList<Integer>();
		playerLocY_ = new ArrayList<Integer>();
		playerTreasure_ = new ArrayList<Integer>();
		mapWidth_ = mazeWidth;
		mapHeight_ = mazeHeight;
		int amtSquares = mazeWidth*mazeHeight;

		for(int i = 0; i < mazeHeight; i++){
			ArrayList<Integer> temp = new ArrayList<Integer>();
			for(int j = 0; j < mazeWidth; j++){
				temp.add(EMPTY_SQUARE);
			}
			map_.add(temp);
		}

		treasureLeft_ = treasureAmt;

		while(treasureAmt > 0){
			int treasureLocation = rand_.nextInt(amtSquares);
			if(map_.get(treasureLocation/mazeWidth).get(treasureLocation%mazeWidth) == EMPTY_SQUARE){
				map_.get(treasureLocation/mazeWidth).set(treasureLocation%mazeWidth, TREASURE_SQUARE);
				treasureAmt--;
			}
		}

		// Index 0 is not used
		playerLocY_.add(0);
		playerLocX_.add(0);
		playerTreasure_.add(0);
	}

	public synchronized int addPlayer(){
		int amtSquares = mapWidth_*mapHeight_;

		while(true){
			int playerLocation = rand_.nextInt(amtSquares);
			if(map_.get(playerLocation/mapWidth_).get(playerLocation%mapWidth_) == EMPTY_SQUARE){
				map_.get(playerLocation/mapWidth_).set(playerLocation%mapWidth_, playerLocY_.size());
				playerLocY_.add(playerLocation%mapWidth_);
				playerLocX_.add(playerLocation%mapWidth_);
				playerTreasure_.add(0);
				break;
			}
		}

		return playerLocY_.size() - 1;
	}

	public synchronized void move(int playerId, DirectionEnum movement){
		int prevX = playerLocX_.get(playerId);
		int prevY = playerLocY_.get(playerId);

		int currX = prevX;
	 	int currY = prevY;

		switch(movement){
			case MOVE_DOWN:
				currY = prevY + 1;
				if(currY >= mapHeight_) currY = prevY;
				break;
			case MOVE_LEFT:
				currX= prevX - 1;
				if(currX < 0) currX = prevX;
				break;
			case MOVE_RIGHT:
				currX = prevX + 1;
				if(currX >= mapWidth_) currX = prevX;
				break;
			case MOVE_UP:
				currY = prevY - 1;
				if(currY < 0) currY = prevY;
				break;
			default:
				break;
		}

		if(map_.get(currY).get(currX) == TREASURE_SQUARE){
			playerTreasure_.set(playerId, playerTreasure_.get(playerId) + 1);
			map_.get(currY).set(currX, EMPTY_SQUARE);
			treasureLeft_--;
		}

		if(map_.get(currY).get(currX) == EMPTY_SQUARE){
			map_.get(currY).set(currX, playerId);
			map_.get(prevY).set(prevX, EMPTY_SQUARE);
			playerLocX_.set(playerId, currX);
			playerLocY_.set(playerId, currY);
		}
	}

	public synchronized ArrayList<ArrayList<Integer> > getMap(){
		return map_;
	}

	public synchronized ArrayList<Integer> getPlayerTreasureList(){
		return playerTreasure_;
	}

	public synchronized int getTreasureLeft(){
		return treasureLeft_;
	}

	public synchronized boolean checkGameEnd(){
		if(treasureLeft_ <= 0) return true;
		return false;
	}
}