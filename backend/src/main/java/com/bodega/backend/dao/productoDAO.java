package com.bodega.backend.dao;

import com.bodega.backend.modelo.producto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class productoDAO {

    // Spring Boot inyecta automáticamente las credenciales desde application.properties
    @Value("${spring.datasource.url}")
    private String url;

    @Value("${spring.datasource.username}")
    private String username;

    @Value("${spring.datasource.password}")
    private String password;

    private Connection conectar() throws SQLException {
        return DriverManager.getConnection(url, username, password);
    }

    // Método Modificado: Ahora devuelve una LISTA de productos para la API (en lugar de System.out)
    public List<producto> obtenerTodos() {
        List<producto> lista = new ArrayList<>();
        String sql = "SELECT * FROM productos;";

        try (Connection con = conectar();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                String nombre = rs.getString("nombre");
                int cantidad = rs.getInt("cantidad");
                double precio = rs.getDouble("precio");
                Date fecha = rs.getDate("fecha_vencimiento");

                producto p = new producto(nombre, cantidad, precio, fecha.toLocalDate());
                lista.add(p);
            }
        } catch (SQLException e) {
            System.out.println("Error al listar productos: " + e.getMessage());
        }
        return lista;
    }

    public void agregarProducto(producto p) {
        String sql = "INSERT INTO productos (nombre, cantidad, precio, fecha_vencimiento) VALUES (?, ?, ?, ?)";

        try (Connection con = conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, p.getNombre());
            ps.setInt(2, p.getCantidad());
            ps.setDouble(3, p.getPrecio());
            ps.setObject(4, p.getFechaVencimiento());

            ps.executeUpdate();
            System.out.println("¡Producto guardado exitosamente desde la API!");

        } catch (SQLException e) {
            System.out.println("Error al registrar en la BD: " + e.getMessage());
        }
    }
}
