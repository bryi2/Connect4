
import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Stack;
import javafx.animation.PauseTransition;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;

public class JavaFXTemplate extends Application {
	TextField resultText;
	// label so we don't have borders around text
	Label welcomeScreen, howToPlay, currPlayer, moveDetails, result;
	HashMap<String, Scene> sceneMap;
	BorderPane pane;
	GridPane gridpane;
	MenuBar menuBar;
	Menu menuGamePlay, menuThemes, menuOptions;
	//every time user clicks location
	EventHandler<ActionEvent> checkPosition;
	// buttons for grid pane
	GameButton[][] arr;
	// welcome screen + result
	Button playGameButton,playAgain;
	// sub menu
	MenuItem exit, reverse,newGame,howTo,theme1,theme2,defaultTheme;
	// using for pause transition between actions
	PauseTransition pause = new PauseTransition(Duration.seconds(4));
	PauseTransition howToPlayPause = new PauseTransition(Duration.seconds(15));
	
	
	
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
		// button for result screen
		playAgain = new Button("Play Again!");
		// sub Menu
		defaultTheme = new MenuItem("Default Theme");
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
		// Event Handler for each click
		checkPosition = new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				GameButton b = (GameButton)event.getSource();
				// store current player 
				int player = GameLogic.getCurrPlayer();
				int row = b.getRow();
				int column = b.getColumn();
				boolean winner = false;
				String color;
			
				
				if (player == 1) { // player 1
					currPlayer.setText("Current Player: Player 1");
					color = "-fx-background-color:red;";
				} else { // player 2
					currPlayer.setText("Current Player: Player 2");
					color = "-fx-background-color:yellow;";
				}

				// check first if bottom row
				if(GameLogic.checkIfBottomRow(row)) {
					// update button states
					b.updateValidButtonStates(b, row,column);
					GameLogic.addLogicBoard(row, column);
					// change GUI
					b.setStyle(color); // changing color
					b.setDisable(true);
					// check for WIN/TIE
					winner = GameLogic.isWinner(GameLogic.getCurrPlayer(), GameLogic.getLogic());
					// add to stack
					b.addNode(b);
					// using labels there is no clear function so use empty string set
					moveDetails.setText("");
					moveDetails.setText("Player " + prevPlayer(player) + " ["+row+","+column + "] is a valid move.");
					// since not bottom row , we now check if button below it disabled, if yes then were good, if not invalid move
				} else if (b.checkBelow(arr,(row+1), column)) {
					//System.out.println("Not bottom row, but button below is disabled, valid move");
					b.updateValidButtonStates(b, row, column);
					GameLogic.addLogicBoard(row, column);
					b.setStyle(color); // changing color
					b.setDisable(true);
					// check for win 
					winner = GameLogic.isWinner(GameLogic.getCurrPlayer(), GameLogic.getLogic());
					// add stack
					b.addNode(b);
					//System.out.println("Counter: " + player); 
					moveDetails.setText("");
					moveDetails.setText("Player " + prevPlayer(player)+ " ["+row+","+column + "] is a valid move.");
				} else {
					// invalid move
					moveDetails.setText("");
					moveDetails.setText("Player " + prevPlayer(player) + " ["+row+","+column + "] is NOT a valid move. Pick Again!");
					
				}
				
				// we have to invoke the scene instead of just printing it out
				if (winner || GameButton.reverse.size() > 41){
					// should pause the scene before showing result
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
						moveDetails.setText("");
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
		
		// lambda expression for when clicking reverse during game play
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
			GameLogic.removeLogicBoard(x.getRow(), x.getColumn());
			x.changeDisable(false);
			x.setDisable(false);
			
		} else {
			System.out.println("Sorry no more take backs! Choose a Spot!");
		}
		});
		
		// Themes 1 and 2 we can use setStyle or setBackground, BackgroundFill just fills the whole screen with color
		theme1.setOnAction(e -> {
			pane.setBackground(new Background(new BackgroundFill(Color.CORNSILK, CornerRadii.EMPTY, Insets.EMPTY)));
			});
		
		theme2.setOnAction(e -> {
			pane.setBackground(new Background(new BackgroundFill(Color.CADETBLUE, CornerRadii.EMPTY, Insets.EMPTY)));
		});
		defaultTheme.setOnAction(e -> {
			pane.setBackground(new Background(new BackgroundFill(Color.DARKSEAGREEN, CornerRadii.EMPTY, Insets.EMPTY)));
		});
		
		
		// newGame for options menu bar
		newGame.setOnAction(e -> {
			
			moveDetails.setText("");
			gridpane.getChildren().clear();
			newGrid(gridpane);
			GameLogic.clearLogicBoard();
			GameButton.reverse.clear();
			primaryStage.setScene(sceneMap.get("play"));
					
		});
		// how to play menu bar option
		howTo.setOnAction(e -> {
			// allows the text to go next line and not get chopped off
			howToPlay.setWrapText(true);
			howToPlay.setText("Welcome to Connect 4: Just like the game name the objective is to get 4 in a row. The rows can be done vertically, horizontally or diagonlly. "
					+ "Each turn, the player will get the chance to drop and make the row of his checkers. If you are the first player to get four of your checkers in a row, you win."
					+ "Also, during your turn you have the option to reverse a move located in the Gameplay menu bar. However, choosing to reverse a move will behave as a turn and the player"
					+ "prior to the most recent move will be chose to make a move.");
			// set a pause for 15 seconds and then remove game play instructions
			howToPlayPause.setOnFinished(s -> {
				// set to blank screen
				howToPlay.setText("");
			});
				howToPlayPause.play();
		});
		
		//create handler for the exit option in Options
		exit.setOnAction(e -> {
			System.exit(0);
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
	
	// function for previous player# for move details
	public int prevPlayer(int currPlayer) {
		if (currPlayer ==1) {
			return 2;
		}
		return 1;
		
	}
	public void setMenuOptions () {
		
		// Menu bar options
		menuGamePlay = new Menu("Game Play");
		menuThemes = new Menu("Themes");
		menuOptions = new Menu("Options");
		// add pull down options according to parent menu
		menuOptions.getItems().addAll(howTo,newGame,exit);
		menuThemes.getItems().addAll(defaultTheme,theme1,theme2);
		menuGamePlay.getItems().addAll(reverse);
		// add parent menu
		menuBar.getMenus().addAll(menuGamePlay, menuThemes, menuOptions);
	}
	
	
	// 3 scenes : welcome, main, win/tie
	public Scene createWelcomeScene() {
		BorderPane welcomePane = new BorderPane();
		// sets how much space you want around all sides of screen
		welcomePane.setPadding(new Insets(50));
		
		// label for intro screen
		welcomeScreen = new Label("Welcome to Connect 4");
		welcomeScreen.setFont(new Font("Arial", 20));
	
		// create and insert background image
		Image image = new Image ("connect4.jpg");
		BackgroundSize bSize = new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, true, false);
		Background background = new Background(new BackgroundImage(image, BackgroundRepeat.NO_REPEAT,BackgroundRepeat.NO_REPEAT,BackgroundPosition.CENTER,bSize));
		// vertical placement order
		VBox paneCenter = new VBox(10, welcomeScreen);
		// pane placement
		welcomePane.setBackground(background);
		//pane.setCenter(paneCenter);
		welcomePane.setBottom(playGameButton);
		welcomePane.setTop(paneCenter);
	
		return new Scene(welcomePane, 1000,550);
	}
	
	public Scene mainGameScene() {
		
		// add menu bar and 3 menu options
		menuBar = new MenuBar();
		// setting menu pull down options
		setMenuOptions();
		
		// initialize grid pane with buttons
		gridpane = new GridPane();
		newGrid(gridpane);
		
		//gridpane.setAlignment(center);
	    pane = new BorderPane();
		
	    pane.setBackground(new Background(new BackgroundFill(Color.DARKSEAGREEN, CornerRadii.EMPTY, Insets.EMPTY)));
		// menuBar to top of border pane
		pane.setTop(menuBar);
	
		
		// create label for how to play text
		howToPlay = new Label("");
		howToPlay.setFont(new Font("Arial",18));
		
		currPlayer = new Label("Current Player: Player 1");
		currPlayer.setFont(new Font("Arial", 18));
		
		moveDetails = new Label("Move Details will post here.");
		moveDetails.setFont(new Font("Arial", 18));
		
		VBox borderTop = new VBox(menuBar, howToPlay, currPlayer,moveDetails);
		// centering
		gridpane.setAlignment(Pos.CENTER);
		
		VBox paneCenter = new VBox(currPlayer, moveDetails,gridpane);
		// center the grid pane 
		paneCenter.setAlignment(Pos.CENTER);
		pane.setCenter(paneCenter);
		pane.setTop(borderTop);
		
		return new Scene(pane, 800,800);
	}
	
	public Scene resultScene() {
		
		BorderPane pane = new BorderPane();
		pane.setPadding(new Insets(50));
		
		Image image = new Image ("youwon.jpg");
		BackgroundSize bSize = new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, true, false);
		Background bg = new Background(new BackgroundImage(image, BackgroundRepeat.NO_REPEAT,BackgroundRepeat.NO_REPEAT,BackgroundPosition.CENTER,bSize));
		pane.setBackground(bg);
		
		
		// create label for win,
		result = new Label("YOU WIN PLAYER " + GameLogic.getCurrPlayer());
		result.setFont(new Font("Arial",20));
	
		pane.setTop(result);
		pane.setBottom(playAgain);
	
		
		return new Scene(pane, 800,540);
	}
	

	

}