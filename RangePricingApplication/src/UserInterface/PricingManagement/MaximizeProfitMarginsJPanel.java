package UserInterface.PricingManagement;

import TheBusiness.Business.Business;
import TheBusiness.ProductManagement.*;
import javax.swing.JPanel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 * Maximize Profit Margins Screen
 * @author anush
 */
public class MaximizeProfitMarginsJPanel extends javax.swing.JPanel {

    JPanel CardSequencePanel;
    Business business;
    PricingController controller;

    public MaximizeProfitMarginsJPanel(Business bz, JPanel jp) {
        CardSequencePanel = jp;
        this.business = bz;
        this.controller = new PricingController(business);
        initComponents();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        resultsTable = new javax.swing.JTable();
        btnRunOptimization = new javax.swing.JButton();
        btnBack = new javax.swing.JButton();
        btnApplyBest = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        lblBestStrategy = new javax.swing.JLabel();

        setBackground(new java.awt.Color(0, 153, 153));
        setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Arial", 0, 24));
        jLabel1.setText("Maximize Profit Margins");
        add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 20, 550, -1));

        jLabel2.setText("Compare all strategies and find the optimal pricing adjustments");
        add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 60, 600, -1));

        resultsTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {},
            new String [] {
                "Strategy", "Products Adjusted", "Current Revenue", "Projected Revenue", "Revenue Δ", "Current Margin", "Projected Margin", "Margin Δ"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(resultsTable);

        add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 140, 900, 280));

        btnRunOptimization.setText("Run Optimization");
        btnRunOptimization.setBackground(new java.awt.Color(0, 150, 200));
        btnRunOptimization.setForeground(new java.awt.Color(255, 255, 255));
        btnRunOptimization.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRunOptimizationActionPerformed(evt);
            }
        });
        add(btnRunOptimization, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 90, 180, 35));

        btnBack.setText("<< Back");
        btnBack.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBackActionPerformed(evt);
            }
        });
        add(btnBack, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 500, 100, 30));

        btnApplyBest.setText("Apply Best Strategy");
        btnApplyBest.setBackground(new java.awt.Color(0, 200, 0));
        btnApplyBest.setForeground(new java.awt.Color(255, 255, 255));
        btnApplyBest.setEnabled(false);
        btnApplyBest.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnApplyBestActionPerformed(evt);
            }
        });
        add(btnApplyBest, new org.netbeans.lib.awtextra.AbsoluteConstraints(770, 500, 150, 30));

        lblBestStrategy.setFont(new java.awt.Font("Dialog", 1, 14));
        lblBestStrategy.setText("");
        add(lblBestStrategy, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 440, 900, 30));
    }// </editor-fold>

    private String bestStrategy = "";
    
    private void btnBackActionPerformed(java.awt.event.ActionEvent evt) {
        CardSequencePanel.remove(this);
        ((java.awt.CardLayout) CardSequencePanel.getLayout()).next(CardSequencePanel);
    }

    private void btnRunOptimizationActionPerformed(java.awt.event.ActionEvent evt) {
        btnRunOptimization.setEnabled(false);
        btnRunOptimization.setText("Running...");
        
        try {
            PricingAnalyzer analyzer = new PricingAnalyzer(business);
            RecommendationEngine engine = new RecommendationEngine();
            SimulationEngine simEngine = new SimulationEngine();
            
            var analysis = analyzer.analyzeAllProducts();
            
            // Test all three strategies
            var lowerRecs = engine.recommendLowerTargets(analysis);
            var higherRecs = engine.recommendHigherTargets(analysis);
            var bothRecs = engine.getAllRecommendations(analysis);
            
            SimulationResult resultLower = simEngine.simulate(lowerRecs, analysis);
            SimulationResult resultHigher = simEngine.simulate(higherRecs, analysis);
            SimulationResult resultBoth = simEngine.simulate(bothRecs, analysis);
            
            // Display results
            DefaultTableModel model = (DefaultTableModel) resultsTable.getModel();
            model.setRowCount(0);
            
            Object[] rowLower = {
                "LOWER_ONLY",
                resultLower.getAdjustmentsCount(),
                String.format("$%,d", resultLower.getCurrentRevenue()),
                String.format("$%,d", resultLower.getProjectedRevenue()),
                String.format("$%+,d", resultLower.getRevenueDelta()),
                String.format("$%,d", resultLower.getCurrentProfitMargin()),
                String.format("$%,d", resultLower.getProjectedProfitMargin()),
                String.format("$%+,d", resultLower.getProfitMarginDelta())
            };
            model.addRow(rowLower);
            
            Object[] rowHigher = {
                "HIGHER_ONLY",
                resultHigher.getAdjustmentsCount(),
                String.format("$%,d", resultHigher.getCurrentRevenue()),
                String.format("$%,d", resultHigher.getProjectedRevenue()),
                String.format("$%+,d", resultHigher.getRevenueDelta()),
                String.format("$%,d", resultHigher.getCurrentProfitMargin()),
                String.format("$%,d", resultHigher.getProjectedProfitMargin()),
                String.format("$%+,d", resultHigher.getProfitMarginDelta())
            };
            model.addRow(rowHigher);
            
            Object[] rowBoth = {
                "BOTH",
                resultBoth.getAdjustmentsCount(),
                String.format("$%,d", resultBoth.getCurrentRevenue()),
                String.format("$%,d", resultBoth.getProjectedRevenue()),
                String.format("$%+,d", resultBoth.getRevenueDelta()),
                String.format("$%,d", resultBoth.getCurrentProfitMargin()),
                String.format("$%,d", resultBoth.getProjectedProfitMargin()),
                String.format("$%+,d", resultBoth.getProfitMarginDelta())
            };
            model.addRow(rowBoth);
            
            // Determine best strategy
            int bestMarginDelta = Math.max(
                resultLower.getProfitMarginDelta(),
                Math.max(resultHigher.getProfitMarginDelta(), resultBoth.getProfitMarginDelta())
            );
            
            if (bestMarginDelta == resultLower.getProfitMarginDelta()) {
                bestStrategy = "LOWER_ONLY";
            } else if (bestMarginDelta == resultHigher.getProfitMarginDelta()) {
                bestStrategy = "HIGHER_ONLY";
            } else {
                bestStrategy = "BOTH";
            }
            
            lblBestStrategy.setText("✓ BEST STRATEGY: " + bestStrategy + 
                " with margin improvement of $" + String.format("%,d", bestMarginDelta));
            
            btnApplyBest.setEnabled(true);
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                "Error during optimization: " + e.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        } finally {
            btnRunOptimization.setEnabled(true);
            btnRunOptimization.setText("Run Optimization");
        }
    }

    private void btnApplyBestActionPerformed(java.awt.event.ActionEvent evt) {
        int confirm = JOptionPane.showConfirmDialog(this,
            "Apply the best strategy (" + bestStrategy + ")?\nThis will update all target prices.",
            "Confirm Optimization",
            JOptionPane.YES_NO_OPTION);
        
        if (confirm == JOptionPane.YES_OPTION) {
            FinalReport report = controller.runCompleteOptimization(bestStrategy);
            
            JOptionPane.showMessageDialog(this,
                "Optimization complete!\n" +
                "Products Adjusted: " + report.getProductsAdjusted() + "\n" +
                "Products Improved: " + report.getProductsImproved() + "\n" +
                "Margin Change: $" + String.format("%,d", report.getTotalMarginDelta()),
                "Success",
                JOptionPane.INFORMATION_MESSAGE);
        }
    }

    // Variables declaration
    private javax.swing.JButton btnBack;
    private javax.swing.JButton btnRunOptimization;
    private javax.swing.JButton btnApplyBest;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblBestStrategy;
    private javax.swing.JTable resultsTable;
    // End of variables declaration
}