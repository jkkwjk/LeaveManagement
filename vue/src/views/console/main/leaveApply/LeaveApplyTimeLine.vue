<template>
        <el-dialog :title="title" :visible.sync="visible" width="50%" @close="$emit('update:row', null)">
            <el-steps :active="active" finish-status="success" process-status="finish">
                <el-step title="交提请假单"></el-step>
                <el-step title="辅导员审批"></el-step>
                <el-step title="院内审批"></el-step>
                <el-step title="任课老师查看" description="韩立文 来狗狗"></el-step>
            </el-steps>
        </el-dialog>
</template>

<script>
    export default {
        name: "LeaveApplyTimeLine",
        props: {row: {type: Object}},
        computed: {
            title(){
                const _title = ['不存在','等待辅导员审批','等待院内审批','等待任课老师查看'];
                return _title[this.active];
            }
        },
        watch: {
            row(val){
                this.visible = val !== null;
            }
        },
        data(){
            return {
                visible: false,
                active: 2,
            }
        }
    }
</script>

<style scoped>
    .leave-apply-process{

    }
</style>