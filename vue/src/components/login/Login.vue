<template>
    <div class="login">
        <el-row>
            <el-col :span="24">
                <el-input class="user" v-model="user" placeholder="请输入账号" type="text" @keyup.enter.native="keyUp"></el-input>
            </el-col>
        </el-row>
        <el-divider class="line"></el-divider>
        <el-row>
            <el-col :span="24">
                <el-input class="pwd" v-model="pwd" placeholder="请输入密码" type="password" @keyup.enter.native="keyUp"></el-input>
            </el-col>
        </el-row>

        <el-button type="success" class="btn-login" style="margin-top: 20px;" @click="submit(user,pwd)">登录</el-button>
    </div>

</template>

<script>
    export default {
        name: "Login",
        data() {
            return {
                user: '',
                pwd: ''
            }
        },
        props:["submit"],
        methods: {
            keyUp(){
                this.submit(this.user, this.pwd);
            }
        }
    }

</script>

<style scoped>
    .login{
        padding: 10px;
    }
    .user>>>.el-input__inner{
        border-bottom: 0;
        border-radius: 5px 5px 0 0;
    }
    .pwd>>>.el-input__inner{
        border-top: 0;
        border-radius: 0 0 5px 5px;
    }
    .btn-login{
        width: 100%;
        margin-top: 20px;
    }
    .line{
        margin: 0;
    }
</style>