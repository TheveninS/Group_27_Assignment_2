/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TheBusiness.CustomerManagement;

import java.util.ArrayList;
import TheBusiness.MarketModel.Market;
import TheBusiness.OrderManagement.Order;
import TheBusiness.Personnel.Person;
import TheBusiness.Personnel.Profile;

/**
 *
 * @author kal bugrara
 */
public class CustomerProfile extends Profile {

    ArrayList<Order> orders;
    ArrayList<Market> markets;
    Person person;

    public CustomerProfile(Person p) {
        super(p);
        person = p;
        orders = new ArrayList();
    }
    
    @Override
    public String getRole(){
        return "Sales";
    }

    public int getTotalPricePerformance() {
        int sum = 0;
        for (Order o : orders) {
            sum += o.getOrderPricePerformance();
        }
        return sum;
    }

    public int getNumberOfOrdersAboveTotalTarget() {
        int sum = 0;
        for (Order o : orders) {
            if (o.isOrderAboveTotalTarget() == true) {
                sum = sum + 1;
            }
        }
        return sum;
    }

    public int getNumberOfOrdersBelowTotalTarget() {
        int sum = 0;
        for (Order o : orders) {
            if (!o.isOrderAboveTotalTarget()) {
                sum = sum + 1;
            }
        }
        return sum;
    }
    
    public int getTotalRevenue() {
        int sum = 0;
        for (Order o : orders) {
            sum += o.getOrderTotal();
        }
        return sum;
    }
    
    public ArrayList<Order> getOrders() {
        return orders;
    }

    public boolean isMatch(String id) {
        if (person.getPersonId().equals(id)) {
            return true;
        }
        return false;
    }

    public void addCustomerOrder(Order o) {
        orders.add(o);
    }

    @Override
    public String toString() {
        return person.getPersonId();
    }

    public String getCustomerId() {
        return person.getPersonId();
    }

    public Person getPerson() {
        return person;
    }
}