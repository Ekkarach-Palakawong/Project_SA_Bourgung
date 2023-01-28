package ku.cs.models;

import java.util.ArrayList;

public class PreparePondList {

    private ArrayList<PreparePond> preparePonds;

    public PreparePondList() {
        preparePonds = new ArrayList<>();
    }

    public void addPreparePond(PreparePond preparePond){preparePonds.add(preparePond);}

    public ArrayList<PreparePond> getPreparePonds(){return preparePonds;}

    public int count() {return preparePonds.size();}

    public PreparePond getPreparePondNumber(int num){
        PreparePond preparePond = preparePonds.get(num);
        return preparePond;
    }

    public PreparePond getPreparePondById(String id){
        for (PreparePond preparePond : preparePonds) {
            if (preparePond.checkId(id)) {
                return preparePond;
            }
        }
        return null;
    }

    public ArrayList<PreparePond> getStaffPreparePond(String userID){
        ArrayList<PreparePond> staffPreparePond = new ArrayList<>();
        for (PreparePond preparePond : preparePonds){
            if (preparePond.getEmployeeID().equals(userID)){
                if (preparePond.getStatus().equals("รอดำเนินการ") || preparePond.getStatus().equals("กำลังดำเนินการ")){
                    staffPreparePond.add(preparePond);
                }
            }
        }

        return staffPreparePond;
    }

}
