<template>
    <div class="mdl-grid"> 
        <div class="mdl-cell mdl-cell--2-col">
            <div class="mdl-textfield mdl-js-textfield mdl-textfield--floating-label">
                <input class="mdl-textfield__input" type="text" id="author" v-model="model.author" />
                <label class="mdl-textfield__label" for="author">Author</label>
            </div>
        </div>
        <div class="mdl-cell mdl-cell--4-col">
            <div class="mdl-textfield mdl-js-textfield mdl-textfield--floating-label">
                <input class="mdl-textfield__input" type="text" id="en" v-model="model.en" />
                <label class="mdl-textfield__label" for="en">EN</label>
            </div>
        </div>
        <div class="mdl-cell mdl-cell--4-col">
            <div class="mdl-textfield mdl-js-textfield mdl-textfield--floating-label">
                <input class="mdl-textfield__input" type="text" id="ptBr" v-model="model.ptBr" />
                <label class="mdl-textfield__label" for="ptBr">PT-BR</label>
            </div>
        </div>
        <div class="mdl-cell mdl-cell--1-col">
            <button class="mdl-button mdl-js-button mdl-button--raised mdl-js-ripple-effect" :disabled="loading" @click="validate">Validate</button>
        </div>
        <div class="mdl-cell mdl-cell--1-col" style="text-align: right" :style="{ 'display' : loading ? 'block' : 'none' }">
            <div class="mdl-spinner mdl-js-spinner is-active"></div>
        </div>
    </div>
</template>

<script>

    export default {
        data() {
            return {
                model: {},
                loading: false
            }
        },
        methods: {
            validate() {
                
                this.loading = true;
                let self = this;
                let success = function() {
                    self.$store.dispatch('fetchInvalidQuotes');
                };

                this.$store.dispatch('validateQuote', { quote: this.model, callbackSuccess: success });
            }
        },
        props: ['quote'],
        mounted() {
            this.model = {
                id: this.quote.id,
                author: this.quote.author,
                en: typeof (this.quote.messages.EN) !== 'undefined' ? this.quote.messages.EN.message : '',
                ptBr: typeof (this.quote.messages['PT-BR']) !== 'undefined' ? this.quote.messages['PT-BR'].message : ''
            }

            if (typeof (componentHandler) !== 'undefined') {
                setTimeout(() => {
                    componentHandler.upgradeDom();
                }, 100);
            }
        }
    }

</script>
