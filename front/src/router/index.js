import { createRouter, createWebHistory } from 'vue-router'
import Inicio from '../views/Inicio.vue'
import Tipo from '../views/Tipo.vue'
import Nave from '../views/Nave.vue'
import NaveForm from '../components/NaveFormulario.vue'
import TipoFormulario from '../components/TipoFormulario.vue'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'inicio',
      component: Inicio
    },
    {
      path: '/tipo',
      name: 'tipo',
      component: Tipo
    },
    {
      path: '/nave',
      name: 'nave',
      component: Nave
    },
    {
      path: '/NaveFormulario/:id?',
      name: 'NaveFormulario',
      component: NaveForm
    },
    {
      path: '/tipoFormulario/:id?',
      name: 'TipoFormulario',
      component: TipoFormulario
    }
  ]
})

export default router
