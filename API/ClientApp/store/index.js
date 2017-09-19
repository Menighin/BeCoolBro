import Vue from 'vue'
import Vuex from 'vuex'
import zenQuotes from './modules/zenQuotes';
import languages from './modules/languages';

Vue.use(Vuex)

export default new Vuex.Store({
    modules: {
        zenQuotes,
        languages
    }
});
