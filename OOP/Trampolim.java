import java.util.LinkedList;
import java.util.Scanner;
import java.util.stream.Collectors;



class Kid {
    private int age;
    private String name;

    public Kid(String name, int age) {
        this.name = name;
        this.age = age;
    }
    public int getAge() {
        return age;
    }
    public String getName() {
        return name;
    }
    public void setAge(int age) {
        this.age = age;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String toString() {
        return name + ":" + age;
    }
}

class Trampoline{
    private LinkedList<Kid> waiting;
    private LinkedList<Kid> playing;
    
    public Trampoline() {
        this.waiting = new LinkedList<Kid>();
        this.playing = new LinkedList<Kid>();
    }

    private Kid removeFromList(String name, LinkedList<Kid> list) {
        for(int i=0;i<list.size();i++){
            if(list.get(i).getName() == name){
               return list.remove(i);
            }
        }
        return null;
    }

    public void arrive(Kid kid) {
        this.waiting.addFirst(kid);
        return;
    }

    public void enter() {
        this.playing.add(this.waiting.removeLast());
        return;
    }

    public void leave() {
        this.waiting.add(this.playing.removeLast());
        return;
    }

    public Kid remoteKid(String name) {
       Kid Aux1, Aux2;
       Aux1 = removeFromList(name, this.playing);
       Aux2 = removeFromList(name, this.playing);

       if(Aux1 != null){
            return Aux1;
       }else{
            return Aux2;
       }
    }

    public String toString() {
        return   "[" + waiting.stream().map(Kid::toString).collect(Collectors.joining(", ")) + "]" + " => "
               + "[" + playing.stream().map(Kid::toString).collect(Collectors.joining(", ")) + "]";
    }
}


class Pula_Pula {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Trampoline tramp = new Trampoline();
        while(true) {
            String line = scanner.nextLine();
            System.out.println("$"+ line);
            String[] ui = line.split(" ");
            if(ui[0].equals("end")) {
                break;
            } else if(ui[0].equals("arrive")) { // name age
                tramp.arrive(new Kid(ui[1], Integer.parseInt(ui[2]))) ;
            } else if(ui[0].equals("enter")) {
                tramp.enter();
            } else if(ui[0].equals("leave")) {
                tramp.leave();
            } else if(ui[0].equals("remoteKid")) {//name
                tramp.remoteKid(ui[1]);
            } else if(ui[0].equals("show")) {
                System.out.println(tramp);
            } else {
                System.out.println("fail: comando invalido");
            }
        }
        scanner.close();
    }
}
