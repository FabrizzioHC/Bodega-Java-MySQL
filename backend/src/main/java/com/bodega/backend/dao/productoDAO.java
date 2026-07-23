package com.bodega.backend.dao; //para activar la ayuda de copilot( ctrl + shift + P ) y luego escribes Copilot: Enable
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
                int id = rs.getInt("id");
                String nombre = rs.getString("nombre");
                int cantidad = rs.getInt("cantidad");
                double precio = rs.getDouble("precio");
                Date fecha = rs.getDate("fecha_vencimiento");

                producto p = new producto(id, nombre, cantidad, precio, fecha.toLocalDate());
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

    // en teoria ya no se necesitaria el mostrar?, por que los datos ya se estan vizualizando en el front en la parte de app.js

    // buscar producto por nombrwe
    public List<producto> buscarProducto(String nombre){
        List<producto> lista = new ArrayList<>();

        String sql = "SELECT * FROM productos WHERE nombre LIKE ?;";

        try(Connection con = conectar();
            PreparedStatement ps = con.prepareStatement(sql)){
            
            ps.setString(1, "%"+ nombre +"%"); // aca declaramos que el ? sera nombre

            try(ResultSet rs = ps.executeQuery()){
                while (rs.next()) {
                    producto p = new producto(
                        rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getInt("cantidad"),
                        rs.getDouble("precio"),
                        rs.getDate("fecha_vencimiento").toLocalDate()
                    );
                    lista.add(p);
                }
            }

        }catch(SQLException e){
            System.out.println("No se pudo encontrar el Producto que esta buscando"+e.getMessage());
        }
        return lista;
    }

    // intento de hacer el metodo Editar:

    public void editarProducto (int idBuscado, producto p){
        String sql = "UPDATE productos SET nombre = ?, cantidad = ?, precio = ?, fecha_vencimiento = ? WHERE id = ?;";

        try(Connection con = conectar();
            PreparedStatement ps = con.prepareStatement(sql)){
            
            ps.setString(1, p.getNombre());
            ps.setInt(2, p.getCantidad());
            ps.setDouble(3, p.getPrecio());
            ps.setObject(4, p.getFechaVencimiento()); // "OBJECT" Soporta LocalDate directamente sin conversiones
            ps.setInt(5, idBuscado);

            int lineasModificadas = ps.executeUpdate();

            if (lineasModificadas > 0) {
                System.out.println("¡Los datos del producto " + idBuscado + " fueron modificados correctamente!");
            } else {
                System.out.println("No se encontró ningún producto con el ID " + idBuscado);
            }

        }catch(SQLException e){
            System.out.println("Hubo un error al Editar el Producto: "+e.getMessage());
        }
    }

    //eliminar
    public void eliminarProducto (int idSeleccionado){
        String sql = "DELETE FROM productos WHERE id = ?;";

        try(Connection con = conectar();
            PreparedStatement ps = con.prepareStatement(sql)){
            
            ps.setInt(1, idSeleccionado);


            int lineasModificadas = ps.executeUpdate();

            if (lineasModificadas > 0) {
                System.out.println("¡Los datos del producto " + idSeleccionado + " fueron eliminados correctamente!");
            } else {
                System.out.println("No se encontró ningún producto con el ID " + idSeleccionado);
            }

        }catch(SQLException e){
            System.out.println("Hubo un error al Eliminar el Producto: "+e.getMessage());
        }
    }
}
