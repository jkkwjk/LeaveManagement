import Vue from 'vue'
import VueRouter from 'vue-router'

Vue.use(VueRouter);

const routes = [
    /*{
        path: '/',
        name: 'home',
        component: Home
    },
    {
        path: '/about',
        name: 'about',
        component: () => import('../views/About.vue')
    }*/
    // {
    //     path: '/',
    //     component:
    // }
];

const router = new VueRouter({
    routes
});

export default router
