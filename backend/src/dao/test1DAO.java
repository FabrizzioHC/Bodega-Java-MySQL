package dao;
import modelo.producto;
import java.util.Scanner;
import java.util.InputMismatchException;
import java.time.LocalDate;

public class test1DAO {
    
    public static productoDAO product = new productoDAO();
    public static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        
        while(true){
            System.out.println("================================");
            System.out.println("Bienvenido a la Bodega Fabri");
            System.out.println("================================");
            System.out.println("1. Agregar Productos");
            System.out.println("2. Mostrar Lista de Productos");
            System.out.println("3. Buscar Producto");
            System.out.println("4. Editar Productos");
            System.out.println("5. Eliminar Productos");
            System.out.println("6. Salir");
            System.out.println("================================");
            int opcion = (int) ValidarDato("int", "Ingrese una opcion:");
            switch(opcion){
                case 1 -> agregarProducto();
                case 2 -> mostrarProductos();
                case 3 -> buscarProducto();
                case 4 -> editarProductos();
                case 5 -> eliminarProductos();
                case 6 -> {
                    System.out.println("Saliendo del Programa...");
                    sc.nextLine();
                    return;
                }
                default -> {
                    System.out.println("Opcion Invalida, porfavor intente de nuevo!");
                    sc.nextLine();
                }
            }
        }
    }

    public static void agregarProducto(){
        System.out.println("Ingrese el Nombre del Producto:");
        String nombre = sc.nextLine();
        int cantidad = (int) ValidarDato("int", "Ingrese la Cantidad del Producto:");
        double precion = (double) ValidarDato("double", "Ingrese el Precio del Producto:");
        
        LocalDate fechaVencimiento = null;
        while (fechaVencimiento == null) {
            System.out.println("Ingrese la Fecha de Vencimiento del Producto (Año-Mes-Dia, Ej: 2026-12-31):");
            try {
                fechaVencimiento = LocalDate.parse(sc.nextLine());
            } catch (Exception e) {
                System.out.println("Formato de fecha inválido. Use estrictamente el formato Año-Mes-Dia.");
            }
        }

        try {
            producto p = new producto(nombre, cantidad, precion, fechaVencimiento);
            product.agregarProducto(p);
        } catch (IllegalArgumentException e) {
            System.out.println("Error de validación: " + e.getMessage());
        }
    }

    public static void mostrarProductos(){
        product.mostrarProductos();
        sc.nextLine();
    }

    public static void buscarProducto(){
        int idBuscado = (int) ValidarDato("int", "Ingrese el ID del Producto a buscar:");
        product.buscarProducto(idBuscado);
        sc.nextLine();
    }

    public static void editarProductos(){

        int idBuscado = (int) ValidarDato("int", "Ingrese el ID del Producto a Editar:");
        product.buscarProducto(idBuscado);
        System.out.println("Ingrese el Nuevo Nombre del Producto:");
        String nombre = sc.nextLine();
        int cantidad = (int) ValidarDato ("int", "Ingrese la Nueva Cantidad del Producto:");
        double precion = (double) ValidarDato ("double", "Ingrese el Nuevo Precio del Producto:");
        System.out.println("Ingrese la Nueva Fecha de Vencimiento del Producto (Año-Mes-Dia):");
        LocalDate fechaVencimiento = LocalDate.parse(sc.nextLine());
        product.editarProducto(idBuscado, nombre, cantidad, precion, fechaVencimiento);
        sc.nextLine();
    }

    public static void eliminarProductos(){
        int idBuscado = (int) ValidarDato("int", "Ingrese el ID del Producto a Eliminar:");
        product.eliminarProducto(idBuscado);
        sc.nextLine();
    }
    
    public static Object ValidarDato (String tipo, String texto){
        while(true){
            System.out.println(texto);
            try{
                switch(tipo){
                    case "int" ->{
                        int valor = sc.nextInt();
                        sc.nextLine();
                        return valor;
                    }
                    case "double" ->{
                        double valor = sc.nextDouble();
                        sc.nextLine();
                        return valor;
                    }
                    default -> {
                        System.out.println("Tipo de dato no valido, intente denuevo");
                        sc.nextLine();
                    }
                }

            }catch(InputMismatchException e){
                System.out.println("Error: Ingrese un valor valido");
                sc.nextLine();
            }catch(IllegalArgumentException e){
                System.out.println("Error: Ingrese un valor valido" + e.getMessage());
                sc.nextLine();

            }
        }
    }
}
