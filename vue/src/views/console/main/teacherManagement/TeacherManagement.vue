<template>
    <div class="teacher-management">
        <table-main :tableData="tableData" :column="column" :load="loadData"
                    :buttonClick="buttonClick" :customButtonClick="customButtonClick"
                    customButtonWidth="200px" columnMinWidth="150px">
        </table-main>
    </div>
</template>

<script>
    import TableMain from "@/components/TableMain";
    export default {
        name: "TeacherManagement",
        components: {TableMain},
        methods:{
            buttonClick(e,a){
                console.log(a);
            },
            loadData(){

            }
        },
        computed: {
            tableData(){
                return this.data.map(_=>{_.type = (_.manageClass?"辅导员":"")+' '+(_.teachLesson?"任课老师":""); return _;});
            }
        },
        data(){
            return {
                column: [
                    {title: "ID", prop: "uid"},
                    {title: "姓名", prop: "name"},
                    {title: "职位", prop: "type"}],
                data: [{
                    uid: '1',
                    name: '金凯凯',
                    type: "",
                    manageClass: ["1944423"],
                    teachLesson: ["22345"]
                }],
                customButtonClick: [{
                    title: "设置管理的班级"
                },{
                    title: "设置任课的课程"
                }],
            }
        },
    }
</script>

<style scoped>
    .teacher-management{
        background-color: red;
        height: 100%;
    }
</style>