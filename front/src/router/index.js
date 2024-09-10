import { createRouter, createWebHistory } from 'vue-router'
import Inicio from '../views/Inicio.vue'
import Tipo from '../views/Tipo.vue'
import Nave from '../views/Nave.vue'
import NaveForm from '../components/NaveForm.vue'
import TipoForm from '../components/TipoForm.vue'

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
      path: '/Nave',
      name: 'Nave',
      component: Nave
    },
    {
      path: '/NaveForm/:id?',
      name: 'NaveForm',
      component: NaveForm
    },
    {
      path: '/tipoForm/:id?',
      name: 'TipoForm',
      component: TipoForm
    }
  ]
})

export default router
