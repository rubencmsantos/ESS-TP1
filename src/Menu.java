import java.util.ArrayList;
import java.util.Scanner;

public class Menu {

	 private ArrayList <String> ops;
	 private int op;
	 
	 public Menu(String[] ops) {
	       this.ops = new ArrayList <String> ();
	       for(String op : ops) 
	            this.ops.add(op);
	       this.op=0;
	   }
	 
	 public void run() {
	       do {
	           apresentaMenu();
	           this.op = Opcao();
	        } while (this.op == -1);
	       
	    }
	 
	 public void apresentaMenu() {
	      for (int i=0; i<this.ops.size(); i++) {
	          System.out.println(this.ops.get(i));
	        }
	  }
	 
	  private int Opcao(){
	      int op;
	      @SuppressWarnings("resource")
		Scanner sc = new Scanner(System.in);
	      op = sc.nextInt();
	      if(op < 0 || op > this.ops.size()) {
	          System.out.println("OPÇÃO NÃO EXISTENTE, TENTE OUTRA\n");
	          op = -1;
	        }
	      return op;
	    }
	  
	  public int getOpcao() {
	        return this.op;
	    }
	
}
