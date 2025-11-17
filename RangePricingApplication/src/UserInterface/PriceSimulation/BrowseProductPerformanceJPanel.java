package UserInterface.PriceSimulation;

import TheBusiness.Business.Business;
import TheBusiness.ProductManagement.*;
import TheBusiness.Supplier.Supplier;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.*;

public class BrowseProductPerformanceJPanel extends JPanel {
    
    private Business business;
    private JPanel cardPanel;
    private JTable table;
    private JComboBox<String> filterCombo;
    
    public BrowseProductPerformanceJPanel(Business b, JPanel cards) {
        this.business = b;
        this.cardPanel = cards;
        
        setBackground(new Color(0, 153, 153));
        setLayout(null);
        
        JLabel title = new JLabel("Browse Product Price Performance");
        title.setFont(new Font("Arial", Font.PLAIN, 24));
        title.setBounds(20, 20, 450, 30);
        add(title);
        
        // Filter
        JLabel filterLbl = new JLabel("Filter:");
        filterLbl.setBounds(20, 60, 60, 25);
        add(filterLbl);
        
        filterCombo = new JComboBox<>(new String[]{"All Products", "Below Target (>70%)", "Above Target (>70%)", "Has Sales"});
        filterCombo.setBounds(80, 60, 200, 25);
        filterCombo.addActionListener(e -> loadData());
        add(filterCombo);
        
        // Table
        String[] columns = {"Product", "Target", "Avg Actual", "Revenue", "Below Count", "Above Count", "Margin"};
        DefaultTableModel model = new DefaultTableModel(columns, 0) {
            public boolean isCellEditable(int row, int col) { return false; }
        };
        table = new JTable(model);
        JScrollPane scroll = new JScrollPane(table);
        scroll.setBounds(20, 100, 860, 350);
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
        
        String filter = (String) filterCombo.getSelectedItem();
        
        for (ProductPerformance pp : products) {
            boolean include = false;
            
            switch(filter) {
                case "All Products": include = true; break;
                case "Below Target (>70%)": include = pp.isConsistentlyBelowTarget(); break;
                case "Above Target (>70%)": include = pp.isConsistentlyAboveTarget(); break;
                case "Has Sales": include = pp.getTotalQuantitySold() > 0; break;
            }
            
            if (include) {
                model.addRow(new Object[]{
                    pp.getProduct().toString(),
                    String.format("$%,d", pp.getProduct().getTargetPrice()),
                    String.format("$%,d", pp.getAvgActualPrice()),
                    String.format("$%,d", pp.getTotalRevenue()),
                    pp.getFrequencyBelowTarget(),
                    pp.getFrequencyAboveTarget(),
                    String.format("$%,d", pp.getPricePerformance())
                });
            }
        }
    }
}