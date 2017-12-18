import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;


public class User implements Serializable, MyObserver{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 
	 */
	
	private String email;
	private String password;
	private String name;
	private Double profit;
	private Double plafond;
	

	private HashSet <Cfd> cfds;
	private HashMap <Integer, String> notifications;
	
	public User() {
		
		this.email = "N/A";
		this.password = "N/A";
		this.name = "N/A";
		this.profit = 0.0;
		this.cfds = new HashSet <Cfd> ();
		this.plafond = 0.0;
		this.notifications = new HashMap <Integer, String> ();
	}
	
	public User (String email, String password, String name, Double profit, Double plafond) {
		
		this.email = email;
		this.name = name;
		this.password = password;
		this.profit = profit;
		this.cfds = new HashSet <Cfd> ();
		this.plafond = plafond;
		this.notifications = new HashMap <Integer, String> ();
	}
	
	public User(User user) {
		
		this.email = user.getEmail();
		this.password = user.getPassword();
		this.name = user.getName();
		this.profit = user.getProfit();
		this.plafond = user.getPlafond();
		
		this.cfds = new HashSet <Cfd> ();
		for(@SuppressWarnings("unused") Cfd a: cfds) {
			this.getCfds();
		}
		
		this.notifications = new HashMap <Integer, String> ();
		for(@SuppressWarnings("unused") String a: notifications.values()) {
			this.getNotifications();
		}
	}

	HashMap<Integer, String> getNotifications() {
		// TODO Auto-generated method stub
		return notifications;
	}
	
	public void addNotification(String m) {
		Integer size = notifications.size()+1;
		notifications.put(size, m);
	}

	Double getProfit() {
		// TODO Auto-generated method stub
		return profit;
	}

	String getName() {
		// TODO Auto-generated method stub
		return name;
	}

	String getPassword() {
		// TODO Auto-generated method stub
		return password;
	}

	String getEmail() {
		// TODO Auto-generated method stub
		return email;
	}
	
	private Double getPlafond() {
		return plafond;
	}
	public HashSet <Cfd> getCfds() {
		
		HashSet <Cfd> aux = new HashSet <Cfd> ();
	        for(Cfd c: cfds) {
	            aux.add(c);
	        }
	        return aux;
	 }
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public void setPassword(String password) {
		this.password = password;
		
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void setProfit(Double profit) {
		this.profit = profit;
	}
	
	public void setPlafond(Double plafond) {
		this.plafond = plafond;
	}
	
	public void setCfds (Cfd c) {
		this.cfds = new HashSet <Cfd> ();
		for(Cfd a: cfds) {
			cfds.add(a);
		}
	}
	
	public void addCFDtoUser(Cfd c) {
		this.cfds.add(c);
	}
	
	public void removeCFDofUser(Cfd c) {
		this.cfds.remove(c);
	}
	
	public String showCFDS() {
		StringBuffer sb = new StringBuffer();
		sb.append("CFDS OF USER:");
		for(Cfd c: cfds) {
			sb.append(c);
		}
		return sb.toString();
	}
	
	public String toString() {
		
		StringBuffer sb = new StringBuffer();
		sb.append("[BALANÇO] ");
		sb.append(this.profit+" $\n");
		sb.append("[PLAFOND INICIAL] ");
		sb.append(this.plafond+" $\n");
		
		return sb.toString();
	}
	
	public User clone() {
		return new User(this);
	}
	
	public boolean equals(Object o) {
	        
	        if(this == o) { 
	            return true;
	        }
	        
	        if((o == null) || (this.getClass() != o.getClass())) return false;
	        else {
	            User a = (User) o;
	            
	            return(this.email.equals(a.getEmail()) 
	                   && this.password.equals(a.getPassword()) 
	                   && this.name.equals(a.getName()) 
	                   && this.cfds.equals(a.getCfds())
	                   && this.profit.equals(a.getProfit()));
	        }
	    }

	@Override
	public void update(String mensagem) {
		// TODO Auto-generated method stub
		Integer size = notifications.size() +1;
		notifications.put(size, mensagem);
	}
	
	
	
	
}

