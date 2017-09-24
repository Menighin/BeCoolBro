<template>
    <div class="mdl-grid portfolio-max-width">
        <h1>ADMIN</h1>
    </div>
</template>

<script>

    export default {
        created() {
            this.$store.dispatch("fetchInvalidQuotes");
        }
    }

</script>

<style>

</style>