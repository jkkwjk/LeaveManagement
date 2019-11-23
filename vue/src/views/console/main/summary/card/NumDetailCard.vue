<template>
    <el-card shadow="hover" class="detail-card"
             :body-style="[{'height':'100%'},{'padding':'0'},{'display':'flex'},{'flex-direction':'column'}]">
        <detail-card-header content="学生请假人数统计" ico="el-icon-s-data" ico-color="#3a87c1"
                            contentColor="#8bb9eb" :button-group="buttonGroup" button-fill="#CC3333">
        </detail-card-header>
        <el-divider style="margin: 0 10px;"></el-divider>
        <div class="content">
            <div class="tip">* 因各种原因请假的学生人数,越上面表示该原因请假的总人数越多</div>
            <div id="numChart" ref="numChart"></div>
        </div>
    </el-card>
</template>

<script>
    import DetailCardHeader from "../DetailCardHeader";
    import getOptions from './NumChart';
    import f from './NumChart.json';
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
                this.$message.info("mouth2");
            },
            theYear(){
                this.$message.info("year2");
            },
            all(){
                this.$message.info("all2");
            }
        },
        mounted() {
            const numChart = this.$echarts.init(this.$refs.numChart);
            numChart.setOption(getOptions(f));
            window.addEventListener("resize", () => {
                numChart.resize();
            });
        }
    }
</script>

<style scoped lang="scss">
    @import "./normal";
    #numChart{
        position: absolute;
        width: 100%;
        margin-top: 20px;
        height: 95%;
    }
</style>