package ku.cs.models;

import java.util.ArrayList;

public class CustomerList {
    private ArrayList<Customer> customers;

    public CustomerList() {
        customers = new ArrayList<>();
    }

    public void addCustomer(Customer customer){customers.add(customer);}

    public ArrayList<Customer> getAllCustomers(){return customers;}

    public int count() {return customers.size();}

    public Customer getCustomerNumber(int num){
        Customer customer = customers.get(num);
        return customer;
    }

    public Customer getCustomerById(String id){
        for (Customer customer : customers) {
            if (customer.checkId(id)) {
                return customer;
            }
        }
        return null;
    }

    public Customer getCustomerByName(String name){
        for (Customer customer : customers) {
            if (customer.checkName(name)) {
                System.out.println("customer" + customer.getId());
                return customer;
            }
        }
        System.out.println("Fail");
        return null;
    }

    public boolean checkCustomerByName(String name){
        for (Customer customer : customers) {
            if (customer.checkName(name)) {
                return false;
            }
        }
        return true;
    }

}
