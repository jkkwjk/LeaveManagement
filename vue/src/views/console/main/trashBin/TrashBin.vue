<template>
    <div class="trash-bin">
        <table-main :tableData="tableData" :column="column" customButtonClick
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
    import dateUtil from "@/util/dateutil";

    export default {

        name: "TrashBin",
        components: {TableMain},
        created(){
            this.$http.get('/stu').then(res=>{
                const data = res.data;
                if (data.code === 200){
                    this.data = data.data;
                }
            });
        },
        computed: {
            tableData(){
                return this.data.map(_=>{
                    const s = new Date(_.startTime);
                    const e = new Date(_.endTime);
                    _.duration = dateUtil.calcDate(s,e) + '天';
                    _.startTime = dateUtil.formatChina(s);
                    _.endTime = dateUtil.formatChina(e);
                    if (_.sendTime !== null && _.sendTime !== '未发送'){
                        _.sendTime = dateUtil.formatChina(new Date(_.sendTime));
                    }else {
                        _.sendTime = '未发送';
                    }
                    return _;
                });
            }
        },
        methods: {
            buttonClick(row, e){
                
                if (e === 'reduction'){
                    this.$http.post('stu/reduction',{
                        id: row.id
                    }).then(res=>{
                        const data = res.data;
                        if (data.code === 200){
                            let index = this.data.findIndex(_=>{return _.id===row.id});
                            this.data.splice(index,1);
                            this.$message.success("还原成功");
                        }else {
                            this.$message.error(data.msg);
                        }
                    });
                    
                }else if (e === 'del'){
                    this.$http.post('stu/del',{
                        id: row.id
                    }).then(res=>{
                        const data = res.data;
                        if (data.code === 200){
                            let index = this.data.findIndex(_=>{return _.id===row.id});
                            this.data.splice(index,1);
                            this.$message.success("删除成功");
                        }else {
                            this.$message.error(data.msg);
                        }
                    });
                }
            }
        },
        data(){
            return{
                column: [
                    {title: "开始时间", prop: "startTime"},{title: "结束时间", prop: "endTime"},
                    {title: "请假天数", prop: "duration"}, {title: "请假类型", prop: "type"},
                    {title: "具体原因", prop: "detail"}],
                data:[]
            }
        }
    }
</script>

<style scoped>
    .trash-bin{
        height: calc(100% - 10px);
    }
</style>