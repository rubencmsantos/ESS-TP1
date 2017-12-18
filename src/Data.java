import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import Exceptions.PassInvalException;
import Exceptions.UserAlreadyException;
import Exceptions.UserInvalException;

public class Data implements Serializable{
	
	private ConcurrentHashMap <Integer, Cfd> cfdlist;
	private HashMap <String, Asset> assetlist;
	private HashMap <String, User> userlist;
	private ConcurrentHashMap <Integer, Watchlist> watchlist;


	
	public Data() {
		
		this.cfdlist = new ConcurrentHashMap<Integer, Cfd> ();
		this.assetlist = new HashMap <String, Asset> ();
		this.userlist = new HashMap <String, User> ();
		this.watchlist = new ConcurrentHashMap <Integer, Watchlist> ();
	}
	
	public Data(ConcurrentHashMap <Integer, Cfd> cfd, HashMap <String, Asset> asset, HashMap <String, User> user,ConcurrentHashMap <Integer, Watchlist> watch) {
		
		this.cfdlist =  cfd;
		this.assetlist = asset;
		this.userlist = user;
		this.watchlist = watch;
	}
	
	
	ConcurrentHashMap <Integer, Watchlist> getWatchlist() {
        return watchlist;
	}
	
	public void addtoWatchlist (Watchlist c) {
		Integer size = watchlist.size()+1;
		watchlist.put(size, c);
	}

// User Operations
	
	public User getUser(String email) {
		return this.userlist.get(email);}
	
	public void removeUser(String email) {
		userlist.remove(email);}
	
	public void addUser(String email, String password, String nome, Double profit, Double plafond) throws UserAlreadyException {
		
		User g = new User(email, password, nome, profit, plafond);
	       
		if(usercontains(email) != true) {
			userlist.put(email,g);}
		else{throw new UserAlreadyException();}
	}
	
	public boolean usercontains(String email) {
		return userlist.containsKey(email);}
	
	public String getMailPass(String email, HashMap <String, User> list) {
		String pass = "N/A";
		for(User u: userlist.values()){
			if(email.equals(u.getEmail())) {
				pass = u.getPassword();}
	    }
	    return pass;}
	
	public User giveuser(String email) {
		User user = new User();
		if(usercontains(email)){
			user = userlist.get(email).clone();}
		return user;}
	
	public void userlog(String email) throws UserInvalException {
	       if(usercontains(email) || email == "0") {
	       }else {
	           throw new UserInvalException();}
	       }
	
	public void userpass(String password, String email) throws PassInvalException {
	       User u = giveuser(email).clone();
	       if(password.equals(u.getPassword()) || email == "0"){}
	       else { 
	           throw new PassInvalException();}}
	
// CFD Operations
	
	public Cfd getCfd(Integer id) {
		return this.cfdlist.get(id);}
	
	public ConcurrentHashMap<Integer,Cfd> getAllCfds() {
		return cfdlist;}
	
	public void removeCfd(Integer id) {
        cfdlist.remove(id);}
	
	public void addCfd(Integer id, Cfd a) {
		cfdlist.put(id, a);}

// Asset Operations
	 
	public Asset getAsset(String codigo) {
		return this.assetlist.get(codigo);}
	
	public HashMap<String,Asset> getAllAssets() {
		return assetlist;}
	
	public void removeAsset(String codigo) {
		assetlist.remove(codigo);}

	public void addAsset(String code, Asset a) {
		assetlist.put(code, a);}
	
	
	public void gravaSessao(String filename) throws IOException {
		
		FileOutputStream fos = new FileOutputStream(filename);
	    ObjectOutputStream oos = new ObjectOutputStream(fos);
	    oos.writeObject(this);
	    oos.flush(); oos.close();
	    
	    }
}
