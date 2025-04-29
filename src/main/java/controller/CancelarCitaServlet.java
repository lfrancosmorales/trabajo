package controller;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import java.io.IOException;
import java.sql.*;
import model.Conexion;

@WebServlet("/CancelarCita")
public class CancelarCitaServlet extends HttpServlet {
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        res.setHeader("Access-Control-Allow-Origin", "*");

        String id = req.getParameter("id");

        try {
            Connection con = Conexion.getConexion();
            PreparedStatement ps = con.prepareStatement("DELETE FROM citas WHERE id = ?");
            ps.setInt(1, Integer.parseInt(id));
            ps.executeUpdate();

            res.getWriter().print("Cita cancelada");
        } catch (Exception e) {
            e.printStackTrace();
            res.getWriter().print("Error al cancelar");
        }
    }
}

