package helpSystem;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class AdminFrame extends JFrame {
    // Declare variables for the UI components
    private JTable userTable;
    private JTable requestTable;
    private DefaultTableModel userModel;
    private DefaultTableModel requestModel;
    private JButton refreshButton;
    private JButton addUserButton;
    private JButton deleteUserButton;
    private JButton updateUserButton;
    private JButton returnButton;

    // Constructor for the AdminFrame
    public AdminFrame() {
        // Set the title of the frame
        setTitle("Admin Dashboard");
        // Set the size of the frame
        setSize(800, 600);
        // Set the default close operation (exit the application when the frame is closed)
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // Center the frame on the screen
        setLocationRelativeTo(null);

        // Create a main panel with a BorderLayout
        JPanel panel = new JPanel(new BorderLayout());

        // Create a table for users
        userModel = new DefaultTableModel(new String[]{"ID", "Username", "Role"}, 0);
        userTable = new JTable(userModel);
        // Add the table to a scroll pane
        JScrollPane userScrollPane = new JScrollPane(userTable);
        // Add the scroll pane to the north part of the main panel
        panel.add(userScrollPane, BorderLayout.NORTH);

        // Create a table for requests
        requestModel = new DefaultTableModel(new String[]{"ID", "User ID", "Request Text", "Response Text", "Status"}, 0);
        requestTable = new JTable(requestModel);
        // Add the table to a scroll pane
        JScrollPane requestScrollPane = new JScrollPane(requestTable);
        // Add the scroll pane to the center part of the main panel
        panel.add(requestScrollPane, BorderLayout.CENTER);

        // Create a panel for buttons
        JPanel buttonPanel = new JPanel();
        // Create buttons for refreshing, adding, deleting, and updating users, and returning to the login page
        refreshButton = new JButton("Refresh");
        addUserButton = new JButton("Add User");
        deleteUserButton = new JButton("Delete User");
        updateUserButton = new JButton("Refresh User");
        returnButton = new JButton("Return");

        // Add the buttons to the button panel
        buttonPanel.add(refreshButton);
        buttonPanel.add(addUserButton);
        buttonPanel.add(deleteUserButton);
        buttonPanel.add(updateUserButton);
        buttonPanel.add(returnButton);

        // Add the button panel to the south part of the main panel
        panel.add(buttonPanel, BorderLayout.SOUTH);

        // Add the main panel to the frame
        add(panel);

        // Add action listeners to the buttons
        refreshButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Refresh the tables when the refresh button is clicked
                refreshTables();
            }
        });

        addUserButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Add a new user when the add user button is clicked
                addUser();
            }
        });

        deleteUserButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Delete a user when the delete user button is clicked
                deleteUser();
            }
        });

        updateUserButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Update a user when the update user button is clicked
                updateUser();
            }
        });

        returnButton.addActionListener(new ActionListener() {
  		  
  		  @Override public void actionPerformed(ActionEvent e) { 
              // Return to the login page when the return button is clicked
  			  returnLogin(); 
  			  }
  		});
        
        // Load initial data
        refreshTables();
    }

    // Method to refresh the tables
    private void refreshTables() {
        // Get all users from the UserController
        List<User> users = UserController.getAllUsers();
        // Clear the user table
        userModel.setRowCount(0); 
        // Add each user to the user table
        for (User user : users) {
            userModel.addRow(new Object[]{user.getId(), user.getUsername(), user.getRole()});
        }

        // Get all requests from the RequestController
        List<Request> requests = RequestController.getAllRequests();
        // Clear the request table
        requestModel.setRowCount(0);
        // Add each request to the request table
        for (Request request : requests) {
            requestModel.addRow(new Object[]{request.getId(), request.getUserId(), request.getRequestText(), request.getResponseText(), request.getStatus()});
        }
    }

    // Method to add a new user
    private void addUser() {
        // Get the username, password, and role from the user
        String username = JOptionPane.showInputDialog(this, "Enter Username:");
        String password = JOptionPane.showInputDialog(this, "Enter Password:");
        String role = JOptionPane.showInputDialog(this, "Enter Role (admin/user):");

        if (username != null && password != null && role != null) {
            // Create a new user using the UserController
            UserController.createUser(username, password, role);
            // Refresh the tables
            refreshTables();
        }
    }

    // Method to delete a user
    private void deleteUser() {
        // Get the selected row from the user table
        int selectedRow = userTable.getSelectedRow();
        if (selectedRow != -1) {
            // Get the selected row from the user table
            int userId = (int) userModel.getValueAt(selectedRow, 0);
            // Delete the user using the UserController
            UserController.deleteUser(userId);
            // Refresh the tables
            refreshTables();
        } else {
            // Show an error message if no user is selected
            JOptionPane.showMessageDialog(this, "Please select a user to delete.");
        }
    }

    // Method to update a user
    private void updateUser() {
        // Get the selected row from the user table
        int selectedRow = userTable.getSelectedRow();
        if (selectedRow != -1) {
            // Get the user ID from the selected row
            int userId = (int) userModel.getValueAt(selectedRow, 0);
            // Get the new username, password, and role from the user
            String username = JOptionPane.showInputDialog(this, "Enter new Username:", userModel.getValueAt(selectedRow, 1));
            String password = JOptionPane.showInputDialog(this, "Enter new Password:");
            String role = JOptionPane.showInputDialog(this, "Enter new Role (admin/user):", userModel.getValueAt(selectedRow, 2));

            if (username != null && password != null && role != null) {
                // Update the user using the UserController
                UserController.updateUser(userId, username, password, role);
                // Refresh the tables
                refreshTables();
            }
        } else {
            // Show an error message if no user is selected
            JOptionPane.showMessageDialog(this, "Please select a user to update.");
        }
    }
    
    // Method to return to the login page
    private void returnLogin() {
    	// Create a new LoginFrame and show it
    	new LoginFrame().setVisible(true);
        // Closes the current frame
        dispose();  
    }

    // Main method to start the application
    public static void main(String[] args) {
        // Use SwingUtilities to create the GUI on the Event-Dispatching thread
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                // Create a new AdminFrame and show it
                new AdminFrame().setVisible(true);
            }
        });
    }
    
    private void refreshRequestTable() {
        List<Request> requests = RequestController.getAllRequests();
        // Clear the request table
        requestModel.setRowCount(0); 
        for (Request request : requests) {
            requestModel.addRow(new Object[]{request.getId(), request.getUserId(), request.getRequestText(), request.getResponseText(), request.getStatus()});
        }
    }
}
