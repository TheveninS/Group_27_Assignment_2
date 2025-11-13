package TheBusiness.CustomerManagement;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Report containing analysis of all customers
 * @author kal bugrara
 */
public class CustomersReport {
    ArrayList<CustomerSummary> customerlist;
    
    public CustomersReport() {
        customerlist = new ArrayList<>();
    }
    
    public void addCustomerSummary(CustomerSummary cs) {
        customerlist.add(cs);
    }
    
    public ArrayList<CustomerSummary> getCustomerList() {
        return customerlist;
    }
    
    /**
     * Get top N customers by revenue
     */
    public ArrayList<CustomerSummary> getTopNCustomersByRevenue(int n) {
        ArrayList<CustomerSummary> sorted = new ArrayList<>(customerlist);
        
        Collections.sort(sorted, new Comparator<CustomerSummary>() {
            @Override
            public int compare(CustomerSummary cs1, CustomerSummary cs2) {
                return Integer.compare(cs2.getSalesRevenue(), cs1.getSalesRevenue());
            }
        });
        
        int limit = Math.min(n, sorted.size());
        return new ArrayList<>(sorted.subList(0, limit));
    }
    
    /**
     * Get customers with most orders above target
     */
    public ArrayList<CustomerSummary> getBestPerformingCustomers(int n) {
        ArrayList<CustomerSummary> sorted = new ArrayList<>(customerlist);
        
        Collections.sort(sorted, new Comparator<CustomerSummary>() {
            @Override
            public int compare(CustomerSummary cs1, CustomerSummary cs2) {
                return Integer.compare(cs2.getNumberOfOrdersAboveTarget(), 
                                     cs1.getNumberOfOrdersAboveTarget());
            }
        });
        
        int limit = Math.min(n, sorted.size());
        return new ArrayList<>(sorted.subList(0, limit));
    }
    
    /**
     * Get total revenue from all customers
     */
    public int getTotalRevenue() {
        int total = 0;
        for (CustomerSummary cs : customerlist) {
            total += cs.getSalesRevenue();
        }
        return total;
    }
    
    /**
     * Get total number of orders
     */
    public int getTotalOrders() {
        int total = 0;
        for (CustomerSummary cs : customerlist) {
            total += cs.getNumberOfOrders();
        }
        return total;
    }
    
    /**
     * Print customer report
     */
    public void printReport() {
        System.out.println("\n=== CUSTOMERS REPORT ===");
        System.out.println("Total Customers: " + customerlist.size());
        System.out.println("Total Revenue: $" + getTotalRevenue());
        System.out.println("Total Orders: " + getTotalOrders());
        
        System.out.println("\nTop 10 Customers by Revenue:");
        ArrayList<CustomerSummary> topCustomers = getTopNCustomersByRevenue(10);
        int rank = 1;
        for (CustomerSummary cs : topCustomers) {
            System.out.println(rank + ". " + cs.toString());
            rank++;
        }
        System.out.println("========================\n");
    }
}