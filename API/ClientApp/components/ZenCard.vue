<template>
    <div class="mdl-cell mdl-card mdl-shadow--4dp portfolio-card">
        <div class="mdl-card__media">
            <img class="article-image" :src="imgData" border="0" alt="">
        </div>
        <div class="mdl-card__supporting-text zen-message">
            <p>{{ message }}</p>
            <span class="author">{{ zenQuote.author }}</span>
        </div>
        <div class="mdl-card__supporting-text zen-tags">
            <ul>
                <li v-for="(t, i) in zenQuote.tags" :key="i">{{ t | tag }}</li>
            </ul>
        </div>
        <div class="mdl-card__actions mdl-card--border">
            <a class="mdl-button mdl-button--colored mdl-js-button mdl-js-ripple-effect mdl-button--accent" href="portfolio-example01.html">Compartilhar</a>
            <div class="mdl-layout-spacer"></div>
            <div class="zen-vote">
    	        <button class="mdl-button mdl-button--icon mdl-button--colored"><i class="fa fa-thumbs-up"></i></button>
                <span>1M</span>
            </div>
            <div class="zen-vote">
                <button class="mdl-button mdl-button--icon mdl-button--colored"><i class="fa fa-thumbs-down"></i></button>
                <span>1.3K</span>
            </div>
        </div>
    </div>
</template>

<script>

    export default {
        computed: {
            imgData() {
                return 'data:image/png;base64,' + this.zenQuote.image64Encoded;
            },
            message() {
                return this.zenQuote.message.length > 200 ? this.zenQuote.message.substring(0, 200) : this.zenQuote.message;
            }
        },
        props: {
            zenQuote: {
                type: Object,
                required: true
            }
        }
    }

</script>

<style scoped>

    @media (min-width: 480px) {
        .mdl-card__supporting-text.zen-message {
            height: 80px;
        }
        .mdl-card__supporting-text.zen-tags {
            height: 20px;
        }
    }

    .zen-message p {
        line-height: 14px;
        margin-bottom: 4px;
    }

    .zen-message .author {
        font-weight: bold;
        text-align: right;
        width: 100%;
        display: block;
    }

    .mdl-card__supporting-text.zen-tags {
        padding-top: 0;
        padding-bottom: 7px;
    }

    .zen-tags ul {
        margin: 0;
        margin-left: -40px;
    }

    .zen-tags ul li {
        display: inline-block;
        list-style-type: none;
        font-size: 11px;
        color: #999;
        line-height: 11px;
        padding: 4px 4px;
        margin: 0 2px;
        background-color: #eee;
        cursor: pointer;
    }

    .zen-tags li:hover {
        background-color: #ddd;
    }

    .mdl-card__actions {
        display: flex;
        box-sizing: border-box;
        align-items: center;
    }

    .mdl-card__actions .mdl-button--icon {
        color: #ff4081;
        margin-left: 2px;
    }

    .zen-vote {
        text-align: center;
        font-size: 11px;
        color: #aaa;
        position: relative;
    }

    .zen-vote > button, .zen-vote > span {
        display: block;
        position: relative;
    }

</style>