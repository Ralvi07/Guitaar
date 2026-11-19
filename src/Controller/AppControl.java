package Controller;
//TO DO SUPPLIER NAME CHECK AT CELL LISTENERS
//TO DO CATEGORY NAME CHECK AT CELL LISTENERS
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import DAOS.BillItem;
import DAOS.FileHandler;
import Model.Bills;
import Model.Category;
import Model.Item;
import Model.Sectors;
import Model.Supplier;
import Views.BillItemUI;
import Views.ItemUi;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
public class AppControl {
private final ItemUi TableUI;
private final FileHandler file;
private final BillItem RAMStorage;
private final BillItemUI PopupUI;
public AppControl() throws IOException, ClassNotFoundException{
	file=new FileHandler();
	RAMStorage=file.getRAMStorage();
	TableUI =new ItemUi();
	PopupUI = new BillItemUI("E");
	TableUI.getCategoriesList(RAMStorage.getCategoriesName());
	TableUI.getSuppliersList(RAMStorage.getSuppliersName());
	TableUI.getTableView().setItems(RAMStorage.getItems());
	TableUI.getBillsTable().setItems(RAMStorage.getBills());
	TableUI.getCategoryTable().setItems(RAMStorage.getCategories());
	TableUI.getSupplierTable().setItems(RAMStorage.getSuppliers());
	TableUI.getSectorsTable().setItems(RAMStorage.getSectors());
	TableUI.getSectorAdd().setOnAction(e -> SectorAction());
    TableUI.getBtnAdd().setOnAction(e -> AddAction());
    TableUI.getCategoryAdd().setOnAction(e -> getCategoryPopup());
    TableUI.getSupplierAdd().setOnAction(e -> getSupplierPopup());
    TableUI.getSaveBar().setOnAction(e -> SaveAction());
    TableUI.getBtnSectorDelete().setOnAction(e ->DeleteSectorAction());
    TableUI.getLoadBar().setOnAction(e -> LoadAction());
    TableUI.getbtnCustomPrices().setOnAction(e-> CustomPricesAction());
    TableUI.getBtnAutoPrices().setOnAction(e -> AutoPricesAction());
    TableUI.getBtnDelete().setOnAction(e-> DeleteAction());
    TableUI.getRecipetButton().setOnAction(e -> PrintRecipetAction());
    TableUI.getAddRecipetButton().setOnAction(e -> AddItemRecipet());
    TableUI.getRecipetCancel().setOnAction(e-> RecipetCancel());
    TableUI.getCategoryDelete().setOnAction(e-> DeleteCategoryAction());
    TableUI.getSupplierDelete().setOnAction(e-> DeleteSupplierAction());
    setCellEditors();
}
private void AddItemRecipet() {
	if(TableUI.getRecieptBarcode().getText().isBlank()|| TableUI.getRecieptAmount().getText().isBlank()) {
		Alert alert2= new Alert(Alert.AlertType.INFORMATION);
    	alert2.setTitle("Info");
        alert2.setHeaderText("Please fill the fields with their respectic values.");
    	alert2.showAndWait();
	}else {
		Item item =RAMStorage.getItemFromBarcode(Integer.parseInt(TableUI.getRecieptBarcode().getText()));
		if(item==null) {
			Alert alert3 = new Alert(Alert.AlertType.ERROR);
			alert3.setTitle("Error");
    		alert3.setHeaderText("Invalid Barcode enterted!");
    		alert3.setContentText("Please renter the barcode!");
    		alert3.showAndWait();
    		TableUI.getRecieptBarcode().clear();
		}else if(Integer.parseInt(TableUI.getRecieptAmount().getText())>RAMStorage.getItemFromBarcode(Integer.parseInt(TableUI.getRecieptBarcode().getText())).getQuantity()){
			Alert alert3 = new Alert(Alert.AlertType.ERROR);
			alert3.setTitle("Error");
    		alert3.setHeaderText("Invalid Amount enterted!");
    		alert3.setContentText("The value exeeds the value instock, please recount and enter!");
    		alert3.showAndWait();
    		TableUI.getRecieptAmount().clear();
		}else {
			addItemtoRecipt(item, Integer.parseInt(TableUI.getRecieptAmount().getText()));
			TableUI.getRecieptAmount().clear();
			TableUI.getRecieptBarcode().clear();
		}
	}
}
private void addItemtoRecipt(Item item,int id) {
	TableUI.getReceiptUI().addItem(item.getName(), id, item.getSellingPrice());
}
private void RecipetCancel() {
	TableUI.RefreshReceiptUI();
	TableUI.getRecieptAmount().clear();
	TableUI.getRecieptBarcode().clear();
}
private void PrintRecipetAction() {
	List<Bills> list= TableUI.getReceiptUI().ReturntotalItems();
	list.getFirst().IterateID();
	if(list.size()==0) {
		Alert alert2= new Alert(Alert.AlertType.INFORMATION);
		alert2.setTitle("Info");
        alert2.setHeaderText("Please enter an article first!");
    	alert2.showAndWait();
	}else {
		for(int i=0;i<list.size();i++) {
			RAMStorage.addBill(list.get(i));
			RAMStorage.getBills().get(i).IterateID();
		}

		try {
			file.PrintBills(list);
		} catch (IOException e) {
			e.printStackTrace();
		}
		RecipetCancel();
	}
	RefreshLists();
	
}
private void SectorAction() {
	if(TableUI.getSectorTextField().getText().isBlank()) {
		Alert alert2= new Alert(Alert.AlertType.INFORMATION);
    	alert2.setTitle("Info");
        alert2.setHeaderText("Please enter the name of the sector.");
    	alert2.showAndWait();
	}else {
		if(RAMStorage.CheckIfSectorExists(TableUI.getSectorTextField().getText())) {
			Alert alert3 = new Alert(Alert.AlertType.ERROR);
    		alert3.setTitle("Error");
    		alert3.setHeaderText("Sector with the same name detected!");
    		alert3.setContentText("You can only have on sector with the same in it!");
    		alert3.showAndWait();
		}else {
			RAMStorage.AddSector(new Sectors(TableUI.getSectorTextField().getText()));
			TableUI.getSectorTextField().clear();
		}
		
	}
}
private void DeleteAction() {
	ObservableList<Item> selectedItems = FXCollections.observableArrayList(TableUI.getTableView().getSelectionModel().getSelectedItems() );
	if(selectedItems.size()==0) {
		Alert alert2= new Alert(Alert.AlertType.INFORMATION);
    	alert2.setTitle("Info");
        alert2.setHeaderText("No item selected!");
    	alert2.showAndWait();
	}
	else {
		Alert alert = new Alert(Alert.AlertType.CONFIRMATION,"Delete selected items?",ButtonType.YES, ButtonType.NO);
	    alert.setTitle("Confirm delete");
		alert.setHeaderText(null);
		Optional<ButtonType> result = alert.showAndWait();
	    if (result.isPresent() && result.get() == ButtonType.YES) {
	    	RAMStorage.deleteItems(selectedItems);
	    	Alert alert2= new Alert(Alert.AlertType.INFORMATION);
	    	alert2.setTitle("Info");
	    	if(selectedItems.size()==0) {
	    		alert2.setHeaderText("Item deleted succsessfully!");
	    	}else {
	    		alert2.setHeaderText("Items deleted succsessfully!");
	    	}
	    	alert2.showAndWait();
	    	TableUI.getTableView().refresh();
	    }
	}
	
}
private void DeleteCategoryAction() {
	ObservableList<Category> selectedItems = FXCollections.observableArrayList(TableUI.getCategoryTable().getSelectionModel().getSelectedItems() );
	if(selectedItems.size()==0) {
		Alert alert2= new Alert(Alert.AlertType.INFORMATION);
    	alert2.setTitle("Info");
        alert2.setHeaderText("No item selected!");
    	alert2.showAndWait();
	}
	else {
		Alert alert = new Alert(Alert.AlertType.CONFIRMATION,"Delete selected categories?",ButtonType.YES, ButtonType.NO);
	    alert.setTitle("Confirm delete");
		alert.setHeaderText(null);
		Optional<ButtonType> result = alert.showAndWait();
	    if (result.isPresent() && result.get() == ButtonType.YES) {
	    	if(RAMStorage.deleteCategory(selectedItems)) {
	    		Alert alert2= new Alert(Alert.AlertType.INFORMATION);
		    	alert2.setTitle("Info");
		    	if(selectedItems.size()==0) {
		    		alert2.setHeaderText("Category deleted succsessfully!");
		    	}else {
		    		alert2.setHeaderText("Categories deleted succsessfully!");
		    	};
		    	alert2.showAndWait();
		    	TableUI.getCategoryTable().refresh();
	    	}else {
	    		Alert alert3 = new Alert(Alert.AlertType.ERROR);
	    		alert3.setTitle("Error");
	    		alert3.setHeaderText("Category deleteion failed!");
	    		alert3.setContentText("You can only delete categories that has no articles in it!");
	    		alert3.showAndWait();
	    	}
	    	
	    }
	}RefreshLists();
	
}
private void DeleteSectorAction() {
	ObservableList<Sectors> selectedItems = FXCollections.observableArrayList(TableUI.getSectorsTable().getSelectionModel().getSelectedItems() );
	if(selectedItems.size()==0) {
		Alert alert2= new Alert(Alert.AlertType.INFORMATION);
    	alert2.setTitle("Info");
        alert2.setHeaderText("No sectors selected!");
    	alert2.showAndWait();
	}
	else {
		Alert alert = new Alert(Alert.AlertType.CONFIRMATION,"Delete selected sectors?",ButtonType.YES, ButtonType.NO);
	    alert.setTitle("Confirm delete");
		alert.setHeaderText(null);
		Optional<ButtonType> result = alert.showAndWait();
	    if (result.isPresent() && result.get() == ButtonType.YES) {
	    	if(RAMStorage.DeleteSectors(selectedItems)) {
	    		Alert alert2= new Alert(Alert.AlertType.INFORMATION);
		    	alert2.setTitle("Info");
		    	if(selectedItems.size()==1) {
		    		alert2.setHeaderText("Sector deleted succsessfully!");
		    	}else {
		    		alert2.setHeaderText("Sectors deleted succsessfully!");
		    	};
		    	alert2.showAndWait();
		    	TableUI.getCategoryTable().refresh();
	    	}else {
	    		Alert alert3 = new Alert(Alert.AlertType.ERROR);
	    		alert3.setTitle("Error");
	    		alert3.setHeaderText("Sectors deleteion failed!");
	    		alert3.setContentText("You can only delete empty sectors!");
	    		alert3.showAndWait();
	    	}
	    	
	    }
	}TableUI.getSectorsTable().refresh();
	
}
private void DeleteSupplierAction() {
	ObservableList<Supplier> selectedItems = FXCollections.observableArrayList(TableUI.getSupplierTable().getSelectionModel().getSelectedItems() );
	if(selectedItems.size()==0) {
		Alert alert2= new Alert(Alert.AlertType.INFORMATION);
    	alert2.setTitle("Info");
        alert2.setHeaderText("No supplier selected!");
    	alert2.showAndWait();
	}
	else {
		Alert alert = new Alert(Alert.AlertType.CONFIRMATION,"Delete selected suppliers?",ButtonType.YES, ButtonType.NO);
	    alert.setTitle("Confirm delete");
		alert.setHeaderText(null);
		Optional<ButtonType> result = alert.showAndWait();
		if (result.isPresent() && result.get() == ButtonType.YES) {
	    	if(RAMStorage.deleteSupplier(selectedItems)) {
	    		Alert alert2= new Alert(Alert.AlertType.INFORMATION);
		    	alert2.setTitle("Info");
		    	if(selectedItems.size()==0) {
		    		alert2.setHeaderText("Supplier deleted succsessfully!");
		    	}else {
		    		alert2.setHeaderText("Suppliers deleted succsessfully!");
		    	};
		    	alert2.showAndWait();
		    	TableUI.getCategoryTable().refresh();
	    	}else {
	    		Alert alert3 = new Alert(Alert.AlertType.ERROR);
	    		alert3.setTitle("Error");
	    		alert3.setHeaderText("Category deleteion failed!");
	    		alert3.setContentText("You can only delete categories that has no articles in it!");
	    		alert3.showAndWait();
	    	}
	    	
	    }
	}RefreshLists();
}
private void setCellEditors() {
	//Sectors name done
	TableUI.getSectorsNameColumn().setOnEditCommit(e ->{ 
		if(!RAMStorage.CheckIfSectorExists(e.getNewValue())) {
			RAMStorage.getSectors().get(e.getTablePosition().getRow()).setName(e.getNewValue());
		}
		else {
			Alert alert3 = new Alert(Alert.AlertType.ERROR);
			alert3.setTitle("Error");
			alert3.setHeaderText("Name replacement failed!");
			alert3.setContentText("A sector with the same name already exists!");
			alert3.showAndWait();
			RAMStorage.getSectors().get(e.getTablePosition().getRow()).setName(e.getOldValue());
			TableUI.getSectorsTable().refresh();
		}

	});
	//Item name done
	TableUI.getItemNameColumn().setOnEditCommit(e ->{  
		if(!RAMStorage.CheckIfNameExists(e.getNewValue())) {
			RAMStorage.getItems().get(e.getTablePosition().getRow()).setName(e.getNewValue());
		}
		else {
			Alert alert3 = new Alert(Alert.AlertType.ERROR);
			alert3.setTitle("Error");
			alert3.setHeaderText("Name replacement failed!");
			alert3.setContentText("An article with the same name already exists!");
			alert3.showAndWait();
			RAMStorage.getItems().get(e.getTablePosition().getRow()).setName(e.getOldValue());
			TableUI.getTableView().refresh();
		}
		});
	// Item discounts
	TableUI.getItemDiscountColumn().setOnEditCommit(e ->{
	RAMStorage.getItems().get(e.getTablePosition().getRow()).setItemDiscount(e.getNewValue());
	TableUI.getTableView().refresh();
	});
	//Item type
	TableUI.getItemTypeColumn().setOnEditCommit(e ->  RAMStorage.getItems().get(e.getTablePosition().getRow()).setType(e.getNewValue()));
	// item comment
	TableUI.getItemCommentColumn().setOnEditCommit(e ->  RAMStorage.getItems().get(e.getTablePosition().getRow()).setComment(e.getNewValue()));
	//item sell price calculation and refresh
	TableUI.getItemSellPriceColumn().setOnEditCommit(e ->{  
    RAMStorage.getItems().get(e.getTablePosition().getRow()).setSellingPrice(e.getNewValue());
    TableUI.getTableView().refresh();
	});
	// item bought price and refresh
	TableUI.getItemBoughtPriceColumn().setOnEditCommit(e ->{
	RAMStorage.getItems().get(e.getTablePosition().getRow()).setBoughtPrice(e.getNewValue());
	TableUI.getTableView().refresh();
	});
	// item stock
	TableUI.getItemStockColumn().setOnEditCommit(e -> {
		RAMStorage.getItems().get(e.getTablePosition().getRow()).setQuantity(e.getNewValue());
		TableUI.getTableView().refresh();	
		});
	// item category popout / edit
	TableUI.getCategoryColumn().setOnEditCommit(e -> {
	    Item rowCategoryName = e.getRowValue();
	    String newrowCategoryName= e.getNewValue();
	    if(newrowCategoryName.equals("Create a new category:")) {
	    	BillItemUI Popout=new BillItemUI("Category");
	    	Popout.getCancelBtn().setOnAction(b -> {
	    	    Popout.getCategoryStage().close();
	    	    TableUI.getTableView().refresh();
	    	});
	    	Popout.getSaveBtn().setOnAction(a -> {
	    	if(Popout.getTFName().getText().isBlank()||Popout.getTFProfitMargin().getText().isBlank()) {
	    		Alert alert = new Alert(Alert.AlertType.ERROR);
	    		alert.setTitle("Error");
	    		alert.setHeaderText("Category Creation failed!");
	    		alert.setContentText("Please fill all the textfields with the appopriate data");
	    		alert.showAndWait();
	    	}else {
	    		if(RAMStorage.CheckIfCategoryNameExists(Popout.getTFName().getText())) {
	    			Alert alert = new Alert(Alert.AlertType.ERROR);
	    			alert.setTitle("Error");
	    			alert.setHeaderText("Creation of category failure!");
	    			alert.setContentText("A category with the same name already exists!");
	    			alert.showAndWait();
	    			Popout.getTFName().clear();
	    		}
	    		else {
	    			RAMStorage.CreateNewCateogryPopup(Popout.getTFName().getText(), Double.parseDouble(Popout.getTFProfitMargin().getText()), rowCategoryName);
	    	    	Popout.getCategoryCombo().setValue(Popout.getTFName().getText());
	    	    	RefreshLists();
	    	    	Popout.getCategoryStage().close();
	    		}
	    	}
	    	});
	    }
	    else {
	    RAMStorage.ChangeItemCategory(newrowCategoryName, rowCategoryName);
	    }
	//item supplier popout and edit    
	});
	TableUI.getSupplierColumn().setOnEditCommit(e -> {
	    Item rowCategoryName = e.getRowValue();
	    String newrowCategoryName= e.getNewValue();
	    if(newrowCategoryName.equals("Create a new supplier:")) {
	    	BillItemUI Popout=new BillItemUI("Supplier");
	    	Popout.getCancelBtn().setOnAction(b -> {
	    	    Popout.getSupplierStage().close();
	    	    TableUI.getTableView().refresh();
	    	});
	    	Popout.getSaveBtn().setOnAction(a -> {
	    		if(Popout.getTFName().getText().isBlank()||Popout.getTFProfitMargin().getText().isBlank()) {
		    		Alert alert = new Alert(Alert.AlertType.ERROR);
		    		alert.setTitle("Error");
		    		alert.setHeaderText("Supplier Creation failed!");
		    		alert.setContentText("Please fill all the textfields with the appopriate data");
		    		alert.showAndWait();
		    	}else {
		    		if(RAMStorage.CheckIfSupplierNameExists(Popout.getTFName().getText())) {
		    			Alert alert = new Alert(Alert.AlertType.ERROR);
		    			alert.setTitle("Error");
		    			alert.setHeaderText("Creation of supplier failure!");
		    			alert.setContentText("A supplier with the same name already exists!");
		    			alert.showAndWait();
		    			Popout.getTFName().clear();
		    		}
		    		else {
		    			RAMStorage.CreateNewSupplierPopup(Popout.getTFName().getText(), Double.parseDouble(Popout.getTFProfitMargin().getText()),Popout.getTFContact().getText(), rowCategoryName);
		    	    	Popout.getSupplierCombo().setValue(Popout.getTFName().getText());
		    	    	RefreshLists();
		    	    	Popout.getSupplierStage().close();
		    		}
		    	}
	    	
	    	});
	    }
	    else {
	    RAMStorage.ChangeItemSupplier(newrowCategoryName, rowCategoryName);
	    }
	//custom profit item    
	});
	TableUI.getCustomProfitMarginColumn().setOnEditCommit(e ->{ 
		RAMStorage.getItems().get(e.getTablePosition().getRow()).setCustomProfit(e.getNewValue());
		TableUI.getTableView().refresh();
		});
	//Category name : TO DO NAME CHECK
	TableUI.getCategoryNameColumn().setOnEditCommit(e ->{ 
		RAMStorage.getCategories().get(e.getTablePosition().getRow()).setName(e.getNewValue());
		RAMStorage.UpdateItemsCategory(RAMStorage.getCategories().get(e.getTablePosition().getRow()));
		RefreshLists();
		TableUI.getCategoriesList(RAMStorage.getCategoriesName());
		});
	//Category profit margin
	TableUI.getCategoryMarginColumn().setOnEditCommit(e ->{ 
		RAMStorage.getCategories().get(e.getTablePosition().getRow()).setProfit(e.getNewValue());
		RAMStorage.UpdateItemsCategory(RAMStorage.getCategories().get(e.getTablePosition().getRow()));
		RefreshLists();
		TableUI.getCategoriesList(RAMStorage.getCategoriesName());
		});
	// Category discount
	TableUI.getCategoryDiscountColumn().setOnEditCommit(e ->{ 
		RAMStorage.getCategories().get(e.getTablePosition().getRow()).setDiscount(e.getNewValue());
		RAMStorage.UpdateItemsCategory(RAMStorage.getCategories().get(e.getTablePosition().getRow()));
		RefreshLists();
		TableUI.getCategoriesList(RAMStorage.getCategoriesName());
		});
	//Supplier name : TO DO Supplier name check
	TableUI.getSupplierNameColumn().setOnEditCommit(e ->{ 
		RAMStorage.getSuppliers().get(e.getTablePosition().getRow()).setName(e.getNewValue());
		RAMStorage.UpdateItemsSupplier(RAMStorage.getSuppliers().get(e.getTablePosition().getRow()));
		RefreshLists();
		TableUI.getSuppliersList(RAMStorage.getSuppliersName());
		});
	//supplier margin 
	TableUI.getSupplierMarginColumn().setOnEditCommit(e ->{ 
		RAMStorage.getSuppliers().get(e.getTablePosition().getRow()).setProfit(e.getNewValue());
		RAMStorage.UpdateItemsSupplier(RAMStorage.getSuppliers().get(e.getTablePosition().getRow()));
		RefreshLists();
		TableUI.getSuppliersList(RAMStorage.getSuppliersName());
		});
    //supplier discount 
	TableUI.getSupplierDiscountColumn().setOnEditCommit(e ->{ 
		RAMStorage.getSuppliers().get(e.getTablePosition().getRow()).setDiscount(e.getNewValue());
		RAMStorage.UpdateItemsSupplier(RAMStorage.getSuppliers().get(e.getTablePosition().getRow()));
		RefreshLists();
		TableUI.getSuppliersList(RAMStorage.getSuppliersName());
		});
}
private void AddAction() {
	PopupUI.getSuppliersList(RAMStorage.getSuppliersName());
	PopupUI.getCategoriesList(RAMStorage.getCategoriesName());
    PopupUI.getItemStage().show();
	PopupUI.getCancelBtn().setOnAction(b -> {
		PopupUI.getTFBarcode().clear();
		PopupUI.getTFName().clear();
		PopupUI.getTFType().clear();
		PopupUI.getTFBoughtStock().clear();
		PopupUI.getTFBoughtPrice().clear();
		PopupUI.getTFComment().clear();
		PopupUI.getCategoryCombo().getSelectionModel().clearSelection();
		PopupUI.getSupplierCombo().getSelectionModel().clearSelection();
		PopupUI.getItemStage().close();
	});
	PopupUI.getSaveBtn().setOnAction(a -> {
	if(PopupUI.getTFBarcode().getText().isBlank()||PopupUI.getTFName().getText().isBlank()||PopupUI.getTFType().getText().isBlank()||PopupUI.getTFBoughtPrice().getText().isBlank()||PopupUI.getTFBoughtStock().getText().isBlank()||PopupUI.getTFComment().getText().isBlank()) {
		Alert alert = new Alert(Alert.AlertType.ERROR);
		alert.setTitle("Error");
		alert.setHeaderText("Article Creation failed!");
		alert.setContentText("Please fill all the textfields with the appopriate data");
		alert.showAndWait();
	}else {
		if(RAMStorage.CheckIfBarcodeExists(Integer.parseInt(PopupUI.getTFBarcode().getText()))) {
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setTitle("Error");
			alert.setHeaderText("Creation of article failure!");
			alert.setContentText("An article with the same barcode already exists!");
			alert.showAndWait();
			PopupUI.getTFBarcode().clear();
		}else {
		Item item= new Item(Integer.parseInt(PopupUI.getTFBarcode().getText()),PopupUI.getTFName().getText(),PopupUI.getTFType().getText(),Double.parseDouble(PopupUI.getTFBoughtPrice().getText()),Integer.parseInt(PopupUI.getTFBoughtStock().getText()),PopupUI.getTFComment().getText());
		RAMStorage.AddItem(item);
		RAMStorage.ChangeItemCategory(PopupUI.getCategoryCombo().getValue(), item);
		RAMStorage.ChangeItemSupplier(PopupUI.getSupplierCombo().getValue(), item);
		RefreshLists();
		PopupUI.getTFBarcode().clear();
		PopupUI.getTFName().clear();
		PopupUI.getTFBoughtStock().clear();
		PopupUI.getTFBoughtPrice().clear();
		PopupUI.getTFComment().clear();
		PopupUI.getTFType().clear();
		PopupUI.getCategoryCombo().getSelectionModel().clearSelection();
		PopupUI.getSupplierCombo().getSelectionModel().clearSelection();
		PopupUI.getItemStage().close();
		}
	}
	});
	PopupUI.getCategoryCombo().valueProperty().addListener((obs, oldValue, newValue) ->{
		if(newValue !=null) {
			if(newValue.equals("Create a new category:")) {
				getCategoryPopup(newValue);
		}}});
	PopupUI.getSupplierCombo().valueProperty().addListener((obs, oldValue, newValue) ->{
		if(newValue !=null) {
			if(newValue.equals("Create a new supplier:")) {
				getSupplierPopup(newValue);
		}}});
}
private void getCategoryPopup(String selection) {
			BillItemUI Popoup=new BillItemUI("Category");
	    	Popoup.getCancelBtn().setOnAction(b -> {
	    	    Popoup.getCategoryStage().close();
	    	});
	    	Popoup.getSaveBtn().setOnAction(a -> {
	    		if(Popoup.getTFName().getText().isBlank()||Popoup.getTFProfitMargin().getText().isBlank()) {
		    		Alert alert = new Alert(Alert.AlertType.ERROR);
		    		alert.setTitle("Error");
		    		alert.setHeaderText("Category Creation failed!");
		    		alert.setContentText("Please fill all the textfields with the appopriate data");
		    		alert.showAndWait();
		    	}else {
		    		if(RAMStorage.CheckIfCategoryNameExists(Popoup.getTFName().getText())) {
		    			Alert alert = new Alert(Alert.AlertType.ERROR);
		    			alert.setTitle("Error");
		    			alert.setHeaderText("Creation of category failure!");
		    			alert.setContentText("A category with the same name already exists!");
		    			alert.showAndWait();
		    			Popoup.getTFName().clear();
		    		}
		    		else {
		    			RAMStorage.CreateNewCategory(Popoup.getTFName().getText(), Double.parseDouble(Popoup.getTFProfitMargin().getText()));
		    	    	PopupUI.getCategoriesList(RAMStorage.getCategoriesName());
		    	    	PopupUI.getCategoryCombo().getSelectionModel().select(Popoup.getTFName().getText());
		    	    	Popoup.getCategoryStage().close();
		    		}
		    	}
	    	
	    	});
}
private void getCategoryPopup() {
	BillItemUI Popoup=new BillItemUI("Category");
	Popoup.getCancelBtn().setOnAction(b -> {
	    Popoup.getCategoryStage().close();
	});
	Popoup.getSaveBtn().setOnAction(a -> {
		if(Popoup.getTFName().getText().isBlank()||Popoup.getTFProfitMargin().getText().isBlank()) {
    		Alert alert = new Alert(Alert.AlertType.ERROR);
    		alert.setTitle("Error");
    		alert.setHeaderText("Category Creation failed!");
    		alert.setContentText("Please fill all the textfields with the appopriate data");
    		alert.showAndWait();
    	}else {
    		if(RAMStorage.CheckIfCategoryNameExists(Popoup.getTFName().getText())) {
    			Alert alert = new Alert(Alert.AlertType.ERROR);
    			alert.setTitle("Error");
    			alert.setHeaderText("Creation of category failure!");
    			alert.setContentText("A category with the same name already exists!");
    			alert.showAndWait();
    			Popoup.getTFName().clear();
    		}
    		else {
    			RAMStorage.CreateNewCategory(Popoup.getTFName().getText(), Double.parseDouble(Popoup.getTFProfitMargin().getText()));
    			TableUI.getCategoryTable().refresh();
    	    	Popoup.getCategoryStage().close();
    	    	Alert alert2= new Alert(Alert.AlertType.INFORMATION);
    			alert2.setTitle("Info");
                alert2.setHeaderText("Category created successfully!");
            	alert2.showAndWait();
    		}
    	}
	
	RefreshLists();});
}
private void getSupplierPopup(String selection) {
	BillItemUI Popoup=new BillItemUI("Supplier");
	Popoup.getCancelBtn().setOnAction(b -> {
	    Popoup.getSupplierStage().close();
	});
	Popoup.getSaveBtn().setOnAction(a -> {
		if(Popoup.getTFName().getText().isBlank()||Popoup.getTFProfitMargin().getText().isBlank()) {
    		Alert alert = new Alert(Alert.AlertType.ERROR);
    		alert.setTitle("Error");
    		alert.setHeaderText("Supplier Creation failed!");
    		alert.setContentText("Please fill all the textfields with the appopriate data");
    		alert.showAndWait();
    	}else {
    		if(RAMStorage.CheckIfSupplierNameExists(Popoup.getTFName().getText())) {
    			Alert alert = new Alert(Alert.AlertType.ERROR);
    			alert.setTitle("Error");
    			alert.setHeaderText("Creation of supplier failure!");
    			alert.setContentText("A supplier with the same name already exists!");
    			alert.showAndWait();
    			Popoup.getTFName().clear();
    		}
    		else {
    			RAMStorage.CreateNewSupplier(Popoup.getTFName().getText(), Double.parseDouble(Popoup.getTFProfitMargin().getText()),Popoup.getTFContact().getText());
    			PopupUI.getSuppliersList(RAMStorage.getSuppliersName());
    			PopupUI.getSupplierCombo().getSelectionModel().select(Popoup.getTFName().getText());
    			Popoup.getSupplierStage().close();
    			
    		}
    	}
	
	});
}
private void getSupplierPopup() {
	BillItemUI Popoup=new BillItemUI("Supplier");
	Popoup.getCancelBtn().setOnAction(b -> {
	    Popoup.getSupplierStage().close();
	});
	Popoup.getSaveBtn().setOnAction(a -> {
		if(Popoup.getTFName().getText().isBlank()||Popoup.getTFProfitMargin().getText().isBlank()) {
    		Alert alert = new Alert(Alert.AlertType.ERROR);
    		alert.setTitle("Error");
    		alert.setHeaderText("Supplier Creation failed!");
    		alert.setContentText("Please fill all the textfields with the appopriate data");
    		alert.showAndWait();
    	}else {
    		if(RAMStorage.CheckIfSupplierNameExists(Popoup.getTFName().getText())) {
    			Alert alert = new Alert(Alert.AlertType.ERROR);
    			alert.setTitle("Error");
    			alert.setHeaderText("Creation of supplier failure!");
    			alert.setContentText("A supplier with the same name already exists!");
    			alert.showAndWait();
    			Popoup.getTFName().clear();
    		}
    		else {
    			RAMStorage.CreateNewSupplier(Popoup.getTFName().getText(), Double.parseDouble(Popoup.getTFProfitMargin().getText()),Popoup.getTFContact().getText());
    			Popoup.getSupplierStage().close();
    			TableUI.getSupplierTable().refresh();
    			Alert alert2= new Alert(Alert.AlertType.INFORMATION);
    			alert2.setTitle("Info");
                alert2.setHeaderText("Supplier created successfully!");
            	alert2.showAndWait();
    		}
    	}
	
		RefreshLists();});
}
private void RefreshLists() {
	PopupUI.getSuppliersList(RAMStorage.getSuppliersName());
	PopupUI.getCategoriesList(RAMStorage.getCategoriesName());
	TableUI.getCategoriesList(RAMStorage.getCategoriesName());
	TableUI.getSuppliersList(RAMStorage.getSuppliersName());
	TableUI.getTableView().refresh();
}
private void AutoPricesAction() {
	Alert alert = new Alert(Alert.AlertType.CONFIRMATION,"Overwrite existing custom prices?",ButtonType.YES, ButtonType.NO);
    alert.setTitle("Comfirm overwrite!");
	alert.setHeaderText(null);
	Optional<ButtonType> result = alert.showAndWait();
	if (result.isPresent() && result.get() == ButtonType.YES) {
		ObservableList<Item> selectedItems = FXCollections.observableArrayList(TableUI.getTableView().getSelectionModel().getSelectedItems());
		RAMStorage.setAutoPricesList(selectedItems);
		RefreshLists();
		Alert alert2= new Alert(Alert.AlertType.INFORMATION);
		alert2.setTitle("Info");
	    alert2.setHeaderText("Automatic prices calculated successfully!");
		alert2.showAndWait();
	}
	
}
private void CustomPricesAction() {
	Alert alert = new Alert(Alert.AlertType.CONFIRMATION,"Overwrite existing automatic prices?",ButtonType.YES, ButtonType.NO);
    alert.setTitle("Comfirm overwrite!");
	alert.setHeaderText(null);
	Optional<ButtonType> result = alert.showAndWait();
    if (result.isPresent() && result.get() == ButtonType.YES) {
    	ObservableList<Item> selectedItems = FXCollections.observableArrayList(TableUI.getTableView().getSelectionModel().getSelectedItems());
    	RAMStorage.setCustomPricesList(selectedItems);
    	RefreshLists();
    	Alert alert2= new Alert(Alert.AlertType.INFORMATION);
    	alert2.setTitle("Info");
        alert2.setHeaderText("Custom prices calculated successfully whereever they were applicable!");
    	alert2.showAndWait();
    }
	
}
private void SaveAction() {
	Alert alert = new Alert(Alert.AlertType.CONFIRMATION,"Overwrite existing file?",ButtonType.YES, ButtonType.NO);
    alert.setTitle("Comfirm overwrite!");
	alert.setHeaderText(null);
	Optional<ButtonType> result = alert.showAndWait();
    if (result.isPresent() && result.get() == ButtonType.YES) {
    	try {
    		file.SaveStorage();
    		Alert alert2= new Alert(Alert.AlertType.INFORMATION);
        	alert2.setTitle("Info");
            alert2.setHeaderText("Storage saved successfully!");
        	alert2.showAndWait();
    	} catch (IOException e) {
    		Alert alert2 = new Alert(Alert.AlertType.ERROR);
    		alert2.setTitle("Error");
    		alert2.setHeaderText("Category Creation failed!");
    		alert2.setContentText(e.getMessage());
    		alert2.showAndWait();
    	}
    }
	
}
private void LoadAction() {
	Alert alert = new Alert(Alert.AlertType.CONFIRMATION,"Load existing file?",ButtonType.YES, ButtonType.NO);
    alert.setTitle("Comfirm overwrite!");
	alert.setHeaderText("Any unsaved changes will be lost!");
	Optional<ButtonType> result = alert.showAndWait();
    if (result.isPresent() && result.get() == ButtonType.YES) {
    	try {
    		Alert alert2= new Alert(Alert.AlertType.INFORMATION);
        	alert2.setTitle("Info");
            alert2.setHeaderText("Storage loaded successfully!");
        	alert2.showAndWait();
    		file.LoadStorage();
    	} catch (ClassNotFoundException e) {
    		Alert alert2 = new Alert(Alert.AlertType.ERROR);
    		alert2.setTitle("Error");
    		alert2.setHeaderText("Category Creation failed!");
    		alert2.setContentText(e.getMessage());
    		alert2.showAndWait();
    	} catch (IOException e) {
    		Alert alert2 = new Alert(Alert.AlertType.ERROR);
    		alert2.setTitle("Error");
    		alert2.setHeaderText("Category Creation failed!");
    		alert2.setContentText(e.getMessage());
    		alert2.showAndWait();
    	}
    	RefreshLists();
    }
	
}
public ItemUi getTableUi(){
	return TableUI;
}
}
