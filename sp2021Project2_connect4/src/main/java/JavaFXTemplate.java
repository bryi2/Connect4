import java.util.HashMap;
import java.util.Stack;

import javafx.animation.PauseTransition;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;

public class JavaFXTemplate extends Application {
	
	TextField welcomeText, currPlayer, moveDetails;
	HashMap<String, Scene> sceneMap;
	
	GridPane gridpane;
	//every time user clicks location
	EventHandler<ActionEvent> checkPosition;
	GameButton[][] arr;

	Button playGameButton, reverseButton, testReturn;
	// using for pause transition between actions
	PauseTransition pause = new PauseTransition(Duration.seconds(4));
	
	public static void main(String[] args) {
		
		launch(args);
	}

	//feel free to remove the starter code from this method
	@Override
	public void start(Stage primaryStage) throws Exception {
		// initialize array to store in grid
		arr = new GameButton [6][7];
		// button to access next GUI (main game)
		playGameButton = new Button("Play Connect Four!");
		reverseButton = new Button("Take it Back!");
		
	
		// initialize the HashMap
		sceneMap = new HashMap<String, Scene>();
		// border title
		primaryStage.setTitle("Welcome to Git Project 2");
		// Welcome Message, we can make better later
		welcomeText = new TextField ("Welcome to Connect 4!");
		
		
		
		
		// Event Handler for each click
		checkPosition = new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				GameButton b = (GameButton)event.getSource();
				int player = b.checkCounter();
				boolean winner = false;
				String color; 
				if (player%2 == 0) { // player 1
					currPlayer.setText("Player 1");
					color = "-fx-background-color:red;";
				} else { // player 2
					currPlayer.setText("Player 2");
					color = "-fx-background-color:yellow;";
				}
				int row = b.getRow();
				int column = b.getColumn();
				// 1st condition
				// check first if bottom row
				if(GameLogic.checkIfBottomRow(row)) {
					// here we validate  move right away and update everything
					
					
					
					
					System.out.println("Yes, its the bottom row");
					// update button states
					b.updateValidButtonStates(b, row,column);
					GameLogic.addLogicBoard(row, column);
					
					// change GUI
					b.setStyle(color); // changing color
					b.setDisable(true);
					
					winner = GameLogic.isWinner(GameLogic.getCurrPlayer(), GameLogic.getLogic());
					// check for WIN/TIE
					// if win (invoke scene 3)
					// not a win, add to stack
					b.addNode(b);
					
					System.out.println("Counter: " + player);
					moveDetails.clear();
					moveDetails.appendText("Player ["+row+","+column + "] is a valid move.");
					if (winner){
						if (player == 0){
							System.out.println("Player YELLOW  won");
						}else{
							System.out.println("Player RED won");
						}
					}
				// since not bottom row , we now check if button below it disabled, if yes then were good, if not invalid move
				} else if (b.checkBelow(arr,(row+1), column)) {
					System.out.println("Not bottom row, but button below is disabled, valid move");
					
					b.updateValidButtonStates(b, row, column);
					GameLogic.addLogicBoard(row, column);
					winner = GameLogic.isWinner(GameLogic.getCurrPlayer(), GameLogic.getLogic());
					b.setStyle(color); // changing color
					b.setDisable(true);
					b.addNode(b);
					if (winner){
						if (player == 0){
							System.out.println("Player YELLOW  won");
						}else{
							System.out.println("Player RED won");
						}
					}
					System.out.println("Counter: " + player);
					moveDetails.clear();
					moveDetails.appendText("Player ["+row+","+column + "] is a valid move.");
				} else {
					// invalid move
					moveDetails.clear();
					moveDetails.appendText("Player ["+row+","+column + "] is NOT a valid move. Pick Again!");
					
				}
			}
		};
		
		// lambda expression for when clicking reverse during gameplay
		reverseButton.setOnAction(e-> { if (!GameButton.reverse.empty()) {
			GameButton x = GameButton.reverse.pop();
			System.out.println("heres the previous row and col: " + x.getRow() + x.getColumn());
			// update the GUI to the color
			x.setStyle ("-fx-base: # f4f162");
			int player = x.checkCounter();
			String color; 
			if (player%2 == 0) { // player 1
				currPlayer.setText("Player 1");
				color = "-fx-background-color:red;";
			} else { // player 2
				currPlayer.setText("Player 2");
				color = "-fx-background-color:yellow;";
			}
			x.setCountMinus();
			// update the previous buttons info
			int row = x.getRow();
			int column = x.getColumn();
			x.changeDisable(false);
			x.setDisable(false);
			
		} else {
			System.out.println("Sorry no more take backs! Choose a Spot!");
		}
		});
		
		
		
		
		// create even handler for PlayGame Button from welcome screen and to play again after win/tie
		playGameButton.setOnAction(e -> primaryStage.setScene(sceneMap.get("play")));
		
		// placing scenes inside HashMap, 1 more needed the win/tie 
		sceneMap.put("welcome", createWelcomeScene());
		sceneMap.put("play",  mainGameScene());
		//sceneMap.put("result", resultScene());
		// call on Hash map to invoke scenes
		// but welcome is always invoked on startup
		primaryStage.setScene(sceneMap.get("welcome"));
		primaryStage.show();
	}
	
	
//	// getter for this grid pane
//	public GameButton grabButton(int row, int column) {
//
//		GameButton b = arr[row][column];
//		return b;
//		//type cast node to GameButton. grabbing 1d index from 2d: (row*row length) + column
//	GameButton b = (GameButton)gridpane.getChildren().get(row*7+column);
//	return b;
//	}
//	
	// function to create and add grid 
	public void newGrid( GridPane grid) {
		
		for (int i = 0 ; i < 6; i++) {
			for (int j = 0 ; j< 7; j++) {
				
				GameButton b1 = new GameButton(i,j);
				// set default button color
				b1.setMinSize(40,40);
				//b1.setStyle("-fx-background-color:orange;");
				// Even Handler when clicked = checkPosition
				b1.setOnAction(checkPosition);
				arr[i][j] = b1;
				grid.add(arr[i][j], j, i);
			}
		}
	}
	
	// 3 scenes : welcome, main, win/tie
	public Scene createWelcomeScene() {
		BorderPane pane = new BorderPane();
		// sets how much space you want around all sides of screen
		pane.setPadding(new Insets(200));
		// vertical placement order
		VBox paneCenter = new VBox(10, welcomeText );
		// pane placement
		pane.setCenter(paneCenter);
		pane.setBottom(playGameButton);
		// pane color
		pane.setStyle("-fx-background-colo: blue;");
		return new Scene(pane, 700,800);
	}
	
	public Scene mainGameScene() {
		gridpane = new GridPane();
		newGrid(gridpane);

		BorderPane pane = new BorderPane();
		currPlayer = new TextField();
		currPlayer.setPrefWidth(80);
		currPlayer.setEditable(false);
		currPlayer.setText("Player 1"); // begin with player 1
		
		moveDetails = new TextField();
		moveDetails.setPrefWidth(100);
		moveDetails.setEditable(false);
		
		pane.setPadding(new Insets(300));
		VBox paneCenter = new VBox(reverseButton,currPlayer, moveDetails,gridpane);
		
		pane.setCenter(paneCenter);
		
		return new Scene(pane, 800,800);
	}
	
//	public Scene resultScene() {
//		
//		BorderPane pane = new BorderPane();
//		pane.setPadding(new Insets(300));
//		
//		pane.setCenter(pane);
//		
//		// this has nothing just did so i can compile for now
//		return new Scene(pane, 800,800);
//	}

	

}
