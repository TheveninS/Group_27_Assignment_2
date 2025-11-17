package UserInterface.MarketingManagement;

import TheBusiness.Business.Business;
import TheBusiness.MarketModel.Channel;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

public class RevenueByChannelJPanel extends JPanel {
    
    private Business business;
    private JPanel cardPanel;
    private JTable table;
    private JTextArea summaryArea;
    
    public RevenueByChannelJPanel(Business b, JPanel cards) {
        this.business = b;
        this.cardPanel = cards;
        
        setBackground(new Color(0, 153, 153));
        setLayout(null);
        
        JLabel title = new JLabel("Revenue Breakdown by Channel");
        title.setFont(new Font("Arial", Font.PLAIN, 24));
        title.setBounds(20, 20, 400, 30);
        add(title);
        
        // Summary panel
        JPanel summaryPanel = new JPanel();
        summaryPanel.setBorder(BorderFactory.createTitledBorder("Summary"));
        summaryPanel.setLayout(null);
        summaryPanel.setBounds(20, 70, 860, 80);
        
        summaryArea = new JTextArea();
        summaryArea.setEditable(false);
        summaryArea.setFont(new Font("Monospaced", Font.BOLD, 12));
        JScrollPane summaryScroll = new JScrollPane(summaryArea);
        summaryScroll.setBounds(10, 25, 840, 45);
        summaryPanel.add(summaryScroll);
        
        add(summaryPanel);
        
        // Table
        String[] columns = {"Channel", "Revenue", "Orders", "% of Total Revenue"};
        DefaultTableModel model = new DefaultTableModel(columns, 0) {
            public boolean isCellEditable(int row, int col) { return false; }
        };
        table = new JTable(model);
        JScrollPane scroll = new JScrollPane(table);
        scroll.setBounds(20, 160, 860, 290);
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
        
        ArrayList<Channel> channels = business.getChannelCatalog().getChannels();
        int totalRevenue = 0;
        int totalOrders = 0;
        
        // First pass - calculate totals
        for (Channel c : channels) {
            int revenue = business.getMasterSolutionOrderList().getRevenueByChannel(c);
            int orders = business.getMasterSolutionOrderList().getOrderCountByChannel(c);
            totalRevenue += revenue;
            totalOrders += orders;
        }
        
        // Second pass - populate table with percentages
        for (Channel c : channels) {
            int revenue = business.getMasterSolutionOrderList().getRevenueByChannel(c);
            int orders = business.getMasterSolutionOrderList().getOrderCountByChannel(c);
            double percentage = totalRevenue > 0 ? (revenue * 100.0 / totalRevenue) : 0;
            
            model.addRow(new Object[]{
                c.getChannelType(),
                String.format("$%,d", revenue),
                orders,
                String.format("%.1f%%", percentage)
            });
        }
        
        if (channels.isEmpty()) {
            model.addRow(new Object[]{"No channels yet", "-", "-", "-"});
            summaryArea.setText("No channel data available");
        } else {
            summaryArea.setText(String.format(
                "Total Channels: %d  |  Total Revenue: $%,d  |  Total Orders: %d  |  Avg Revenue per Channel: $%,d",
                channels.size(), totalRevenue, totalOrders, channels.size() > 0 ? totalRevenue / channels.size() : 0
            ));
        }
    }
}