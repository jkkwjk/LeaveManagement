import Vue from 'vue'
import App from './App.vue'
import router from './router'

import vueAxios from 'vue-axios'
import axios from 'axios'
import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';

Vue.use(ElementUI);
Vue.use(vueAxios, axios);


new Vue({
    router,
    render: h => h(App)
}).$mount('#app');