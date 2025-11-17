package UserInterface.SalesManagement;

import TheBusiness.Business.Business;
import TheBusiness.OrderManagement.Order;
import TheBusiness.SalesManagement.SalesPersonProfile;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

public class SalesCommissionJPanel extends JPanel {
    
    private Business business;
    private JPanel cardPanel;
    private SalesPersonProfile salesperson;
    private JTable table;
    private JTextField totalRevenueField, totalCommissionField, avgCommissionField;
    
    // Commission rate (5% example)
    private static final double COMMISSION_RATE = 0.05;
    
    public SalesCommissionJPanel(Business b, SalesPersonProfile spp, JPanel cards) {
        this.business = b;
        this.salesperson = spp;
        this.cardPanel = cards;
        
        setBackground(new Color(0, 153, 153));
        setLayout(null);
        
        JLabel title = new JLabel("Sales Commission Report");
        title.setFont(new Font("Arial", Font.PLAIN, 24));
        title.setBounds(20, 20, 400, 30);
        add(title);
        
        JLabel info = new JLabel("Commission Rate: " + (COMMISSION_RATE * 100) + "%");
        info.setBounds(20, 55, 300, 20);
        add(info);
        
        // Summary Panel
        JPanel summaryPanel = new JPanel();
        summaryPanel.setBorder(BorderFactory.createTitledBorder("Commission Summary"));
        summaryPanel.setLayout(null);
        summaryPanel.setBounds(20, 85, 860, 80);
        
        JLabel revLbl = new JLabel("Total Revenue:");
        revLbl.setBounds(30, 30, 120, 25);
        summaryPanel.add(revLbl);
        
        totalRevenueField = new JTextField();
        totalRevenueField.setBounds(150, 30, 150, 25);
        totalRevenueField.setEditable(false);
        summaryPanel.add(totalRevenueField);
        
        JLabel commLbl = new JLabel("Total Commission:");
        commLbl.setBounds(340, 30, 120, 25);
        summaryPanel.add(commLbl);
        
        totalCommissionField = new JTextField();
        totalCommissionField.setBounds(460, 30, 150, 25);
        totalCommissionField.setEditable(false);
        totalCommissionField.setFont(new Font("Arial", Font.BOLD, 12));
        totalCommissionField.setForeground(new Color(0, 128, 0));
        summaryPanel.add(totalCommissionField);
        
        JLabel avgLbl = new JLabel("Avg per Order:");
        avgLbl.setBounds(650, 30, 100, 25);
        summaryPanel.add(avgLbl);
        
        avgCommissionField = new JTextField();
        avgCommissionField.setBounds(750, 30, 90, 25);
        avgCommissionField.setEditable(false);
        summaryPanel.add(avgCommissionField);
        
        add(summaryPanel);
        
        // Table
        String[] columns = {"Order #", "Customer", "Order Total", "Commission"};
        DefaultTableModel model = new DefaultTableModel(columns, 0) {
            public boolean isCellEditable(int row, int col) { return false; }
        };
        table = new JTable(model);
        JScrollPane scroll = new JScrollPane(table);
        scroll.setBounds(20, 180, 860, 270);
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
        int totalRevenue = 0;
        double totalCommission = 0;
        
        int orderNum = 1;
        for (Order order : orders) {
            int orderTotal = order.getOrderTotal();
            double commission = orderTotal * COMMISSION_RATE;
            
            totalRevenue += orderTotal;
            totalCommission += commission;
            
            model.addRow(new Object[]{
                "Order #" + orderNum++,
                order.customer.getCustomerId(),
                String.format("$%,d", orderTotal),
                String.format("$%,.2f", commission)
            });
        }
        
        double avgCommission = orders.size() > 0 ? totalCommission / orders.size() : 0;
        
        totalRevenueField.setText(String.format("$%,d", totalRevenue));
        totalCommissionField.setText(String.format("$%,.2f", totalCommission));
        avgCommissionField.setText(String.format("$%,.2f", avgCommission));
        
        if (orders.isEmpty()) {
            model.addRow(new Object[]{"No orders yet", "-", "-", "-"});
        }
    }
}