
public class gameLogic  {
	
	// We can set all methods to static since everything in this class is used to verify, confirm, and check for wins. No modifications.
	// if odd then player 1 red,  even == player #2 blue
	public static int currentPlayer = 0;
	public static int [][] logicBoard = new int [6][7];
	
	// 1st step in validating move
	public static boolean checkIfBottomRow(int row) {
	
		if (row == 5) {
			return true;
		}
		return false;
	}
	
//	// trying to find efficient way of just keeping track of player# i cant think rn
//	public static int validMovePlayer() {
//		
//		return ((currentPlayer%2)+1);
//	}
//	public static int reverseMovePlayer() {
//		
//		return ((currentPlayer%2)-1);
//	}
	
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
	
	public static boolean checkBoard(int row, int column) {
		System.out.println("row, column: "+ row + column);
	
		//System.out.println("inside check Win, current player is: " + getCurrPlayer());
		//System.out.println("check acutal board to see whats in the row/column. Value is: " +getBoard(row,column));
		// for rows [0-2], check column + 1 
		if (row <= 2 && column <=2 ) {
			System.out.println("LINE35:");
			// condition to go ahead and check horizontal, if no piece next to it then no need
			if (logicBoard[row][column+1] == getCurrPlayer()) {
				boolean win = horizontalRightCheck(row, column);
				// check win method
			// condition to go into diagonal check
			} else if (logicBoard[row+1][column+1] == getCurrPlayer()) {
				boolean win = diagonalRightCheck(row);
				// check win method
			} else if (logicBoard[row+1][column] == getCurrPlayer()) {
				boolean win = verticalDownCheck(row, column);
			}
			
		
		}else if(row <= 2 && column == 3) {
			checkHorizontalThree1(row,column);
			checkDiagonalThree1(row, column);
			verticalDownCheck(row,column);

		} else if (row <=2 && column >=4) {
			horizontalRightCheck(row, column);
			diagonalLeftCheck(column);
			checkHorizontalThree1(row, column);
		}
	
		return false;
	}
	
	public static boolean horizontalRightCheck(int row, int column) {
	

		System.out.println("LINE44:");
		int count = 0;

		for(int i = 0; i < 3; i++) {
			count = 0;
			for (int j = 0; j<4; j++) {
				if(logicBoard[i][j] == getCurrPlayer()) {
					count++;
					System.out.println("this is the count so far: " + count);
					if (count ==4) {
						System.out.println("YOU win");
						return true;
					}
				}
			}
		}
		for(int i = 0; i < 3; i++) {
			count = 0;
			for (int j = 1; j<5; j++) {
				if(logicBoard[i][j] == getCurrPlayer()) {
					count++;
					System.out.println("this is the count so far: " + count);
					if (count ==4) {
						System.out.println("YOU win");
						return true;
					}
				}
			}
		}
		
		for(int i = 0; i < 3; i++) {
			count = 0;
			for (int j = 2; j<6; j++) {
				if(logicBoard[i][j] == getCurrPlayer()) {
					count++;
					System.out.println("this is the count so far: " + count);
					if (count ==4) {
						System.out.println("YOU win");
						return true;
					}
				}
			}
		}
		return false;
	}
	
	public static boolean checkHorizontalThree1(int row, int column) {
		
		int count = 0;
		horizontalRightCheck(row, column);
		for(int i = 3 ; i< 7; i++) {
			if(logicBoard[row][i]== getCurrPlayer()) {
				count++;
			}
			if (count == 4) {
				System.out.println("This is a horizontalThree1 Win");
				return true;
			}
		}
		return false;
	}
	
	public static boolean diagonalRightCheck(int row) {
		
		System.out.println("DiagonalRightChecking-> CurrPlayer is: " + getCurrPlayer());
		int count =0;
		int resetRow = row;
		for (int j = 0; j < 4; j++) {
			System.out.println("1");
			System.out.println("this is the RRROWWWW: " +row);
			if (logicBoard[row][j] == getCurrPlayer()) {
				count++;
			}
			if(count ==4) {
				System.out.println("THIS is a diagonal Win");
				
			}
			row++;
		}
		count = 0;
		row = resetRow;
		for (int i = 1 ; i < 5; i++) {
			System.out.println("2");
			System.out.println("this is the RRROWWWW: " +row);
			if (logicBoard[row][i] == getCurrPlayer()) {
				count++;
			}
			if (count == 4) {
				System.out.println("THIS is a diagonal Win");
				//return true;
			}
			row++;
		}
		count = 0;
		row = resetRow;
		for (int i =2; i < 6; i++) {
			System.out.println("3");
			System.out.println("this is the RRROWWWW: " +row);
			if (logicBoard[row][i] == getCurrPlayer()) {
				System.out.println("counting: " + count);
				count++;
			}
			if (count ==4) {
				System.out.println("THIS is a diagonal Win");
				//return true;
			}
			row++;
		}
		return false;
	}
	public static boolean diagonalLeftCheck(int column) {
		
		System.out.println("DiagonalLEFTChecking-> CurrPlayer is: " + getCurrPlayer());
		int count =0;
		int resetColumn = column;
		for (int j = 0; j < 4; j++) {
			System.out.println("1");
			//System.out.println("this is the RRROWWWW: " +row);
			if (logicBoard[j][column] == getCurrPlayer()) {
				count++;
			}
			if(count ==4) {
				System.out.println("THIS is a diagonal Win");
				
			}
			column--;
		}
		count = 0;
		column = resetColumn;
		for (int i = 1 ; i < 5; i++) {
			System.out.println("2");
			//System.out.println("this is the RRROWWWW: " +row);
			if (logicBoard[i][column] == getCurrPlayer()) {
				count++;
			}
			if (count == 4) {
				System.out.println("THIS is a diagonal Win");
				//return true;
			}
			column--;
		}
		count = 0;
		column = resetColumn;
		for (int i =2; i < 6; i++) {
			System.out.println("3");
			//System.out.println("this is the RRROWWWW: " +row);
			if (logicBoard[i][column] == getCurrPlayer()) {
				System.out.println("counting: " + count);
				count++;
			}
			if (count ==4) {
				System.out.println("THIS is a diagonal Win");
				//return true;
			}
			column--;
		}
		return false;
	}
	
	public static boolean checkDiagonalThree1(int row, int column) {
		
		int resetRow = row;
		int count = 0;
		for (int i = 3; i < 7; i++) {
			
			if (logicBoard[row][i] == getCurrPlayer()) {
				count++;
			}
			if (count == 4) {
				System.out.println("THIS is a diagonal Three win");
			}
			row++;
		}
		
		count = 0; 
		row = resetRow;
		for (int i = 3; i < 0; i--) {
			if (logicBoard[row][i] == getCurrPlayer()) {
				count++;
			}
			if (count == 4) {
				System.out.println("THIS is a diagonal Three win");
			}
			row++;
		}
		return false;
	}
	public static boolean verticalDownCheck(int row, int column) {
		
		int count = 0;

		for (int i = 0; i < 4; i++) {
			System.out.println();
			System.out.println("Inside vertica1: ");
			if(logicBoard[row][column] == getCurrPlayer()) {
				count++;
			}
			if (count == 4) {
				System.out.println("THIS is a vertical Down win");
			}
			row++;
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
	
	
	
}
