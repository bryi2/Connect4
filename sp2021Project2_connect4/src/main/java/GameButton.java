import java.util.Stack;

import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;

public class GameButton extends Button {
	
	
	// button status; disabled = clicked
	private boolean disabled;
	private int row;
	private int column;
	// use this to determine player. %2 : 0 = p1, 1 = p2
	private int counter;
	// static so stack info travels across all classes
	public static Stack<GameButton> reverse = new Stack<GameButton>();
	
	// constructor
	GameButton(int row, int column) {
		
		this.counter = 0;
		this.row = row;
		this.column = column;
		this.disabled = false;
	}
//	GameButton() {
//		
//	}
	
	public boolean checkBelow(GameButton[][] arr, int row, int column) {
		
		if (arr[row][column].disabled) { 
			return true;
		}
		return false;
	}
	public void updateValidButtonStates(GameButton b,int row, int column) {
		//System.out.println("before: " + row + column + b.counter);
		b.changeDisable(true);
		b.setRow(row);
		b.setColumn(column);
		b.setCount();
		//System.out.println("after: " + b.row + b.column + b.counter);
	}
	
	public void updatePreviousButtonStates(GameButton b) {
		
		
	}
	// call to add valid node(button) to stack
	public void addNode (GameButton button) {

		System.out.println("right after if:");
		reverse.push(button);
		System.out.println("adding button to stack details: " + button.getRow() + button.getColumn());
		System.out.println("Size of Stack: " + reverse.size());
		
		
	}

	
	public int getRow() {
		return this.row;
	}
	public int getColumn() {
		return this.column;
	}
	public void setRow(int row) {
		this.row = row;
	}
	public void setColumn(int column) {
		this.column = column;
	}
	public boolean checkDisable() {
		return this.disabled;
	}
	
	public void changeDisable(boolean x) {
		this.disabled = x;
	}

	public int checkCounter() {
		return this.counter;
	}
	
	public void setCount() {
		this.counter++;
	}
	
	
}
