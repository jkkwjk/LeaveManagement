<template>
    <div class="archive">
        <div style="height: 50px;">
            <DateFilter :dateChange="dateChange"></DateFilter>
            <el-button type="primary" :disabled="data.length === 0" @click="expectClick" style="margin-left: 10px;"> 确认导出 </el-button>
        </div>
        <table-main :tableData="tableData" :column="column" :hiddenColumn="hiddenColumn" subHeight="60px" :load="loadData"
                    columnMinWidth="160px">
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
        created(){
            switch(this.$store.state.authType){
                case '学生':
                    this.baseUrl = 'stu';
                    break;
                case '辅导员':
                    this.baseUrl = 'cou';
                    break;
                case '院领导':
                    this.baseUrl = 'col';
                    break;
            }
        },
        computed: {
            tableData(){
                return this.data.map(_=>{
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
            expectClick(){
                window.location.href = `${this.$http.defaults.baseURL}${this.baseUrl}/getArchive?startTime=${this.currentDate[0].getTime()}&endTime=${this.currentDate[1].getTime()}`;
            },
            dateChange(date){
                if (date === null){
                    this.data = null;
                    this.currentDate = null;
                }else {
                    date[1].setDate(dateUtil.daysInMonth(date[1])); // 设置结束时间为 月底
                    this.currentDate = date;
                    this.data = [];
                    this.page = 1;
                    this.hasNext = true;
                    this.loadData();
                }
            },
            loadData(){
                if(this.hasNext && !this.lock && this.currentDate != null){
                    this.lock = true;
                    const startTime = this.currentDate[0].getTime();
                    const endTime = this.currentDate[1].getTime();
                    this.$http.post(`/${this.baseUrl}/getArchive`,{
                        page: this.page,
                        num: this.num,
                        startTime: startTime,
                        endTime: endTime,
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
                page: 1,
                num: 20,
                hasNext: true,
                lock: false,
                currentDate: null,
                baseUrl: '',
                column: [
                    {title: "发送时间", prop: "sendTime"},{title: "学号", prop: "number"},
                    {title: "姓名", prop: "name"}, {title: "辅导员", prop: "counselor"},
                    {title: "请假类型", prop: "type"}, {title: "具体原因", prop: "detail"}],
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