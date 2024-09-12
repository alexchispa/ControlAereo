<script setup>
import { RouterLink, RouterView, useRoute } from 'vue-router';
import { ref } from 'vue';

const isSidebarOpen = ref(false);

const toggleSidebar = () => {
  isSidebarOpen.value = !isSidebarOpen.value;
};

const route = useRoute();
</script>

<template>
  <div class="app-container">
    <nav class="sidebar" :class="{ open: isSidebarOpen }">
      <button class="sidebar-toggle" @click="toggleSidebar">
        <span class="hamburger-icon"></span>
      </button>
      <RouterLink
        to="/"
        class="sidebar-link"
        :class="{ active: route.path === '/' }"
      >
        Inicio
      </RouterLink>
      <RouterLink
        to="/tipo"
        class="sidebar-link"
        :class="{ active: route.path.startsWith('/tipo') }"
      >
        Tipos
      </RouterLink>
      <RouterLink
        to="/nave"
        class="sidebar-link"
        :class="{ active: route.path.startsWith('/nave') }"
      >
        Naves
      </RouterLink>
    </nav>
    <div class="main-content" :class="{ shifted: isSidebarOpen }">
      <RouterView />
    </div>
  </div>
</template>

<style scoped>
.app-container {
  display: flex;
  min-height: 100vh;
}

.sidebar {
  position: fixed;
  top: 0;
  left: 0;
  width: 250px;
  height: 100vh;
  background-color: #333;
  color: white;
  display: flex;
  flex-direction: column;
  align-items: center;
  padding-top: 20px;
  transition: transform 0.3s ease;
  box-shadow: 2px 0 10px rgba(0, 0, 0, 0.2);
  z-index: 1000;
}

.sidebar-link {
  color: white;
  text-decoration: none;
  border-radius: 10px;
  padding: 15px 20px;
  width: 80%;
  text-align: center;
  display: block;
  transition: text-shadow 0.3s ease, background-color 0.3s ease;
}

.sidebar-link:hover {
  background-color: #969494; /* Color más claro al pasar el cursor */
  text-shadow: 0 0 4px white; /* Efecto glow más sutil */
}

.sidebar-link.active {
  background-color: #444; /* Color más oscuro cuando está activo */
  text-shadow: 0 0 8px white; /* Efecto glow blanco */
}

.sidebar-toggle {
  position: absolute;
  top: 20px;
  right: -50px;
  background-color: #333;
  color: white;
  border: none;
  border-radius: 50%;
  width: 40px;
  height: 40px;
  display: flex;
  align-items: center;
  justify-content: center;
  cursor: pointer;
  box-shadow: 0 4px 10px rgba(0, 0, 0, 0.2);
  z-index: 1001;
}

.hamburger-icon {
  width: 20px;
  height: 2px;
  background-color: white;
  position: relative;
}

.hamburger-icon::before,
.hamburger-icon::after {
  content: '';
  width: 20px;
  height: 2px;
  background-color: white;
  position: absolute;
  left: 0;
}

.hamburger-icon::before {
  top: -6px;
}

.hamburger-icon::after {
  top: 6px;
}

/* Estilos para pantallas pequeñas */
@media (max-width: 768px) {
  .sidebar {
    transform: translateX(-100%);
  }

  .sidebar.open {
    transform: translateX(0);
  }

  .sidebar-toggle {
    display: flex;
  }

  .main-content.shifted {
    margin-left: 0;
  }
}

/* Estilos para pantallas grandes */
@media (min-width: 769px) {
  .sidebar {
    transform: translateX(0);
    width: 250px;
  }

  .sidebar-toggle {
    display: none;
  }

  .main-content {
    margin-left: 250px;
  }
}

.main-content {
  flex: 1;
  padding: 20px;
  transition: margin-left 0.3s ease;
}
</style>
