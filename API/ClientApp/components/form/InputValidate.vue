<template>
    <div class="mdl-grid"> 
        <div class="mdl-cell mdl-cell--3-col">
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
            <button class="mdl-button mdl-js-button mdl-button--raised mdl-js-ripple-effect" @click="validate">Validate</button>
        </div>
    </div>
</template>

<script>

    export default {
        data() {
            return {
                model: {}
            }
        },
        methods: {
            validate() {

                let self = this;
                let success = function() {
                    self.model.author = "WORKED";
                };

                this.$store.dispatch('validateQuote', { quote: this.model, callback: success });
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
