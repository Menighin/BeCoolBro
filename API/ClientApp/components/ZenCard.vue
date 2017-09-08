<template>
    <div class="mdl-cell mdl-card mdl-shadow--4dp portfolio-card">
        <div class="mdl-card__media">
            <img class="article-image" :src="imgSrc" border="0" alt="">
        </div>
        <div class="mdl-card__title">
            <h2 class="mdl-card__title-text">{{ title }}</h2>
        </div>
        <div class="mdl-card__supporting-text">
            <slot></slot>
        </div>
        <div class="mdl-card__actions mdl-card--border">
            <a class="mdl-button mdl-button--colored mdl-js-button mdl-js-ripple-effect mdl-button--accent" href="portfolio-example01.html">Compartilhar</a>
            <div class="mdl-layout-spacer"></div>
    	    <button class="mdl-button mdl-button--icon mdl-button--colored"><i class="fa fa-thumbs-up"></i></button>
		    <button class="mdl-button mdl-button--icon mdl-button--colored"><i class="fa fa-thumbs-down"></i></button>
        </div>
    </div>
</template>

<script>

    export default {
        computed: {
            imgSrc() {
                return 'data:image/png;base64,' + this.img;
            }
        },
        props: {
            img: {
                type: String,
                required: true
            },
            title: {
                type: String,
                required: true
            }
        }
    }

</script>