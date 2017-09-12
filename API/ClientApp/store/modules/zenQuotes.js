import Vue from 'vue';

const state = {
    zenQuotes: []
};

const mutations = {
    setQuotes(state, quotes) {
        state.zenQuotes = quotes;
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
                console.log(json);
                commit('setQuotes', json);
            })
            .catch((error => {
                console.log('Error: ' + error.statusText);
                console.log(error);
            }));
    },
    rateQuote({ commit }, rate ) {
        Vue.http.put('/api/zen/' + rate.id + '/rate', rate );
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
