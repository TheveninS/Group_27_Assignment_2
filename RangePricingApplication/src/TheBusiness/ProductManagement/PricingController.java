package TheBusiness.ProductManagement;

import TheBusiness.Business.Business;
import TheBusiness.Supplier.Supplier;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Main controller - orchestrates the entire pricing optimization workflow
 * @author Syrill
 */
public class PricingController {
    
    private Business business;
    private PricingAnalyzer analyzer;
    private RecommendationEngine recommendationEngine;
    private SimulationEngine simulationEngine;
    
    public PricingController(Business business) {
        this.business = business;
        this.analyzer = new PricingAnalyzer(business);
        this.recommendationEngine = new RecommendationEngine();
        this.simulationEngine = new SimulationEngine();
    }
    
    /**
     * Apply price adjustments to products
     */
    public void applyAdjustments(ArrayList<PriceAdjustment> adjustments) {
        System.out.println("\nApplying " + adjustments.size() + " price adjustments...");
        
        for (PriceAdjustment adj : adjustments) {
            Product product = adj.getProduct();
            int newTarget = adj.getSuggestedTarget();
            
            // Update the product's target price
            product.updateProduct(
                product.getFloorPrice(),
                product.getCeilingPrice(),
                newTarget
            );
        }
        
        System.out.println("✓ Price adjustments applied successfully!");
    }
    
    /**
     * Generate final comparison report
     */
    public FinalReport generateFinalReport(
            Map<Product, ProductPerformance> beforeAnalysis,
            ArrayList<PriceAdjustment> appliedAdjustments) {
        
        System.out.println("\nGenerating final report...");
        
        FinalReport report = new FinalReport();
        
        // Calculate totals BEFORE
        int totalRevenueBefore = 0;
        int totalMarginBefore = 0;
        for (ProductPerformance perf : beforeAnalysis.values()) {
            totalRevenueBefore += perf.getTotalRevenue();
            totalMarginBefore += perf.getPricePerformance();
        }
        
        report.setTotalRevenueBefore(totalRevenueBefore);
        report.setTotalMarginBefore(totalMarginBefore);
        
        // Re-analyze AFTER adjustments
        Map<Product, ProductPerformance> afterAnalysis = analyzer.analyzeAllProducts();
        
        // Calculate totals AFTER
        int totalRevenueAfter = 0;
        int totalMarginAfter = 0;
        for (ProductPerformance perf : afterAnalysis.values()) {
            totalRevenueAfter += perf.getTotalRevenue();
            totalMarginAfter += perf.getPricePerformance();
        }
        
        report.setTotalRevenueAfter(totalRevenueAfter);
        report.setTotalMarginAfter(totalMarginAfter);
        report.setProductsAdjusted(appliedAdjustments.size());
        
        // Create comparisons for adjusted products
        for (PriceAdjustment adj : appliedAdjustments) {
            Product product = adj.getProduct();
            
            ProductPerformance before = beforeAnalysis.get(product);
            ProductPerformance after = afterAnalysis.get(product);
            
            if (before != null && after != null) {
                ProductComparison comparison = new ProductComparison(product.toString());
                
                comparison.setOldTarget(adj.getCurrentTarget());
                comparison.setNewTarget(adj.getSuggestedTarget());
                
                comparison.setOldRevenue(before.getTotalRevenue());
                comparison.setNewRevenue(after.getTotalRevenue());
                
                comparison.setOldFreqBelow(before.getFrequencyBelowTarget());
                comparison.setNewFreqBelow(after.getFrequencyBelowTarget());
                
                comparison.setOldFreqAbove(before.getFrequencyAboveTarget());
                comparison.setNewFreqAbove(after.getFrequencyAboveTarget());
                
                comparison.setOldMargin(before.getPricePerformance());
                comparison.setNewMargin(after.getPricePerformance());
                
                report.addComparison(comparison);
            }
        }
        
        System.out.println("✓ Final report generated!");
        return report;
    }
    
    /**
     * Run complete optimization workflow
     */
    public FinalReport runCompleteOptimization(String strategy) {
        System.out.println("\n" + "=".repeat(80));
        System.out.println("STARTING COMPLETE PRICING OPTIMIZATION");
        System.out.println("Strategy: " + strategy);
        System.out.println("=".repeat(80));
        
        // Step 1: Analyze current state
        System.out.println("\n[1/5] Analyzing current pricing performance...");
        Map<Product, ProductPerformance> beforeAnalysis = analyzer.analyzeAllProducts();
        analyzer.printAnalysisReport();
        
        // Step 2: Generate recommendations
        System.out.println("\n[2/5] Generating recommendations...");
        ArrayList<PriceAdjustment> recommendations;
        
        if (strategy.equals("LOWER_ONLY")) {
            recommendations = recommendationEngine.recommendLowerTargets(beforeAnalysis);
        } else if (strategy.equals("HIGHER_ONLY")) {
            recommendations = recommendationEngine.recommendHigherTargets(beforeAnalysis);
        } else {
            recommendations = recommendationEngine.getAllRecommendations(beforeAnalysis);
        }
        
        recommendationEngine.printRecommendationsReport(beforeAnalysis);
        
        // Step 3: Run simulation
        System.out.println("\n[3/5] Running simulation...");
        SimulationResult simulation = simulationEngine.simulate(recommendations, beforeAnalysis);
        simulationEngine.printSimulationReport(simulation);
        
        // Step 4: Apply changes
        System.out.println("\n[4/5] Applying price adjustments...");
        applyAdjustments(recommendations);
        
        // Step 5: Generate final report
        System.out.println("\n[5/5] Generating final report...");
        FinalReport finalReport = generateFinalReport(beforeAnalysis, recommendations);
        
        return finalReport;
    }
}