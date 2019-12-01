<template>
    <div class="leave-management">
        <table-filter-box height="50px" :column="filterArray" :changeFilter="changeFilter">
        </table-filter-box>
        <table-main :tableData="data" :column="column" :hiddenColumn="hiddenColumn"
                    :willSort="willSort" subHeight="50px" :load="loadData"
                    :buttonClick="buttonClick">
        </table-main>
    </div>

</template>

<script>
    import TableMain from "@/components/TableMain";
    import TableFilterBox from "@/components/TableFilterBox";
    export default {
        name: "leaveManagement",
        components: {TableFilterBox, TableMain},
        methods:{
            buttonClick(row, type){
                row.showWhat = type;
                if (type === 'allow'){
                    // 对多次同一理由请假的学生弹出dialog进行提醒
                    console.log(row.uid);
                }else if(type === 'reject') {
                    console.log(row.uid);
                }
            },
            willSort({ prop, order }){
                this.filter.sort = { prop, order };
            },
            changeFilter(v){
                this.filter.custom = v;
            },
            loadData(){
                this.$message.info("i am load");
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
                this.data = this.data.concat(a);
            }
        },
        data(){
            return{
                filter: {
                    sort: {
                        prop: '',
                        type: ''
                    },
                    custom: []
                },
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
                            {title: "事假", prop: "事假"},{title: "病假", prop: "病假"},]},
                    {title: "具体原因", prop: "detail"},
                    {title: "学期", prop: 'team', extra:[{title: "后端添加数据", prop: "2018-2019-1"}]},
                ],
                data: [
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
                },{
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
                },{
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
                }]
            }
        }
    }
</script>

<style scoped>
    .leave-management{
        overflow: hidden;
        height: calc(100% - 10px); /*适配chrome*/
    }
</style>