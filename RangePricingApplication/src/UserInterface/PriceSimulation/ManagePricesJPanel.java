package UserInterface.PriceSimulation;

import TheBusiness.Business.Business;
import TheBusiness.ProductManagement.Product;
import TheBusiness.Supplier.Supplier;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

public class ManagePricesJPanel extends JPanel {
    
    private Business business;
    private JPanel cardPanel;
    private JTable table;
    private JComboBox<String> supplierCombo;
    private JTextField floorField, ceilingField, targetField;
    private Product selectedProduct;
    
    public ManagePricesJPanel(Business b, JPanel cards) {
        this.business = b;
        this.cardPanel = cards;
        
        setBackground(new Color(0, 153, 153));
        setLayout(null);
        
        JLabel title = new JLabel("Manage Product Prices");
        title.setFont(new Font("Arial", Font.PLAIN, 24));
        title.setBounds(20, 20, 400, 30);
        add(title);
        
        JLabel supplierLbl = new JLabel("Supplier:");
        supplierLbl.setBounds(20, 60, 70, 25);
        add(supplierLbl);
        
        supplierCombo = new JComboBox<>();
        supplierCombo.setBounds(90, 60, 200, 25);
        supplierCombo.addActionListener(e -> loadProducts());
        add(supplierCombo);
        
        // Product table
        String[] columns = {"Product Name", "Floor Price", "Ceiling Price", "Target Price"};
        DefaultTableModel model = new DefaultTableModel(columns, 0) {
            public boolean isCellEditable(int row, int col) { return false; }
        };
        table = new JTable(model);
        table.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                loadSelectedProduct();
            }
        });
        JScrollPane scroll = new JScrollPane(table);
        scroll.setBounds(20, 95, 860, 200);
        add(scroll);
        
        // Edit panel
        JPanel editPanel = new JPanel();
        editPanel.setBorder(BorderFactory.createTitledBorder("Edit Selected Product Prices"));
        editPanel.setLayout(null);
        editPanel.setBounds(20, 310, 860, 120);
        
        JLabel floorLbl = new JLabel("Floor Price:");
        floorLbl.setBounds(20, 30, 100, 25);
        editPanel.add(floorLbl);
        
        floorField = new JTextField();
        floorField.setBounds(120, 30, 150, 25);
        editPanel.add(floorField);
        
        JLabel ceilingLbl = new JLabel("Ceiling Price:");
        ceilingLbl.setBounds(300, 30, 100, 25);
        editPanel.add(ceilingLbl);
        
        ceilingField = new JTextField();
        ceilingField.setBounds(400, 30, 150, 25);
        editPanel.add(ceilingField);
        
        JLabel targetLbl = new JLabel("Target Price:");
        targetLbl.setBounds(580, 30, 100, 25);
        editPanel.add(targetLbl);
        
        targetField = new JTextField();
        targetField.setBounds(680, 30, 150, 25);
        editPanel.add(targetField);
        
        JButton updateBtn = new JButton("Update Prices");
        updateBtn.setBounds(120, 70, 150, 30);
        updateBtn.setBackground(new Color(102, 153, 255));
        updateBtn.setForeground(Color.WHITE);
        updateBtn.addActionListener(e -> updatePrices());
        editPanel.add(updateBtn);
        
        add(editPanel);
        
        loadSuppliers();
        
        JButton back = new JButton("<< Back");
        back.setBounds(20, 445, 90, 30);
        back.addActionListener(e -> {
            cardPanel.remove(this);
            ((CardLayout) cardPanel.getLayout()).next(cardPanel);
        });
        add(back);
    }
    
    private void loadSuppliers() {
        supplierCombo.removeAllItems();
        ArrayList<Supplier> suppliers = business.getSupplierDirectory().getSuplierList();
        
        for (Supplier s : suppliers) {
            supplierCombo.addItem(s.getName());
        }
        
        if (suppliers.size() > 0) {
            supplierCombo.setSelectedIndex(0);
            loadProducts();
        }
    }
    
    private void loadProducts() {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0);
        
        if (supplierCombo.getSelectedItem() == null) return;
        
        String supplierName = (String) supplierCombo.getSelectedItem();
        Supplier supplier = business.getSupplierDirectory().findSupplier(supplierName);
        
        if (supplier != null) {
            ArrayList<Product> products = supplier.getProductCatalog().getProductList();
            
            for (Product p : products) {
                model.addRow(new Object[]{
                    p.toString(),
                    String.format("$%,d", p.getFloorPrice()),
                    String.format("$%,d", p.getCeilingPrice()),
                    String.format("$%,d", p.getTargetPrice())
                });
            }
        }
    }
    
    private void loadSelectedProduct() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow < 0) return;
        
        String supplierName = (String) supplierCombo.getSelectedItem();
        Supplier supplier = business.getSupplierDirectory().findSupplier(supplierName);
        
        if (supplier != null) {
            ArrayList<Product> products = supplier.getProductCatalog().getProductList();
            if (selectedRow < products.size()) {
                selectedProduct = products.get(selectedRow);
                
                floorField.setText(String.valueOf(selectedProduct.getFloorPrice()));
                ceilingField.setText(String.valueOf(selectedProduct.getCeilingPrice()));
                targetField.setText(String.valueOf(selectedProduct.getTargetPrice()));
            }
        }
    }
    
    private void updatePrices() {
        if (selectedProduct == null) {
            JOptionPane.showMessageDialog(this, "Please select a product first");
            return;
        }
        
        try {
            int floor = Integer.parseInt(floorField.getText());
            int ceiling = Integer.parseInt(ceilingField.getText());
            int target = Integer.parseInt(targetField.getText());
            
            if (floor > ceiling) {
                JOptionPane.showMessageDialog(this, "Floor price cannot be greater than ceiling price");
                return;
            }
            
            if (target < floor || target > ceiling) {
                JOptionPane.showMessageDialog(this, "Target price must be between floor and ceiling");
                return;
            }
            
            selectedProduct.updateProduct(floor, ceiling, target);
            loadProducts();
            JOptionPane.showMessageDialog(this, "Prices updated successfully!");
            
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Please enter valid numeric values");
        }
    }
}