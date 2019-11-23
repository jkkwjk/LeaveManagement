<template>
    <el-card shadow="hover" class="summary-card"
             :body-style="[{'height':'100%'},{'padding':'0'},{'display':'flex'},{'flex-direction':'column'}]">
        <div class="bg-ico">
            <i :class="bgimg"> </i>
        </div>
        <div class="top" :style="{'background-color':bgc}">
            <div class="show-msg">
                <div class="num">{{ value }}</div>

                <div class="content">{{ content }}</div>
            </div>
        </div>
        <div class="bottom" :style="{'background-color':darkenBgc}">

        </div>

    </el-card>
</template>

<script>
    import {getDarkColor} from '@/util/ColorUtil.js'
    export default {
        name: "SummaryCard",
        props: {
            bgc: {required: true},
            value: {type: Number},
            content: {type: String},
            bgimg: {type: String}
        },
        computed:{
            darkenBgc(){
                return getDarkColor(this.bgc,0.15);
            }
        }
    }

</script>

<style scoped lang="scss">
    .summary-card{
        height: 130px;
        overflow: hidden;
        position: relative;
        .bg-ico{
            position: absolute;
            z-index: 1;
            color: rgba(255,255,255,0.06);
            font-size: 70px;
            left: -10px;
            bottom: 0;
        }
        .top{
            position: relative;
            flex: 5;
        }
        .bottom{
            flex: 1;
        }
    }
    .card-body{
        width: 100%;
        height: 100%;
    }
    .show-msg{
        position: absolute;
        bottom: 5px;
        right: 5px;
        display: flex;
        text-align: right;
        color: white;
        flex-direction: column;
        .num{
            font-size: 30px;
        }
        .content{
            font-size: 15px;
        }
    }
</style>