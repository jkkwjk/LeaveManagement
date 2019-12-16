<template>
    <div id="head">
        <div class="left">
            <i class="el-icon-s-platform"></i>
            <span class="title">请假管理系统</span>
        </div>
        <div class="right">
            <!--<el-dropdown @command="clickItem" @visible-change="opened">
                <ico-group ico="el-icon-bell" content="提醒" :value="value" type="info"></ico-group>
                <el-dropdown-menu slot="dropdown">
                    之后又后端给数据渲染 command代表消息id
                    <el-dropdown-item command="a">黄金<span style="color: red;">糕</span></el-dropdown-item>
                    <el-dropdown-item command="b">狮子头</el-dropdown-item>
                    <el-dropdown-item command="c">螺蛳粉</el-dropdown-item>
                </el-dropdown-menu>
            </el-dropdown>-->
            <title-user-info :name="userName" :img="this.$store.state.avatar" :logout="logout"></title-user-info>
        </div>
    </div>
</template>

<script>
    import IcoGroup from "@/components/IcoGroup";
    import TitleUserInfo from "@/components/TitleUserInfo";
    export default {
        name: "Head",
        components: {TitleUserInfo, IcoGroup},
        created(){
            this.$http.post('/user/getInfo').then(res=>{
                const data = res.data;
                if (data.code === 200){
                    this.userName = data.data.name;
                    this.$store.commit('update',['avatar',`///localhost:8080/api/img/${data.data.avatar}`]);
                }
            });
            
        },
        data() {
            return {
                userName: '',
            }
        },
        props: {
            'logout': {type: Function},
            'value': {type: Number},
            'opened': {type: Function},
            'clickItem': {type: Function}
        }
    }
</script>

<style scoped lang="scss">
    #head{
        width: 100%;
        height: 100%;
        background-color: #fff;
        display: flex;
    }
    .left{
        display: flex;
        flex: 1;
        align-items: center;
        padding-left: 30px;
        i{
            font-size: 20px;
            color: darken(#82B1DF,10);
        }
        .title{
            font-size: 20px;
            color: #82B1DF;
            margin-left: 15px;
        }
    }
    .right{
        display: flex;
        align-items: center;
        padding-right: 10px;
        .ico-group{
            margin-right: 50px;
            color: #666;
            font-size: 13px;
            font-family: 'Avenir', Helvetica, Arial, sans-serif;
        }
    }

</style>