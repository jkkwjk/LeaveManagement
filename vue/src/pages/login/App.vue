<template>
    <div id="app">
        <div class="contain">
            <div class="show-tip">
                <span>请假管理系统登录</span>
                <img src="/logo.png">
            </div>
            <Login class="login" :submit="login"></Login>
        </div>
    </div>
</template>

<script>
    import Login from "@/views/login/Login";
    export default {
        name: "App",
        components: {
            Login
        },
        methods: {
            login(user, pwd){
                if (user === ''){
                    this.$message({
                        type: 'error',
                        message: '请输入您的账号',
                        showClose: true,
                        duration: 2000
                    });
                }else if (pwd === ''){
                    this.$message({
                        type: 'error',
                        message: '请输入您的密码',
                        showClose: true,
                        duration: 2000
                    });
                }else {
                    this.$http.post("/user/login",{
                        id: user,
                        password: pwd
                    }).then(res=>{
                        console.log(res);
                    });
                }
            }
        }
    }
</script>

<style scoped lang="scss">
    #app{
        width: 100%;
        height: 100%;
        background-color: #334053;
    }

    .contain{
        height: 400px;
        width: 400px;
        margin: 0 auto;
    }

    .show-tip{
        display: flex;
        padding: 10px;
        align-items: center;
        justify-content: center;
        span{
            color: white;
            font-size: 30px;
        }
        img{
            height: 80px;
            margin-left: 10px;
        }
    }
</style>