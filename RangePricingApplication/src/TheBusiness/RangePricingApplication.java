package TheBusiness;

import TheBusiness.Business.Business;
import TheBusiness.ProductManagement.*;
import java.util.Map;
import java.util.ArrayList;

/**
 *
 * @author kal bugrara
 */
public class RangePricingApplication {

    public static void main(String[] args) {
        // Step 1: Generate all data
        Business business = ConfigureABusiness.initializeWithGeneratedData();
        System.out.println("\nBusiness initialized successfully!");
        
        // Step 2: Analyze pricing performance
        System.out.println("\nAnalyzing product performance...");
        PricingAnalyzer analyzer = new PricingAnalyzer(business);
        Map<Product, ProductPerformance> analysis = analyzer.analyzeAllProducts();
        analyzer.printAnalysisReport();
        
        // Step 3: Get recommendations
        System.out.println("\nGenerating price adjustment recommendations...");
        RecommendationEngine recommendationEngine = new RecommendationEngine();
        ArrayList<PriceAdjustment> allRecommendations = recommendationEngine.getAllRecommendations(analysis);
        recommendationEngine.printRecommendationsReport(analysis);
        
        // Step 4: Run simulations
        System.out.println("\nRunning simulations...");
        SimulationEngine simulationEngine = new SimulationEngine();
        
        // Baseline (no changes)
        SimulationResult baseline = new SimulationResult();
        baseline.setCurrentRevenue(analyzer.getTotalCompanyRevenue());
        baseline.setCurrentProfitMargin(analyzer.getTotalProfitMargin());
        
        // Simulate different scenarios
        SimulationResult lowerOnly = simulationEngine.simulateLowerAdjustments(allRecommendations, analysis);
        SimulationResult higherOnly = simulationEngine.simulateHigherAdjustments(allRecommendations, analysis);
        SimulationResult allAdjustments = simulationEngine.simulate(allRecommendations, analysis);
        
        // Compare scenarios
        simulationEngine.compareScenarios(baseline, lowerOnly, higherOnly, allAdjustments);
        
        // Show best scenario
        System.out.println("\n=== RECOMMENDATION ===");
        if (allAdjustments.getProfitMarginDelta() > lowerOnly.getProfitMarginDelta() && 
            allAdjustments.getProfitMarginDelta() > higherOnly.getProfitMarginDelta()) {
            System.out.println("Best Strategy: Apply ALL adjustments");
            System.out.println("Expected profit improvement: $" + allAdjustments.getProfitMarginDelta());
        } else if (higherOnly.getProfitMarginDelta() > lowerOnly.getProfitMarginDelta()) {
            System.out.println("Best Strategy: Raise targets for high-performers only");
            System.out.println("Expected profit improvement: $" + higherOnly.getProfitMarginDelta());
        } else {
            System.out.println("Best Strategy: Lower targets for underperformers only");
            System.out.println("Expected profit improvement: $" + lowerOnly.getProfitMarginDelta());
        }
        System.out.println("======================\n");
    }
}