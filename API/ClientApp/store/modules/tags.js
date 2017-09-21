import Vue from 'vue';

const state = {
    tags: []
};

const mutations = {
    setTags(state, tags) {
        state.tags = tags;
    },
    addTags(state, tags) {
        state.tags = state.tags.concat(tags)
    }
};

const actions = {
    fetchTags({ commit }) {
        Vue.http.get('/api/zen/tags')
            .then((response) => {
                return response.json();
            })
            .then(json => {
                commit('setTags', json);
            })
            .catch((error => {
                console.log('Error: ' + error.statusText);
                console.log(error);
            }));
    }
};

const getters = {
    tags() {
        return state.tags;
    }
};

export default {
    state,
    mutations,
    actions,
    getters
};
