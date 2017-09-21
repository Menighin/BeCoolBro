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
                <div class="mdl-cell" :class="{ 'mdl-cell--11-col':  i == messages.length - 1, 'mdl-cell--12-col': i != messages.length - 1 || messages.length >= languages.length}">
                    <input-combo 
                        :index="i"
                        textName="msg1" 
                        textLabel="Message" 
                        selectName="language1" 
                        selectLabel="Language"
                        :options="languages"
                        @changeLanguage="updateLanguages"></input-combo>
                </div>
                <div class="mdl-cell mdl-cell--1-col" v-if="i == messages.length - 1 && messages.length < languages.length">
                    <button @click="addMessage" class="mdl-button mdl-button--icon"><i class="material-icons">add</i></button>
                </div>
            </div>

            <div class="mdl-grid mdl-grid--no-spacing">
                <tag-input class="mdl-cell mdl-cell--2-col" v-for="t in tags" :key="t.id">{{ t.label }}</tag-input>
            </div>
            
        </form>
    </div>
</template>

<script>

    import InputCombo from './form/InputCombo';
    import TagInput from './form/TagInput';

    export default {
        data() {
            return {
                messages: [null]
            }
        },
        methods: {
            updateLanguages(i, language) {
                this.messages[i] = language;
            },
            addMessage() {
                if (this.messages.length < this.languages.length)
                    this.messages.push(null);
            }
        },
        components: {
            inputCombo: InputCombo,
            tagInput: TagInput
        },
        created() {
            this.$store.dispatch('fetchLanguages');
            this.$store.dispatch('fetchTags');
        },
        computed: {
            languages() {
                let l = this.$store.getters.languages;
                let result = [];
                l.forEach((v) => {
                    result.push({ label: v.description, value: v.id });
                });
                return result;
            },
            tags() {
                let t = this.$store.getters.tags;
                let result = [];
                t.forEach((v) => {
                    result.push({ label: v.name, value: v.id, color: v.color });
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