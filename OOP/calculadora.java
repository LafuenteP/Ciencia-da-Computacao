import java.util.*;

// Esse rascunho não possui o método de divisão, nem a invocação na main.


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