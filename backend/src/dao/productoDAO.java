package dao;
import modelo.producto;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import conexion.conexionDB; // Importamos tu conexión

public class productoDAO {

    public void agregarProducto(producto p){
        
        String sql = "INSERT INTO productos (nombre, cantidad, precio, fecha_vencimiento) VALUES (?, ?, ?, ?)";

        // 2. Abrimos la conexión y preparamos la consulta
        try (Connection con = conexionDB.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {
            
            // 3. Rellenamos los "?" con los datos reales del producto "p"
            // El número indica la posición del "?" de izquierda a derecha (empieza en 1)
            ps.setString(1, p.getNombre());    // Primer "?" -> Nombre (String)
            ps.setInt(2, p.getCantidad());    // Segundo "?" -> Cantidad (int)
            ps.setDouble(3, p.getPrecio());    // Tercer "?" -> Precio (double)
            ps.setObject(4, p.getFechaVencimiento()); // Cuarto "?" -> Fecha de Vencimiento (LocalDate)
            
            // 4. Ejecutamos la acción en la base de datos
            ps.executeUpdate(); 
            System.out.println("¡Producto guardado exitosamente en MySQL!");
            
        } catch (SQLException e) {
            System.out.println("Error al registrar en la BD: " + e.getMessage());
        }
    }

    public void mostrarProductos() {
        String sql = "SELECT * FROM productos;";

        // 1. Conectamos y preparamos la consulta
        try (Connection con = conexionDB.conectar();
            PreparedStatement ps = con.prepareStatement(sql);
            // 2. ¡Aquí está la magia! Ejecutamos la consulta y guardamos el resultado en "rs"
            ResultSet rs = ps.executeQuery()) {
            
            System.out.println("=========================================================");
            System.out.println("             INVENTARIO ACTUAL DE LA BODEGA              ");
            System.out.println("=========================================================");

            boolean tieneDatos = false;

            System.out.printf("| %-5s | %-20s | %-8s | %-12s | %-12s |%n","ID", "Nombre", "Cantidad", "Precio", "Vencimiento");
            System.out.println("---------------------------------------------------------------------------------");

            // 3. El bucle "while" recorre la tabla fila por fila mientras existan registros
            while (rs.next()) {
                tieneDatos = true;
                
                // Extraemos los valores de las columnas usando el tipo de dato y el nombre exacto de la columna en MySQL
                int id = rs.getInt("id");
                String nombre = rs.getString("nombre");
                int cantidad = rs.getInt("cantidad");
                double precio = rs.getDouble("precio");
                java.sql.Date fechaVencimiento = rs.getDate("fecha_vencimiento");

                // 4. Imprimimos directamente los datos que acabamos de sacar de la BD
                System.out.printf("| %-5d | %-20s | %-8d | S/. %-8.2f | %-12s |%n", id, nombre, cantidad, precio, fechaVencimiento);
            }

            if (!tieneDatos) {
                System.out.println("La bodega está vacía. ¡Registra un producto primero!");
            }

            System.out.println("=========================================================");

        } catch (SQLException e) {
            System.out.println("Error al mostrar los productos: " + e.getMessage());
        }
    }

    public void buscarProducto(int idBuscado) { //Agregar en su momento 2 formas de buscar tanto por ID y por nombre
        String sql = "SELECT * FROM productos WHERE id = ?;";

        // 1. Preparamos la conexión y el Statement (sin ejecutar aún)
        try (Connection con = conexionDB.conectar();
            PreparedStatement ps = con.prepareStatement(sql)) {
            
            // 2. ¡PRIMERO asignamos el valor al "?"!
            ps.setInt(1, idBuscado); 
            
            // 3. ¡AHORA SÍ ejecutamos la consulta y abrimos el ResultSet!
            try (ResultSet rs = ps.executeQuery()) {
                
                System.out.println("=========================================================");
                System.out.println("          RESULTADO DE LA BÚSQUEDA DEL PRODUCTO          ");
                System.out.println("=========================================================");

                System.out.printf("| %-5s | %-20s | %-8s | %-12s | %-12s |%n","ID", "Nombre", "Cantidad", "Precio", "Vencimiento");
                System.out.println("---------------------------------------------------------------------------------");

                // 4. Como solo esperamos 1 resultado, usamos "if" en lugar de "while"
                if (rs.next()) {
                    // Extraemos los valores directamente, ya sabemos que el ID coincide
                    int id = rs.getInt("id");
                    String nombre = rs.getString("nombre");
                    int cantidad = rs.getInt("cantidad");
                    double precio = rs.getDouble("precio");
                    java.sql.Date fechaVencimiento = rs.getDate("fecha_vencimiento");

                    System.out.printf("| %-5d | %-20s | %-8d | S/. %-8.2f | %-12s |%n", id, nombre, cantidad, precio, fechaVencimiento);
                } else {
                    // Si rs.next() es false, significa que la consulta no trajo filas de la BD
                    System.out.println("No se encontró el producto con el ID: " + idBuscado);
                }
                System.out.println("=========================================================");
            }
            
        } catch (SQLException e) {
            System.out.println("Error al buscar el producto en la BD: " + e.getMessage());
        }
    }

    public void editarProducto(int idBuscado, String nombre, int cantidad, double precio, LocalDate fechaVencimiento) {
        
        String sql = "UPDATE productos SET nombre = ?, cantidad = ?, precio = ?, fecha_vencimiento = ? WHERE id = ?;";

        try (Connection con = conexionDB.conectar();
            PreparedStatement ps = con.prepareStatement(sql)) {
            
            // Ahora solo tenemos 4 signos de interrogación en total:
            ps.setString(1, nombre);     // 1er "?" -> Nuevo Nombre
            ps.setInt(2, cantidad);      // 2do "?" -> Nueva Cantidad
            ps.setDouble(3, precio);     // 3er "?" -> Nuevo Precio
            ps.setObject(4, fechaVencimiento);
            ps.setInt(5, idBuscado);     // 4to "?" -> El ID del producto que queremos modificar

            int filasAfectadas = ps.executeUpdate();
            
            if (filasAfectadas > 0) {
                System.out.println("¡Producto editado con éxito en MySQL!");
            } else {
                System.out.println("No se encontró ningún producto con el ID: " + idBuscado);
            }
        } catch (SQLException e) {
            System.out.println("Error al editar el producto: " + e.getMessage());
        }
    }

    public void eliminarProducto(int idBuscado){

        String sql = "DELETE FROM productos WHERE id = ?;";
        
        try (Connection con = conexionDB.conectar();
            PreparedStatement ps = con.prepareStatement(sql)) {
            
            // Rellenamos el único "?" con el ID que queremos eliminar
            ps.setInt(1, idBuscado);// 1er "?" -> El ID del producto que queremos eliminar
            int filasAfectadas = ps.executeUpdate();
            
            if (filasAfectadas > 0) {
                System.out.println("¡Producto eliminado con éxito en MySQL!");
            } else {
                System.out.println("No se encontró ningún producto con el ID: " + idBuscado);
            }
        } catch (SQLException e) {
            System.out.println("Error al eliminar el producto: " + e.getMessage());
        }
    }
}
