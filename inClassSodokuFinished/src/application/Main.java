package application;
	 
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;


public class Main extends Application {
	
	TextField[][] textFields = new TextField[9][9];
	
	/* (non-Javadoc)
	 * @see javafx.application.Application#start(javafx.stage.Stage)
	 */
	@Override
	public void start(Stage primaryStage) {
		
		// Make the structure for the UI
		GridPane grid = new GridPane();
		grid.setAlignment(Pos.CENTER);
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(25,25,25,25));
		
		// Insert the TextFields that will be editted
		for(int r = 0 ; r < 9 ; r++) {
			for(int c = 0 ; c < 9 ; c++) {
				TextField t = new TextField();
				t.setAlignment(Pos.CENTER);
				textFields[c][r] = t;
				grid.add(t, c, r);
			}
		}
		
		// Add a clear button
		Button btn = new Button("Clear");
		HBox hbBtn = new HBox(10);
		hbBtn.getChildren().add(btn);
		hbBtn.setAlignment(Pos.BOTTOM_LEFT);
		grid.add(hbBtn, 0, 9,3,1);
		
		// Event driven architecture: What to do when the button is pressed.
		btn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
					
				for(int r = 0 ; r < 9 ; r++) {
					for(int c = 0 ; c < 9 ; c++) {
						textFields[c][r].setText("");
						textFields[c][r].setStyle("-fx-background-color:rgb(255,255,255)");
					}
				}
			}
		});
		
		// Add a solve button
		btn = new Button("Solve");
		hbBtn = new HBox(10);
		hbBtn.getChildren().add(btn);
		hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
		grid.add(hbBtn, 6, 9,3,1);
		
		// Event driven architecture: What to do when the button is pressed.
		btn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				//Make a new solver
				Solver s = new Solver();
				
				// The original partial solution
				String[][] board = new String[9][9];
					
				for(int r = 0 ; r < 9 ; r++) {
					for(int c = 0 ; c < 9 ; c++) {
						board[c][r] = textFields[c][r].getText().trim();
					}
				}
				// Recurse for a solution
				String[][] solution = s.solve(board);
				// Update the UI bbased on the results
				for(int r = 0 ; r < 9 ; r++) {
					for(int c = 0 ; c < 9 ; c++) {
						if(textFields[c][r].getText().trim().equals("")) {
							if( solution == null) {
								textFields[c][r].setStyle("-fx-background-color:rgb(255,204,204)");
							}
							else {
								textFields[c][r].setStyle("-fx-background-color:rgb(204,255,204)");
								textFields[c][r].setText(solution[c][r]);
							}
						}
						else {
							textFields[c][r].setStyle("-fx-background-color:rgb(255,255,255)");
						}
					}
				}
				
			}
		});
		
		// Launch the UI
		Scene scene = new Scene(grid, 400, 400);
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
