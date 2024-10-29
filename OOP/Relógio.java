import java.util.*;

// Nesse rascunho, falta a parte do nextSecond

class Time {
    private int hour   = 0;
    private int minute = 0;
    private int second = 0;

    public Time(int hour, int minute, int second) {
        setHour(hour);
        setMinute(minute);
        setSecond(second);
    }

    public void nextSecond(){
        this.second = getSecond() + 1;
        if(getSecond()>=60){
            this.second = 0;
            this.minute = getMinute() + 1;
        }
        if(getMinute()>=60){
            this.minute = 0;
            this.hour = getHour() + 1;
        }
        if(getHour()>=24){
            this.hour = 0;
            this.minute = 0;
            this.second = 0;
        }
    }

    public void setHour(int hour) {
         if(hour >= 24){
            System.out.println("fail: hora invalida");
        }else{
            this.hour= hour;
        }
    }
    public void setMinute(int minute) {
        if(minute >= 60){
             System.out.println("fail: minuto invalido");
        }else{
            this.minute = minute;
        }
    }
    public void setSecond(int second) {
         if(second >=  60){
             System.out.println("fail: segundo invalido");
        }else{
            this.second = second;
        }
    }
    public int getHour() {
        return hour;
    }
    public int getMinute() {
        return minute;
    }
    public int getSecond() {
        return second;
    }
    public String toString() {
        return String.format("%02d:%02d:%02d", hour, minute, second);
    }
}

public class Relógio {
    public static void main(String[] a) {
        Time time = new Time(0, 0, 0);
        
        while (true) {
            var line = input();
            write("$" + line);
            var args = line.split(" ");

            
            if (args[0].equals("show"))  { 
                write("" + time); 
            }
            else if (args[0].equals("init")) {
                time = new Time((int)number(args[1]), (int)number(args[2]), (int)number(args[3]));
            }
            else if (args[0].equals("set")) {
                time.setHour((int)number(args[1]));
                time.setMinute((int)number(args[2]));
                time.setSecond((int)number(args[3]));
            }
            else if (args[0].equals("end"))   { 
                break; 
            }
             else if (args[0].equals("next"))   { 
                time.nextSecond();
            }
            else { 
                write("fail: comando invalido"); 
            }
        }
    }

    private static Scanner scanner = new Scanner(System.in);
    private static String  input()              { return scanner.nextLine(); }
    private static double  number(String value) { return Double.parseDouble(value); }
    private static void    write(String value)  { System.out.println(value); }
}