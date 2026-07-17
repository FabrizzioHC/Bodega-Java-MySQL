package modelo;

public class producto {
    
    private int id;
    private String nombre;
    private int cantidad;
    private double precio;

    public producto(){

    }

    public producto(int id, String nombre, int cantidad, double precio) {
        this.id = id;
        this.nombre = nombre;
        this.cantidad = cantidad;
        this.precio = precio;
    }

    public int getId(){
        return id;
    }

    public void setId(int id){
        this.id = id;
    }

    public String getNombre(){
        return nombre;
    }

    public void setNombre(String nombre){
        if(nombre == null || nombre.trim().isEmpty()){
            throw new IllegalArgumentException("El titulo del Libro no puede estar vacio!!");
        }
        this.nombre = nombre;
    }

    public int getCantidad (){
        return cantidad;
    }

    public void setCantidad(int cantidad){
        if(cantidad < 0){
            throw new IllegalArgumentException("La cantidad del producto no puede ser nula o negativa!!");
        }
        this.cantidad = cantidad;
    }

    public double getPrecio(){
        return precio;
    }

    public void setPrecio(double precio){
        if(precio < 0){
            throw new IllegalArgumentException("El precio del producto no puede ser negativo!!");
        }
        this.precio = precio;
    }
}
