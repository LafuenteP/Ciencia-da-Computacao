import java.util.*;

class Client {
    private String id;
    private String fone;
    public Client(String id, String fone) {
        this.id = id;
        this.fone = fone;
    }

    @Override
    public String toString() {
        return id + ":" + fone;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFone() {
        return this.fone;
    }

    public void setFone(String fone) {
        this.fone = fone;
    }
}


class Sala{
    private ArrayList<Client> cadeiras;

    private int procurar(String id){
        for(int i=0; i<this.cadeiras.size();i++){
            if(this.cadeiras.get(i) != null){
                if(this.cadeiras.get(i).getId().equals(id)){
                    return i;
                }
            }
        }
        return -1;
    }


    public Sala(int capacidade) {
        this.cadeiras = new ArrayList<Client>();
        for(int i=0; i<capacidade;i++){
            this.cadeiras.add(null);
        }
    }

    public List<Client> getCadeiras() {
        return this.cadeiras;
    }
    
    public boolean reservar(String id, String fone, int ind) {
        Client cliente = new Client(id, fone);
        if(ind > this.cadeiras.size()-1 || ind < 0){
            System.out.println("fail: cadeira nao existe");
            return false;
        }
        if(this.procurar(id) != -1){
             System.out.println("fail: cliente ja esta no cinema");
            return false;
        }
        if(this.cadeiras.get(ind)==null){
            this.cadeiras.set(ind, cliente);
            return true;
        }else{
            System.out.println("fail: cadeira ja esta ocupada");
            return false;
        }
    }

    public void cancelar(String id) {
        if(this.procurar(id)== -1){
            System.out.println("fail: cliente nao esta no cinema");
            return;
        }
        this.cadeiras.set(this.procurar(id), null);
        return;
    }

    @Override
    public String toString() {
        String saida = "[";
        int Contador = 0;
        for (Client cliente : cadeiras) {
            if(cliente == null){
                saida += "-";
            }else
                saida += cliente + "";
               
            if(Contador != cadeiras.size() - 1)
                saida += " ";
            Contador++;
        }
        return saida + "]";
    }
}

class cinema {
    static Shell sh = new Shell();
    static Sala sala = new Sala(0);

    public static void main(String args[]) {
        sh.chain.put("init",     () -> { sala = new Sala(getInt(1));});
        sh.chain.put("show",     () -> { System.out.println(sala);});
        sh.chain.put("reservar", () -> { sala.reservar(getStr(1), getStr(2), getInt(3));});
        sh.chain.put("cancelar", () -> { sala.cancelar(getStr(1));});

        sh.execute();
    }

    static int getInt(int pos) {
        return Integer.parseInt(sh.param.get(pos));
    }
    static String getStr(int pos) {
        return sh.param.get(pos);
    }
}

class Shell {    
    public Scanner scanner = new Scanner(System.in);
    public HashMap<String, Runnable> chain = new HashMap<>();
    public ArrayList<String> param = new ArrayList<>();
    public Shell() {
        Locale.setDefault(new Locale("en", "US"));
    }
    public void execute() {
        while(true) {
            param.clear();
            String line = scanner.nextLine();
            Collections.addAll(param, line.split(" "));
            System.out.println("$" + line);
            if(param.get(0).equals("end")) {
                break;
            } else if (chain.containsKey(param.get(0))) {
                chain.get(param.get(0)).run();
            } else {
                System.out.println("fail: comando invalido");
            }
        }
    }
}

