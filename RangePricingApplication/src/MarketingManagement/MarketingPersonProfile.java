package MarketingManagement;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import TheBusiness.OrderManagement.Order;
import TheBusiness.Personnel.Person;
import TheBusiness.Personnel.Profile;
import TheBusiness.ProductManagement.Product;
import TheBusiness.MarketModel.Market;
import TheBusiness.MarketModel.Channel;
import TheBusiness.CustomerManagement.CustomerProfile;

public class MarketingPersonProfile extends Profile {
    ArrayList<Order> salesorders;
    private Map<Market, Integer> marketRevenue;
    private Map<Channel, Integer> channelRevenue;
    private Map<String, ProductMarketingMetrics> productMetrics;
    
    public MarketingPersonProfile(Person p) {
        super(p); 
        salesorders = new ArrayList();
        marketRevenue = new HashMap<>();
        channelRevenue = new HashMap<>();
        productMetrics = new HashMap<>();
    }
    
    public void addSalesOrder(Order o){
        salesorders.add(o);
        updateMetrics(o);
    }
    
    private void updateMetrics(Order order) {
        int orderTotal = order.getOrderTotal();
        
        // Track revenue by market if available
        if (order.getSolutionOrder() != null && 
            order.getSolutionOrder().getMarketChannelComb() != null) {
            Market market = order.getSolutionOrder().getMarketChannelComb().getMarket();
            Channel channel = order.getSolutionOrder().getMarketChannelComb().getChannel();
            
            marketRevenue.put(market, 
                marketRevenue.getOrDefault(market, 0) + orderTotal);
            channelRevenue.put(channel, 
                channelRevenue.getOrDefault(channel, 0) + orderTotal);
        }
        
        // Track product-level metrics
        order.getOrderitems().forEach(item -> {
            Product product = item.getSelectedProduct();
            String productKey = product.toString();
            
            ProductMarketingMetrics metrics = productMetrics.getOrDefault(
                productKey, new ProductMarketingMetrics(product));
            
            metrics.addSale(item.getQuantity(), item.getActualPrice(), 
                           item.getOrderItemTotal());
            productMetrics.put(productKey, metrics);
        });
    }
    
    // Analytics Methods
    public double getTotalRevenue() {
        return salesorders.stream()
            .mapToDouble(Order::getOrderTotal)
            .sum();
    }
    
    public int getTotalOrdersCount() {
        return salesorders.size();
    }
    
    public double getAverageOrderValue() {
        if (salesorders.isEmpty()) return 0;
        return getTotalRevenue() / salesorders.size();
    }
    
    public Map<Market, Integer> getMarketPerformance() {
        return new HashMap<>(marketRevenue);
    }
    
    public Map<Channel, Integer> getChannelPerformance() {
        return new HashMap<>(channelRevenue);
    }
    
    public ProductMarketingMetrics getProductMetrics(Product product) {
        return productMetrics.get(product.toString());
    }
    
    public Map<String, ProductMarketingMetrics> getAllProductMetrics() {
        return new HashMap<>(productMetrics);
    }
    
    public Market getTopPerformingMarket() {
        return marketRevenue.entrySet().stream()
            .max(Map.Entry.comparingByValue())
            .map(Map.Entry::getKey)
            .orElse(null);
    }
    
    public Channel getTopPerformingChannel() {
        return channelRevenue.entrySet().stream()
            .max(Map.Entry.comparingByValue())
            .map(Map.Entry::getKey)
            .orElse(null);
    }
    
    public Product getTopSellingProduct() {
        return productMetrics.values().stream()
            .max((a, b) -> Integer.compare(a.getTotalUnitsSold(), b.getTotalUnitsSold()))
            .map(ProductMarketingMetrics::getProduct)
            .orElse(null);
    }
    
    public Product getMostProfitableProduct() {
        return productMetrics.values().stream()
            .max((a, b) -> Integer.compare(a.getTotalRevenue(), b.getTotalRevenue()))
            .map(ProductMarketingMetrics::getProduct)
            .orElse(null);
    }
    
    // Customer Analytics
    public int getUniqueCustomerCount() {
        return (int) salesorders.stream()
            .map(Order::getCustomer)
            .distinct()
            .count();
    }
    
    public double getCustomerRetentionRate() {
        if (salesorders.size() < 2) return 0;
        
        long repeatCustomers = salesorders.stream()
            .map(Order::getCustomer)
            .collect(java.util.stream.Collectors.groupingBy(c -> c, 
                     java.util.stream.Collectors.counting()))
            .values().stream()
            .filter(count -> count > 1)
            .count();
            
        return (repeatCustomers * 100.0) / getUniqueCustomerCount();
    }
    
    @Override
    public String getRole(){
        return "Marketing";
    }
    
    // Inner class for product-level marketing metrics
    public static class ProductMarketingMetrics {
        private Product product;
        private int totalUnitsSold;
        private int totalRevenue;
        private int numberOfSales;
        private int totalActualPrice;
        
        public ProductMarketingMetrics(Product p) {
            this.product = p;
            this.totalUnitsSold = 0;
            this.totalRevenue = 0;
            this.numberOfSales = 0;
            this.totalActualPrice = 0;
        }
        
        public void addSale(int quantity, int actualPrice, int revenue) {
            this.totalUnitsSold += quantity;
            this.totalRevenue += revenue;
            this.numberOfSales++;
            this.totalActualPrice += actualPrice;
        }
        
        public Product getProduct() { return product; }
        public int getTotalUnitsSold() { return totalUnitsSold; }
        public int getTotalRevenue() { return totalRevenue; }
        public int getNumberOfSales() { return numberOfSales; }
        
        public double getAveragePrice() {
            return numberOfSales > 0 ? (double) totalActualPrice / numberOfSales : 0;
        }
        
        public double getAverageUnitsPerSale() {
            return numberOfSales > 0 ? (double) totalUnitsSold / numberOfSales : 0;
        }
    }
}