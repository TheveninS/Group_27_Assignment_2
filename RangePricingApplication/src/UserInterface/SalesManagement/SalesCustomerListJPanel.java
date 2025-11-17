package UserInterface.SalesManagement;

import TheBusiness.Business.Business;
import TheBusiness.CustomerManagement.CustomerProfile;
import TheBusiness.OrderManagement.Order;
import TheBusiness.SalesManagement.SalesPersonProfile;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SalesCustomerListJPanel extends JPanel {
    
    private Business business;
    private JPanel cardPanel;
    private SalesPersonProfile salesperson;
    private JTable table;
    
    public SalesCustomerListJPanel(Business b, SalesPersonProfile spp, JPanel cards) {
        this.business = b;
        this.salesperson = spp;
        this.cardPanel = cards;
        
        setBackground(new Color(0, 153, 153));
        setLayout(null);
        
        JLabel title = new JLabel("My Customers");
        title.setFont(new Font("Arial", Font.PLAIN, 24));
        title.setBounds(20, 20, 400, 30);
        add(title);
        
        // Table
        String[] columns = {"Customer Name", "Orders", "Total Revenue", "Avg Order"};
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
        Map<String, CustomerStats> customerMap = new HashMap<>();
        
        for (Order order : orders) {
            String customerId = order.customer.getCustomerId();
            int orderTotal = order.getOrderTotal();
            
            if (!customerMap.containsKey(customerId)) {
                customerMap.put(customerId, new CustomerStats(customerId));
            }
            
            CustomerStats stats = customerMap.get(customerId);
            stats.orderCount++;
            stats.totalRevenue += orderTotal;
        }
        
        for (CustomerStats stats : customerMap.values()) {
            int avgOrder = stats.orderCount > 0 ? stats.totalRevenue / stats.orderCount : 0;
            
            model.addRow(new Object[]{
                stats.customerName,
                stats.orderCount,
                String.format("$%,d", stats.totalRevenue),
                String.format("$%,d", avgOrder)
            });
        }
        
        if (customerMap.isEmpty()) {
            model.addRow(new Object[]{"No customers yet", "-", "-", "-"});
        }
    }
    
    private class CustomerStats {
        String customerName;
        int orderCount;
        int totalRevenue;
        
        CustomerStats(String name) {
            this.customerName = name;
            this.orderCount = 0;
            this.totalRevenue = 0;
        }
    }
}