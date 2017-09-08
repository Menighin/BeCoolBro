import Vue from 'vue'
import Vuex from 'vuex'
import zenQuotes from './modules/zenQuotes';

Vue.use(Vuex)

export default new Vuex.Store({
    modules: {
        zenQuotes
    }
});
