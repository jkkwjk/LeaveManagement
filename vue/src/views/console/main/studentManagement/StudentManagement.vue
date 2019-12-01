<template>
    <div class="teacher-management">
        <table-main :tableData="data" :column="column" :load="loadData" customButtonClick
                    customButtonWidth="100px" columnMinWidth="150px">
                <div slot="button" slot-scope="scope">
                    <el-button type="text" size="small" @click="buttonClick(scope.data.row)">{{ dialogTable.data.title }}</el-button>
                </div>
        </table-main>
        <el-dialog :title="dialogTable.data.title" :visible.sync="dialogTable.visible" width="25%">
            <el-form>
                <el-form-item label="选课:">
                        <el-select
                                v-model="dialogTable.data.chooseClass"
                                multiple
                                filterable
                                remote
                                reserve-keyword
                                placeholder="请选择课程"
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
        name: "StudentManagement",
        components: {TableMain},
        methods:{
            buttonClick(row){
                // 需要先从服务端获取数据
                this.dialogTable.visible = true;
                this.dialogTable.data.chooseClass = [];
                this.dialogTable.data.class = [];
            },
            loadData(){
                // table 分页
            },
            getLesson(query){
                // 筛选的时候要把已经筛选好的数据发送到后端, 同个时间段老师不能有两节课
                if (query !== ''){
                    // 记得加载时要设置loading
                    this.dialogTable.data.class = ['19216','1112','7785','192216','11132','77845'];
                }else {
                    this.dialogTable.data.class = []
                }
            }
        },
        data(){
            return {
                column: [
                    {title: "学号", prop: "uid"},
                    {title: "姓名", prop: "name"},
                    {title: "所在班级", prop: "class"}],
                data: [{
                    uid: '189050416',
                    name: '金凯凯',
                    class: "1944423",
                }],
                dialogTable: {
                    data: {
                        class: [],
                        chooseClass: [],
                        loading: false,
                        title: '选择课程',
                    },
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