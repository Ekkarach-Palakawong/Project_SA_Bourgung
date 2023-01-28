package ku.cs.models;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class FarmingList {
    private ArrayList<Farming> farmings;

    public FarmingList() {
        farmings = new ArrayList<>();
    }

    public void addFarming(Farming farming){farmings.add(farming);}

    public ArrayList<Farming> getFarmings(){return farmings;}

    public int count() {return farmings.size();}

    public Farming getFarmingNumber(int num){
        Farming farming = farmings.get(num);
        return farming;
    }

    public Farming getFarmingById(String id){
        for (Farming farming : farmings) {
            if (farming.checkId(id)) {
                return farming;
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

    public boolean addFarmingInputCheck(String round){
        try
        {
            Integer.parseInt(round);
            int intRound  = Integer.parseInt(round);
            System.out.println(round + " is a valid integer");
        }
        catch (NumberFormatException e)
        {
            System.out.println(round + " is not a valid integer");
            return false;
        }
        return true;
    }

    public ArrayList<Farming> getStaffFarming(){
        ArrayList<Farming> staffFarming = new ArrayList<>();
        for (Farming farming : farmings){
            if (farming.getFarmingStatus().equals("ปกติ") || farming.getFarmingStatus().equals("ป่วย")){
                staffFarming.add(farming);
            }
        }

        return staffFarming;
    }

    public ArrayList<Farming> getManagerFarming(){
        ArrayList<Farming> managerFarming = new ArrayList<>();
        for (Farming farming : farmings){
            if (farming.getFarmingStatus().equals("ปกติ")){
                managerFarming.add(farming);
            }
        }

        return managerFarming;
    }

    public boolean checkDateInManagePrawn(String inputDate, String selectedFarmingDate){
        SimpleDateFormat sdfrmt = new SimpleDateFormat("yyyy-MM-dd");
        sdfrmt.setLenient(false);
        try
        {
            Date managePrawnDate = sdfrmt.parse(inputDate);
            Date farmingDate = sdfrmt.parse(selectedFarmingDate);

            if (managePrawnDate.compareTo(farmingDate) > 0){
                return true;
            }
            else if (managePrawnDate.compareTo(farmingDate) == 0){
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

    public boolean checkDateInQc(String inputDate, String selectedFarmingDate){
        SimpleDateFormat sdfrmt = new SimpleDateFormat("yyyy-MM-dd");
        sdfrmt.setLenient(false);
        try
        {
            Date qcDate = sdfrmt.parse(inputDate);
            Date farmingDate = sdfrmt.parse(selectedFarmingDate);

            System.out.println("qcDate is " + qcDate);
            System.out.println("farmingDate is " + farmingDate);

            if (qcDate.compareTo(farmingDate) > 0){
                return true;
            }
            else if (qcDate.compareTo(farmingDate) == 0){
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
