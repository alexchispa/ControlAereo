<script setup>
import { ref, onMounted } from 'vue';
import axios from 'axios';
import { useRouter } from 'vue-router';

const tipos = ref([]);
const avisos = ref([]); // Estado para los mensajes de aviso
const router = useRouter();
const showDeleteDialog = ref(false); // Estado para la ventana emergente de eliminación
const showErrorDialog = ref(false); // Estado para la ventana emergente de error
const tipoToDelete = ref(null); // ID del tipo a eliminar

const fetchTipos = async () => {
  try {
    const response = await axios.get('/api/tipos');
    tipos.value = response.data;
    checkAviso(); // Verificar si hay un mensaje de aviso en localStorage
  } catch (error) {
    console.error('Error fetching tipos:', error);
  }
};

const deleteTipo = async (id) => {
  try {
    // Verificar si el tipo tiene naves asociadas
    const tipo = tipos.value.find(t => t.id === id);
    if (tipo && tipo.naves.length > 0) {
      showErrorDialog.value = true; // Muestra la ventana emergente de error
      return;
    }
    
    tipoToDelete.value = id; // Establece el ID del tipo para eliminar
    showDeleteDialog.value = true; // Muestra la ventana emergente de eliminación
  } catch (error) {
    console.error('Error deleting tipo:', error);
  }
};

const confirmDelete = async () => {
  try {
    if (tipoToDelete.value) {
      await axios.delete(`/api/tipos/${tipoToDelete.value}`);
      fetchTipos(); // Refresca la lista después de la eliminación
      showAviso('Tipo eliminado exitosamente'); // Actualizar mensaje de aviso
      showDeleteDialog.value = false; // Oculta la ventana emergente
      tipoToDelete.value = null; // Limpia el ID del tipo a eliminar
    }
  } catch (error) {
    console.error('Error deleting tipo:', error);
  }
};

const cancelDelete = () => {
  showDeleteDialog.value = false; // Oculta la ventana emergente de eliminación
  tipoToDelete.value = null; // Limpia el ID del tipo a eliminar
};

const cancelError = () => {
  showErrorDialog.value = false; // Oculta la ventana emergente de error
};

const viewTipo = (id) => {
  router.push({ name: 'TipoFormulario', params: { id }, query: { readonly: true } });
};

const editTipo = (id) => {
  router.push({ name: 'TipoFormulario', params: { id }, query: { readonly: false } });
};

const createTipo = () => {
  router.push({ name: 'TipoFormulario' });
};

const showAviso = (mensaje) => {
  const id = Date.now();
  avisos.value.push({ id, mensaje });
  setTimeout(() => {
    avisos.value = avisos.value.filter(aviso => aviso.id !== id);
  }, 5000); // Ocultar el mensaje después de 5 segundos
};

const checkAviso = () => {
  const mensaje = localStorage.getItem('aviso');
  if (mensaje) {
    showAviso(mensaje);
    localStorage.removeItem('aviso'); // Eliminar el mensaje de localStorage después de mostrarlo
  }
};

onMounted(fetchTipos);
</script>

<template>
  <div class="tipo-container">
    <h1 class="title">Tipos</h1>
    <button @click="createTipo" class="create-button">Crear</button>
    <div v-for="aviso in avisos" :key="aviso.id" class="modal-aviso">{{ aviso.mensaje }}</div> <!-- Mostrar mensajes de aviso -->

    <div class="table-container">
      <table class="custom-table">
        <thead>
          <tr>
            <th class="nombre-col">Nombre</th>
            <th>Modelo</th>
            <th>Civil</th>
            <th>Aérea</th>
            <th>De Carga</th>
            <th>N. Naves</th> <!-- Cabecera de la columna con clase específica -->
            <th>Acciones</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="tipo in tipos" :key="tipo.id">
            <td class="truncate">{{ tipo.nombre }}</td>
            <td class="truncate">{{ tipo.modelo }}</td>
            <td>
              <span v-if="tipo.esCivil" class="icon-tick">✔️</span>
              <span v-else class="icon-cross">❌</span>
            </td>
            <td>
              <span v-if="tipo.esAerea" class="icon-tick">✔️</span>
              <span v-else class="icon-cross">❌</span>
            </td>
            <td>
              <span v-if="tipo.esDeCarga" class="icon-tick">✔️</span>
              <span v-else class="icon-cross">❌</span>
            </td>
            <td class="number-cell">{{ tipo.naves.length }}</td> <!-- Aplicar la clase específica -->
            <td class="action-buttons">
              <button @click="viewTipo(tipo.id)" class="action-button">Ver</button>
              <button @click="editTipo(tipo.id)" class="action-button">Editar</button>
              <button @click="deleteTipo(tipo.id)" class="action-button delete-button">Eliminar</button>
            </td>
          </tr>
        </tbody>
      </table>
    </div>

    <!-- Diálogo de eliminación -->
    <div v-if="showDeleteDialog" class="delete-dialog">
      <div class="delete-dialog-content">
        <p>¿Estás seguro de que deseas eliminar este tipo?</p>
        <div class="delete-dialog-buttons">
          <button @click="confirmDelete" class="confirm-button">Confirmar</button>
          <button @click="cancelDelete" class="cancel-button">Cancelar</button>
        </div>
      </div>
    </div>

    <!-- Diálogo de error -->
    <div v-if="showErrorDialog" class="error-dialog">
      <div class="error-dialog-content">
        <p>No se puede eliminar el tipo porque tiene naves asociadas.</p>
        <div class="error-dialog-buttons">
          <button @click="cancelError" class="error-button">Aceptar</button>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped>
/* Contenedor Principal */
.tipo-container {
  max-width: 1400px;
  margin: 40px auto; /* Ajuste en el margen superior */
  text-align: center;
  padding: 20px;
  background-color: #e2e8f0;
  border-radius: 15px;
  box-shadow: 0 6px 25px rgba(0, 0, 0, 0.3);
  color: #bbe1fa;
  word-wrap: break-word;
  white-space: pre-wrap;
  min-height: 80vh; /* Aumenta la altura mínima para ocupar más pantalla verticalmente */
}

/* Título */
.title {
  font-size: 2.5rem;
  color: #34495e;
  margin-bottom: 30px;
  font-weight: bold;
  text-transform: uppercase;
  letter-spacing: 2px;
}

/* Botón de Crear */
.create-button {
  margin-bottom: 20px;
  padding: 14px 24px;
  background-color: #3282b8;
  color: #1b262c;
  border: none;
  cursor: pointer;
  border-radius: 10px;
  font-size: 1.2rem;
  transition: background-color 0.3s ease, transform 0.2s ease, box-shadow 0.2s ease;
  box-shadow: 0 4px 12px rgba(48, 148, 214, 0.4);
}

.create-button:hover {
  background-color: #218838;
}

/* Mensajes de Aviso */
.modal-aviso {
  position: fixed;
  top: 85px; /* Ajusta este valor para mover los avisos más abajo */
  right: 20px; /* Cambiado a la esquina superior derecha */
  transform: none; /* Eliminado el translateX para evitar centrado */
  background-color: #28a745;
  color: white;
  padding: 10px 20px;
  border-radius: 5px;
  box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
  z-index: 1000;
  margin-top: 10px;
}

/* Tabla */
.custom-table {
  width: 100%;
  border-collapse: collapse;
  margin-top: 20px;
  background-color: #1c3b4d;
  box-shadow: 0 4px 15px rgba(0, 0, 0, 0.2);
}

th, td {
  padding: 16px;
  text-align: center;
  font-size: 1rem;
  border-bottom: 1px solid #34495e;
  color: #1d2122;
}

/* Encabezados de la tabla */
th {
  background-color: #3282b8;
  color: #ecf0f1;
  text-transform: uppercase;
  letter-spacing: 1px;
  position: sticky; /* Hacer la cabecera fija */
  top: 0; /* Posición fija en la parte superior */
  z-index: 1; /* Asegurar que la cabecera esté por encima del contenido */
}

/* Truncamiento de texto en celdas */
td {
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
  max-width: 300px;
}

/* Columnas específicas */
.nombre-col {
  max-width: 300px;
}

.capacidad-col {
  max-width: 100px;
}

.number-align {
  text-align: center; /* Centra el contenido de las celdas con números */
}

/* Contenedor de la tabla */
.table-container {
  max-height: 500px; /* Ajusta la altura máxima según sea necesario */
  overflow-y: auto; /* Añadir scroll vertical */
  position: relative; /* Necesario para la cabecera fija */
}

/* Filas de la tabla */
tbody tr {
  background-color: #f1f2f6;
  transition: background-color 0.2s ease;
}

tbody tr:hover {
  background-color: #c9d3dd;
}

.action-buttons {
  display: flex;
  justify-content: flex-end; /* Alinea los botones a la derecha */
  gap: 10px; /* Espacio entre los botones */
}

.action-button {
  padding: 10px 18px;
  background-color: #0f4c75;
  color: #ecf0f1;
  border: none;
  cursor: pointer;
  border-radius: 8px;
  font-size: 0.9rem;
  transition: background-color 0.3s ease, transform 0.2s ease, box-shadow 0.2s ease;
  box-shadow: 0 4px 12px rgba(15, 76, 117, 0.4);
}


.action-button:hover {
  background-color: #082d49;
  transform: translateY(-3px);
  box-shadow: 0 6px 14px rgba(15, 76, 117, 0.6);
}

.delete-button {
  background-color: #e63946;
}

.delete-button:hover {
  background-color: #c92a3b;
}

/* Aumento de especificidad para los iconos */
.table-container td .icon-tick {
  color: #28a745 !important;
  font-size: 1.5rem !important;
}

.table-container td .icon-cross {
  color: #251d91 !important;
  font-size: 1.5rem !important;
}

/* Diálogos */
.delete-dialog, .error-dialog {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background-color: rgba(0, 0, 0, 0.5);
  display: flex;
  justify-content: center;
  align-items: center;
  z-index: 1000; /* Aumentar el z-index para que se superponga a todos los elementos */
}

.delete-dialog-content, .error-dialog-content {
  background-color: #fff;
  padding: 20px;
  border-radius: 10px;
  box-shadow: 0 4px 10px rgba(0, 0, 0, 0.2);
  text-align: center;
}

.delete-dialog-buttons, .error-dialog-buttons {
  margin-top: 10px;
  display: flex;
  justify-content: space-around;
}

.confirm-button, .cancel-button, .error-button {
  padding: 10px 20px;
  border: none;
  border-radius: 5px;
  cursor: pointer;
  font-size: 1rem;
}

.confirm-button {
  background-color: #28a745;
  color: #fff;
}

.confirm-button:hover {
  background-color: #218838;
}

.cancel-button {
  background-color: #f0ad4e;
  color: #fff;
}

.cancel-button:hover {
  background-color: #ec971f;
}

.error-button {
  background-color: #d9534f;
  color: #fff;
}

.error-button:hover {
  background-color: #c9302c;
}

.number-cell {
  text-align: right;
  padding-right: 75px;
}
</style>
