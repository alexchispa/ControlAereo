<script setup>
import { reactive, onMounted, ref } from 'vue';
import axios from 'axios';
import { useRoute, useRouter } from 'vue-router';

// Estado del formulario
const form = reactive({
  id: null,
  nombre: '',
  modelo: '',
  fabricante: '',
  capacidad: '',
  esCivil: false,
  esAerea: false,
  esDeCarga: false,
  naves: []
});

// Estado para la ventana emergente
const showConfirmationDialog = ref(false);
const isConfirming = ref(false);

const route = useRoute();
const router = useRouter();
const id = route.params.id;

const showModal = ref(false); // Estado para controlar la visibilidad del modal
const modalMessage = ref(''); // Mensaje del modal
const readonly = ref(route.query.readonly === 'true'); // Cambiar a ref


const fetchTipo = async () => {
  if (id) {
    try {
      const response = await axios.get(`/api/tipos/${id}`);
      // Verificar que la respuesta sea un objeto válido antes de asignarlo
      if (response.data && typeof response.data === 'object') {
        Object.assign(form, response.data);
        readonly.value = route.query.readonly === 'true'; // Deshabilitar edición si readonly es true
      } else {
        console.error('Respuesta no válida recibida:', response.data);
        modalMessage.value = 'Error: los datos recibidos no son válidos.';
        showModal.value = true; // Mostrar un mensaje de error en un modal
      }
    } catch (error) {
      console.error('Error fetching tipo:', error);
      modalMessage.value = 'Error al obtener los datos del tipo.';
      showModal.value = true;
    }
  } else {
    readonly.value = false; // Permitir edición si no hay ID (crear nuevo tipo)
  }
};


const validateForm = () => {
  if (form.nombre.length > 200) {
    modalMessage.value = 'El nombre no puede exceder los 200 caracteres.';
    showModal.value = true;
    return false;
  }
  if (form.modelo.length > 200) {
    modalMessage.value = 'El modelo no puede exceder los 200 caracteres.';
    showModal.value = true;
    return false;
  }
  if (form.fabricante.length > 200) {
    modalMessage.value = 'El fabricante no puede exceder los 200 caracteres.';
    showModal.value = true;
    return false;
  }
  if (form.capacidad == null || form.capacidad < 0) {
    modalMessage.value = 'La capacidad debe ser un número positivo.';
    showModal.value = true;
    return false;
  }
  return true;
};

const submitForm = async () => {
  if (!validateForm()) return;

  if (id) {
    showConfirmationDialog.value = true;
  } else {
    await createTipo();
  }
};

const createTipo = async () => {
  try {
    await axios.post('/api/tipos', form);
    console.log('Tipo creado:', form);
    localStorage.setItem('aviso', 'Tipo creado exitosamente'); // Guardar mensaje de aviso
    router.push('/tipo'); // Redirige a la lista de tipos después de enviar el formulario
  } catch (error) {
    console.error('Error creating tipo:', error);
    if (error.response && error.response.data) {
      modalMessage.value = `Error: ${error.response.data.message}`;
    } else {
      modalMessage.value = 'Error al crear el tipo.';
    }
    showModal.value = true;
  }
};


const confirmSubmit = async () => {
  showConfirmationDialog.value = false;
  isConfirming.value = true;
  try {
    await axios.put(`/api/tipos/${id}`, form);
    console.log('Tipo actualizado:', form);
    localStorage.setItem('aviso', 'Tipo actualizado exitosamente'); // Guardar mensaje de aviso
    router.push('/tipo'); // Redirige a la lista de tipos después de enviar el formulario
  } catch (error) {
    console.error('Error updating tipo:', error);
  } finally {
    isConfirming.value = false;
  }
};

const cancelSubmit = () => {
  showConfirmationDialog.value = false;
};

const cancel = () => {
  router.push('/tipo');
};

// Función para truncar texto largo
const truncate = (text, length) => {
  return text.length > length ? text.substring(0, length) + '...' : text;
};

onMounted(fetchTipo);
</script>


<template>
  <div class="container">
    <div class="form-section">
      <h1>{{ id ? form.nombre : 'Nuevo Tipo' }}</h1>
      <form @submit.prevent="submitForm">
        <!-- Campos del formulario -->
        <div class="form-group">
          <label for="nombre">Nombre</label>
          <!-- Agregar atributo name -->
          <input type="text" id="nombre" name="nombre" v-model="form.nombre" :readonly="readonly" required />
        </div>

        <div class="form-group">
          <label for="modelo">Modelo</label>
          <!-- Agregar atributo name -->
          <input type="text" id="modelo" name="modelo" v-model="form.modelo" :readonly="readonly" required />
        </div>

        <div class="form-group">
          <label for="fabricante">Fabricante</label>
          <!-- Agregar atributo name -->
          <input type="text" id="fabricante" name="fabricante" v-model="form.fabricante" :readonly="readonly" required />
        </div>

        <div class="form-group">
          <label for="capacidad">Capacidad</label>
          <!-- Agregar atributo name -->
          <input type="number" id="capacidad" name="capacidad" v-model="form.capacidad" :readonly="readonly" required />
        </div>

        <div class="form-group">
          <label for="esCivil">Es Civil</label>
          <!-- Agregar atributo name -->
          <input type="checkbox" id="esCivil" name="esCivil" v-model="form.esCivil" :disabled="readonly" />
        </div>

        <div class="form-group">
          <label for="esAerea">Es Aérea</label>
          <!-- Agregar atributo name -->
          <input type="checkbox" id="esAerea" name="esAerea" v-model="form.esAerea" :disabled="readonly" />
        </div>

        <div class="form-group">
          <label for="esDeCarga">Es de Carga</label>
          <!-- Agregar atributo name -->
          <input type="checkbox" id="esDeCarga" name="esDeCarga" v-model="form.esDeCarga" :disabled="readonly" />
        </div>

        <!-- Botones "Guardar/Crear" y "Cancelar" -->
        <div 
          class="form-actions" 
          :class="{ 'single-button': readonly }" 
        >
          <!-- Botón "Crear/Modificar" solo se muestra si no es readonly -->
          <button v-if="!readonly" type="submit" class="submit-button">
            {{ id ? 'Guardar' : 'Crear' }}
          </button>
          <!-- Botón "Cancelar" siempre está presente -->
          <button type="button" @click="cancel" class="cancel-button">Cancelar</button>
        </div>
      </form>
    </div>

    <!-- Mostrar siempre la lista de naves asociadas -->
    <div class="naves-section">
      <h2>Naves Asociadas</h2>
      <div class="naves-list-container">
        <ul class="naves-list" v-if="form.naves.length > 0">
          <li v-for="nave in form.naves" :key="nave.id">{{ truncate(nave.nombre, 50) }}</li>
        </ul>
        <p class="message-adjust" v-else>¡Todavía no tiene ninguna nave asociada!</p>
      </div>
    </div>

    <!-- Modal de confirmación -->
    <div v-if="showConfirmationDialog" class="confirmation-dialog">
      <div class="confirmation-dialog-content">
        <p>¿Estás seguro de que deseas actualizar el tipo de nave?</p>
        <div class="confirmation-dialog-buttons">
          <button @click="confirmSubmit" class="submit-button">Aceptar</button>
          <button @click="cancelSubmit" class="cancel-button">Cancelar</button>
        </div>
      </div>
    </div>

    <!-- Modal de error -->
    <div v-if="showModal" class="modal">
      <div class="modal-content">
        <span class="close" @click="showModal = false">&times;</span>
        <p>{{ modalMessage }}</p>
      </div>
    </div>
  </div>
</template>



<style scoped>
input[type=number] {
  -moz-appearance: textfield; /* Firefox */
}

.container {
  display: flex;
  justify-content: space-between;
  gap: 20px;
  margin: 40px auto;
  max-width: 1200px;
  padding: 20px;
}

.form-section, .naves-section {
  flex: 1;
  background-color: #e2e8f0;
  border-radius: 15px;
  padding: 20px;
  box-shadow: 0 6px 25px rgba(0, 0, 0, 0.3);
  color: #34495e;
  overflow: hidden;
}

.naves-section {
  max-height: 600px;
  overflow-y: auto;
}

h1 {
  font-size: 2rem;
  margin-bottom: 20px;
  text-align: center;
}

h2 {
  font-size: 1.5rem;
  margin-bottom: 20px;
  padding-top: 20px;
  text-align: center;
}

.form-group {
  margin-bottom: 20px;
}

label {
  display: block;
  margin-bottom: 5px;
  font-weight: bold;
}

input[type="text"],
input[type="number"] {
  width: calc(100% - 22px); /* Ajustar el ancho con margen y borde */
  padding: 10px;
  border: 1px solid #ccc;
  border-radius: 5px;
}

input[type="checkbox"] {
  margin-right: 10px;
}

.naves-list-container {
  max-height: 500px;
  overflow-y: auto;
}

.naves-list {
  list-style-type: none;
  padding: 0;
  margin: 0;
}

.naves-list li {
  background-color: #f1f2f6;
  padding: 10px;
  margin-bottom: 5px;
  border-radius: 5px;
}

/* Botones */
.form-actions {
  display: flex;
  justify-content: space-between;
}

.form-actions.single-button {
  justify-content: flex-end; /* Solo el botón cancelar debe estar alineado a la derecha */
}

  
button {
  padding: 12px 20px;
  border: none;
  cursor: pointer;
  border-radius: 8px;
  font-size: 1rem;
  transition: background-color 0.3s ease, transform 0.2s ease;
}

/* Botón de enviar */
.submit-button {
  background-color: #48bb78;
  color: white;
  box-shadow: 0 4px 10px rgba(72, 187, 120, 0.4);
}

.submit-button:hover {
  background-color: #38a169;
  transform: translateY(-3px);
  box-shadow: 0 6px 14px rgba(72, 187, 120, 0.6);
}

.cancel-button {
  background-color: #e53e3e;
  color: white;
  box-shadow: 0 4px 10px rgba(229, 62, 62, 0.4);
}

.cancel-button:hover {
  background-color: #c53030;
  transform: translateY(-3px);
  box-shadow: 0 6px 14px rgba(229, 62, 62, 0.6);
}


.modal {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background: rgba(0, 0, 0, 0.5);
  display: flex;
  justify-content: center;
  align-items: center;
  z-index: 1000;
}

.modal-content {
  background: white;
  padding: 20px;
  border-radius: 10px;
  text-align: center;
}

.close {
  position: absolute;
  top: 10px;
  right: 10px;
  cursor: pointer;
}

.message-adjust {
  text-align: center;
  font-weight: bold;

}

.confirmation-dialog {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background: rgba(0, 0, 0, 0.5);
  display: flex;
  justify-content: center;
  align-items: center;
}

.confirmation-dialog-content {
  background: #1b262c;
  padding: 20px;
  border-radius: 15px;
  text-align: center;
  box-shadow: 0 4px 15px rgba(0, 0, 0, 0.2);
  color: #ecf0f1;
  max-width: 400px; /* Limitar ancho del diálogo */
  margin: auto; /* Centrar horizontalmente */
}

.confirmation-dialog-buttons {
  display: flex;
  justify-content: space-between;
  margin-top: 20px;
}


</style>
