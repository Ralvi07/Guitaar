package Model;


public abstract class User {
 private String name="W.D";
 private String surname="Gaster";
 private String username="Gaster666";
 private String password="1225";
 private static int id=0;
 private int ID;
 User(String usr, String pass,String name, String surname){
	 username=usr;
	 password=pass;
	 this.name=name;
	 this.surname=surname;
	 ID=id;
	 id++;
 }
 User(){
	 
}
public String returname() {
	return name;
}
public String returnsurname() {
	return surname;
}
public int returnid() {
	return ID;
}
public void setPassword(String pass) {
	password=pass;
}
public String getPassword() {
	return password;
}

public String getUsername() {
	return username;
}
}
