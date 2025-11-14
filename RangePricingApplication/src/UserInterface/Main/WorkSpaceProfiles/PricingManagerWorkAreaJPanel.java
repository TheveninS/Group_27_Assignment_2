package UserInterface.Main.WorkSpaceProfiles;

import TheBusiness.Business.Business;
import UserInterface.PricingManagement.*;
import javax.swing.JPanel;

/**
 * Pricing Manager Workspace - Main Dashboard
 * @author Anush
 */
public class PricingManagerWorkAreaJPanel extends javax.swing.JPanel {

    javax.swing.JPanel CardSequencePanel;
    Business business;

    public PricingManagerWorkAreaJPanel(Business b, JPanel clp) {
        business = b;
        this.CardSequencePanel = clp;
        initComponents();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        btnBrowsePerformance = new javax.swing.JButton();
        btnAdjustLower = new javax.swing.JButton();
        btnAdjustHigher = new javax.swing.JButton();
        btnRunSimulation = new javax.swing.JButton();
        btnMaximizeMargins = new javax.swing.JButton();
        btnFinalReport = new javax.swing.JButton();

        setForeground(new java.awt.Color(51, 51, 51));

        jLabel1.setFont(new java.awt.Font("Dialog", 1, 24));
        jLabel1.setText("Pricing Management");

        btnBrowsePerformance.setBackground(new java.awt.Color(102, 153, 255));
        btnBrowsePerformance.setFont(getFont());
        btnBrowsePerformance.setForeground(new java.awt.Color(255, 255, 255));
        btnBrowsePerformance.setText("Browse Product Price Performance");
        btnBrowsePerformance.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnBrowsePerformance.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBrowsePerformanceActionPerformed(evt);
            }
        });

        btnAdjustLower.setBackground(new java.awt.Color(102, 153, 255));
        btnAdjustLower.setFont(getFont());
        btnAdjustLower.setForeground(new java.awt.Color(255, 255, 255));
        btnAdjustLower.setText("Adjust Target Prices Lower");
        btnAdjustLower.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnAdjustLower.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAdjustLowerActionPerformed(evt);
            }
        });

        btnAdjustHigher.setBackground(new java.awt.Color(102, 153, 255));
        btnAdjustHigher.setFont(getFont());
        btnAdjustHigher.setForeground(new java.awt.Color(255, 255, 255));
        btnAdjustHigher.setText("Adjust Target Prices Higher");
        btnAdjustHigher.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnAdjustHigher.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAdjustHigherActionPerformed(evt);
            }
        });

        btnRunSimulation.setBackground(new java.awt.Color(102, 153, 255));
        btnRunSimulation.setFont(getFont());
        btnRunSimulation.setForeground(new java.awt.Color(255, 255, 255));
        btnRunSimulation.setText("Run Simulation");
        btnRunSimulation.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnRunSimulation.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRunSimulationActionPerformed(evt);
            }
        });

        btnMaximizeMargins.setBackground(new java.awt.Color(102, 153, 255));
        btnMaximizeMargins.setFont(getFont());
        btnMaximizeMargins.setForeground(new java.awt.Color(255, 255, 255));
        btnMaximizeMargins.setText("Maximize Profit Margins");
        btnMaximizeMargins.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnMaximizeMargins.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMaximizeMarginsActionPerformed(evt);
            }
        });

        btnFinalReport.setBackground(new java.awt.Color(102, 153, 255));
        btnFinalReport.setFont(getFont());
        btnFinalReport.setForeground(new java.awt.Color(255, 255, 255));
        btnFinalReport.setText("Generate Final Report");
        btnFinalReport.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnFinalReport.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFinalReportActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(101, 101, 101)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(btnBrowsePerformance, javax.swing.GroupLayout.DEFAULT_SIZE, 240, Short.MAX_VALUE)
                            .addComponent(btnAdjustLower, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnAdjustHigher, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(75, 75, 75)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(btnRunSimulation, javax.swing.GroupLayout.DEFAULT_SIZE, 240, Short.MAX_VALUE)
                            .addComponent(btnMaximizeMargins, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnFinalReport, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap(400, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(jLabel1)
                .addGap(50, 50, 50)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnBrowsePerformance, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnRunSimulation, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(30, 30, 30)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAdjustLower, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnMaximizeMargins, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(30, 30, 30)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAdjustHigher, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnFinalReport, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(250, Short.MAX_VALUE))
        );
    }// </editor-fold>

    private void btnBrowsePerformanceActionPerformed(java.awt.event.ActionEvent evt) {
        BrowseProductPerformanceJPanel panel = new BrowseProductPerformanceJPanel(business, CardSequencePanel);
        CardSequencePanel.add("BrowsePerformance", panel);
        ((java.awt.CardLayout) CardSequencePanel.getLayout()).next(CardSequencePanel);
    }

    private void btnAdjustLowerActionPerformed(java.awt.event.ActionEvent evt) {
        AdjustPricesLowerJPanel panel = new AdjustPricesLowerJPanel(business, CardSequencePanel);
        CardSequencePanel.add("AdjustLower", panel);
        ((java.awt.CardLayout) CardSequencePanel.getLayout()).next(CardSequencePanel);
    }

    private void btnAdjustHigherActionPerformed(java.awt.event.ActionEvent evt) {
        AdjustPricesHigherJPanel panel = new AdjustPricesHigherJPanel(business, CardSequencePanel);
        CardSequencePanel.add("AdjustHigher", panel);
        ((java.awt.CardLayout) CardSequencePanel.getLayout()).next(CardSequencePanel);
    }

    private void btnRunSimulationActionPerformed(java.awt.event.ActionEvent evt) {
        RunSimulationJPanel panel = new RunSimulationJPanel(business, CardSequencePanel);
        CardSequencePanel.add("RunSimulation", panel);
        ((java.awt.CardLayout) CardSequencePanel.getLayout()).next(CardSequencePanel);
    }

    private void btnMaximizeMarginsActionPerformed(java.awt.event.ActionEvent evt) {
        MaximizeProfitMarginsJPanel panel = new MaximizeProfitMarginsJPanel(business, CardSequencePanel);
        CardSequencePanel.add("MaximizeMargins", panel);
        ((java.awt.CardLayout) CardSequencePanel.getLayout()).next(CardSequencePanel);
    }

    private void btnFinalReportActionPerformed(java.awt.event.ActionEvent evt) {
        GenerateFinalReportJPanel panel = new GenerateFinalReportJPanel(business, CardSequencePanel);
        CardSequencePanel.add("FinalReport", panel);
        ((java.awt.CardLayout) CardSequencePanel.getLayout()).next(CardSequencePanel);
    }

    // Variables declaration
    private javax.swing.JButton btnBrowsePerformance;
    private javax.swing.JButton btnAdjustLower;
    private javax.swing.JButton btnAdjustHigher;
    private javax.swing.JButton btnRunSimulation;
    private javax.swing.JButton btnMaximizeMargins;
    private javax.swing.JButton btnFinalReport;
    private javax.swing.JLabel jLabel1;
    // End of variables declaration
}