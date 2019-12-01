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
                    action="https://jsonplaceholder.typicode.com/posts/"
                    :show-file-list="false"
                    :auto-upload="false"
                    accept=".jpg,.png"
                    :on-success="handleAvatarSuccess"
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
        methods: {
            changePwd(pwd){

            },
            changeEmail(pwd){

            },
            changeAvatar(){
                // 存在bug  应该让头像只能上传一个
                this.$store.commit('update',['avatar','/logout.png']);
            },
            handleAvatarSuccess(res, file) {

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
                    console.log(1);
                    this.imageUrl = URL.createObjectURL(file.raw);
                    return true;
                }else {
                    console.log(isLt2M,isPIC);
                    return false;
                }
            }
        },
        data(){
            return{
                imageUrl: '',
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