package UserInterface.ManageTheBusiness;

import TheBusiness.Business.Business;
import TheBusiness.ProductManagement.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class ManageBusinessDetail1 extends javax.swing.JPanel {

    JPanel CardSequencePanel;
    Business business;

    public ManageBusinessDetail1(Business bz, JPanel jp) {
        CardSequencePanel = jp;
        this.business = bz;
        
        setBackground(new Color(0, 153, 153));
        setLayout(null);
        
        JLabel title = new JLabel("Detailed Business Analytics");
        title.setFont(new Font("Arial", Font.BOLD, 24));
        title.setBounds(20, 20, 450, 30);
        add(title);
        
        // Top Products Table
        JLabel topProductsLbl = new JLabel("Top 20 Products by Revenue");
        topProductsLbl.setFont(new Font("Arial", Font.BOLD, 14));
        topProductsLbl.setBounds(20, 65, 300, 25);
        add(topProductsLbl);
        
        String[] columns = {"Product", "Revenue", "Units Sold", "Avg Price", "Margin"};
        DefaultTableModel model = new DefaultTableModel(columns, 0) {
            public boolean isCellEditable(int row, int col) { return false; }
        };
        JTable table = new JTable(model);
        JScrollPane scroll = new JScrollPane(table);
        scroll.setBounds(20, 95, 860, 350);
        add(scroll);
        
        // Load data
        PricingAnalyzer analyzer = new PricingAnalyzer(business);
        ArrayList<ProductPerformance> products = analyzer.getProductsWithSales();
        
        // Sort by revenue
        Collections.sort(products, new Comparator<ProductPerformance>() {
            @Override
            public int compare(ProductPerformance a, ProductPerformance b) {
                return Integer.compare(b.getTotalRevenue(), a.getTotalRevenue());
            }
        });
        
        // Add top 20 to table
        int count = 0;
        for (ProductPerformance pp : products) {
            if (count >= 20) break;
            
            model.addRow(new Object[]{
                pp.getProduct().toString(),
                String.format("$%,d", pp.getTotalRevenue()),
                pp.getTotalQuantitySold(),
                String.format("$%,d", pp.getAvgActualPrice()),
                String.format("$%,d", pp.getPricePerformance())
            });
            
            count++;
        }
        
        if (products.isEmpty()) {
            model.addRow(new Object[]{"No product sales data yet", "-", "-", "-", "-"});
        }
        
        JButton back = new JButton("<< Back");
        back.setBounds(20, 460, 90, 30);
        back.addActionListener(e -> {
            CardSequencePanel.remove(this);
            ((java.awt.CardLayout) CardSequencePanel.getLayout()).next(CardSequencePanel);
        });
        add(back);
    }
}