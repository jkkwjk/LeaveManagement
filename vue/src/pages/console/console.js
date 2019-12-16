import Vue from 'vue'
import App from './App.vue'
import router from './router'

import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';

Vue.use(ElementUI);

//解决点击同一个路由报错
import Router from 'vue-router'

const originalPush = Router.prototype.push;
Router.prototype.push = function push(location) {
    return originalPush.call(this, location).catch(err => err)
};
// end

// echarts
import echarts from 'echarts'
Vue.prototype.$echarts = echarts;
//end

//全局变量
import store from './store'
import axios from '@/http/http'

Vue.prototype.$store = store
Vue.prototype.$http = axios;
//end

//权限校验

axios.get('auth').then(res => {
    const data = res.data;
    store.commit('update',['authType',data.name]);
    store.commit('update',['auth',data.data.router]);

    router.beforeEach((to, from, next) => {
        const path = to.path.substr(1);
        if(store.state.auth.some(_=>{return path === _}) || path === ''){
            next();
        }else {
            next(false);
        }
    });

    new Vue({
        store,
        router,
        render: h => h(App)
    }).$mount('#app');
}).catch(res => {
    window.location.href = "login.html";
});


//end
