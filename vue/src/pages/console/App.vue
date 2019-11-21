<template>
    <div id="app">
        <el-container style="height: 100%">
            <el-header style="padding: 0;">
                <Head :logout="logout" :value="value" :opened="opened" :clickItem="clickItem"></Head>
            </el-header>
            <el-container style="padding-top: 10px;">
                <el-aside width="200px">
                    <Aside :data="Aside" style="border-radius: 5px 5px 5px 5px;"></Aside>
                </el-aside>
                <el-main>
                    <router-view></router-view>
                </el-main>
            </el-container>
        </el-container>
    </div>
</template>

<script>
    import Aside from "@/components/console/Aside/Aside";
    import a from '@/faker/Aside/AsideDatayuan.json'
    import Head from "@/components/console/Head/Head";
    export default {
        name: "App",
        components: {Head, Aside},
        data(){
           return{
               Aside: [],
               value: 100,
           }
        },
        methods: {
            logout(){
                this.$message.info("logout");
            },
            opened(e){
                if (e && this.value!==0){
                    this.value = 0;
                    this.$message.info("opened");
                }

            },
            clickItem(id){
                this.$message.info(`clickItem: ${id}`);
            }

        },
        created() {
            this.Aside = a;
        }
    }
</script>

<style scoped lang="scss">
    #app{
        width: 100%;
        height: 100%;
        background-color: #E9ECF5;
    }
</style>