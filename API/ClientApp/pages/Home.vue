<template>
        <div class="mdl-grid portfolio-max-width">
            <zen-card v-for="q in quotes" :img="q" title="ZenCard">
                This is a Zen Card. Use it wisely.
            </zen-card>
        </div>
</template>

<script>

    import ZenCard from '../components/ZenCard';

    export default {
        data() {
            return {
            }
        },
        components: {
            zenCard: ZenCard
        },
        methods: {
            
        },
        computed: {
            quotes() {
                return this.$store.getters.quotes;
            }
        },
        created() {
            this.$store.dispatch("fetchQuotes");
        }
    }
</script>

<style>
</style>