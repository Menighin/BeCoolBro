import './css/site.css'
import 'core-js/es6/promise'
import 'core-js/es6/array'
import Vue from 'vue'
import axios from 'axios'
import router from './router'
import VueResource from 'vue-resource';
import store from './store'
import { sync } from 'vuex-router-sync'
import App from './AppVue'

Vue.prototype.$http = axios;

sync(store, router)

Vue.use(VueResource);

Vue.filter('tag', function(value) {
    return '#' + value;
});

Vue.filter('zenLikes', function(value) {
    var n = parseInt(value);
    if (isNaN(n) || n <= 999) return value;

    if (n >= 1000)
        return (n / 1000).toFixed(2) + "K";
    else if (n >= 1000000)
        return (n / 1000000).toFixed(2) + "M";

    return 'oops!';
});

const app = new Vue({
    el: '#app',
    store,
    router,
    render: h => h(App)
});