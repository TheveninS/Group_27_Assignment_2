package UserInterface.ManageTheBusiness;

import TheBusiness.Business.Business;
import TheBusiness.ProductManagement.*;
import TheBusiness.CustomerManagement.CustomersReport;
import TheBusiness.OrderManagement.MasterOrderList;
import TheBusiness.Supplier.Supplier;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class ManageTheBusinessJPanel extends javax.swing.JPanel {

    JPanel CardSequencePanel;
    Business business;

    public ManageTheBusinessJPanel(Business bz, JPanel jp) {
        CardSequencePanel = jp;
        this.business = bz;
        
        setBackground(new Color(0, 153, 153));
        setLayout(null);
        
        JLabel title = new JLabel("Business Performance Overview");
        title.setFont(new Font("Arial", Font.BOLD, 24));
        title.setBounds(20, 20, 500, 30);
        add(title);
        
        // Key Metrics Panel
        JPanel metricsPanel = new JPanel();
        metricsPanel.setBorder(BorderFactory.createTitledBorder("Key Business Metrics"));
        metricsPanel.setLayout(null);
        metricsPanel.setBounds(20, 70, 860, 200);
        metricsPanel.setBackground(Color.WHITE);
        
        // Calculate metrics
        PricingAnalyzer analyzer = new PricingAnalyzer(business);
        int totalRevenue = analyzer.getTotalCompanyRevenue();
        int totalMargin = analyzer.getTotalProfitMargin();
        
        CustomersReport custReport = business.getCustomerDirectory().generatCustomerPerformanceReport();
        int totalCustomers = custReport.getCustomerList().size();
        int totalOrders = custReport.getTotalOrders();
        
        int totalProducts = 0;
        int totalSuppliers = business.getSupplierDirectory().getSuplierList().size();
        ArrayList<Supplier> suppliers = business.getSupplierDirectory().getSuplierList();
        for (Supplier supplier : suppliers) {
            totalProducts += supplier.getProductCatalog().getProductList().size();
        }
        
        int productsWithSales = analyzer.getProductsWithSales().size();
        int avgOrderValue = totalOrders > 0 ? totalRevenue / totalOrders : 0;
        
        // Revenue
        JLabel revLbl = new JLabel("Total Revenue:");
        revLbl.setFont(new Font("Arial", Font.BOLD, 14));
        revLbl.setBounds(30, 30, 150, 25);
        metricsPanel.add(revLbl);
        
        JLabel revValue = new JLabel(String.format("$%,d", totalRevenue));
        revValue.setFont(new Font("Arial", Font.PLAIN, 18));
        revValue.setForeground(new Color(0, 128, 0));
        revValue.setBounds(180, 30, 200, 25);
        metricsPanel.add(revValue);
        
        // Profit Margin
        JLabel marginLbl = new JLabel("Profit Margin:");
        marginLbl.setFont(new Font("Arial", Font.BOLD, 14));
        marginLbl.setBounds(30, 65, 150, 25);
        metricsPanel.add(marginLbl);
        
        JLabel marginValue = new JLabel(String.format("$%,d", totalMargin));
        marginValue.setFont(new Font("Arial", Font.PLAIN, 18));
        marginValue.setForeground(totalMargin >= 0 ? new Color(0, 128, 0) : Color.RED);
        marginValue.setBounds(180, 65, 200, 25);
        metricsPanel.add(marginValue);
        
        // Customers
        JLabel custLbl = new JLabel("Total Customers:");
        custLbl.setFont(new Font("Arial", Font.BOLD, 14));
        custLbl.setBounds(30, 100, 150, 25);
        metricsPanel.add(custLbl);
        
        JLabel custValue = new JLabel(String.valueOf(totalCustomers));
        custValue.setFont(new Font("Arial", Font.PLAIN, 18));
        custValue.setBounds(180, 100, 200, 25);
        metricsPanel.add(custValue);
        
        // Orders
        JLabel orderLbl = new JLabel("Total Orders:");
        orderLbl.setFont(new Font("Arial", Font.BOLD, 14));
        orderLbl.setBounds(30, 135, 150, 25);
        metricsPanel.add(orderLbl);
        
        JLabel orderValue = new JLabel(String.valueOf(totalOrders));
        orderValue.setFont(new Font("Arial", Font.PLAIN, 18));
        orderValue.setBounds(180, 135, 200, 25);
        metricsPanel.add(orderValue);
        
        // Avg Order Value
        JLabel avgLbl = new JLabel("Avg Order Value:");
        avgLbl.setFont(new Font("Arial", Font.BOLD, 14));
        avgLbl.setBounds(30, 170, 150, 25);
        metricsPanel.add(avgLbl);
        
        JLabel avgValue = new JLabel(String.format("$%,d", avgOrderValue));
        avgValue.setFont(new Font("Arial", Font.PLAIN, 18));
        avgValue.setBounds(180, 170, 200, 25);
        metricsPanel.add(avgValue);
        
        // Right column
        // Suppliers
        JLabel suppLbl = new JLabel("Suppliers:");
        suppLbl.setFont(new Font("Arial", Font.BOLD, 14));
        suppLbl.setBounds(450, 30, 150, 25);
        metricsPanel.add(suppLbl);
        
        JLabel suppValue = new JLabel(String.valueOf(totalSuppliers));
        suppValue.setFont(new Font("Arial", Font.PLAIN, 18));
        suppValue.setBounds(600, 30, 200, 25);
        metricsPanel.add(suppValue);
        
        // Products
        JLabel prodLbl = new JLabel("Total Products:");
        prodLbl.setFont(new Font("Arial", Font.BOLD, 14));
        prodLbl.setBounds(450, 65, 150, 25);
        metricsPanel.add(prodLbl);
        
        JLabel prodValue = new JLabel(String.valueOf(totalProducts));
        prodValue.setFont(new Font("Arial", Font.PLAIN, 18));
        prodValue.setBounds(600, 65, 200, 25);
        metricsPanel.add(prodValue);
        
        // Products with Sales
        JLabel prodSalesLbl = new JLabel("Products Selling:");
        prodSalesLbl.setFont(new Font("Arial", Font.BOLD, 14));
        prodSalesLbl.setBounds(450, 100, 150, 25);
        metricsPanel.add(prodSalesLbl);
        
        JLabel prodSalesValue = new JLabel(String.valueOf(productsWithSales));
        prodSalesValue.setFont(new Font("Arial", Font.PLAIN, 18));
        prodSalesValue.setBounds(600, 100, 200, 25);
        metricsPanel.add(prodSalesValue);
        
        // Sales Coverage
        JLabel coverageLbl = new JLabel("Sales Coverage:");
        coverageLbl.setFont(new Font("Arial", Font.BOLD, 14));
        coverageLbl.setBounds(450, 135, 150, 25);
        metricsPanel.add(coverageLbl);
        
        double coverage = totalProducts > 0 ? (productsWithSales * 100.0 / totalProducts) : 0;
        JLabel coverageValue = new JLabel(String.format("%.1f%%", coverage));
        coverageValue.setFont(new Font("Arial", Font.PLAIN, 18));
        coverageValue.setBounds(600, 135, 200, 25);
        metricsPanel.add(coverageValue);
        
        add(metricsPanel);
        
        // Performance Summary Panel
        JPanel summaryPanel = new JPanel();
        summaryPanel.setBorder(BorderFactory.createTitledBorder("Performance Summary"));
        summaryPanel.setLayout(null);
        summaryPanel.setBounds(20, 285, 860, 150);
        summaryPanel.setBackground(Color.WHITE);
        
        JTextArea summaryArea = new JTextArea();
        summaryArea.setEditable(false);
        summaryArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        
        StringBuilder sb = new StringBuilder();
        sb.append("BUSINESS PERFORMANCE SUMMARY\n");
        sb.append("═══════════════════════════════════════════════════════════\n\n");
        sb.append(String.format("Total Revenue: $%,d\n", totalRevenue));
        sb.append(String.format("Profit Margin: $%,d (%.1f%% of revenue)\n", 
            totalMargin, totalRevenue > 0 ? (totalMargin * 100.0 / totalRevenue) : 0));
sb.append(String.format("\nCustomer Base: %d customers generating %d orders\n", 
    totalCustomers, totalOrders));
sb.append(String.format("Average Order Value: $%,d\n", avgOrderValue));
sb.append(String.format("\nProduct Portfolio: %d products across %d suppliers\n", 
    totalProducts, totalSuppliers));
sb.append(String.format("Active Products: %d (%.1f%% of portfolio)\n", 
    productsWithSales, coverage));
        
        summaryArea.setText(sb.toString());
        
        JScrollPane summaryScroll = new JScrollPane(summaryArea);
        summaryScroll.setBounds(10, 25, 840, 115);
        summaryPanel.add(summaryScroll);
        
        add(summaryPanel);
        
        // Action buttons
        JButton detailsBtn = new JButton("View Detailed Analytics");
        detailsBtn.setBounds(300, 450, 180, 35);
        detailsBtn.setBackground(new Color(102, 153, 255));
        detailsBtn.setForeground(Color.WHITE);
        detailsBtn.addActionListener(e -> {
            ManageBusinessDetail1 detail = new ManageBusinessDetail1(business, CardSequencePanel);
            CardSequencePanel.add(detail);
            ((java.awt.CardLayout) CardSequencePanel.getLayout()).next(CardSequencePanel);
        });
        add(detailsBtn);
        
        JButton refreshBtn = new JButton("Refresh");
        refreshBtn.setBounds(490, 450, 100, 35);
        refreshBtn.setBackground(new Color(102, 153, 255));
        refreshBtn.setForeground(Color.WHITE);
        refreshBtn.addActionListener(e -> {
            CardSequencePanel.remove(this);
            ManageTheBusinessJPanel newPanel = new ManageTheBusinessJPanel(business, CardSequencePanel);
            CardSequencePanel.add(newPanel);
            ((java.awt.CardLayout) CardSequencePanel.getLayout()).next(CardSequencePanel);
        });
        add(refreshBtn);
        
        JButton backBtn = new JButton("<< Back");
        backBtn.setBounds(20, 450, 90, 35);
        backBtn.addActionListener(e -> {
            CardSequencePanel.remove(this);
            ((java.awt.CardLayout) CardSequencePanel.getLayout()).next(CardSequencePanel);
        });
        add(backBtn);
    }
}