package UserInterface.MarketingManagement;

import TheBusiness.Business.Business;
import TheBusiness.MarketModel.Channel;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

public class ManageChannelsJPanel extends JPanel {
    
    private Business business;
    private JPanel cardPanel;
    private JTable table;
    private JTextField channelTypeField;
    
    public ManageChannelsJPanel(Business b, JPanel cards) {
        this.business = b;
        this.cardPanel = cards;
        
        setBackground(new Color(0, 153, 153));
        setLayout(null);
        
        JLabel title = new JLabel("Manage Channels");
        title.setFont(new Font("Arial", Font.PLAIN, 24));
        title.setBounds(20, 20, 400, 30);
        add(title);
        
        // Add new channel section
        JPanel addPanel = new JPanel();
        addPanel.setBorder(BorderFactory.createTitledBorder("Add New Channel"));
        addPanel.setLayout(null);
        addPanel.setBounds(20, 70, 860, 80);
        
        JLabel typeLbl = new JLabel("Channel Type:");
        typeLbl.setBounds(20, 30, 100, 25);
        addPanel.add(typeLbl);
        
        channelTypeField = new JTextField();
        channelTypeField.setBounds(120, 30, 200, 25);
        addPanel.add(channelTypeField);
        
        JLabel exampleLbl = new JLabel("(e.g., TV, Web, Mobile, Social Media, Email)");
        exampleLbl.setBounds(330, 30, 300, 25);
        exampleLbl.setFont(new Font("Arial", Font.ITALIC, 11));
        addPanel.add(exampleLbl);
        
        JButton addBtn = new JButton("Add Channel");
        addBtn.setBounds(120, 55, 120, 25);
        addBtn.setBackground(new Color(102, 153, 255));
        addBtn.setForeground(Color.WHITE);
        addBtn.addActionListener(e -> addChannel());
        addPanel.add(addBtn);
        
        add(addPanel);
        
        // Table
        String[] columns = {"Channel Type", "Status"};
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
    
    private void addChannel() {
        String type = channelTypeField.getText().trim();
        
        if (type.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter a channel type");
            return;
        }
        
        // Check if channel already exists
        Channel existing = business.getChannelCatalog().findChannel(type);
        if (existing != null) {
            JOptionPane.showMessageDialog(this, "Channel '" + type + "' already exists!");
            return;
        }
        
        business.getChannelCatalog().newChannel(type);
        JOptionPane.showMessageDialog(this, "Channel '" + type + "' added successfully!");
        
        channelTypeField.setText("");
        loadData();
    }
    
    private void loadData() {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0);
        
        ArrayList<Channel> channels = business.getChannelCatalog().getChannels();
        
        for (Channel c : channels) {
            model.addRow(new Object[]{
                c.getChannelType(),
                "Active"
            });
        }
        
        if (channels.isEmpty()) {
            model.addRow(new Object[]{"No channels yet", "-"});
        }
    }
}