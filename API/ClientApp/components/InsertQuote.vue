<template>
    <div class="zen-insert portfolio-max-width">
        <div class="close-insert" @click="close"><i class="material-icons">close</i></div>
        <div class="insert-title"><h2>New Quote!</h2></div>
        <form action="#" onsubmit="return false">
            <div class="mdl-textfield mdl-js-textfield mdl-textfield--floating-label">
				<input class="mdl-textfield__input" type="text" id="username" v-model="username" />
				<label class="mdl-textfield__label" for="username">Your name</label>
				<span class="mdl-textfield__error">Only alphabet and no spaces, please!</span>
			</div>
            <div class="mdl-textfield mdl-js-textfield mdl-textfield--floating-label">
				<input class="mdl-textfield__input" type="text" id="author" v-model="author" />
				<label class="mdl-textfield__label" for="author">Quote author</label>
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
                        @changeLanguage="updateLanguages"
                        v-model="messages[i]"></input-combo>
                </div>
                <div class="mdl-cell mdl-cell--1-col" v-if="i == messages.length - 1 && messages.length < languages.length">
                    <button @click="addMessage" class="mdl-button mdl-button--icon"><i class="material-icons">add</i></button>
                </div>
            </div>

            <div class="mdl-grid">
                <tag-input class="mdl-cell mdl-cell--2-col" v-for="t in tags" :key="t.id" :tagId="t.id" :color="t.color" @change="checkTag">{{ t.label }}</tag-input>
            </div>
            
            <button class="mdl-button mdl-js-button mdl-button--raised mdl-js-ripple-effect" @click="submit">Submit</button>
        </form>
    </div>
</template>

<script>

    import InputCombo from './form/InputCombo';
    import TagInput from './form/TagInput';

    export default {
        data() {
            return {
                messages: [null],
                author: '',
                username: '',
                tagsIds: new Set()
            }
        },
        methods: {
            close(event) {
                this.$emit('close', event);
            },
            updateLanguages(i, language) {
                this.messages[i] = language;
            },
            addMessage() {
                if (this.messages.length < this.languages.length)
                    this.messages.push(null);
            },
            checkTag(id, value) {
                if (value) {
                    this.tagsIds.add(parseInt(id));
                } else {
                    this.tagsIds.delete(parseInt(id));
                }
            },
            submit(event) {

                let messages = [];

                this.messages.forEach((v) => {
                    messages.push({language: v.select, message: v.input});
                });

                var zenQuote = {
                    author: this.author,
                    messages: messages,
                    tags: this.tagsIds,
                    user: this.username
                };

                this.$store.dispatch('postQuote', zenQuote);
                this.resetComponent();
                this.close(event);

            },
            resetComponent() {
                this.messages = [null];
                this.author = '';
                this.username = '';
                this.tagsIds = new Set();
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
                    result.push({ label: v.name, id: v.id, color: v.color });
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

    .close-insert {
        color: white;
        cursor: pointer;
        float: right;
        padding: 10px;
    }

</style>