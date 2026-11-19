package Views;

import java.io.IOException;

import Controller.AppControl;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class LoginPage {

    private final TextField tfUsername = new TextField();
    private final PasswordField pfPassword = new PasswordField();
    private final Button btnLogin = new Button("Login");
    private final Label lblMessage = new Label();

    public void show(Stage stage) {
        tfUsername.setPromptText("Username");
        pfPassword.setPromptText("Password");

        GridPane grid = new GridPane();
        grid.setPadding(new Insets(20));
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setAlignment(Pos.CENTER);

        grid.add(new Label("Username:"), 0, 0);
        grid.add(tfUsername, 1, 0);
        grid.add(new Label("Password:"), 0, 1);
        grid.add(pfPassword, 1, 1);
        grid.add(btnLogin, 1, 2);
        grid.add(lblMessage, 1, 3);

        lblMessage.setStyle("-fx-text-fill: red;");

        btnLogin.setOnAction(e -> {
			try {
				handleLogin(stage);
			} catch (ClassNotFoundException | IOException e1) {
				e1.printStackTrace();
			}
		});

        stage.setScene(new Scene(grid, 320, 200));
        stage.setTitle("Login");
        stage.show();
    }

    private void handleLogin(Stage stage) throws ClassNotFoundException, IOException {
        String username = tfUsername.getText();
        String password = pfPassword.getText();

        if (username.equals("Gaster666") && password.equals("1225")) {
            lblMessage.setText("Login successful!");
            lblMessage.setStyle("-fx-text-fill: green;");
            AppControl controller = new AppControl();
    		Scene scene = new Scene(controller.getTableUi(), 1700, 800);
    		stage.close();
    		stage.setTitle("Guitaar");
    		stage.setScene(scene);
    		stage.show();
        } else {
            lblMessage.setText("Invalid username or password");
        }
    }
}