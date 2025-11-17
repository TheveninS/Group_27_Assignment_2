package UserInterface.MarketingManagement;

import MarketAnalytics.MarketSummary;
import TheBusiness.Business.Business;
import TheBusiness.MarketModel.Market;
import TheBusiness.SolutionOrders.MasterSolutionOrderList;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

public class MarketPerformanceJPanel extends JPanel {
    
    private Business business;
    private JPanel cardPanel;
    private JTable table;
    
    public MarketPerformanceJPanel(Business b, JPanel cards) {
        this.business = b;
        this.cardPanel = cards;
        
        setBackground(new Color(0, 153, 153));
        setLayout(null);
        
        JLabel title = new JLabel("Market Performance Report");
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
        String[] columns = {"Market Name", "Revenue", "# Orders"};
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
        ArrayList<Market> markets = business.getMarketCatalog().getMarkets();
        
        int totalRevenue = 0;
        int totalOrders = 0;
        
        for (Market m : markets) {
            MarketSummary summary = new MarketSummary(m, msol);
            
            totalRevenue += summary.getRevenue();
            totalOrders += summary.getNumberOfOrders();
            
            model.addRow(new Object[]{
                m.getName(),
                String.format("$%,d", summary.getRevenue()),
                summary.getNumberOfOrders()
            });
        }
        
        if (markets.isEmpty()) {
            model.addRow(new Object[]{"No markets yet", "-", "-"});
            totalLbl.setText("No market data available");
        } else {
            totalLbl.setText(String.format("Total Markets: %d  |  Total Revenue: $%,d  |  Total Orders: %d",
                markets.size(), totalRevenue, totalOrders));
        }
    }
}