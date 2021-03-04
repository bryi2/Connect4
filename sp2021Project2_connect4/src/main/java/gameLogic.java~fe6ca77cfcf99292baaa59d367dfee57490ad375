public class GameLogic  {
	
	// We can set all methods to static since everything in this class is used to verify, confirm, and check for wins. No modifications.
	public static int currentPlayer = 0;
	public static int [][] logicBoard = new int [6][7];
	
	static int[] win1 = new int[2];
	static int[] win2 = new int[2];
	static int[] win3 = new int[2];
	static int[] win4 = new int[2];
	
	static int[][] winningSet = new int[][]{win1, win2, win3, win4};
	
	// 1st step in validating move
	public static boolean checkIfBottomRow(int row) {
		if (row == 5) {
			return true;
		}
		return false;
	}
	public static void addLogicBoard(int row, int column) {
		// increment currPlayer each valid move
		currentPlayer++;
		if (getCurrPlayer() == 2) {
			System.out.println("Inside add, for player 2");
			logicBoard[row][column] = 2;
		} else {
		// if player#1 fill spot with 1
		logicBoard[row][column] = 1;
		}
	}
	public static void removeLogicBoard(int row, int column) {
		currentPlayer--; // this updates the player to previous one
		logicBoard[row][column] = -1; // basically removes the previous move, so won't affect the isWinner function
	}
	
	public static boolean isWinner(int player, int[][] logicBoard){
		// checking for horiztonal win
		for(int i = 0; i<logicBoard.length; i++){
			for (int j = 0;j < logicBoard[0].length - 3;j++){
				if (logicBoard[i][j] == player   && 
					logicBoard[i][j+1] == player &&
					logicBoard[i][j+2] == player &&
					logicBoard[i][j+3] == player){
					
					win1[0] = i;
					win1[1] = j;
					win2[0] = i;
					win2[1] = j+1;
					win3[0] = i;
					win3[1] = j+2;
					win4[0] = i;
					win4[1] = j+3;
				
					
					return true;
				}
			}			
		}
		// checking for vertical win
		for(int i = 0; i < logicBoard.length - 3; i++){
			for(int j = 0; j < logicBoard[0].length; j++){
				if (logicBoard[i][j] == player   && 
					logicBoard[i+1][j] == player &&
					logicBoard[i+2][j] == player &&
					logicBoard[i+3][j] == player){
					
					win1[0] = i;
					win1[1] = j;
					win2[0] = i+1;
					win2[1] = j;
					win3[0] = i+2;
					win3[1] = j;
					win4[0] = i+3;
					win4[1] = j;
					return true;
				}
			}
		}
		// check upward diagonal
		for(int i = 3; i < logicBoard.length; i++){
			for(int j = 0; j < logicBoard[0].length - 3; j++){
				if (logicBoard[i][j] == player   && 
					logicBoard[i-1][j+1] == player &&
					logicBoard[i-2][j+2] == player &&
					logicBoard[i-3][j+3] == player){
					win1[0] = i;
					win1[1] = j;
					win2[0] = i-1;
					win2[1] = j+1;
					win3[0] = i-2;
					win3[1] = j+2;
					win4[0] = i-3;
					win4[1] = j+3;
					return true;
				}
			}
		}
		//check downward diagonal
		for(int i = 0; i < logicBoard.length - 3; i++){
			for(int j = 0; j < logicBoard[0].length - 3; j++){
				if (logicBoard[i][j] == player   && 
					logicBoard[i+1][j+1] == player &&
					logicBoard[i+2][j+2] == player &&
					logicBoard[i+3][j+3] == player){
					
					win1[0] = i;
					win1[1] = j;
					win2[0] = i+1;
					win2[1] = j+1;
					win3[0] = i+2;
					win3[1] = j+2;
					win4[0] = i+3;
					win4[1] = j+3;
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
	// getter for our board
	public static int[][] getLogic() {
		return logicBoard;
	}
	
	public static int[][] getWinningSet() {
		return winningSet;
	}
	
	public static void clearLogicBoard() {
		for (int row = 0; row < logicBoard.length; row++)
		{
			for (int col = 0; col < logicBoard[row].length; col++)
		    {
				logicBoard[row][col] = -1;
		    }
		}
	}
}
