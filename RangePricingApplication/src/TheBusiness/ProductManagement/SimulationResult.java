package TheBusiness.ProductManagement;

import java.util.ArrayList;

/**
 * Holds the results of a pricing simulation
 * @author Syrill
 */
public class SimulationResult {
    
    private int currentRevenue;
    private int projectedRevenue;
    private int revenueDelta;
    
    private int currentProfitMargin;
    private int projectedProfitMargin;
    private int profitMarginDelta;
    
    private int adjustmentsCount;
    private ArrayList<PriceAdjustment> appliedAdjustments;
    
    public SimulationResult() {
        appliedAdjustments = new ArrayList<>();
    }
    
    // Getters and Setters
    public int getCurrentRevenue() {
        return currentRevenue;
    }
    
    public void setCurrentRevenue(int currentRevenue) {
        this.currentRevenue = currentRevenue;
    }
    
    public int getProjectedRevenue() {
        return projectedRevenue;
    }
    
    public void setProjectedRevenue(int projectedRevenue) {
        this.projectedRevenue = projectedRevenue;
    }
    
    public int getRevenueDelta() {
        return revenueDelta;
    }
    
    public void setRevenueDelta(int revenueDelta) {
        this.revenueDelta = revenueDelta;
    }
    
    public int getCurrentProfitMargin() {
        return currentProfitMargin;
    }
    
    public void setCurrentProfitMargin(int currentProfitMargin) {
        this.currentProfitMargin = currentProfitMargin;
    }
    
    public int getProjectedProfitMargin() {
        return projectedProfitMargin;
    }
    
    public void setProjectedProfitMargin(int projectedProfitMargin) {
        this.projectedProfitMargin = projectedProfitMargin;
    }
    
    public int getProfitMarginDelta() {
        return profitMarginDelta;
    }
    
    public void setProfitMarginDelta(int profitMarginDelta) {
        this.profitMarginDelta = profitMarginDelta;
    }
    
    public int getAdjustmentsCount() {
        return adjustmentsCount;
    }
    
    public void setAdjustmentsCount(int adjustmentsCount) {
        this.adjustmentsCount = adjustmentsCount;
    }
    
    public ArrayList<PriceAdjustment> getAppliedAdjustments() {
        return appliedAdjustments;
    }
    
    public void addAppliedAdjustment(PriceAdjustment adjustment) {
        appliedAdjustments.add(adjustment);
    }
    
    public double getRevenueChangePercentage() {
        if (currentRevenue == 0) return 0;
        return (revenueDelta * 100.0) / currentRevenue;
    }
    
    public double getProfitMarginChangePercentage() {
        if (currentProfitMargin == 0) return 0;
        return (profitMarginDelta * 100.0) / Math.abs(currentProfitMargin);
    }
    
    @Override
    public String toString() {
        return String.format(
            "Simulation Result:\n" +
            "  Adjustments Applied: %d\n" +
            "  Current Revenue: $%d\n" +
            "  Projected Revenue: $%d (%+.2f%%)\n" +
            "  Revenue Delta: $%+d\n" +
            "  Current Profit Margin: $%d\n" +
            "  Projected Profit Margin: $%d\n" +
            "  Margin Delta: $%+d",
            adjustmentsCount,
            currentRevenue,
            projectedRevenue,
            getRevenueChangePercentage(),
            revenueDelta,
            currentProfitMargin,
            projectedProfitMargin,
            profitMarginDelta
        );
    }
}