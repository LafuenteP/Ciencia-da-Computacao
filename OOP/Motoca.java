/**
 * This is a modeling and implementation project for a motorized scooter in a park. 
 * The idea is to simulate the functioning of this scooter through classes in a program.
 * To achieve this, two main classes will be implemented: Person and Scooter.
 *
 * Description:
 * The Scooter class represents the scooter itself. It has attributes such as power, time, 
 * and the person who is currently using it. 
 * The scooter starts with a power of 1, no minutes, and no one on it. 
 * Only one person can be on the scooter at a time.
 * 
 * The main functionalities of the scooter include:
 * - Getting on a person,
 * - Getting off a person,
 * - Buying time,
 * - Driving for a specified duration,
 * - Honking.
 * 
 * The Person class represents the users of the scooter. It has attributes for name and age.
 * 
 * Commands:
 * All commands follow the format $command arg1 arg2 ....
 * In case of error, an appropriate message must be printed.
 * 
 * - $show: Displays the current state of the scooter, including power, time, 
 *   and the person currently on the scooter.
 *   Format: "power:{this.power}, time:{this.time}, person:{this.person}"
 *   Example: power:1, time:0, person:(marcos:4)
 * 
 * - $init: Resets the scooter to the initial state, with power 1, no minutes, and no one on it.
 * 
 * - $enter: Allows a person to get on the scooter. It must be followed by the arguments 
 *   name and age of the person.
 * 
 * - $leave: Makes the person currently on the scooter get off.
 * 
 * - $buy: Allows buying time in minutes to use the scooter. 
 *   The time received is added to the current time.
 * 
 * - $drive: Allows driving the scooter for a specified duration.
 * 
 * - $honk: Allows honking the scooter.
 */

import java.util.*;

class Person {
    private String name;
    private int age;
    public Person(String name, int age){
        this.age = age;
        this.name = name;
    }
    public String getName() {
        return this.name;
    }
    public int getAge() {
        return this.age;
    }
    public String toString(){
        String saida = this.name + ":" + this.age;
        return saida;
    }
}

class Motorcycle {
    private Person person; //agregacao
    private int power;
    private int time;
    
    //Inicia o atributo power, time com zero e person com null
    public Motorcycle(int power){
        this.power = power;
        this.person = null;
        this.time = 0;
    }
    public void buyTime( int time){
        this.time += time;
    }

    public String honk(){
        String saida = "P";
        
        for(int i=0;i<this.power;i++)
            saida += "e";
        saida += "m";

        return saida;
    }

    public void drive(int time){
        if(getTime()<=0){
            System.out.println("fail: buy time first");
            return;
        }if(this.person == null){
            System.out.println("fail: empty motorcycle");
            return;
        }if(person.getAge() > 10){
            System.out.println("fail: too old to drive");
            return;
        }if(getTime()<time){
            System.out.println("fail: time finished after " + (time - this.time) + " minutes");
            this.time = 0;
            return;
        }else{
            this.time -= time;
            return;
        }
    }

    public int getPower() {
        return this.power;
    }
    public int getTime() {
        return this.time;
    }
    public Person getPerson() {
        return this.person;
    }

    
    //Se estiver vazio, coloca a pessoa na moto e retorna true
    public boolean enter(Person person) {
        if(this.person == null){
            this.person = person;
            return true;
        }else{
            System.out.println("fail: busy motorcycle");
            return false;
        }
    }
    public Person leave() {
        if(this.person != null){
            Person aux = this.person;
            this.person = null;
            return aux;
        }else{
            System.out.println("fail: empty motorcycle");
            return null;
        }
    }
    public String toString(){
        String saida = "power:" + this.power + ", time:" 
                        + this.time + ", person:";
        if(this.person == null) saida += "(empty)";
        else saida += "(" + this.person + ")";

        return saida;
    }
}
class Motoca{
    static Motorcycle motoca = new Motorcycle(1);

    public static void main(String[] args) {    
        while(true) {
            String line = input();
            args = line.split(" ");
            write('$' + line);

            if      (args[0].equals("show"))     { System.out.println(motoca);                         }
            else if (args[0].equals("init"))     { motoca = new Motorcycle(number(args[1]));           }  
            else if (args[0].equals("enter"))    { motoca.enter(new Person(args[1], number(args[2]))); }
            else if (args[0].equals("end"))      { break;                                              }
            else if (args[0].equals("buy"))      { motoca.buyTime(number(args[1]));                    }
            else if (args[0].equals("drive"))    { motoca.drive(number(args[1]));                      }
            else if (args[0].equals("honk"))     { System.out.println(motoca.honk());                  }
            else if (args[0].equals("leave"))    {
                Person person = motoca.leave();
                if(person != null) {
                    System.out.println(person.toString());
                }
            }
            else {
                System.out.println("fail: comando invalido");
            }
        }
    }

    static Scanner scanner = new Scanner(System.in);
    public static String input()           { return scanner.nextLine();    }
    public static void write(String value) { System.out.println(value);    }
    public static int number(String str)   { return Integer.parseInt(str); }
}