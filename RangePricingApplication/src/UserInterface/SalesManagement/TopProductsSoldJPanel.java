package UserInterface.SalesManagement;

import TheBusiness.Business.Business;
import TheBusiness.OrderManagement.Order;
import TheBusiness.OrderManagement.OrderItem;
import TheBusiness.ProductManagement.Product;
import TheBusiness.SalesManagement.SalesPersonProfile;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class TopProductsSoldJPanel extends JPanel {
    
    private Business business;
    private JPanel cardPanel;
    private SalesPersonProfile salesperson;
    private JTable table;
    
    public TopProductsSoldJPanel(Business b, SalesPersonProfile spp, JPanel cards) {
        this.business = b;
        this.salesperson = spp;
        this.cardPanel = cards;
        
        setBackground(new Color(0, 153, 153));
        setLayout(null);
        
        JLabel title = new JLabel("Top Products I've Sold");
        title.setFont(new Font("Arial", Font.PLAIN, 24));
        title.setBounds(20, 20, 400, 30);
        add(title);
        
        // Table
        String[] columns = {"Product", "Quantity Sold", "Total Revenue", "Avg Price"};
        DefaultTableModel model = new DefaultTableModel(columns, 0) {
            public boolean isCellEditable(int row, int col) { return false; }
        };
        table = new JTable(model);
        JScrollPane scroll = new JScrollPane(table);
        scroll.setBounds(20, 70, 860, 380);
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
        
        ArrayList<Order> orders = salesperson.getSalesOrders();
        Map<String, ProductStats> productMap = new HashMap<>();
        
        for (Order order : orders) {
            for (OrderItem item : order.orderitems) {
                String productName = item.getSelectedProduct().toString();
                int quantity = item.getQuantity();
                int revenue = item.getOrderItemTotal();
                
                if (!productMap.containsKey(productName)) {
                    productMap.put(productName, new ProductStats(productName));
                }
                
                ProductStats stats = productMap.get(productName);
                stats.quantitySold += quantity;
                stats.totalRevenue += revenue;
            }
        }
        
        for (ProductStats stats : productMap.values()) {
            int avgPrice = stats.quantitySold > 0 ? stats.totalRevenue / stats.quantitySold : 0;
            
            model.addRow(new Object[]{
                stats.productName,
                stats.quantitySold,
                String.format("$%,d", stats.totalRevenue),
                String.format("$%,d", avgPrice)
            });
        }
        
        if (productMap.isEmpty()) {
            model.addRow(new Object[]{"No products sold yet", "-", "-", "-"});
        }
    }
    
    private class ProductStats {
        String productName;
        int quantitySold;
        int totalRevenue;
        
        ProductStats(String name) {
            this.productName = name;
            this.quantitySold = 0;
            this.totalRevenue = 0;
        }
    }
}