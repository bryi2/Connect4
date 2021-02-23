import javafx.scene.control.Button;

public class GameButton extends Button {
	
	// button dead/alive
	public boolean dOa;
	// player turn
	public int player;
	public int row;
	public int column;
	
	GameButton(int row, int column) {
		
		this.row = row;
		this.column = column;
		// need to change but for now testing
		this.dOa = false;
		this.player = 1;
		
	}

}
