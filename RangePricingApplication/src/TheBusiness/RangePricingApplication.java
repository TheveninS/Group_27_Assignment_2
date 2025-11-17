package TheBusiness;

import TheBusiness.Business.Business;
import TheBusiness.ProductManagement.*;

/**
 * Main application entry point
 * @author kal bugrara
 */
public class RangePricingApplication {
    

    public static void main(String[] args) {
    // Launch the GUI instead of console output
    java.awt.EventQueue.invokeLater(new Runnable() {
        public void run() {
            new UserInterface.Main.PricingMainFrame().setVisible(true);
        }
    });

        // Step 1: Initialize business with generated data
        Business business = ConfigureABusiness.initializeWithGeneratedData();
        
        // Step 2: Generate Top Performers Report
        System.out.println("\n" + "=".repeat(80));
        System.out.println("GENERATING INITIAL PERFORMANCE REPORTS");
        System.out.println("=".repeat(80));
        
        TopPerformersReport topReport = new TopPerformersReport(business);
        topReport.printTopPerformersReport();
        
        // Step 3: Run Pricing Analysis
        PricingAnalyzer analyzer = new PricingAnalyzer(business);
        analyzer.printAnalysisReport();
        
        // Step 4: Run Complete Pricing Optimization
        PricingController controller = new PricingController(business);
        
        System.out.println("\n" + "=".repeat(80));
        System.out.println("RUNNING PRICING OPTIMIZATION");
        System.out.println("=".repeat(80));
        
        // Try all three strategies and compare
        System.out.println("\n[Strategy 1] LOWER TARGETS ONLY");
        FinalReport reportLower = controller.runCompleteOptimization("LOWER_ONLY");
        
        System.out.println("\n[Strategy 2] HIGHER TARGETS ONLY");
        FinalReport reportHigher = controller.runCompleteOptimization("HIGHER_ONLY");
        
        System.out.println("\n[Strategy 3] BOTH ADJUSTMENTS");
        FinalReport reportBoth = controller.runCompleteOptimization("BOTH");
        
        
        // Step 5: Print Final Report (Best Strategy)
        System.out.println("\n" + "=".repeat(80));
        System.out.println("FINAL REPORT - BEST STRATEGY");
        System.out.println("=".repeat(80));
        
        // Choose best strategy based on margin improvement
        FinalReport bestReport = reportLower;
        String bestStrategy = "LOWER_ONLY";
        
        if (reportHigher.getTotalMarginDelta() > bestReport.getTotalMarginDelta()) {
            bestReport = reportHigher;
            bestStrategy = "HIGHER_ONLY";
        }
        if (reportBoth.getTotalMarginDelta() > bestReport.getTotalMarginDelta()) {
            bestReport = reportBoth;
            bestStrategy = "BOTH";
        }
        
        System.out.println("Best Strategy: " + bestStrategy);
        bestReport.printReport();
        
        // Step 6: Export to CSV
        String filename = "pricing_optimization_report_" + bestStrategy + ".csv";
        bestReport.exportToCSV(filename);
        
        System.out.println("\n✓ Application complete!");
        System.out.println("✓ CSV report saved to: " + filename);
        
        // Step 7: Print Updated Top Performers
        System.out.println("\n" + "=".repeat(80));
        System.out.println("TOP PERFORMERS AFTER OPTIMIZATION");
        System.out.println("=".repeat(80));
        topReport.printTopPerformersReport();
    }
    
    
    
}

