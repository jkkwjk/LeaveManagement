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
    },
    {
        path: '/学生管理',
        component: () => import('../../views/console/main/studentManagement/StudentManagement')
    },
    {
        path: '/个人信息',
        component: () => import('../../views/console/main/userInfo/UserInfo')
    },
    {
        path: '/请假申请',
        component: () => import('../../views/console/main/leaveApply/LeaveApply')
    },
    {
        path: '/回收站',
        component: () => import('../../views/console/main/trashBin/TrashBin')
    },
    {
        path: '/请假查看',
        component: () => import('../../views/console/main/leaveCheck/LeaveCheck')
    },
    {
        path: '/课程归档',
        component: () => import('../../views/console/main/teacherArchive/TeacherArchive')
    }
];

const router = new VueRouter({
    routes
});

export default router
