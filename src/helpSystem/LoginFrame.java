package helpSystem;

import javax.swing.*;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

// Graphic Interface frame for user login
public class LoginFrame extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton, exitButton;

    public LoginFrame() {
        setTitle("Login");
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

        
        usernameField = new JTextField(15);
        passwordField = new JPasswordField(15);
        loginButton = new JButton("Login");
        exitButton = new JButton("Exit");
        
        // Button Panel
        JPanel buttonPanel = new JPanel();
        loginButton = new JButton("Login");
        exitButton = new JButton("Exit");
        buttonPanel.add(loginButton);
        buttonPanel.add(exitButton);

        // Add panels to the frame
        add(panel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        /**
         * Login button action listener.
         * 
         * When the login button is clicked, it retrieves the username and password from the text fields,
         * and attempts to log in to the system. If the credentials are valid, it opens either the AdminFrame
         * or UserFrame depending on the user's role, and closes the LoginFrame. If the credentials are invalid,
         * it displays an error message.
         */
        
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());

                try (Connection conn = DatabaseConnection.getConnection()) {
                    String sql = "SELECT * FROM users WHERE username = ? AND password = ?";
                    PreparedStatement pstmt = conn.prepareStatement(sql);
                    pstmt.setString(1, username);
                    pstmt.setString(2, password);
                    ResultSet rs = pstmt.executeQuery();

                    if (rs.next()) {
                        String role = rs.getString("role");
                        if (role.equals("admin")) {
                            // Opens Admin Frame
                            AdminFrame adminFrame = new AdminFrame();
                            adminFrame.setVisible(true);
                        } else {
                            // Opens User Frame
                            UserFrame userFrame = new UserFrame();
                            userFrame.setVisible(true);
                        }
                        dispose(); // Closes login
                    } else {
                        JOptionPane.showMessageDialog(null, "Login failed. Wrong credentials.");
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });


        /**
         * Exit button action listener.
         * 
         * When the exit button is clicked, it closes the LoginFrame and exits the application.
         */
        
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose(); // Close the LoginFrame
                System.exit(0); // Exit the application
            }
        });
    }

    

    
}
