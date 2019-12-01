<template>
    <div class="teacher-management">
        <table-main :tableData="tableData" :column="column" :load="loadData" customButtonClick
                    customButtonWidth="220px" columnMinWidth="150px">
                <div slot="button" slot-scope="scope">
                    <el-button type="text" size="small" @click="buttonClick(scope.data.row,'manage')">{{ dialogButton.manage.title }}</el-button>
                    <el-button type="text" size="small" @click="buttonClick(scope.data.row,'teach')">{{ dialogButton.teach.title }}</el-button>
                </div>
        </table-main>
        <el-dialog :title="dialogTable.data.title" :visible.sync="dialogTable.visible" width="25%">
            <el-form v-if="dialogTable.data.title === dialogButton.manage.title">
                <el-form-item label="班级:">
                        <el-select
                                v-model="dialogTable.data.chooseClass"
                                multiple
                                filterable
                                remote
                                reserve-keyword
                                placeholder="请输入班级"
                                :remote-method="getClass"
                                :loading="dialogTable.data.loading"
                                style="width: calc(100% - 80px)">
                            <el-option v-for="item in dialogTable.data.class" :key="item" :label="item" :value="item"></el-option>
                        </el-select>
                </el-form-item>
                <el-form-item style="width: 100%;">
                    <el-button type="primary" style="float: right; width: 100px;">交提</el-button>
                </el-form-item>
            </el-form>

            <el-form v-if="dialogTable.data.title === dialogButton.teach.title">
                <el-form-item label="课程号:">
                    <el-select
                            v-model="dialogTable.data.chooseClass"
                            multiple
                            filterable
                            remote
                            placeholder="请输入课程"
                            :remote-method="getLesson"
                            :loading="dialogTable.data.loading"
                            style="width: calc(100% - 80px)">
                        <el-option v-for="item in dialogTable.data.class" :key="item" :label="item" :value="item"></el-option>
                    </el-select>
                </el-form-item>
                <el-form-item style="width: 100%;">
                    <el-button type="primary" style="float: right; width: 100px;">交提</el-button>
                </el-form-item>
            </el-form>
        </el-dialog>
    </div>
</template>

<script>
    import TableMain from "@/components/TableMain";
    export default {
        name: "TeacherManagement",
        components: {TableMain},
        methods:{
            buttonClick(row,what){
                this.dialogTable.visible = true;
                this.dialogTable.data.chooseClass = [];
                this.dialogTable.data.class = [];
                this.dialogTable.data = this.dialogButton[what];
            },
            loadData(){
                // table 分页
            },
            getClass(query){
                if (query !== ''){
                    // 记得加载时要设置loading
                    this.dialogTable.data.class = ['19216','1112','7785','192216','11132','77845'];
                }else {
                    this.dialogTable.data.class = []
                }
            },
            getLesson(query){
                // 筛选的时候要把已经筛选好的数据发送到后端, 同个时间段老师不能有两节课
                if (query !== ''){
                    this.dialogTable.data.class = ['19216','1112','7785','192216','11132','77845'];
                }else {
                    this.dialogTable.data.class = []
                }
            }
        },
        computed: {
            tableData(){
                return this.data.map(_=>{_.type = (_.manageClass.size!==0?"辅导员":"")+' '+(_.teachLesson.size!==0?"任课老师":""); return _;});
            }
        },
        data(){
            return {
                column: [
                    {title: "工号", prop: "uid"},
                    {title: "姓名", prop: "name"},
                    {title: "职位", prop: "type"}],
                data: [{
                    uid: '199664',
                    name: '金凯凯',
                    manageClass: ["1944423"],
                    teachLesson: ["22345"]
                }],
                dialogButton: {
                    manage: {
                        class: [],
                        chooseClass: [],
                        loading: false,
                        title: '设置管理的班级',
                    },
                    teach: {
                        class: [],
                        chooseClass: [],
                        loading: false,
                        title: '设置任课的课程',
                    }
                },
                dialogTable: {
                    data: {},
                    visible: false,
                }
            }
        },
    }
</script>

<style scoped>
    .teacher-management{
        height: calc(100% - 10px);
    }
</style>