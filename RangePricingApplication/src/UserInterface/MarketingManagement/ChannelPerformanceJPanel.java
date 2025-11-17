package UserInterface.MarketingManagement;

import MarketAnalytics.ChannelSummary;
import TheBusiness.Business.Business;
import TheBusiness.MarketModel.Channel;
import TheBusiness.SolutionOrders.MasterSolutionOrderList;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

public class ChannelPerformanceJPanel extends JPanel {
    
    private Business business;
    private JPanel cardPanel;
    private JTable table;
    
    public ChannelPerformanceJPanel(Business b, JPanel cards) {
        this.business = b;
        this.cardPanel = cards;
        
        setBackground(new Color(0, 153, 153));
        setLayout(null);
        
        JLabel title = new JLabel("Channel Performance Report");
        title.setFont(new Font("Arial", Font.PLAIN, 24));
        title.setBounds(20, 20, 400, 30);
        add(title);
        
        // Summary panel
        JPanel summaryPanel = new JPanel();
        summaryPanel.setBorder(BorderFactory.createTitledBorder("Summary"));
        summaryPanel.setLayout(null);
        summaryPanel.setBounds(20, 70, 860, 60);
        
        JLabel totalLbl = new JLabel();
        totalLbl.setBounds(20, 25, 800, 25);
        summaryPanel.add(totalLbl);
        
        add(summaryPanel);
        
        // Table
        String[] columns = {"Channel Type", "Revenue", "# Orders"};
        DefaultTableModel model = new DefaultTableModel(columns, 0) {
            public boolean isCellEditable(int row, int col) { return false; }
        };
        table = new JTable(model);
        JScrollPane scroll = new JScrollPane(table);
        scroll.setBounds(20, 140, 860, 300);
        add(scroll);
        
        loadData(totalLbl);
        
        JButton back = new JButton("<< Back");
        back.setBounds(20, 460, 90, 30);
        back.addActionListener(e -> {
            cardPanel.remove(this);
            ((CardLayout) cardPanel.getLayout()).next(cardPanel);
        });
        add(back);
    }
    
    private void loadData(JLabel totalLbl) {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0);
        
        MasterSolutionOrderList msol = business.getMasterSolutionOrderList();
        ArrayList<Channel> channels = business.getChannelCatalog().getChannels();
        
        int totalRevenue = 0;
        int totalOrders = 0;
        
        for (Channel c : channels) {
            ChannelSummary summary = new ChannelSummary(c, msol);
            
            totalRevenue += summary.getRevenues();
            totalOrders += summary.getNumberOfOrders();
            
            model.addRow(new Object[]{
                c.getChannelType(),
                String.format("$%,d", summary.getRevenues()),
                summary.getNumberOfOrders()
            });
        }
        
        if (channels.isEmpty()) {
            model.addRow(new Object[]{"No channels yet", "-", "-"});
            totalLbl.setText("No channel data available");
        } else {
            totalLbl.setText(String.format("Total Channels: %d  |  Total Revenue: $%,d  |  Total Orders: %d",
                channels.size(), totalRevenue, totalOrders));
        }
    }
}