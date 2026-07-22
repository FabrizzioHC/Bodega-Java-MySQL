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

    // OBTENER TODOS (GET)
    @GetMapping
    public List<producto> listar() {
        return dao.obtenerTodos();
    }

    // CREAR PRODUCTO (POST)
    @PostMapping
    public String guardar(@RequestBody producto p) {
        dao.agregarProducto(p);
        return "¡Producto guardado exitosamente!";
    }

    // BUSCAR POR NOMBRE (GET con @RequestParam)
    @GetMapping("/buscar")
    public List<producto> buscar(@RequestParam String nombre) {
        return dao.buscarProducto(nombre);
    }

    //EDITAR PRODUCTO (PUT con @PathVariable + @RequestBody)
    @PutMapping("/{id}")
    public void editar(@PathVariable int idBuscado, @RequestBody producto p) {
        dao.editarProducto(idBuscado, p);
    }

    //ELIMINAR PRODUCTO (DELETE con @PathVariable)
    @DeleteMapping("/{id}")
    public void editar(@PathVariable int idSeleccionado) {
        dao.eliminarProducto(idSeleccionado);
    }
}