<template>
    <div class="zen-insert portfolio-max-width">
        <div class="insert-title"><h2>New Quote!</h2></div>
        <form action="#">
            <div class="mdl-textfield mdl-js-textfield mdl-textfield--floating-label">
				<input class="mdl-textfield__input" type="text" id="author" />
				<label class="mdl-textfield__label" for="author">Author</label>
				<span class="mdl-textfield__error">Only alphabet and no spaces, please!</span>
			</div>
        </form>
    </div>
</template>

<style scoped>

    .zen-insert {
        padding: 10px 30px;
    }

    .insert-title {
        display: block;
        margin: 0 auto;
        color: white;
        text-align: center;
    }

    form {
        text-align: center;
        color: white;
    }

    .mdl-textfield {
        width: 100%;
    }

    .mdl-textfield__input {
        border-bottom: 1px solid rgba(255, 255, 255, 0.5);
        color: white;
    }

    .mdl-textfield__label {
        color: white !important;
    }

    .mdl-textfield__label:after {
        background-color: white;
    }

</style>