package dao;
import modelo.producto;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.InputMismatchException;

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
        int id = (int) ValidarDato("int", "Ingrese el ID del Producto:");
        System.out.println("Ingrese el Nombre del Producto:");
        String nombre = sc.nextLine();
        int cantidad = (int) ValidarDato ("int", "Ingrese la Cantidad del Producto:");
        double precion = (double) ValidarDato ("double", "Ingrese el Precio del Producto:");
        producto p = new producto(id, nombre, cantidad, precion);
        product.agregarProducto(p);
        System.out.println("Producto agregado con exito!");
        sc.nextLine();
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
        int id = (int) ValidarDato("int", "Ingrese el Nuevo ID del Producto:");
        System.out.println("Ingrese el Nuevo Nombre del Producto:");
        String nombre = sc.nextLine();
        int cantidad = (int) ValidarDato ("int", "Ingrese la Nueva Cantidad del Producto:");
        double precion = (double) ValidarDato ("double", "Ingrese el Nuevo Precio del Producto:");
        product.editarProducto(idBuscado, id, nombre, cantidad, precion);
        System.out.println("Producto editado con exito!");
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
