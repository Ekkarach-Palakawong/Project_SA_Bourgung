package ku.cs.models;

import java.util.ArrayList;

public class PrawnList {

    private ArrayList<Prawn> prawns;

    public PrawnList() {
        prawns = new ArrayList<>();
    }

    public void addPrawn(Prawn prawn){prawns.add(prawn);}

    public ArrayList<Prawn> getPrawns(){return prawns;}

    public int count() {return prawns.size();}

    public Prawn getPrawnNumber(int num){
        Prawn prawn = prawns.get(num);
        return prawn;
    }

    public Prawn getPrawnById(String id){
        for (Prawn prawn: prawns){
            if (prawn.checkPrawnId(id)){
                return prawn;
            }
        }
        return null;
    }
}
