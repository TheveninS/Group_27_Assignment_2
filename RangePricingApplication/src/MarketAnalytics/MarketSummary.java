package MarketAnalytics;

import TheBusiness.MarketModel.Market;
import TheBusiness.SolutionOrders.MasterSolutionOrderList;

/**
 * Summary of revenue performance for a specific market
 * @author kal bugrara
 */
public class MarketSummary {
    Market market;
    int revenue;
    int numberOfOrders;
    
    public MarketSummary(Market m, MasterSolutionOrderList msol) {
        market = m;
        revenue = msol.getRevenueByMarket(m);
        // Count orders for this market
        numberOfOrders = msol.getOrderCountByMarket(m);
    }
    
    public Market getMarket() {
        return market;
    }
    
    public int getRevenue() {
        return revenue;
    }
    
    public int getNumberOfOrders() {
        return numberOfOrders;
    }
    
    public String getMarketName() {
        return market.name; // You may need a getter method in Market class
    }
    
    @Override
    public String toString() {
        return String.format("Market: %s | Revenue: $%,d | Orders: %d",
            getMarketName(), revenue, numberOfOrders);
    }
}