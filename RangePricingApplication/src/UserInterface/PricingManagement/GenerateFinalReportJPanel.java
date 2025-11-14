package UserInterface.PricingManagement;

import TheBusiness.Business.Business;
import TheBusiness.ProductManagement.*;
import javax.swing.JPanel;
import javax.swing.JOptionPane;
import javax.swing.JFileChooser;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;
import java.util.Map;

/**
 * Generate Final Report Screen
 * @author anush
 */
public class GenerateFinalReportJPanel extends javax.swing.JPanel {

    JPanel CardSequencePanel;
    Business business;
    FinalReport currentReport;

    public GenerateFinalReportJPanel(Business bz, JPanel jp) {
        CardSequencePanel = jp;
        this.business = bz;
        initComponents();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        cmbStrategy = new javax.swing.JComboBox<>();
        btnGenerate = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        reportTable = new javax.swing.JTable();
        btnBack = new javax.swing.JButton();
        btnExportCSV = new javax.swing.JButton();
        lblSummary = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();

        setBackground(new java.awt.Color(0, 153, 153));
        setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Arial", 0, 24));
        jLabel1.setText("Generate Final Report");
        add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 20, 550, -1));

        jLabel2.setText("Select Strategy:");
        add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 70, 120, -1));

        cmbStrategy.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { 
            "LOWER_ONLY", 
            "HIGHER_ONLY", 
            "BOTH" 
        }));
        add(cmbStrategy, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 65, 200, 30));

        btnGenerate.setText("Generate Report");
        btnGenerate.setBackground(new java.awt.Color(0, 150, 200));
        btnGenerate.setForeground(new java.awt.Color(255, 255, 255));
        btnGenerate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGenerateActionPerformed(evt);
            }
        });
        add(btnGenerate, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 65, 150, 30));

        reportTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {},
            new String [] {
                "Product", "Old Target", "New Target", "Target Δ", "Old Revenue", "New Revenue", "Old Margin", "New Margin", "Margin Δ", "Improved?"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(reportTable);

        add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 160, 900, 280));

        btnBack.setText("<< Back");
        btnBack.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBackActionPerformed(evt);
            }
        });
        add(btnBack, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 500, 100, 30));

        btnExportCSV.setText("Export to CSV");
        btnExportCSV.setEnabled(false);
        btnExportCSV.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExportCSVActionPerformed(evt);
            }
        });
        add(btnExportCSV, new org.netbeans.lib.awtextra.AbsoluteConstraints(800, 500, 120, 30));

        lblSummary.setFont(new java.awt.Font("Dialog", 1, 13));
        lblSummary.setText("");
        add(lblSummary, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 450, 900, 40));

        jLabel3.setFont(new java.awt.Font("Dialog", 1, 14));
        jLabel3.setText("Before/After Comparison:");
        add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 130, 400, -1));
    }// </editor-fold>

    private void btnBackActionPerformed(java.awt.event.ActionEvent evt) {
        CardSequencePanel.remove(this);
        ((java.awt.CardLayout) CardSequencePanel.getLayout()).next(CardSequencePanel);
    }

    private void btnGenerateActionPerformed(java.awt.event.ActionEvent evt) {
        btnGenerate.setEnabled(false);
        btnGenerate.setText("Generating...");
        
        try {
            String strategy = (String) cmbStrategy.getSelectedItem();
            
            // Save current state
            PricingAnalyzer analyzer = new PricingAnalyzer(business);
            Map<Product, ProductPerformance> beforeAnalysis = analyzer.analyzeAllProducts();
            
            // Run optimization
            PricingController controller = new PricingController(business);
            RecommendationEngine engine = new RecommendationEngine();
            
            ArrayList<PriceAdjustment> adjustments;
            if (strategy.equals("LOWER_ONLY")) {
                adjustments = engine.recommendLowerTargets(beforeAnalysis);
            } else if (strategy.equals("HIGHER_ONLY")) {
                adjustments = engine.recommendHigherTargets(beforeAnalysis);
            } else {
                adjustments = engine.getAllRecommendations(beforeAnalysis);
            }
            
            // Generate report
            currentReport = controller.generateFinalReport(beforeAnalysis, adjustments);
            
            // Display in table
            DefaultTableModel model = (DefaultTableModel) reportTable.getModel();
            model.setRowCount(0);
            
            ArrayList<ProductComparison> comparisons = currentReport.getComparisons();
            
            // Show top 20 most improved
            comparisons.sort((a, b) -> Integer.compare(b.getMarginChange(), a.getMarginChange()));
            int count = 0;
            for (ProductComparison comp : comparisons) {
                if (count >= 20) break;
                
                Object[] row = {
                    comp.getProductName(),
                    String.format("$%,d", comp.getOldTarget()),
                    String.format("$%,d", comp.getNewTarget()),
                    String.format("$%+,d", comp.getTargetChange()),
                    String.format("$%,d", comp.getOldRevenue()),
                    String.format("$%,d", comp.getNewRevenue()),
                    String.format("$%,d", comp.getOldMargin()),
                    String.format("$%,d", comp.getNewMargin()),
                    String.format("$%+,d", comp.getMarginChange()),
                    comp.isImproved() ? "✓" : "✗"
                };
                model.addRow(row);
                count++;
            }
            
            // Update summary
            lblSummary.setText(String.format(
                "Summary: %d products adjusted | %d improved (%.1f%%) | Revenue: $%,d → $%,d (%+.1f%%) | Margin: $%,d → $%,d (%+d)",
                currentReport.getProductsAdjusted(),
                currentReport.getProductsImproved(),
                (currentReport.getProductsImproved() * 100.0 / currentReport.getProductsAdjusted()),
                currentReport.getTotalRevenueBefore(),
                currentReport.getTotalRevenueAfter(),
                currentReport.getRevenueChangePercent(),
                currentReport.getTotalMarginBefore(),
                currentReport.getTotalMarginAfter(),
                currentReport.getTotalMarginDelta()
            ));
            
            btnExportCSV.setEnabled(true);
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                "Error generating report: " + e.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        } finally {
            btnGenerate.setEnabled(true);
            btnGenerate.setText("Generate Report");
        }
    }

    private void btnExportCSVActionPerformed(java.awt.event.ActionEvent evt) {
        if (currentReport == null) {
            JOptionPane.showMessageDialog(this, "Please generate a report first!");
            return;
        }
        
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setSelectedFile(new java.io.File("pricing_report.csv"));
        
        int result = fileChooser.showSaveDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            String filename = fileChooser.getSelectedFile().getAbsolutePath();
            if (!filename.endsWith(".csv")) {
                filename += ".csv";
            }
            
            currentReport.exportToCSV(filename);
            
            JOptionPane.showMessageDialog(this,
                "Report exported successfully to:\n" + filename,
                "Export Complete",
                JOptionPane.INFORMATION_MESSAGE);
        }
    }

    // Variables declaration
    private javax.swing.JButton btnBack;
    private javax.swing.JButton btnGenerate;
    private javax.swing.JButton btnExportCSV;
    private javax.swing.JComboBox<String> cmbStrategy;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblSummary;
    private javax.swing.JTable reportTable;
    // End of variables declaration
}