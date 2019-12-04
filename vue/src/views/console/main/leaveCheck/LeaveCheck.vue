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
                <el-form-item>
                    <el-button @click="show" type="primary" :disabled="lesson === ''">确认</el-button>
                </el-form-item>
            </el-form>
        </div>
        <table-main :tableData="tableData" :column="column" :hiddenColumn="hiddenColumn" subHeight="60px"
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
                    if (typeof _.sendTime === "number"){
                        _.sendTime = dateUtil.formatChina(new Date(_.sendTime));
                    }
                    return _;
                });
            }
        },
        created(){
            this.lessonOption = [
                {title: '高等数学A1(周三 1 2)',prop: 'SMLA-8159-995'},
                {title: '高等数学A2(周三 3 4)',prop: 'SMLA-8159-996'},
                {title: '高等数学A3(周四 5 6)',prop: 'SMLA-8159-997'}
            ];
        },
        methods: {
            show(){
                // 通过lesson向服务器读取数据
                // 一旦查询过就代表老师看过了所有该课程请假数据
            }
        },
        data(){
            return{
                lessonOption: [],
                lesson: '',
                column: [
                    {title: "发送时间", prop: "sendTime"},{title: "学号", prop: "number"},
                    {title: "姓名", prop: "name"}, {title: "辅导员", prop: "counselor"},
                    {title: "请假类型", prop: "type"}, {title: "具体原因", prop: "detail"},
                    {title: "请假天数", prop: "duration"}],
                hiddenColumn: [
                    {title: "班级", prop: "class"},{title: "开始时间", prop: "startTime"},
                    {title: "结束时间", prop: "endTime"}],
                data: [
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
                    },{
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
                    },{
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
                    }]
            }
        },
    }
</script>

<style scoped lang="scss">

</style>