<script setup>
import { RouterLink, RouterView } from 'vue-router';
</script>

<template>
  <div class="app-container">
    <nav class="navbar">
      <RouterLink active-class="active" to="/">Inicio</RouterLink>
      <RouterLink active-class="active" to="/tipo">Tipos</RouterLink>
      <RouterLink active-class="active" to="/nave">Naves</RouterLink>
    </nav>
    <div class="main-content">
      <RouterView />
    </div>
  </div>
</template>

<style scoped>
.app-container {
  display: flex;
  flex-direction: column;
  min-height: 100vh;
  overflow-x: hidden; /* Evita el desplazamiento horizontal */
}

.active {
  font-weight: bold;
  color: #b82d2d;
}

.navbar {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 80px; /* Aumenta la altura de la barra de navegación */
  display: flex;
  align-items: center;
  justify-content: space-around;
  background-color: #333;
  color: white;
  font-size: 1.2rem; /* Aumenta el tamaño de la fuente */
  padding: 0 20px;
  box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
}

.navbar a {
  color: white;
  text-decoration: none;
  padding: 10px 20px;
}

.navbar a:hover {
  background-color: #444;
  border-radius: 5px;
}

.main-content {
  margin-top: 80px; /* Ajusta el margen superior para evitar que el contenido se superponga con la barra de navegación */
  padding: 20px;
}
</style>  