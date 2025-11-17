package UserInterface.PriceSimulation;

import TheBusiness.Business.Business;
import TheBusiness.ProductManagement.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

public class AdjustTargetLowerJPanel extends JPanel {
    
    private Business business;
    private JPanel cardPanel;
    private JTable table;
    private JTextField percentField;
    
    public AdjustTargetLowerJPanel(Business b, JPanel cards) {
        this.business = b;
        this.cardPanel = cards;
        
        setBackground(new Color(0, 153, 153));
        setLayout(null);
        
        JLabel title = new JLabel("Adjust Target Prices Lower");
        title.setFont(new Font("Arial", Font.PLAIN, 24));
        title.setBounds(20, 20, 400, 30);
        add(title);
        
        JLabel info = new JLabel("Products consistently selling below target (>70% of orders)");
        info.setBounds(20, 55, 500, 20);
        add(info);
        
        // Adjustment controls
        JLabel percentLbl = new JLabel("Reduce by %:");
        percentLbl.setBounds(500, 55, 100, 25);
        add(percentLbl);
        
        percentField = new JTextField("10");
        percentField.setBounds(590, 55, 60, 25);
        add(percentField);
        
        JButton applyBtn = new JButton("Apply Reduction");
        applyBtn.setBounds(660, 55, 140, 25);
        applyBtn.addActionListener(e -> applyReduction());
        add(applyBtn);
        
        // Table
        String[] columns = {"Product", "Current Target", "Avg Actual", "New Target", "Below %", "Status"};
        DefaultTableModel model = new DefaultTableModel(columns, 0) {
            public boolean isCellEditable(int row, int col) { return false; }
        };
        table = new JTable(model);
        JScrollPane scroll = new JScrollPane(table);
        scroll.setBounds(20, 90, 860, 360);
        add(scroll);
        
        loadData();
        
        JButton back = new JButton("<< Back");
        back.setBounds(20, 470, 90, 30);
        back.addActionListener(e -> {
            cardPanel.remove(this);
            ((CardLayout) cardPanel.getLayout()).next(cardPanel);
        });
        add(back);
    }
    
    private void loadData() {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0);
        
        PricingAnalyzer analyzer = new PricingAnalyzer(business);
        ArrayList<ProductPerformance> products = analyzer.getProductsWithSales();
        
        for (ProductPerformance pp : products) {
            if (pp.isConsistentlyBelowTarget()) {
                int currentTarget = pp.getProduct().getTargetPrice();
                int avgActual = pp.getAvgActualPrice();
                double belowPercent = (pp.getFrequencyBelowTarget() * 100.0) / 
                    (pp.getFrequencyBelowTarget() + pp.getFrequencyAboveTarget());
                
                model.addRow(new Object[]{
                    pp.getProduct().toString(),
                    String.format("$%,d", currentTarget),
                    String.format("$%,d", avgActual),
                    "Not adjusted",
                    String.format("%.1f%%", belowPercent),
                    "Ready"
                });
            }
        }
    }
    
    private void applyReduction() {
        try {
            int percent = Integer.parseInt(percentField.getText());
            if (percent < 1 || percent > 50) {
                JOptionPane.showMessageDialog(this, "Enter percentage between 1-50");
                return;
            }
            
            DefaultTableModel model = (DefaultTableModel) table.getModel();
            PricingAnalyzer analyzer = new PricingAnalyzer(business);
            ArrayList<ProductPerformance> products = analyzer.getProductsWithSales();
            
            int adjusted = 0;
            for (ProductPerformance pp : products) {
                if (pp.isConsistentlyBelowTarget()) {
                    int oldTarget = pp.getProduct().getTargetPrice();
                    int newTarget = oldTarget - (oldTarget * percent / 100);
                    pp.getProduct().updateTargetPrice(newTarget);
                    adjusted++;
                }
            }
            
            loadData();
            updateTableWithNewTargets(percent);
            JOptionPane.showMessageDialog(this, adjusted + " products adjusted by -" + percent + "%");
            
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Invalid percentage");
        }
    }
    
    private void updateTableWithNewTargets(int percent) {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        for (int i = 0; i < model.getRowCount(); i++) {
            String targetStr = model.getValueAt(i, 1).toString().replace("$", "").replace(",", "");
            int oldTarget = Integer.parseInt(targetStr);
            int newTarget = oldTarget - (oldTarget * percent / 100);
            model.setValueAt(String.format("$%,d", newTarget), i, 3);
            model.setValueAt("Adjusted ✓", i, 5);
        }
    }
}