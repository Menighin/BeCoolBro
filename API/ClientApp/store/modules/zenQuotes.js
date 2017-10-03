import Vue from 'vue';

const state = {
    zenQuotes: [],
    invalidZenQuotes: []
};

const mutations = {
    setQuotes(state, quotes) {
        state.zenQuotes = quotes;
    },
    pushQuotes(state, quotes) {
        state.zenQuotes = state.zenQuotes.concat(quotes);
    },
    setInvalidQuotes(state, quotes) {
        state.invalidZenQuotes = quotes;
    },
    addQuotes(state, quotes) {
        state.zenQuotes = state.zenQuotes.concat(quotes)
    }
};

const actions = {
    fetchQuotes({ commit }, { page, successCallback }) {
        Vue.http.get('/api/zen/images?page=' + page)
            .then((response) => {
                return response.json();
            })
            .then(json => {
                commit('pushQuotes', json);
                if (typeof (successCallback) !== 'undefined') successCallback(json);
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
    postQuote({ commit }, { quote, callbackSuccess, callbackFail }) {
        Vue.http.post('/api/zen', quote)
        .then(response => {
            if (typeof(callbackSuccess) !== 'undefined') callbackSuccess();
        })
        .catch((error => {
            console.log('Error: ' + error.statusText);
            console.log(error);
            if (typeof(callbackFail) !== 'undefined') callbackFail();
        }));
    },
    validateQuote({ commit }, { quote, callbackSuccess, callbackFail }) {
        Vue.http.put('/api/zen/' + quote.id + '/validate', quote)
            .then(response => {
                if (typeof(callbackSuccess) !== 'undefined') callbackSuccess();
            })
            .catch((error => {
                console.log('Error: ' + error.statusText);
                console.log(error);
                if (typeof(callbackFail) !== 'undefined') callbackFail();
            }));;
    }
};

const getters = {
    quotes() {
        return state.zenQuotes;
    },
    invalidQuotes() {
        return state.invalidZenQuotes;
    }
};

export default {
    state,
    mutations,
    actions,
    getters
};
