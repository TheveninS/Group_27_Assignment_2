package TheBusiness;

import TheBusiness.Business.Business;
import TheBusiness.ProductManagement.*;

/**
 *
 * @author kal bugrara
 */
public class RangePricingApplication {

    public static void main(String[] args) {
        // Initialize business with generated data
        Business business = ConfigureABusiness.initializeWithGeneratedData();
        
        // Create pricing controller
        PricingController controller = new PricingController(business);
        
        // Run complete optimization with LOWER_ONLY strategy (best from simulation)
        FinalReport report = controller.runCompleteOptimization("LOWER_ONLY");
        
        // Print final report
        report.printReport();
        
        // Export to CSV
        report.exportToCSV("pricing_optimization_report.csv");
        
        System.out.println("\n✓ Pricing optimization complete!");
        System.out.println("✓ CSV report saved to: pricing_optimization_report.csv");
    }
}