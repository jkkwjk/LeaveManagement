import Vue from 'vue'
import VueRouter from 'vue-router'

Vue.use(VueRouter);

const routes = [
    {
        path: '/概要查询',
        component: () => import('../../components/console/Main/Summary')
    }
];

const router = new VueRouter({
    routes
});

export default router
