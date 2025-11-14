package UserInterface.PricingManagement;

import TheBusiness.Business.Business;
import TheBusiness.ProductManagement.*;
import java.util.ArrayList;
import java.util.Map;
import javax.swing.JPanel;
import javax.swing.JOptionPane;

/**
 * Run Simulation Screen
 * @author anush
 */
public class RunSimulationJPanel extends javax.swing.JPanel {

    JPanel CardSequencePanel;
    Business business;
    PricingAnalyzer analyzer;
    RecommendationEngine engine;
    SimulationEngine simEngine;

    public RunSimulationJPanel(Business bz, JPanel jp) {
        CardSequencePanel = jp;
        this.business = bz;
        this.analyzer = new PricingAnalyzer(business);
        this.engine = new RecommendationEngine();
        this.simEngine = new SimulationEngine();
        initComponents();
        loadCurrentState();
    }

    private void loadCurrentState() {
        lblCurrentRevenue.setText("Current Revenue: $" + String.format("%,d", analyzer.getTotalCompanyRevenue()));
        lblCurrentMargin.setText("Current Margin: $" + String.format("%,d", analyzer.getTotalProfitMargin()));
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        cmbStrategy = new javax.swing.JComboBox<>();
        btnRunSimulation = new javax.swing.JButton();
        btnBack = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtResults = new javax.swing.JTextArea();
        lblCurrentRevenue = new javax.swing.JLabel();
        lblCurrentMargin = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();

        setBackground(new java.awt.Color(0, 153, 153));
        setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Arial", 0, 24));
        jLabel1.setText("Run Simulation");
        add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 20, 550, -1));

        jLabel2.setText("Select Strategy:");
        add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 100, 120, -1));

        cmbStrategy.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { 
            "LOWER_ONLY - Reduce prices for underperforming products", 
            "HIGHER_ONLY - Increase prices for overperforming products", 
            "BOTH - Apply both lower and higher adjustments" 
        }));
        add(cmbStrategy, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 95, 450, 30));

        btnRunSimulation.setText("Run Simulation");
        btnRunSimulation.setBackground(new java.awt.Color(0, 150, 200));
        btnRunSimulation.setForeground(new java.awt.Color(255, 255, 255));
        btnRunSimulation.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRunSimulationActionPerformed(evt);
            }
        });
        add(btnRunSimulation, new org.netbeans.lib.awtextra.AbsoluteConstraints(620, 95, 150, 30));

        btnBack.setText("<< Back");
        btnBack.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBackActionPerformed(evt);
            }
        });
        add(btnBack, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 500, 100, 30));

        txtResults.setEditable(false);
        txtResults.setColumns(20);
        txtResults.setRows(5);
        txtResults.setFont(new java.awt.Font("Monospaced", 0, 12));
        jScrollPane1.setViewportView(txtResults);

        add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 180, 900, 300));

        lblCurrentRevenue.setFont(new java.awt.Font("Dialog", 1, 14));
        lblCurrentRevenue.setText("Current Revenue: $0");
        add(lblCurrentRevenue, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 60, 400, -1));

        lblCurrentMargin.setFont(new java.awt.Font("Dialog", 1, 14));
        lblCurrentMargin.setText("Current Margin: $0");
        add(lblCurrentMargin, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 60, 400, -1));

        jLabel3.setFont(new java.awt.Font("Dialog", 1, 14));
        jLabel3.setText("Simulation Results:");
        add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 150, 400, -1));
    }// </editor-fold>

    private void btnBackActionPerformed(java.awt.event.ActionEvent evt) {
        CardSequencePanel.remove(this);
        ((java.awt.CardLayout) CardSequencePanel.getLayout()).next(CardSequencePanel);
    }

    private void btnRunSimulationActionPerformed(java.awt.event.ActionEvent evt) {
        txtResults.setText("Running simulation...\n\n");
        
        String selectedStrategy = (String) cmbStrategy.getSelectedItem();
        String strategy = "BOTH";
        
        if (selectedStrategy.startsWith("LOWER_ONLY")) {
            strategy = "LOWER_ONLY";
        } else if (selectedStrategy.startsWith("HIGHER_ONLY")) {
            strategy = "HIGHER_ONLY";
        }
        
        try {
            Map<Product, ProductPerformance> analysis = analyzer.analyzeAllProducts();
            ArrayList<PriceAdjustment> recommendations;
            
            if (strategy.equals("LOWER_ONLY")) {
                recommendations = engine.recommendLowerTargets(analysis);
            } else if (strategy.equals("HIGHER_ONLY")) {
                recommendations = engine.recommendHigherTargets(analysis);
            } else {
                recommendations = engine.getAllRecommendations(analysis);
            }
            
            SimulationResult result = simEngine.simulate(recommendations, analysis);
            
            StringBuilder sb = new StringBuilder();
            sb.append("=== SIMULATION RESULTS ===\n\n");
            sb.append("Strategy: ").append(strategy).append("\n");
            sb.append("Products to Adjust: ").append(result.getAdjustmentsCount()).append("\n\n");
            
            sb.append("--- CURRENT STATE ---\n");
            sb.append(String.format("Revenue: $%,d\n", result.getCurrentRevenue()));
            sb.append(String.format("Profit Margin: $%,d\n\n", result.getCurrentProfitMargin()));
            
            sb.append("--- PROJECTED STATE ---\n");
            sb.append(String.format("Revenue: $%,d (%+.2f%%)\n", 
                result.getProjectedRevenue(), 
                result.getRevenueChangePercentage()));
            sb.append(String.format("Profit Margin: $%,d (%+d)\n\n", 
                result.getProjectedProfitMargin(), 
                result.getProfitMarginDelta()));
            
            sb.append("--- IMPACT ---\n");
            sb.append(String.format("Revenue Change: $%+,d\n", result.getRevenueDelta()));
            sb.append(String.format("Margin Change: $%+,d\n", result.getProfitMarginDelta()));
            
            if (result.getProfitMarginDelta() > 0) {
                sb.append("\n✓ This strategy IMPROVES profit margins!");
            } else {
                sb.append("\n✗ This strategy DECREASES profit margins.");
            }
            
            txtResults.setText(sb.toString());
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                "Error running simulation: " + e.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    // Variables declaration
    private javax.swing.JButton btnBack;
    private javax.swing.JButton btnRunSimulation;
    private javax.swing.JComboBox<String> cmbStrategy;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblCurrentRevenue;
    private javax.swing.JLabel lblCurrentMargin;
    private javax.swing.JTextArea txtResults;
    // End of variables declaration
}