import Vue from 'vue'
import VueRouter from 'vue-router'

Vue.use(VueRouter);

const routes = [
    {
        path: '/概要查询',
        component: () => import('../../views/console/main/summary/Summary')
    },
    {
        path: '/导入数据',
        component: () => import('../../views/console/main/addData/addData')
    },
    {
        path: '/请假审批',
        component: () => import('../../views/console/main/leaveManagement/leaveManagement')
    }
];

const router = new VueRouter({
    routes
});

export default router
