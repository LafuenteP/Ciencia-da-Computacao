import java.util.*;

/**
 * The purpose of this activity is to implement an animal that goes through various growth stages until death.
 * 
 * Description:
 * The animal has a species, a life stage, and a characteristic noise it makes.
 * The stages the animal goes through are:
 * - 0: Baby
 * - 1: Child
 * - 2: Adult
 * - 3: Elder
 * - 4: Dead
 * 
 * When making noise, the animal emits the characteristic sound of its species, with the following restrictions:
 * - If it's a baby, it emits "—".
 * - If it's an elder, it emits "RIP".
 * 
 * When growing, the animal advances stages in its life.
 * Upon death, a warning message should be displayed: warning: {species} died.
 * Calling the growth method after the animal's death should display a warning: warning: {species} died.
 * 
 * Responsibilities:
 * - The code should be implemented in the Animal class.
 * - The methods in the Animal class should be called in the Student class.
 * - The Shell class is responsible for reading text requests and calling methods in the Student class.
 * 
 * Commands:
 * All commands follow the format $command arg1 arg2 ...
 * - $show: Displays the current state of the animal.
 *   Format: "{species}:{stage}:{noise}"
 *   Example: cat:0:meow
 * - $init species noise: Initializes the animal with the specified species and noise.
 * - $grow qtd: Makes the animal grow by a specified number of life stages.
 * - $noise: Makes the animal emit a sound.
 * - $end: Terminates execution.
 */


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
    