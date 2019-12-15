<template>
    <div id="summary">
        <div class="title"> 请假管理系统 </div>
        <el-row :gutter="20">
            <el-col :span="6">
                <summary-card bgc="#3598DC" content="今日申请请假人数" :value="summary.todayLeave" bgimg="el-icon-s-data"></summary-card>
            </el-col>
            <el-col :span="6">
                <summary-card bgc="#FF6666" content="昨日申请请假人数" :value="summary.yesterdayLeave" bgimg="el-icon-document"></summary-card>
            </el-col>
            <el-col :span="6">
                <summary-card bgc="#32C6D4" content="本周申请请假人数" :value="summary.weekLeave" bgimg="el-icon-s-order"></summary-card>
            </el-col>
            <el-col :span="6">
                <summary-card bgc="#99CCCC" content="上周申请请假人数" :value="summary.lastWeekLeave" bgimg="el-icon-eleme"></summary-card>
            </el-col>
        </el-row>
        <el-row :gutter="20" style="margin-top: 20px;">
            <el-col :span="24">
                <detail-card-map :base-url="baseUrl"></detail-card-map>
            </el-col>
            <!-- <el-col :span="12">
                <detail-card-value></detail-card-value>
            </el-col> -->
        </el-row>
        <el-row :gutter="20" style="margin-top: 20px;">
            <el-col :span="24">
                <num-detail-card :base-url="baseUrl"></num-detail-card>
            </el-col>
        </el-row>
    </div>
</template>

<script>
    import SummaryCard from "@/components/SummaryCard";
    import DetailCardMap from "./card/MapDetailCard"
    import DetailCardValue from "./card/ValueDetailCard";
    import NumDetailCard from "./card/NumDetailCard";
    export default {
        name: "Summary",
        components: {NumDetailCard, DetailCardValue, DetailCardMap, SummaryCard},
        created(){
            if (this.$store.state.authType === '辅导员'){
                this.baseUrl = 'cou';
            }else if(this.$store.state.authType === '院领导'){
                this.baseUrl = 'col';
            }
            
            this.$http.post(`/${this.baseUrl}/getSummary`).then(res=>{
                const data = res.data;
                if (data.code === 200){
                    this.summary.todayLeave = data.data[0];
                    this.summary.yesterdayLeave = data.data[1];
                    this.summary.weekLeave = data.data[2];
                    this.summary.lastWeekLeave = data.data[3];
                }
            })
        },
        data(){
            return {
                baseUrl: '',
                summary: {
                    todayLeave: 0,
                    yesterdayLeave: 0,
                    weekLeave: 0,
                    lastWeekLeave: 0,
                },

            }
        }
    }
</script>

<style scoped lang="scss">
    .title{
        height: 100px;
        display: flex;
        justify-content: center;
        align-items: center;
        font-size: 30px;
        color: #666;
        font-weight: 900;
    }
</style>