import java.util.Stack;

import javafx.scene.control.Button;

public class GameButton extends Button {
	
	private boolean disabled;
	private int row;
	private int column;
	// static so stack info travels across all classes
	public static Stack<GameButton> reverse = new Stack<GameButton>();
	
	// constructor
	GameButton(int row, int column) {
		this.row = row;
		this.column = column;
		this.disabled = false;
	}
	
	public boolean checkBelow(GameButton[][] arr, int row, int column) {
		return arr[row][column].disabled;
	}
	
	public void updateValidButtonStates(GameButton b,int row, int column) {
		b.changeDisable(true);
		b.setRow(row);
		b.setColumn(column);
	}

	// call to add valid node(button) to stack
	public void addNode (GameButton button) {
		
		reverse.push(button);
//		System.out.println("adding button to stack details: " + button.getRow() + button.getColumn());
//		System.out.println("Size of Stack: " + reverse.size());
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
}
