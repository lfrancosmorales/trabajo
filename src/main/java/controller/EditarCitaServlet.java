package controller;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import java.io.IOException;
import java.sql.*;
import model.Conexion;

@WebServlet("/EditarCita")
public class EditarCitaServlet extends HttpServlet {
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        res.setHeader("Access-Control-Allow-Origin", "*");
        res.setContentType("text/plain");

        String id = req.getParameter("id");
        String nuevaFechaHora = req.getParameter("fechaHora"); 

        try (Connection con = Conexion.getConexion()) {
            PreparedStatement ps = con.prepareStatement(
                "UPDATE citas SET fecha_hora = ? WHERE id = ?"
            );
            ps.setString(1, nuevaFechaHora);
            ps.setInt(2, Integer.parseInt(id));
            int filasActualizadas = ps.executeUpdate();

            if (filasActualizadas > 0) {
                res.getWriter().print("Cita actualizada correctamente.");
            } else {
                res.getWriter().print("No se encontró ninguna cita con ese ID.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            res.getWriter().print("Error al actualizar la cita: " + e.getMessage());
        }
    }
}
