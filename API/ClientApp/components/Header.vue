<template>
        <header class="mdl-layout__header mdl-layout__header--waterfall portfolio-header">
            <div class="mdl-layout__header-row portfolio-logo-row">
                <span class="mdl-layout__title">
                    <div class="portfolio-logo"></div>
                    <span class="mdl-layout__title">Simple portfolio website</span>
                </span>
            </div>
            <div class="mdl-layout__header-row top-search">
                <form action="#" v-on:submit="search">
                    <div class="mdl-textfield mdl-js-textfield">
                        <input class="mdl-textfield__input" type="text" id="sample1" v-model="searchText">
                        <label class="mdl-textfield__label" for="sample1">Search...</label>
                    </div>
                </form>
            </div>
            <div class="mdl-layout__header-row portfolio-navigation-row mdl-layout--large-screen-only">
                <form action="#" v-on:submit="search">
                    <div class="mdl-textfield mdl-js-textfield">
                        <input class="mdl-textfield__input" type="text" id="sample1" v-model="searchText">
                        <label class="mdl-textfield__label" for="sample1">Search...</label>
                    </div>
                </form>
            </div>
        </header>
</template>

<script>

    import { EventBus } from '../eventBus.js';

    export default {
        created() {
            MaterialLayout.prototype.headerClickHandler_ = function () { };
        },
        data() {
            return {
                searchText: ''
            }
        },
        methods: {
            search() {
                EventBus.$emit('searchQuotes', this.searchText);
            }
        }
    }

</script>

<style scoped>

    .top-search {
       position: absolute;
       left: 50%;
       margin-left: -150px;
       transform: scale(0);
       transform-origin: center;
       transition: all 200ms;
    }

    .is-compact .top-search {
        display: block;
        transform: scale(1);
    }


</style>