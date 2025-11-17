package UserInterface.SalesManagement;

import TheBusiness.Business.Business;
import TheBusiness.OrderManagement.Order;
import TheBusiness.SalesManagement.SalesPersonProfile;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class ManageSalesProfileJPanel extends JPanel {
    
    private Business business;
    private JPanel cardPanel;
    private SalesPersonProfile salesperson;
    private JTextField nameField, totalOrdersField, totalRevenueField, avgOrderField;
    
    public ManageSalesProfileJPanel(Business b, SalesPersonProfile spp, JPanel cards) {
        this.business = b;
        this.salesperson = spp;
        this.cardPanel = cards;
        
        setBackground(new Color(0, 153, 153));
        setLayout(null);
        
        JLabel title = new JLabel("My Sales Profile");
        title.setFont(new Font("Arial", Font.PLAIN, 24));
        title.setBounds(20, 20, 400, 30);
        add(title);
        
        // Profile Information Panel
        JPanel infoPanel = new JPanel();
        infoPanel.setBorder(BorderFactory.createTitledBorder("Profile Information"));
        infoPanel.setLayout(null);
        infoPanel.setBounds(20, 70, 860, 200);
        
        JLabel nameLbl = new JLabel("Name:");
        nameLbl.setBounds(30, 40, 150, 25);
        infoPanel.add(nameLbl);
        
        nameField = new JTextField();
        nameField.setBounds(180, 40, 250, 25);
        nameField.setEditable(false);
        infoPanel.add(nameField);
        
        JLabel ordersLbl = new JLabel("Total Orders:");
        ordersLbl.setBounds(30, 80, 150, 25);
        infoPanel.add(ordersLbl);
        
        totalOrdersField = new JTextField();
        totalOrdersField.setBounds(180, 80, 250, 25);
        totalOrdersField.setEditable(false);
        infoPanel.add(totalOrdersField);
        
        JLabel revenueLbl = new JLabel("Total Revenue:");
        revenueLbl.setBounds(30, 120, 150, 25);
        infoPanel.add(revenueLbl);
        
        totalRevenueField = new JTextField();
        totalRevenueField.setBounds(180, 120, 250, 25);
        totalRevenueField.setEditable(false);
        infoPanel.add(totalRevenueField);
        
        JLabel avgLbl = new JLabel("Average Order Value:");
        avgLbl.setBounds(30, 160, 150, 25);
        infoPanel.add(avgLbl);
        
        avgOrderField = new JTextField();
        avgOrderField.setBounds(180, 160, 250, 25);
        avgOrderField.setEditable(false);
        infoPanel.add(avgOrderField);
        
        add(infoPanel);
        
        // Performance Panel
        JPanel perfPanel = new JPanel();
        perfPanel.setBorder(BorderFactory.createTitledBorder("Performance Summary"));
        perfPanel.setLayout(null);
        perfPanel.setBounds(20, 290, 860, 150);
        
        JTextArea perfArea = new JTextArea();
        perfArea.setEditable(false);
        perfArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        JScrollPane scroll = new JScrollPane(perfArea);
        scroll.setBounds(20, 30, 820, 100);
        perfPanel.add(scroll);
        
        add(perfPanel);
        
        // Load data
        loadProfileData(perfArea);
        
        JButton back = new JButton("<< Back");
        back.setBounds(20, 460, 90, 30);
        back.addActionListener(e -> {
            cardPanel.remove(this);
            ((CardLayout) cardPanel.getLayout()).next(cardPanel);
        });
        add(back);
    }
    
    private void loadProfileData(JTextArea perfArea) {
        nameField.setText(salesperson.getPerson().getPersonId());
        
        ArrayList<Order> orders = salesperson.getSalesOrders();
        int totalOrders = orders.size();
        int totalRevenue = 0;
        int ordersAboveTarget = 0;
        int ordersBelowTarget = 0;
        
        for (Order order : orders) {
            totalRevenue += order.getOrderTotal();
            if (order.isOrderAboveTotalTarget()) {
                ordersAboveTarget++;
            } else {
                ordersBelowTarget++;
            }
        }
        
        int avgOrderValue = totalOrders > 0 ? totalRevenue / totalOrders : 0;
        
        totalOrdersField.setText(String.valueOf(totalOrders));
        totalRevenueField.setText(String.format("$%,d", totalRevenue));
        avgOrderField.setText(String.format("$%,d", avgOrderValue));
        
        // Performance summary
        StringBuilder sb = new StringBuilder();
        sb.append("PERFORMANCE SUMMARY\n");
        sb.append("═══════════════════════════════════════════════════════\n");
        sb.append(String.format("Total Orders: %d\n", totalOrders));
        sb.append(String.format("Orders Above Target: %d (%.1f%%)\n", 
            ordersAboveTarget, totalOrders > 0 ? (ordersAboveTarget * 100.0 / totalOrders) : 0));
        sb.append(String.format("Orders Below Target: %d (%.1f%%)\n", 
            ordersBelowTarget, totalOrders > 0 ? (ordersBelowTarget * 100.0 / totalOrders) : 0));
        sb.append(String.format("\nTotal Revenue: $%,d\n", totalRevenue));
        sb.append(String.format("Average Order Value: $%,d\n", avgOrderValue));
        
        perfArea.setText(sb.toString());
    }
}