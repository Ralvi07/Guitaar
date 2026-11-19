package Views;

import java.util.ArrayList;
import java.util.List;

import Model.Bills;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

public class ReceiptUI extends BorderPane {

    private final VBox itemsBox = new VBox(5);
    private final Label lblTotal = new Label("Total: 0.00");
    private final List<Bills> items = new ArrayList<>();

    public ReceiptUI() {

        
        VBox receipt = new VBox(8);
        receipt.setPadding(new Insets(15));
        receipt.setStyle("-fx-border-color: black; -fx-border-width: 1;");
        Label title = new Label("Gaster Store!!");
        title.setStyle("-fx-font-size: 16; -fx-font-weight: bold;");
        receipt.getChildren().addAll(title, new Separator(), itemsBox,
        new Separator(), lblTotal);
        setCenter(receipt);


    }
    public void addItem(String name, int qty, double price) {
    	Bills item = new Bills(name, qty, price);
        items.add(item);
        HBox row = new HBox(10);
        row.setAlignment(Pos.CENTER_LEFT);
        Label lblName = new Label(name + " x" + qty);
        Label lblPrice = new Label(
                String.format("%.2f", item.getTotal())
        );
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        row.getChildren().addAll(lblName, spacer, lblPrice);
        itemsBox.getChildren().add(row);
        updateTotal();
    }

    private void updateTotal() {
        double sum = items.stream().mapToDouble(Bills::getTotal).sum();
        lblTotal.setText("Total: " + String.format("%.2f", sum));
    }
 public List<Bills> ReturntotalItems(){
	 return items;
 }

}