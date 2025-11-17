package UserInterface.CustomerManagement;

import TheBusiness.Business.Business;
import TheBusiness.CustomerManagement.CustomerProfile;
import TheBusiness.CustomerManagement.CustomerSummary;
import TheBusiness.CustomerManagement.CustomersReport;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

public class ManageCustomersJPanel extends JPanel {
    
    private Business business;
    private JPanel cardPanel;
    private JTable table;
    private JComboBox<String> sortCombo;
    
    public ManageCustomersJPanel(Business b, JPanel cards) {
        this.business = b;
        this.cardPanel = cards;
        
        setBackground(new Color(0, 153, 153));
        setLayout(null);
        
        JLabel title = new JLabel("Manage Customers");
        title.setFont(new Font("Arial", Font.PLAIN, 24));
        title.setBounds(20, 20, 400, 30);
        add(title);
        
        JLabel sortLbl = new JLabel("Sort by:");
        sortLbl.setBounds(20, 60, 60, 25);
        add(sortLbl);
        
        sortCombo = new JComboBox<>(new String[]{"Name", "Revenue (High to Low)", "Orders (High to Low)", "Above Target"});
        sortCombo.setBounds(80, 60, 200, 25);
        sortCombo.addActionListener(e -> loadData());
        add(sortCombo);
        
        JButton refreshBtn = new JButton("Refresh");
        refreshBtn.setBounds(290, 60, 90, 25);
        refreshBtn.addActionListener(e -> loadData());
        add(refreshBtn);
        
        // Table
        String[] columns = {"Customer Name", "Total Revenue", "# Orders", "Orders Above Target", "Orders Below Target"};
        DefaultTableModel model = new DefaultTableModel(columns, 0) {
            public boolean isCellEditable(int row, int col) { return false; }
        };
        table = new JTable(model);
        JScrollPane scroll = new JScrollPane(table);
        scroll.setBounds(20, 100, 860, 350);
        add(scroll);
        
        JLabel totalLbl = new JLabel();
        totalLbl.setBounds(20, 460, 500, 25);
        add(totalLbl);
        
        loadData();
        
        // Update total label
        CustomersReport report = business.getCustomerDirectory().generatCustomerPerformanceReport();
        totalLbl.setText(String.format("Total Customers: %d | Total Revenue: $%,d | Total Orders: %d",
            report.getCustomerList().size(),
            report.getTotalRevenue(),
            report.getTotalOrders()));
        
        JButton back = new JButton("<< Back");
        back.setBounds(20, 495, 90, 30);
        back.addActionListener(e -> {
            cardPanel.remove(this);
            ((CardLayout) cardPanel.getLayout()).next(cardPanel);
        });
        add(back);
    }
    
    private void loadData() {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0);
        
        CustomersReport report = business.getCustomerDirectory().generatCustomerPerformanceReport();
        ArrayList<CustomerSummary> customers = report.getCustomerList();
        
        String sortBy = (String) sortCombo.getSelectedItem();
        
        // Sort based on selection
        if (sortBy.equals("Revenue (High to Low)")) {
            customers = report.getTopNCustomersByRevenue(customers.size());
        } else if (sortBy.equals("Above Target")) {
            customers = report.getBestPerformingCustomers(customers.size());
        } else if (sortBy.equals("Orders (High to Low)")) {
            customers.sort((a, b) -> Integer.compare(b.getNumberOfOrders(), a.getNumberOfOrders()));
        }
        
        for (CustomerSummary cs : customers) {
            model.addRow(new Object[]{
                cs.getCustomerName(),
                String.format("$%,d", cs.getSalesRevenue()),
                cs.getNumberOfOrders(),
                cs.getNumberOfOrdersAboveTarget(),
                cs.getNumberOfOrdersBelowTarget()
            });
        }
    }
}