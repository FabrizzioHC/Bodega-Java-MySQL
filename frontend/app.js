// URL base de tu API
const API_URL = "http://localhost:8080/api/productos";

// Elementos del DOM
const tablaProductos = document.getElementById("tabla-productos");
const productoForm = document.getElementById("producto-form");
const inputNombre = document.getElementById("nombre");
const inputCantidad = document.getElementById("cantidad");
const inputPrecio = document.getElementById("precio");
const inputVencimiento = document.getElementById("vencimiento");
//elementos para editar
const modalEditar = document.getElementById("modal-editar");
const formEditar = document.getElementById("form-editar");
const editId = document.getElementById("edit-id");
const editNombre = document.getElementById("edit-nombre");
const editCantidad = document.getElementById("edit-cantidad");
const editPrecio = document.getElementById("edit-precio");
const editVencimiento = document.getElementById("edit-vencimiento");

// CARGAR PRODUCTOS DESDE LA API REAL (GET)
async function cargarProductos(terminoBusqueda = "") {

    if (typeof terminoBusqueda !== "string") {
        terminoBusqueda = "";
    }

    try {

        // Por defecto pide todos los productos
        let url = API_URL;

        // Si el usuario escribió algo en el buscador, cambiamos la URL a la ruta /buscar
        if (terminoBusqueda.trim() !== "") {
            url = `${API_URL}/buscar?nombre=${encodeURIComponent(terminoBusqueda)}`;
        }

        // Hacemos la petición a Java
        const respuesta = await fetch(url);
        const productos = await respuesta.json();

        // Limpiamos la tabla
        tablaProductos.innerHTML = "";

        if (productos.length === 0) { // cuando no hay productos en la base de datos
            tablaProductos.innerHTML = `
                <tr>
                    <td colspan="6" class="text-center text-muted py-4">
                        No hay productos en la base de datos.
                    </td>
                </tr>`;
            return;
        }


        // Pintamos los datos reales que llegaron de MySQL
        productos.forEach((p) => { 
            const tr = document.createElement("tr");
            tr.innerHTML = `
                <td>${p.id}</td>
                <td class="fw-semibold">${p.nombre}</td>
                <td>${p.cantidad}</td>
                <td>S/. ${Number(p.precio).toFixed(2)}</td>
                <td>${p.fechaVencimiento}</td>
                <td>
                    <button class="btn btn-action-custom edit me-1" 
                            onclick="prepararEdicion(${p.id}, '${p.nombre}', ${p.cantidad}, ${p.precio}, '${p.fechaVencimiento}')">
                        ✏️
                    </button>
                    <button class="btn btn-action-custom delete" 
                            onclick="eliminarProducto(${p.id})">
                        ❌
                    </button>
                </td>
            `;
            tablaProductos.appendChild(tr);
        });

    } catch (error) {
        console.error("Error al conectar con la API:", error);
        tablaProductos.innerHTML = `
            <tr>
                <td colspan="6" class="text-center text-danger py-4">
                    No se pudo conectar con el servidor Java (Spring Boot).
                </td>
            </tr>`;
    }
}

// ENVIAR UN NUEVO PRODUCTO A LA API REAL (POST) ==== AGREGAR
productoForm.addEventListener("submit", async (e) => {
    e.preventDefault();

    // Armamos el objeto con la misma estructura del JSON que espera Java
    const nuevoProducto = {
        nombre: inputNombre.value.trim(),
        cantidad: parseInt(inputCantidad.value),
        precio: parseFloat(inputPrecio.value),
        fechaVencimiento: inputVencimiento.value
    };

    try {
        // Enviamos la petición POST con el JSON en el cuerpo
        const respuesta = await fetch(API_URL, {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify(nuevoProducto)
        });

        if (respuesta.ok) {
            productoForm.reset();
            // Recargamos la tabla pidiéndole los datos frescos a MySQL
            cargarProductos(); 
        } else {
            alert("Error al guardar el producto.");
        }

    } catch (error) {
        console.error("Error al guardar:", error);
    }
});

// Seleccionamos el campo de texto del buscador
const inputBuscar = document.querySelector("input[placeholder*='Buscar']");

// mira si hay texto en la caja de busqueda
if (inputBuscar) {
    inputBuscar.addEventListener("input", (e) => {
        // agarramos el texto dentro de la caja de busqueda
        cargarProductos(e.target.value);
    });
}


//======================== EDITAR =============================

// 1B. Al hacer clic en ✏️, carga los datos en los inputs del modal y lo MUESTRA
function prepararEdicion(id, nombre, cantidad, precio, fechaVencimiento) {
    editId.value = id;
    editNombre.value = nombre;
    editCantidad.value = cantidad;
    editPrecio.value = precio;
    editVencimiento.value = fechaVencimiento;

    // Quitamos "d-none" para mostrar el modal en pantalla
    modalEditar.classList.remove("d-none");
}

// 2B. Función para ocultar el modal (al dar clic en Cancelar)
function cerrarModalEditar() {
    modalEditar.classList.add("d-none");
}

// 3B. Evento Submit del formulario del Modal -> Envía el PUT a Spring Boot
formEditar.addEventListener("submit", async (e) => {
    e.preventDefault();

    const id = editId.value;
    const datosEditados = {
        nombre: editNombre.value.trim(),
        cantidad: parseInt(editCantidad.value),
        precio: parseFloat(editPrecio.value),
        fechaVencimiento: editVencimiento.value
    };

    try {
        const respuesta = await fetch(`${API_URL}/${id}`, {
            method: "PUT",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify(datosEditados)
        });

        if (respuesta.ok) {
            cerrarModalEditar(); // Cerramos la tarjeta emergente
            cargarProductos();   // Actualizamos la tabla
        } else {
            alert("No se pudo actualizar el producto.");
        }
    } catch (error) {
        console.error("Error al actualizar:", error);
    }
});

//============================================================

//======================== ELIMINAR (DELETE) =============================

async function eliminarProducto(id) {
    // Por si quieres cancelar al intentar eliminar un producto
    if (confirm(`¿Estás seguro de que deseas eliminar el producto con ID ${id}?`)) {
        try {
            // 2. Petición DELETE a la URL
            const respuesta = await fetch(`${API_URL}/${id}`, {
                method: "DELETE"
            });

            if (respuesta.ok) {
                //Recargamos la tabla
                cargarProductos();
            } else {
                alert("No se pudo eliminar el producto de la base de datos.");
            }
        } catch (error) {
            console.error("Error al conectar con la API para eliminar:", error);
        }
    }
}
//============================================================

// Cargar la lista apenas abra la página
document.addEventListener("DOMContentLoaded", cargarProductos);