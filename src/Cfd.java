import java.io.Serializable;

public class Cfd implements Serializable {


	private Integer id;
	private Asset asset;
	private String type;
	private String user;
	Double lowerlimit;
	private Double upperlimit;
	private Double units;
	private Double start_value;
	private Boolean active;
	
	public Cfd() {
		this.id = 0;
		this.asset = new Asset();
		this.type = "N/A";
		this.user = "N/A";
		this.lowerlimit = 0.0;
		this.upperlimit = 0.0;
		this.units = 0.0;
		this.start_value = 0.0;
		this.active = true;
	}
	public Cfd(Integer id, Asset asset, String type, String user, Double lowerlimit, Double upperlimit, Double units, Double start_value, Boolean active) {
		this.id = id;
		this.asset = asset;
		this.type = type;
		this.user = user;
		this.lowerlimit = lowerlimit;
		this.upperlimit = upperlimit;
		this.units = units;
		this.start_value = start_value;
		this.active = active;
	}
	
	public Cfd (Cfd cfd) {
		
		this.id = cfd.getId();
		this.asset = cfd.getAsset();
		this.type = cfd.getType();
		this.user = cfd.getUser();
		this.lowerlimit = cfd.getLowerlimit();
		this.upperlimit = cfd.getUpperlimit();
		this.units = cfd.getUnits();
		this.start_value = cfd.getStart_value();
		this.active = cfd.getActive();
	}

	Boolean getActive() {
		// TODO Auto-generated method stub
		return active;
	}

	Double getStart_value() {
		// TODO Auto-generated method stub
		return start_value;
	}

	Double getUpperlimit() {
		// TODO Auto-generated method stub
		return upperlimit;
	}

	Double getLowerlimit() {
		// TODO Auto-generated method stub
		return lowerlimit;
	}

	String getUser() {
		// TODO Auto-generated method stub
		return user;
	}

	String getType() {
		// TODO Auto-generated method stub
		return type;
	}

	Asset getAsset() {
		// TODO Auto-generated method stub
		return asset;
	}

	Integer getId() {
		// TODO Auto-generated method stub
		return id;
	}
	
	Double getUnits() {
		return units;
	}
	
	public void setUnActive() {
		this.active = false ;
	}
	
	
	public Cfd clone() {
		return new Cfd(this);
	}
	
	public boolean equals(Object o) {
        
        if(this == o) { 
            return true;
        }
        
        if((o == null) || (this.getClass() != o.getClass())) return false;
        else {
            Cfd a = (Cfd) o;
            
            return(this.id.equals(a.getId()) 
                   && this.asset.equals(a.getAsset()) 
                   && this.type.equals(a.getType()) 
                   && this.user.equals(a.getUser()) 
                   && this.lowerlimit.equals(a.getLowerlimit())
                   && this.upperlimit.equals(a.getUpperlimit())
                   && this.start_value.equals(a.getStart_value())
                   && this.active.equals(a.getActive()));
        }
    }
	
	public String toString() {
		
		StringBuffer sb = new StringBuffer();
		sb.append("[CFD ID] ");
		sb.append(this.id+"\n");
		sb.append("[ASSET] ");
		sb.append(this.asset.getName()+"\n");
		sb.append("[TYPE] ");
		sb.append(this.type+"\n");
		sb.append("[OWNER] ");
		sb.append(this.user+"\n");
		sb.append("[UNITS BOUGHT] ");
		sb.append(this.units+"\n");
		sb.append("[STOP LOSS LIMIT] ");
		sb.append(this.lowerlimit+"\n");
		sb.append("[TAKE PROFIT LIMIT] ");
		sb.append(this.upperlimit+"\n");
		sb.append("[STOCK START VALUE] ");
		sb.append(this.start_value+"\n");
		sb.append("[STATUS] ");
		if(this.active == true) {
			sb.append("ACTIVE\n");
		} else sb.append("TERMINATED\n");
		
		return sb.toString();
	
	}
	
	 
	
	
}
