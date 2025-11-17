package UserInterface.SalesManagement;

import TheBusiness.Business.Business;
import TheBusiness.OrderManagement.Order;
import TheBusiness.SalesManagement.SalesPersonProfile;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

public class ManageSalesPersonnelJPanel extends JPanel {
    
    private Business business;
    private JPanel cardPanel;
    private JTable table;
    
    public ManageSalesPersonnelJPanel(Business b, JPanel cards) {
        this.business = b;
        this.cardPanel = cards;
        
        setBackground(new Color(0, 153, 153));
        setLayout(null);
        
        JLabel title = new JLabel("Manage Sales Personnel");
        title.setFont(new Font("Arial", Font.PLAIN, 24));
        title.setBounds(20, 20, 400, 30);
        add(title);
        
        JLabel info = new JLabel("Sales team performance and order tracking");
        info.setBounds(20, 55, 500, 20);
        add(info);
        
        // Table
        String[] columns = {"Sales Person", "Total Orders", "Total Revenue", "Avg Order Value"};
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
    
    // Use the public getter method
    ArrayList<SalesPersonProfile> salesPeople = business.getSalesPersonDirectory().getSalesPersonList();
    
    for (SalesPersonProfile spp : salesPeople) {
        // Use getter instead of direct field access
        ArrayList<Order> orders = spp.getSalesOrders();
        int totalOrders = orders.size();
        int totalRevenue = 0;
        
        for (Order order : orders) {
            totalRevenue += order.getOrderTotal();
        }
        
        int avgOrderValue = totalOrders > 0 ? totalRevenue / totalOrders : 0;
        
        model.addRow(new Object[]{
            spp.getPerson().getPersonId(),
            totalOrders,
            String.format("$%,d", totalRevenue),
            String.format("$%,d", avgOrderValue)
        });
    }
    
    if (salesPeople.isEmpty()) {
        model.addRow(new Object[]{"No sales personnel yet", "-", "-", "-"});
    }
}
    
}