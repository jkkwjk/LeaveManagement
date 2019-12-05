import Vue from 'vue'
import App from './App.vue'
import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';
import axios from '@/http/http'

Vue.prototype.$http = axios;
Vue.use(ElementUI);

new Vue({
    render: h => h(App)
}).$mount('#app');