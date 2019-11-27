<template>
    <div class="archive">
        <div style="height: 50px;">
            <DateFilter :dateChange="dateChange"></DateFilter>
            <el-button type="primary" :disabled="data===null" @click="expectClick" style="margin-left: 10px;"> 确认导出 </el-button>
        </div>
        <table-main :tableData="data" :column="column" :hiddenColumn="hiddenColumn" subHeight="60px" :load="loadData">
        </table-main>
    </div>
</template>

<script>
    import TableMain from "@/components/TableMain";
    import DateFilter from "@/components/DateFilter";

    export default {
        name: "Archive",
        components: {DateFilter, TableMain},
        methods:{
            expectClick(){
                this.$message.info("导出");
            },
            dateChange(date){
                console.log(date);
                if (date === null){
                    this.data = null;
                }else {
                    this.data = [
                        {
                            uid: '1',
                            name: '金凯凯',
                            number: '189050416',
                            counselor: '赵雅静',
                            class: '189050113',
                            type: '公假',
                            detail: '去比赛',
                            duration: '2天',
                            startTime: '2019年11月24日16:44:04',
                            endTime: '2019年11月26日16:44:10',

                            showWhat: 'button'
                        }, {
                            uid: '2',
                            name: '金凯凯',
                            number: '189050416',
                            counselor: '赵雅静',
                            class: '189050113',
                            type: '公假',
                            detail: '去比赛',
                            duration: '2天',
                            startTime: '2019年11月24日16:44:04',
                            endTime: '2019年11月26日16:44:10',

                            showWhat: 'allow'
                        }, {
                            uid: '3',
                            name: '金凯凯',
                            number: '189050416',
                            counselor: '赵雅静',
                            class: '189050113',
                            type: '公假',
                            detail: '去比赛',
                            duration: '2天',
                            startTime: '2019年11月24日16:44:04',
                            endTime: '2019年11月26日16:44:10',

                            showWhat: 'reject'
                        }];
                }
            },
            loadData(){
                const a = new Array(20).fill({
                    uid: '1',
                    name: '金凯凯',
                    number: '189050416',
                    counselor: '赵雅静',
                    class: '189050113',
                    type: '公假',
                    detail: '去比赛',
                    duration: '2天',
                    startTime: '2019年11月24日16:44:04',
                    endTime: '2019年11月26日16:44:10',

                    showWhat: 'button'
                });
                //this.data = this.data.concat(a);
                this.$message.info("no data");
            }
        },
        data(){
            return{
                column: [
                    {title: "ID", prop: "uid"},{title: "学号", prop: "number"},
                    {title: "姓名", prop: "name"}, {title: "辅导员", prop: "counselor"},
                    {title: "请假类型", prop: "type"}, {title: "具体原因", prop: "detail"},
                    {title: "请假天数", prop: "duration"}],
                hiddenColumn: [
                    {title: "班级", prop: "class"},{title: "开始时间", prop: "startTime"},
                    {title: "结束时间", prop: "endTime"}],
                filterArray: [
                    {title: "请假类型", prop: "type", extra:[{title: "公假", prop: "公假"},
                            {title: "事假", prop: "事假"},{title: "病假", prop: "病假"}]},
                    {title: "具体原因", prop: "detail"},
                    {title: "学期", prop: 'team', extra:[{title: "后端添加数据", prop: "2018-2019-1"}]},
                ],
                data: null
            }
        }
    }
</script>

<style scoped>

</style>