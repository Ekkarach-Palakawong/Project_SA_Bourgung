package ku.cs.models;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class QCList {
    private ArrayList<QC> qualityControls;

    public QCList() {
        qualityControls = new ArrayList<>();
    }

    public void addQC(QC qc){
        qualityControls.add(qc);
    }

    public ArrayList<QC> getAllManagePrawn() {
        return qualityControls;
    }

    public int count() {
        return qualityControls.size();
    }

    public QC getQCNumber(int num){
        QC qc = qualityControls.get(num);
        return qc;
    }

    public QC getQcById(String id){
        for (QC qualityControl : qualityControls) {
            if (qualityControl.checkID(id)) {
                return qualityControl;
            }
        }
        return null;
    }
    @Override
    public String toString() {
        return "QCList{" +
                "qualityControl=" + qualityControls +
                '}';
    }

    public QC latestQC(String farmingID){
        ArrayList<Integer> id = new ArrayList<>();
        int max = 0 ;
        for (QC qc : qualityControls) {
            if (qc.getFarmingID().equals(farmingID)){
                String idString = qc.getId();
                Integer idInt = Integer.parseInt(idString.substring(2));
                id.add(idInt);
            }
        }
        for (int i = 0; i < id.size(); i++){
            if (id.get(i) > max){
                max = id.get(i);
            }
        }
        if (max == 0){
            return null;
        }
        else {
//            System.out.println("QC" + max);
            String qcGetID = "QC"+ max;
            return  getQcById(qcGetID);
        }
    }

    public boolean checkDateInQC(String inputDate, String farmingId){
        SimpleDateFormat sdfrmt = new SimpleDateFormat("yyyy-MM-dd");
        sdfrmt.setLenient(false);
        QC qc = latestQC(farmingId);
        try
        {
            Date qcDate = sdfrmt.parse(inputDate);
            Date latestQc = sdfrmt.parse(qc.getTime());

            if (qcDate.compareTo(latestQc) > 0){
                return true;
            }
            else if (qcDate.compareTo(latestQc) == 0){
                return true;
            }
            else {
                return false;
            }
        }
        catch (ParseException e)
        {
            return false;
        }
    }

    public boolean checkDateInCheckStock(String inputDate, String farmingId){
        SimpleDateFormat sdfrmt = new SimpleDateFormat("yyyy-MM-dd");
        sdfrmt.setLenient(false);
        QC latestQCDate = latestQC(farmingId);
        try
        {
            Date sellDate = sdfrmt.parse(inputDate);
            Date qcDate = sdfrmt.parse(latestQCDate.getTime());

            if (sellDate.compareTo(qcDate) > 0){
                return true;
            }
            else if (sellDate.compareTo(qcDate) == 0){
                return true;
            }
            else {
                return false;
            }
        }
        catch (ParseException e)
        {
            return false;
        }
    }
}
