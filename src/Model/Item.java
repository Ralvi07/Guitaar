// Represents a product in the inventory.
package Model;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Item {
private transient IntegerProperty id=new SimpleIntegerProperty(0);
private transient StringProperty name= new SimpleStringProperty("NULL");
private transient StringProperty comment= new SimpleStringProperty("NULL");
private transient DoubleProperty boughtprice= new SimpleDoubleProperty(0);
private transient DoubleProperty SellingPrice=new SimpleDoubleProperty(0);
private transient IntegerProperty quantity= new SimpleIntegerProperty(0);
private transient DoubleProperty customprofit=new SimpleDoubleProperty(0);
private transient StringProperty type= new SimpleStringProperty("NULL");
private transient IntegerProperty sold=new SimpleIntegerProperty(0);
private transient DoubleProperty Profitability=new SimpleDoubleProperty(0);
private transient StringProperty Categoryname=new SimpleStringProperty("NULL");
private transient DoubleProperty CategoryProfitMargin= new SimpleDoubleProperty(0);;
private transient StringProperty SupplierName=new SimpleStringProperty("NULL");;
private transient StringProperty SupplierContact=new SimpleStringProperty("NULL"); ;
private transient DoubleProperty SupplierProfitMargin=new SimpleDoubleProperty(0);;
private transient DoubleProperty JoinedProfitMargin=new SimpleDoubleProperty(0);
private transient DoubleProperty ItemDiscount= new SimpleDoubleProperty(0);
private transient DoubleProperty SupplierDiscount= new SimpleDoubleProperty(0);
private transient DoubleProperty CategoryDiscount= new SimpleDoubleProperty(0);
private transient DoubleProperty TotalDiscount= new SimpleDoubleProperty(0);

public Item(int id, String name, String type,double boughtprice, int quantity,String comment){
	this.boughtprice=new SimpleDoubleProperty(boughtprice);
	this.id=new SimpleIntegerProperty(id);
	this.name=new SimpleStringProperty(name);
	this.quantity=new SimpleIntegerProperty(quantity);
	this.comment=new SimpleStringProperty(comment);
	this.type=new SimpleStringProperty(type);
}
public Item(){
	
}
public void setPrice(double nr) {
	SellingPrice.set(nr);
}
public double calculateDefaultPrice() {
	return (((boughtprice.get()+(boughtprice.get()*JoinedProfitMargin.get()/100))*(1-getTotalDiscount()/100)*100))/100;
}
public double calculateCustomPrice() {
	return (((boughtprice.get()+(boughtprice.get()*customprofit.get()/100))*(1-getTotalDiscount()/100)*100))/100;
}
public void setItemDiscount(double nr) {
	ItemDiscount.set(nr);
	CalculateTotalDiscount();
}
public double getItemDiscount() {
	return ItemDiscount.get();
}
public void setSupplierDiscount(double nr) {
	SupplierDiscount.set(nr);
	CalculateTotalDiscount();
}
public double getSupplierDiscount() {
	return SupplierDiscount.get();
}
public void setCategoryDiscount(double nr) {
	CategoryDiscount.set(nr);
	CalculateTotalDiscount();
}
public double getCategoryDiscount() {
	return CategoryDiscount.get();
}
public void  setTotalDiscount(double nr) {
	 TotalDiscount.set(nr);
}
public double getTotalDiscount() {
	return TotalDiscount.get();
}
public void CalculateTotalDiscount() {
	setTotalDiscount(getItemDiscount()+getSupplierDiscount()+getCategoryDiscount());
	CalculateSellingPrice();
}
public void setCategoryName(String name) {
   Categoryname.set(name);
}
public void setSupplierName(String sup) {
	SupplierName.set(sup);
}
public void setCategoryProfitMargin(double nr) {
	CategoryProfitMargin.set(nr);
	CalculateJointProfitMargin();
}
public void setSupplierContact(String name) {
	SupplierContact.set(name);
}
public void setSupplierProfitMargin(double profit) {
	SupplierProfitMargin.set(profit);
	CalculateJointProfitMargin();
}
public void CalculateJointProfitMargin() {
	JoinedProfitMargin.set(getSupplierProfitMargin()+getCategoryProfitMargin());
	CalculateSellingPrice();
}
public double getSupplierProfitMargin() {
	return SupplierProfitMargin.get();
	
}
public double getCategoryProfitMargin() {
	return CategoryProfitMargin.get();
}
public void CalculateProfitability() {
	Profitability.set((getSellingPrice()-getBoughtPrice())*getSold());
}
public String getCategoryName() {
	return Categoryname.get();
}
public String getSupplierName() {
	return SupplierName.get();
}
public double getProfitability() {
	return Profitability.get();
}
public double getJoinedProfitMargin() {
	return JoinedProfitMargin.get();
}
public void setComment(String comment) {
	this.comment.set(comment);
}
public String getComment(){
	return comment.get();
}
public void setBarcode(int id) {
	this.id.set(id);
}
public 	int getBarcode() {
	return id.get();
}
public void setName(String name) {
	this.name.set(name);
}
public String getName() {
	return name.get();
}
public void setBoughtPrice(double price) {
	boughtprice.set(price);
	CalculateSellingPrice();
}
public double getBoughtPrice() {
	return boughtprice.get();
}
public void setSellingPrice(double price) {
	SellingPrice.set(price);
	if(price*(1-(getTotalDiscount()/100))!=0 && price*(1-(getTotalDiscount()/100))>0) {
		price=price*(1+(getTotalDiscount()/100));
		setCustomPrivateProfit((((price-boughtprice.get()/boughtprice.get()))));
	}else {
		setCustomPrivateProfit(0);
	}
	
}
public double getSellingPrice() {
	return SellingPrice.get();
}
public void setQuantity(int nr) {
	quantity.set(nr);
}
public void addQuantity(int nr) {
	quantity.add(nr);
}
public int getQuantity() {
	return quantity.get();
}
public void setCustomProfit(double profit) {
	customprofit=new SimpleDoubleProperty(profit);
	CalculateSellingPrice();
}
private void setCustomPrivateProfit(double profit) {
	customprofit=new SimpleDoubleProperty(profit);
}
public double getCustomProfit() {
	return customprofit.get();
}
public int getSold() {
	return sold.get();
}
public void addSold(int nr) {
	sold.set(sold.get()+nr);
	quantity.set(quantity.get()-nr);
	CalculateProfitability();
}
public void setSold(int nr) {
	sold.set(nr);
	CalculateProfitability();
}
public void setType(String type) {
	this.type.set(type);
}
public String getType() {
	return type.get();
}
public double getProfits() {
	return (getSellingPrice()-getBoughtPrice())*sold.get();
}
public String getContact() {
	return SupplierContact.get();
}
private void CalculateSellingPrice() {
	if(customprofit.get()==0) {
		SellingPrice.set((((boughtprice.get()+(boughtprice.get()*JoinedProfitMargin.get()/100))*(1-getTotalDiscount()/100))));
	}else {
		SellingPrice.set((((boughtprice.get()+(boughtprice.get()*customprofit.get()/100))*(1-getTotalDiscount()/100))));
	}
}
public void setProfitability(double nr) {
	Profitability.set(nr);
}
public void setJoinedProfitMargin(double nr) {
	JoinedProfitMargin.set(nr);
}
public void WriteData(DataOutputStream outputStream) throws IOException {
	outputStream.writeInt(getBarcode());
	outputStream.writeUTF(getName());
	outputStream.writeUTF(getComment());
	outputStream.writeDouble(getBoughtPrice());
	outputStream.writeDouble(getSellingPrice());
	outputStream.writeInt(getQuantity());
	outputStream.writeDouble(getCustomProfit());
	outputStream.writeUTF(getType());
	outputStream.writeInt(getSold());
	outputStream.writeDouble(getProfitability());
	outputStream.writeUTF(getCategoryName());
	outputStream.writeDouble(getCategoryProfitMargin());
	outputStream.writeUTF(getSupplierName());
	outputStream.writeUTF(getContact());
	outputStream.writeDouble(getSupplierProfitMargin());
	outputStream.writeDouble(getJoinedProfitMargin());
	outputStream.writeDouble(getItemDiscount());
	outputStream.writeDouble(getSupplierDiscount());
	outputStream.writeDouble(getCategoryDiscount());
}
public void ReadData(DataInputStream inputStream) throws IOException {
	setBarcode(inputStream.readInt());
	setName(inputStream.readUTF());
	setComment(inputStream.readUTF());
	setBoughtPrice(inputStream.readDouble());
	setSellingPrice(inputStream.readDouble());
	setQuantity(inputStream.readInt());
	setCustomProfit(inputStream.readDouble());
	setType(inputStream.readUTF());
	setSold(inputStream.readInt());
	setProfitability(inputStream.readDouble());
	setCategoryName(inputStream.readUTF());
	setCategoryProfitMargin(inputStream.readDouble());
	setSupplierName(inputStream.readUTF());
	setSupplierContact(inputStream.readUTF());
	setSupplierProfitMargin(inputStream.readDouble());
	setJoinedProfitMargin(inputStream.readDouble());
	setItemDiscount(inputStream.readDouble());
	setSupplierDiscount(inputStream.readDouble());
	setCategoryDiscount(inputStream.readDouble());
}
}

