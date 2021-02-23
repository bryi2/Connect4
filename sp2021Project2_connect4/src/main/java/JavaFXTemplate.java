
import java.util.HashMap;

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
	
	TextField welcomeText;
	HashMap<String, Scene> sceneMap;
	Button playGameButton;
	GridPane gridpane;
	GameButton[][] arr;
	// using for pause transition between actions
	PauseTransition pause = new PauseTransition(Duration.seconds(4));
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		launch(args);
	}

	//feel free to remove the starter code from this method
	@Override
	public void start(Stage primaryStage) throws Exception {
		// initialize array to store in grid
		arr = new GameButton [6][7];
		// button to access next GUI (main game)
		playGameButton = new Button("Play Connect Four!");
		
	
		// initialize the HashMap
		sceneMap = new HashMap<String, Scene>();
		// border title
		primaryStage.setTitle("Welcome to Git Project 2");
		// Welcome Message, we can make better later
		welcomeText = new TextField ("Welcome to Connect 4!");
		
		
		
		
		
		
		
		
		// create even handler for PlayGame Button from welcome screen and to play again after win/tie
		playGameButton.setOnAction(e -> primaryStage.setScene(sceneMap.get("play")));
		
		// placing scenes inside HashMap, 1 more needed the win/tie 
		sceneMap.put("welcome", createWelcomeScene());
		sceneMap.put("play",  mainGameScene());
		// call on Hash map to invoke scenes
		primaryStage.setScene(sceneMap.get("welcome"));
		primaryStage.show();
	}
	
	// function to create and add grid 
	public void addGrid( GridPane grid) {
		
		for (int i = 0 ; i < 6; i++) {
			for (int j = 0 ; j< 7; j++) {
				
				GameButton b1 = new GameButton(i,j);
				
				b1.setMinSize(40,40);
				b1.setStyle("-fx-background-coor:blue;");
				// all buttons when pressed will have the same actions, setting variables
				//b1.setOnAction(myHAndler);
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
;
		gridpane = new GridPane();
		addGrid(gridpane);

		BorderPane pane = new BorderPane();
		pane.setPadding(new Insets(300));
		VBox paneCenter = new VBox(gridpane);
		
		pane.setCenter(paneCenter);
		
	
		
		return new Scene(pane, 800,800);
	}

	

}
