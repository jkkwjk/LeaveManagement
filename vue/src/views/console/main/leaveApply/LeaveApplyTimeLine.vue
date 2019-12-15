<template>
        <el-dialog :title="title" :visible.sync="visible" width="50%" @close="$emit('update:row', null)">
            <el-steps :active="active" finish-status="success" process-status="finish">
                <el-step title="交提请假单"></el-step>
                <el-step title="辅导员审批"></el-step>
                <el-step title="院内审批"></el-step>
                <el-step title="任课老师查看"></el-step>
            </el-steps>
        </el-dialog>
</template>

<script>
    export default {
        name: "LeaveApplyTimeLine",
        props: {row: {type: Object}},
        computed: {
            title(){
                const _title = ['请等待获得数据','等待辅导员审批','等待院内审批','等待任课老师查看'];
                return _title[this.active];
            }
        },
        watch: {
            row(val){
                if (val !== null){
                    console.log(val);
                    this.$http.post('/stu/getStatus',{
                        id: val.id
                    }).then(res=>{
                        const data = res.data;
                        if (data.code === 200){
                            this.active = data.data.active;
                        }else {
                            this.$message.error("获取信息失败");
                        }
                    });
                    this.visible = true;
                }
            }
        },
        data(){
            return {
                visible: false,
                active: 0,
            }
        }
    }
</script>

<style scoped>
</style>