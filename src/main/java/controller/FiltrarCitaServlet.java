package controller;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import java.io.IOException;
import java.sql.*;
import model.Conexion;

@WebServlet("/FiltrarCitas")
public class FiltrarCitaServlet extends HttpServlet {
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        res.setHeader("Access-Control-Allow-Origin", "*");
        res.setContentType("text/html;charset=UTF-8");

        String doctor = req.getParameter("doctor");
        String paciente = req.getParameter("paciente");

        try (Connection con = Conexion.getConexion()) {
        
            String query = "SELECT c.id, c.paciente, c.fecha_hora, d.nombre AS doctor " +
                           "FROM citas c INNER JOIN doctores d ON c.id_doctor = d.id WHERE 1=1";

            if (doctor != null && !doctor.isEmpty()) {
                query += " AND d.nombre LIKE ?";
            }
            if (paciente != null && !paciente.isEmpty()) {
                query += " AND c.paciente LIKE ?";
            }

            PreparedStatement ps = con.prepareStatement(query);
            int paramIndex = 1;

            if (doctor != null && !doctor.isEmpty()) {
                ps.setString(paramIndex++, "%" + doctor + "%");
            }
            if (paciente != null && !paciente.isEmpty()) {
                ps.setString(paramIndex++, "%" + paciente + "%");
            }

            ResultSet rs = ps.executeQuery();

            StringBuilder html = new StringBuilder();
            while (rs.next()) {
                html.append("ID: ").append(rs.getInt("id")).append(" - ");
                html.append("Paciente: ").append(rs.getString("paciente")).append(" - ");
                html.append("Doctor: ").append(rs.getString("doctor")).append(" - ");
                html.append("Fecha y Hora: ").append(rs.getTimestamp("fecha_hora")).append("<br>");
            }

            res.getWriter().print(html.toString());
        } catch (Exception e) {
            e.printStackTrace();
            res.getWriter().print("Error al filtrar: " + e.getMessage());
        }
    }
}
