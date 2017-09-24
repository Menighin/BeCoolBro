<template>

    <div class="tag" @click="select" :class="{'active': active}" :style="{'background-color': color}">

        <span><slot></slot></span>

    </div>

</template>

<script>

    export default {
        data() {
            return {
                active: false
            }
        },
        methods: {
            select() {
                this.active = !this.active;
                this.$emit('change', this.tagId, this.active);
            }
        },
        props: {
            tagId: Number,
            color: String
        }
    }

</script>

<style scoped>

    .tag {
        text-align: center;
        cursor: pointer;
    }

    .tag::after {
        content: "";
        display: block;
        text-align: center;
        margin: 0 auto;
        width: 0%;
        height: 2px;
        background-color: white;
        transition: all 100ms;
    }

    .tag:hover::after {
        width: 100%;
    }

    .tag.active::after {
        content: "";
        display: block;
        text-align: center;
        margin: 0 auto;
        width: 100%;
        height: 2px;
        background-color: white;
    }

    .tag.active {
        font-weight: bold;
    }

</style>

