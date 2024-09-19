package helpSystem;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class AdminFrame extends JFrame {
    private JTable userTable;
    private JTable requestTable;
    private DefaultTableModel userModel;
    private DefaultTableModel requestModel;
    private JButton refreshButton;
    private JButton addUserButton;
    private JButton deleteUserButton;
    private JButton updateUserButton;
    private JButton returnButton;

    public AdminFrame() {
        setTitle("Admin Dashboard");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Painel principal
        JPanel panel = new JPanel(new BorderLayout());

        // Tabela de usuários
        userModel = new DefaultTableModel(new String[]{"ID", "Username", "Role"}, 0);
        userTable = new JTable(userModel);
        JScrollPane userScrollPane = new JScrollPane(userTable);
        panel.add(userScrollPane, BorderLayout.NORTH);

        // Tabela de solicitações
        requestModel = new DefaultTableModel(new String[]{"ID", "User ID", "Request Text", "Response Text", "Status"}, 0);
        requestTable = new JTable(requestModel);
        JScrollPane requestScrollPane = new JScrollPane(requestTable);
        panel.add(requestScrollPane, BorderLayout.CENTER);

        // Painel de botões
        JPanel buttonPanel = new JPanel();
        refreshButton = new JButton("Refresh");
        addUserButton = new JButton("Add User");
        deleteUserButton = new JButton("Delete User");
        updateUserButton = new JButton("Refresh User");
        returnButton = new JButton("Return");

        buttonPanel.add(refreshButton);
        buttonPanel.add(addUserButton);
        buttonPanel.add(deleteUserButton);
        buttonPanel.add(updateUserButton);
        buttonPanel.add(returnButton);

        panel.add(buttonPanel, BorderLayout.SOUTH);

        add(panel);

        // Ações dos botões
        refreshButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                refreshTables();
            }
        });

        addUserButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addUser();
            }
        });

        deleteUserButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteUser();
            }
        });

        updateUserButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateUser();
            }
        });

        returnButton.addActionListener(new ActionListener() {
  		  
  		  @Override public void actionPerformed(ActionEvent e) { 
  			  returnLogin(); 
  			  }
  		});
        
        // Carregar dados iniciais
        refreshTables();
    }

    private void refreshTables() {
        // Atualizar tabela de usuários
        List<User> users = UserController.getAllUsers();
        userModel.setRowCount(0); // Limpar tabela
        for (User user : users) {
            userModel.addRow(new Object[]{user.getId(), user.getUsername(), user.getRole()});
        }

        // Atualizar tabela de solicitações
        List<Request> requests = RequestController.getAllRequests();
        requestModel.setRowCount(0); // Limpar tabela
        for (Request request : requests) {
            requestModel.addRow(new Object[]{request.getId(), request.getUserId(), request.getRequestText(), request.getResponseText(), request.getStatus()});
        }
    }

    private void addUser() {
        String username = JOptionPane.showInputDialog(this, "Enter Username:");
        String password = JOptionPane.showInputDialog(this, "Enter Password:");
        String role = JOptionPane.showInputDialog(this, "Enter Role (admin/user):");

        if (username != null && password != null && role != null) {
            UserController.createUser(username, password, role);
            refreshTables();
        }
    }

    private void deleteUser() {
        int selectedRow = userTable.getSelectedRow();
        if (selectedRow != -1) {
            int userId = (int) userModel.getValueAt(selectedRow, 0);
            UserController.deleteUser(userId);
            refreshTables();
        } else {
            JOptionPane.showMessageDialog(this, "Please select a user to delete.");
        }
    }

    private void updateUser() {
        int selectedRow = userTable.getSelectedRow();
        if (selectedRow != -1) {
            int userId = (int) userModel.getValueAt(selectedRow, 0);
            String username = JOptionPane.showInputDialog(this, "Enter new Username:", userModel.getValueAt(selectedRow, 1));
            String password = JOptionPane.showInputDialog(this, "Enter new Password:");
            String role = JOptionPane.showInputDialog(this, "Enter new Role (admin/user):", userModel.getValueAt(selectedRow, 2));

            if (username != null && password != null && role != null) {
                UserController.updateUser(userId, username, password, role);
                refreshTables();
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please select a user to update.");
        }
    }
    
    // To Return to Login Page
    private void returnLogin() {
    	
    	new LoginFrame().setVisible(true);
        dispose(); // Closes the current frame (UserFrame)
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new AdminFrame().setVisible(true);
            }
        });
    }
    
    private void refreshRequestTable() {
        List<Request> requests = RequestController.getAllRequests();
        requestModel.setRowCount(0); // Limpar tabela
        for (Request request : requests) {
            requestModel.addRow(new Object[]{request.getId(), request.getUserId(), request.getRequestText(), request.getResponseText(), request.getStatus()});
        }
    }
}
