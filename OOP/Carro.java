/**
 * In this activity, we will implement an eco-friendly car. It should be capable of boarding and deboarding people, refueling, and driving.
 * 
 * Description:
 * The car should be initialized with an empty tank, no passengers, and 0 kilometers traveled.
 * To simplify, our sports car supports up to 2 people, and its tank holds up to 100 liters of fuel.
 * 
 * Responsibilities:
 * - The code should be implemented in the Carro class.
 * - The Student class is responsible for calling methods from the Carro class.
 * - The Shell class is responsible for reading text commands and invoking methods in the Student class.
 * 
 * Commands:
 * All commands follow the format $command arg1 arg2 ...
 * - $show: Displays the current state of the car.
 *   Format: "pass:{this.pass},gas:{this.gas},km:{this.km}".
 *   Example: pass:0, gas:0, km:0.
 * 
 * - $init: Initializes all attributes.
 *   - Tank empty.
 *   - 0 passengers.
 *   - 0 kilometers traveled.
 *   - Maximum of 2 people.
 *   - Maximum of 100 liters of fuel.
 * 
 * - $enter: Boards one person at a time, but not beyond the maximum capacity.
 *   - If the car is full, displays the error message "fail: limite de pessoas atingido".
 * 
 * - $leave: Deboards one person at a time.
 *   - If there is no one in the car, displays the error message "fail: nao ha ninguem no carro".
 * 
 * - $fuel qtd: Refuels the tank with the specified number of liters.
 *   - If attempting to refuel above the limit, it discards the excess fuel.
 * 
 * - $drive dist: To drive, the car consumes fuel and increases its mileage.
 *   - Can only drive if there is fuel and at least one passenger.
 *   - If there are no passengers, displays the error message "fail: não há ninguém no carro".
 *   - If there is no fuel, displays the error message "fail: tanque vazio".
 *   - If there is insufficient fuel to complete the entire journey, drives as far as possible and displays a message indicating how far it traveled, like "fail: tanque vazio após andar {qtd} km".
 */


import java.util.Scanner;


class Car{
    int pass;
    int passMax;
    int gas;
    int gasMax;
    int km;
    
    Car() {
        this.passMax = 2;
        this.gasMax = 100;
    }
    
    void drive(int km){
        if(this.pass == 0)
            System.out.println("fail: nao ha ninguem no carro");
        else if(this.gas == 0)
            System.out.println("fail: tanque vazio");
        else if(this.gas < km){
            System.out.println("fail: tanque vazio apos andar " + this.gas + " km");
            this.km += this.gas;
            this.gas = 0;
        }
        else if(this.gas >= km){
            this.km += km;
            this.gas -= km;
        }
    }
    
    void fuel(int comb){
        this.gas += comb;
        if(this.gas > this.gasMax)
            this.gas = this.gasMax;
    }
    
    void show(){
        System.out.println(
            "pass: " + this.pass + ", " +
            "gas: " + this.gas + ", " +
            "km: " + this.km);
    }
    
    void enter(){
        if(this.pass < this.passMax)
            this.pass++;
        else 
            System.out.println("fail: limite de pessoas atingido");
    }
    
    void leave(){
        if(this.pass > 0)
            this.pass--;
        else
            System.out.println("fail: nao ha ninguem no carro");
    }
}



public class Carro {
    public static void main(String[] a) {
        Car car = new Car();
        
        while (true) {
            var line = input();
            write("$" + line);
            var args = line.split(" ");

            if      (args[0].equals("end"))   { break;                                }
            else if (args[0].equals("show"))  { car.show(); } //System.out.println(car);              }
            else if (args[0].equals("enter")) { car.enter();                          }
            else if (args[0].equals("leave")) { car.leave();                          }
            else if (args[0].equals("drive")) { car.drive((int) number(args[1]));     }
            else if (args[0].equals("fuel"))  { car.fuel((int) number(args[1]));      }
            else                              { write("fail: comando invalido");}
        }
    }

    private static Scanner scanner = new Scanner(System.in);
    private static String  input()              { return scanner.nextLine(); }
    private static double  number(String value) { return Double.parseDouble(value); }
    private static void    write(String value)  { System.out.println(value); }
}