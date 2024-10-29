/**
 * The goal of this activity is to implement a mechanical pencil that allows 
 * inserting and removing lead, as well as writing on a sheet of paper, 
 * taking into account the hardness and size of the lead.
 *
 * Description:
 * The mechanical pencil is capable of initializing, inserting, and removing lead, 
 * in addition to writing on a sheet of paper.
 * To insert lead, it is necessary to specify the thickness (float), 
 * the hardness (string), and the size in mm (int).
 * The removal of the lead is only possible if there is any in the pencil.
 * Writing on the sheet is only possible if there is enough lead and if 
 * the size of the lead is greater than 10mm.
 * The amount of lead used varies according to the hardness of the lead. 
 * The softer the lead, the more it wears down.
 * When the size of the lead reaches 10mm, writing is no longer possible.
 * If there is not enough lead to finish writing on the sheet, 
 * a warning message for incomplete text is issued.
 *
 * Responsibilities:
 * The Lead class is responsible for storing the information about the lead.
 * - thickness: the thickness and will have values like 0.3, 0.5, 0.7.
 * - hardness: the hardness can have the following values: HB, 2B, 4B, 6B.
 * The method usagePerSheet returns the amount of lead used per sheet.
 * - A HB lead uses 1mm per sheet.
 * - A 2B lead uses 2mm per sheet.
 * - A 4B lead uses 4mm per sheet.
 * - A 6B lead uses 6mm per sheet.
 * - size: represents the size of the lead in millimeters.
 *
 * The Pencil class is responsible for managing the operations of inserting, 
 * removing lead, and writing on the sheet. 
 * It references a single lead object as an attribute.
 * It also has a thickness indicator.
 *
 * The Adapter class integrates the test calls and the Lead and Pencil classes.
 * A method of special attention is the insert method. 
 * In it, the Adapter receives the data from the Lead.
 * It should create the Lead object and pass it to the pencil it manages.
 */


import java.text.DecimalFormat;
import java.util.Scanner;

class Lead {
    private float thickness; //calibre
    private String hardness; //dureza
    private int size; //tamanho em mm

    public Lead(float thickness, String hardness, int size) {
        this.hardness = hardness;
        this.thickness = thickness;
        this.size = size;

    }

    public float getThickness() {
        return this.thickness;
    }

    public String getHardness() {
        return this.hardness;
    }

    public int getSize() {
        return this.size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int usagePerSheet() {
        if(hardness.equals("HB"))
            return 1;
        else if(hardness.equals("2B"))
            return 2;
        else if(hardness.equals("4B"))
            return 4;
        else
            return 6;
    }

    public String toString() {
        DecimalFormat form = new DecimalFormat("0.0");
        return form.format(thickness) + ":" + hardness + ":" + size;
    }
}


class Pencil {
    private float thickness;
    private Lead tip;

    public Pencil(float thickness) {
        this.thickness = thickness;
        this.tip = null;
    }

    public float getThickness() {
        return this.thickness;
    }

    public void setThickness(float value) {
        this.thickness = value;
    }

    public boolean hasGrafite() {
        if(this.tip == null){
            return false;
        } else {
            return true;
        }
    }

    public boolean insert(Lead grafite) {
        if(hasGrafite()){
                System.out.println("fail: ja existe grafite");
                return false;
        }else{
            if(grafite.getThickness() != this.thickness){
                System.out.println("fail: calibre incompativel");
                return false;
            }else{
                this.tip = grafite;
                return true;
            }
        }
    }

    public Lead remove() {
         if(hasGrafite()){
            Lead aux = this.tip;
            this.tip = null;
            return aux;
        }else{
            System.out.println("fail: nao existe grafite");
                return null;
        }
    }

    public void writePage() {
        if(!hasGrafite()){
            System.out.println("fail: nao existe grafite");
            return;
        }else if(tip.getSize() == 10){
             System.out.println("fail: tamanho insuficiente");
             return;
        }else{
            tip.setSize(tip.getSize() - tip.usagePerSheet());
            if(tip.getSize() <=9){
                System.out.println("fail: folha incompleta");
                 tip.setSize(10);
            }
        }
    }
    
    public String toString() {
        String saida = "calibre: " + thickness + ", grafite: ";
        if (tip != null)
            saida += "[" + tip + "]";
        else
            saida += "null";
        return saida;
    }
}

public class Grafite {
    public static void main(String[] args) {
        Pencil pencil = new Pencil(0.5f);

        while (true) {
            String line = input();
            String[] argsL = line.split(" ");
            write('$' + line);

            if      ("end".equals(argsL[0])   ) { break;                                                                    }
            else if ("init".equals(argsL[0])  ) { pencil = new Pencil(number(argsL[1]));                                       }
            else if ("insert".equals(argsL[0])) { pencil.insert(new Lead(number(argsL[1]), argsL[2], (int) number(argsL[3]))); }
            else if ("remove".equals(argsL[0])) { pencil.remove();                                                             }
            else if ("write".equals(argsL[0]) ) { pencil.writePage();                                                          }
            else if ("show".equals(argsL[0])  ) { write(pencil.toString());                                                               }
        }
    }

    static Scanner scanner = new Scanner(System.in);

    public static String input()           { return scanner.nextLine();    }
    public static void write(String value) { System.out.println(value);    }
    public static float number(String str) { return Float.parseFloat(str); }
}