package ku.cs.models;

import java.util.ArrayList;

public class PurchaseOrderList {
    private ArrayList<PurchaseOrder> purchaseOrders;

    public PurchaseOrderList() {
        purchaseOrders = new ArrayList<>();
    }

    public void addPurchaseOrder(PurchaseOrder purchaseOrder){purchaseOrders.add(purchaseOrder);}

    public ArrayList<PurchaseOrder> getPurchaseOrders(){return purchaseOrders;}

    public int count() {return purchaseOrders.size();}

    public PurchaseOrder getPurchaseOrderNumber(int num){
        PurchaseOrder purchaseOrder = purchaseOrders.get(num);
        return purchaseOrder;
    }

    public PurchaseOrder getPurchaseOrderById(String id){
        for (PurchaseOrder purchaseOrder : purchaseOrders) {
            if (purchaseOrder.checkId(id)) {
                return purchaseOrder;
            }
        }
        return null;
    }

    public ArrayList<PurchaseOrder> getStaffPurchaseOrder(){
        ArrayList<PurchaseOrder> staffPurchaseOrder = new ArrayList<>();
        for (PurchaseOrder purchaseOrder : purchaseOrders){
            if (purchaseOrder.getStatus().equals("ยังไม่ส่ง")){
                staffPurchaseOrder.add(purchaseOrder);
            }
        }

        return staffPurchaseOrder;
    }
}
