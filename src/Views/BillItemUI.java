// Defines the Java interface for working with items on a bill.
package Views;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class BillItemUI {
   private final Stage CategoryPopup = new Stage();
   private final Stage SupplierPopup = new Stage();
   private final Stage ItemPopup = new Stage();
   private final TextField TFName= new TextField();
   private final TextField TFProfitMargin= new TextField();
   private final TextField TFBarcode = new TextField();
   private final TextField TFBoughtPrice= new TextField();
   private final TextField TFBoughtStock= new TextField();
   private final TextField TFType= new TextField();
   private final TextField TFComment= new TextField();
   private final TextField TFContact = new TextField();
   private final Button btnSave = new Button("Save");
   private final Button btnCancel = new Button("Cancel");
   public  final ObservableList<String> cat=  FXCollections.observableArrayList("Create a new category:");
   private final ObservableList<String> sup=  FXCollections.observableArrayList("Create a new supplier:");
   private final ComboBox<String> Categories= new ComboBox<>();
   private final ComboBox<String> Suppliers = new ComboBox<>();
   
   
public BillItemUI(String type){
	if(type.equals("Category")) {
		CategoryPopup.setTitle("Add Category:");
		TFName.setPromptText("Enter Category Name:");
	    TFProfitMargin.setPromptText("Enter Profit Margin");
	    TFProfitMargin.setTextFormatter(new TextFormatter<>(input -> {
		    return input.getControlNewText().matches("\\d*(\\.\\d*)?")  ? input : null;
		}));
		TFName.setPrefWidth(130);
		TFProfitMargin.setPrefWidth(130);
		GridPane grid = new GridPane();
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(10));
		grid.add(new Label("Name:"), 0, 0);
		grid.add(TFName, 1, 0);
		grid.add(new Label("Profit Margin in %:"), 0, 1);
		grid.add(TFProfitMargin, 1, 1);
		HBox buttons = new HBox(10, btnSave, btnCancel);
		buttons.setAlignment(Pos.CENTER_RIGHT);
		grid.add(buttons, 1, 2);
		GridPane.setColumnSpan(buttons, 2);
		CategoryPopup.setScene(new Scene(grid, 270, 130));
		CategoryPopup.initModality(Modality.APPLICATION_MODAL);
		CategoryPopup.show();
	}
	else if(type.equals("Supplier")){
		SupplierPopup.setTitle("Add Supplier:");
		TFName.setPromptText("Enter supplier name");
		TFContact.setPromptText("wdgaster@royalmail.und");
		TFProfitMargin.setPromptText("Enter Profit Margin");
		TFProfitMargin.setTextFormatter(new TextFormatter<>(input -> {
		    return input.getControlNewText().matches("\\d*(\\.\\d*)?")  ? input : null;
		}));
		TFName.setPrefWidth(130);
		TFProfitMargin.setPrefWidth(130);
		TFContact.setPrefWidth(130);
		GridPane grid = new GridPane();
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(10));
		grid.add(new Label("Name:"), 0, 0);
		grid.add(TFName, 1, 0);
		grid.add(new Label("Email:"), 0, 1);
		grid.add(TFContact, 1, 1);
		grid.add(new Label("Profit Margin in %:"), 0, 2);
		grid.add(TFProfitMargin, 1, 2);
		HBox buttons = new HBox(10, btnSave, btnCancel);
		buttons.setAlignment(Pos.BOTTOM_RIGHT);
		grid.add(buttons, 1, 3);
		SupplierPopup.setScene(new Scene(grid, 280, 150));
		SupplierPopup.initModality(Modality.APPLICATION_MODAL);
		SupplierPopup.show();
	}
	else {
		ItemPopup.setTitle("Add Article:");
		TFName.setPromptText("Enter article name");
		TFType.setPromptText("Enter type of the article");
		TFComment.setPromptText("Enter a comment (Optional)");
		TFBarcode.setPromptText("Scan barcode or enter it manually");
		TFBarcode.setTextFormatter(new TextFormatter<>(input -> {
		    return input.getControlNewText().matches("\\d*") ? input : null;
		}));
		TFBoughtPrice.setPromptText("Enter bought price");
		TFBoughtPrice.setTextFormatter(new TextFormatter<>(input -> {
		    return input.getControlNewText().matches("\\d*(\\.\\d*)?")  ? input : null;
		}));
		TFBoughtStock.setPromptText("Enter bought stock");
		TFBoughtStock.setTextFormatter(new TextFormatter<>(input -> {
		    return input.getControlNewText().matches("\\d*") ? input : null;
		}));
		TFName.setPrefWidth(180);
		TFType.setPrefWidth(180);
		TFComment.setPrefWidth(180);
		TFBarcode.setPrefWidth(180);
		TFBoughtPrice.setPrefWidth(180);
		TFBoughtStock.setPrefWidth(180);
		GridPane grid = new GridPane();
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(10));
		grid.add(new Label("Name:"), 0, 0);
		grid.add(TFName, 1, 0);
		grid.add(new Label("Type:"), 0, 1);
		grid.add(TFType, 1, 1);
		grid.add(new Label("Comment:"), 0, 2);
		grid.add(TFComment, 1, 2);
		grid.add(new Label("Barcode:"),0,3);
		grid.add(TFBarcode, 1, 3);
		grid.add(new Label("Bought Price:"),0,4);
		grid.add(TFBoughtPrice, 1, 4);
		grid.add(new Label("Bought Stock:"),0,5);
		grid.add(TFBoughtStock, 1, 5);
		grid.add(new Label("Category:"),0,6);
		grid.add(Categories, 1, 6);
		grid.add(new Label("Supplier:"),0,7);
		grid.add(Suppliers, 1, 7);
		Categories.setItems(cat);
		Suppliers.setItems(sup);
		HBox buttons = new HBox(10, btnSave, btnCancel);
		buttons.setAlignment(Pos.BOTTOM_RIGHT);
		grid.add(buttons, 1, 8);
		ItemPopup.setScene(new Scene(grid, 290, 350));
		ItemPopup.initModality(Modality.APPLICATION_MODAL);
	}
}
public void getCategoriesList(ObservableList<String> cat) {
	Categories.hide();
	this.cat.clear();
	 for(int i=0;i<cat.size();i++) {
		this.cat.add(cat.get(i));
	 }
	 this.cat.add("Create a new category:");
}
public void getSuppliersList(ObservableList<String> sup) {
	Suppliers.hide();
	this.sup.clear();
	 for(int i=0;i<sup.size();i++) {
		this.sup.add(sup.get(i));
	 }
	 this.sup.add("Create a new supplier:");
}
public ComboBox<String> getCategoryCombo() {
	return Categories;
}
public ComboBox<String> getSupplierCombo(){
	return Suppliers;
}
public TextField getTFType() {
	return TFType;
}
public TextField getTFComment() {
	return TFComment;
}
public TextField getTFBoughtPrice() {
	return TFBoughtPrice;
}
public TextField getTFBarcode() {
	return TFBarcode;
}
public TextField getTFBoughtStock() {
	return TFBoughtStock;
}
public Button getSaveBtn() {
	return btnSave;
}
public Button getCancelBtn() {
	return btnCancel;
}
public TextField getTFName() {
	return TFName;
}
public TextField getTFProfitMargin() {
	return TFProfitMargin;
}
public TextField getTFContact() {
	return TFContact;
}
public Stage getCategoryStage() {
    return CategoryPopup;
}
public Stage getSupplierStage() {
    return SupplierPopup;
}
public Stage getItemStage() {
    return ItemPopup;
}
}

