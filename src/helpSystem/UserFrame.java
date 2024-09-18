package helpSystem;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class UserFrame extends JFrame {
    private JTable requestTable;
    private DefaultTableModel requestModel;
    private JTextArea requestTextArea;
    private JButton newRequestButton;
    private JButton refreshButton;

    public UserFrame() {
        setTitle("User Dashboard");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Painel principal
        JPanel panel = new JPanel(new BorderLayout());

        // Tabela de solicitações
        requestModel = new DefaultTableModel(new String[]{"ID", "Request Text", "Response Text", "Status"}, 0);
        requestTable = new JTable(requestModel);
        JScrollPane requestScrollPane = new JScrollPane(requestTable);
        panel.add(requestScrollPane, BorderLayout.CENTER);

        // Área de texto para nova solicitação
        requestTextArea = new JTextArea(5, 20);
        JScrollPane textScrollPane = new JScrollPane(requestTextArea);
        panel.add(textScrollPane, BorderLayout.NORTH);

        // Painel de botões
        JPanel buttonPanel = new JPanel();
        newRequestButton = new JButton("Nova Solicitação");
        refreshButton = new JButton("Atualizar");

        buttonPanel.add(newRequestButton);
        buttonPanel.add(refreshButton);

        panel.add(buttonPanel, BorderLayout.SOUTH);

        add(panel);

        // Ações dos botões
        newRequestButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createNewRequest();
            }
        });

        refreshButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                refreshRequestTable();
            }
        });

        // Carregar dados iniciais
        refreshRequestTable();
    }

    private void createNewRequest() {
        String requestText = requestTextArea.getText();
        if (!requestText.isEmpty()) {
            RequestController.createRequest(getCurrentUserId(), requestText);
            requestTextArea.setText(""); // Limpar a área de texto após criar a solicitação
            refreshRequestTable();
        } else {
            JOptionPane.showMessageDialog(this, "O texto da solicitação não pode estar vazio.");
        }
    }

    private void refreshRequestTable() {
        List<Request> requests = RequestController.getAllRequests();
        requestModel.setRowCount(0); // Limpar tabela
        for (Request request : requests) {
            requestModel.addRow(new Object[]{request.getId(), request.getRequestText(), request.getResponseText(), request.getStatus()});
        }
    }

    // Método fictício para obter o ID do usuário atual
    private int getCurrentUserId() {
        // Implementar lógica para obter o ID do usuário atual
        return 1; // Exemplo estático
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new UserFrame().setVisible(true);
            }
        });
    }
}