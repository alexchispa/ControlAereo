<script setup>
import { reactive, onMounted, ref } from 'vue';
import axios from 'axios';
import { useRoute, useRouter } from 'vue-router';

// Estado del formulario
const form = reactive({
  nombre: '',
  piloto: '',
  colores: '',
  base: '',
  modificaciones: '',
  anyoFabricacion: '',
  peso: '',
  longitud: '',
  bodega: '',
  carga: '',
  capacidadPersonas: '',
  tipo: null // Debe ser un objeto de tipo
});

// Estado para la ventana emergente
const showConfirmationDialog = ref(false);
const isConfirming = ref(false);

const tipos = reactive([]); // Array para almacenar los tipos disponibles
const showModal = ref(false); // Estado para controlar la visibilidad del modal
const modalMessage = ref(''); // Mensaje del modal

const route = useRoute();
const router = useRouter();
const id = route.params.id;
const readonly = route.query.readonly === 'true'; // Obtener el parámetro readonly de la URL

const fetchNave = async () => {
  if (id) {
    try {
      const response = await axios.get(`/api/naves/${id}`);
      Object.assign(form, response.data);
      
      // Espera a que los tipos estén cargados antes de asignar el tipo
      await fetchTipos();

      // Asigna el id del tipo al form.tipo
      form.tipo = response.data.tipo ? response.data.tipo.id : null;
    } catch (error) {
      console.error('Error fetching nave:', error);
    }
  }
};


const fetchTipos = async () => {
  try {
    const response = await axios.get('/api/tipos');
    tipos.length = 0; // Limpiar el array antes de llenarlo
    tipos.push(...response.data);
  } catch (error) {
    console.error('Error fetching tipos:', error);
  }
};


const validateForm = () => {
  if (form.nombre.length > 200) {
    modalMessage.value = 'El nombre no puede exceder los 200 caracteres.';
    showModal.value = true;
    return false;
  }
  if (form.piloto.length > 200) {
    modalMessage.value = 'El piloto no puede exceder los 200 caracteres.';
    showModal.value = true;
    return false;
  }
  if (form.colores.length > 200) {
    modalMessage.value = 'Los colores no pueden exceder los 200 caracteres.';
    showModal.value = true;
    return false;
  }
  if (form.base.length > 200) {
    modalMessage.value = 'La base no puede exceder los 200 caracteres.';
    showModal.value = true;
    return false;
  }
  if (form.modificaciones.length > 500) {
    modalMessage.value = 'Las modificaciones no pueden exceder los 500 caracteres.';
    showModal.value = true;
    return false;
  }
  return true;
};

const submitForm = async () => {
  if (!validateForm()) return;

  // Verifica si form.tipo es null y maneja el caso
  if (form.tipo === null) {
    form.tipo = { id: null }; // Asegúrate de que tenga un valor predeterminado si es null
  }

  if (id) {
    showConfirmationDialog.value = true;
  } else {
    await createNave();
  }
};

const createNave = async () => {
  try {
    await axios.post('/api/naves', form, {
      headers: {
        'Content-Type': 'application/json'
      }
    });
    console.log('Nave creada:', form);
    localStorage.setItem('aviso', 'Nave creada exitosamente');
    router.push('/nave');
  } catch (error) {
    console.error('Error creating nave:', error);
  }
};


const confirmSubmit = async () => {
  showConfirmationDialog.value = false;
  isConfirming.value = true;
  try {
    await axios.put(`/api/naves/${id}`, form, {
      headers: {
        'Content-Type': 'application/json'
      }
    });
    console.log('Nave actualizada:', form);
    localStorage.setItem('aviso', 'Nave actualizada exitosamente'); // Guardar mensaje de aviso
    router.push('/nave'); // Redirige a la lista de naves después de enviar el formulario
  } catch (error) {
    console.error('Error updating nave:', error);
  } finally {
    isConfirming.value = false;
  }
};

const cancelSubmit = () => {
  showConfirmationDialog.value = false;
};

const cancel = () => {
  router.push('/nave');
};

onMounted(() => {
  fetchTipos(); // Llama a fetchTipos cuando el componente se monta
  fetchNave(); // Llama a fetchNave después de fetchTipos
});
</script>

<template>
  <div class="form-container">
    <h1>{{ id ? form.nombre : 'Nueva Nave' }}</h1>
    <form @submit.prevent="submitForm">
      <div class="form-fields">
        <div class="form-group">
          <label for="nombre">Nombre</label>
          <input type="text" id="nombre" v-model="form.nombre" :readonly="readonly" required />
        </div>

        <div class="form-group">
          <label for="piloto">Piloto</label>
          <input type="text" id="piloto" v-model="form.piloto" :readonly="readonly" required />
        </div>

        <div class="form-group">
          <label for="colores">Colores</label>
          <input type="text" id="colores" v-model="form.colores" :readonly="readonly" required />
        </div>

        <div class="form-group">
          <label for="base">Base</label>
          <input type="text" id="base" v-model="form.base" :readonly="readonly" required />
        </div>

        <div class="form-group">
          <label for="modificaciones">Modificaciones</label>
          <textarea id="modificaciones" v-model="form.modificaciones" :readonly="readonly" required></textarea>
        </div>

        <div class="form-group">
          <label for="anyoFabricacion">Año de Fabricación</label>
          <input class="number-align" type="number" id="anyoFabricacion" v-model="form.anyoFabricacion" :readonly="readonly" required />
        </div>

        <div class="form-group">
          <label for="peso">Peso</label>
          <input class="number-align" type="number" id="peso" v-model="form.peso" :readonly="readonly" required />
        </div>

        <div class="form-group">
          <label for="longitud">Longitud</label>
          <input class="number-align" type="number" id="longitud" v-model="form.longitud" :readonly="readonly" required />
        </div>

        <div class="form-group">
          <label for="bodega">Bodega</label>
          <input class="number-align" type="number" id="bodega" v-model="form.bodega" :readonly="readonly" required />
        </div>

        <div class="form-group">
          <label for="carga">Carga</label>
          <input class="number-align" type="number" id="carga" v-model="form.carga" :readonly="readonly" required />
        </div>

        <div class="form-group">
          <label for="capacidadPersonas">Capacidad de Personas</label>
          <input class="number-align" type="number" id="capacidadPersonas" v-model="form.capacidadPersonas" :readonly="readonly" required />
        </div>

        <div class="form-group">
          <label for="tipo">Tipo</label>
          <select id="tipo" v-model="form.tipo" :disabled="readonly">
            <option :value="null">Sin Tipo</option>
            <option v-for="tipo in tipos" :key="tipo.id" :value="tipo.id">{{ tipo.nombre }}</option>
          </select>
        </div>
      </div>
      <!-- Botones: crear/modificar o solo cancelar -->
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

    <!-- Ventana Emergente de Confirmación -->
    <div v-if="showConfirmationDialog" class="confirmation-dialog">
      <div class="confirmation-dialog-content">
        <p>¿Estás seguro de que deseas actualizar la nave?</p>
        <div class="confirmation-dialog-buttons">
          <button @click="confirmSubmit" class="confirm-button">Aceptar</button>
          <button @click="cancelSubmit" class="cancel-button">Cancelar</button>
        </div>
      </div>
    </div>
  </div>

  <!-- Modal de Mensajes -->
  <div v-if="showModal" class="modal">
    <div class="modal-content">
      <span class="close" @click="showModal = false">&times;</span>
      <p>{{ modalMessage }}</p>
    </div>
  </div>
</template>

<style scoped>
input[type=number] {
  -moz-appearance: textfield; /* Firefox */
}

.form-container {
  max-width: 700px;
  margin: 40px auto;
  padding: 20px;
  background-color: #f7fafc;
  border-radius: 10px;
  box-shadow: 0 4px 15px rgba(0, 0, 0, 0.1);
  text-align: center;
}

.form-fields {
  max-height: 620px; /* Ajusta la altura máxima según sea necesario */
  overflow-y: auto; /* Habilita el scroll vertical */
  margin-bottom: 20px; /* Espacio entre los campos y los botones */
}

/* Título */
h1 {
  font-size: 2rem;
  color: #2d3748;
  margin-bottom: 20px;
  font-weight: bold;
  text-transform: uppercase;
  letter-spacing: 1px;
}

/* Estilos del formulario */
.form-group {
  margin-bottom: 20px;
  text-align: left;
}

label {
  font-weight: 600;
  color: #2d3748;
  display: block;
  margin-bottom: 8px;
}

.number-align {
  text-align: right;
  width: 10%;
  padding-right: 10px;
}

input, textarea, select {
  width: 100%;
  padding: 12px;
  font-size: 1rem;
  border: 1px solid #cbd5e0;
  border-radius: 8px;
  background-color: #edf2f7;
  box-sizing: border-box;
  transition: border-color 0.3s ease;
}

input:focus, textarea:focus, select:focus {
  border-color: #63b3ed;
  outline: none;
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

/* Botón de Habilitar */
.enable-edit-button {
  background-color: #28a745;
}

.enable-edit-button:hover {
  background-color: #218838;
}

/* Botón de cancelar */
.cancel-button {
  background-color: #e53e3e;
  color: white;
  box-shadow: 0 4px 10px rgba(229, 62, 62, 0.4);
  justify-content: flex-end;
}

.cancel-button:hover {
  background-color: #c53030;
  transform: translateY(-3px);
  box-shadow: 0 6px 14px rgba(229, 62, 62, 0.6);
}

/* Ventana emergente de confirmación */
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
</style>