<template>
    <div id="user-info">
        <el-avatar :size="40" :src="img" v-if="img"></el-avatar>
        <span class="user-name">{{ name }}</span>
        <el-divider direction="vertical"></el-divider>
        <a href="#" @click.prevent="logout"><img src="/logout.png" v-if="logout" alt="登录图片加载失败"></a>
    </div>

</template>

<script>
    export default {
        name: "UserInfo",
        props: {
            img: {},
            name: {
                required: true,
            },
            logout: {
                type: Function,
            }
        }
    }
</script>

<style scoped lang="scss">
    #user-info{
        display: flex;
        align-items: center;
        img{
            width: 25px;
        }
        a{
            margin-left: 20px;
        }
    }
    .user-name{
        color: #555;
        font-size: 13px;
        margin-left: 8px;
        font-weight: 600
    }
</style>