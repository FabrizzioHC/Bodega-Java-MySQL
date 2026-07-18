package modelo;
import java.time.LocalDate;

public class producto {
    
    private String nombre;
    private int cantidad;
    private double precio;
    private LocalDate fechaVencimiento;

    public producto(){

    }

    public producto(String nombre, int cantidad, double precio, LocalDate fechaVencimiento) {

        setNombre(nombre);
        setCantidad(cantidad);
        setPrecio(precio);
        setFechaVencimiento(fechaVencimiento);
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

    public LocalDate getFechaVencimiento(){
        return fechaVencimiento;
    }

    public void setFechaVencimiento(LocalDate fechaVencimiento){
        if(fechaVencimiento.isBefore(LocalDate.now())){
            throw new IllegalArgumentException("La fecha de vencimiento no puede ser anterior a la fecha actual!!");
        }
        this.fechaVencimiento = fechaVencimiento;
    }
}
