/*
!Oque fez? -
            Como o exercício foi simultanêo a aula, eu fui aprendendo o que o código pedia e simultanêamente
            fui realizando o exercício, nesse exercício eu aprofundei mais o conhecimento sobre a main e como
            ela recebe os valores e comandos digitados pelo usuário, em paralelo, também aprendi a como chamar
            as funções criadas anteriormente no código, usando "this." como referência dentro da função.
 
!Com quem fez ? -
            fiz sozinho porém simultanemente a aula, retirando algumas dúvidas somente.
 
!O que aprendeu ? - 
            Aprendi utilizar melhor os metódos e implementar-los corretamente com uma entrada especí-
            ica, ou seja, uma entrada do usuário chama um metódo.
 
!Quanto tempo levou? -
            Cerca de 2 horas.
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