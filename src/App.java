import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import conexion.conexionDB;

public class App {
    public static void main(String[] args) {

    System.out.println("Iniciando la aplicación...");

        // Llamamos directamente a ConexionDB.conectar()
        try (Connection conexion = conexionDB.conectar()) {
            System.out.println("¡Conexión obtenida desde ConexionDB!\n");
        } catch (Exception e) {
            System.out.println("❌ Error en la aplicación:");
            e.printStackTrace();
        }
    }
}