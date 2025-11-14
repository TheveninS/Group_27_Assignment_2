package UserInterface.PricingManagement;

import TheBusiness.Business.Business;
import TheBusiness.ProductManagement.PricingAnalyzer;
import TheBusiness.ProductManagement.ProductPerformance;
import java.util.ArrayList;
import javax.swing.JPanel;
import javax.swing.table.DefaultTableModel;

/**
 * Browse Product Price Performance Screen
 * @author anush
 */
public class BrowseProductPerformanceJPanel extends javax.swing.JPanel {

    JPanel CardSequencePanel;
    Business business;
    PricingAnalyzer analyzer;

    public BrowseProductPerformanceJPanel(Business bz, JPanel jp) {
        CardSequencePanel = jp;
        this.business = bz;
        this.analyzer = new PricingAnalyzer(business);
        initComponents();
        loadProductData();
    }

    private void loadProductData() {
        ArrayList<ProductPerformance> products = analyzer.getProductsWithSales();
        
        DefaultTableModel model = (DefaultTableModel) productsTable.getModel();
        model.setRowCount(0);
        
        for (ProductPerformance perf : products) {
            Object[] row = {
                perf.getProduct().toString(),
                perf.getProduct().getTargetPrice(),
                perf.getAvgActualPrice(),
                perf.getFrequencyAboveTarget(),
                perf.getFrequencyBelowTarget(),
                perf.getTotalRevenue(),
                perf.getPricePerformance()
            };
            model.addRow(row);
        }
        
        // Update summary
        lblTotalProducts.setText("Total Products: " + products.size());
        lblTotalRevenue.setText("Total Revenue: $" + String.format("%,d", analyzer.getTotalCompanyRevenue()));
        lblTotalMargin.setText("Total Margin: $" + String.format("%,d", analyzer.getTotalProfitMargin()));
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        productsTable = new javax.swing.JTable();
        btnBack = new javax.swing.JButton();
        btnRefresh = new javax.swing.JButton();
        lblTotalProducts = new javax.swing.JLabel();
        lblTotalRevenue = new javax.swing.JLabel();
        lblTotalMargin = new javax.swing.JLabel();

        setBackground(new java.awt.Color(0, 153, 153));
        setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Arial", 0, 24));
        jLabel1.setText("Browse Product Price Performance");
        add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 20, 550, -1));

        productsTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {},
            new String [] {
                "Product", "Target Price", "Avg Actual", "Freq Above", "Freq Below", "Revenue", "Margin"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(productsTable);

        add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 120, 900, 350));

        btnBack.setText("<< Back");
        btnBack.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBackActionPerformed(evt);
            }
        });
        add(btnBack, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 500, 100, 30));

        btnRefresh.setText("Refresh Data");
        btnRefresh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRefreshActionPerformed(evt);
            }
        });
        add(btnRefresh, new org.netbeans.lib.awtextra.AbsoluteConstraints(820, 500, 100, 30));

        lblTotalProducts.setFont(new java.awt.Font("Dialog", 1, 14));
        lblTotalProducts.setText("Total Products: 0");
        add(lblTotalProducts, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 70, 250, -1));

        lblTotalRevenue.setFont(new java.awt.Font("Dialog", 1, 14));
        lblTotalRevenue.setText("Total Revenue: $0");
        add(lblTotalRevenue, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 70, 250, -1));

        lblTotalMargin.setFont(new java.awt.Font("Dialog", 1, 14));
        lblTotalMargin.setText("Total Margin: $0");
        add(lblTotalMargin, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 70, 250, -1));
    }// </editor-fold>

    private void btnBackActionPerformed(java.awt.event.ActionEvent evt) {
        CardSequencePanel.remove(this);
        ((java.awt.CardLayout) CardSequencePanel.getLayout()).next(CardSequencePanel);
    }

    private void btnRefreshActionPerformed(java.awt.event.ActionEvent evt) {
        loadProductData();
    }

    // Variables declaration
    private javax.swing.JButton btnBack;
    private javax.swing.JButton btnRefresh;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblTotalProducts;
    private javax.swing.JLabel lblTotalRevenue;
    private javax.swing.JLabel lblTotalMargin;
    private javax.swing.JTable productsTable;
    // End of variables declaration
}