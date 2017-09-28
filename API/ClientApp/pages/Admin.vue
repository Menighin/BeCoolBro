<template>
    <div class="mdl-grid portfolio-max-width">
        <div v-if="!logged" style="margin: 0 auto">
            <div class="mdl-textfield mdl-js-textfield mdl-textfield--floating-label">
				<input class="mdl-textfield__input" type="text" id="password" v-model="password" />
				<label class="mdl-textfield__label" for="password">Password</label>
			</div>
            <button class="mdl-button mdl-js-button mdl-button--raised mdl-js-ripple-effect" @click="submit">Submit</button>
        </div>
        <div v-else>

            <input-validate v-for="q in invalidQuotes" :quote="q" :key="q.id"></input-validate>

        </div>
    </div>
</template>

<script>

    import InputValidate from '../components/form/InputValidate';

    export default {
        created() {
            this.$store.dispatch("fetchInvalidQuotes");
        },
        data() {
            return {
                password: ''
            }
        },
        computed: {
            logged() {
                return this.$store.getters.logged;
            },
            invalidQuotes() {
                let invalidQuotes = this.$store.getters.invalidQuotes;

                let quotes = [];

                invalidQuotes.forEach((q) => {
                    let temp = { id: q.id, author: q.author, messages: {} };

                    q.messages.forEach((m) => {
                        temp.messages[m.language.code] = { message: m.message, id: m.id };
                    });

                    quotes.push(temp);

                });

                setTimeout(() => {
                    componentHandler.upgradeDom();
                }, 100);

                return quotes;

            }
        },
        methods: {
            submit() {
                this.$store.dispatch('login', { password: this.password });
            }
        },
        components: {
            inputValidate: InputValidate
        }
    }

</script>

<style>

</style>