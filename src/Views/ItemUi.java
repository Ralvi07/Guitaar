package Views;

import java.util.ArrayList;

import Model.Bills;
import Model.Category;
import Model.Item;
import Model.Sectors;
import Model.Supplier;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.converter.DoubleStringConverter;
import javafx.util.converter.IntegerStringConverter;

public class ItemUi extends BorderPane {
	private final ObservableList<String> cat=  FXCollections.observableArrayList();
	private final ObservableList<String> sup=  FXCollections.observableArrayList();
	private final ObservableList<String> users=  FXCollections.observableArrayList();
	private final ObservableList<String> leftusers=  FXCollections.observableArrayList();
	private final ObservableList<String> leftcat=  FXCollections.observableArrayList();
	private final TableView<Item> tableView = new TableView<>();
	private final TableColumn<Item, String> ItemNameColumn;
	private final TableColumn<Item, Integer> BarcodeColumn;
	private final TableColumn<Item, Double> ItemSellPriceColumn;
	private final TableColumn<Item, Double> ItemBoughtPriceColumn;
	private final TableColumn<Item, Integer> ItemStockColumn;
	private final TableColumn<Item, String> CategoryColumn;
	private final TableColumn<Item, String> SupplierColumn;
	private final TableColumn<Item, Integer> TotalItemSoldColumn;
	private final TableColumn<Item, Double> TotalProfitabilityColumn;
	private final TableColumn<Item, Double> DefaultProfitMarginColumn;
	private final TableColumn<Item, Double> CustomProfitMarginColumn;
	private final TableColumn<Item, String> ItemTypeColumn;
	private final TableColumn<Item, String> ItemCommentColumn;
	private final TableColumn<Item, Double> ItemDiscountColumn;
	private final TableColumn<Item, Double> TotalDiscountColumn;
	private final Button btnAdd = new Button("Add");
	private final Button btnDelete = new Button("Delete");
	private final Button btnRecieptAdd = new Button("Add");
	private final Button btnRecieptCancel= new Button("Cancel");
	private final Button btnSectorDelete = new Button("Delete");
	private final MenuItem Save = new MenuItem("Save Data");
	private final MenuItem Load = new MenuItem("Load Data");
	private final Button btnAutoPrices = new Button("Set automated prices");
	private final Button btnCustomPrices = new Button("Set custom prices");
	private final Button btnCategoryAdd = new Button("Add");
	private final Button btnCategoryDelete = new Button("Delete");
	private final Button btnSupplierAdd = new Button("Add");
	private final Button btnSectorAdd = new Button("Add");
	private final Button btnSupplierDelete = new Button("Delete");
	private final TableView<Category> categoriesTable = new TableView<>();
	private final TableColumn<Category, String> CategoryNameColumn;
	private final TableColumn<Category, Integer> CategoryIDColumn;
	private final TableColumn<Category, Double> CategoryMarginColumn;
	private final TableColumn<Category, Integer> CategoryNumbersColumn;
	private final TableColumn<Category, Double> CategoryDiscountColumn;
	private final TableView<Supplier> suppliersTable= new TableView<>();
	private final TableColumn<Supplier, String> SupplierNameColumn;
	private final TableColumn<Supplier, Integer> SupplierIDColumn;
	private final TableColumn<Supplier, Double> SupplierMarginColumn;
	private final TableColumn<Supplier, Integer> SupplierNumbersColumn;
	private final TableColumn<Supplier, String> SupplierContactColumn;
	private final TableColumn<Supplier, Double> SupplierDiscountColumn;
	private final VBox bottomBox;
	private final VBox supplierbottomBox;
	private final VBox categorybottomBox;
	private final VBox sectorbottomBox;
	private final TableView<Sectors> sectorsTable = new TableView<>();
	private final TableColumn<Sectors, String> SectorsNameColumn;
	private final TableColumn<Sectors, String> SectorsUserNames;
	private final TableColumn<Sectors, String> SectorsCategories;
	private final TextField TFSectorName = new TextField();
    private ReceiptUI receiptUI = new ReceiptUI();
    private final TextField tfBarcode = new TextField();
    private final TextField tfAmount = new TextField();
    private final Button btnPrint = new Button("Print Receipt");
    private final TableView<Bills> billTable=new TableView<>();
    private final TableColumn<Bills, Integer> BillsIDColumn;
	private final TableColumn<Bills, String> BillsItemColumn;
	private final TableColumn<Bills, Integer> QuantityColumn;
	private final TableColumn<Bills, Double> PricesColumn;
	private final TableColumn<Bills, Double> TotalPricesColumn;
	private final VBox billsbottom;

	@SuppressWarnings("unchecked")
	public ItemUi() {
		
		
		
		billTable.setEditable(false);
		billTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		billTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_FLEX_LAST_COLUMN);
		
		BillsItemColumn = new TableColumn<>("Article Name:");
		BillsItemColumn.setMinWidth(100);
		BillsItemColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
		BillsItemColumn.setCellFactory(TextFieldTableCell.forTableColumn());
		
		BillsIDColumn = new TableColumn<>("Reciept ID:");
		BillsIDColumn.setMinWidth(50);
		BillsIDColumn.setCellValueFactory(new PropertyValueFactory<>("actualID"));
		BillsIDColumn.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));

		QuantityColumn = new TableColumn<>("Quantity:");
		QuantityColumn.setMinWidth(50);
		QuantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));
		QuantityColumn.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
		
		PricesColumn = new TableColumn<>("Prices:");
		PricesColumn.setMinWidth(50);
		PricesColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
		PricesColumn.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
		
		TotalPricesColumn = new TableColumn<>("Total Prices:");
		TotalPricesColumn.setMinWidth(50);
		TotalPricesColumn.setCellValueFactory(new PropertyValueFactory<>("total"));
		TotalPricesColumn.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
		
		billTable.getColumns().addAll(BillsItemColumn,BillsIDColumn,QuantityColumn,PricesColumn,TotalPricesColumn);
		
        
        tfBarcode.setPromptText("Enter Barcode");
        tfBarcode.setTextFormatter(new TextFormatter<>(input -> {
		    return input.getControlNewText().matches("\\d*") ? input : null;
		}));
        tfAmount.setPromptText("Enter Amount");
        tfAmount.setTextFormatter(new TextFormatter<>(input -> {
		    return input.getControlNewText().matches("\\d*") ? input : null;
		}));
        
		tableView.setEditable(true);
		tableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_FLEX_LAST_COLUMN);
		
		ItemDiscountColumn = new TableColumn<>("Discount:");
		ItemDiscountColumn.setMinWidth(100);
		ItemDiscountColumn.setCellValueFactory(new PropertyValueFactory<>("ItemDiscount"));
		ItemDiscountColumn.setCellFactory(e -> {
		    TextFieldTableCell<Item, Double> cell =  new TextFieldTableCell<>(new DoubleStringConverter());
		    cell.setConverter(new DoubleStringConverter());
            cell.itemProperty().addListener((obs, oldVal, newVal) -> {
		        if (cell.getGraphic() instanceof TextField tf) {
		            tf.setTextFormatter(new TextFormatter<Double>(change ->
		                change.getControlNewText().matches("\\d*(\\.\\d*)?") ? change : null
		            ));
		        }
		    });

		    return cell;
		});
		
		TotalDiscountColumn = new TableColumn<>("Total Discount:");
		TotalDiscountColumn.setMinWidth(100);
		TotalDiscountColumn.setCellValueFactory(new PropertyValueFactory<>("TotalDiscount"));
		TotalDiscountColumn.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
		TotalDiscountColumn.setEditable(false);
		
		ItemTypeColumn= new TableColumn<>("Type:");
		ItemTypeColumn.setMinWidth(100);
		ItemTypeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
		ItemTypeColumn.setCellFactory(TextFieldTableCell.forTableColumn());
		
		ItemCommentColumn= new TableColumn<>("Comment:");
		ItemCommentColumn.setMinWidth(150);
		ItemCommentColumn.setCellValueFactory(new PropertyValueFactory<>("comment"));
		ItemCommentColumn.setCellFactory(TextFieldTableCell.forTableColumn());
		
		ItemNameColumn = new TableColumn<>("Article Name:");
		ItemNameColumn.setMinWidth(100);
		ItemNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
		ItemNameColumn.setCellFactory(TextFieldTableCell.forTableColumn());
		
		BarcodeColumn = new TableColumn<>("Barcode:");
		BarcodeColumn.setMinWidth(50);
		BarcodeColumn.setCellValueFactory(new PropertyValueFactory<>("barcode"));
		BarcodeColumn.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
		BarcodeColumn.setEditable(false);
		
		ItemSellPriceColumn = new TableColumn<>("Selling Price:");
		ItemSellPriceColumn.setMinWidth(100);
		ItemSellPriceColumn.setCellValueFactory(new PropertyValueFactory<>("SellingPrice"));
		ItemSellPriceColumn.setCellFactory(e -> {
		    TextFieldTableCell<Item, Double> cell =  new TextFieldTableCell<>(new DoubleStringConverter());
		    cell.setConverter(new DoubleStringConverter());
            cell.itemProperty().addListener((obs, oldVal, newVal) -> {
		        if (cell.getGraphic() instanceof TextField tf) {
		            tf.setTextFormatter(new TextFormatter<Double>(change ->
		                change.getControlNewText().matches("\\d*(\\.\\d*)?") ? change : null
		            ));
		        }
		    });

		    return cell;
		});
		
		ItemBoughtPriceColumn = new TableColumn<>("Bought Price");
		ItemBoughtPriceColumn.setMinWidth(100);
		ItemBoughtPriceColumn.setCellValueFactory(new PropertyValueFactory<>("BoughtPrice"));
		ItemBoughtPriceColumn.setCellFactory(e -> {
		    TextFieldTableCell<Item, Double> cell =  new TextFieldTableCell<>(new DoubleStringConverter());
		    cell.setConverter(new DoubleStringConverter());
            cell.itemProperty().addListener((obs, oldVal, newVal) -> {
		        if (cell.getGraphic() instanceof TextField tf) {
		            tf.setTextFormatter(new TextFormatter<Double>(change ->
		                change.getControlNewText().matches("\\d*(\\.\\d*)?") ? change : null
		            ));
		        }
		    });

		    return cell;
		});
		
		ItemStockColumn = new TableColumn<>("Current Stock");
		ItemStockColumn.setMinWidth(100);
		ItemStockColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));
		ItemStockColumn.setCellFactory(e -> {
		    TextFieldTableCell<Item, Integer> cell =  new TextFieldTableCell<>(new IntegerStringConverter());
		    cell.setConverter(new IntegerStringConverter());
            cell.itemProperty().addListener((obs, oldVal, newVal) -> {
		        if (cell.getGraphic() instanceof TextField tf) {
		            tf.setTextFormatter(new TextFormatter<Integer>(change ->
		                change.getControlNewText().matches("\\d*") ? change : null
		            ));
		        }
		    });

		    return cell;
		});
		
		CategoryColumn = new TableColumn<>("Category:");
		CategoryColumn.setCellValueFactory(new PropertyValueFactory<>("categoryName"));
		CategoryColumn.setCellFactory(ComboBoxTableCell.forTableColumn(cat));
		
		SupplierColumn = new TableColumn<>("Supplier:");
		SupplierColumn.setMinWidth(100);
		SupplierColumn.setCellValueFactory(new PropertyValueFactory<>("supplierName"));
		SupplierColumn.setCellFactory(ComboBoxTableCell.forTableColumn(sup));
		
		TotalItemSoldColumn = new TableColumn<>("Sold Stock:");
		TotalItemSoldColumn.setMinWidth(100);
		TotalItemSoldColumn.setCellValueFactory(new PropertyValueFactory<>("sold"));
		TotalItemSoldColumn.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
		TotalItemSoldColumn.setEditable(false);
		
		TotalProfitabilityColumn = new TableColumn<>("Article Profits:");
		TotalProfitabilityColumn.setMinWidth(100);
		TotalProfitabilityColumn.setCellValueFactory(new PropertyValueFactory<>("profitability"));
		TotalProfitabilityColumn.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
		TotalProfitabilityColumn.setEditable(false);
		
		DefaultProfitMarginColumn = new TableColumn<>("Default Profit Margin:");
		DefaultProfitMarginColumn.setMinWidth(100);
		DefaultProfitMarginColumn.setCellValueFactory(new PropertyValueFactory<>("JoinedProfitMargin"));
		DefaultProfitMarginColumn.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
		DefaultProfitMarginColumn.setEditable(false);
		
		
		CustomProfitMarginColumn = new TableColumn<>("Custom Profit Margin:");
		CustomProfitMarginColumn.setMinWidth(100);
		CustomProfitMarginColumn.setCellValueFactory(new PropertyValueFactory<>("CustomProfit"));
		CustomProfitMarginColumn.setCellFactory(e -> {
		    TextFieldTableCell<Item, Double> cell =  new TextFieldTableCell<>(new DoubleStringConverter());
		    cell.setConverter(new DoubleStringConverter());
            cell.itemProperty().addListener((obs, oldVal, newVal) -> {
		        if (cell.getGraphic() instanceof TextField tf) {
		            tf.setTextFormatter(new TextFormatter<Double>(change ->
		                change.getControlNewText().matches("\\d*(\\.\\d*)?") ? change : null
		            ));
		        }
		    });

		    return cell;
		});
		
		tableView.getColumns().addAll(BarcodeColumn,ItemNameColumn,ItemSellPriceColumn,ItemStockColumn,ItemTypeColumn,ItemCommentColumn,ItemDiscountColumn,TotalDiscountColumn,ItemBoughtPriceColumn,TotalItemSoldColumn,TotalProfitabilityColumn,CategoryColumn,SupplierColumn,DefaultProfitMarginColumn,CustomProfitMarginColumn);
		
		categoriesTable.setEditable(true);
		categoriesTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		categoriesTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_FLEX_LAST_COLUMN);
		
		CategoryIDColumn = new TableColumn<>("ID:");
		CategoryIDColumn.setMinWidth(50);
		CategoryIDColumn.setCellValueFactory(new PropertyValueFactory<>("iD"));
		CategoryIDColumn.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
		CategoryIDColumn.setEditable(false);
		
		CategoryNameColumn = new TableColumn<>("Category Name:");
		CategoryNameColumn.setMinWidth(100);
		CategoryNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
		CategoryNameColumn.setCellFactory(TextFieldTableCell.forTableColumn());
		
		CategoryMarginColumn = new TableColumn<>("Profit Margin:");
		CategoryMarginColumn.setMinWidth(100);
		CategoryMarginColumn.setCellValueFactory(new PropertyValueFactory<>("profit"));
		CategoryMarginColumn.setCellFactory(e -> {
		    TextFieldTableCell<Category, Double> cell =  new TextFieldTableCell<>(new DoubleStringConverter());
		    cell.setConverter(new DoubleStringConverter());
            cell.itemProperty().addListener((obs, oldVal, newVal) -> {
		        if (cell.getGraphic() instanceof TextField tf) {
		            tf.setTextFormatter(new TextFormatter<Double>(change ->
		                change.getControlNewText().matches("\\d*(\\.\\d*)?") ? change : null
		            ));
		        }
		    });

		    return cell;
		});
		
		CategoryDiscountColumn = new TableColumn<>("Discount:");
		CategoryDiscountColumn.setMinWidth(100);
		CategoryDiscountColumn.setCellValueFactory(new PropertyValueFactory<>("discount"));
		CategoryDiscountColumn.setCellFactory(e -> {
		    TextFieldTableCell<Category, Double> cell =  new TextFieldTableCell<>(new DoubleStringConverter());
		    cell.setConverter(new DoubleStringConverter());
            cell.itemProperty().addListener((obs, oldVal, newVal) -> {
		        if (cell.getGraphic() instanceof TextField tf) {
		            tf.setTextFormatter(new TextFormatter<Double>(change ->
		                change.getControlNewText().matches("\\d*(\\.\\d*)?") ? change : null
		            ));
		        }
		    });

		    return cell;
		});
		
		CategoryNumbersColumn = new TableColumn<>("Number of Articles :");
		CategoryNumbersColumn.setMinWidth(50);
		CategoryNumbersColumn.setCellValueFactory(new PropertyValueFactory<>("itemIDSize"));
		CategoryNumbersColumn.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
		CategoryNumbersColumn.setEditable(false);
		
		categoriesTable.getColumns().addAll(CategoryIDColumn,CategoryNameColumn,CategoryMarginColumn,CategoryDiscountColumn,CategoryNumbersColumn);
		
		suppliersTable.setEditable(true);
		suppliersTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		suppliersTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_FLEX_LAST_COLUMN);
		
		SupplierIDColumn = new TableColumn<>("ID:");
		SupplierIDColumn.setMinWidth(50);
		SupplierIDColumn.setCellValueFactory(new PropertyValueFactory<>("iD"));
		SupplierIDColumn.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
		SupplierIDColumn.setEditable(false);
		
		SupplierNameColumn= new TableColumn<>("Supplier Name:");
		SupplierNameColumn.setMinWidth(100);
		SupplierNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
		SupplierNameColumn.setCellFactory(TextFieldTableCell.forTableColumn());
		
		SupplierMarginColumn = new TableColumn<>("Profit Margin:");
		SupplierMarginColumn.setMinWidth(100);
		SupplierMarginColumn.setCellValueFactory(new PropertyValueFactory<>("profit"));
		SupplierMarginColumn.setCellFactory(e -> {
		    TextFieldTableCell<Supplier, Double> cell =  new TextFieldTableCell<>(new DoubleStringConverter());
		    cell.setConverter(new DoubleStringConverter());
            cell.itemProperty().addListener((obs, oldVal, newVal) -> {
		        if (cell.getGraphic() instanceof TextField tf) {
		            tf.setTextFormatter(new TextFormatter<Double>(change ->
		                change.getControlNewText().matches("\\d*(\\.\\d*)?") ? change : null
		            ));
		        }
		    });

		    return cell;
		});
		
		SupplierDiscountColumn = new TableColumn<>("Discount:");
		SupplierDiscountColumn.setMinWidth(100);
		SupplierDiscountColumn.setCellValueFactory(new PropertyValueFactory<>("discount"));
		SupplierDiscountColumn.setCellFactory(e -> {
		    TextFieldTableCell<Supplier, Double> cell =  new TextFieldTableCell<>(new DoubleStringConverter());
		    cell.setConverter(new DoubleStringConverter());
            cell.itemProperty().addListener((obs, oldVal, newVal) -> {
		        if (cell.getGraphic() instanceof TextField tf) {
		            tf.setTextFormatter(new TextFormatter<Double>(change ->
		                change.getControlNewText().matches("\\d*(\\.\\d*)?") ? change : null
		            ));
		        }
		    });

		    return cell;
		});
		
		SupplierNumbersColumn = new TableColumn<>("Number of Articles :");
		SupplierNumbersColumn.setMinWidth(50);
		SupplierNumbersColumn.setCellValueFactory(new PropertyValueFactory<>("itemIDSize"));
		SupplierNumbersColumn.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
		SupplierNumbersColumn.setEditable(false);
		
		SupplierContactColumn= new TableColumn<>("Email:");
		SupplierContactColumn.setMinWidth(100);
		SupplierContactColumn.setCellValueFactory(new PropertyValueFactory<>("contact"));
		SupplierContactColumn.setCellFactory(TextFieldTableCell.forTableColumn());
		
		suppliersTable.getColumns().addAll(SupplierIDColumn,SupplierNameColumn,SupplierMarginColumn,SupplierDiscountColumn,SupplierNumbersColumn, SupplierContactColumn);
		
		sectorsTable.setEditable(true);
		sectorsTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		sectorsTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_FLEX_LAST_COLUMN);
		
		SectorsNameColumn= new TableColumn<>("Names:");
		SectorsNameColumn.setMinWidth(100);
		SectorsNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
		SectorsNameColumn.setCellFactory(TextFieldTableCell.forTableColumn());
		
		SectorsCategories = new TableColumn<>("Categories");
		SectorsCategories.setMinWidth(120);
        SectorsCategories.setCellFactory(col -> new TableCell<Sectors, String>() {
            private final Button btn = new Button("Edit Categories");
        protected void updateItem(String item, boolean empty) {
		        super.updateItem(item, empty);
		        if (empty || getTableRow() == null || getTableRow().getItem() == null) {
		            setGraphic(null);
		            return;
		        }
		        Sectors sector = getTableRow().getItem();

		        btn.setOnAction(e -> {
		        	EditCategories(sector);
		        });
		        setGraphic(btn);
		        setText(null);
		    }
		});
		
        SectorsUserNames = new TableColumn<>("Employes");
        SectorsUserNames.setMinWidth(120);
        SectorsUserNames.setCellFactory(col -> new TableCell<Sectors, String>() {
        private final Button btn = new Button("Edit Employes");
        protected void updateItem(String item, boolean empty) {
		        super.updateItem(item, empty);
		        if (empty || getTableRow() == null || getTableRow().getItem() == null) {
		            setGraphic(null);
		            return;
		        }
		        Sectors sector = getTableRow().getItem();
                btn.setOnAction(e -> {
                        EditEmployes(sector);
		        });
		        setGraphic(btn);
		        setText(null);
		    }
		});
        
        sectorsTable.getColumns().addAll(SectorsNameColumn,SectorsUserNames,SectorsCategories);
        
        MenuBar menuBar = new MenuBar();
		Menu menu = new Menu("Menu");
		Menu storage= new Menu("Storage Views");
		Menu recipets= new Menu("Receipts");
		MenuItem recipetItem= new MenuItem("Receipts UI");
		recipetItem.setOnAction(e->  showRecipetUI());
		MenuItem itemsItem = new MenuItem("Articles Table:");
		itemsItem.setOnAction(e -> showItemScene());
		MenuItem categoryItem = new MenuItem("Categories Table:");
		categoryItem.setOnAction(e -> showCategoryScene());
		MenuItem supplierItem = new MenuItem("Suppliers Table:");
		supplierItem.setOnAction(e -> showSupplierScene());
		MenuItem sectorsItem = new MenuItem("Sectors Table:");
		sectorsItem.setOnAction(e -> showSectorsScene());
		MenuItem exitItem = new MenuItem("Exit Program");
		MenuItem RecieptTbl = new MenuItem("Reciepts Table");
		RecieptTbl.setOnAction(e ->showRecieptTable());
		exitItem.setOnAction(e -> System.exit(0));
		recipets.getItems().addAll(recipetItem,RecieptTbl);
		storage.getItems().addAll(itemsItem, categoryItem,supplierItem,sectorsItem);
		menu.getItems().addAll(Save,Load,exitItem);
		menuBar.getMenus().addAll(menu,storage,recipets);
		
		HBox sectorBox= new HBox(10);
		sectorBox.setPadding(new Insets(10));
		TFSectorName.setPromptText("Enter sectors name");
		sectorBox.getChildren().addAll(TFSectorName,btnSectorAdd,btnSectorDelete);
		
		
		sectorbottomBox= new VBox(5);
		sectorbottomBox.getChildren().addAll(sectorBox);
		
		HBox buttonBox = new HBox(10);
		buttonBox.setPadding(new Insets(10));
		buttonBox.getChildren().addAll(btnAdd, btnDelete, btnAutoPrices,btnCustomPrices);
		
		HBox categorybox = new HBox(10);
		categorybox.setPadding(new Insets(10));
		categorybox.getChildren().addAll(btnCategoryAdd,btnCategoryDelete);
		
		HBox supplierbox = new HBox(10);
		supplierbox.setPadding(new Insets(10));
		supplierbox.getChildren().addAll(btnSupplierAdd,btnSupplierDelete);
		
		
		VBox topBox= new VBox(5);
		topBox.setPadding(new Insets(10));
		topBox.getChildren().addAll(menuBar);
		
		bottomBox = new VBox(5);
		bottomBox.getChildren().addAll(buttonBox);
		
		billsbottom= new VBox(5);
		
		supplierbottomBox = new VBox(5);
		supplierbottomBox.getChildren().addAll(supplierbox);
		
		categorybottomBox = new VBox(5);
		categorybottomBox.getChildren().addAll(categorybox);
		
		this.setPadding(new Insets(15));
		this.setTop(topBox);
		this.setCenter(tableView);
		this.setBottom(bottomBox);
	}
	public Button getCategoryAdd() {
		return btnCategoryAdd;
	}
	public Button getCategoryDelete() {
		return btnCategoryDelete;
	}
	public Button getSupplierAdd() {
		return btnSupplierAdd;
	}
	public Button getSupplierDelete() {
		return btnSupplierDelete;
	}
	public TableColumn<Category,Double> getCategoryDiscountColumn() {
		return CategoryDiscountColumn;
	}
	public TableColumn<Supplier,Double> getSupplierDiscountColumn() {
		return SupplierDiscountColumn;
	}
	public TableColumn<Category,String> getCategoryNameColumn() {
		return CategoryNameColumn;
	}
	public TableColumn<Category,Integer> getCategoryIDColumn() {
		return CategoryIDColumn;
	}
	public TableColumn<Category,Double> getCategoryMarginColumn() {
		return CategoryMarginColumn;
	}
	public TableColumn<Category,Integer> getCategoryNumbersColumn() {
		return CategoryNumbersColumn;
	}
	public TableColumn<Supplier,String> getSupplierNameColumn() {
		return SupplierNameColumn;
	}
	public TableColumn<Supplier,Integer> getSupplierIDColumn() {
		return SupplierIDColumn;
	}
	public TableColumn<Supplier,Double> getSupplierMarginColumn() {
		return SupplierMarginColumn;
	}
	public TableColumn<Supplier,Integer> getSupplierNumbersColumn() {
		return SupplierNumbersColumn;
	}
	public TableColumn<Supplier,String> getSupplierContactColumn() {
		return SupplierContactColumn;
	}
	public Button getbtnCustomPrices() {
		return btnCustomPrices;
	}
	public TableColumn<Item, Double> getItemDiscountColumn() {
		return ItemDiscountColumn;
	}
	public TableColumn<Item, Double> getTotalDiscountColumn() {
		return TotalDiscountColumn;
	}
	public TableColumn<Item, String> getItemTypeColumn() {
		return ItemTypeColumn;
	}
	public TableColumn<Item, String> getItemCommentColumn() {
		return ItemCommentColumn;
	}
	public TableColumn<Item, String> getItemNameColumn() {
		return ItemNameColumn;
	}

	public TableColumn<Item, Integer> getBarcodeColumn() {
		return BarcodeColumn;
	}
	public Button getRecipetCancel() {
		return btnRecieptCancel;
	}
	public TableView<Bills> getBillsTable(){
		return billTable;
	}

	public TableColumn<Item, Double> getItemSellPriceColumn() {
		return ItemSellPriceColumn;
	}

	public TableColumn<Item, Double> getItemBoughtPriceColumn() {
		return ItemBoughtPriceColumn;
	}
	public TableColumn<Item, Integer> getItemStockColumn() {
		return ItemStockColumn;
	}
	public TableColumn<Item, String> getCategoryColumn() {
		return CategoryColumn;
	}
	public TableColumn<Item, String> getSupplierColumn() {
		return SupplierColumn;
	}
	public TableColumn<Item, Integer> getTotalItemSoldColumn() {
		return TotalItemSoldColumn;
	}
	public TableColumn<Item, Double> getTotalProfitabilityColumn() {
		return TotalProfitabilityColumn;
	}
	public TableColumn<Item, Double> getDefaultProfitMarginColumn() {
		return DefaultProfitMarginColumn;
	}
	public TableColumn<Item, Double> getCustomProfitMarginColumn() {
		return CustomProfitMarginColumn;
	}
	public TableView<Item> getTableView() {
		return tableView;
	}
	public TableView<Category> getCategoryTable(){
		return categoriesTable;
	}
	public TableView<Supplier> getSupplierTable(){
		return suppliersTable;
	}
    public Button getBtnAdd() {
		return btnAdd;
	}
    public Button getBtnDelete() {
		return btnDelete;
	}
	public MenuItem getSaveBar() {
		return Save;
	}
	public MenuItem getLoadBar() {
		return Load;
	}

	public Button getBtnAutoPrices() {
		return btnAutoPrices;
	}
	public Button getBtnSectorDelete() {
		return btnSectorDelete;
	}
	public void getCategoriesList(ObservableList<String> cat) {
		this.cat.clear();
		 for(int i=0;i<cat.size();i++) {
			this.cat.add(cat.get(i));
		 }
	
		 this.cat.add("Create a new category:");
	}
	public void getSuppliersList(ObservableList<String> sup) {
		this.sup.clear();
		 for(int i=0;i<sup.size();i++) {
			this.sup.add(sup.get(i));
		 }
		 this.sup.add("Create a new supplier:");
		 
	}
	public void getUsersList(ObservableList<String> user) {
		this.users.clear();
		 for(int i=0;i<user.size();i++) {
			this.users.add(user.get(i));
			if(!leftusers.contains(users.get(i))){
				leftusers.add(users.get(i));
			}
		 }
		 for(int i=0;i<leftusers.size();i++) {
			 if(!users.contains(leftusers.get(i)));
			 leftusers.remove(leftusers.get(i));
		 }
	}
	private void showItemScene() {
		this.setCenter(tableView);
		this.setBottom(bottomBox);
	}
	private void showRecieptTable() {
		this.setCenter(billTable);
		this.setBottom(billsbottom);
	}
	public void showRecipetUI() {
        setCenter(receiptUI);
        HBox button= new HBox(10,new Label("Barcode:"),tfBarcode,new Label("Amount:"),tfAmount,btnRecieptAdd,btnRecieptCancel,btnPrint);
        setBottom(button);
        
    }
	public ReceiptUI getReceiptUI() {
		return receiptUI;
	}
	public void RefreshReceiptUI() {
		receiptUI=new ReceiptUI();
		showRecipetUI();
	}
	public Button getRecipetButton() {
		return btnPrint;
	}
	public Button getAddRecipetButton() {
		return btnRecieptAdd;
	}
    public TextField getRecieptBarcode() {
    	return tfBarcode;
    }
    public TextField getRecieptAmount() {
    	return tfAmount;
    }
	private void showCategoryScene() {
		this.setCenter(categoriesTable);
		this.setBottom(categorybottomBox);
	}
	private void showSupplierScene() {
		this.setCenter(suppliersTable);
		this.setBottom(supplierbottomBox);
		
	}
	private void showSectorsScene() {
		this.setCenter(sectorsTable);
		this.setBottom(sectorbottomBox);
		
	}
	public TableColumn<Sectors,String> getSectorsNameColumn() {
		return SectorsNameColumn;
	}
	public Button getSectorAdd() {
		return btnSectorAdd;
	}
	public TextField getSectorTextField() {
		return TFSectorName;
	}
	public TableView<Sectors> getSectorsTable() {
		return sectorsTable;
	}
	
	private void FilterCategories() {
		 ArrayList<Boolean>Check=new ArrayList<>();
			for(int i=0;i<cat.size();i++) {
					for(int j=0;j<sectorsTable.getItems().size();j++) {
					if(!sectorsTable.getItems().get(j).getCategories().contains(cat.get(i))) {
						Check.add(true);
					}else{
						Check.add(false);
					}
					}
					if(!Check.contains(false)) {
						leftcat.add(cat.get(i));
						
					}
					Check.clear();
     }
 }
	private void FilterUsers(ObservableList<String> taken) {
		if(leftusers.size()==0) {
			for(int i=0;i<users.size();i++) {
				if(!leftusers.contains(users.get(i))) {
					if(!taken.contains(users.get(i))) {
						leftusers.add(users.get(i));
					}
					
				}
			}
		}else {
			for(int i=0;i<users.size();i++) {
					if(!taken.contains(users.get(i))) {
						leftusers.add(users.get(i));
		}
     }}
	}
	private void EditCategories(Sectors sector) {
		Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Select Categories");
        ObservableList<String> allCategories = FXCollections.observableArrayList();
        FilterCategories();
        
        for(int i=0;i<sector.getCategoriesSize();i++) {
        	if(!cat.contains(sector.getCategories().get(i))){
        		sector.getCategories().remove(sector.getCategories().get(i));
        	}
        }
        for(int i=0;i<sector.getCategories().size();i++) {
        	allCategories.add(sector.getCategories().get(i));
        }
        for(int i=0;i<leftcat.size();i++) {
        		allCategories.add(leftcat.get(i));
        }
        allCategories.remove("Create a new category:");
        
        VBox box = new VBox(8);
        box.setPadding(new Insets(10));
        for (String cat : allCategories) {
            CheckBox cb = new CheckBox(cat);
            cb.setSelected(sector.getCategories().contains(cat));
            cb.setOnAction(e -> {
                if (cb.isSelected()) {
                       sector.getCategories().add(cat);
                        leftcat.remove(cat);
                } else {
                    sector.getCategories().remove(cat);
                    if(!leftcat.contains(cat))
                    leftcat.add(cat);
                }
            });

            box.getChildren().add(cb);
        }

        Button close = new Button("Done");
        close.setOnAction(e ->{
        leftcat.clear();
        stage.close();
        });

        box.getChildren().add(close);

        stage.setScene(new Scene(box, 250, 250));
        stage.showAndWait();
        
	}
	private void EditEmployes(Sectors sector) {
		Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Select Employees:");
        ObservableList<String> allCategories = FXCollections.observableArrayList();
        FilterUsers(sector.getUserNames());
        
        for(int i=0;i<sector.getUserNames().size();i++) {
        	allCategories.add(sector.getUserNames().get(i));
        }
        for(int i=0;i<leftusers.size();i++) {
        	if(!allCategories.contains(leftusers.get(i))) {
        		allCategories.add(leftusers.get(i));
        	}
        }
        
        VBox box = new VBox(8);
        box.setPadding(new Insets(10));
        for (String cat : allCategories) {
            CheckBox cb = new CheckBox(cat);
            cb.setSelected(sector.getCategories().contains(cat));
            cb.setOnAction(e -> {
                if (cb.isSelected()) {
                       sector.getCategories().add(cat);
                       leftusers.remove(cat);
                } else {
                    sector.getCategories().remove(cat);
                    leftusers.add(cat);
                }
            });

            box.getChildren().add(cb);
        }

        Button close = new Button("Done");
        close.setOnAction(e -> stage.close());

        box.getChildren().add(close);

        stage.setScene(new Scene(box, 250, 250));
        stage.show();
	}
}

