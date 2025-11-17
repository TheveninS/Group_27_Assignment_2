package UserInterface.SalesManagement;

import TheBusiness.Business.Business;
import TheBusiness.Personnel.Person;
import TheBusiness.SalesManagement.SalesPersonProfile;
import TheBusiness.UserAccountManagement.UserAccount;
import javax.swing.*;
import java.awt.*;

public class AddSalesPersonJPanel extends JPanel {
    private Business business;
    private JPanel cardPanel;
    private JTextField nameField, usernameField, passwordField;
    
    public AddSalesPersonJPanel(Business b, JPanel cards) {
        this.business = b;
        this.cardPanel = cards;
        
        setBackground(new Color(0, 153, 153));
        setLayout(null);
        
        JLabel title = new JLabel("Add New Sales Person");
        title.setFont(new Font("Arial", Font.PLAIN, 24));
        title.setBounds(20, 20, 400, 30);
        add(title);
        
        JLabel nameLbl = new JLabel("Full Name:");
        nameLbl.setBounds(50, 100, 120, 25);
        add(nameLbl);
        
        nameField = new JTextField();
        nameField.setBounds(180, 100, 250, 25);
        add(nameField);
        
        JLabel userLbl = new JLabel("Username:");
        userLbl.setBounds(50, 150, 120, 25);
        add(userLbl);
        
        usernameField = new JTextField();
        usernameField.setBounds(180, 150, 250, 25);
        add(usernameField);
        
        JLabel passLbl = new JLabel("Password:");
        passLbl.setBounds(50, 200, 120, 25);
        add(passLbl);
        
        passwordField = new JPasswordField();
        passwordField.setBounds(180, 200, 250, 25);
        add(passwordField);
        
        JButton createBtn = new JButton("Create Sales Person");
        createBtn.setBounds(180, 260, 180, 35);
        createBtn.setBackground(new Color(102, 153, 255));
        createBtn.setForeground(Color.WHITE);
        createBtn.addActionListener(e -> createSalesPerson());
        add(createBtn);
        
        JButton back = new JButton("<< Back");
        back.setBounds(20, 470, 90, 30);
        back.addActionListener(e -> {
            cardPanel.remove(this);
            ((CardLayout) cardPanel.getLayout()).next(cardPanel);
        });
        add(back);
    }
    
    private void createSalesPerson() {
        String name = nameField.getText().trim();
        String username = usernameField.getText().trim();
        String password = passwordField.getText().trim();
        
        if (name.isEmpty() || username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "All fields are required");
            return;
        }
        
        // Check if username already exists
        UserAccount existingUser = business.getUserAccountDirectory().findUserAccount(name);
        if (existingUser != null) {
            JOptionPane.showMessageDialog(this, 
                "A user with this name already exists. Please use a different name.");
            return;
        }
        
        try {
            // Create person
            Person person = business.getPersonDirectory().newPerson(name);
            
            // Create sales person profile
            SalesPersonProfile salesProfile = business.getSalesPersonDirectory()
                .newSalesPersonProfile(person);
            
            // Create user account
            UserAccount userAccount = business.getUserAccountDirectory()
                .newUserAccount(salesProfile, username, password);
            
            JOptionPane.showMessageDialog(this, 
                "Sales person '" + name + "' created successfully!\n" +
                "Username: " + username + "\n" +
                "They can now login to the system.",
                "Success",
                JOptionPane.INFORMATION_MESSAGE);
            
            // Clear fields
            nameField.setText("");
            usernameField.setText("");
            passwordField.setText("");
            
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, 
                "Error creating sales person: " + ex.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }
}