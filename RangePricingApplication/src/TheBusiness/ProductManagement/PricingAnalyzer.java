package TheBusiness.ProductManagement;

import TheBusiness.Business.Business;
import TheBusiness.Supplier.Supplier;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Analyzes pricing performance across all products
 * @author Your Name
 */
public class PricingAnalyzer {
    
    private Business business;
    
    public PricingAnalyzer(Business business) {
        this.business = business;
    }
    
    /**
     * Analyze all products and return their performance metrics
     */
    public Map<Product, ProductPerformance> analyzeAllProducts() {
        Map<Product, ProductPerformance> results = new HashMap<>();
        
        // Loop through all suppliers
        for (Supplier supplier : business.getSupplierDirectory().getSuplierList()) {
            
            // Loop through all products for this supplier
            for (Product product : supplier.getProductCatalog().getProductList()) {
                
                // Calculate performance for this product
                ProductPerformance performance = new ProductPerformance(product);
                results.put(product, performance);
            }
        }
        
        return results;
    }
    
    /**
     * Get only products that have sales data
     */
    public ArrayList<ProductPerformance> getProductsWithSales() {
        ArrayList<ProductPerformance> withSales = new ArrayList<>();
        
        Map<Product, ProductPerformance> allProducts = analyzeAllProducts();
        
        for (ProductPerformance perf : allProducts.values()) {
            if (perf.getTotalQuantitySold() > 0) {
                withSales.add(perf);
            }
        }
        
        return withSales;
    }
    
    /**
     * Get products that consistently sell below target
     */
    public ArrayList<ProductPerformance> getProductsBelowTarget() {
        ArrayList<ProductPerformance> belowTarget = new ArrayList<>();
        
        Map<Product, ProductPerformance> allProducts = analyzeAllProducts();
        
        for (ProductPerformance perf : allProducts.values()) {
            if (perf.isConsistentlyBelowTarget()) {
                belowTarget.add(perf);
            }
        }
        
        return belowTarget;
    }
    
    /**
     * Get products that consistently sell above target
     */
    public ArrayList<ProductPerformance> getProductsAboveTarget() {
        ArrayList<ProductPerformance> aboveTarget = new ArrayList<>();
        
        Map<Product, ProductPerformance> allProducts = analyzeAllProducts();
        
        for (ProductPerformance perf : allProducts.values()) {
            if (perf.isConsistentlyAboveTarget()) {
                aboveTarget.add(perf);
            }
        }
        
        return aboveTarget;
    }
    
    /**
     * Calculate total company revenue
     */
    public int getTotalCompanyRevenue() {
        int total = 0;
        
        for (ProductPerformance perf : getProductsWithSales()) {
            total += perf.getTotalRevenue();
        }
        
        return total;
    }
    
    /**
     * Calculate total company profit margin (vs target)
     */
    public int getTotalProfitMargin() {
        int total = 0;
        
        for (ProductPerformance perf : getProductsWithSales()) {
            total += perf.getPricePerformance();
        }
        
        return total;
    }
    
    /**
     * Print summary report
     */
    public void printAnalysisReport() {
        System.out.println("\n=== PRICING ANALYSIS REPORT ===");
        
        ArrayList<ProductPerformance> withSales = getProductsWithSales();
        ArrayList<ProductPerformance> belowTarget = getProductsBelowTarget();
        ArrayList<ProductPerformance> aboveTarget = getProductsAboveTarget();
        
        System.out.println("Total Products: " + business.getSupplierDirectory().getSuplierList().size() * 50);
        System.out.println("Products with Sales: " + withSales.size());
        System.out.println("Products Below Target (>70%): " + belowTarget.size());
        System.out.println("Products Above Target (>70%): " + aboveTarget.size());
        
        System.out.println("\nCompany Financials:");
        System.out.println("Total Revenue: $" + getTotalCompanyRevenue());
        System.out.println("Total Profit Margin (vs Target): $" + getTotalProfitMargin());
        
        System.out.println("\nSample Products Below Target:");
        int count = 0;
        for (ProductPerformance perf : belowTarget) {
            System.out.println("  " + perf.toString());
            count++;
            if (count >= 5) break; // Show first 5
        }
        
        System.out.println("\nSample Products Above Target:");
        count = 0;
        for (ProductPerformance perf : aboveTarget) {
            System.out.println("  " + perf.toString());
            count++;
            if (count >= 5) break; // Show first 5
        }
        
        System.out.println("================================\n");
    }
}