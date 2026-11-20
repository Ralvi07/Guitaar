// Holds the collection of bill records used by the Java application's data layer.
package DAOS;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.List;
import Model.Bills;
import Model.Category;
import Model.Item;
import Model.Sectors;
import Model.Supplier;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class BillItem {
 private ObservableList<Item> item= FXCollections.observableArrayList();
 private ObservableList<Category> cat= FXCollections.observableArrayList();
 private ObservableList<Supplier> sup=  FXCollections.observableArrayList();
 private ObservableList<Sectors> sec= FXCollections.observableArrayList();
 private ObservableList<Bills> Bills= FXCollections.observableArrayList();
BillItem(){
	
}
public boolean ChangeCategoryName(String name, Category cat) {
	if(!CheckIfCategoryNameExists(name)) {
		CheckifSectorHasCategory(name, cat.getName());
		return true;
	}
	return false;
}
private void CheckifSectorHasCategory(String name, String newName) {
	for(int i=0;i<sec.size();i++) {
		if(sec.get(i).getCategories().contains(name)) {
			for(int j=0;j<sec.get(j).getCategoriesSize();j++) {
				if(sec.get(i).getCategories().get(j).equals(name)) {
					sec.get(i).getCategories().remove(j);
					sec.get(i).getCategories().add(j, newName);
					return;
				}
			}
		}
	}
}

public ObservableList<Bills> getBills(){
	return Bills;
}
public void addBill(Bills bill) {
	Bills.add(bill);
	Item item =getItemFromName(bill.getName());
	item.addSold(bill.getQuantity());
}
public Item getItemFromName(String name) {
	for(int i=0;i<item.size();i++) {
		if(name.equals(item.get(i).getName())) {
			return item.get(i);
		}
	}
	return null;
}
public boolean DeleteSectors(List<Sectors> sectors) {
	for(int i=0;i<sectors.size();i++) {
		if(sectors.get(i).getCategoriesSize()>0) {
			return false;
		 }
		if(sectors.get(i).getUserNamesSize()>0) {
		   return false;
		}
		
			
			
	}for(int i=0;i<sectors.size();i++) {
			this.sec.remove(sectors.get(i));
			
	}
		return true;
}
public ObservableList<Sectors> getSectors(){
	return sec;
}
public void AddSector(Sectors sector) {
	sec.add(sector);
}
public boolean CheckIfSectorExists(String string) {
	for(int i=0;i<sec.size();i++) {
		if(sec.get(i).getName().equals(string)) {
			return true;
		}
	}
	return false;
}
public void UpdateItemsCategory(Category cat) {
	for(int i=0;i<item.size();i++) {
		if(cat.getItemIDS().contains(item.get(i).getBarcode())) {
			ChangeItemCategory(cat,item.get(i));
		}
	}
}
public void UpdateItemsSupplier(Supplier sup) {
	for(int i=0;i<item.size();i++) {
		if(sup.getItemIDS().contains(item.get(i).getBarcode())) {
			ChangeItemSupplier(sup,item.get(i));
		}
	}
}
public void deleteItems(List<Item> item) {
	for(int i=0;i<item.size();i++) {
		deleteItem(item.get(i));
	}
}
private void deleteItem(Item item) {
	removeItemBarcodes(item);
	this.item.remove(item);
}
public boolean deleteCategory(List<Category> cat) {
	boolean check=false;
	for(int i=0;i<cat.size();i++) {
		if(cat.get(i).getItemIDS().size()!=0) {
			return check;
		}
			
			
	}
	check=true;
	if(check) {
		for(int i=0;i<cat.size();i++) {
			this.cat.remove(cat.get(i));
			
	}
		return true;
	}
    return false;
}
public boolean deleteSupplier(List<Supplier> cat) {
	boolean check=false;
	for(int i=0;i<cat.size();i++) {
		if(cat.get(i).getItemIDS().size()!=0) {
			return check;
		}
			
			
	}
	check=true;
	if(check) {
		for(int i=0;i<cat.size();i++) {
			this.sup.remove(cat.get(i));
			
	}
		return true;
	}
    return false;
}
private void removeItemBarcodes(Item item) {
	for(int i=0;i<cat.size();i++) {
		if(cat.get(i).checkBarcode(item.getBarcode())) {
			cat.get(i).removeBarcode(item.getBarcode());
			break;
		}
	}
	for(int i=0;i<sup.size();i++) {
		if(sup.get(i).checkBarcode(item.getBarcode())) {
			sup.get(i).removeBarcode(item.getBarcode());
			break;
		}
	}
}
public ObservableList<Item> getItems(){
	return item;
}
public ObservableList<Category> getCategories(){
	return cat;
}
public ObservableList<Supplier> getSuppliers(){
	return sup;
}
public void AddItem(Item item) {
	this.item.add(item);
}
public void setAutoPricesList(List<Item> item) {
	for(int i=0;i<item.size();i++) {
		setAutoPrices(item.get(i));
	}
}
public void setCustomPricesList(List<Item> item) {
	for(int i=0;i<item.size();i++) {
		setCustomPrices(item.get(i));
	}
}
private void setAutoPrices(Item item) {
     item.setPrice(item.calculateDefaultPrice());
}
private void setCustomPrices(Item item) {
		item.setPrice(item.calculateCustomPrice());
}
private void CompleteItemCategory(Item item, Category cat) {
	item.setCategoryName(cat.getName());
	item.setCategoryProfitMargin(cat.getProfit());
	item.setCategoryDiscount(cat.getDiscount());
	cat.addBarcode(item.getBarcode());
}
public ObservableList<String> getSuppliersName(){
	 ObservableList<String> sup= FXCollections.observableArrayList();
	 for(int i=0;i<this.sup.size();i++) {
		 sup.add(this.sup.get(i).getName());
	 }
	 return sup;
}
public ObservableList<String> getCategoriesName(){
	 ObservableList<String> cat= FXCollections.observableArrayList();
	 for(int i=0;i<this.cat.size();i++) {
		 cat.add(this.cat.get(i).getName());
	 }
	 return cat;
}
public void ChangeItemCategory(String name,Item item) {
	for(int i=0;i<cat.size();i++) {
		if(cat.get(i).checkBarcode(item.getBarcode())) {
			cat.get(i).removeBarcode(item.getBarcode());
		}
		if(name.equals(cat.get(i).getName())) {
			CompleteItemCategory(item,cat.get(i));
		}
	}
}
private void ChangeItemCategory(Category name,Item item) {
	for(int i=0;i<cat.size();i++) {
		if(cat.get(i).checkBarcode(item.getBarcode())) {
			cat.get(i).removeBarcode(item.getBarcode());
		}
	  CompleteItemCategory(item,name);
		}
}
public void ChangeItemSupplier(String name,Item item) {
	for(int i=0;i<sup.size();i++) {
		if(sup.get(i).checkBarcode(item.getBarcode())) {
			sup.get(i).removeBarcode(item.getBarcode());
		}
		if(name.equals(sup.get(i).getName())) {
			CompleteItemSupplier(item,sup.get(i));
		}
	}
}
private void ChangeItemSupplier(Supplier name,Item item) {
	for(int i=0;i<sup.size();i++) {
		if(sup.get(i).checkBarcode(item.getBarcode())) {
			sup.get(i).removeBarcode(item.getBarcode());
		}
			CompleteItemSupplier(item,name);
		}
}
private void CompleteItemSupplier(Item item,Supplier sup) {
	item.setSupplierName(sup.getName());
	item.setSupplierProfitMargin(sup.getProfit());
	item.setSupplierDiscount(sup.getDiscount());
	sup.addBarcode(item.getBarcode());
}
public Item getItemFromBarcode(int id) {
	for(int i=0;i<item.size();i++) {
		if(item.get(i).getBarcode()==id) {
			return item.get(i);
		}
	}
	return null;
}
public boolean CheckIfBarcodeExists(int id) {
	for(int i=0;i<item.size();i++) {
		if(item.get(i).getBarcode()==id) {
			return true;
		}
	}
	return false;
}
public boolean CheckIfNameExists(String id) {
	for(int i=0;i<item.size();i++) {
		if(item.get(i).getName().equals(id)) {
			return true;
		}
	}
	return false;
}
public boolean CheckIfCategoryNameExists(String name){
	for(int i=0;i<cat.size();i++) {
		if(cat.get(i).getName().equals(name)) {
			return true;
		}
	}
	return false;
}
public boolean CheckIfSupplierNameExists(String name){
	for(int i=0;i<sup.size();i++) {
		if(sup.get(i).getName().equals(name)) {
			return true;
		}
	}
	return false;
}
public Category getCategoryFromName(String name) {
	for(int i=0;i<cat.size();i++) {
		if(cat.get(i).getName().equals(name)) {
			return cat.get(i);
		}
	}
	return null;
}
public Supplier getSupplierFromName(String name) {
	for(int i=0;i<sup.size();i++) {
		if(sup.get(i).getName().equals(name)) {
			return sup.get(i);
		}
	}
	return null;
}
public void CreateNewCategory(String name, double profit) {
	cat.add(new Category(name,profit));
	
}
public void CreateNewSupplier(String name, double profit ,String contact) {
	sup.add(new Supplier(name, contact, profit));
}
public void CreateNewCateogryPopup(String name, double profit, Item item) {
	CreateNewCategory(name,profit);
	ChangeItemCategory(name,item);
}
public void CreateNewSupplierPopup(String name, double profit, String contact, Item item) {
	CreateNewSupplier(name,profit,contact);
	ChangeItemSupplier(name,item);
}
private void WriteItemArray(DataOutputStream outputStream) throws IOException {
	outputStream.writeInt(item.size());
	for(int i=0;i<item.size();i++) {
		item.get(i).WriteData(outputStream);
	}
}
private void WriteCategoryArray(DataOutputStream outputStream) throws IOException {
	outputStream.writeInt(cat.size());
	for(int i=0;i<cat.size();i++) {
		cat.get(i).WriteData(outputStream);
	}
}
private void WriteSupplierArray(DataOutputStream outputStream) throws IOException {
	outputStream.writeInt(sup.size());
	for(int i=0;i<sup.size();i++) {
		sup.get(i).WriteData(outputStream);
	}
}
private void WriteSectorsArray(DataOutputStream outputStream) throws IOException {
	outputStream.writeInt(sec.size());
	for(int i=0;i<sec.size();i++) {
		sec.get(i).WriteData(outputStream);
	}
}
private void WriteBillsArray(DataOutputStream outputStream) throws IOException {
	outputStream.writeInt(Bills.size());
	for(int i=0;i<Bills.size();i++) {
		Bills.get(i).WriteData(outputStream);
	}
}
public void WriteData(DataOutputStream outputStream) throws IOException {
	WriteItemArray(outputStream);
	WriteCategoryArray(outputStream);
	WriteSupplierArray(outputStream);
	WriteSectorsArray(outputStream);
	WriteBillsArray(outputStream);
}
private void ReadItemArray(DataInputStream inputStream) throws IOException {
	item.clear();
    int index=inputStream.readInt();
	for(int i=0;i<index;i++) {
		Item items= new Item();
		items.ReadData(inputStream);
		item.add(items);
	}
}
private void ReadCategoryArray(DataInputStream inputStream) throws IOException, ClassNotFoundException {
	cat.clear();
    int index=inputStream.readInt();
	for(int i=0;i<index;i++) {
		Category items= new Category();
		items.ReadData(inputStream);
		cat.add(items);
	}
	
}
private void ReadSupplierArray(DataInputStream inputStream) throws IOException, ClassNotFoundException {
	sup.clear();
    int index=inputStream.readInt();
	for(int i=0;i<index;i++) {
		Supplier items= new Supplier();
		items.ReadData(inputStream);
		sup.add(items);
	}
}
private void ReadSectorsArray(DataInputStream inputStream) throws IOException, ClassNotFoundException {
	sec.clear();
    int index=inputStream.readInt();
	for(int i=0;i<index;i++) {
		Sectors items= new Sectors();
		items.ReadData(inputStream);
		sec.add(items);
	}
}
private void ReadBillsArray(DataInputStream inputStream) throws IOException, ClassNotFoundException {
	Bills.clear();
    int index=inputStream.readInt();
	for(int i=0;i<index;i++) {
		Bills items= new Bills();
		items.ReadData(inputStream);
		Bills.add(items);
	}
}
public void ReadData(DataInputStream inputStream) throws IOException, ClassNotFoundException	{
	ReadItemArray(inputStream);
	ReadCategoryArray(inputStream);
	ReadSupplierArray(inputStream);
	ReadSectorsArray(inputStream);
	ReadBillsArray(inputStream);
	
}
}

