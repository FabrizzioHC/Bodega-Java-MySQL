package conexion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class conexionDB {
    // Credenciales privadas para que nadie las modifique desde fuera
    private static final String URL = "jdbc:mysql://localhost:3306/prueba_1";
    private static final String USUARIO = "root";
    private static final String CONTRASEÑA = "abc123$";

    // Método único para obtener la conexión
    public static Connection conectar() throws SQLException {
        return DriverManager.getConnection(URL, USUARIO, CONTRASEÑA);
    }
}
