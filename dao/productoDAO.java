package dao;
import java.util.ArrayList;
import java.util.List;
import modelo.producto;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import conexion.conexionDB; // Importamos tu conexión

public class productoDAO {
    public List <producto> Productos = new ArrayList<>();

    public void agregarProducto(producto p){
        
        String sql = "INSERT INTO productos (id, nombre, cantidad, precio) VALUES (?, ?, ?, ?)";

        // 2. Abrimos la conexión y preparamos la consulta
        try (Connection con = conexionDB.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {
            
            // 3. Rellenamos los "?" con los datos reales del producto "p"
            // El número indica la posición del "?" de izquierda a derecha (empieza en 1)
            ps.setInt(1, p.getId());          // Primer "?" -> ID (int)
            ps.setString(2, p.getNombre());    // Segundo "?" -> Nombre (String)
            ps.setInt(3, p.getCantidad());    // Tercer "?" -> Cantidad (int)
            ps.setDouble(4, p.getPrecio());    // Cuarto "?" -> Precio (double)
            
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

            // 3. El bucle "while" recorre la tabla fila por fila mientras existan registros
            while (rs.next()) {
                tieneDatos = true;
                
                // Extraemos los valores de las columnas usando el tipo de dato y el nombre exacto de la columna en MySQL
                int id = rs.getInt("id");
                String nombre = rs.getString("nombre");
                int cantidad = rs.getInt("cantidad");
                double precio = rs.getDouble("precio");

                // 4. Imprimimos directamente los datos que acabamos de sacar de la BD
                System.out.println("ID: " + id + " || Nombre: " + nombre + " || Cantidad: " + cantidad + " || Precio: S/. " + precio);
            }

            if (!tieneDatos) {
                System.out.println("La bodega está vacía. ¡Registra un producto primero!");
            }

            System.out.println("=========================================================");

        } catch (SQLException e) {
            System.out.println("Error al mostrar los productos: " + e.getMessage());
        }
    }

    public void buscarProducto(int idBuscado) {
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

                // 4. Como solo esperamos 1 resultado, usamos "if" en lugar de "while"
                if (rs.next()) {
                    // Extraemos los valores directamente, ya sabemos que el ID coincide
                    int id = rs.getInt("id");
                    String nombre = rs.getString("nombre");
                    int cantidad = rs.getInt("cantidad");
                    double precio = rs.getDouble("precio");

                    System.out.println("ID: " + id + " || Nombre: " + nombre + " || Cantidad: " + cantidad + " || Precio: S/. " + precio);
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

    public void editarProducto(int id, String nombre, int cantidad, double precio){

        String sql = "UPDATE productos SET id = ?, nombre = ?, cantidad = ?, precio = ? WHERE id = ?";

        try (Connection con = conexionDB.conectar();
             PreparedStatement ps = con.prepareStatement(sql);) {
            
            ps.setInt(1, id);
            ps.setString(2, nombre);
            ps.setInt(3, cantidad);
            ps.setDouble(4, precio);

            try (ResultSet rs = ps.executeQuery()) {
                
                System.out.println("=========================================================");
                System.out.println("      PRODUCTO EDITADO EXITOSAMENTE EN LA BODEGA         ");
                System.out.println("=========================================================");

                if (rs.next()) {
                    
                    int idNuevo = rs.getInt("id");
                    String nombreNuevo = rs.getString("nombre");
                    int cantidadNueva = rs.getInt("cantidad");
                    double precioNuevo = rs.getDouble("precio");

                    System.out.println("ID: " + idNuevo + " || Nombre: " + nombreNuevo + " || Cantidad: " + cantidadNueva + " || Precio: S/. " + precioNuevo);
                } else {
                    // Si rs.next() es false, significa que la consulta no trajo filas de la BD
                    System.out.println("No se encontró el producto con el ID: " + id);
                }
                System.out.println("=========================================================");
            }

        } catch (SQLException e) {
            System.out.println("Error al registrar en la BD: " + e.getMessage());
        }
    }

    public void eliminarProducto(int idBuscado){

        boolean encontrar = false;
        for(producto p : Productos){
            if(p.getId() == idBuscado){
                Productos.remove(p);
                System.out.println("Se elimino el Producto" + p.getNombre() + " con el ID: " + idBuscado);
                encontrar = true;
            }
        }
        if (!encontrar) {
            System.out.println("No se encontro el producto con el ID: " + idBuscado);
        }
    }
}
