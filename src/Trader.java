import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;
import java.util.concurrent.ConcurrentHashMap;

import Exceptions.PassInvalException;
import Exceptions.UserAlreadyException;
import Exceptions.UserInvalException;
import yahoofinance.*;
import yahoofinance.quotes.stock.StockQuote;

public class Trader implements Runnable, DataSubject {

 
	private static Data data;
	private static User actual;
	private static Integer id;
	private static Menu printlogmenu;
	private static Menu printusermenu;
	private static Thread thread;
	private static Integer stop=0;
	
	//OBSERVER
	private static Map<String,MyObserver> observers; 
	private String mensagem;

	private static void apagaecra(){
	      System.out.println("\f");
	      
	//OBSERVER ADDITION
	  	
	

	  // OBSERVER ADDITIONS
	    }

	public synchronized void run() {
		
		
		
		//Display info about this particular thread
		try {
			
			ConcurrentHashMap <Integer, Cfd> cfdlist = new ConcurrentHashMap<Integer, Cfd> ();
			ConcurrentHashMap <Integer, Watchlist> watchlist = new ConcurrentHashMap <Integer, Watchlist> ();
			System.out.println("RUNNING");

			cfdlist = data.getAllCfds();	
			System.out.println("RUNNING");

			watchlist = data.getWatchlist();
			
			System.out.println("RUNNING");

			Double ask;
			Double bid;
			Stock st;
			Stock sl;
			Double value;
			System.out.println("RUNNING");


			while(stop==0) {
				
			for(Cfd c : cfdlist.values()) {
		
				st = YahooFinance.get(c.getAsset().getSymbol());
				ask = st.getQuote().getAsk().doubleValue();
				bid = st.getQuote().getBid().doubleValue();
				
				if(c.getType().equals("BUY") && c.getActive().equals(true)) {
						if(ask >= c.getUpperlimit() || ask <= c.getLowerlimit()) {
							auto_closeCfd(c, ask);
							observers.get(actual.getEmail()).update("CFD FECHADO");
							avisa("CFD FECHADO");
						}
				}
				if(c.getType().equals("SELL") && c.getActive().equals(true)) {
						if(bid >= c.getUpperlimit() || bid <= c.getLowerlimit()) {
							auto_closeCfd(c, bid);
							observers.get(actual.getEmail()).update("CFD FECHADO");
							avisa("CFD FECHADO");
						}
				}

				
			}

				for(Watchlist w: watchlist.values()) {
					
					sl = YahooFinance.get(w.getCode());
					value = sl.getQuote().getPrice().doubleValue();
					if(w.getUpordown().equals(1)) {
						//UPPER THAN
						String isup = "UP";
						if(value >= w.getLimit()) avisa(isup);
					}
					if(w.getUpordown().equals(0)) {
						//LOWER THAN
						String isdown = "DOWN";
						if(value < w.getLimit()) avisa(isdown);
					}
				
			}
			}
			
			try {
				Thread.sleep(4000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
				
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			
			
		finally {}
		System.out.println(Thread.currentThread().getName());
	}
	

	public static void main(String[] args) throws IOException {
	
		Locale.setDefault(new Locale("en", "US"));
        data = new Data();
        actual = new User();
        observers = new HashMap<String,MyObserver>();
        carregaSessao();
        Trader t = new Trader();
        thread = new Thread(t);
        thread.start();
        loadmenus();
        loginmenu(); 
 
      
    }
	
  	private static void avisa(String mensagem) {
  		data.getUser(actual.getEmail()).addNotification(mensagem);		
  	}

  	public static void addObserver(String e, MyObserver observer){
  		
  	      observers.put(e, observer);		
  	   }
  	
  	public void removeObserver(MyObserver observer) {
  		observers.remove(actual.getEmail());
  	}

	public static void loadmenus() {
    
    String[] logmenu = 
    		
                 {"  ___ ___ ___   _____ ___    _   ___ ___ _  _  ___ \n" + 
                 		" | __/ __/ __| |_   _| _ \\  /_\\ |   \\_ _| \\| |/ __|\n" + 
                 		" | _|\\__ \\__ \\   | | |   / / _ \\| |) | || .` | (_ |\n" + 
                 		" |___|___/___/   |_| |_|_\\/_/ \\_\\___/___|_|\\_|\\___|\n" + 
                 		"",
                  "[0] SAIR",
                  "[1] EFECTUAR LOGIN",
                  "[2] REGISTAR NOVO UTILIZADOR",};
    
    String[] usermenu = 
    		
		        {"  ___ ___ ___   _____ ___    _   ___ ___ _  _  ___ \n" + 
		        		" | __/ __/ __| |_   _| _ \\  /_\\ |   \\_ _| \\| |/ __|\n" + 
		        		" | _|\\__ \\__ \\   | | |   / / _ \\| |) | || .` | (_ |\n" + 
		        		" |___|___/___/   |_| |_|_\\/_/ \\_\\___/___|_|\\_|\\___|\n" +
		        		"",
		         "[0] LOGOUT",
		         "[1] CHECK ASSET LIST",
		         "[2] CHECK ACTIVE CFDS",
		         "[3] CHECK PROFIT",
		         "[4] CLOSE POSITION",
		         "[5] OPEN POSITION/CFD",
		         "[6] ADD ASSET TO WATCHLIST",
		         "[7] SHOW WATCHLIST",
		         "[8] SHOW NOTIFICATIONS",
		         };
    
    printusermenu = new Menu(usermenu);
    printlogmenu = new Menu(logmenu);
     
}

	public static void loginmenu() {
	    
	    int op = 1;
	    String filename = new String();
	    @SuppressWarnings("resource")
		Scanner into = new Scanner(System.in);
	    
	    while(op!=0) {
	        apagaecra();
	        printlogmenu.apresentaMenu();
	
	        System.out.print("[OPÇÃO] : ");
	        op = into.nextInt();
	        
	    switch(op) {
	    
	         case 0: {
	             apagaecra();
	             System.out.print("SAVING...");
	             filename = "data";
	             
	             try {
	            	 	data.gravaSessao(filename);
	             }
	             catch (IOException l) {
	            	 	l.printStackTrace();
	             }
	             
	             apagaecra();
	             stop = 0;
	 
	             System.out.print("[[SAFE TO CLOSE]]\n");

	             System.out.println("PRESSIONE ENTER PARA FECHAR\n");
	             Scanner keyboard = new Scanner(System.in);
	             keyboard.nextLine();
	             keyboard.close();
	             break;}
	
	         case 1: {
	             login();
	             userMenu();
	             break;}
	        
	         case 2: {
	        	 	 apagaecra();
	             novouser();
	             break;}
	         default : {
	        	 	apagaecra();
	        	 	loginmenu();
	         }
	    }
	    } 
    }
 
	@SuppressWarnings("resource")
	public static void login(){
		apagaecra();

	    System.out.println("[LOGIN]"); 
	    boolean ur = true;	   
	   
	    
	    Scanner sc = new Scanner(System.in);
	    String username = new String();
	    String pword = new String();
	    
	    while(ur == true) {	
	    	
	       System.out.print("[USERNAME]  ");
	       username = sc.nextLine();
	       System.out.print("[PASSWORD]  ");
	       pword = sc.nextLine();
	       
	       try {
	               ur = false;
	               data.userlog(username);
	               data.userpass(pword, username);
	               
	               actual = (User) data.getUser(username).clone();
	               
	           } catch (UserInvalException us) { 
	               ur = true;
	               apagaecra();
	               System.out.print("[DADOS INVÁLIDOS]\n");
	               System.out.print("[INTRODUZA NOVAMENTE]\n");

	           } catch (PassInvalException us){
	               ur = true; 
	            }
	        }
	    apagaecra();
	    System.out.println("[WELCOME " + actual.getName()+"]");
	    System.out.print(actual);
	    System.out.println("\nPRESSIONE ENTER PARA ENTRAR\n");
		Scanner keyboard = new Scanner(System.in);
	    keyboard.nextLine();
	}

	private static void userMenu() {
		
	    int op;
	    do {
        @SuppressWarnings("resource")
		Scanner in = new Scanner(System.in); 
        apagaecra();
        
        printusermenu.apresentaMenu();
        
        System.out.print("[OPÇÃO] : ");
        op = in.nextInt();
        apagaecra();
        
        switch(op) {
            case 0: {
            		// LOGOUT
                System.out.println("[LOGGING OUT]");
                System.out.println("PRESSIONE ENTER PARA VOLTAR AO MENU PRINCIPAL\n");
                @SuppressWarnings("resource")
				Scanner keyboard = new Scanner(System.in);
                keyboard.nextLine();
                break;
            }
            case 1: {
            		// CHECK ASSET LIST
            	    System.out.println("REFRESHING ASSET RATES\n");
            	    try{
            	    		load_assets();
            	    } catch (IOException e) {}
            	    apagaecra();
            	    show_assets();
            	    System.out.println("PRESSIONE ENTER PARA CONTINUAR\n");
            	    @SuppressWarnings("resource")
				Scanner keyboard = new Scanner(System.in);
            	    keyboard.nextLine();
            	    break;
            }
            
            case 2:{
            		// CHECK USER CFDS
            		listcfds();
            		System.out.println("PRESSIONE ENTER PARA CONTINUAR\n");
            		Scanner keyboard = new Scanner(System.in);
            		keyboard.nextLine();
            		break;
            }
            case 3:{
            		// CHECK PROFIT
            		checkprofit();
            		System.out.println("PRESSIONE ENTER PARA CONTINUAR\n");
            		Scanner keyboard = new Scanner(System.in);
            		keyboard.nextLine();
            		break;
            }
            case 4:{
            		// CLOSE POSITION / CFD
            		try {
						close_cfd();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
            		System.out.println("PRESSIONE ENTER PARA CONTINUAR\n");
            		Scanner keyboard = new Scanner(System.in);
            		keyboard.nextLine();
            		break;
            }
            case 5:{
            		// OPEN POSITION / CFD
            		show_assets();
            		try {
						create_cfd();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
            		break;
            }
            case 6: {
            		// ADD ASSET TO WATCHLIST
            		show_assets();
            		addtoWatchlist();
            		break;
            }
            case 7: {
            		try {
						show_watchlist();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
            		System.out.println("PRESSIONE ENTER PARA CONTINUAR\n");
            		Scanner keyboard = new Scanner(System.in);
            		keyboard.nextLine();
            		break;
            }
            case 8: {
            		show_notifications();
            		System.out.println("PRESSIONE ENTER PARA CONTINUAR\n");
            		Scanner keyboard = new Scanner(System.in);
            		keyboard.nextLine();
            		break;
            }
            default: {
            		userMenu();
            }

        }
        
    } while(op!=0);
}


public static void novouser() {
	
    String email;
    String password;
    String nome;
    Double plafond;
    boolean aux = true;
    
    Scanner sc = new Scanner(System.in);

    while(aux == true) {
        
        System.out.print("[INTRODUZA O SEU EMAIL]  ");
        email = sc.nextLine();
        
         try {
            aux = false;
             System.out.print("[INTRODUZA A SUA PASSWORD]  ");
             password = sc.nextLine();
             System.out.print("[INTRODUZA O SEU PRIMEIRO E ÚLTIMO NOME]  ");
             nome = sc.nextLine();
             System.out.print("[INTRODUZA O SEU PLAFOND INICIAL EM EUROS]  ");
             plafond = sc.nextDouble();
            
             data.addUser(email, password, nome, 0.0, plafond);
             
         } catch (UserAlreadyException u) { 
            aux = true;
            System.out.println("[EMAIL JÁ EM USO, TENTE OUTRO]");  
        }
    }
     
     System.out.println("[REGISTO EFECTUADO COM SUCESSO]\n");
     System.out.println("[PRESSIONE ENTER PARA CONTINUAR]\n");
     Scanner keyboard = new Scanner(System.in);
     keyboard.nextLine();
 }

public static void load_assets() throws IOException {
	
	data.addAsset("FB", new Asset(YahooFinance.get("FB")));
	data.addAsset("APPL",new Asset(YahooFinance.get("AAPL")));
	data.addAsset("TSLA",new Asset(YahooFinance.get("TSLA")));
	data.addAsset("DE",new Asset(YahooFinance.get("DE")));
	data.addAsset("GOOG", new Asset(YahooFinance.get("GOOG")));
	data.addAsset("ORCL", new Asset(YahooFinance.get("ORCL")));
	data.addAsset("PFE",new Asset(YahooFinance.get("PFE")));
	data.addAsset("IBM",new Asset(YahooFinance.get("IBM")));
	data.addAsset("PBR",new Asset(YahooFinance.get("PBR")));
	data.addAsset("OI",new Asset(YahooFinance.get("OI")));
	data.addAsset("GDDY",new Asset(YahooFinance.get("GDDY")));
	data.addAsset("VZ",new Asset(YahooFinance.get("VZ")));
	data.addAsset("ATC",new Asset(YahooFinance.get("ATC.AS")));
	data.addAsset("AIRBUS",new Asset(YahooFinance.get("AIR.PA")));
}

public static void show_notifications() {
	
	ArrayList <ArrayList<String>> list = new ArrayList<> ();
	ArrayList<String> header = new ArrayList<>();
	HashMap <Integer, String> not = new HashMap <Integer, String> ();
	not = data.getUser(actual.getEmail()).getNotifications();
	header.add("NÚMERO");
	header.add("MENSAGEM");
	list.add(header);
	
	for(Integer id: not.keySet()) {
		ArrayList <String> line = new ArrayList <> ();
		line.add(""+id);
		line.add(not.get(id));
		
		list.add(line);
	}
	PrettyTable t = new PrettyTable(list);
	t.print();
}

public static void show_assets() {
	
	ArrayList <ArrayList<String>> list = new ArrayList<> ();
	ArrayList<String> header = new ArrayList<>();
	HashMap <String,Asset> assets = new HashMap <String,Asset> ();
	assets = data.getAllAssets();
	header.add("CÓDIGO");
	header.add("NAME");
	header.add("ASK [$]");
	header.add("BID [$]");
	header.add("PREÇO DE FECHO [$]");
	list.add(header);
	
	for(String cod: assets.keySet()) {
		
		ArrayList<String> line = new ArrayList <>();
		line.add(assets.get(cod).getSymbol());
		line.add(assets.get(cod).getName());
		if(assets.get(cod).getAsk().equals(0.0)) {
			line.add("CLOSED");
			line.add("CLOSED");
		} else { 
		line.add(""+assets.get(cod).getAsk());
		line.add(""+assets.get(cod).getBid());
		}
		line.add(""+assets.get(cod).getPrevious());
		list.add(line);
	}
	PrettyTable t = new PrettyTable(list);
	t.print();
}

public static synchronized void create_cfd() throws IOException {
	
	String cfd_email;
	String cfd_asset;
	String cfd_type;
	Double cfd_upper;
	Double cfd_lower;
	Double cfd_start_value;
	Double cfd_units;


	Asset n;
	
	Scanner sc = new Scanner(System.in);
	
	System.out.println("[START A NEW CFD]\n");
	cfd_email = actual.getEmail();
	
	System.out.print("[INSERT THE ASSET CODE] ");
	cfd_asset = sc.nextLine();
	
	System.out.print("[CHOOSE THE TYPE | SHORT OR BUY] ");
	cfd_type = sc.nextLine();
	
	System.out.print("[AMOUNT OF UNITS] ");
	cfd_units = sc.nextDouble();
	
	System.out.print("[SET \"TAKE PROFIT\" $] ");
	cfd_upper = sc.nextDouble();

	System.out.print("[SET \"STOP LOSS\" $] ");
	cfd_lower = sc.nextDouble();

	if(cfd_type.equals("BUY")) {
		cfd_start_value = YahooFinance.get(cfd_asset).getQuote().getBid().doubleValue();
		if(cfd_start_value == 0.0) cfd_start_value = YahooFinance.get(cfd_asset).getQuote().getPreviousClose().doubleValue();

	} else {
		cfd_start_value = YahooFinance.get(cfd_asset).getQuote().getAsk().doubleValue();
		if(cfd_start_value == 0.0) cfd_start_value = YahooFinance.get(cfd_asset).getQuote().getPreviousClose().doubleValue();
	}
	
	n = data.getAsset(cfd_asset);
	
	ConcurrentHashMap <Integer, Cfd> ncfd = new ConcurrentHashMap <Integer, Cfd> ();
	ncfd = data.getAllCfds();
	id = ncfd.size()+1;
	
	Cfd novo =  new Cfd(id, n, cfd_type, cfd_email, cfd_lower, cfd_upper, cfd_units, cfd_start_value, true);

	data.addCfd(id, novo);
	data.getUser(cfd_email).setCfds(novo);
	addObserver(actual.getEmail(), (MyObserver) actual);
	
 
	System.out.println("[CFD REGISTADO COM SUCESSO]");
	System.out.print("[PRESSIONE ENTER PARA VOLTAR]\n");
	Scanner keyboard = new Scanner(System.in);
	keyboard.nextLine();

}

public static synchronized void listcfds() {
	
	String list_email;
	Cfd cur;

	System.out.println("[ACTIVE CFDS]\n");
	
	ConcurrentHashMap <Integer, Cfd> ncfd = new ConcurrentHashMap <Integer, Cfd> ();
	ncfd = data.getAllCfds();
	
	list_email = data.getUser(actual.getEmail()).getEmail();
	
	ArrayList <ArrayList<String>> list = new ArrayList<> ();
	ArrayList<String> header = new ArrayList<>();
	header.add("NR DO CONTRATO");
	header.add("UTILIZADOR");
	header.add("ASSET");
	header.add("TIPO");
	header.add("UNIDADES");
	header.add("PREÇO INCIAL [$]");
	header.add("TAKE PROFIT [$]");
	header.add("STOP LOSS [$]");
	header.add("ESTADO");
	list.add(header);

	for(Integer id: ncfd.keySet()) {
		if(ncfd.get(id).getUser().equals(list_email)) {
			
			cur = ncfd.get(id);
			ArrayList<String> line = new ArrayList <>();
			
			line.add(""+cur.getId());
			line.add(""+data.giveuser(list_email).getName());
			line.add(""+cur.getAsset().getSymbol());
			line.add(""+cur.getType());
			line.add(""+cur.getUnits());
			line.add(""+cur.getStart_value());
			line.add(""+cur.getUpperlimit());
			line.add(""+cur.getLowerlimit());
			if(cur.getActive()) {
				line.add("ACTIVO");
			} else line.add("FECHADO");

			list.add(line);
		}	
	}
	
	PrettyTable tablecfd = new PrettyTable(list);
	tablecfd.print();
}

public static void checkprofit() {
	
	Double pf;
	String list_email;

	list_email = actual.getEmail();
	System.out.println("[PROFIT]");
	pf = data.getUser(list_email).getProfit();
	if(pf >= 0.0) {
		System.out.println("GAINING "+pf+"\n");
	} else {
		System.out.println("[[[LOSING]]] "+pf+"\n");
	}
	
}

public static synchronized void close_cfd() throws IOException {
	
	Integer nr;
	String email;
	String type;
	Double balance;
	Double current_value;
	Double start_value;
	Double units;
	String asset;
	Double ratio;
	Double invested;
	Double gain;
	Cfd closing;
	Asset hold;
	Double profit;
	
	email = actual.getEmail();
	
	listcfds();
	
	System.out.print("\n[INSERT CFD TO CLOSE]  ");
	Scanner into = new Scanner(System.in);
	nr = into.nextInt();
	

	closing = data.getCfd(nr);
	start_value = closing.getStart_value();
	units = closing.getUnits();
	type = closing.getType();
	hold = closing.getAsset();
	
	if(type.equals("BUY")) {
		current_value = YahooFinance.get(hold.getSymbol()).getQuote().getAsk().doubleValue();
		if(current_value == 0.0) current_value = YahooFinance.get(hold.getSymbol()).getQuote().getPreviousClose().doubleValue();
		ratio = (current_value / start_value) / 100;
		invested = start_value * units;
		balance = invested * ratio;
		gain = invested+balance;

		
		
	} else {
		current_value = YahooFinance.get(hold.getSymbol()).getQuote().getBid().doubleValue();
		if(current_value == 0.0) current_value = YahooFinance.get(hold.getSymbol()).getQuote().getPreviousClose().doubleValue();
		ratio = (current_value / start_value) / 100;
		invested = start_value * units;
		balance = invested * ratio;
		gain = invested+balance;
	}
	
	data.getCfd(nr).setUnActive();
	
	profit = data.getUser(email).getProfit();
	
	profit = profit + balance;
	data.getUser(email).setProfit(profit);
	data.getUser(actual.getEmail()).addNotification("CFD "+nr+" FECHADO");
	
	System.out.println("[CFD CLOSED]");
	System.out.println("[GAIN] $"+ balance +"\n");

}

public synchronized void auto_closeCfd(Cfd c, Double value) throws IOException {
	
	Integer nr;
	String email;
	Double balance;
	Double current_value;
	Double start_value;
	Double units;
	Double ratio;
	Double invested;
	Double gain;
	Cfd closing;
	Asset hold;
	Double profit;
	
	email = actual.getEmail();
	
	nr = c.getId();
	start_value = c.getStart_value();
	units = c.getUnits();
	hold = c.getAsset();
	
	
	current_value = value;
	if(current_value == 0.0) current_value = YahooFinance.get(hold.getSymbol()).getQuote().getPreviousClose().doubleValue();
	ratio = (current_value / start_value) / 100;
	invested = start_value * units;
	balance = invested * ratio;
	gain = invested+balance;
	
	data.getCfd(nr).setUnActive();
	profit = data.getUser(email).getProfit();
	
	profit = profit + balance;
	data.getUser(email).setProfit(profit);
	
}

private static synchronized void addtoWatchlist() {
	
	String code;
	String u;
	Integer upordown;
	Double limit;
	Watchlist w  = new Watchlist();
	
	u = actual.getEmail();
	System.out.print("[INSERT ASSET CODE TO WATCH]  ");
	Scanner into = new Scanner(System.in);
	code = into.nextLine();
	
	System.out.print("[INSERT LIMIT WARNING]  ");
	limit = into.nextDouble();
	
	System.out.print("[UP OR DOWN THE LIMIT? 1 - UP | 0 - DOWN]  ");
	upordown = into.nextInt();
	
	w = new Watchlist(code, u, limit, upordown);
	data.addtoWatchlist(w);	

}

private static void show_watchlist() throws IOException {
	
	ConcurrentHashMap<Integer, Watchlist> userlist = new ConcurrentHashMap <Integer, Watchlist> ();
	Watchlist cur = new Watchlist();
	
	ArrayList <ArrayList<String>> list = new ArrayList<> ();
	ArrayList<String> header = new ArrayList<>();
	header.add("ASSET CODE");
	header.add("USER");
	header.add("LIMIT");
	header.add("ACTUAL VALUE");
	header.add("UP OR DOWN");
	list.add(header);
	
	userlist = data.getWatchlist();
	
	for(Integer id : userlist.keySet()) {
		ArrayList<String> line = new ArrayList <>();
		cur = userlist.get(id);
		line.add(""+cur.getCode());
		line.add(""+cur.getUser());
		line.add(""+cur.getLimit());
		line.add(""+YahooFinance.get(cur.getCode()).getQuote().getPreviousClose());
		line.add(""+cur.getUpordown());

		list.add(line);
	}	


PrettyTable tablecfd = new PrettyTable(list);
tablecfd.print();
	
	
}


private static void carregaSessao() {
    try{
        System.out.print("LOADING...");
        String filename = "data";

        FileInputStream fis = new FileInputStream(filename);
        ObjectInputStream ois = new ObjectInputStream(fis);

        try {
            data =  (Data)ois.readObject();
        }
        catch (ClassNotFoundException e) {
            e.printStackTrace();
            data = new Data();   
        }
        catch (ClassCastException e) {
        		e.printStackTrace();
        		data = new Data();  
        }
        catch (IOException e) {
            e.printStackTrace();
            data = new Data();
        }
        
   		} catch (FileNotFoundException e){
   			System.out.println(e.getMessage()+"ERRO AO CARREGAR, INICIE APLICAÇÃO DE NOVO\n");
        carregaSessao();
    }
    catch (IOException e) {
        System.out.println("IOException DETECTED"+e.getMessage());
        data = new Data(); 
    } 
}   


public void gravaSessao(String filename) throws IOException {
  FileOutputStream fos = new FileOutputStream(filename);
  ObjectOutputStream oos = new ObjectOutputStream(fos);
  
  oos.writeObject(this);
  oos.flush(); oos.close();
}


}






