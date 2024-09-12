<script setup>
import { ref, onMounted } from 'vue';
import axios from 'axios';
import { useRouter } from 'vue-router';

const naves = ref([]);
const avisos = ref([]); // Estado para los mensajes de aviso
const router = useRouter();
const showDeleteDialog = ref(false); // Estado para la ventana emergente de eliminación
const naveToDelete = ref(null); // ID de la nave a eliminar

const fetchNaves = async () => {
  try {
    const response = await axios.get('/api/naves');
    naves.value = response.data;
    checkAviso(); // Verificar si hay un mensaje de aviso en localStorage
  } catch (error) {
    console.error('Error fetching naves:', error);
  }
};

const deleteNave = async (id) => {
  naveToDelete.value = id; // Establece el ID de la nave para eliminar
  showDeleteDialog.value = true; // Muestra la ventana emergente
};

const confirmDelete = async () => {
  try {
    if (naveToDelete.value) {
      await axios.delete(`/api/naves/${naveToDelete.value}`);
      fetchNaves(); // Refresca la lista después de la eliminación
      showAviso('Nave borrada exitosamente'); // Actualizar mensaje de aviso
      showDeleteDialog.value = false; // Oculta la ventana emergente
      naveToDelete.value = null; // Limpia el ID de la nave a eliminar
    }
  } catch (error) {
    console.error('Error deleting nave:', error);
  }
};

const cancelDelete = () => {
  showDeleteDialog.value = false; // Oculta la ventana emergente
  naveToDelete.value = null; // Limpia el ID de la nave a eliminar
};

const viewNave = async (id) => {
  router.push({ name: 'NaveFormulario', params: { id }, query: { readonly: true } });
};

const updateNave = async (id) => {
  router.push({ name: 'NaveFormulario', params: { id }, query: { readonly: false } });
};

const submitForm = async () => {
  if (!validateForm()) return;

  console.log('Formulario antes de enviar:', form); // Añadir esta línea

  if (id) {
    showConfirmationDialog.value = true;
  } else {
    await createNave();
  }
};

const createNave = () => {
  router.push({ name: 'NaveFormulario' });
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

onMounted(fetchNaves);

</script>

<template>
  <div class="nave-container">
    <h1 class="title">Naves</h1>
    <button @click="createNave" class="create-button">Crear</button>
    <div v-for="aviso in avisos" :key="aviso.id" class="modal-aviso">{{ aviso.mensaje }}</div> <!-- Mostrar mensajes de aviso -->
    <div class="table-container">
      <table class="custom-table">
        <thead>
          <tr>
            <th class="nombre-col">Nombre</th> <!-- Añadido nombre-col -->
            <th>Piloto</th>
            <th>Base</th>
            <th>Carga(Kg)</th>
            <th class="capacidad-col">Capacidad (Pers.)</th>
            <th class="collapse-tip">Tipo</th>
            <th>Acciones</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="nave in naves" :key="nave.id">
            <td class="truncate nombre-col">{{ nave.nombre }}</td> <!-- Añadido nombre-col -->
            <td class="truncate">{{ nave.piloto }}</td>
            <td class="truncate">{{ nave.base }}</td>
            <td class="truncate number-align">{{ nave.carga }}</td>
            <td class="truncate number-align">{{ nave.capacidadPersonas }}</td>
            <td class="truncate collapse-tip" v-if="nave.tipo">{{ nave.tipo.nombre }}</td>
            <td class="truncate" v-else></td>
            <td class="action-buttons">
              <button @click="viewNave(nave.id)" class="action-button">Ver</button>
              <button @click="updateNave(nave.id)" class="action-button">Editar</button>
              <button @click="deleteNave(nave.id)" class="action-button delete-button">Borrar</button>
            </td>
          </tr>
        </tbody>
      </table>
    </div>

    <!-- Ventana emergente de confirmación de eliminación -->
    <div v-if="showDeleteDialog" class="delete-dialog">
      <div class="delete-dialog-content">
        <p>¿Estás seguro de que deseas borrar esta nave?</p>
        <div class="delete-dialog-buttons">
          <button @click="confirmDelete" class="confirm-button">Confirmar</button>
          <button @click="cancelDelete" class="cancel-button">Cancelar</button>
        </div>
      </div>
    </div>
  </div>
</template>



<style scoped>
.nave-container {
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

.title {
  font-size: 2.5rem;
  color: #34495e;
  margin-bottom: 30px;
  font-weight: bold;
  text-transform: uppercase;
  letter-spacing: 2px;
}

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

.table-container {
  max-height: 500px; /* Ajusta la altura máxima según sea necesario */
  overflow-y: auto; /* Añadir scroll vertical */
  overflow-x: auto; /* Añadir scroll horizontal */
  position: relative; /* Necesario para la cabecera fija */
}

.custom-table {
  width: 100%;
  border-collapse: collapse;
  margin-top: 20px;
  background-color: #dfe6e9;
  box-shadow: 0 4px 15px rgba(0, 0, 0, 0.2);
}

th, td {
  padding: 16px;
  font-size: 1rem;
  border-bottom: 1px solid #34495e;
  color: #1d2122;
}

th {
  background-color: #3282b8;
  color: #ecf0f1;
  text-transform: uppercase;
  letter-spacing: 1px;
  position: sticky; /* Hacer la cabecera fija */
  top: 0; /* Posición fija en la parte superior */
  z-index: 1; /* Asegurar que la cabecera esté por encima del contenido */
}

tbody tr {
  background-color: #f1f2f6;
  transition: background-color 0.2s ease;
}

td {
  word-wrap: break-word; /* Asegura que las palabras largas se rompan */
  white-space: nowrap; /* Evita el ajuste de línea dentro de las celdas */
  overflow: hidden; /* Oculta el contenido que desborda */
  text-overflow: ellipsis; /* Muestra puntos suspensivos cuando el texto es demasiado largo */
  position: relative; /* Necesario para el tooltip */
}

.nombre-col {
  max-width: 200px; /* Limita el ancho de la columna de nombre */
  overflow: hidden;
  text-overflow: ellipsis;
}

.collapse-tip {
  max-width: 200px; /* Limita el ancho de la columna de nombre */
  overflow: hidden;
  text-overflow: ellipsis;
}

.capacidad-col {
  max-width: 100px; /* Ajusta el ancho de la columna de capacidad */
}

.number-align {
  text-align: right;
  padding-right: 75px;
 
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

.delete-dialog {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background: rgba(0, 0, 0, 0.5);
  display: flex;
  justify-content: center;
  align-items: center;
  z-index: 1000; /* Aumentar el z-index para que se superponga a todos los elementos */
}

.delete-dialog-content {
  background: #1b262c;
  padding: 20px;
  border-radius: 15px;
  text-align: center;
  box-shadow: 0 4px 15px rgba(0, 0, 0, 0.2);
  color: #ecf0f1;
  max-width: 400px; /* Limitar ancho del diálogo */
  margin: auto; /* Centrar horizontalmente */
  margin-top: 15%; /* Alinearlo más arriba en lugar de centrado vertical */
}

.delete-dialog-buttons {
  margin-top: 20px;
  display: flex;
  justify-content: space-around;
}

.confirm-button {
  padding: 10px 18px;
  background-color: #e63946;
  color: white;
  border: none;
  cursor: pointer;
  border-radius: 8px;
  font-size: 1rem;
  transition: background-color 0.3s ease, transform 0.2s ease, box-shadow 0.2s ease;
}

.confirm-button:hover {
  background-color: #c92a3b;
}

.cancel-button {
  padding: 10px 18px;
  background-color: #6c757d;
  color: white;
  border: none;
  cursor: pointer;
  border-radius: 8px;
  font-size: 1rem;
  transition: background-color 0.3s ease, transform 0.2s ease, box-shadow 0.2s ease;
}

.cancel-button:hover {
  background-color: #5a6268;
}
</style>
