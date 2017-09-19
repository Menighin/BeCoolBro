import Vue from 'vue';

const state = {
    languages: []
};

const mutations = {
    setLanguages(state, languages) {
        state.languages = languages;
    },
    addLanguages(state, languages) {
        state.languages = state.languages.concat(languages)
    }
};

const actions = {
    fetchLanguages({ commit }) {
        Vue.http.get('/api/zen/languages')
            .then((response) => {
                return response.json();
            })
            .then(json => {
                commit('setLanguages', json);
            })
            .catch((error => {
                console.log('Error: ' + error.statusText);
                console.log(error);
            }));
    }
};

const getters = {
    languages() {
        return state.languages;
    }
};

export default {
    state,
    mutations,
    actions,
    getters
};
