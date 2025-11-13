package TheBusiness.CustomerManagement;

import TheBusiness.OrderManagement.Order;

/**
 * Summary of customer performance metrics
 * @author kal bugrara
 */
public class CustomerSummary {
    CustomerProfile customer;
    int salesRevenue;
    int numberOfOrders;
    int numberOfOrdersAboveTarget;
    int numberOfOrdersBelowTarget;
    int totalPricePerformance;
    
    public CustomerSummary(CustomerProfile cp) {
        customer = cp;
        calculateMetrics();
    }
    
    private void calculateMetrics() {
        salesRevenue = 0;
        numberOfOrders = customer.orders.size();
        numberOfOrdersAboveTarget = customer.getNumberOfOrdersAboveTotalTarget();
        numberOfOrdersBelowTarget = customer.getNumberOfOrdersBelowTotalTarget();
        totalPricePerformance = customer.getTotalPricePerformance();
        
        // Calculate total revenue
        for (Order order : customer.orders) {
            salesRevenue += order.getOrderTotal();
        }
    }
    
    public CustomerProfile getCustomer() {
        return customer;
    }
    
    public int getSalesRevenue() {
        return salesRevenue;
    }
    
    public int getNumberOfOrders() {
        return numberOfOrders;
    }
    
    public int getNumberOfOrdersAboveTarget() {
        return numberOfOrdersAboveTarget;
    }
    
    public int getNumberOfOrdersBelowTarget() {
        return numberOfOrdersBelowTarget;
    }
    
    public int getTotalPricePerformance() {
        return totalPricePerformance;
    }
    
    public String getCustomerName() {
        return customer.getCustomerId();
    }
    
    @Override
    public String toString() {
        return String.format("Customer: %s | Revenue: $%,d | Orders: %d | Above Target: %d | Below Target: %d",
            getCustomerName(), salesRevenue, numberOfOrders, 
            numberOfOrdersAboveTarget, numberOfOrdersBelowTarget);
    }
}