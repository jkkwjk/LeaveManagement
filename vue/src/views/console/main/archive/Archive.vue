<template>
    <div class="archive">
        <div style="height: 50px;">
            <DateFilter :dateChange="dateChange"></DateFilter>
            <el-button type="primary" :disabled="data===null" @click="expectClick" style="margin-left: 10px;"> 确认导出 </el-button>
        </div>
        <table-main :tableData="tableData" :column="column" :hiddenColumn="hiddenColumn" subHeight="60px" :load="loadData">
        </table-main>
    </div>
</template>

<script>
    import TableMain from "@/components/TableMain";
    import DateFilter from "@/components/DateFilter";
    import dateUtil from "@/util/dateutil"
    export default {
        name: "Archive",
        components: {DateFilter, TableMain},
        computed: {
            tableData(){
                return this.data.map(_=>{
                    const s = new Date(_.startTime);
                    const e = new Date(_.endTime);
                    _.duration = dateUtil.calcDate(s,e) + '天';
                    _.startTime = dateUtil.formatChina(s);
                    _.endTime = dateUtil.formatChina(e);
                    if (typeof _.sendTime === "number"){
                        _.sendTime = dateUtil.formatChina(new Date(_.sendTime));
                    }
                    return _;
                });
            }
        },
        methods:{
            expectClick(){
                this.$message.info("导出");
            },
            dateChange(date){
                if (date === null){
                    this.data = null;
                }else {
                    this.data = [
                        {
                            uid: '1',
                            name: '金凯凯',
                            number: '189050416',
                            sendTime: 1575264600817,
                            counselor: '赵雅静',
                            class: '189050113',
                            type: '公假',
                            detail: '去比赛',
                            startTime: 1575264600817,
                            endTime: 1575999606817,

                            showWhat: 'button'
                        }, {
                            uid: '2',
                            name: '金凯凯',
                            number: '189050416',
                            sendTime: 1575264600817,
                            counselor: '赵雅静',
                            class: '189050113',
                            type: '公假',
                            detail: '去比赛',
                            startTime: 1575264600817,
                            endTime: 1575999606817,

                            showWhat: 'allow'
                        }, {
                            uid: '3',
                            name: '金凯凯',
                            number: '189050416',
                            sendTime: 1575264600817,
                            counselor: '赵雅静',
                            class: '189050113',
                            type: '公假',
                            detail: '去比赛',
                            startTime: 1575264600817,
                            endTime: 1575999606817,

                            showWhat: 'reject'
                        }];
                }
            },
            loadData(){
                const a = new Array(20).fill({
                    uid: '1',
                    name: '金凯凯',
                    number: '189050416',
                    sendTime: 1575264600817,
                    counselor: '赵雅静',
                    class: '189050113',
                    type: '公假',
                    detail: '去比赛',
                    startTime: 1575264600817,
                    endTime: 1575999606817,

                    showWhat: 'button'
                });
                //this.data = this.data.concat(a);
                this.$message.info("no data");
            }
        },
        data(){
            return{
                column: [
                    {title: "发送时间", prop: "sendTime"},{title: "学号", prop: "number"},
                    {title: "姓名", prop: "name"}, {title: "辅导员", prop: "counselor"},
                    {title: "请假类型", prop: "type"}, {title: "具体原因", prop: "detail"},
                    {title: "请假天数", prop: "duration"}],
                hiddenColumn: [
                    {title: "班级", prop: "class"},{title: "开始时间", prop: "startTime"},
                    {title: "结束时间", prop: "endTime"}],
                data: []
            }
        }
    }
</script>

<style scoped>

</style>