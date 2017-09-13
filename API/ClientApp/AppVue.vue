<template>
    <div id="app" class="mdl-layout mdl-js-layout mdl-layout--fixed-header">

        <zen-header></zen-header>
        
        <main class="mdl-layout__content">
            <router-view></router-view>

            <div class="mdl-sheet__container">
                <div class="mdl-sheet mdl-shadow--2dp" :class="{ 'mdl-sheet--opened': fabOpened}" @click="fabOpened = !fabOpened">
                    <i class="material-icons mdl-sheet__icon">add</i>
                    
                    <div class="mdl-sheet__content">
                        <div class="mdl-list">
                        <div class="mdl-list__item">
                            <span class="mdl-list__item-primary-content">
                            <i class="material-icons mdl-list__item-avatar">person</i>
                            <span>Bryan Cranston</span>
                            </span>
                            <a class="mdl-list__item-secondary-action" href="#"><i class="material-icons">add</i></a>
                        </div>
                        <div class="mdl-list__item">
                            <span class="mdl-list__item-primary-content">
                            <i class="material-icons mdl-list__item-avatar">person</i>
                            <span>Aaron Paul</span>
                            </span>
                            <a class="mdl-list__item-secondary-action" href="#"><i class="material-icons">add</i></a>
                        </div>
                        <div class="mdl-list__item">
                            <span class="mdl-list__item-primary-content">
                            <i class="material-icons mdl-list__item-avatar">person</i>
                            <span>Bob Odenkirk</span>
                            </span>
                            <span class="mdl-list__item-secondary-content">
                            <a class="mdl-list__item-secondary-action" href="#"><i class="material-icons">star</i></a>
                            </span>
                        </div>
                        </div>
                    </div>
                </div>
            </div>

        </main>
    </div>

</template>

<script>

import Header from './components/Header';

export default {
    data() {
        return {
            fabOpened: false
        };
    },
    components: {
        zenHeader: Header
    }
    //https://codepen.io/anon/pen/VMwyay?editors=0010
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

    .mdl-sheet {
        position: absolute;
        right: 0;
        bottom: 0;
        background: #FF5722;
        width: 60px;
        height: 60px;
        border-radius: 50%;
        cursor: pointer;
        -webkit-transition: all 600ms;
        transition: all 600ms;
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
        background: #fff;
        color: #263238;
        border-radius: 2px;
        width: 250px;
        height: auto;
        min-height: 150px;
        overflow-y: auto;
        transform: translate(-50%, -50%);
    }
    .mdl-sheet--opened .mdl-sheet__icon {
        display: none;
    }
    .mdl-sheet--opened .mdl-sheet__content {
        display: block;
    }

</style>