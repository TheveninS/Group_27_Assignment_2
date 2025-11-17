package UserInterface.SalesManagement;

import TheBusiness.Business.Business;
import TheBusiness.OrderManagement.Order;
import TheBusiness.SalesManagement.SalesPersonProfile;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class SalesPerformanceSummaryJPanel extends JPanel {
    
    private Business business;
    private JPanel cardPanel;
    private SalesPersonProfile salesperson;
    
    public SalesPerformanceSummaryJPanel(Business b, SalesPersonProfile spp, JPanel cards) {
        this.business = b;
        this.salesperson = spp;
        this.cardPanel = cards;
        
        setBackground(new Color(0, 153, 153));
        setLayout(null);
        
        JLabel title = new JLabel("Performance Summary");
        title.setFont(new Font("Arial", Font.PLAIN, 24));
        title.setBounds(20, 20, 400, 30);
        add(title);
        
        JTextArea summaryArea = new JTextArea();
        summaryArea.setEditable(false);
        summaryArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        JScrollPane scroll = new JScrollPane(summaryArea);
        scroll.setBounds(20, 70, 860, 380);
        add(scroll);
        
        loadSummary(summaryArea);
        
        JButton back = new JButton("<< Back");
        back.setBounds(20, 470, 90, 30);
        back.addActionListener(e -> {
            cardPanel.remove(this);
            ((CardLayout) cardPanel.getLayout()).next(cardPanel);
        });
        add(back);
    }
    
    private void loadSummary(JTextArea summaryArea) {
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
        double commission = totalRevenue * 0.05; // 5% commission
        
        StringBuilder sb = new StringBuilder();
        sb.append("═══════════════════════════════════════════════════════════════════\n");
        sb.append("              SALES PERFORMANCE SUMMARY REPORT\n");
        sb.append("═══════════════════════════════════════════════════════════════════\n\n");
        
        sb.append("Sales Person: ").append(salesperson.getPerson().getPersonId()).append("\n\n");
        
        sb.append("ORDER STATISTICS:\n");
        sb.append("─────────────────────────────────────────────────────────────────\n");
        sb.append(String.format("  Total Orders Processed:     %d\n", totalOrders));
        sb.append(String.format("  Orders Above Target:        %d (%.1f%%)\n", 
            ordersAboveTarget, totalOrders > 0 ? (ordersAboveTarget * 100.0 / totalOrders) : 0));
        sb.append(String.format("  Orders Below Target:        %d (%.1f%%)\n\n", 
            ordersBelowTarget, totalOrders > 0 ? (ordersBelowTarget * 100.0 / totalOrders) : 0));
        
        sb.append("REVENUE STATISTICS:\n");
        sb.append("─────────────────────────────────────────────────────────────────\n");
        sb.append(String.format("  Total Revenue Generated:    $%,d\n", totalRevenue));
        sb.append(String.format("  Average Order Value:        $%,d\n", avgOrderValue));
        sb.append(String.format("  Total Commission Earned:    $%,.2f\n\n", commission));
        
        sb.append("PERFORMANCE RATING:\n");
        sb.append("─────────────────────────────────────────────────────────────────\n");
        double successRate = totalOrders > 0 ? (ordersAboveTarget * 100.0 / totalOrders) : 0;
        String rating = successRate >= 70 ? "EXCELLENT" : 
                       successRate >= 50 ? "GOOD" : 
                       successRate >= 30 ? "AVERAGE" : "NEEDS IMPROVEMENT";
        sb.append(String.format("  Success Rate:               %.1f%%\n", successRate));
        sb.append(String.format("  Performance Rating:         %s\n\n", rating));
        
        sb.append("═══════════════════════════════════════════════════════════════════\n");
        
        summaryArea.setText(sb.toString());
    }
}