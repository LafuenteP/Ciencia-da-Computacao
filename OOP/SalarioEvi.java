import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

public class MsgException extends RuntimeException {
    public MsgException(String message){
        super(message); 
    }
}
abstract class Funcionario {
    protected String nome;
    protected int bonus;
    protected int diarias;
    protected int maxDiarias;

    public Funcionario(String nome){
        this.nome=nome;
        this.bonus=0;
        this.diarias=0;
        this.maxDiarias=0;
    }
    public String getNome(){
        return this.nome;
    }
    public void setBonus(int bonus){
        this.bonus = bonus;
    }
      
    //se não atingiu o máximo, adicione mais uma diária
    //se atingiu o máximo, lance uma MsgException
    public void addDiaria(){
         if(diarias<maxDiarias){
            diarias++;
         }
         else{
            throw new MsgException("fail:limite de diarias atingido");
         }
         
    }
    //retorna bonus + diarias * 100
    public int getSalario(){
        return this.bonus + this.diarias * 100;
    }
        
}
class Professor extends Funcionario {
    protected String classe;
    //inicializa classe e muda maxDiarias para 2
    public Professor(String nome, String classe){
        super(nome);
        this.classe=classe;
        maxDiarias=2;
    }
    public String getClasse(){
        return this.classe;
    }
    //lógica do salário do professor
    //usa o super.getSalario para pegar bonus e diarias
    public int getSalario(){
        if (this.classe.equals("A")){
            return 3000 + super.getSalario();
        }
        else if (this.classe.equals("B")){
            return 5000 +super.getSalario();
        }
        else if (this.classe.equals("C")){
            return 7000 +super.getSalario();
        }
        else if (this.classe.equals("D")){
            return 9000 +super.getSalario();
        }
        else if (this.classe.equals("E")){
            return 11000 +super.getSalario();
        }
        else{
            return 0;
        }
    }
    public String toString(){
        return "prof:" + nome + ":" + this.classe + ":" +
        this.getSalario();
    }
}
class STA extends Funcionario {
    protected int nivel;
    public STA(String nome, int nivel){
        super(nome);
        this.nivel=nivel;
        this.maxDiarias=1;
    }
    public int getNivel(){
        return nivel;
    }
    public int getSalario(){
        return super.getSalario()+ 3000 + 300 * nivel;
    }
        
    public String toString(){
        return "sta:" + nome + ":" + nivel + ":" + getSalario();
    }
        
}
class Tercerizado extends Funcionario {
    protected int horas;
    protected boolean isSalubre = false;
    public Tercerizado(String nome, int horas, String isSalubre){
        super(nome);
        this.horas=horas;
        this.isSalubre = isSalubre.equals("sim");
    }

    public int getHoras(){
        return horas;
    }
    public String getIsSalubre(){
        if (isSalubre){
            return "sim";
        } 
        return "nao";
        
    }

    public int getSalario(){
        if(isSalubre){
            return 4*horas+ super.getSalario()+500;
        }
        return 4*horas+ super.getSalario();
    }
        
    public void addDiaria(){
        throw new MsgException ("fail: terc nao pode receber diaria");
    }
    public String toString(){
        return "ter:" + nome + ":"+ horas +":"+ getIsSalubre()+":"
        +getSalario();
    }
}
class UFC {
    private Map<String, Funcionario> funcionarios = new TreeMap<>();
    public String toString(){
        StringBuilder result = new StringBuilder();
        for(Funcionario func : funcionarios.values()){
            result.append(func.toString()).append("\n");
        }
        return result.toString().trim();
    
    }   
    
    public Funcionario getFuncionario(String nome){
        if(funcionarios.containsKey(nome)){
            return funcionarios.get(nome);
        }
        throw new MsgException("fail: ");
    }
    public void addFuncionario(Funcionario funcionario){
        if(funcionarios.containsKey(funcionario.getNome())){
            throw new MsgException("fail funcionario" + funcionario.getNome() + "ja cadastrado");
        }
        funcionarios.put(funcionario.getNome(), funcionario);
       
    }
    public void rmFuncionario(String nome){
        if(!funcionarios.containsKey(nome)){
            throw new MsgException("fail: funcionario" + nome + "nao existe");
        }
        funcionarios.remove(nome);
    }
    
    //reparte o bonus para todos os funcionarios
    public void setBonus(int bonus){
        if(funcionarios.size() == 0){
            return;
        } int bonusInd = bonus / funcionarios.size();
        for(Funcionario func : funcionarios.values()){
            func.setBonus(bonusInd);
        }
    }

class SalarioEvi {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        UFC ufc = new UFC();
        while (true) {
            try {
                String line = scanner.nextLine();
                System.out.println("$" + line);
                String ui[] = line.split(" ");
                if (ui[0].equals("end")) {
                    break;
                } else if (ui[0].equals("addProf")) {
                    ufc.addFuncionario(new Professor(ui[1], ui[2]));
                } else if (ui[0].equals("addSta")) {
                    ufc.addFuncionario(new STA(ui[1], Integer.parseInt(ui[2])));
                } else if (ui[0].equals("addTer")) {
                    ufc.addFuncionario(new Tercerizado(ui[1], Integer.parseInt(ui[2]), ui[3]));
                } else if (ui[0].equals("rm")) {
                    ufc.rmFuncionario(ui[1]);
                } else if (ui[0].equals("showAll")) {
                    System.out.println(ufc);
                } else if (ui[0].equals("show")) {
                    System.out.println(ufc.getFuncionario(ui[1]));
                } else if (ui[0].equals("addDiaria")) {
                    ufc.getFuncionario(ui[1]).addDiaria();
                } else if (ui[0].equals("setBonus")) {
                    ufc.setBonus(Integer.parseInt(ui[1]));
                } else {
                    System.out.print("fail: comando invalido");
                }
            } catch (MsgException me) {
                System.out.println(me.getMessage());
            }
        }
    }
} }
    

