package MarketAnalytics;

import TheBusiness.MarketModel.Channel;
import TheBusiness.SolutionOrders.MasterSolutionOrderList;

/**
 * Summary of revenue performance for a specific channel
 * @author kal bugrara
 */
public class ChannelSummary {
    Channel channel;
    int revenues;
    int numberOfOrders;
    
    public ChannelSummary(Channel c, MasterSolutionOrderList msol) {
        channel = c;
        revenues = msol.getRevenueByChannel(c);
        // Count orders for this channel
        numberOfOrders = msol.getOrderCountByChannel(c);
    }
    
    public Channel getChannel() {
        return channel;
    }
    
    public int getRevenues() {
        return revenues;
    }
    
    public int getNumberOfOrders() {
        return numberOfOrders;
    }
    
    public String getChannelType() {
        return channel.getChannelType();
    }
    
    @Override
    public String toString() {
        return String.format("Channel: %s | Revenue: $%,d | Orders: %d",
            channel.getChannelType()