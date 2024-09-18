package helpSystem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class RequestController {

    // Método para criar uma nova solicitação
    public static void createRequest(int userId, String requestText) {
        try (Connection conn = DatabaseConnection.getConnection()) {
            String sql = "INSERT INTO requests (user_id, request_text, status) VALUES (?, ?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, userId);
            pstmt.setString(2, requestText);
            pstmt.setString(3, "Pending");
            pstmt.executeUpdate();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    // Método para atualizar uma solicitação existente
    public static void updateRequest(int requestId, String responseText, String status) {
        try (Connection conn = DatabaseConnection.getConnection()) {
            String sql = "UPDATE requests SET response_text = ?, status = ?, updated_at = CURRENT_TIMESTAMP WHERE id = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, responseText);
            pstmt.setString(2, status);
            pstmt.setInt(3, requestId);
            pstmt.executeUpdate();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    // Método para deletar uma solicitação
    public static void deleteRequest(int requestId) {
        try (Connection conn = DatabaseConnection.getConnection()) {
            String sql = "DELETE FROM requests WHERE id = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, requestId);
            pstmt.executeUpdate();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    // Método para listar todas as solicitações
    public static List<Request> getAllRequests() {
        List<Request> requests = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection()) {
            String sql = "SELECT * FROM requests";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Request request = new Request();
                request.setId(rs.getInt("id"));
                request.setUserId(rs.getInt("user_id"));
                request.setRequestText(rs.getString("request_text"));
                request.setResponseText(rs.getString("response_text"));
                request.setStatus(rs.getString("status"));
                request.setCreatedAt(rs.getTimestamp("created_at"));
                request.setUpdatedAt(rs.getTimestamp("updated_at"));
                requests.add(request);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return requests;
    }

    // Método para obter uma solicitação específica pelo ID
    public static Request getRequestById(int requestId) {
        Request request = null;
        try (Connection conn = DatabaseConnection.getConnection()) {
            String sql = "SELECT * FROM requests WHERE id = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, requestId);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                request = new Request();
                request.setId(rs.getInt("id"));
                request.setUserId(rs.getInt("user_id"));
                request.setRequestText(rs.getString("request_text"));
                request.setResponseText(rs.getString("response_text"));
                request.setStatus(rs.getString("status"));
                request.setCreatedAt(rs.getTimestamp("created_at"));
                request.setUpdatedAt(rs.getTimestamp("updated_at"));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return request;
    }
}