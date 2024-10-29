
import java.text.DecimalFormat;
import java.util.Scanner;
import java.util.ArrayList;

class Slot {
    private String name;
    private float price;
    private int quantity;
    
    public Slot( String name, float price, int quantity ) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }

     public String getName(){
        return this.name;
    }

    public float getPrice(){
        return this.price;
    }

    public int getQuantity(){
        return this.quantity;
    }
    
    public void setName(String name){
        this.name = name;
    }

    public void setPrice(float price){
        this.price = price;
    }

    public void setQuantity(int quantity){
        this.quantity = quantity;
    }
    
    public String toString() {
        return String.format("%8s",this.name) + " : " +
               this.quantity + " U : " +
               junk.decForm.format(this.price) + " RS";
    }
}

class VendingMachine {
    private ArrayList<Slot> slots;
    private float profit;
    private float cash;
    private int capacity;
    
    public VendingMachine( int capacity ) {
        this.slots = new ArrayList<Slot>();
        for(int i = 0; i<capacity;i++){
            this.slots.add(new Slot("empty", (float)0.00, 0));
        }
    }

    private boolean indiceInvalido( int ind ) {
        if ( ind < 0 || ind >= this.slots.size() ) {
            return true;
        }
        return false;
    }

    public Slot getSlot( int ind ) {
        if ( this.indiceInvalido(ind) ) {
            return null;
        }

        return this.slots.get(ind);
    }
    public void setSlot( int ind, Slot slot ) {
        if(ind > this.slots.size() || ind<0){
            System.out.println("fail: indice nao existe");
            return;
        }
        this.slots.set(ind, slot);
    }
    public void clearSlot( int ind ) {
        this.slots.set(ind, new Slot("empty", (float)0.00, 0));
    }

    public float withdrawCash() {
        junk.println("voce recebeu " + junk.decForm.format(this.cash) + " RS");
        this.cash= (float)0.00;
        return this.cash;
    }

    public void insertCash( float cash){
        this.cash += cash;
    }

    public float getProfit() {
        return this.profit;
    }

    public void buyItem( int ind ) {
        if(!indiceInvalido(ind)){
            System.out.println("fail: indice nao existe");
            return;
        }
        
        Slot slot = slots.get(ind);
        if(cash < slot.getPrice()){
            System.out.println("fail: saldo insuficiente");
            return;
        }
        
        if(slot.getQuantity() == 0){
            System.out.println("fail: espiral sem produtos");
            return;
        }
        
        System.out.println("voce comprou um " + slot.getName());
        slot.setQuantity(slot.getQuantity() - 1);
        this.cash -= slot.getPrice();
        this.profit += slot.getPrice();
    }


    public String toString() {
        String s = "saldo: " + junk.decForm.format(this.cash) + "\n";
        for (int i=0; i<this.slots.size(); i++) {
            Slot slot = this.getSlot(i);
            s += i + " [" + slot + "]\n";
        }
        return s;
    }
}

class junk {
    public static void main (String[] args) {
        VendingMachine machine = new VendingMachine(0);
        
        while( true ) {
            String linha = input();
            String[] palavras = linha.split(" ");
            println("$" + linha);

               if ( palavras[0].equals("end") ) { break; }
            else if ( palavras[0].equals("init")     ) { machine = new VendingMachine( Integer.parseInt(palavras[1]) ); }
            else if ( palavras[0].equals("show")     ) { print(machine); }
            else if ( palavras[0].equals("set")      ) { machine.setSlot( Integer.parseInt(palavras[1]), new Slot( palavras[2], Float.parseFloat(palavras[4]), Integer.parseInt(palavras[3]) ) ); }
            else if ( palavras[0].equals("limpar")   ) { machine.clearSlot( Integer.parseInt(palavras[1]) ); }
            else if ( palavras[0].equals("apurado")  ) { println( "apurado total: " + decForm.format(machine.getProfit()) ); }
            else if ( palavras[0].equals("dinheiro")   ) { machine.insertCash( Float.parseFloat(palavras[1]) ); }
            else if ( palavras[0].equals("comprar")   ) { machine.buyItem( Integer.parseInt(palavras[1]) ); }
            else if ( palavras[0].equals("troco")   ) { machine.withdrawCash(); }
            else { println("comando invalido!"); }
        }
    }

    public static Scanner scan = new Scanner(System.in);
    public static String input() { return scan.nextLine(); }
    public static void println(Object str) { System.out.println(str); }
    public static void print(Object str) { System.out.print(str); }
    public static DecimalFormat decForm = new DecimalFormat("0.00");
}
