<template>
    <el-card shadow="hover" class="detail-card"
             :body-style="[{'height':'100%'},{'padding':'0'},{'display':'flex'},{'flex-direction':'column'}]">
        <detail-card-header content="请假学生地域分布" ico="el-icon-s-promotion" ico-color="#3a87c1"
                            contentColor="#8bb9eb" :button-group="buttonGroup">
        </detail-card-header>
        <el-divider style="margin: 0 10px;"></el-divider>
        <div class="content">
            <div class="tip">* 生源地中申请请假的学生与该生源地学生总数的比值</div>
            <div id="positionChart" ref="positionChart"></div>
        </div>
    </el-card>
</template>

<script>
    import DetailCardHeader from "../DetailCardHeader";
    import getOptions from './MapChart';
    import f from './MapChart.json';

    export default {
        name: "DetailCardMap",
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
                this.$message.info("mouth");
            },
            theYear(){
                this.$message.info("year");
            },
            all(){
                this.$message.info("all");
            }
        },
        mounted() {
            const mapChart = this.$echarts.init(this.$refs.positionChart);
            mapChart.setOption(getOptions(f));
            window.addEventListener("resize", () => {
                mapChart.resize();
            });
        }
    }
</script>

<style scoped lang="scss">
    @import "./normal";
    #positionChart{
        position: absolute;
        width: 100%;
        margin-top: 20px;
        height: 95%;
    }
</style>