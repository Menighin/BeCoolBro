import Vue from 'vue';

const state = {
    logged: false
};

const mutations = {
    setLogged(state, logged) {
        state.logged = logged;
    }
};

const actions = {
    login({ commit }, password) {
        Vue.http.post('/api/login', password)
            .then((response) => {
                if (response.status == 200)
                    commit('setLogged', true);
            })
            .catch((error => {
                if (error.status != 200)
                    commit('setLogged', false);
            }));
    }
};

const getters = {
    logged() {
        return state.logged;
    }
};

export default {
    state,
    mutations,
    actions,
    getters
};
