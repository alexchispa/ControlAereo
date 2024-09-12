<template>
  <div class="inicio">
    <header>
      <h1>Bienvenido a NaveExplora</h1>
      <p>Explora el mundo de las naves marítimas y aéreas.</p>
    </header>

    <section class="intro-section">
      <h2>¿Qué puedes hacer?</h2>
      <p>
        NaveExplora te permite conocer, gestionar y descubrir diferentes tipos de naves, tanto marítimas como aéreas. Sumérgete en el mundo de las naves y descubre todas sus características.
      </p>
    </section>

    <section class="cta-section">
      <h2>Comienza tu exploración</h2>
      
    </section>
  </div>
</template>

<script>
export default {
  name: 'Inicio',
};
</script>

<style scoped>
body {
  font-family: 'Arial', sans-serif;
  background-color: #E3F2FD; /* Azul muy claro */
  margin: 0;
  padding: 0;
}

.inicio {
  padding: 20px;
  text-align: center;
}

header {
  background-color: #0D47A1; /* Azul oscuro para el fondo del header */
  color: #FFFFFF; /* Blanco para el texto */
  padding: 60px 20px;
  border-radius: 10px;
  margin-bottom: 40px;
  box-shadow: 0px 4px 15px rgba(0, 0, 0, 0.3);
}

header h1 {
  margin: 0;
  font-size: 3em;
  font-weight: bold;
}

header p {
  margin: 15px 0 0;
  font-size: 1.5em;
  font-weight: 300;
}

.intro-section {
  background-color: #1976D2; /* Azul brillante para las secciones */
  color: #FFFFFF; /* Blanco para el texto */
  padding: 40px;
  border-radius: 10px;
  margin-bottom: 40px;
  box-shadow: 0px 4px 15px rgba(0, 0, 0, 0.2);
}

.intro-section h2 {
  font-size: 2.2em;
  margin-bottom: 20px;
}

.intro-section p {
  font-size: 1.2em;
  line-height: 1.6em;
}

.cta-section {
  background-color: #64B5F6; /* Azul claro */
  padding: 40px;
  border-radius: 10px;
  box-shadow: 0px 4px 15px rgba(0, 0, 0, 0.2);
}

.cta-section h2 {
  font-size: 2em;
  color: #ffffff; /* Azul marino profundo */
  margin-bottom: 25px;
}

.cta-buttons {
  display: flex;
  flex-direction: column;
  gap: 20px;
}

.cta-button {
  background-color: #3949AB; /* Azul medio */
  color: #FFFFFF; /* Blanco para el texto */
  padding: 15px 25px;
  text-decoration: none;
  border-radius: 8px;
  font-size: 1.3em;
  font-weight: bold;
  transition: background-color 0.3s ease;
}

.cta-button:hover {
  background-color: #42A5F5; /* Azul más claro para los botones al pasar el mouse */
}

@media (min-width: 768px) {
  .cta-buttons {
    flex-direction: row;
    justify-content: center;
  }

  .cta-button {
    width: 220px;
    text-align: center;
  }
}


</style>
