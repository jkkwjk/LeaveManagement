<template>
    <div id="add-data">
        <el-upload
                drag
                action="https://jsonplaceholder.typicode.com/posts/"
                multiple
                :accept="'xls'"
                :on-success="uploadSuccess">
            <i class="el-icon-upload"></i>
            <div class="el-upload__text">将文件拖到此处，或<em>点击上传</em></div>
            <div class="el-upload__tip" slot="tip">只能上传xls/xlsx，且不超过3MB</div>
        </el-upload>
    </div>
</template>

<script>
    export default {
        name: "addData",
        methods: {
            uploadSuccess(){
                this.$message.info("上传成功");
            }
        }
    }
</script>

<style scoped lang="scss">
    #add-data{

    }
</style>