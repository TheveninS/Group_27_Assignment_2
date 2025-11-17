package UserInterface.PriceSimulation;

import TheBusiness.Business.Business;
import TheBusiness.ProductManagement.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.*;

public class GenerateFinalReportJPanel extends JPanel {
    
    private Business business;
    private JPanel cardPanel;
    private JTable table;
    private JTextArea summaryArea;
    
    public GenerateFinalReportJPanel(Business b, JPanel cards) {
        this.business = b;
        this.cardPanel = cards;
        
        setBackground(new Color(0, 153, 153));
        setLayout(null);
        
        JLabel title = new JLabel("Final Product Performance Report");
        title.setFont(new Font("Arial", Font.PLAIN, 24));
        title.setBounds(20, 20, 450, 30);
        add(title);
        
        // Summary panel
        summaryArea = new JTextArea();
        summaryArea.setEditable(false);
        summaryArea.setFont(new Font("Monospaced", Font.BOLD, 12));
        summaryArea.setBackground(new Color(102, 153, 255));
        summaryArea.setForeground(Color.WHITE);
        JScrollPane summaryScroll = new JScrollPane(summaryArea);
        summaryScroll.setBounds(20, 60, 860, 80);
        add(summaryScroll);
        
        // Table
        String[] columns = {"Product", "Target Price", "Revenue", "Below Target", "Above Target", "Margin"};
        DefaultTableModel model = new DefaultTableModel(columns, 0) {
            public boolean isCellEditable(int row, int col) { return false; }
        };
        table = new JTable(model);
        JScrollPane scroll = new JScrollPane(table);
        scroll.setBounds(20, 150, 860, 280);
        add(scroll);
        
        JButton generateBtn = new JButton("Generate Report");
        generateBtn.setBounds(20, 440, 150, 30);
        generateBtn.setBackground(new Color(102, 153, 255));
        generateBtn.setForeground(Color.WHITE);
        generateBtn.addActionListener(e -> generateReport());
        add(generateBtn);
        
        JButton exportBtn = new JButton("Export to CSV");
        exportBtn.setBounds(180, 440, 150, 30);
        exportBtn.addActionListener(e -> exportToCSV());
        add(exportBtn);
        
        JButton back = new JButton("<< Back");
        back.setBounds(20, 470, 90, 30);
        back.addActionListener(e -> {
            cardPanel.remove(this);
            ((CardLayout) cardPanel.getLayout()).next(cardPanel);
        });
        add(back);
        
        generateReport();
    }
    
    private void generateReport() {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0);
        
        PricingAnalyzer analyzer = new PricingAnalyzer(business);
        ArrayList<ProductPerformance> products = analyzer.getProductsWithSales();
        
        int totalRevenue = 0;
        int totalMargin = 0;
        int productsAboveTarget = 0;
        int productsBelowTarget = 0;
        
        for (ProductPerformance pp : products) {
            totalRevenue += pp.getTotalRevenue();
            totalMargin += pp.getPricePerformance();
            
            if (pp.isConsistentlyAboveTarget()) productsAboveTarget++;
            if (pp.isConsistentlyBelowTarget()) productsBelowTarget++;
            
            model.addRow(new Object[]{
                pp.getProduct().toString(),
                String.format("$%,d", pp.getProduct().getTargetPrice()),
                String.format("$%,d", pp.getTotalRevenue()),
                pp.getFrequencyBelowTarget(),
                pp.getFrequencyAboveTarget(),
                String.format("$%,d", pp.getPricePerformance())
            });
        }
        
        // Update summary
        StringBuilder sb = new StringBuilder();
        sb.append("  COMPANY PERFORMANCE SUMMARY\n");
        sb.append(String.format("  Total Revenue: $%,d  |  ", totalRevenue));
        sb.append(String.format("Profit Margin: $%,d  |  ", totalMargin));
        sb.append(String.format("Products with Sales: %d\n", products.size()));
        sb.append(String.format("  Above Target: %d  |  Below Target: %d", 
            productsAboveTarget, productsBelowTarget));
        
        summaryArea.setText(sb.toString());
    }
    
    private void exportToCSV() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Save Report as CSV");
        fileChooser.setSelectedFile(new java.io.File("product_performance_report.csv"));
        
        int userSelection = fileChooser.showSaveDialog(this);
        
        if (userSelection == JFileChooser.APPROVE_OPTION) {
            java.io.File fileToSave = fileChooser.getSelectedFile();
            try (java.io.PrintWriter writer = new java.io.PrintWriter(fileToSave)) {
                
                // Write header
                DefaultTableModel model = (DefaultTableModel) table.getModel();
                for (int i = 0; i < model.getColumnCount(); i++) {
                    writer.print(model.getColumnName(i));
                    if (i < model.getColumnCount() - 1) writer.print(",");
                }
                writer.println();
                
                // Write data
                for (int row = 0; row < model.getRowCount(); row++) {
                    for (int col = 0; col < model.getColumnCount(); col++) {
                        writer.print(model.getValueAt(row, col).toString().replace(",", ""));
                        if (col < model.getColumnCount() - 1) writer.print(",");
                    }
                    writer.println();
                }
                
                JOptionPane.showMessageDialog(this, "Report exported successfully!");
                
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error exporting: " + ex.getMessage());
            }
        }
    }
}