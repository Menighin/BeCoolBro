<template>
    <div class="mdl-grid mdl-grid--no-spacing">
        <div class="mdl-cell mdl-cell--2-col">
            <select :name="selectName" @change="changeLanguage">
                <option v-for="o in options" :key="o.value" :value="o.value">{{ o.label }}</option>
            </select>
        </div>
        <div class="mdl-cell mdl-cell--10-col mdl-textfield mdl-js-textfield mdl-textfield--floating-label">
            <input class="mdl-textfield__input" type="text" :name="textName" />
            <label class="mdl-textfield__label">{{ textLabel }}</label>
        </div>
    </div>
</template>

<script>

    export default {
        methods: {
            changeLanguage(evt) {
                this.$emit('changeLanguage', this.index, evt.target.value)
            }
        },
        props: {
            index: Number,
            textName: String,
            textLabel: String,
            selectName: String,
            selectLabel: String,
            options: Array,
            negativeOptions: Array
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