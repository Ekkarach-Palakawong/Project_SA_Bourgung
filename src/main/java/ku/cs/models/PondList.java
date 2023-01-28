package ku.cs.models;

import java.util.ArrayList;

public class PondList {

    private ArrayList<Pond> ponds;

    public PondList() {
        ponds = new ArrayList<>();
    }

    public void addPond(Pond pond){ponds.add(pond);}

    public ArrayList<Pond> getPonds(){return ponds;}

    public int count() {return ponds.size();}

    public Pond getPondNumber(int num){
        Pond pond = ponds.get(num);
        return pond;
    }

    public Pond getPondById(String id){
        for (Pond pond : ponds) {
            if (pond.checkId(id)) {
                return pond;
            }
        }
        return null;
    }

    public ArrayList<Pond> getStaffPond(){
        ArrayList<Pond> staffPond = new ArrayList<>();
        for (Pond pond : ponds){
            if (pond.getStatus().equals("เตรียมบ่อเสร็จสิ้น")){
                staffPond.add(pond);
            }
        }

        return staffPond;
    }

    public ArrayList<Pond> getManagerPond(){
        ArrayList<Pond> managerPond = new ArrayList<>();
        for (Pond pond : ponds){
            if (pond.getStatus().equals("ยังไม่ดำเนินการ")){
                managerPond.add(pond);
            }
        }

        return managerPond;
    }
}
