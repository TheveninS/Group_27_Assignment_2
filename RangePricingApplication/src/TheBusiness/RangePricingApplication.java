package TheBusiness;

import TheBusiness.Business.Business;
import TheBusiness.ProductManagement.PricingAnalyzer;

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
        analyzer.printAnalysisReport();
    }

}