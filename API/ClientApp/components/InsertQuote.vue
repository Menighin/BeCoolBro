<template>
    <div class="zen-insert portfolio-max-width">
        <div class="insert-title"><h2>New Quote!</h2></div>
        <form action="#" onsubmit="return false">
            <div class="mdl-textfield mdl-js-textfield mdl-textfield--floating-label">
				<input class="mdl-textfield__input" type="text" id="author" />
				<label class="mdl-textfield__label" for="author">Author</label>
				<span class="mdl-textfield__error">Only alphabet and no spaces, please!</span>
			</div>
            <div class="mdl-grid mdl-grid--no-spacing" v-for="(m, i) in messages" :key="i">
                <div class="mdl-cell mdl-cell--11-col">
                    <input-combo 
                        :index="i"
                        textName="msg1" 
                        textLabel="Message" 
                        selectName="language1" 
                        selectLabel="Language"
                        :options="languages"
                        :negativeOptions="messages"
                        @changeLanguage="updateLanguages"></input-combo>
                </div>
                <div class="mdl-cell mdl-cell--1-col">
                    <button @click="messages.push(null)" class="mdl-button mdl-button--icon"><i class="material-icons">add</i></button>
                </div>
            </div>
        </form>
    </div>
</template>

<script>

    import InputCombo from './form/InputCombo';

    export default {
        data() {
            return {
                messages: [null]
            }
        },
        methods: {
            updateLanguages(i, language) {
                this.messages[i] = language;
            }
        },
        components: {
            inputCombo: InputCombo
        },
        created() {
            this.$store.dispatch('fetchLanguages');
        },
        computed: {
            languages() {
                let l = this.$store.getters.languages;
                let result = [];
                l.forEach((v) => {
                    result.push({ label: v.description, value: v.id });
                });
                return result;
            }
        }
    };

</script>

<style>

    .zen-insert {
        padding: 10px 30px;
    }

    .insert-title {
        display: block;
        margin: 0 auto;
        color: white;
        text-align: center;
    }

    form {
        text-align: center;
        color: white;
    }

    .mdl-textfield {
        width: 100%;
    }

    .mdl-textfield__input {
        border-bottom: 1px solid rgba(255, 255, 255, 0.5);
        color: white;
    }

    .mdl-textfield__label {
        color: white !important;
    }

    .mdl-textfield__label:after {
        background-color: white;
    }

</style>