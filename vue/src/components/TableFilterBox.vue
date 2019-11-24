<template>
    <div class="table-filter-box" :style="{'height': height}">

    </div>
</template>

<script>
    export default {
        name: "TableFilterBox",
        props: {
            height:{type: String, require: true}
        }
    }
</script>

<style scoped>
    .table-filter-box{

    }

</style>