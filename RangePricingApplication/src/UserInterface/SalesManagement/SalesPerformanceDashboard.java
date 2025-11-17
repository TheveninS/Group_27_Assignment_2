package UserInterface.SalesManagement;

import TheBusiness.Business.Business;
import TheBusiness.OrderManagement.Order;
import TheBusiness.SalesManagement.SalesPersonProfile;
import javax.swing.*;
import java.awt.*;

public class SalesPerformanceDashboard extends JPanel {
    
    private Business business;
    private JPanel cardPanel;
    private SalesPersonProfile salesperson;
    
    public SalesPerformanceDashboard(Business b, SalesPersonProfile spp, JPanel cards) {
        this.business = b;
        this.salesperson = spp;
        this.cardPanel = cards;
        
        setBackground(new Color(0, 153, 153));
        setLayout(null);
        
        JLabel title = new JLabel("Sales Performance Dashboard");
        title.setFont(new Font("Arial", Font.BOLD, 28));
        title.setBounds(20, 20, 500, 35);
        add(title);
        
        JLabel subtitle = new JLabel("Your sales metrics and performance analysis");
        subtitle.setBounds(20, 60, 500, 20);
        add(subtitle);
        
        // Menu buttons
        String[] labels = {
            "My Profile & Stats",
            "Order History",
            "Commission Report",
            "Customer List",
            "Top Products Sold",
            "Performance Summary"
        };
        
        for (int i = 0; i < 6; i++) {
            JButton btn = new JButton(labels[i]);
            btn.setBackground(new Color(102, 153, 255));
            btn.setForeground(Color.WHITE);
            btn.setFont(new Font("Arial", Font.PLAIN, 14));
            btn.setBounds(100, 120 + (i * 50), 320, 40);
            
            final int index = i;
            btn.addActionListener(e -> openPanel(index));
            add(btn);
        }
        
        JButton back = new JButton("<< Back");
        back.setBounds(20, 470, 90, 30);
        back.addActionListener(e -> {
            cardPanel.remove(this);
            ((CardLayout) cardPanel.getLayout()).next(cardPanel);
        });
        add(back);
    }
    
    private void openPanel(int index) {
        JPanel panel = null;
        switch(index) {
            case 0: // My Profile & Stats
                panel = new ManageSalesProfileJPanel(business, salesperson, cardPanel);
                break;
            case 1: // Order History
                panel = new UserInterface.Main.WorkSpaceProfiles.OrderManagement.ManageSalesPersonOrders(business, cardPanel);
                break;
            case 2: // Commission Report
                panel = new SalesCommissionJPanel(business, salesperson, cardPanel);
                break;
            case 3: // Customer List
                panel = new SalesCustomerListJPanel(business, salesperson, cardPanel);
                break;
            case 4: // Top Products Sold
                panel = new TopProductsSoldJPanel(business, salesperson, cardPanel);
                break;
            case 5: // Performance Summary
                panel = new SalesPerformanceSummaryJPanel(business, salesperson, cardPanel);
                break;
        }
        if (panel != null) {
            cardPanel.add(panel);
            cardPanel.revalidate();
            cardPanel.repaint();
            ((CardLayout) cardPanel.getLayout()).next(cardPanel);
        }
    }
}