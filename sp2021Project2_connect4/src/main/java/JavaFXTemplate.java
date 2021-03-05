
import java.util.HashMap;
import java.util.Stack;
import javafx.animation.PauseTransition;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;

public class JavaFXTemplate extends Application {
	
	TextField  currPlayer, moveDetails, resultText;
	
	// label so we dont have borders around text
	Label welcomeScreen;
	HashMap<String, Scene> sceneMap;
	
	GridPane gridpane;
	MenuBar menuBar;
	Menu menuGamePlay, menuThemes, menuOptions;
	//every time user clicks location
	EventHandler<ActionEvent> checkPosition;
	GameButton[][] arr;

	Button playGameButton,playAgain;
	MenuItem exit, reverse,newGame,howTo,theme1,theme2;
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
		
	
		playAgain = new Button("Play Again!");
		theme1 = new MenuItem("Theme 1");
		theme2 = new MenuItem("Theme 2");
		howTo = new MenuItem("How To Play?");
		exit = new MenuItem("Exit");
		newGame = new MenuItem("New Game");
		reverse = new MenuItem("Reverse Move");
		
		
		// initialize the HashMap
		sceneMap = new HashMap<String, Scene>();
		// border title
		primaryStage.setTitle("Welcome to Git Project 2");
		// Welcome Message, we can make better later
		//welcomeText = new TextField ("Welcome to Connect 4!");
		
		
		// Event Handler for each click
		checkPosition = new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				GameButton b = (GameButton)event.getSource();
				int player = GameLogic.getCurrPlayer();
				boolean winner = false;
				String color; 
				if (player == 1) { // player 1
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
					
					// check for WIN/TIE
					winner = GameLogic.isWinner(GameLogic.getCurrPlayer(), GameLogic.getLogic());
					// if win (invoke scene 3)
					
					// not a win, add to stack
					b.addNode(b);
					
					System.out.println("Counter: " + player);
					moveDetails.clear();
					moveDetails.appendText("Player ["+row+","+column + "] is a valid move.");

				// since not bottom row , we now check if button below it disabled, if yes then were good, if not invalid move
				} else if (b.checkBelow(arr,(row+1), column)) {
					System.out.println("Not bottom row, but button below is disabled, valid move");
					b.updateValidButtonStates(b, row, column);
					GameLogic.addLogicBoard(row, column);
					b.setStyle(color); // changing color
					b.setDisable(true);
					// check for win 
					winner = GameLogic.isWinner(GameLogic.getCurrPlayer(), GameLogic.getLogic());
					
					b.addNode(b);
					System.out.println("Counter: " + player); 
					moveDetails.clear();
					moveDetails.appendText("Player ["+row+","+column + "] is a valid move.");
				} else {
					// invalid move
					moveDetails.clear();
					moveDetails.appendText("Player ["+row+","+column + "] is NOT a valid move. Pick Again!");
					
				}
				
				// we have to envoke the scene instead of just printing it out
				if (winner || GameButton.reverse.size() > 41){
					// prob should pause the scene before showing result
					int[][] winSet = GameLogic.getWinningSet();;
					boolean isWin = winner;
					if (winner) {
						GameButton but1 = arr[winSet[0][0]][winSet[0][1]];
						GameButton but2 = arr[winSet[1][0]][winSet[1][1]];
						GameButton but3 = arr[winSet[2][0]][winSet[2][1]];
						GameButton but4 = arr[winSet[3][0]][winSet[3][1]];
						but1.setText("W");
						but2.setText("W");
						but3.setText("W");
						but4.setText("W");
					}
					pause.setOnFinished( e -> { 
						primaryStage.setScene(sceneMap.get("result"));
						for (int i = 0; i < winSet.length; i++)
				        {
				            for (int j = 0; (winSet[i] != null && j < winSet[i].length); j++)
				                System.out.print(winSet[i][j] + " ");
				        }
						moveDetails.clear();
						gridpane.getChildren().clear();
						newGrid(gridpane);
						GameButton.reverse.clear();
						GameLogic.clearLogicBoard();
						if (player == 1){
							resultText.setText("Player RED  won");
						} else {
							resultText.setText("Player YELLOW won");
						}
						if (!isWin) {
							resultText.setText("Game was a tie!");
						}
					});
					pause.play();
					
					
					
				}
			}
		};
		
		// lambda expression for when clicking reverse during gameplay
		reverse.setOnAction(e-> { if (!GameButton.reverse.empty()) {
			GameButton x = GameButton.reverse.pop();
			
			System.out.println("heres the previous row and col: " + x.getRow() + x.getColumn());
			// update the GUI to the color
			// we get an error here on java fx , TA said to just use background color we can change later
			x.setStyle ("-fx-base: # f4f162");
			int player = GameLogic.getCurrPlayer();
			String color; 
			if (player == 1) { // player 1
				currPlayer.setText("Player 1");
				color = "-fx-background-color:red;";
			} else { // player 2
				currPlayer.setText("Player 2");
				color = "-fx-background-color:yellow;";
			}
			//GameLogic.changeCurrPlayer();
			// update the previous buttons info
			int row = x.getRow();
			int column = x.getColumn();
			GameLogic.removeLogicBoard(x.getRow(), x.getColumn());
			x.changeDisable(false);
			x.setDisable(false);
			
		} else {
			System.out.println("Sorry no more take backs! Choose a Spot!");
		}
		});
		
		// newGame for options menubar
		newGame.setOnAction(new EventHandler<ActionEvent>() {
					public void handle(ActionEvent t) {
						moveDetails.clear();
						gridpane.getChildren().clear();
						newGrid(gridpane);
						GameLogic.clearLogicBoard();
						GameButton.reverse.clear();
						primaryStage.setScene(sceneMap.get("play"));
					}
		});
		
		//create handler for the exit option in Options
		exit.setOnAction(new EventHandler<ActionEvent>() {
				public void handle (ActionEvent t) {
					System.exit(0);
				}
		});

		
		// create even handler for PlayGame Button from welcome screen and to play again after win/tie
		playGameButton.setOnAction(e -> primaryStage.setScene(sceneMap.get("play")));
		
		
		// create an event handler for playAgain button after the result scene
		playAgain.setOnAction(e -> primaryStage.setScene(sceneMap.get("play")));
		
		
		
		// placing scenes inside HashMap, 1 more needed the win/tie 
		sceneMap.put("welcome", createWelcomeScene());
		sceneMap.put("play",  mainGameScene());
		sceneMap.put("result", resultScene());
		// call on Hash map to invoke scenes
		// but welcome is always invoked on startup
		primaryStage.setScene(sceneMap.get("welcome"));
		primaryStage.show();
	}
	

	// function to create and add grid 
	public void newGrid( GridPane grid) {
		for (int i = 0 ; i < 6; i++) {
			for (int j = 0 ; j< 7; j++) {
				GameButton b1 = new GameButton(i,j);
				// set default button color
				b1.setMinSize(50,45);
				b1.setOnAction(checkPosition);
				arr[i][j] = b1;
				grid.add(arr[i][j], j, i);
			}
		}
	}
	
	public void setMenuOptions () {
		
		// Menu bar options
		menuGamePlay = new Menu("Game Play");
		menuThemes = new Menu("Themes");
		menuOptions = new Menu("Options");
	
	
		
	}
	
	
	// 3 scenes : welcome, main, win/tie
	public Scene createWelcomeScene() {
		BorderPane pane = new BorderPane();
		// sets how much space you want around all sides of screen
		pane.setPadding(new Insets(100));
		
		welcomeScreen = new Label("Welcome to Connect 4");
		welcomeScreen.setFont(new Font("Arial", 20));
	
		
		Image image = new Image ("connect4.jpg");
		BackgroundSize bSize = new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, true, false);
		Background background = new Background(new BackgroundImage(image, BackgroundRepeat.NO_REPEAT,BackgroundRepeat.NO_REPEAT,BackgroundPosition.CENTER,bSize));
		// vertical placement order
		VBox paneCenter = new VBox(10, welcomeScreen);
		// pane placement
		pane.setBackground(background);
		//pane.setCenter(paneCenter);
		pane.setBottom(playGameButton);
		pane.setTop(paneCenter);
		// pane color
		pane.setStyle("-fx-background-colo: blue;");
		return new Scene(pane, 800,800);
	}
	
	public Scene mainGameScene() {
		
		// add menubar and 3 menu options
		menuBar = new MenuBar();
		// function call, probably dont need i'll fix all this later just testing
		setMenuOptions();

		
		// add pulldown option according to parent menu
		menuOptions.getItems().addAll(howTo,newGame,exit);
		menuThemes.getItems().addAll(theme1,theme2);
		menuGamePlay.getItems().addAll(reverse);
		

		
		menuBar.getMenus().addAll(menuGamePlay, menuThemes, menuOptions);
		

		
		gridpane = new GridPane();
		newGrid(gridpane);
		
		//gridpane.setAlignment(center);
		BorderPane pane = new BorderPane();
		// menuBar to top of borderpane
		pane.setTop(menuBar);
		
		// for now its textfield, but were going to use labels so the text just appears in the background naturally instead of text with borders
		currPlayer = new TextField();
		currPlayer.setPrefWidth(80);
		currPlayer.setEditable(false);
		currPlayer.setText("Player 1"); // begin with player 1
		
		moveDetails = new TextField();
		moveDetails.setPrefWidth(100);
		moveDetails.setEditable(false);
		
		
		gridpane.setAlignment(Pos.CENTER);
		
		
		
		VBox paneCenter = new VBox(currPlayer, moveDetails,gridpane);
		// center the gridpane 
		paneCenter.setAlignment(Pos.CENTER);
		
		pane.setCenter(paneCenter);
		
		return new Scene(pane, 800,800);
	}
	
	public Scene resultScene() {
		
		BorderPane pane = new BorderPane();
		pane.setPadding(new Insets(300));
		
		resultText = new TextField();
		resultText.setPrefWidth(80);
		resultText.setEditable(false);
		
		VBox paneCenter = new VBox(playAgain, resultText);
		pane.setCenter(paneCenter);
		
		// this has nothing just did so i can compile for now
		return new Scene(pane, 800,800);
	}

	

}