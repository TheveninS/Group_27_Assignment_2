package UserInterface.PriceSimulation;

import TheBusiness.Business.Business;
import TheBusiness.ProductManagement.*;
import javax.swing.*;
import java.awt.*;
import java.util.*;

public class MaximizeProfitJPanel extends JPanel {
    
    private Business business;
    private JPanel cardPanel;
    private JTextArea logArea;
    private JProgressBar progressBar;
    private JButton optimizeBtn;
    
    public MaximizeProfitJPanel(Business b, JPanel cards) {
        this.business = b;
        this.cardPanel = cards;
        
        setBackground(new Color(0, 153, 153));
        setLayout(null);
        
        JLabel title = new JLabel("Maximize Profit Margins");
        title.setFont(new Font("Arial", Font.PLAIN, 24));
        title.setBounds(20, 20, 400, 30);
        add(title);
        
        JLabel info = new JLabel("Iteratively optimize target prices to maximize company profit margin");
        info.setBounds(20, 55, 600, 20);
        add(info);
        
        optimizeBtn = new JButton("Start Optimization");
        optimizeBtn.setBounds(20, 80, 180, 30);
        optimizeBtn.setBackground(new Color(102, 153, 255));
        optimizeBtn.setForeground(Color.WHITE);
        optimizeBtn.addActionListener(e -> startOptimization());
        add(optimizeBtn);
        
        progressBar = new JProgressBar(0, 100);
        progressBar.setBounds(220, 80, 660, 30);
        progressBar.setStringPainted(true);
        add(progressBar);
        
        logArea = new JTextArea();
        logArea.setEditable(false);
        logArea.setFont(new Font("Monospaced", Font.PLAIN, 11));
        JScrollPane scroll = new JScrollPane(logArea);
        scroll.setBounds(20, 120, 860, 340);
        add(scroll);
        
        JButton back = new JButton("<< Back");
        back.setBounds(20, 470, 90, 30);
        back.addActionListener(e -> {
            cardPanel.remove(this);
            ((CardLayout) cardPanel.getLayout()).next(cardPanel);
        });
        add(back);
    }
    
    private void startOptimization() {
        optimizeBtn.setEnabled(false);
        progressBar.setValue(0);
        logArea.setText("");
        
        SwingWorker<Void, String> worker = new SwingWorker<Void, String>() {
            @Override
            protected Void doInBackground() throws Exception {
                PricingAnalyzer analyzer = new PricingAnalyzer(business);
                RecommendationEngine recEngine = new RecommendationEngine();
                SimulationEngine simEngine = new SimulationEngine();
                
                int initialMargin = analyzer.getTotalProfitMargin();
                publish("Starting optimization...");
                publish("Initial profit margin: $" + String.format("%,d", initialMargin));
                publish("───────────────────────────────────────────────────────\n");
                
                int iteration = 0;
                int maxIterations = 5;
                int bestMargin = initialMargin;
                
                while (iteration < maxIterations) {
                    iteration++;
                    publish("\nIteration " + iteration + ":");
                    progressBar.setValue((iteration * 100) / maxIterations);
                    
                    Map<Product, ProductPerformance> analysis = analyzer.analyzeAllProducts();
                    ArrayList<PriceAdjustment> recs = recEngine.getAllRecommendations(analysis);
                    
                    if (recs.isEmpty()) {
                        publish("  No more adjustments needed. Optimization complete!");
                        break;
                    }
                    
                    publish("  Found " + recs.size() + " products to adjust");
                    
                    // Apply adjustments
                    for (PriceAdjustment adj : recs) {
                        adj.getProduct().updateTargetPrice(adj.getSuggestedTarget());
                    }
                    
                    int newMargin = analyzer.getTotalProfitMargin();
                    int improvement = newMargin - bestMargin;
                    
                    publish(String.format("  New margin: $%,d (change: %+,d)", newMargin, improvement));
                    
                    if (improvement <= 0) {
                        publish("  No improvement. Stopping optimization.");
                        break;
                    }
                    
                    bestMargin = newMargin;
                    Thread.sleep(500);
                }
                
                progressBar.setValue(100);
                int totalImprovement = bestMargin - initialMargin;
                double percentChange = (totalImprovement * 100.0) / Math.abs(initialMargin);
                
                publish("\n═══════════════════════════════════════════════════════");
                publish("OPTIMIZATION COMPLETE");
                publish("═══════════════════════════════════════════════════════");
                publish(String.format("Initial Margin:  $%,d", initialMargin));
                publish(String.format("Final Margin:    $%,d", bestMargin));
                publish(String.format("Improvement:     $%,d (%+.2f%%)", totalImprovement, percentChange));
                publish(String.format("Iterations:      %d", iteration));
                
                return null;
            }
            
            @Override
            protected void process(java.util.List<String> chunks) {
                for (String msg : chunks) {
                    logArea.append(msg + "\n");
                }
                logArea.setCaretPosition(logArea.getDocument().getLength());
            }
            
            @Override
            protected void done() {
                optimizeBtn.setEnabled(true);
            }
    };
        
        worker.execute();
    }
}