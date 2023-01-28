package ku.cs.models;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

public class ManagePrawnList {

    private ArrayList<ManagePrawn> managePrawns;

    public ManagePrawnList() {
        managePrawns = new ArrayList<>();
    }

    public void addManagePrawn(ManagePrawn managePrawn){managePrawns.add(managePrawn);}

    public ArrayList<ManagePrawn> getAllManagePrawn(){return managePrawns;}

    public int count() {return managePrawns.size();}

    public ManagePrawn getManagePrawnNumber(int num){
        ManagePrawn managePrawn = managePrawns.get(num);
        return managePrawn;
    }

    public ManagePrawn getManagePrawnById (String id) {
        for (ManagePrawn managePrawn: managePrawns) {
            if (managePrawn.checkPrawnId(id)) {
                return managePrawn;
            }
        }
        return null;
    }

    public static boolean validateJavaDate(String strDate)
    {
        if (strDate.length() == 10){
            if (strDate.trim().equals(""))
            {
                return false;
            }
            else
            {

                SimpleDateFormat sdfrmt = new SimpleDateFormat("yyyy-MM-dd");
                sdfrmt.setLenient(false);

                try
                {
                    Date javaDate = sdfrmt.parse(strDate);
                    System.out.println(strDate+" is valid date format");
                }
                catch (ParseException e)
                {
                    System.out.println(strDate+" is Invalid Date format");
                    return false;
                }
                return true;
            }
        }
        else {
            return false;
        }
    }

    public ManagePrawn latestManagePrawn(String farmingID){
        ArrayList<Integer> id = new ArrayList<>();
        int max = 0 ;
        for (ManagePrawn managePrawn : managePrawns) {
            if (managePrawn.getFarmingID().equals(farmingID)){
                if (managePrawn.getType().equals("วัดน้ำหนัก")){
                    String idString = managePrawn.getId();
                    System.out.println("idString is: " + idString);
                    Integer idInt = Integer.parseInt(idString.substring(2));
                    id.add(idInt);
                }
            }
        }
        for (int i = 0; i < id.size(); i++){
            if (id.get(i) > max){
                max = id.get(i);
            }
        }
        System.out.println("max is: " + max);
        String managePrawnGetID = "TK"+ max;
        return  getManagePrawnById(managePrawnGetID);
    }

    public boolean checkDateInManagePrawn(String inputDate, String farmingId){
        SimpleDateFormat sdfrmt = new SimpleDateFormat("yyyy-MM-dd");
        sdfrmt.setLenient(false);
        ManagePrawn managePrawn = latestManagePrawn(farmingId);
        try
        {
            Date managePrawnDate = sdfrmt.parse(inputDate);
            Date latestManagePrawnDate = sdfrmt.parse(managePrawn.getDate());

            if (managePrawnDate.compareTo(latestManagePrawnDate) > 0){
                return true;
            }
            else if (managePrawnDate.compareTo(latestManagePrawnDate) == 0){
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
    public boolean checkDateInQC(String inputDate, String farmingId){
        SimpleDateFormat sdfrmt = new SimpleDateFormat("yyyy-MM-dd");
        sdfrmt.setLenient(false);
        ManagePrawn managePrawn = latestManagePrawn(farmingId);
        try
        {
            Date qcDate = sdfrmt.parse(inputDate);
            Date latestManagePrawnDate = sdfrmt.parse(managePrawn.getDate());

            if (qcDate.compareTo(latestManagePrawnDate) > 0){
                return true;
            }
            else if (qcDate.compareTo(latestManagePrawnDate) == 0){
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
