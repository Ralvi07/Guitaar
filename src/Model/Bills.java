package Model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import javafx.beans.property.DoubleProperty;

public class Bills {

    private final StringProperty name=new SimpleStringProperty("NULL");
    private static IntegerProperty id=new SimpleIntegerProperty(0);
    private final IntegerProperty quantity= new SimpleIntegerProperty(0) ;
    private final IntegerProperty ID= new SimpleIntegerProperty(0);
    private final DoubleProperty price= new SimpleDoubleProperty(0);

    public Bills(String name, int quantity, double price) {
        this.name.set(name);
        this.quantity.set(quantity);
        this.price.set(price);
    }
    public Bills(){
    	
    }
    public int getID() {
    	return id.get();
    }
    public int getActualID() {
    	return ID.get();
    }
    public void IterateID() {
    	id.set(getID()+1);
    	ID.set(id.get());
    }
    public String getName() {
    	return name.get();
    }
    public int getQuantity() {
    	return quantity.get();
    }
    public double getPrice() {
    	return price.get();
    }
    public double getTotal() {
        return quantity.get() * price.get();
    }
    public void setName(String name) {
    	this.name.set(name);
    }
    public void setID(int nr) {
    	ID.set(nr);
    }
    public void setid(int nr) {
    	id.set(nr);
    }
    public void setQuantity(int nr) {
    	quantity.set(nr);
    }
    public void setPrice(double nr) {
    	price.set(nr);
    }
    public void WriteData(DataOutputStream outputStream) throws IOException {
    	outputStream.writeInt(ID.get());
    	outputStream.writeInt(id.get());
    	outputStream.writeUTF(getName());
    	outputStream.writeDouble(price.get());
    	outputStream.writeInt(quantity.get());
        
    }

    public void ReadData(DataInputStream inputStream) throws IOException {
    	setID(inputStream.readInt());
    	setid(inputStream.readInt());
    	setName(inputStream.readUTF());
    	setPrice(inputStream.readDouble());
    	setQuantity(inputStream.readInt());
    }
}