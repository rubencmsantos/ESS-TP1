import java.io.Serializable;
import yahoofinance.Stock;

public class Asset  implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String code, name;
	private Double ask;
	private Double bid;
	private Double prev;

	public Asset() {
		
		this.code = "N/A";
		this.name = "N/A";
		this.ask = 0.0;
		this.bid = 0.0;
		this.prev = 0.0;
	}
	
	public Asset(Stock st) {
		
		this.code = st.getQuote().getSymbol();
		this.name  = st.getName();
		this.ask = (st.getQuote().getAsk()).doubleValue();
		this.bid = (st.getQuote().getBid()).doubleValue();
		this.prev = (st.getQuote().getPreviousClose()).doubleValue();
	}
	
	
	public String getSymbol() {
		return this.code;
	}
	
	public Double getPrevious() {
		return this.prev;
	}
	
	public String getName() {
		return this.name;
	}
	
	public Double getBid() {
		return this.bid;
	}
	
	
	public Double getAsk() {
		return this.ask;
	}
	
	
	public void setCode(String code) {
		this.code = code;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void setBid(Double bid) {
		this.bid = bid;
	}
	
	public void setSell_value(Double ask) {
		this.ask = ask;
	}

	
	 public boolean equals(Object o) {
	        
	        if(this == o) return true;
	        
	        if((o == null) || (this.getClass() != o.getClass())) return false;
	        else {
	            Asset a = (Asset) o;
	            
	            return(this.code.equals(a.getName())
	                   && this.name.equals(a.getSymbol()) 
	                   && this.bid.equals(a.getBid())
	                   && this.ask == (a.getAsk()) 
	                   );
	        }
	    
	    }
	 
	public String toString() {
			
			StringBuffer sb = new StringBuffer();
			sb.append("["+this.getName()+"]"+" ["+this.getSymbol()+"] "+"\n");
			sb.append("[ASK] "+this.getAsk()+"\n");
			sb.append("[BID] "+this.getBid()+"\n");
			sb.append("[PREV CLOSE] "+this.getPrevious()+"\n");

			return sb.toString();
		}
	 }
	

