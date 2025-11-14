package UserInterface.PricingManagement;

import TheBusiness.Business.Business;
import TheBusiness.ProductManagement.*;
import java.util.ArrayList;
import java.util.Map;
import javax.swing.JPanel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 * Adjust Target Prices Lower Screen
 * @author anush
 */
public class AdjustPricesLowerJPanel extends javax.swing.JPanel {

    JPanel CardSequencePanel;
    Business business;
    PricingAnalyzer analyzer;
    RecommendationEngine engine;
    ArrayList<PriceAdjustment> recommendations;

    public AdjustPricesLowerJPanel(Business bz, JPanel jp) {
        CardSequencePanel = jp;
        this.business = bz;
        this.analyzer = new PricingAnalyzer(business);
        this.engine = new RecommendationEngine();
        initComponents();
        loadRecommendations();
    }

    private void loadRecommendations() {
        Map<Product, ProductPerformance> analysis = analyzer.analyzeAllProducts();
        recommendations = engine.recommendLowerTargets(analysis);
        
        DefaultTableModel model = (DefaultTableModel) recommendationsTable.getModel();
        model.setRowCount(0);
        
        for (PriceAdjustment adj : recommendations) {
            Object[] row = {
                adj.getProduct().toString(),
                adj.getCurrentTarget(),
                adj.getSuggestedTarget(),
                adj.getAdjustmentAmount(),
                String.format("%.1f%%", adj.getAdjustmentPercentage()),
                adj.getReason()
            };
            model.addRow(row);
        }
        
        lblTotalRecommendations.setText("Recommendations: " + recommendations.size() + " products need lower prices");
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        recommendationsTable = new javax.swing.JTable();
        btnBack = new javax.swing.JButton();
        btnApply = new javax.swing.JButton();
        lblTotalRecommendations = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();

        setBackground(new java.awt.Color(0, 153, 153));
        setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Arial", 0, 24));
        jLabel1.setText("Adjust Target Prices Lower");
        add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 20, 550, -1));

        jLabel2.setText("Products selling below target (>70% of sales) - recommend price reduction");
        add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 60, 600, -1));

        recommendationsTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {},
            new String [] {
                "Product", "Current Target", "Suggested Target", "Change ($)", "Change (%)", "Reason"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(recommendationsTable);

        add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 120, 900, 350));

        btnBack.setText("<< Back");
        btnBack.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBackActionPerformed(evt);
            }
        });
        add(btnBack, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 500, 100, 30));

        btnApply.setText("Apply Changes");
        btnApply.setBackground(new java.awt.Color(0, 200, 0));
        btnApply.setForeground(new java.awt.Color(255, 255, 255));
        btnApply.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnApplyActionPerformed(evt);
            }
        });
        add(btnApply, new org.netbeans.lib.awtextra.AbsoluteConstraints(800, 500, 120, 30));

        lblTotalRecommendations.setFont(new java.awt.Font("Dialog", 1, 14));
        lblTotalRecommendations.setText("Recommendations: 0");
        add(lblTotalRecommendations, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 90, 600, -1));
    }// </editor-fold>

    private void btnBackActionPerformed(java.awt.event.ActionEvent evt) {
        CardSequencePanel.remove(this);
        ((java.awt.CardLayout) CardSequencePanel.getLayout()).next(CardSequencePanel);
    }

    private void btnApplyActionPerformed(java.awt.event.ActionEvent evt) {
        int confirm = JOptionPane.showConfirmDialog(this,
            "Apply " + recommendations.size() + " price adjustments?\nThis will lower target prices for products selling below target.",
            "Confirm Changes",
            JOptionPane.YES_NO_OPTION);
        
        if (confirm == JOptionPane.YES_OPTION) {
            PricingController controller = new PricingController(business);
            controller.applyAdjustments(recommendations);
            
            JOptionPane.showMessageDialog(this,
                "Successfully applied " + recommendations.size() + " price adjustments!",
                "Success",
                JOptionPane.INFORMATION_MESSAGE);
            
            loadRecommendations(); // Refresh
        }
    }

    // Variables declaration
    private javax.swing.JButton btnBack;
    private javax.swing.JButton btnApply;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblTotalRecommendations;
    private javax.swing.JTable recommendationsTable;
    // End of variables declaration
}