<template>
    <div class="archive">
        <div style="height: 50px;">
            <el-cascader :props="cascader" v-model="currentLesson"></el-cascader>
            <el-button type="primary" :disabled="data.length === 0" @click="expectClick" style="margin-left: 10px;"> 确认导出 </el-button>
        </div>
        <table-main :tableData="tableData" :column="column" :hiddenColumn="hiddenColumn" subHeight="60px"
                    :load="loadData" :bgcAuto="false">
        </table-main>
    </div>
</template>

<script>
    let id = 0;
    import TableMain from "@/components/TableMain";
    import dateUtil from "@/util/dateutil"
    export default {
        name: "TeacherArchive",
        components: {TableMain},
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
        watch: {
            currentLesson(val){
                if (val !== ''){
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
                }else {
                    this.data = [];
                }
            }
        },
        methods:{
            expectClick(){
                const team = this.currentLesson[0];
                const lesson = this.currentLesson[1];
                this.$message.info("导出"+ lesson + team);
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
                cascader: {
                    lazy: true,
                    lazyLoad (node, resolve) {
                        const { level , value} = node;
                        let nodes;
                        if (level === 0){
                            nodes = [
                                {value: '2018-2019-1', label:'第一学期'},
                                {value: '2018-2019-2', label:'第二学期'}
                            ];
                        }else if (level === 1){
                            if (value === '2018-2019-1'){
                                nodes = [
                                    {value: 'XML-865-22', label:'高等数学A',leaf: true },
                                    {value: 'XML-865-23', label:'高等数学B',leaf: true}
                                ];
                            }
                        }

                        // 通过调用resolve将子节点数据返回，通知组件数据加载完成
                        resolve(nodes);

                    }
                },
                currentLesson: '',
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