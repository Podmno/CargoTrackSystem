import { createApp } from 'vue'
import App from './App.vue'
import {
    createRouter,
    createWebHistory
  } from 'vue-router'
import axios from '@/axiosInstance.js'
import RegisterForm from './components/RegisterForm.vue'
import LoginForm from './components/LoginForm.vue';
import HelloWorld from './components/HelloWorld.vue'
import UserLogistics from './components/UserLogistics.vue'
import DeliveryList from './components/DeliveryList.vue'
import DeliveryUpdate from './components/DeliveryUpdate.vue'
import DeliveryAdd from './components/DeliveryAdd.vue'
import UserAdmin from './components/UserAdmin.vue'
  
  const routes = [
    {path: '/', redirect: "/login"},
    {path: '/register', component: RegisterForm, meta: {title:'注册'}},
    {path: '/login', name:"login", component: LoginForm,meta: {title:'登录'}},
    {path: '/userlogistics', component: UserLogistics,meta: {title:'用户快递列表'}},
    {path: '/userlogistics/:uname', component: UserLogistics,meta: {title:'用户快递列表'}},
    {path: '/deliverylist', component: DeliveryList,meta: {title:'物流信息'}},
    {path: '/deliverylist/:number', component: DeliveryList,meta: {title:'物流信息'}},
    {path: '/deliveryupdate', component: DeliveryUpdate,meta: {title:'更新物流信息'}},
    {path: '/deliveryupdate/:number', component: DeliveryUpdate, meta: {title:'更新物流信息'}},
    {path: '/deliveryadd', component: DeliveryAdd,meta: {title:'添加物流订单'}},
    {path: '/useradmin', component: UserAdmin,meta: {title:'管理平台'}},
    {path: '/:pathMatch(.*)*', name:'notFound', component: HelloWorld,meta: {title:'404'}}
  ]
  
  const router = createRouter({
    history: createWebHistory(),
    routes
  })

  router.beforeEach((to, from, next) => {
    if (to.meta.title) {
      document.title = to.meta.title
    }
    next()
  })


const app = createApp(App).use(router).mount('#app');
app.config.globalProperties.$axios = axios;
