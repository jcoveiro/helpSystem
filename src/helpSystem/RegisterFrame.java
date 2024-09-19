package helpSystem;

import javax.swing.*;

import java.awt.BorderLayout;
//import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class RegisterFrame extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JTextField roleField;
	private JButton registerButton, clearButton;

    public RegisterFrame() {
        setTitle("Register");
        setSize(300, 150);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
        setLayout(new BorderLayout());

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5); // Spacing between components
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Username
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(new JLabel("Username:"), gbc);

        gbc.gridx = 1;
        usernameField = new JTextField(15);
        panel.add(usernameField, gbc);

        // Password
        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(new JLabel("Password:"), gbc);

        gbc.gridx = 1;
        passwordField = new JPasswordField(15);
        panel.add(passwordField, gbc);
        
        // Role
        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(new JLabel("Role:"), gbc);

        gbc.gridx = 1;
        roleField = new JTextField(15);
        panel.add(roleField, gbc);
        
        // Initialize buttons
        registerButton = new JButton("Register");
        clearButton = new JButton("Clear");
        
        // Button Panel for login and clear
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(registerButton);
        buttonPanel.add(clearButton);

        // Add panels to the frame
        add(panel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
         
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());
                String role = roleField.getText();

                // Insert user into the database
                try (Connection conn = DatabaseConnection.getConnection()) {
                     String sql = "INSERT INTO users(username,password,role) VALUES(?,?,?)";
                     PreparedStatement pstmt = conn.prepareStatement(sql);
                     pstmt.setString(1, username);
                     pstmt.setString(2, password);
                     pstmt.setString(3, role);
                     int rowsInserted = pstmt.executeUpdate();

                     if (rowsInserted > 0) {
                         JOptionPane.showMessageDialog(null, "User registered successfully.");

                         // Check role and open corresponding frame
                         if (role.equalsIgnoreCase("admin")) {
                             AdminFrame adminFrame = new AdminFrame();
                             adminFrame.setVisible(true);
                         } else {
                             UserFrame userFrame = new UserFrame();
                             userFrame.setVisible(true);
                         }

                         dispose(); // Closes the register window
                     } else {
                         JOptionPane.showMessageDialog(null, "Registration failed.");
                     }
                 } catch (Exception ex) {
                     ex.printStackTrace();
                     JOptionPane.showMessageDialog(null, "An error occurred during registration.");
                 }
            }
        });

        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	usernameField.setText("");  // Clear username field
                passwordField.setText("");  // Clear password field
                roleField.setText("");  // Clear role field
            }
        });
        
    }   

}