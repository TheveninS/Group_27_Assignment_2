package TheBusiness.ProductManagement;

import TheBusiness.OrderManagement.OrderItem;

/**
 * Holds calculated performance metrics for a single product
 * @author Syrill
 */
public class ProductPerformance {
    
    private Product product;
    private int totalRevenue;
    private int totalQuantitySold;
    private int avgActualPrice;
    private int frequencyBelowTarget;
    private int frequencyAboveTarget;
    private int frequencyAtTarget;
    private int pricePerformance; // total profit margin vs target
    
    public ProductPerformance(Product product) {
        this.product = product;
        calculateMetrics();
    }
    
    /**
     * Calculate all metrics from the product's order items
     */
    private void calculateMetrics() {
        if (product.orderitems.isEmpty()) {
            // No sales yet - all metrics stay at 0
            return;
        }
        
        totalRevenue = 0;
        totalQuantitySold = 0;
        frequencyBelowTarget = 0;
        frequencyAboveTarget = 0;
        frequencyAtTarget = 0;
        pricePerformance = 0;
        
        // Loop through all sales of this product
        for (OrderItem item : product.orderitems) {
            int actualPrice = item.getActualPrice();
            int targetPrice = product.getTargetPrice();
            int quantity = item.getQuantity();
            
            // Calculate revenue
            totalRevenue += item.getOrderItemTotal(); // actualPrice * quantity
            totalQuantitySold += quantity;
            
            // Count frequency above/below/at target
            if (actualPrice < targetPrice) {
                frequencyBelowTarget++;
            } else if (actualPrice > targetPrice) {
                frequencyAboveTarget++;
            } else {
                frequencyAtTarget++;
            }
            
            // Calculate price performance (margin vs target)
            pricePerformance += item.calculatePricePerformance();
        }
        
        // Calculate average actual selling price
        if (totalQuantitySold > 0) {
            avgActualPrice = totalRevenue / totalQuantitySold;
        }
    }
    
    /**
     * Check if product consistently sells below target (>70% of sales)
     */
    public boolean isConsistentlyBelowTarget() {
        int totalSales = frequencyBelowTarget + frequencyAboveTarget + frequencyAtTarget;
        if (totalSales == 0) return false;
        
        return (frequencyBelowTarget * 100.0 / totalSales) > 70;
    }
    
    /**
     * Check if product consistently sells above target (>70% of sales)
     */
    public boolean isConsistentlyAboveTarget() {
        int totalSales = frequencyBelowTarget + frequencyAboveTarget + frequencyAtTarget;
        if (totalSales == 0) return false;
        
        return (frequencyAboveTarget * 100.0 / totalSales) > 70;
    }
    
    // Getters
    public Product getProduct() {
        return product;
    }
    
    public int getTotalRevenue() {
        return totalRevenue;
    }
    
    public int getTotalQuantitySold() {
        return totalQuantitySold;
    }
    
    public int getAvgActualPrice() {
        return avgActualPrice;
    }
    
    public int getFrequencyBelowTarget() {
        return frequencyBelowTarget;
    }
    
    public int getFrequencyAboveTarget() {
        return frequencyAboveTarget;
    }
    
    public int getFrequencyAtTarget() {
        return frequencyAtTarget;
    }
    
    public int getPricePerformance() {
        return pricePerformance;
    }
    
    @Override
    public String toString() {
        return String.format("Product: %s | Revenue: $%d | Avg Price: $%d | Below: %d | Above: %d | Margin: $%d",
                product.toString(),
                totalRevenue,
                avgActualPrice,
                frequencyBelowTarget,
                frequencyAboveTarget,
                pricePerformance);
    }
}