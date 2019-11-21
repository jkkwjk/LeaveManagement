<template>
    <div class="ico-group">
        <i :class="ico"></i>
        <span>{{ content }}</span>
        <el-badge class="item" v-if="value" :value="value" :type="type" :max="99"></el-badge>
    </div>
</template>

<script>
    export default {
        name: "IcoGroup",
        props: {
            content: {type: String},
            ico:{type: String},
            value:{type: [String,Number]},
            type:{type: String},
        }
    }
</script>

<style scoped lang="scss">
    .ico-group{
        span{
            margin-left: 5px;
        }
        .item{
            position: absolute;
            top: -10px;
            left: 30px;
            opacity: 0.95;
        }
    }
</style>