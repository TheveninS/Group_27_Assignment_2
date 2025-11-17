package UserInterface.MarketingManagement;

import TheBusiness.Business.Business;
import TheBusiness.MarketModel.Market;
import TheBusiness.MarketModel.Channel;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

public class ManageMarketsJPanel extends JPanel {
    
    private Business business;
    private JPanel cardPanel;
    private JTable table;
    private JTextField marketNameField;
    
    public ManageMarketsJPanel(Business b, JPanel cards) {
        this.business = b;
        this.cardPanel = cards;
        
        setBackground(new Color(0, 153, 153));
        setLayout(null);
        
        JLabel title = new JLabel("Manage Markets");
        title.setFont(new Font("Arial", Font.PLAIN, 24));
        title.setBounds(20, 20, 400, 30);
        add(title);
        
        // Add new market section
        JPanel addPanel = new JPanel();
        addPanel.setBorder(BorderFactory.createTitledBorder("Add New Market"));
        addPanel.setLayout(null);
        addPanel.setBounds(20, 70, 860, 80);
        
        JLabel nameLbl = new JLabel("Market Name:");
        nameLbl.setBounds(20, 30, 100, 25);
        addPanel.add(nameLbl);
        
        marketNameField = new JTextField();
        marketNameField.setBounds(120, 30, 200, 25);
        addPanel.add(marketNameField);
        
        JButton addBtn = new JButton("Add Market");
        addBtn.setBounds(340, 30, 120, 25);
        addBtn.setBackground(new Color(102, 153, 255));
        addBtn.setForeground(Color.WHITE);
        addBtn.addActionListener(e -> addMarket());
        addPanel.add(addBtn);
        
        add(addPanel);
        
        // Table
        String[] columns = {"Market Name", "Valid Channels", "Market Size"};
        DefaultTableModel model = new DefaultTableModel(columns, 0) {
            public boolean isCellEditable(int row, int col) { return false; }
        };
        table = new JTable(model);
        JScrollPane scroll = new JScrollPane(table);
        scroll.setBounds(20, 160, 860, 280);
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
    
    private void addMarket() {
        String name = marketNameField.getText().trim();
        
        if (name.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter a market name");
            return;
        }
        
        business.getMarketCatalog().newMarket(name);
        JOptionPane.showMessageDialog(this, "Market '" + name + "' added successfully!");
        
        marketNameField.setText("");
        loadData();
    }
    
    private void loadData() {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0);
        
        ArrayList<Market> markets = business.getMarketCatalog().getMarkets();
        
        for (Market m : markets) {
            ArrayList<Channel> channels = m.getValidChannels();
            String channelNames = channels.isEmpty() ? "None" : 
                channels.size() + " channel(s)";
            
            model.addRow(new Object[]{
                m.getName(),
                channelNames,
                "N/A"
            });
        }
        
        if (markets.isEmpty()) {
            model.addRow(new Object[]{"No markets yet", "-", "-"});
        }
    }
}