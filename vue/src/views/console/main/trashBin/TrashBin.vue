<template>
    <div class="trash-bin">
        <table-main :tableData="data" :column="column" customButtonClick
                    customButtonWidth="150px" columnMinWidth="160px">
            <div slot="button" slot-scope="scope">
                <el-button type="text" size="small" @click="buttonClick(scope.data.row,'reduction')">还原</el-button>
                <el-button type="text" size="small" @click="buttonClick(scope.data.row,'del')" style="color: red">彻底删除</el-button>
            </div>
        </table-main>
    </div>
</template>

<script>
    import TableMain from "@/components/TableMain";

    export default {

        name: "TrashBin",
        components: {TableMain},
        methods: {
            buttonClick(row, e){
                let index = this.data.findIndex(_=>{return _.uid===row.uid});
                this.data.splice(index,1);
                if (e === 'reduction'){
                    this.$message.success("还原成功");
                }else if (e === 'del'){
                    this.$message.success("删除成功");
                }
            }
        },
        data(){
            return{
                column: [
                    {title: "开始时间", prop: "startTime"},{title: "结束时间", prop: "endTime"},
                    {title: "请假天数", prop: "duration"}, {title: "请假类型", prop: "type"},
                    {title: "具体原因", prop: "detail"}],
                data:[{
                    uid: '1',
                    sendTime: '2019-11-30 15:08:21',
                    counselor: '赵雅静',
                    type: '公假',
                    detail: '去比赛',
                    duration: '2天',
                    startTime: '2019-11-24 16:44:04',
                    endTime: '2019-11-26 16:44:10',

                    showWhat: 'button'
                },{
                    uid: '2',
                    sendTime: '2019-11-30 15:08:21',
                    counselor: '赵雅静',
                    type: '公假',
                    detail: '去比赛',
                    duration: '2天',
                    startTime: '2019-11-24 16:44:04',
                    endTime: '2019-11-26 16:44:10',

                    showWhat: 'button'
                }]
            }
        }
    }
</script>

<style scoped>
    .trash-bin{
        height: calc(100% - 10px);
    }
</style>