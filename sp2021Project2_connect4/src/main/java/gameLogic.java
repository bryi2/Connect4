
public class gameLogic  {
	
	// We can set all methods to static since everything in this class is used to verify, confirm, and check for wins. No modifications.
	public static int currentPlayer = 0;
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

	
	
	
}
