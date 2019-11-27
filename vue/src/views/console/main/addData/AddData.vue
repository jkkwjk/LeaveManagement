<template>
    <div id="add-data">
        <el-steps :active="active" simple process-status="finish" finish-status="success">
            <el-step title="上传文件" icon="el-icon-upload"></el-step>
            <el-step title="选择文件" icon="el-icon-document-add"></el-step>
            <el-step title="填写表单" icon="el-icon-edit"></el-step>
        </el-steps>
        <div class="step1">
            <el-upload
                    drag
                    action="https://jsonplaceholder.typicode.com/posts/"
                    accept="application/vnd.openxmlformats-officedocument.spreadsheetml.sheet, application/vnd.ms-excel"
                    :on-success="uploadSuccess"
                    with-credentials
                    :before-upload="beforeAvatarUpload"
                    :on-preview="onPreview"
                    :on-remove="removeFile"
                    multiple
                    class="upload">
                <i class="el-icon-upload"></i>
                <div class="el-upload__text">将需要导入的EXCEL文件拖到此处，或<em>点击上传</em></div>
                <div class="el-upload__tip" slot="tip">只能上传xls/xlsx，且不超过3MB</div>
            </el-upload>
        </div>
        <transition tag="div" enter-active-class="animated fadeInDown" leave-active-class="animated fadeOutUp">
        <el-card class="step2" v-show="showStep2" shadow="hover">
            <div class="title">
                请填写表单: {{ currentFile===null?'':currentFile.name }}
            </div>
            <div class="form">
                <el-form label-width="130px" v-model="form">
                    <el-form-item label="导入的数据:" style="margin-top: 10px;">
                        <el-select v-model="form.type" placeholder="请选择数据类型">
                            <el-option label="学生数据" value="student"></el-option>
                            <el-option label="教师数据" value="teacher"></el-option>
                            <el-option label="班级数据" value="class"></el-option>
                            <el-option label="请假数据" value="leave"></el-option>
                        </el-select>
                    </el-form-item>
                    <el-form-item label="清空原有数据:">
                        <el-switch v-model="form.clear"></el-switch>
                    </el-form-item>
                    <el-button type="primary" @click="onImport" style="float: right">立即导入</el-button>
                </el-form>
            </div>
        </el-card>
        </transition>
    </div>
</template>

<script>
    // import animate from 'animate.css'
    export default {
        name: "addData",
        data(){
            return{
                showStep2: false,
                form: {
                    type: '',
                    clear: false
                },
                currentFile: null,
                okFile: [],
                active: 0
            }
        },
        methods: {
            uploadSuccess(res,file){
                this.$message.info("上传成功");
                this.okFile.push(file);
            },

            beforeAvatarUpload(file) {
                const isLt3M = file.size / 1024 / 1024 < 3;
                if (!isLt3M) {
                    this.$message.error('上传EXCEL文件大小不能超过 3MB!');
                }
                return isLt3M;
            },
            /** 点击文件是判断该文件是否上传成功
             * @param file
             */
            onPreview(file){
                let isOkUpload = false;
                for (let i in this.okFile){
                    let data = this.okFile[i];
                    if(data === file){
                        isOkUpload = true;
                        break;
                    }
                }
                if (isOkUpload)
                    this.currentFile = file;
            },
            /**
             * 交提前置操作
             */
            onImport(){
                if (this.form.type===''){
                    this.$message.error("请选择导入的数据类型");
                }else if (this.form.clear === true){
                    this.$confirm('此操作将删除数据库中该表所有数据, 是否继续?', '提示', {
                        confirmButtonText: '确定',
                        cancelButtonText: '取消',
                        type: 'warning'
                    }).then(() => {
                        this.submit();
                    }).catch(()=>{});
                }else {
                    this.submit();
                }

            },
            submit(){
                this.$message.info(`我交提了${this.form.type}..${this.form.clear}`);
                this.currentFile = null;
            },
            /**
             * 移除文件 判断OKfile中是否存在上传成功的文件 是删除 否直接删除
             * @param file
             */
            removeFile(file){
                let i = 0;
                for (;i<this.okFile.length; ++i){
                    let data = this.okFile[i];
                    if(data === file)
                        break;
                }
                this.okFile.splice(i,1);
                if (this.currentFile === file)
                    this.currentFile = null;
            }
        },
        watch: {
            currentFile(val){
                this.showStep2 = val !== null;
                if (this.showStep2){
                    this.active = 2;
                }else {
                    this.active = 1;
                }
            },
            okFile(){
                if (this.okFile.length !== 0 && this.active === 0){
                    this.active = 1;
                }else if (this.okFile.length === 0){
                    this.active = 0;
                }
            }
        }
    }
</script>

<style scoped lang="scss">
    .step1{
        text-align: center;
        margin-top: 20px;
    }
    .step2{
        display: flex;
        flex-direction: column;
        width: 600px;
        margin: 20px auto 0 auto;
        .title{
            font-size: 20px;
            color: #666;
            text-align: center;
        }
        .form{
            background-color: #fff;
            display: flex;
            align-items: center;
            justify-content: center;
        }
    }
</style>

<style scoped>
    .upload /deep/ .el-upload-list{
        text-align: left;
    }
    .upload /deep/ .el-upload .el-upload-dragger{
        width: 600px;
    }
</style>