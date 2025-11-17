package UserInterface.PriceSimulation;

import TheBusiness.Business.Business;
import TheBusiness.ProductManagement.*;
import javax.swing.*;
import java.awt.*;
import java.util.*;

public class RunSimulationJPanel extends JPanel {
    
    private Business business;
    private JPanel cardPanel;
    private JTextArea resultArea;
    private JTextField lowerPercentField, higherPercentField;
    
    public RunSimulationJPanel(Business b, JPanel cards) {
        this.business = b;
        this.cardPanel = cards;
        
        setBackground(new Color(0, 153, 153));
        setLayout(null);
        
        JLabel title = new JLabel("Run Pricing Simulation");
        title.setFont(new Font("Arial", Font.PLAIN, 24));
        title.setBounds(20, 20, 400, 30);
        add(title);
        
        // Controls
        JLabel lowerLbl = new JLabel("Lower targets by %:");
        lowerLbl.setBounds(20, 70, 130, 25);
        add(lowerLbl);
        
        lowerPercentField = new JTextField("10");
        lowerPercentField.setBounds(150, 70, 60, 25);
        add(lowerPercentField);
        
        JLabel higherLbl = new JLabel("Raise targets by %:");
        higherLbl.setBounds(230, 70, 130, 25);
        add(higherLbl);
        
        higherPercentField = new JTextField("10");
        higherPercentField.setBounds(360, 70, 60, 25);
        add(higherPercentField);
        
        JButton runBtn = new JButton("Run Simulation");
        runBtn.setBounds(440, 70, 150, 25);
        runBtn.setBackground(new Color(102, 153, 255));
        runBtn.setForeground(Color.WHITE);
        runBtn.addActionListener(e -> runSimulation());
        add(runBtn);
        
        // Results area
        resultArea = new JTextArea();
        resultArea.setEditable(false);
        resultArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        JScrollPane scroll = new JScrollPane(resultArea);
        scroll.setBounds(20, 110, 860, 350);
        add(scroll);
        
        JButton back = new JButton("<< Back");
        back.setBounds(20, 470, 90, 30);
        back.addActionListener(e -> {
            cardPanel.remove(this);
            ((CardLayout) cardPanel.getLayout()).next(cardPanel);
        });
        add(back);
    }
    
    private void runSimulation() {
        try {
            int lowerPercent = Integer.parseInt(lowerPercentField.getText());
            int higherPercent = Integer.parseInt(higherPercentField.getText());
            
            if (lowerPercent < 1 || lowerPercent > 50 || higherPercent < 1 || higherPercent > 50) {
                JOptionPane.showMessageDialog(this, "Enter percentages between 1-50");
                return;
            }
            
            PricingAnalyzer analyzer = new PricingAnalyzer(business);
            RecommendationEngine recEngine = new RecommendationEngine();
            SimulationEngine simEngine = new SimulationEngine();
            
            // Get current state
            Map<Product, ProductPerformance> currentAnalysis = analyzer.analyzeAllProducts();
            int currentRevenue = analyzer.getTotalCompanyRevenue();
            int currentMargin = analyzer.getTotalProfitMargin();
            
            // Get recommendations
            ArrayList<PriceAdjustment> lowerRecs = recEngine.recommendLowerTargets(currentAnalysis);
            ArrayList<PriceAdjustment> higherRecs = recEngine.recommendHigherTargets(currentAnalysis);
            
            // Simulate
            SimulationResult lowerSim = simEngine.simulate(lowerRecs, currentAnalysis);
            SimulationResult higherSim = simEngine.simulate(higherRecs, currentAnalysis);
            
            ArrayList<PriceAdjustment> allRecs = new ArrayList<>();
            allRecs.addAll(lowerRecs);
            allRecs.addAll(higherRecs);
            SimulationResult combinedSim = simEngine.simulate(allRecs, currentAnalysis);
            
            // Display results
            StringBuilder sb = new StringBuilder();
            sb.append("═══════════════════════════════════════════════════════════════════════\n");
            sb.append("                       PRICING SIMULATION RESULTS\n");
            sb.append("═══════════════════════════════════════════════════════════════════════\n\n");
            
            sb.append("CURRENT STATE:\n");
            sb.append(String.format("  Revenue:        $%,d\n", currentRevenue));
            sb.append(String.format("  Profit Margin:  $%,d\n\n", currentMargin));
            
            sb.append("SCENARIO A: Lower Targets Only (-" + lowerPercent + "%)\n");
            sb.append(String.format("  Products:       %d\n", lowerRecs.size()));
            sb.append(String.format("  Proj Revenue:   $%,d (%+.2f%%)\n", 
                lowerSim.getProjectedRevenue(), lowerSim.getRevenueChangePercentage()));
            sb.append(String.format("  Proj Margin:    $%,d (%+d)\n\n", 
                lowerSim.getProjectedProfitMargin(), lowerSim.getProfitMarginDelta()));
            
            sb.append("SCENARIO B: Raise Targets Only (+" + higherPercent + "%)\n");
            sb.append(String.format("  Products:       %d\n", higherRecs.size()));
            sb.append(String.format("  Proj Revenue:   $%,d (%+.2f%%)\n", 
                higherSim.getProjectedRevenue(), higherSim.getRevenueChangePercentage()));
            sb.append(String.format("  Proj Margin:    $%,d (%+d)\n\n", 
                higherSim.getProjectedProfitMargin(), higherSim.getProfitMarginDelta()));
            
            sb.append("SCENARIO C: Both Adjustments\n");
            sb.append(String.format("  Products:       %d\n", allRecs.size()));
            sb.append(String.format("  Proj Revenue:   $%,d (%+.2f%%)\n", 
                combinedSim.getProjectedRevenue(), combinedSim.getRevenueChangePercentage()));
            sb.append(String.format("  Proj Margin:    $%,d (%+d)\n\n", 
                combinedSim.getProjectedProfitMargin(), combinedSim.getProfitMarginDelta()));
            
            // Recommend best
            SimulationResult best = combinedSim;
            String bestName = "Scenario C (Both)";
            
            if (lowerSim.getProfitMarginDelta() > best.getProfitMarginDelta()) {
                best = lowerSim;
                bestName = "Scenario A (Lower Only)";
            }
            if (higherSim.getProfitMarginDelta() > best.getProfitMarginDelta()) {
                best = higherSim;
                bestName = "Scenario B (Higher Only)";
            }
            
            sb.append("═══════════════════════════════════════════════════════════════════════\n");
            sb.append("RECOMMENDATION: " + bestName + "\n");
            sb.append(String.format("Expected margin improvement: $%,d\n", best.getProfitMarginDelta()));
            sb.append("═══════════════════════════════════════════════════════════════════════\n");
            
            resultArea.setText(sb.toString());
            
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Invalid percentage values");
        }
    }
}