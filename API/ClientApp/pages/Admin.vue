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