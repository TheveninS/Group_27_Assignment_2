package MarketingManagement;

import TheBusiness.Business.Business;
import java.util.ArrayList;
import java.util.Comparator;
import TheBusiness.Personnel.Person;
import TheBusiness.ProductManagement.Product;

public class MarketingPersonDirectory {
    Business business;
    ArrayList<MarketingPersonProfile> marketingpersonlist;

    public MarketingPersonDirectory(Business d) {
        business = d;
        marketingpersonlist = new ArrayList();
    }

    public MarketingPersonProfile newMarketingPersonProfile(Person p) {
        MarketingPersonProfile sp = new MarketingPersonProfile(p);
        marketingpersonlist.add(sp);
        return sp;
    }

    public MarketingPersonProfile findMarketingPerson(String id) {
        for (MarketingPersonProfile sp : marketingpersonlist) {
            if (sp.isMatch(id)) {
                return sp;
            }
        }
        return null;
    }
    
    // Aggregate Analytics
    public double getTotalMarketingRevenue() {
        return marketingpersonlist.stream()
            .mapToDouble(MarketingPersonProfile::getTotalRevenue)
            .sum();
    }
    
    public int getTotalMarketingOrders() {
        return marketingpersonlist.stream()
            .mapToInt(MarketingPersonProfile::getTotalOrdersCount)
            .sum();
    }
    
    public MarketingPersonProfile getTopPerformer() {
        return marketingpersonlist.stream()
            .max(Comparator.comparingDouble(MarketingPersonProfile::getTotalRevenue))
            .orElse(null);
    }
    
    public ArrayList<MarketingPersonProfile> getMarketingPersonList() {
        return marketingpersonlist;
    }
    
    public Product getMostPromotedProduct() {
        // Aggregate across all marketing personnel
        java.util.Map<String, Integer> productCounts = new java.util.HashMap<>();
        
        for (MarketingPersonProfile mpp : marketingpersonlist) {
            mpp.getAllProductMetrics().forEach((productName, metrics) -> {
                productCounts.put(productName, 
                    productCounts.getOrDefault(productName, 0) + metrics.getTotalUnitsSold());
            });
        }
        
        return productCounts.entrySet().stream()
            .max(java.util.Map.Entry.comparingByValue())
            .map(java.util.Map.Entry::getKey)
            .flatMap(name -> marketingpersonlist.stream()
                .map(mpp -> mpp.getAllProductMetrics().get(name))
                .filter(m -> m != null)
                .findFirst()
                .map(MarketingPersonProfile.ProductMarketingMetrics::getProduct))
            .orElse(null);
    }
}