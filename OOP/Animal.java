import java.util.*;

class animal{
    public String especie;
    public String barulho;
    public int idade;

    public animal(String esp, String bar){
        this.especie = esp;
        this.barulho = bar;
        this.idade = 0;
    }

    public String fazerBarulho(){
        if(this.idade == 0) return "---";
        if(this.idade >= 4) return "RIP";
        else return this.barulho;
    }

    public void envelhecer(){
        this.idade += 1;
        if(this.idade >= 4){
            System.out.println("warning: " + this.especie + "morreu");
        }
    }

    public String toString() {
        return String.format("%s:%d:%s", especie, idade, barulho);
    }



}

public class Animal{
     static animal anim = new animal("", "");
    public static void main(String[] _args){
        while (true) {
            String line = input();
            String[] args = line.split(" ");
            write('$' + line);

            if ("show".equals(args[0])) {
                write(anim.toString());
            }
            else if ("grow".equals(args[0])) {
                anim.envelhecer();
            }
            else if ("init".equals(args[0])) {
                anim = new animal(args[1], args[2]);
            }
            else if ("noise".equals(args[0])){
                anim.fazerBarulho();
            }
            else if ("end".equals(args[0])){
                break;
            }
            else
                System.out.println("fail: comando invalido");
            

        }
    }
    private static Scanner scanner = new Scanner(System.in);
    private static String input() { return scanner.nextLine(); }
    private static double number(String value) { return Double.parseDouble(value); }
    private static void write(String value) { System.out.println(value); }
}
    