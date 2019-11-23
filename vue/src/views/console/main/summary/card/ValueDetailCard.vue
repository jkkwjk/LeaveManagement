<template>
    <el-card shadow="hover" class="detail-card"
             :body-style="[{'height':'100%'},{'padding':'0'},{'display':'flex'},{'flex-direction':'column'}]">
        <detail-card-header content="请假与成绩相关性" ico="el-icon-circle-plus" ico-color="#CC3333"
                            contentColor="#d73434" :button-group="buttonGroup" buttonFill="#CC3333">
        </detail-card-header>
        <el-divider style="margin: 0 10px;"></el-divider>
        <div class="content">
            <div class="tip">* 当有某一区间特别突出时,代表请假与成绩具有相关性,反之则无</div>
            <div id="valueChart" ref="valueChart"></div>
        </div>
    </el-card>
</template>

<script>
    import DetailCardHeader from "../DetailCardHeader";
    import getOptions from './ValueChart';
    import f from './ValueChart.json';
    export default {
        components: {DetailCardHeader},
        data(){
            return {
                buttonText: ['本月','全年','所有'],
                buttonClick: [this.theMouth,this.theYear,this.all],
            }
        },
        computed: {
            buttonGroup(){
                let ret = [];
                for (let i=0; i<this.buttonText.length; ++i){
                    ret.push({
                        'content':this.buttonText[i],
                        'method':this.buttonClick[i]
                    });
                }
                return ret;
            }
        },
        methods: {
            theMouth(e){
                this.$message.info("mouth1");
            },
            theYear(){
                this.$message.info("year1");
            },
            all(){
                this.$message.info("all1");
            }
        },
        mounted() {
            const valueChart = this.$echarts.init(this.$refs.valueChart);
            valueChart.setOption(getOptions(f));
            window.addEventListener("resize", () => {
                valueChart.resize();
            });
        }
    }
</script>

<style scoped lang="scss">
    @import "./normal";
    #valueChart{
        position: absolute;
        width: 100%;
        margin-top: 20px;
        height: 95%;
    }
</style>