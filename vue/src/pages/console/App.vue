<template>
    <div id="app">
        <el-container style="height: 100%">
            <el-header>Header</el-header>
            <el-container>
                <el-aside width="200px">
                    <Aside :data="Aside"></Aside>
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
    export default {
        name: "App",
        components: {Aside},
        data(){
           return{
               Aside:[],
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