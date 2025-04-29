package controller;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import java.io.IOException;
import java.sql.*;
import model.Conexion;

@WebServlet("/Cita")
public class CitaServlet extends HttpServlet {
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        res.setHeader("Access-Control-Allow-Origin", "*");

        String paciente = req.getParameter("paciente");
        int idDoctor = Integer.parseInt(req.getParameter("idDoctor"));
        String fechaHora = req.getParameter("fechaHora");

        try (Connection con = Conexion.getConexion();
             PreparedStatement stmt = con.prepareStatement(
                 "INSERT INTO citas (paciente, id_doctor, fecha_hora) VALUES (?, ?, ?)")) {
            
            stmt.setString(1, paciente);
            stmt.setInt(2, idDoctor);
            stmt.setString(3, fechaHora);
            stmt.executeUpdate();

            res.getWriter().print("Cita añadida correctamente");
        } catch (SQLException e) {
            res.getWriter().print("Error: " + e.getMessage());
        }
    }

    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        res.setHeader("Access-Control-Allow-Origin", "*");
        res.setContentType("text/html");

        try (Connection con = Conexion.getConexion();
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM citas")) {
            
            while (rs.next()) {
                res.getWriter().println(
                    rs.getInt("id") + ": " +
                    rs.getString("paciente") + " - " +
                    rs.getTimestamp("fecha_hora") + "<br>"
                );
            }
        } catch (SQLException e) {
            res.getWriter().print("Error: " + e.getMessage());
        }
    }
}


