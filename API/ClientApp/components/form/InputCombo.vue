<template>
    <div class="mdl-grid mdl-grid--no-spacing">
        <div class="mdl-cell mdl-cell--2-col">
            <select :name="selectName" @change="(evt) => {updateModel('select', evt)}">
                <option v-for="o in options" :key="o.value" :value="o.value">{{ o.label }}</option>
            </select>
        </div>
        <div class="mdl-cell mdl-cell--10-col mdl-textfield mdl-js-textfield mdl-textfield--floating-label">
            <input class="mdl-textfield__input" type="text" :name="textName" @input="updateModel('input')" v-model="input"/>
            <label class="mdl-textfield__label">{{ textLabel }}</label>
        </div>
    </div>
</template>

<script>

    export default {
        data() {
            return {
                input: '',
                select: null
            }
        },
        methods: {
            updateModel(type, evt) {

                if (type === 'select')
                    this.select = parseInt(evt.target.value);

                if (this.select == null)
                    this.select = this.options[0].value;

                this.$emit('input', {
                    select: this.select,
                    input: this.input,
                });
            }
        },
        props: {
            index: Number,
            textName: String,
            textLabel: String,
            selectName: String,
            selectLabel: String,
            options: Array,
            negativeOptions: Array,
            value: Object
        },
        mounted() {
            if (typeof (componentHandler) !== 'undefined') {
                componentHandler.upgradeDom();
            }
        }
    };

</script>

<style scoped>

    .mdl-selectfield label, .mdl-grid .mdl-cell .mdl-selectfield__placeholder {
        color: white;
    }

    .mdl-selectfield__select {
        border-bottom: 1px solid rgba(255, 255, 255, 0.5);
    }

    .mdl-selectfield__label:after {
        background-color: white;
    }

    .mdl-selectfield--floating-label.is-focused .mdl-selectfield__label {
        color: white;
    }

</style>