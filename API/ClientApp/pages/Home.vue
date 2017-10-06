<template>
    <div>
        <div class="mdl-grid zen-tags-max-width">
            <tag-input class="mdl-cell mdl-cell--1-col" v-for="t in tags" :key="t.id" :tagId="t.id" :color="t.color" @change="checkTag">{{ t.label }}</tag-input>
        </div>

        <div class="mdl-grid portfolio-max-width" v-infinite-scroll="loadMoreQuotes" infinite-scroll-disabled="infiniteScroll" infinite-scroll-distance="50">
            <zen-card v-for="q in quotes" :zenQuote="q" :key="q.id"> </zen-card>
            
            <div class="mdl-cell mdl-cell--12-col" style="text-align: center" v-if="loading">
                <div class="mdl-spinner mdl-js-spinner is-active"></div>
            </div> 
        </div>
    </div>
</template>

<script>

    import ZenCard from '../components/ZenCard';
    import { EventBus } from '../eventBus.js';
    import TagInput from '../components/form/TagInput';

    export default {
        data() {
            return {
                loading: false,
                allPages: false,
                page: 1,
                tagsIds: new Set()
            }
        },
        components: {
            zenCard: ZenCard,
            tagInput: TagInput
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
                    },
                    search: self.searchText,
                    tags: Array.from(self.tagsIds)
                });
            },
            checkTag(id, value) {
                if (value) {
                    this.tagsIds.add(parseInt(id));
                } else {
                    this.tagsIds.delete(parseInt(id));
                }
                this.page = 1;
                this.$store.dispatch('clearQuotes');
                this.loadMoreQuotes();
            }
        },
        computed: {
            quotes() {
                return this.$store.getters.quotes;
            },
            infiniteScroll() {
                return this.loading || this.allPages;
            },
            tags() {
                let t = this.$store.getters.tags;
                let result = [];
                t.forEach((v) => {
                    result.push({ label: v.name, id: v.id, color: v.color });
                });
                return result;
            }
        },
        mounted() {
            let self = this;
            EventBus.$on('searchQuotes', function (searchText) {
                self.searchText = searchText;
                self.page = 1;
                self.$store.dispatch('clearQuotes');
                self.loadMoreQuotes();
            });
        },
        created() {
            this.$store.dispatch('fetchTags');
        }
    }
</script>

<style>
</style>