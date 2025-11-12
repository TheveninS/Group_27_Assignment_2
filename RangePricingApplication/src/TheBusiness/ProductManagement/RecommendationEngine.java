package TheBusiness.ProductManagement;

import java.util.ArrayList;
import java.util.Map;

/**
 * Recommends price adjustments based on product performance
 * @author Your Name
 */
public class RecommendationEngine {
    
    /**
     * Recommend products that should have LOWER target prices
     */
    public ArrayList<PriceAdjustment> recommendLowerTargets(
            Map<Product, ProductPerformance> analysis) {
        
        ArrayList<PriceAdjustment> recommendations = new ArrayList<>();
        
        for (Map.Entry<Product, ProductPerformance> entry : analysis.entrySet()) {
            Product product = entry.getKey();
            ProductPerformance perf = entry.getValue();
            
            // Only recommend if consistently selling below target
            if (perf.isConsistentlyBelowTarget()) {
                int currentTarget = product.getTargetPrice();
                int avgActual = perf.getAvgActualPrice();
                
                // Strategy: Suggest new target = 95% of average actual price
                int suggestedTarget = (int)(avgActual * 0.95);
                
                // Ensure it stays above floor price
                if (suggestedTarget < product.getFloorPrice()) {
                    suggestedTarget = product.getFloorPrice();
                }
                
                // Only recommend if it's actually lower than current
                if (suggestedTarget < currentTarget) {
                    String reason = String.format(
                        "Selling below target %d%% of the time (avg: $%d)",
                        perf.getFrequencyBelowTarget() * 100 / 
                            (perf.getFrequencyBelowTarget() + perf.getFrequencyAboveTarget() + perf.getFrequencyAtTarget()),
                        avgActual
                    );
                    
                    PriceAdjustment adjustment = new PriceAdjustment(
                        product,
                        currentTarget,
                        suggestedTarget,
                        reason,
                        "LOWER"
                    );
                    
                    recommendations.add(adjustment);
                }
            }
        }
        
        return recommendations;
    }
    
    /**
     * Recommend products that should have HIGHER target prices
     */
    public ArrayList<PriceAdjustment> recommendHigherTargets(
            Map<Product, ProductPerformance> analysis) {
        
        ArrayList<PriceAdjustment> recommendations = new ArrayList<>();
        
        for (Map.Entry<Product, ProductPerformance> entry : analysis.entrySet()) {
            Product product = entry.getKey();
            ProductPerformance perf = entry.getValue();
            
            // Only recommend if consistently selling above target
            if (perf.isConsistentlyAboveTarget()) {
                int currentTarget = product.getTargetPrice();
                int avgActual = perf.getAvgActualPrice();
                
                // Strategy: Suggest new target = 105% of average actual price
                int suggestedTarget = (int)(avgActual * 1.05);
                
                // Ensure it stays below ceiling price
                if (suggestedTarget > product.getCeilingPrice()) {
                    suggestedTarget = product.getCeilingPrice();
                }
                
                // Only recommend if it's actually higher than current
                if (suggestedTarget > currentTarget) {
                    String reason = String.format(
                        "Selling above target %d%% of the time (avg: $%d)",
                        perf.getFrequencyAboveTarget() * 100 / 
                            (perf.getFrequencyBelowTarget() + perf.getFrequencyAboveTarget() + perf.getFrequencyAtTarget()),
                        avgActual
                    );
                    
                    PriceAdjustment adjustment = new PriceAdjustment(
                        product,
                        currentTarget,
                        suggestedTarget,
                        reason,
                        "HIGHER"
                    );
                    
                    recommendations.add(adjustment);
                }
            }
        }
        
        return recommendations;
    }
    
    /**
     * Get all recommendations (both lower and higher)
     */
    public ArrayList<PriceAdjustment> getAllRecommendations(
            Map<Product, ProductPerformance> analysis) {
        
        ArrayList<PriceAdjustment> all = new ArrayList<>();
        all.addAll(recommendLowerTargets(analysis));
        all.addAll(recommendHigherTargets(analysis));
        return all;
    }
    
    /**
     * Print recommendations report
     */
    public void printRecommendationsReport(Map<Product, ProductPerformance> analysis) {
        System.out.println("\n=== PRICE ADJUSTMENT RECOMMENDATIONS ===");
        
        ArrayList<PriceAdjustment> lowerRecs = recommendLowerTargets(analysis);
        ArrayList<PriceAdjustment> higherRecs = recommendHigherTargets(analysis);
        
        System.out.println("\nProducts Needing LOWER Target Prices: " + lowerRecs.size());
        int count = 0;
        for (PriceAdjustment adj : lowerRecs) {
            System.out.println("  " + adj.toString());
            count++;
            if (count >= 10) break;
        }
        
        System.out.println("\nProducts Needing HIGHER Target Prices: " + higherRecs.size());
        count = 0;
        for (PriceAdjustment adj : higherRecs) {
            System.out.println("  " + adj.toString());
            count++;
            if (count >= 10) break;
        }
        
        System.out.println("\nTotal Recommendations: " + (lowerRecs.size() + higherRecs.size()));
        System.out.println("=========================================\n");
    }
}