<script setup>
import { reactive, onMounted, ref } from 'vue';
import axios from 'axios';
import { useRoute, useRouter } from 'vue-router';

// Estado del formulario
const form = reactive({
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
const isEditable = ref(false); // Estado para controlar si el formulario es editable

const fetchTipo = async () => {
  if (id) {
    try {
      const response = await axios.get(`/api/tipos/${id}`);
      Object.assign(form, response.data);
      isEditable.value = route.query.readonly !== 'true'; // Deshabilitar edición si readonly es true
    } catch (error) {
      console.error('Error fetching tipo:', error);
    }
  } else {
    isEditable.value = true; // Habilitar edición si no hay id (crear nuevo tipo)
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

const truncate = (text, length) => {
  return text.length > length ? text.substring(0, length) + '...' : text;
};

onMounted(fetchTipo);
</script>

<template>
  <div class="form-container">
    <h1>{{ id ? 'Modificar ' + form.nombre : 'Nuevo Tipo' }}</h1>

    <!-- Disposición con flexbox: el formulario a la izquierda, la lista de naves a la derecha -->
    <div class="form-and-naves-container">
      
      <!-- Formulario -->
      <form @submit.prevent="submitForm" class="form-left">
        <div class="form-group">
          <label for="nombre">Nombre</label>
          <input type="text" id="nombre" v-model="form.nombre" :readonly="!isEditable" required />
        </div>

        <div class="form-group">
          <label for="modelo">Modelo</label>
          <input type="text" id="modelo" v-model="form.modelo" :readonly="!isEditable" required />
        </div>

        <div class="form-group">
          <label for="fabricante">Fabricante</label>
          <input type="text" id="fabricante" v-model="form.fabricante" :readonly="!isEditable" required />
        </div>

        <div class="form-group">
          <label for="capacidad">Capacidad</label>
          <input type="number" id="capacidad" v-model="form.capacidad" :readonly="!isEditable" required />
        </div>

        <div class="form-group">
          <label for="esCivil">Es Civil</label>
          <input type="checkbox" id="esCivil" v-model="form.esCivil" :disabled="!isEditable" />
        </div>

        <div class="form-group">
          <label for="esAerea">Es Aérea</label>
          <input type="checkbox" id="esAerea" v-model="form.esAerea" :disabled="!isEditable" />
        </div>

        <div class="form-group">
          <label for="esDeCarga">Es de Carga</label>
          <input type="checkbox" id="esDeCarga" v-model="form.esDeCarga" :disabled="!isEditable" />
        </div>

        <div class="form-actions">
          <button type="submit" class="submit-button" v-if="isEditable">{{ id ? 'Guardar cambios' : 'Crear' }}</button>
          <button type="button" @click="cancel" class="cancel-button">Cancelar</button>
        </div>
      </form>

      <!-- Lista de Naves a la derecha con scroll -->
      <div class="naves-container" v-if="id">
        <h3>Naves Asociadas</h3>
        <ul class="naves-list">
          <li v-for="nave in form.naves" :key="nave.id">{{ truncate(nave.nombre, 50) }}</li>
        </ul>
      </div>

    </div>

    <div v-if="showModal" class="modal">
      <div class="modal-content">
        <span class="close" @click="showModal = false">&times;</span>
        <p>{{ modalMessage }}</p>
      </div>
    </div>
    
    <!-- Ventana Emergente de Confirmación -->
    <div v-if="showConfirmationDialog" class="confirmation-dialog">
      <div class="confirmation-dialog-content">
        <p>¿Estás seguro de que deseas actualizar el tipo de nave?</p>
        <div class="confirmation-dialog-buttons">
          <button @click="confirmSubmit" class="confirm-button">Aceptar</button>
          <button @click="cancelSubmit" class="cancel-button">Cancelar</button>
        </div>
      </div>
    </div>
  </div>
</template>


<style scoped>
.form-container {
  max-width: 800px;
  margin: 40px auto;
  padding: 20px;
  background-color: #e2e8f0;
  border-radius: 15px;
  box-shadow: 0 6px 25px rgba(0, 0, 0, 0.3);
  color: #34495e;
}

h1 {
  font-size: 2rem;
  margin-bottom: 20px;
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
input[type="number"],
textarea {
  width: 100%;
  padding: 10px;
  border: 1px solid #ccc;
  border-radius: 5px;
}

input[type="checkbox"] {
  margin-right: 10px;
}

.naves-list {
  list-style-type: none;
  padding: 0;
}

.naves-list li {
  background-color: #f1f2f6;
  padding: 10px;
  margin-bottom: 5px;
  border-radius: 5px;
}

.form-actions {
  display: flex;
  justify-content: space-between;
}

.submit-button,
.cancel-button {
  padding: 10px 20px;
  border: none;
  border-radius: 5px;
  cursor: pointer;
  font-size: 1rem;
}

.submit-button {
  background-color: #3282b8;
  color: white;
}

.submit-button:hover {
  background-color: #218838;
}

.cancel-button {
  background-color: #6c757d;
  color: white;
}

.cancel-button:hover {
  background-color: #5a6268;
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
  z-index: 1000;
}

.confirmation-dialog-content {
  background: white;
  padding: 20px;
  border-radius: 10px;
  text-align: center;
}

.confirmation-dialog-buttons {
  display: flex;
  justify-content: space-around;
  margin-top: 20px;
}

.confirm-button,
.cancel-button {
  padding: 10px 20px;
  border: none;
  border-radius: 5px;
  cursor: pointer;
  font-size: 1rem;
}

.confirm-button {
  background-color: #c0392b;
  color: white;
}

.confirm-button:hover {
  background-color: #e74c3c;
}

.cancel-button {
  background-color: #3498db;
  color: white;
}

.cancel-button:hover {
  background-color: #2980b9;
}
</style>