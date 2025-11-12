package TheBusiness.ProductManagement;

import java.util.ArrayList;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Final comparison report - before vs after adjustments
 * @author Syrill
 */
public class FinalReport {
    
    private ArrayList<ProductComparison> comparisons;
    private int totalRevenueBefore;
    private int totalRevenueAfter;
    private int totalMarginBefore;
    private int totalMarginAfter;
    private int productsAdjusted;
    private int productsImproved;
    
    public FinalReport() {
        comparisons = new ArrayList<>();
    }
    
    public void addComparison(ProductComparison comparison) {
        comparisons.add(comparison);
        if (comparison.isImproved()) {
            productsImproved++;
        }
    }
    
    // Getters and Setters
    public ArrayList<ProductComparison> getComparisons() {
        return comparisons;
    }
    
    public int getTotalRevenueBefore() {
        return totalRevenueBefore;
    }
    
    public void setTotalRevenueBefore(int totalRevenueBefore) {
        this.totalRevenueBefore = totalRevenueBefore;
    }
    
    public int getTotalRevenueAfter() {
        return totalRevenueAfter;
    }
    
    public void setTotalRevenueAfter(int totalRevenueAfter) {
        this.totalRevenueAfter = totalRevenueAfter;
    }
    
    public int getTotalMarginBefore() {
        return totalMarginBefore;
    }
    
    public void setTotalMarginBefore(int totalMarginBefore) {
        this.totalMarginBefore = totalMarginBefore;
    }
    
    public int getTotalMarginAfter() {
        return totalMarginAfter;
    }
    
    public void setTotalMarginAfter(int totalMarginAfter) {
        this.totalMarginAfter = totalMarginAfter;
    }
    
    public int getProductsAdjusted() {
        return productsAdjusted;
    }
    
    public void setProductsAdjusted(int productsAdjusted) {
        this.productsAdjusted = productsAdjusted;
    }
    
    public int getProductsImproved() {
        return productsImproved;
    }
    
    public int getTotalRevenueDelta() {
        return totalRevenueAfter - totalRevenueBefore;
    }
    
    public int getTotalMarginDelta() {
        return totalMarginAfter - totalMarginBefore;
    }
    
    public double getRevenueChangePercent() {
        if (totalRevenueBefore == 0) return 0;
        return (getTotalRevenueDelta() * 100.0) / totalRevenueBefore;
    }
    
    public double getMarginChangePercent() {
        if (totalMarginBefore == 0) return 0;
        return (getTotalMarginDelta() * 100.0) / Math.abs(totalMarginBefore);
    }
    
    /**
     * Print report to console
     */
    public void printReport() {
        System.out.println("\n" + "=".repeat(80));
        System.out.println("FINAL PRICING ADJUSTMENT REPORT");
        System.out.println("=".repeat(80));
        
        System.out.println("\n--- SUMMARY ---");
        System.out.println("Products Adjusted: " + productsAdjusted);
        System.out.println("Products Improved: " + productsImproved + 
                          " (" + (productsImproved * 100 / productsAdjusted) + "%)");
        
        System.out.println("\n--- FINANCIAL IMPACT ---");
        System.out.println(String.format("Revenue:       $%,d → $%,d (%+.2f%%)",
            totalRevenueBefore, totalRevenueAfter, getRevenueChangePercent()));
        System.out.println(String.format("Profit Margin: $%,d → $%,d (%+d)",
            totalMarginBefore, totalMarginAfter, getTotalMarginDelta()));
        
        System.out.println("\n--- TOP 20 IMPROVEMENTS ---");
        // Sort by margin improvement
        comparisons.sort((a, b) -> Integer.compare(b.getMarginChange(), a.getMarginChange()));
        
        int count = 0;
        for (ProductComparison comp : comparisons) {
            if (count >= 20) break;
            if (comp.getMarginChange() > 0) {
                System.out.println("  " + comp.toString());
                count++;
            }
        }
        
        System.out.println("\n" + "=".repeat(80) + "\n");
    }
    
    /**
     * Export to CSV file
     */
    public void exportToCSV(String filename) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filename))) {
            // Write header
            writer.println("Product,Old Target,New Target,Target Change," +
                          "Old Revenue,New Revenue,Revenue Change," +
                          "Old Freq Below,New Freq Below," +
                          "Old Freq Above,New Freq Above," +
                          "Old Margin,New Margin,Margin Change,Improved");
            
            // Write data rows
            for (ProductComparison comp : comparisons) {
                writer.printf("%s,%d,%d,%d,%d,%d,%d,%d,%d,%d,%d,%d,%d,%d,%s%n",
                    comp.getProductName(),
                    comp.getOldTarget(),
                    comp.getNewTarget(),
                    comp.getTargetChange(),
                    comp.getOldRevenue(),
                    comp.getNewRevenue(),
                    comp.getRevenueChange(),
                    comp.getOldFreqBelow(),
                    comp.getNewFreqBelow(),
                    comp.getOldFreqAbove(),
                    comp.getNewFreqAbove(),
                    comp.getOldMargin(),
                    comp.getNewMargin(),
                    comp.getMarginChange(),
                    comp.isImproved() ? "YES" : "NO"
                );
            }
            
            // Write summary at bottom
            writer.println();
            writer.println("SUMMARY");
            writer.printf("Total Revenue Before,$%d%n", totalRevenueBefore);
            writer.printf("Total Revenue After,$%d%n", totalRevenueAfter);
            writer.printf("Revenue Change,$%d%n", getTotalRevenueDelta());
            writer.printf("Total Margin Before,$%d%n", totalMarginBefore);
            writer.printf("Total Margin After,$%d%n", totalMarginAfter);
            writer.printf("Margin Change,$%d%n", getTotalMarginDelta());
            writer.printf("Products Adjusted,%d%n", productsAdjusted);
            writer.printf("Products Improved,%d%n", productsImproved);
            
            System.out.println("✓ Report exported to: " + filename);
            
        } catch (IOException e) {
            System.err.println("Error exporting report: " + e.getMessage());
        }
    }
}