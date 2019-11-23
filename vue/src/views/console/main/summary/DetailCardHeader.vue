<template>
    <div class="detail-card-header">
        <div class="text" >
            <i :class="ico" :style="{'color':icoColor}"></i>
            <span :style="{'color':contentColor}">{{ content }}</span>
        </div>
        <div class="button">
            <el-radio-group v-model="choose" size="small" :fill="buttonFill">
                <el-radio-button v-for="{content} in buttonGroup" :label="content" :key="content"></el-radio-button>
            </el-radio-group>
        </div>
    </div>
</template>

<script>
    export default {
        name: "DetailCardHeader",
        props: {
            ico: {type: String},
            icoColor: {type: String},
            content: {require: true},
            contentColor: {type: String},
            buttonGroup:{type: Array},
            buttonFill:{type: String, default: "#409eff"}
        },
        data(){
            return{
                choose: this.buttonGroup[0].content
            }
        },
        watch:{
            choose: function (newValue) {
                let i=0;
                for(; i<this.buttonGroup.length; ++i) if (this.buttonGroup[i].content===newValue) break;
                this.buttonGroup[i].method();
            }
        }

    }
</script>

<style scoped lang="scss">
    .detail-card-header{
        display: flex;
        justify-content: center;
        align-items: center;
    }
    .text{
        flex: 1;
        span{
            font-weight: bolder;
            margin-left: 5px;
        }
        i{
            font-size: 25px;
        }
    }
    .button{
        right: 0;
    }
</style>