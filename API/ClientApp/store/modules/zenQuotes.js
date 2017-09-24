import Vue from 'vue';

const state = {
    zenQuotes: [],
    invalidZenQuotes: []
};

const mutations = {
    setQuotes(state, quotes) {
        state.zenQuotes = quotes;
    },
    setInvalidQuotes(state, quotes) {
        state.invalidZenQuotes = quotes;
    },
    addQuotes(state, quotes) {
        state.zenQuotes = state.zenQuotes.concat(quotes)
    }
};

const actions = {
    fetchQuotes({ commit }) {
        Vue.http.get('/api/zen/images')
            .then((response) => {
                return response.json();
            })
            .then(json => {
                commit('setQuotes', json);
            })
            .catch((error => {
                console.log('Error: ' + error.statusText);
                console.log(error);
            }));
    },
    fetchInvalidQuotes({ commit }) {
        Vue.http.get('/api/zen/invalid')
            .then((response) => {
                return response.json();
            })
            .then(json => {
                commit('setInvalidQuotes', json)
            })
            .catch((error => {
                console.log('Error: ' + error.statusText);
                console.log(error);
            }));
    },
    rateQuote({ commit }, rate ) {
        Vue.http.put('/api/zen/' + rate.id + '/rate', rate );
    },
    postQuote({ commit }, quote) {
        Vue.http.post('/api/zen', quote);
    }
};

const getters = {
    quotes() {
        return state.zenQuotes;
    }
};

export default {
    state,
    mutations,
    actions,
    getters
};
