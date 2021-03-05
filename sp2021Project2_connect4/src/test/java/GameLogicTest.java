import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;


class GameLogicTest {
	

	public static int currentPlayer = 0;
	public static int [][] logicBoard = new int [6][7];
	private static int it,it2;
	
	@BeforeAll
	static void setup() {
		
		// parameterized test
		it = 0;
		it2 = 0;
	}
	
	@BeforeEach
	void init() {
		// reset count per test
		GameLogic.currentPlayer = 0;
		GameLogic.clearLogicBoard();
	
	}

	
	@Test
	void testCurrentPlayer1() {
		// test player 1
		GameLogic.currentPlayer++;
		System.out.println("heres the count: " + GameLogic.getCountPlayer());
		assertEquals(1, GameLogic.getCurrPlayer(), "Current Player value is wrong");
	}
	
	@Test
	void testCurrentPlayer2() {
		
		// test player 2, we use modulus function so incrementing again should return 2
		GameLogic.currentPlayer++;
		GameLogic.currentPlayer++;
		System.out.println("heres the count: " + GameLogic.getCountPlayer());
		assertEquals(2, GameLogic.getCurrPlayer(), "Current Player value is wrong");
	}
	
	@Test
	void testAddLogicBoard1() {
		
		// first step in add logic function increments curr Player, so first check it correct
		GameLogic.addLogicBoard(3,3);
		assertEquals(1,GameLogic.getCurrPlayer(), "Incrementing curr player not working in add logic method");
	}
	
	@Test
	void testAddLogicBoard2() {
		
		// check actual coordinate value
		// by default we increment before test and increment in method, so 2%2 we expect player 2
		GameLogic.addLogicBoard(3, 3);
		assertEquals(1,GameLogic.logicBoard[3][3], "Value at coordinate is wrong, add logic method");
	}
	
	@Test 
	void testAddLogicBoard3() {
		
		// double check location
		for (int i = 0 ; i < 1; i++) {
			for (int j = 0; j < 7; j++) {
				GameLogic.addLogicBoard(i, j);
			}
		}
		
		assertEquals(6, GameLogic.logicBoard.length, "The row size does not match in Logic Board");
		
	}
	
	@Test 
	void testAddLogicBoard4() {
		
		// double check location
		for (int i = 0 ; i < 1; i++) {
			for (int j = 0; j < 7; j++) {
				GameLogic.addLogicBoard(i, j);
			}
		}
		// check column size
		assertEquals(7, GameLogic.logicBoard[1].length, "The column size does not match in Logic Board");
	}
	
	@ParameterizedTest
	@ValueSource ( ints = { -1,-1,-1,-1,-1,-1,-1})
//	@Test
	void testClearBoard(int val) {
		// intialize container to store clear values
		int arr[] = new int[7];
		// first populate with player #'s
		
		for (int i = 0 ; i < 1; i++) {
			for (int j = 0; j<7; j++) {
				GameLogic.addLogicBoard(i,j);
			}
		}
		// our clear Board resets all values to -1
		GameLogic.clearLogicBoard();
		for (int i = 0 ; i < 1; i++) {
			for (int j = 0 ; j< 7; j++) {
				// populate what should be all -1
				arr[j] = GameLogic.logicBoard[i][j];
			}
		}
		
		assertEquals(val,arr[it],"Value is not -1 when clearing board");
		it++;
	}
	
	@Test
	void checkIfBottomRow1() {
		
		// add to correct bottom row, row parameter if 5(last row) always true
		
		assertEquals(true,GameLogic.checkIfBottomRow(5), "Bottom row method is not working");
	}
	
	@Test
	void checkIfBottomRow2() {
		
		// add incorrect row value anything < 5
		
		assertEquals(false, GameLogic.checkIfBottomRow(4), "Bottom row method is not working");
	}
	
	

	@Test
	void checkIfHorizontalWinTrue1() {  
		
		
			// row 0 column 0-3 for correct horizontal
			for (int j = 0; j < 4; j++) {
				GameLogic.addLogicBoard(0, j);
				// decrement allows me to keep the same player# for testing purpose
				GameLogic.currentPlayer--;
			}
		
		
		assertEquals(true, GameLogic.isWinner(1, GameLogic.getLogic()), "is winnder is not working");
	}
	
	@Test
	void checkIfHorizontalWinTrue2() {  
		
		
			// row 3 column 3-7 for correct horizontal
			for (int j = 3; j < 7; j++) {
				GameLogic.addLogicBoard(4, j);
				// decrement allows me to keep the same player# for testing purpose
				GameLogic.currentPlayer--;
			}
		
		
		assertEquals(true, GameLogic.isWinner(1, GameLogic.getLogic()), "is winnder is not working");
	}

	@Test
	void checkIfHorizontalWinFalse1() {  
		
			
			// 4 moves different players each no win
			for (int j = 0; j < 4; j++) {
				GameLogic.addLogicBoard(4, j);
			}
		
		assertEquals(false, GameLogic.isWinner(1, GameLogic.getLogic()), "is winnder is not working");
	}
	
	@Test
	void checkIfHorizontalWinFalse2() {
		// 3 in a row for player#1
		for (int i = 0; i < 3; i++) {
			GameLogic.addLogicBoard(2, i);
			GameLogic.currentPlayer--;
		}
		// increment to change 4th piece to player#2
		GameLogic.currentPlayer++;
		GameLogic.addLogicBoard(2, 3);
		
		assertEquals(false, GameLogic.isWinner(1,GameLogic.getLogic()), "Is winner horizontal is marking an incorrect 4 as a win");
	}
	
	@Test
	void checkIfVerticalWinTrue1() {
		
		for (int i = 2; i < 6; i++) {
			GameLogic.addLogicBoard(i, 3);
			GameLogic.currentPlayer--;
		}
		
		assertEquals(true, GameLogic.isWinner(1, GameLogic.getLogic()),"Is winner Vertical Win is not correct");
	}
	
	@Test
	void checkIfVerticalWinTrue2() {
		
		for (int i = 1; i < 5; i++) {
			GameLogic.addLogicBoard(i, 6);
			GameLogic.currentPlayer--;
		}
		
		assertEquals(true, GameLogic.isWinner(1, GameLogic.getLogic()),"Is winner Vertical Win is not correct");
	}
	
	@Test
	void checkIfUpwardDiagonalWin1() {
		
		int column = 2;
		for (int i = 4; i >0; i--) {
			GameLogic.addLogicBoard(i, column);
			GameLogic.currentPlayer--;
			column++;
		}
		assertEquals(true, GameLogic.isWinner(1, GameLogic.getLogic()),"Upper Diagonal Win is not Correct");
	}
	@Test
	void checkIfUpwardDiagonalWin2() {
		
		int column = 3;
		for (int i = 5; i >1; i--) {
			GameLogic.addLogicBoard(i, column);
			GameLogic.currentPlayer--;
			column++;
		}
		assertEquals(true, GameLogic.isWinner(1, GameLogic.getLogic()),"Upper Diagonal Win is not Correct");
	}
	
	@Test
	void checkIfDownwardDiagonalWin1() {
		
		int column = 2;
		for (int i = 2; i < 6; i++) {
			GameLogic.addLogicBoard(i, column);
			GameLogic.currentPlayer--;
			column++;
		}
		assertEquals(true, GameLogic.isWinner(1, GameLogic.getLogic()),"Downward Diagonal Win is not Correct");
	}
	
	@Test
	void checkIfDownwardDiagonalWin2() {
		
		int column = 3;
		for (int i = 0; i < 4; i++) {
			GameLogic.addLogicBoard(i, column);
			GameLogic.currentPlayer--;
			column++;
		}
		assertEquals(true, GameLogic.isWinner(1, GameLogic.getLogic()),"Downward Diagonal Win is not Correct");
	}
	
	@Test
	void checkRemoveLogicBoardCorrectPlayer() {
		
		// add piece to board which also increments count to 1, so 1%2 = player 1
		GameLogic.addLogicBoard(4, 4);
		// remove function decrements count so 1 -1 = 0%2 = player 2
		GameLogic.removeLogicBoard(4, 4);
		assertEquals(2, GameLogic.getCurrPlayer(), "Incorrect Player number after removing piece from logic board");
	}
	
	@ParameterizedTest
	@ValueSource (ints = {-1, -1, -1,-1,-1})
	void checkRemoveLogicBoardPieceValue(int val) {
		int[] arr = new int[5];		// set pieces to board populating with player# 1 and 2
		
		for (int i = 0 ; i <5; i++) {
			GameLogic.addLogicBoard(i,1);
		}
		for (int i =0; i < 5; i++) {
			GameLogic.removeLogicBoard(i, 1);
			arr[i] = GameLogic.logicBoard[i][1];
		}
		
		assertEquals(val, arr[it2], "Value after remove logic board method does not equal -1");
		it2++;
	}
}
	

