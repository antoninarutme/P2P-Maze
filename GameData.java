
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
		int playerIndex = 1;

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
		while(true){
			int playerLocation = rand_.nextInt(amtSquares);
			if(map_.get(playerLocation/mazeWidth).get(playerLocation%mazeWidth) == EMPTY_SQUARE){
				map_.get(playerLocation/mazeWidth).set(playerLocation%mazeWidth, playerIndex);
				playerLocY_.add(playerLocation%mazeWidth);
				playerLocX_.add(playerLocation%mazeWidth);
				playerTreasure_.add(0);
				playerIndex++;
				playerAmt--;
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
			case DirectionEnum.MOVE_DOWN:
				currY = prevY + 1;
				if(currY >= mapHeight_) currY = prevY;
				break;
			case DirectionEnum.MOVE_LEFT:
				currX= prevX - 1;
				if(currX < 0) currX = prevX;
				break;
			case DirectionEnum.MOVE_RIGHT:
				currX = prevX + 1;
				if(currX >= mapWidth_) currX = prevX;
				break;
			case DirectionEnum.MOVE_UP:
				currY = prevY - 1;
				if(currY < 0) currY = prevY;
				break;
			default:
				break;
		}

		if(map_[currY][currX] == TREASURE_SQUARE){
			playerTreasure_.get(playerId)++;
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