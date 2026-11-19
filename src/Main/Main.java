package Main;

import java.io.IOException;

import Views.LoginPage;
import javafx.application.Application;
import javafx.stage.Stage;
 
public class Main extends Application {
	public void start(Stage stage) throws ClassNotFoundException, IOException {
		 LoginPage page= new LoginPage();
		 page.show(stage);
	}

	public static void main(String[] args) {
		Application.launch(args);
	}
}
