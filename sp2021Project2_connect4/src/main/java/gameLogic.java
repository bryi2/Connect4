public class GameLogic  {
	
	// We can set all methods to static since everything in this class is used to verify, confirm, and check for wins. No modifications.
	public static int currentPlayer = 0;
	public static int [][] logicBoard = new int [6][7];
	// 1st step in validating move
	public static boolean checkIfBottomRow(int row) {
	
		if (row == 5) {
			return true;
		}
		return false;
	}
	
	// trying to find efficient way of just keeping track of player# i cant think rn
	public static int validMovePlayer() {
		
		return ((currentPlayer%2)+1);
	}
	public static int reverseMovePlayer() {
		
		return ((currentPlayer%2)-1);
	}
	public static void addLogicBoard(int row, int column) {
		// increment currPlayer each valid move
		currentPlayer++;
		//System.out.println("LINE 27:row and column inside is: " + row  + column);
		// if player #2 then fill spot with 2
		
		if (getCurrPlayer() == 2) {
			System.out.println("Inside add, for player 2");
			logicBoard[row][column] = 2;
		} else {
		// if player#1 fill spot with 1
		logicBoard[row][column] = 1;
		}
	}
	
	public static boolean isWinner(int player, int[][] logicBoard){
		//check for 4 across
		for(int row = 0; row<logicBoard.length; row++){
			for (int col = 0;col < logicBoard[0].length - 3;col++){
				if (logicBoard[row][col] == player   && 
					logicBoard[row][col+1] == player &&
					logicBoard[row][col+2] == player &&
					logicBoard[row][col+3] == player){
					return true;
				}
			}			
		}
		//check for 4 up and down
		for(int row = 0; row < logicBoard.length - 3; row++){
			for(int col = 0; col < logicBoard[0].length; col++){
				if (logicBoard[row][col] == player   && 
					logicBoard[row+1][col] == player &&
					logicBoard[row+2][col] == player &&
					logicBoard[row+3][col] == player){
					return true;
				}
			}
		}
		//check upward diagonal
		for(int row = 3; row < logicBoard.length; row++){
			for(int col = 0; col < logicBoard[0].length - 3; col++){
				if (logicBoard[row][col] == player   && 
					logicBoard[row-1][col+1] == player &&
					logicBoard[row-2][col+2] == player &&
					logicBoard[row-3][col+3] == player){
					return true;
				}
			}
		}
		//check downward diagonal
		for(int row = 0; row < logicBoard.length - 3; row++){
			for(int col = 0; col < logicBoard[0].length - 3; col++){
				if (logicBoard[row][col] == player   && 
					logicBoard[row+1][col+1] == player &&
					logicBoard[row+2][col+2] == player &&
					logicBoard[row+3][col+3] == player){
					return true;
				}
			}
		}
		return false;
	}
	
	// getter currPlyaer
	public static int getCurrPlayer() {
		if (currentPlayer%2 == 1) {
			return 1;
		}
		return 2;
	}
	
	//check using getter
	public static int getBoard(int row, int column) {
		int x = logicBoard[row][column];
		return x;
	}
	
	public static int[][] getLogic() {
		return logicBoard;
	}
}
