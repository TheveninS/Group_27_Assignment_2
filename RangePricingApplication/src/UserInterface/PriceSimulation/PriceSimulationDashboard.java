package UserInterface.PriceSimulation;

import TheBusiness.Business.Business;
import javax.swing.*;
import java.awt.*;

public class PriceSimulationDashboard extends JPanel {
    
    private Business business;
    private JPanel cardPanel;
    
    public PriceSimulationDashboard(Business b, JPanel cards) {
        this.business = b;
        this.cardPanel = cards;
        
        setBackground(new Color(0, 153, 153));
        setLayout(null);
        
        JLabel title = new JLabel("Pricing Simulation Platform");
        title.setFont(new Font("Arial", Font.BOLD, 28));
        title.setBounds(20, 20, 500, 35);
        add(title);
        
        JLabel subtitle = new JLabel("Optimize target prices to maximize company profitability");
        subtitle.setBounds(20, 60, 500, 20);
        add(subtitle);
        
        // Menu buttons
        String[] labels = {
            "1. Browse Product Price Performance",
            "2. Adjust Target Prices Lower", 
            "3. Adjust Target Prices Higher",
            "4. Run Simulation",
            "5. Maximize Profit Margins",
            "6. Generate Final Report"
        };
        
        for (int i = 0; i < 6; i++) {
            JButton btn = new JButton(labels[i]);
            btn.setBackground(new Color(102, 153, 255));
            btn.setForeground(Color.WHITE);
            btn.setFont(new Font("Arial", Font.PLAIN, 14));
            btn.setBounds(100, 120 + (i * 50), 320, 40);
            
            final int index = i;
            btn.addActionListener(e -> openPanel(index));
            add(btn);
        }
        
        JButton back = new JButton("<< Back");
        back.setBounds(20, 470, 90, 30);
        back.addActionListener(e -> {
            cardPanel.remove(this);
            ((CardLayout) cardPanel.getLayout()).next(cardPanel);
        });
        add(back);
    }
    
    private void openPanel(int index) {
        JPanel panel = null;
        switch(index) {
            case 0: panel = new BrowseProductPerformanceJPanel(business, cardPanel); break;
            case 1: panel = new AdjustTargetLowerJPanel(business, cardPanel); break;
            case 2: panel = new AdjustTargetHigherJPanel(business, cardPanel); break;
            case 3: panel = new RunSimulationJPanel(business, cardPanel); break;
            case 4: panel = new MaximizeProfitJPanel(business, cardPanel); break;
            case 5: panel = new GenerateFinalReportJPanel(business, cardPanel); break;
        }
        if (panel != null) {
            cardPanel.add(panel);
            ((CardLayout) cardPanel.getLayout()).next(cardPanel);
        }
    }
}