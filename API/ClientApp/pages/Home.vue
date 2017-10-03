<template>
        <div class="mdl-grid portfolio-max-width" v-infinite-scroll="loadMoreQuotes" infinite-scroll-disabled="infiniteScroll" infinite-scroll-distance="50">
            <zen-card v-for="q in quotes" :zenQuote="q" :key="q.id"> </zen-card>
            
            <div class="mdl-cell mdl-cell--12-col" style="text-align: center" v-if="loading">
                <div class="mdl-spinner mdl-js-spinner is-active"></div>
            </div> 
        </div>
</template>

<script>

    import ZenCard from '../components/ZenCard';

    export default {
        data() {
            return {
                loading: false,
                allPages: false,
                page: 1
            }
        },
        components: {
            zenCard: ZenCard
        },
        methods: {
            loadMoreQuotes() {
                this.loading = true;
                var self = this;

                setTimeout(() => { componentHandler.upgradeDom();}, 10);

                this.$store.dispatch("fetchQuotes", { 
                    page: self.page, 
                    successCallback: function(json) {
                        self.loading = false;
                        self.page++;
                        if(json.length == 0) self.allPages = true;
                    }
                });
                
            }
        },
        computed: {
            quotes() {
                return this.$store.getters.quotes;
            },
            infiniteScroll() {
                console.log(this.loading || this.allPages);
                return this.loading || this.allPages;
            }
        }
    }
</script>

<style>
</style>