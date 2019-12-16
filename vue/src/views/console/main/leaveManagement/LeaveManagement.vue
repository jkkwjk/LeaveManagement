<template>
    <div class="leave-management">
        <table-filter-box height="50px" :column="filterArray" :changeFilter="changeFilter">
        </table-filter-box>
        <table-main :tableData="tableData" :column="column" :hiddenColumn="hiddenColumn"
                    :willSort="willSort" subHeight="50px" :load="loadData"
                    :buttonClick="buttonClick" columnMinWidth="160px">
        </table-main>
    </div>

</template>

<script>
    import TableMain from "@/components/TableMain";
    import TableFilterBox from "@/components/TableFilterBox";
    import dateUtil from "@/util/dateutil";

    export default {
        name: "leaveManagement",
        components: {TableFilterBox, TableMain},
        created(){
            if (this.$store.state.authType === '辅导员'){
                this.baseUrl = 'cou';
                // 如果是辅导员的话, 删除辅导员列
                const index = this.column.findIndex(_=>{return _.prop === 'counselor'})
                this.column.splice(index,1);
            }else if(this.$store.state.authType === '院领导'){
                this.baseUrl = 'col';
            }
            this.$http.post('/sys/getTeam').then(res=>{
                const data = res.data;
                if (data.code === 200){
                    let template = {title: "学期", prop: 'team', extra:[]};
                    data.data.map(_=>{ template.extra.push({title: _, prop: _}) });
                    this.filterArray.push(template);
                }
            })
        },
        watch: {
            filter:{
                handler(val){
                    this.page = 1;
                    this.data = [];
                    this.hasNext = true;
                    this.loadData();
                },
                deep: true
            }
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
        methods:{
            buttonClick(row, type){
                if (type === 'allow'){
                    // 对多次同一理由请假的学生弹出dialog进行提醒
                    this.$http.post(`/${this.baseUrl}/allow/${row.id}`).then(res=>{
                        const data = res.data;
                        if (data.code === 200){
                            row.showWhat = type;
                            this.$message.success("已同意申请");
                        }else {
                            this.$message.error(data.msg);
                        }
                    })
                }else if(type === 'reject') {
                    this.$http.post(`/${this.baseUrl}/reject/${row.id}`).then(res=>{
                        const data = res.data;
                        if (data.code === 200){
                            row.showWhat = type;
                            this.$message.success("已拒绝申请");
                        }else {
                            this.$message.error(data.msg);
                        }
                    })
                }
            },
            willSort({ prop, order }){
                this.filter.sort = { prop, order };
            },
            changeFilter(v){
                this.filter.custom = v;
            },
            loadData(){
                console.log("in",this.lock);
                if(this.hasNext && !this.lock){
                    this.lock = true;
                    this.$http.post(`/${this.baseUrl}`,{
                        page: this.page,
                        num: this.num,
                        custom: JSON.stringify(this.filter)
                    }).then(res=>{
                        const list = res.data.data;
                        if (list.length < this.num){
                            this.hasNext = false;
                        }else {
                            this.page++;
                        }
                        this.lock = false;
                        this.data = this.data.concat(list);
                    }).catch(res=>{
                        this.lock = false;
                    });
                }
            }
        },
        data(){
            return{
                baseUrl: '',
                page: 1,
                num: 20, // 存在bug 只有有滚动条的时候才能分页
                hasNext: true,
                lock: false,
                filter: {
                    sort: {
                        prop: '',
                        type: ''
                    },
                    custom: []
                },
                column: [
                    {title: "发送时间", prop: "sendTime"},{title: "学号", prop: "number"},
                    {title: "姓名", prop: "name"}, {title: "辅导员", prop: "counselor"},
                    {title: "请假类型", prop: "type"}, {title: "具体原因", prop: "detail"},
                    {title: "请假天数", prop: "duration"}],
                hiddenColumn: [
                    {title: "班级", prop: "class"},{title: "开始时间", prop: "startTime"},
                    {title: "结束时间", prop: "endTime"}],
                filterArray: [
                    {title: "请假类型", prop: "type", extra:[{title: "公假", prop: "公假"},
                            {title: "事假", prop: "事假"},{title: "病假", prop: "病假"},]},
                    {title: "具体原因", prop: "detail"}
                ],
                data: []
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