<template>
    <div id="head">
        <div class="left">
            <i class="el-icon-s-platform"></i>
            <span class="title">请假管理系统</span>
        </div>
        <div class="right">
            <ico-group ico="el-icon-bell" content="提醒" :value="101" type="info"></ico-group>

        </div>
    </div>
</template>

<script>
    import IcoGroup from "./IcoGroup";
    export default {
        name: "Head",
        components: {IcoGroup}
    }
</script>

<style scoped lang="scss">
    #head{
        width: 100%;
        height: 100%;
        background-color: #fff;
        display: flex;
    }
    .left{
        display: flex;
        flex: 1;
        align-items: center;
        padding-left: 30px;
        i{
            font-size: 20px;
            color: darken(#82B1DF,10);
        }
        .title{
            font-size: 20px;
            color: #82B1DF;
            margin-left: 15px;
        }
    }
    .right{
        display: flex;
        align-items: center;
        .ico-group{
            width: 100px;
            color: #666;
            font-size: 13px;
            font-family: 'Avenir', Helvetica, Arial, sans-serif;
        }
    }

</style>