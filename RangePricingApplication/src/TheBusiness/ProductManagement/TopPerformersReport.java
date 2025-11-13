package TheBusiness.ProductManagement;

import TheBusiness.Business.Business;
import TheBusiness.CustomerManagement.CustomerProfile;
import TheBusiness.CustomerManagement.CustomersReport;
import TheBusiness.CustomerManagement.CustomerSummary;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Generates reports for top performing products, customers, etc.
 * @author Your Name
 */
public class TopPerformersReport {
    
    private Business business;
    private PricingAnalyzer analyzer;
    
    public TopPerformersReport(Business business) {
        this.business = business;
        this.analyzer = new PricingAnalyzer(business);
    }
    
    /**
     * Get top N products by revenue
     */
    public ArrayList<ProductPerformance> getTopNProductsByRevenue(int n) {
        ArrayList<ProductPerformance> allProducts = analyzer.getProductsWithSales();
        
        Collections.sort(allProducts, new Comparator<ProductPerformance>() {
            @Override
            public int compare(ProductPerformance p1, ProductPerformance p2) {
                return Integer.compare(p2.getTotalRevenue(), p1.getTotalRevenue());
            }
        });
        
        int limit = Math.min(n, allProducts.size());
        return new ArrayList<>(allProducts.subList(0, limit));
    }
    
    /**
     * Get top N products above target (highest profit margin vs target)
     */
    public ArrayList<ProductPerformance> getTopNProductsAboveTarget(int n) {
        ArrayList<ProductPerformance> aboveTarget = analyzer.getProductsAboveTarget();
        
        Collections.sort(aboveTarget, new Comparator<ProductPerformance>() {
            @Override
            public int compare(ProductPerformance p1, ProductPerformance p2) {
                return Integer.compare(p2.getPricePerformance(), p1.getPricePerformance());
            }
        });
        
        int limit = Math.min(n, aboveTarget.size());
        return new ArrayList<>(aboveTarget.subList(0, limit));
    }
    
    /**
     * Get top N products below target (worst performing - lowest margin vs target)
     */
    public ArrayList<ProductPerformance> getTopNProductsBelowTarget(int n) {
        ArrayList<ProductPerformance> belowTarget = analyzer.getProductsBelowTarget();
        
        Collections.sort(belowTarget, new Comparator<ProductPerformance>() {
            @Override
            public int compare(ProductPerformance p1, ProductPerformance p2) {
                // Sort by most negative performance (worst first)
                return Integer.compare(p1.getPricePerformance(), p2.getPricePerformance());
            }
        });
        
        int limit = Math.min(n, belowTarget.size());
        return new ArrayList<>(belowTarget.subList(0, limit));
    }
    
    /**
     * Get top N customers by revenue
     */
    public ArrayList<CustomerSummary> getTopNCustomers(int n) {
        CustomersReport report = business.getCustomerDirectory().generatCustomerPerformanceReport();
        return report.getTopNCustomersByRevenue(n);
    }
    
    /**
     * Print comprehensive top performers report
     */
    public void printTopPerformersReport() {
        System.out.println("\n" + "=".repeat(80));
        System.out.println("TOP PERFORMERS REPORT");
        System.out.println("=".repeat(80));
        
        // Top 3 Products by Revenue
        System.out.println("\n--- TOP 3 PRODUCTS BY REVENUE ---");
        ArrayList<ProductPerformance> topByRevenue = getTopNProductsByRevenue(3);
        int rank = 1;
        for (ProductPerformance perf : topByRevenue) {
            System.out.println(rank + ". " + perf.toString());
            rank++;
        }
        
        // Top 3 Products Above Target
        System.out.println("\n--- TOP 3 PRODUCTS ABOVE TARGET (Best Margins) ---");
        ArrayList<ProductPerformance> topAbove = getTopNProductsAboveTarget(3);
        rank = 1;
        for (ProductPerformance perf : topAbove) {
            System.out.println(rank + ". " + perf.toString() + 
                             " [Margin: $" + perf.getPricePerformance() + "]");
            rank++;
        }
        
        // Top 3 Products Below Target (Needs Price Reduction)
        System.out.println("\n--- TOP 3 PRODUCTS BELOW TARGET (Needs Attention) ---");
        ArrayList<ProductPerformance> topBelow = getTopNProductsBelowTarget(3);
        rank = 1;
        for (ProductPerformance perf : topBelow) {
            System.out.println(rank + ". " + perf.toString() + 
                             " [Margin Loss: $" + perf.getPricePerformance() + "]");
            rank++;
        }
        
        // Top 3 Customers
        System.out.println("\n--- TOP 3 CUSTOMERS BY REVENUE ---");
        ArrayList<CustomerSummary> topCustomers = getTopNCustomers(3);
        rank = 1;
        for (CustomerSummary cs : topCustomers) {
            System.out.println(rank + ". " + cs.toString());
            rank++;
        }
        
        System.out.println("\n" + "=".repeat(80) + "\n");
    }
}