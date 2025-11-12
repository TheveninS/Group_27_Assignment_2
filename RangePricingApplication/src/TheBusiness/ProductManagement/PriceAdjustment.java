package TheBusiness.ProductManagement;

/**
 * Represents a recommended price adjustment for a product
 * @author Syrill
 */
public class PriceAdjustment {
    
    private Product product;
    private int currentTarget;
    private int suggestedTarget;
    private String reason;
    private String direction; // "LOWER" or "HIGHER"
    
    public PriceAdjustment(Product product, int currentTarget, int suggestedTarget, 
                          String reason, String direction) {
        this.product = product;
        this.currentTarget = currentTarget;
        this.suggestedTarget = suggestedTarget;
        this.reason = reason;
        this.direction = direction;
    }
    
    public int getAdjustmentAmount() {
        return suggestedTarget - currentTarget;
    }
    
    public double getAdjustmentPercentage() {
        return ((suggestedTarget - currentTarget) * 100.0) / currentTarget;
    }
    
    // Getters
    public Product getProduct() {
        return product;
    }
    
    public int getCurrentTarget() {
        return currentTarget;
    }
    
    public int getSuggestedTarget() {
        return suggestedTarget;
    }
    
    public String getReason() {
        return reason;
    }
    
    public String getDirection() {
        return direction;
    }
    
    @Override
    public String toString() {
        return String.format("%s: %s | Current: $%d → Suggested: $%d (%+.1f%%) | %s",
                direction,
                product.toString(),
                currentTarget,
                suggestedTarget,
                getAdjustmentPercentage(),
                reason);
    }
}