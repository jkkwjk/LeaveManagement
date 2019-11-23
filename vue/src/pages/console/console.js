import Vue from 'vue'
import App from './App.vue'
import router from './router'

import vueAxios from 'vue-axios'
import axios from 'axios'
import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';

Vue.use(ElementUI);
Vue.use(vueAxios, axios);

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
new Vue({
    router,
    render: h => h(App)
}).$mount('#app');