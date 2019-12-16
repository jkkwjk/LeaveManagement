<template>
    <div id="app">
        <el-container style="height: 100%;">
            <el-header style="padding: 0;">
                <Header :logout="logout" :value="value" :opened="opened" :clickItem="clickItem"></Header>
            </el-header>
            <el-container style="padding-top: 10px;background-color: #E9ECF5;">
                <el-aside width="200px">
                    <Aside :data="aside" style="border-radius: 5px 5px 5px 5px;"></Aside>
                </el-aside>
                <el-main>
                    <transition tag="div" name="el-fade-in-linear" mode="out-in">
                        <router-view class="router-view"></router-view>
                    </transition>
                </el-main>
            </el-container>
        </el-container>
    </div>
</template>

<script>
    import Aside from "@/views/console/aside/Aside";

    import counselor from '@/assets/aside/AsideDataCounselor.json'
    import college from '@/assets/aside/AsideDataCollege.json'
    import stu from '@/assets/aside/AsideDataStu.json'
    import teacher from '@/assets/aside/AsideDataTeacher.json'

    import Header from "@/views/console/header/Header";
    import animate from 'animate.css'
    export default {
        name: "App",
        components: {Header, Aside},
        data() {
            return {
                aside: [],
                value: 100, //消息提醒个数
            }
        },
        methods: {
            logout() {
                this.$http.get('/user/logout').then(res=>{
                    const data = res.data;
                    if (data.code === 200){
                        window.location.href = 'login.html';
                    }
                });
            },
            opened(e) {
                if (e && this.value !== 0) {
                    this.value = 0;
                    this.$message.info("opened");
                }
            },
            clickItem(id) {
                this.$message.info(`clickItem: ${id}`);
            }
        },
        created() {
            switch(this.$store.state.authType){
                case '学生':
                    this.aside = stu;
                    break;
                case '教师':
                    this.aside = teacher;
                    break;
                case '辅导员':
                    this.aside = counselor;
                    break;
                case '院领导':
                    this.aside = college;
                    break;
            }
        },
        mounted() {
            setTimeout(()=>{
                if (this.$route.path === '/') this.$router.push('/'+this.aside.active);
            }, 500);
        }
    }
</script>

<style scoped lang="scss">
    #app{
        width: 100%;
        height: 100%;
        background-color: #E9ECF5;
    }
    .el-aside::-webkit-scrollbar {
        display: none;
    }
    .el-main{
        padding: 0 20px;
        height: auto;
    }
    .router-view{
        width: 100%;
        height: 100%;
        border-radius: 5px 0 0 5px;
    }
</style>