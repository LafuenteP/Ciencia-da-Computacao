/**
 * You must implement a virtual pet simulator. The pet can eat, play, sleep, and take a bath. It will eventually die if not properly cared for.
 *
 * Intro:
 * Your system should:
 * 
 * - Initialize:
 *   - Passing the maximum energy, satiety, and cleanliness levels of the pet.
 *   - All levels should start at maximum upon pet creation.
 * 
 * Other attributes include:
 * - Diamonds: which the pet will earn while playing.
 * - Age: which increases with each action performed, starting at 0.
 * 
 * Actions:
 * - Eating, playing, sleeping, and taking a bath:
 *   - Each operation causes increases and decreases in the attributes.
 *   - No attribute can exceed its maximum or drop below 0.
 * 
 * Dying:
 * - If any attribute reaches 0, the pet dies, and no actions can be performed except displaying the data.
 */


import java.util.*;

class Pet{
    private int energyMax, hungryMax, cleanMax;
    private int energy, hungry, clean;
    private int diamonds;
    private int age;
    private boolean alive;

    public Pet(int energy, int hungry, int clean){
        this.energy = energy;
        this.energyMax = energy;
        this.hungry = hungry;
        this.hungryMax = hungry;
        this.clean = clean;
        this.cleanMax = clean;
        this.diamonds = 0;
        this.age = 0;
        this.alive = true;
    }

    void setEnergy(int value){
        if(value <= 0){
            this.energy = 0;
            System.out.println("fail: pet morreu de fraqueza");
            this.alive = false;
            return;
        } 
        if(value > this.energyMax) {
            this.energy = this.energyMax;
            return;
        }
        this.energy = value;
    }


    public void setHungry(int value){
        if(value <= 0){
            this.hungry = 0;
            System.out.println("fail: pet morreu de fome");
            this.alive = false;
            return;
        } 
        if(value > this.hungry) {
            this.hungry = this.hungryMax;
            return;
        }
        this.hungry = value;
    }
    
    void setClean(int value){
        if(value <= 0){
            this.clean = 0;
            System.out.println("fail: pet morreu de sujeira");
            this.alive = false;
            return;
        } 
        if(value > this.clean) {
            this.clean = this.cleanMax;
            return;
        }
        this.clean = value;
    }

    private boolean testAlive(){
        if(getHungry() == 0 || getEnergy() == 0 || getClean() == 0){
            System.out.println("fail: pet esta morto");
            return false;
        } ;
        return true; 
    }


    public String toString(){
        String ss = "";
        ss +=  "E:" + energy + "/" + energyMax + ", "
            +  "S:" + hungry + "/" + hungryMax + ", "
            +  "L:" + clean + "/" + cleanMax + ", "
            +  "D:" + diamonds + ", " + "I:"  + age;
        return ss;
    }

    public void play(){
        if(!testAlive())
            return;
        setEnergy(getEnergy() - 2);
        setHungry(getHungry() - 1);
        setClean(getClean() - 3);
        diamonds += 1;
        age += 1;
    }
    public void shower(){
        if(!testAlive())
            return;
        setEnergy(getEnergy() - 3);
        setHungry(getHungry() - 1);
        setClean(getCleanMax());
        age += 2;
    }

    public void eat(){
        if(!testAlive())
            return;
        setEnergy(getEnergy() - 1);
        setHungry(getHungry() + 4);
        setClean(getClean() - 2);
        age += 1;
    }

    public void sleep(){
        if(!testAlive())
            return;
        if(getEnergy() >= getEnergyMax() - 4){
            System.out.println("fail: nao esta com sono");
            return;
        }
        age += getEnergyMax() - getEnergy();
        setEnergy(getEnergyMax());
        setHungry(getHungry() - 1);
        setClean(getClean());
        
    }


    int getClean(){
        return clean;
    }
    int getHungry(){
        return hungry;
    }
    int getEnergy(){
        return energy;
    }
    int getEnergyMax(){
        return energyMax;
    }
    int getCleanMax(){
        return cleanMax;
    }
    int getHungryMax(){
        return hungryMax;
    }
}


public class Tamagoshi {
    public static void main(String[] a) {
        Pet pet = new Pet(0, 0, 0);
        
        while (true) {
            var line = input();
            write("$" + line);
            var args = line.split(" ");

            if      (args[0].equals("end"))   { break;                                                                           }
            else if (args[0].equals("show"))  { write(pet.toString());                                                           }
            else if (args[0].equals("init"))  { pet = new Pet((int)number(args[1]), (int)number(args[2]), (int)number(args[3])); }
            else if (args[0].equals("play"))  { pet.play();                                                                      }
            else if (args[0].equals("eat"))   { pet.eat();                                                                       }
            else if (args[0].equals("sleep")) { pet.sleep();                                                                     }
            else if (args[0].equals("shower")){ pet.shower();                                                                    }
            else                              { write("fail: comando invalido");                                                 }
        }
    }

    private static Scanner scanner = new Scanner(System.in);
    private static String  input()              { return scanner.nextLine(); }
    private static double  number(String value) { return Double.parseDouble(value); }
    private static void    write(String value)  { System.out.println(value); }
}