<template>
    <div id="app" class="mdl-layout mdl-js-layout mdl-layout--fixed-header">

        <zen-header></zen-header>
        
        <main class="mdl-layout__content">
            <router-view></router-view>

            <div class="mdl-sheet__container" :class="{ 'mdl-sheet-container--opened': fabOpened }">
                <div class="mdl-sheet mdl-shadow--2dp" :class="{ 'mdl-sheet--opened': fabOpened}" @click="fabOpened = true">
                    <i class="material-icons mdl-sheet__icon">add</i>
                    
                    <div class="mdl-sheet__content">
                        <div class="close-insert" @click="closeInsert"><i class="material-icons">close</i></div>
                        <zen-insert></zen-insert>
                    </div>
                </div>
            </div>

        </main>
    </div>

</template>

<script>

import Header from './components/Header';
import InsertQuote from './components/InsertQuote';

export default {
    data() {
        return {
            fabOpened: false
        };
    },
    methods: {
        closeInsert(event) {
            event.stopPropagation();
            this.fabOpened = false;
        }
    },
    components: {
        zenHeader: Header,
        zenInsert: InsertQuote
    }
}
</script>

<style scoped>

    .mdl-layout__content {
        padding: 24px 12px;
    }
    .mdl-layout__content h1 {
        font-size: 30px;
        margin: 0 0 .3em;
    }

    .mdl-sheet__container {
        z-index: 10000;
        position: fixed;
        right: 32px;
        bottom: 32px;
        -webkit-transform: rotate(0deg);
                transform: rotate(0deg);
    }

    .mdl-sheet-container--opened {
        position: absolute;
        right: 0;
        bottom: 0;
        width: 100%;
        height: 100%;
    }
    
    .mdl-sheet {
        position: absolute;
        right: 0;
        bottom: 0;
        background: #ff4081;
        width: 60px;
        height: 60px;
        border-radius: 50%;
        cursor: pointer;
        -webkit-transition: all 180ms;
        transition: all 180ms;
    }
    .mdl-sheet .mdl-sheet__content {
        display: none;
    }

    .mdl-sheet__icon {
        color: #fff;
        position: absolute;
        top: 50%;
        left: 50%;
        -webkit-transform: translate(-12px, -12px);
                transform: translate(-12px, -12px);
        font-size: 24px;
        width: 24px;
        height: 24px;
        line-height: 24px;
    }

    .mdl-sheet--opened {
        background: #ff4081;
        color: #263238;
        border-radius: 2px;
        width: 250px;
        height: auto;
        min-height: 150px;
        overflow-y: auto;
        transform: translate(0%, 0%);
        height: 100%;
        width: 100%;
        cursor: default;
    }
    .mdl-sheet--opened .mdl-sheet__icon {
        display: none;
    }
    .mdl-sheet--opened .mdl-sheet__content {
        display: block;
    }

    .close-insert {
        color: white;
        cursor: pointer;
        float: right;
        padding: 10px;
    }

</style>