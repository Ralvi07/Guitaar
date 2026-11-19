package DAOS;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import Model.Bills;

public class FileHandler {

private final BillItem RamLoad;
private final boolean Check;
public FileHandler() throws IOException{
	RamLoad=new BillItem();
	 File Creator = new File("storage.dat");
	 if(Creator.createNewFile()) {
			 Check=false;
	 }else {
         Check=true;
	 }
	 try {
		LoadStorage();
	} catch (ClassNotFoundException e) {
		e.printStackTrace();
	} catch (IOException e) {
		e.printStackTrace();
	}
}
public void SaveStorage() throws IOException {
	    
		 FileOutputStream file = new FileOutputStream("storage.dat");
		 DataOutputStream Stream = new DataOutputStream(file);
		 RamLoad.WriteData(Stream);

}
public void LoadStorage() throws ClassNotFoundException, IOException {
	if(Check) {
		FileInputStream file = new FileInputStream("storage.dat");
		DataInputStream in = new DataInputStream(file);
	      RamLoad.ReadData(in);

	}
}
public void PrintBills(List<Bills> bills) throws IOException {
	LocalDate today = LocalDate.now();
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    FileWriter writer = new FileWriter("RecieptNr"+bills.getFirst().getActualID()+".txt", true);
    writer.write("Date: " + today.format(formatter) + "\n");
    double wow=0;
    for(int i=0;i<bills.size();i++) {
    	writer.write("Article: " + bills.get(i).getName() + "\n");
    	writer.write("Amount: " + bills.get(i).getQuantity() + "\n");
    	writer.write("Price: " + bills.get(i).getPrice() + "\n");
    	writer.write("Total Price per Articel: " + bills.get(i).getTotal() + "\n");
    	wow+=bills.get(i).getTotal();
    }
    writer.write("Total Price: " + wow + "\n");
    writer.close();
}
public BillItem getRAMStorage() {
	return RamLoad;
}
}