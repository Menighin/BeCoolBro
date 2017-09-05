<template>
    <div id="app" class="mdl-layout mdl-js-layout mdl-layout--fixed-header">

        <zen-header></zen-header>
        
        <main class="mdl-layout__content">
            <div class="mdl-grid portfolio-max-width">
                <router-view></router-view>
            </div>
        </main>
    </div>

</template>

<script>

import Header from './components/Header';

export default {
    components: {
        zenHeader: Header
    }
}
</script>

<style>
</style>