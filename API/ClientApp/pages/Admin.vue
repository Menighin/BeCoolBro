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
            <h1>LOGGED!</h1>
            <p>{{ invalidQuotes }}</p>

            <div class="mdl-grid">
                <div class="mdl-cell mdl-cell--3-col">
                    <p>Author</p>
                </div>
                <div class="mdl-cell mdl-cell--4-col">
                    <p>EN</p>
                </div>
                <div class="mdl-cell mdl-cell--4-col">
                    <p>PT-BR</p>
                </div>

                <template v-for="q in invalidQuotes">
                    <div class="mdl-cell mdl-cell--3-col" :key="q.id">
                        <p>{{ q.author }}</p>
                    </div>
                    <div class="mdl-cell mdl-cell--4-col" :key="q.id">
                        <p v-if="typeof (q.messages.EN) !== 'undefined'">{{ q.messages.EN.message}}</p>
                    </div>
                    <div class="mdl-cell mdl-cell--4-col" :key="q.id">
                        <p v-if="typeof (q.messages['PT-BR']) !== 'undefined'">{{ q.messages['PT-BR'].message }}</p>
                    </div>
                    <div class="mdl-cell mdl-cell--1-col" :key="q.id">
                        <button class="mdl-button mdl-js-button mdl-button--raised mdl-js-ripple-effect">Validate</button>
                    </div>
                </template>

            </div>
        </div>
    </div>
</template>

<script>

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

                return quotes;

            }
        },
        methods: {
            submit() {
                this.$store.dispatch('login', { password: this.password });
            }
        }
    }

</script>

<style>

</style>