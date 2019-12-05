import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)

const store = new Vuex.Store({
    state: {
        avatar: '/dev/avatar.jpg',
        auth: [],
        authType: '',
    },
    mutations: {
        update(state,[key,value]){
            state[key]=value;
        },
    }
})

export default store