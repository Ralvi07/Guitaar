// Represents an inventory category.
package Model;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;


import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;



public class Category {
private static transient IntegerProperty ids=new SimpleIntegerProperty(0);
private transient IntegerProperty id=new SimpleIntegerProperty(0);
private transient StringProperty name= new SimpleStringProperty("NULL");
private transient DoubleProperty profitmargin= new SimpleDoubleProperty(0);
private transient DoubleProperty discount=new SimpleDoubleProperty(0);
private ArrayList<Integer> itemids= new ArrayList<>();
public Category(String name,double profitmargin){
	this.name=new SimpleStringProperty(name);
	this.profitmargin=new SimpleDoubleProperty(profitmargin);
	id= new SimpleIntegerProperty(ids.get());
	ids.set(ids.get()+1);
}
public Category(){
	
}
public ArrayList<Integer> getItemIDS() {
	return itemids;
}
public int getItemIDSize() {
	return itemids.size();
}
public void setDiscount(double nr) {
	discount.set(nr);
}
public double getDiscount() {
	return discount.get();
}
public int getID(){
	return id.get();
}
public String getName() {
	return name.get();
}
public void setName(String name) {
	this.name.set(name);
}
public void setProfit(double prf) {
	profitmargin.set(prf);
}
public double getProfit() {
	return profitmargin.get();
}
public void addBarcode(int id) {
	itemids.add(id);
}
public void removeBarcode(int id) {
       for(int i=0;i<itemids.size();i++) {
    	   if(itemids.get(i).equals(id)) {
    		   itemids.remove(i);
    		   itemids.trimToSize();
    	   }
       }
}
public boolean checkBarcode(int id) {
	for(int i=0;i<itemids.size();i++) {
		if(itemids.get(i)==id) {
			return true;
		}
	}
	return false;
}
public void setID(int nr) {
	id.set(nr);
}
public void WriteData(DataOutputStream outputStream) throws IOException {
	outputStream.writeInt(ids.get());
	outputStream.writeInt(getID());
	outputStream.writeUTF(getName());
	outputStream.writeDouble(getProfit());
	outputStream.writeInt(itemids.size());
    for(int i=0;i<itemids.size();i++) {
    	outputStream.writeInt(itemids.get(i));
    }
	outputStream.writeDouble(getDiscount());
}

public void ReadData(DataInputStream inputStream) throws IOException, ClassNotFoundException {
	ids.set(inputStream.readInt());
	setID(inputStream.readInt());
	setName(inputStream.readUTF());
	setProfit(inputStream.readDouble());
	int index=inputStream.readInt();
	itemids.clear();
	for(int i=0;i<index;i++) {
		itemids.add(inputStream.readInt());
	}
	setDiscount(inputStream.readDouble());
}
}
