package TheBusiness.ProductManagement;

import java.util.ArrayList;
import java.util.Map;

/**
 * Simulates the impact of price adjustments
 * @author Syrill
 */
public class SimulationEngine {
    
    /**
     * Simulate what happens if we apply the given price adjustments
     * 
     * Assumption: If we change the target price, future sales will happen 
     * closer to the new target (instead of current actual prices)
     */
    public SimulationResult simulate(
            ArrayList<PriceAdjustment> adjustments,
            Map<Product, ProductPerformance> currentAnalysis) {
        
        SimulationResult result = new SimulationResult();
        
        // Calculate current state (baseline)
        int currentTotalRevenue = 0;
        int currentTotalMargin = 0;
        
        for (ProductPerformance perf : currentAnalysis.values()) {
            currentTotalRevenue += perf.getTotalRevenue();
            currentTotalMargin += perf.getPricePerformance();
        }
        
        result.setCurrentRevenue(currentTotalRevenue);
        result.setCurrentProfitMargin(currentTotalMargin);
        
        // Simulate new state after adjustments
        int projectedRevenue = currentTotalRevenue;
        int projectedMargin = currentTotalMargin;
        
        for (PriceAdjustment adjustment : adjustments) {
            Product product = adjustment.getProduct();
            ProductPerformance perf = currentAnalysis.get(product);
            
            // Skip products with no sales
            if (perf == null || perf.getTotalQuantitySold() == 0) {
                continue;
            }
            
            int oldTarget = adjustment.getCurrentTarget();
            int newTarget = adjustment.getSuggestedTarget();
            int quantitySold = perf.getTotalQuantitySold();
            int oldAvgPrice = perf.getAvgActualPrice();
            
            // Remove old contribution from totals
            projectedRevenue -= perf.getTotalRevenue();
            projectedMargin -= perf.getPricePerformance();
            
            // Simulate new behavior:
            // Assumption: Sales will shift toward the new target
            // New avg price = weighted average of old avg and new target
            int newAvgPrice = (oldAvgPrice + newTarget) / 2;
            
            // Calculate new revenue and margin
            int newRevenue = newAvgPrice * quantitySold;
            int newMargin = (newAvgPrice - newTarget) * quantitySold;
            
            // Add new contribution to totals
            projectedRevenue += newRevenue;
            projectedMargin += newMargin;
            
            result.addAppliedAdjustment(adjustment);
        }
        
        result.setProjectedRevenue(projectedRevenue);
        result.setRevenueDelta(projectedRevenue - currentTotalRevenue);
        
        result.setProjectedProfitMargin(projectedMargin);
        result.setProfitMarginDelta(projectedMargin - currentTotalMargin);
        
        result.setAdjustmentsCount(adjustments.size());
        
        return result;
    }
    
    /**
     * Simulate applying only LOWER adjustments
     */
    public SimulationResult simulateLowerAdjustments(
            ArrayList<PriceAdjustment> allAdjustments,
            Map<Product, ProductPerformance> currentAnalysis) {
        
        ArrayList<PriceAdjustment> lowerOnly = new ArrayList<>();
        for (PriceAdjustment adj : allAdjustments) {
            if (adj.getDirection().equals("LOWER")) {
                lowerOnly.add(adj);
            }
        }
        
        return simulate(lowerOnly, currentAnalysis);
    }
    
    /**
     * Simulate applying only HIGHER adjustments
     */
    public SimulationResult simulateHigherAdjustments(
            ArrayList<PriceAdjustment> allAdjustments,
            Map<Product, ProductPerformance> currentAnalysis) {
        
        ArrayList<PriceAdjustment> higherOnly = new ArrayList<>();
        for (PriceAdjustment adj : allAdjustments) {
            if (adj.getDirection().equals("HIGHER")) {
                higherOnly.add(adj);
            }
        }
        
        return simulate(higherOnly, currentAnalysis);
    }
    
    /**
     * Print simulation report
     */
    public void printSimulationReport(SimulationResult result) {
        System.out.println("\n=== SIMULATION RESULTS ===");
        System.out.println(result.toString());
        System.out.println("==========================\n");
    }
    
    /**
     * Compare multiple simulation scenarios
     */
    public void compareScenarios(
            SimulationResult baseline,
            SimulationResult lowerOnly,
            SimulationResult higherOnly,
            SimulationResult all) {
        
        System.out.println("\n=== SCENARIO COMPARISON ===");
        System.out.println("\nBaseline (No Changes):");
        System.out.println("  Revenue: $" + baseline.getCurrentRevenue());
        System.out.println("  Margin: $" + baseline.getCurrentProfitMargin());
        
        System.out.println("\nScenario A: Lower Targets Only (" + lowerOnly.getAdjustmentsCount() + " products)");
        System.out.println("  Projected Revenue: $" + lowerOnly.getProjectedRevenue() + 
                          String.format(" (%+.2f%%)", lowerOnly.getRevenueChangePercentage()));
        System.out.println("  Projected Margin: $" + lowerOnly.getProjectedProfitMargin() + 
                          String.format(" (%+d)", lowerOnly.getProfitMarginDelta()));
        
        System.out.println("\nScenario B: Higher Targets Only (" + higherOnly.getAdjustmentsCount() + " products)");
        System.out.println("  Projected Revenue: $" + higherOnly.getProjectedRevenue() + 
                          String.format(" (%+.2f%%)", higherOnly.getRevenueChangePercentage()));
        System.out.println("  Projected Margin: $" + higherOnly.getProjectedProfitMargin() + 
                          String.format(" (%+d)", higherOnly.getProfitMarginDelta()));
        
        System.out.println("\nScenario C: All Adjustments (" + all.getAdjustmentsCount() + " products)");
        System.out.println("  Projected Revenue: $" + all.getProjectedRevenue() + 
                          String.format(" (%+.2f%%)", all.getRevenueChangePercentage()));
        System.out.println("  Projected Margin: $" + all.getProjectedProfitMargin() + 
                          String.format(" (%+d)", all.getProfitMarginDelta()));
        
        System.out.println("\n===========================\n");
    }
}