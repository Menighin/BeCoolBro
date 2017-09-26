import Vue from 'vue'
import Vuex from 'vuex'
import zenQuotes from './modules/zenQuotes';
import languages from './modules/languages';
import tags from './modules/tags';
import admin from './modules/admin';

Vue.use(Vuex)

export default new Vuex.Store({
    modules: {
        zenQuotes,
        languages,
        tags,
        admin
    }
});
