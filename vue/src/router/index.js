import Vue from 'vue'
import VueRouter from 'vue-router'


Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    name: 'Index',
    component: () => import('../views/Index.vue'),
    redirect: '/home',
    children:[
      {path: 'home', name: 'Home', component: () => import('../views/Home.vue')},
      {path: 'sentiArgs', name: 'SentiArgs', component: () => import('../views/SentiArgs.vue')},
      {path: 'options', name: 'Options', component: () => import('../views/Options.vue')},
      {path: 'login', name: 'Login', component: () => import('../views/Login.vue')},
      {path: 'file', name: 'File', component: () => import('../views/File.vue')}
    ]
  },

]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

export default router
