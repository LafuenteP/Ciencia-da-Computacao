import java.util.*;

/**
 * The objective of this activity is to implement a battery-powered calculator. If there is battery, it can perform addition and division operations. 
 * It is also possible to display the battery level and recharge the calculator. It will alert when the battery is low and if there is an attempt to divide by zero.
 * 
 * Description:
 * The calculator has a display and a battery. It stores the current battery value and the maximum value.
 * The display is where the result of operations is shown.
 * The battery is the amount of energy the calculator has.
 * Each operation consumes one point of battery.
 * The calculator cannot perform operations if there is no battery.
 * The calculator cannot perform divisions by zero.
 * 
 * Responsibilities:
 * - The code should be implemented in the Calculadora class.
 * - The Student class is responsible for calling methods from the Calculadora class.
 * - The Shell class is responsible for reading text requests and invoking methods in the Student class.
 * 
 * Commands:
 * All commands follow the format $command arg1 arg2 ...
 * - $show: Displays the current display value and battery level.
 *   Format: "{this.display:.2f}, {this.battery}"
 * 
 * - $init batteryMax: Initializes the calculator with a zeroed battery and display, and sets the maximum battery value defined by the parameter.
 * 
 * - $charge value: Adds charge to the battery, but cannot exceed the limit.
 * 
 * - $sum a b: Sums two values and stores the result in the display.
 *   - If there is no battery, it emits the message "fail: bateria insuficiente".
 * 
 * - $div a b: Divides two values and stores the result in the display.
 *   - If there is no battery, it emits the message "fail: bateria insuficiente".
 *   - If there is an attempt to divide by zero, it emits the message "fail: divisao por zero".
 * 
 * - $end: Terminates execution.
 */

class Calculator {
    public int bateriaM;
    public int bateria;
    public float display;

    public Calculator(int bateriaM) {
        this.bateriaM = bateriaM;
        this.bateria = 0;
        this.display = 0.0f;
    }

    public void chargeBattery(int value) {
        if(value<0){
            return;
        }else{
            this.bateria += value;
            if(this.bateria>this.bateriaM) this.bateria = this.bateriaM;
        }
    }

    public boolean useBattery() {
        if(this.bateria == 0){
            System.out.println("fail: bateria insuficiente");
            return false;
        }else{
            this.bateria -= 1;
            return true;
        }
    }

    public void sum(int a, int b) {
        if(useBattery()){
            this.display = (a+b);
        }
    }

    public void div(int numerador, int denominador){
        if(!useBattery()){
            return;
        }
        if(denominador == 0){
            System.out.println("fail: divisao por zero");
        }else{
            this.display = (float) numerador/denominador;
        }
    }

    public String toString() {
        return String.format("display = %.2f, battery = %d", this.display, this.bateria);

        // se seu java estiver utilizando `,` como separador decimal, use:
        // DecimalFormat df = new DecimalFormat("0.00");
        // return String.format("display = %s, battery = %d", df.format(this.display), this.battery);
    }
}

public class calculadora {
    static Calculator calc = new Calculator(0);

    public static void main(String[] _args) {
        while (true) {
            String line = input();
            String[] args = line.split(" ");
            write('$' + line);

            if ("show".equals(args[0])) {
                write(calc.toString());
            }
            else if ("init".equals(args[0])) {
                calc = new Calculator((int) number(args[1]));
            }
            else if ("charge".equals(args[0])) {
                calc.chargeBattery((int) number(args[1]));
            }
            else if ("sum".equals(args[0])) {
                calc.sum((int) number(args[1]), (int) number(args[2]));
            }
            else if ("end".equals(args[0])) {
                break;
            }
            else {
                write("fail: comando invalido");
            }
        }
    }

    private static Scanner scanner = new Scanner(System.in);
    private static String input() { return scanner.nextLine(); }
    private static double number(String value) { return Double.parseDouble(value); }
    private static void write(String value) { System.out.println(value); }
}