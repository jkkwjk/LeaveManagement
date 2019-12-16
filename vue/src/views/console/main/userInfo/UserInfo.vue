<template>
    <div class="user-info">
        <div class="left">
            <change-pwd :changePwd="changePwd"></change-pwd>
            <e-mail :changeEmail="changeEmail"></e-mail>
        </div>
        <el-divider direction="vertical"></el-divider>
        <div class="right">
            <el-upload
                    class="avatar-uploader"
                    action=""
                    :multiple="false"
                    :show-file-list="false"
                    :auto-upload="false"
                    accept=".jpg,.png"
                    :on-change="beforeAvatarUpload"
                    ref="uploadAvatar">
                <img v-if="imageUrl" :src="imageUrl" class="avatar">
                <i v-else class="el-icon-plus avatar-uploader-icon"></i>
            </el-upload>
            <el-button type="primary" size="medium" style="margin-top: 5px;" @click="changeAvatar">点我确认修改头像</el-button>
        </div>

    </div>
</template>

<script>
    import ChangePwd from "./ChangePwd";
    import EMail from "./EMail";
    export default {
        name: "UserInfo",
        components: {EMail, ChangePwd},
        created(){
            switch(this.$store.state.authType){
                case '学生':
                    this.baseUrl = 'stu';
                    break;
                case '辅导员':
                    this.baseUrl = 'cou';
                    break;
                case '院领导':
                    this.baseUrl = 'col';
                    break;
            }
        },
        methods: {
            changePwd(pwd){
                this.$http.post('user/modifyPwd',{
                    pwd: pwd
                }).then(res=>{
                    const data = res.data;
                    if (data.code === 200){
                        this.$message.success("修改密码成功");
                    }else {
                        this.$message.error(data.msg);
                    }
                })
            },
            changeEmail(email){
                this.$http.post('user/modifyEMail',{
                    email: email
                }).then(res=>{
                    const data = res.data;
                    if (data.code === 200){
                        this.$message.success("修改邮箱成功");
                    }else {
                        this.$message.error(data.msg);
                    }
                })
            },
            changeAvatar(){
                // 存在bug  应该让头像只能上传一个
                //this.$store.commit('update',['avatar','/logout.png']);
                let formData=new FormData();
                formData.append('file',this.file);
                console.log(this.$refs.uploadAvatar);
                this.$http.post('/user/modifyAvatar',formData,{
                    headers: {'Content-Type':'multipart/form-data'}
                }).then(res=>{
                    const data = res.data;
                    if (data.code === 200){
                        this.$message.success("修改成功");
                        this.$store.commit('update',['avatar',`///localhost:8080/api/img/${data.data}`]);
                    }else {
                        this.$message.error(data.msg);
                    }
                });
            },
            beforeAvatarUpload(file, fileList) {
                fileList = [file];
                console.log(fileList);
                const isPIC = (file.raw.type === 'image/jpeg' || file.raw.type === 'image/png');
                const isLt2M = file.size / 1024 / 1024 < 2;

                if (!isPIC) {
                    this.$message.error('只能上传静态图片 !');
                }
                if (!isLt2M) {
                    this.$message.error('上传头像图片大小不能超过 2MB!');
                }
                if (isPIC && isLt2M){
                    this.imageUrl = URL.createObjectURL(file.raw);
                    this.file = file.raw;
                    return true;
                }else {
                    return false;
                }
            }
        },
        data(){
            return{
                imageUrl: '',
                baseUrl: '',
                file: undefined
            }
        }
    }
</script>

<style scoped lang="scss">
    .user-info{
        background-color: #fff;
        display: flex;
        flex-direction: row;
        padding: 50px 0;
        box-sizing:border-box; /*将边框计算到box的尺寸中*/
        .left{
            flex: 2;
        }
        .right{
            flex: 1;
            text-align: center;
        }
    }
    .el-divider.el-divider--vertical{
        height: 100%;
    }

    .avatar-uploader-icon {
        font-size: 28px;
        color: #8c939d;
        width: 178px;
        height: 178px;
        line-height: 178px;
        text-align: center;
    }
    .avatar {
        width: 178px;
        height: 178px;
        display: block;
    }
</style>
<style scoped>
    .avatar-uploader /deep/ .el-upload:hover {
        border-color: #409EFF;
    }
    .avatar-uploader /deep/ .el-upload {
        border: 1px dashed #d9d9d9;
        border-radius: 6px;
        cursor: pointer;
        position: relative;
        overflow: hidden;
    }
</style>