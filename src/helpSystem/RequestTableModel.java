package helpSystem;

import javax.swing.table.AbstractTableModel;
import java.util.List;

public class RequestTableModel extends AbstractTableModel {
    private List<Request> requests;
    private String[] columnNames = {"ID", "User ID", "Request Text", "Response Text", "Status"};

    public RequestTableModel(List<Request> requests) {
        this.requests = requests;
    }

    @Override
    public int getRowCount() {
        return requests.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Request request = requests.get(rowIndex);
        switch (columnIndex) {
            case 0: return request.getId();
            case 1: return request.getUserId();
            case 2: return request.getRequestText();
            case 3: return request.getResponseText();
            case 4: return request.getStatus();
            default: return null;
        }
    }

    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }
}