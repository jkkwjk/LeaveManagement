<template>
    <div class="leave-apply">
        <table-filter-box height="50px" :column="filterArray" :changeFilter="changeFilter">
        </table-filter-box>

        <table-main :tableData="tableData" :column="column" :hiddenColumn="hiddenColumn"
                    :willSort="willSort" subHeight="50px" :load="loadData"
                    customButtonClick customButtonWidth="170px" columnMinWidth="190px" ref="table-main">
            <div slot="button" slot-scope="scope" class="btn-group">
                <el-button type="text" size="small" @click="buttonClick(scope.data.row,'send')" v-if="scope.data.row.showWhat === 'button'">发送</el-button>
                <el-button type="text" size="small" @click="buttonClick(scope.data.row,'edit')" v-if="scope.data.row.showWhat === 'button'">编辑</el-button>
                <el-button type="text" size="small" @click="buttonClick(scope.data.row,'del')" v-if="scope.data.row.showWhat === 'button'">放入回收站</el-button>
                <span v-if="scope.data.row.showWhat === 'reject'" style="color: red;">拒绝</span>
                <span v-if="scope.data.row.showWhat === 'allow'" style="color: darkgreen;">同意</span>
                <el-button type="text" size="small" @click="buttonClick(scope.data.row,'wait')" v-if="scope.data.row.showWhat === 'wait'">查看当前进度</el-button>
            </div>
        </table-main>
        <!--        确认dialog-->
        <el-dialog title="警告" :visible.sync="confirm.visible" width="30%">
            <span>确认发送? 发送后将不能修改</span>
            <span slot="footer">
                <el-button @click="confirm.visible = false">取 消</el-button>
                <el-button type="primary" @click="confirm__">继 续</el-button>
            </span>
        </el-dialog>
        <!--        请假申请交提与修改dialog-->
        <el-dialog :title="form.origin? '修改请假申请':'新建请假申请'" :visible.sync="form.visible" width="50%"
                   @closed="form.data = {time: [], type: '', detail: ''}">

            <el-form :model="form.data" ref="form">
                <el-form-item label="请假类型:" :label-width="form.labelWidth" prop="type"
                              :rules="[{ required: true, message: '请选择请假类型', trigger: 'change' }]">
                    <el-select v-model="form.data.type" placeholder="请选择请假类型">
                        <el-option label="事假" value="事假"></el-option>
                        <el-option label="病假" value="病假"></el-option>
                        <el-option label="公假" value="公假"></el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="请假原因: " :label-width="form.labelWidth" prop="detail"
                              :rules="[{ required: true, message: '请输入具体原因', trigger: 'change' }]">
                    <el-input v-model="form.data.detail" placeholder="请输入具体原因"></el-input>
                </el-form-item>
                <el-form-item label="请假时间:" :label-width="form.labelWidth" prop="time"
                              :rules="[{ required: true, message: '请选择请假时间', trigger: 'change' }]">
                    <el-date-picker
                            v-model="form.data.time"
                            type="datetimerange"
                            range-separator="至" start-placeholder="开始时间" end-placeholder="结束时间"
                            :picker-options="{disabledDate: (date)=>{return date < new Date()}}">
                    </el-date-picker>
                </el-form-item>
            </el-form>
            <div slot="footer">
                <el-button @click="form.visible = false">取 消</el-button>
                <el-button type="primary" @click="addOrChangeLeave('form')">确 定</el-button>
            </div>
        </el-dialog>
        <leave-apply-time-line :row.sync="timeline.row"></leave-apply-time-line>
        <div class="right-bottom">
            <el-button type="warning" icon="el-icon-plus" circle @click="createLeave"></el-button>
        </div>
    </div>
</template>

<script>
    import TableMain from "@/components/TableMain";
    import TableFilterBox from "@/components/TableFilterBox";

    import dateUtil from "@/util/dateutil";
    import LeaveApplyTimeLine from "./LeaveApplyTimeLine";
    export default {
        name: "leaveApply",
        components: {LeaveApplyTimeLine, TableFilterBox, TableMain},
        created(){
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
        methods:{
            addOrChangeLeave(formName){
                this.$refs[formName].validate((valid) => {
                    if (valid) {
                        const type = this.form.data.type;
                        const detail = this.form.data.detail;
                        const time = this.form.data.time;
                        if (this.form.origin === undefined){
                            this.$http.post('/stu/add',{
                                type: type,
                                detail: detail,
                                startTime: time[0].getTime(),
                                endTime: time[1].getTime(),
                            }).then(res=>{
                                const data = res.data;
                                if (data.code === 200){
                                    this.$message.success("添加成功");
                                    this.data.unshift(data.data);
                                }else {
                                    this.$message.error(data.msg);
                                }
                            });
                        }else {
                            this.$http.post('/stu/modify',{
                                id: this.form.origin.id,
                                detail: detail,
                                type: type,
                                startTime: time[0].getTime(),
                                endTime: time[1].getTime()
                            }).then(res=>{
                                const data = res.data;
                                if(data.code === 200){
                                    this.$message.success("修改成功");
                                    this.form.origin.type = type;
                                    this.form.origin.detail = detail;
                                    this.form.origin.startTime = dateUtil.formatChina(time[0]);
                                    this.form.origin.endTime = dateUtil.formatChina(time[1]);
                                    this.form.origin.duration = dateUtil.calcDate(time[0],time[1])+'天';
                                }else {
                                    this.$$message.error(data.msg);
                                }
                            });
                            

                        }
                        this.form.visible = false;
                    } else {
                        return false;
                    }
                });
            },
            createLeave(){
                this.form.visible = true;
                this.form.origin = undefined;
            },
            confirm__(){
                this.$http.post('/stu/send',{
                    id: this.confirm.data.id
                }).then(res=>{
                    const data = res.data;
                    if (data.code === 200){
                        this.$message.success("发送成功");
                        this.confirm.data.showWhat = 'wait';
                        this.confirm.data.sendTime = new Date().getTime();
                    }else {
                        this.$message.error(data.msg);
                    }
                });
                this.confirm.visible = false;
            },
            buttonClick(row, type){
                switch (type) {
                    case 'send':
                        if (dateUtil.fixTimezoneOffset(dateUtil.parse(row.endTime)) < new Date()){
                            this.$message.error("请检查时间是否超出范围");
                        }else {
                            this.confirm.visible = true;
                            this.confirm.data = row;
                        }
                        break;
                    case 'del':
                        this.$http.post('/stu/toTrash',{
                            id: row.id
                        }).then(res=>{
                            const data = res.data;
                            if (data.code === 200){
                                let index = this.data.findIndex(_=>{return _.id===row.id});
                                this.data.splice(index,1);
                                if (this.data.length <= 20)
                                    this.loadData();

                                this.$message.success("成功放入回收站");
                            }else {
                                this.$message.error(data.msg);
                            }
                        });
                        
                        break;
                    case 'edit':
                        this.form.origin = row;
                        this.form.data.type = row.type;
                        this.form.data.detail = row.detail;
                        this.form.data.time = [dateUtil.fixTimezoneOffset(dateUtil.parse(row.startTime)),
                            dateUtil.fixTimezoneOffset(dateUtil.parse(row.endTime))];
                        this.form.visible = true;
                        break;
                    case 'wait':
                        // 显示查看进度条
                        this.timeline.row = row;
                        break;
                    default:
                        break;
                }
            },
            willSort({ prop, order }){
                this.filter.sort = { prop, order };
            },
            changeFilter(v){
                this.filter.custom = v;
            },
            loadData(){
                if(this.hasNext && !this.lock){
                    this.lock = true;
                    this.$http.post('/stu',{
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
        data(){
            return{
                page: 1,
                hasNext: true,
                num: 20,
                lock: false, // fix loadData可能会调用多次的问题
                timeline: {
                    row: null
                },
                form: {
                    visible: false,
                    origin: undefined,
                    labelWidth: '100px',
                    data: {
                        time: [],
                        type: '',
                        detail: ''
                    }
                },
                confirm: {
                    data: undefined,
                    visible: false,
                },
                filter: {
                    sort: {
                        prop: '',
                        order: ''
                    },
                    custom: []
                },
                column: [
                    {title: "发送时间", prop: "sendTime"}, {title: "辅导员", prop: "counselor"},
                    {title: "请假类型", prop: "type"}, {title: "具体原因", prop: "detail"},
                    {title: "请假天数", prop: "duration"}],
                hiddenColumn: [
                    {title: "开始时间", prop: "startTime"},
                    {title: "结束时间", prop: "endTime"}],
                filterArray: [
                    {title: "请假类型", prop: "type", extra:[{title: "公假", prop: "公假"},
                            {title: "事假", prop: "事假"},{title: "病假", prop: "病假"},]},
                    {title: "具体原因", prop: "detail"},
                ],
                data: []
            }
        }
    }
</script>

<style scoped lang="scss">
    .leave-apply{
        height: calc(100% - 10px); /*适配chrome*/
    }
    .btn-group{
        button{
            padding: 0;
        }
    }
    .right-bottom{
        position: fixed;
        right: 5px;
        bottom: 5px;
        z-index: 1000;
    }
    .el-date-editor--datetimerange{
        width: 100%;
    }
</style>