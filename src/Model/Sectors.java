package Model;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.List;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Sectors {
private transient StringProperty name= new SimpleStringProperty("NULL");
private transient ObservableList<String> UserNames= FXCollections.observableArrayList();
private transient ObservableList<String> Categories= FXCollections.observableArrayList();
public Sectors(String name){
	this.name=new SimpleStringProperty(name);
}
public Sectors(){
	
}
public int getUserNamesSize() {
	return UserNames.size();
}
public int getCategoriesSize() {
	return Categories.size();
}
public void setName(String name) {
	this.name.set(name);
}
public String getName() {
	return name.get();
}
public void setUserNames(List<String> names) {
	for(int i=0;i<names.size();i++) {
		UserNames.add(names.get(i));
	}
}
public ObservableList<String> getUserNames(){
	return UserNames;
}
public void setCategories(List<String> categories) {
	for(int i=0;i<categories.size();i++) {
		Categories.add(categories.get(i));
	}
}
public ObservableList<String> getCategories(){
	return Categories;
}
public void WriteData(DataOutputStream outputStream) throws IOException {
	outputStream.writeUTF(getName());
	outputStream.writeInt(UserNames.size());
    for(int i=0;i<UserNames.size();i++) {
    	outputStream.writeUTF(UserNames.get(i));
    }
    outputStream.writeInt(Categories.size());
    for(int i=0;i<Categories.size();i++) {
    	outputStream.writeUTF(Categories.get(i));
    }
}
public void ReadData(DataInputStream inputStream) throws IOException, ClassNotFoundException {
	setName(inputStream.readUTF());
	int index=inputStream.readInt();
	UserNames.clear();
	for(int i=0;i<index;i++) {
		UserNames.add(inputStream.readUTF());
	}
	index=inputStream.readInt();
	Categories.clear();
	for(int i=0;i<index;i++) {
		Categories.add(inputStream.readUTF());
	}
}
}
