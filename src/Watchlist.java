import java.io.Serializable;

public class Watchlist implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Double limit;
	private String code;
	private String user;
	private Integer upordown;
	
	
	public Watchlist() {
		
		this.code = "N/A";
		this.user = "N/A";
		this.limit = 0.0;
		this.upordown = 0;
	}
	
	public Watchlist(String code, String user, Double limit, Integer upordown) {
		
		this.code = code;
		this.user = user;
		this.limit = limit;
		this.upordown  = upordown;
	}
	
	public Watchlist(Watchlist a) {
		this.code = a.getCode();
		this.user = a.getUser();
		this.limit = a.getLimit();
		this.upordown = a.getUpordown();
	}

	Integer getUpordown() {
		// TODO Auto-generated method stub
		return this.upordown;
	}

	Double getLimit() {
		// TODO Auto-generated method stub
		return this.limit;
	}

	String getCode() {
		// TODO Auto-generated method stub
		return this.code;
	}
	
	String getUser() {
		// TODO Auto-generated method stub
		return this.user;
	}
	
	private void setCode(String code) {
		this.code = code;
	}
	private void setLimit(Double limit) {
		this.limit = limit;
	}
	private void setUpordown(Integer upordown) {
		this.upordown = upordown;
	}
	private void setUser(String user) {
		this.user = user;
	}
	
	 public boolean equals(Object o) {
	        
	        if(this == o) return true;
	        
	        if((o == null) || (this.getClass() != o.getClass())) return false;
	        else {
	            Watchlist a = (Watchlist) o;
	            
	            return(this.code.equals(a.getCode())
	            		   && this.user.equals(a.getUser())
	                   && this.limit.equals(a.getLimit()) 
	                   && this.upordown.equals(a.getUpordown())
	                   );
	        }
	    
	    }
	 
	public String toString() {
			
			StringBuffer sb = new StringBuffer();
			sb.append("["+this.getCode()+"]"+"\n");
			sb.append("["+this.getUser()+"]"+"\n");
			sb.append("[LIMIT] "+this.getLimit()+"\n");
			sb.append("[ABOVE OR LOWER] "+this.getUpordown()+"\n");
	
			return sb.toString();
	}
	
}
