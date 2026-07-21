package com.bodega.backend.controller;

import com.bodega.backend.dao.productoDAO;
import com.bodega.backend.modelo.producto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/productos")
@CrossOrigin(origins = "*") // Permite que tu Frontend (index.html) se conecte sin bloqueos de seguridad
public class ProductoController {

    @Autowired
    private productoDAO dao;

    // Ruta 1: GET http://localhost:8080/api/productos (Devuelve la lista de productos en JSON)
    @GetMapping
    public List<producto> listar() {
        return dao.obtenerTodos();
    }

    // Ruta 2: POST http://localhost:8080/api/productos (Recibe un JSON y guarda el producto)
    @PostMapping
    public String guardar(@RequestBody producto p) {
        dao.agregarProducto(p);
        return "¡Producto guardado exitosamente!";
    }
}