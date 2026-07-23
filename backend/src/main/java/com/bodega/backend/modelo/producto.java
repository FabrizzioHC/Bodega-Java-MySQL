package com.bodega.backend.modelo;
import java.time.LocalDate;

public class producto {
    
    private Integer id;
    private String nombre;
    private int cantidad;
    private double precio;
    private LocalDate fechaVencimiento;

    public producto(){

    }

    public producto(Integer id, String nombre, int cantidad, double precio, LocalDate fechaVencimiento) {

        this.id = id;
        setNombre(nombre);
        setCantidad(cantidad);
        setPrecio(precio);
        setFechaVencimiento(fechaVencimiento);
    }

    public producto(String nombre, int cantidad, double precio, LocalDate fechaVencimiento) {

        setNombre(nombre);
        setCantidad(cantidad);
        setPrecio(precio);
        setFechaVencimiento(fechaVencimiento);
    }



    public Integer getId(){
        return id;
    }

    public void setId(Integer id){
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

    public LocalDate getFechaVencimiento(){
        return fechaVencimiento;
    }

    public void setFechaVencimiento(LocalDate fechaVencimiento){
        this.fechaVencimiento = fechaVencimiento;
    }
}
