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
        component: () => import('../../views/console/main/addData/AddData')
    },
    {
        path: '/请假审批',
        component: () => import('../../views/console/main/leaveManagement/LeaveManagement')
    },
    {
        path: '/我要归档',
        component: () => import('../../views/console/main/archive/Archive')
    },
    {
        path: '/教师管理',
        component: () => import('../../views/console/main/teacherManagement/TeacherManagement')
    }
];

const router = new VueRouter({
    routes
});

export default router
