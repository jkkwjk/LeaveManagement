<template>
    <div class="leave-check">
        <div style="height: 50px;">
            <el-form inline>
                <el-form-item label="选择课程: ">
                    <el-select v-model="lesson" placeholder="请选择">
                        <el-option
                                v-for="item in lessonOption"
                                :key="item.value"
                                :label="item.title"
                                :value="item.prop">
                        </el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="选择星期: ">
                    <el-select v-model="week" placeholder="请选择">
                        <el-option label="本周" :value="0"></el-option>
                        <el-option label="上周" :value="-1"></el-option>
                        <el-option label="下周" :value="1"></el-option>
                    </el-select>
                </el-form-item>
                <el-form-item>
                    <el-button @click="show" type="primary" :disabled="lesson === ''">确认</el-button>
                </el-form-item>
            </el-form>
        </div>
        <table-main :tableData="tableData" :column="column" subHeight="60px"
                    :bgcAuto="false" columnMinWidth="155px">

        </table-main>
    </div>
</template>

<script>
    import TableMain from "@/components/TableMain";
    import DateFilter from "@/components/DateFilter";
    import dateUtil from "@/util/dateutil"
    export default {
        name: "LeaveCheck",
        components: {DateFilter, TableMain},
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
        created(){
            this.$http.get('/tea').then(res=>{
                const data = res.data;
                if (data.code === 200){
                    this.lessonOption = data.data;
                }
            });
        },
        methods: {
            show(){
                this.$http.post('/tea',{
                    week: this.week,
                    lesson: this.lesson
                }).then(res=>{
                    const data = res.data;
                    if (data.code === 200){
                        this.data = data.data
                    }
                });
            }
        },
        data(){
            return{
                lessonOption: [],
                lesson: '',
                week: 0,
                column: [
                    {title: "学号", prop: "number"},{title: "班级", prop: "class"},
                    {title: "姓名", prop: "name"}, {title: "辅导员", prop: "counselor"},
                    {title: "请假类型", prop: "type"}, {title: "具体原因", prop: "detail"}],
                data: []
            }
        },
    }
</script>

<style scoped lang="scss">

</style>