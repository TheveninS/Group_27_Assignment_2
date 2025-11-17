package UserInterface.MarketingManagement;

import TheBusiness.Business.Business;
import TheBusiness.MarketModel.SolutionOffer;
import TheBusiness.MarketModel.MarketChannelAssignment;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

public class SolutionOfferAnalysisJPanel extends JPanel {
    
    private Business business;
    private JPanel cardPanel;
    private JTable table;
    private JTextArea summaryArea;
    
    public SolutionOfferAnalysisJPanel(Business b, JPanel cards) {
        this.business = b;
        this.cardPanel = cards;
        
        setBackground(new Color(0, 153, 153));
        setLayout(null);
        
        JLabel title = new JLabel("Solution Offer Analysis");
        title.setFont(new Font("Arial", Font.PLAIN, 24));
        title.setBounds(20, 20, 400, 30);
        add(title);
        
        JLabel info = new JLabel("Performance analysis of solution offerings by market-channel");
        info.setBounds(20, 55, 500, 20);
        add(info);
        
        // Summary panel
        JPanel summaryPanel = new JPanel();
        summaryPanel.setBorder(BorderFactory.createTitledBorder("Summary"));
        summaryPanel.setLayout(null);
        summaryPanel.setBounds(20, 85, 860, 80);
        
        summaryArea = new JTextArea();
        summaryArea.setEditable(false);
        summaryArea.setFont(new Font("Monospaced", Font.BOLD, 12));
        JScrollPane summaryScroll = new JScrollPane(summaryArea);
        summaryScroll.setBounds(10, 25, 840, 45);
        summaryPanel.add(summaryScroll);
        
        add(summaryPanel);
        
        // Table
        String[] columns = {"Solution Offer", "Market", "Channel", "Price", "Revenue", "# Orders"};
        DefaultTableModel model = new DefaultTableModel(columns, 0) {
            public boolean isCellEditable(int row, int col) { return false; }
        };
        table = new JTable(model);
        JScrollPane scroll = new JScrollPane(table);
        scroll.setBounds(20, 175, 860, 275);
        add(scroll);
        
        loadData();
        
        JButton refreshBtn = new JButton("Refresh Data");
        refreshBtn.setBounds(740, 460, 120, 30);
        refreshBtn.setBackground(new Color(102, 153, 255));
        refreshBtn.setForeground(Color.WHITE);
        refreshBtn.addActionListener(e -> loadData());
        add(refreshBtn);
        
        JButton back = new JButton("<< Back");
        back.setBounds(20, 460, 90, 30);
        back.addActionListener(e -> {
            cardPanel.remove(this);
            ((CardLayout) cardPanel.getLayout()).next(cardPanel);
        });
        add(back);
    }
    
    private void loadData() {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0);
        
        // Get all market-channel combinations
        ArrayList<MarketChannelAssignment> combos = business.getMarketChannelComboCatalog()
            .getMarketChannelAssignments();
        
        int totalSolutionOffers = 0;
        int totalRevenue = 0;
        int totalOrders = 0;
        int solutionOfferNumber = 1;
        
        for (MarketChannelAssignment mca : combos) {
            // Find solution offers for this market-channel combo
            ArrayList<SolutionOffer> offers = business.getSolutionOfferCatalog()
                .findSolutionsForMarketChannelCombo(mca);
            
            for (SolutionOffer offer : offers) {
                int revenue = offer.getRevenues();
                int orders = offer.solutionorders.size(); // Number of orders for this solution
                int price = offer.getSolutionPrice();
                
                totalRevenue += revenue;
                totalOrders += orders;
                totalSolutionOffers++;
                
                model.addRow(new Object[]{
                    "Solution #" + solutionOfferNumber++,
                    mca.getMarket().getName(),
                    mca.getChannel().getChannelType(),
                    String.format("$%,d", price),
                    String.format("$%,d", revenue),
                    orders
                });
            }
        }
        
        if (totalSolutionOffers == 0) {
            model.addRow(new Object[]{"No solution offers yet", "-", "-", "-", "-", "-"});
            summaryArea.setText("No solution offer data available. Create markets, channels, and solution offers to see data here.");
        } else {
            int avgRevenue = totalSolutionOffers > 0 ? totalRevenue / totalSolutionOffers : 0;
            double avgOrders = totalSolutionOffers > 0 ? (double) totalOrders / totalSolutionOffers : 0;
            
            summaryArea.setText(String.format(
                "Total Solution Offers: %d  |  Total Revenue: $%,d  |  Total Orders: %d\n" +
                "Avg Revenue per Solution: $%,d  |  Avg Orders per Solution: %.1f",
                totalSolutionOffers, totalRevenue, totalOrders, avgRevenue, avgOrders
            ));
        }
    }
}