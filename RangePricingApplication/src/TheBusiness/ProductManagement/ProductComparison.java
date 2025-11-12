package TheBusiness.ProductManagement;

/**
 * Compares before/after state for a single product
 * @author Syrill
 */
public class ProductComparison {
    
    private String productName;
    private int oldTarget;
    private int newTarget;
    private int oldRevenue;
    private int newRevenue;
    private int oldFreqBelow;
    private int newFreqBelow;
    private int oldFreqAbove;
    private int newFreqAbove;
    private int oldMargin;
    private int newMargin;
    
    public ProductComparison(String productName) {
        this.productName = productName;
    }
    
    // Getters and Setters
    public String getProductName() {
        return productName;
    }
    
    public int getOldTarget() {
        return oldTarget;
    }
    
    public void setOldTarget(int oldTarget) {
        this.oldTarget = oldTarget;
    }
    
    public int getNewTarget() {
        return newTarget;
    }
    
    public void setNewTarget(int newTarget) {
        this.newTarget = newTarget;
    }
    
    public int getOldRevenue() {
        return oldRevenue;
    }
    
    public void setOldRevenue(int oldRevenue) {
        this.oldRevenue = oldRevenue;
    }
    
    public int getNewRevenue() {
        return newRevenue;
    }
    
    public void setNewRevenue(int newRevenue) {
        this.newRevenue = newRevenue;
    }
    
    public int getOldFreqBelow() {
        return oldFreqBelow;
    }
    
    public void setOldFreqBelow(int oldFreqBelow) {
        this.oldFreqBelow = oldFreqBelow;
    }
    
    public int getNewFreqBelow() {
        return newFreqBelow;
    }
    
    public void setNewFreqBelow(int newFreqBelow) {
        this.newFreqBelow = newFreqBelow;
    }
    
    public int getOldFreqAbove() {
        return oldFreqAbove;
    }
    
    public void setOldFreqAbove(int oldFreqAbove) {
        this.oldFreqAbove = oldFreqAbove;
    }
    
    public int getNewFreqAbove() {
        return newFreqAbove;
    }
    
    public void setNewFreqAbove(int newFreqAbove) {
        this.newFreqAbove = newFreqAbove;
    }
    
    public int getOldMargin() {
        return oldMargin;
    }
    
    public void setOldMargin(int oldMargin) {
        this.oldMargin = oldMargin;
    }
    
    public int getNewMargin() {
        return newMargin;
    }
    
    public void setNewMargin(int newMargin) {
        this.newMargin = newMargin;
    }
    
    public int getTargetChange() {
        return newTarget - oldTarget;
    }
    
    public int getRevenueChange() {
        return newRevenue - oldRevenue;
    }
    
    public int getMarginChange() {
        return newMargin - oldMargin;
    }
    
    public boolean isImproved() {
        return newMargin > oldMargin;
    }
    
    @Override
    public String toString() {
        return String.format(
            "%s | Target: $%d → $%d (%+d) | Revenue: $%d → $%d | Margin: $%d → $%d (%+d)",
            productName,
            oldTarget, newTarget, getTargetChange(),
            oldRevenue, newRevenue,
            oldMargin, newMargin, getMarginChange()
        );
    }
}