package UserInterface.MarketingManagement;

import TheBusiness.Business.Business;
import TheBusiness.MarketModel.Market;
import TheBusiness.MarketModel.Channel;
import TheBusiness.MarketModel.MarketChannelAssignment;
import TheBusiness.SolutionOrders.MasterSolutionOrderList;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

public class MarketChannelAnalysisJPanel extends JPanel {
    
    private Business business;
    private JPanel cardPanel;
    private JTable table;
    
    public MarketChannelAnalysisJPanel(Business b, JPanel cards) {
        this.business = b;
        this.cardPanel = cards;
        
        setBackground(new Color(0, 153, 153));
        setLayout(null);
        
        JLabel title = new JLabel("Market-Channel Combination Analysis");
        title.setFont(new Font("Arial", Font.PLAIN, 24));
        title.setBounds(20, 20, 500, 30);
        add(title);
        
        JLabel info = new JLabel("Revenue performance by Market-Channel combinations");
        info.setBounds(20, 55, 500, 20);
        add(info);
        
        // Table
        String[] columns = {"Market", "Channel", "Revenue", "# Orders", "Target Met?"};
        DefaultTableModel model = new DefaultTableModel(columns, 0) {
            public boolean isCellEditable(int row, int col) { return false; }
        };
        table = new JTable(model);
        JScrollPane scroll = new JScrollPane(table);
        scroll.setBounds(20, 90, 860, 350);
        add(scroll);
        
        loadData();
        
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
        
        MasterSolutionOrderList msol = business.getMasterSolutionOrderList();
        ArrayList<MarketChannelAssignment> combos = business.getMarketChannelComboCatalog()
            .getMarketChannelAssignments();
        
        for (MarketChannelAssignment mca : combos) {
            int revenue = msol.getRevenueByMarketChannelCombo(mca);
            int orders = msol.getOrderCountByMarketChannelCombo(mca);
            int target = mca.getTargetRevenue();
            String targetMet = target > 0 ? (revenue >= target ? "Yes ✓" : "No") : "N/A";
            
            model.addRow(new Object[]{
                mca.getMarket().getName(),
                mca.getChannel().getChannelType(),
                String.format("$%,d", revenue),
                orders,
                targetMet
            });
        }
        
        if (combos.isEmpty()) {
            model.addRow(new Object[]{"No market-channel combinations yet", "-", "-", "-", "-"});
        }
    }
}