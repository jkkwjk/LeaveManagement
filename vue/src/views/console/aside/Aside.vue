<template>
    <el-menu :default-active="$route.path" class="el-menu-vertical-demo"
             @open="handleOpen" @close="handleClose" unique-opened router>
        <template v-for="{ico, title, children} in data.data">
            <el-submenu :index="title" v-if="children">
                <template slot="title">
                    <i :class="ico" v-if="ico"></i>
                    <span>{{ title }}</span>
                </template>
                <AsideItem :child="children"></AsideItem>
            </el-submenu>

            <el-menu-item :index="'/'+title" v-else>
                <i :class="ico" v-if="ico"></i>
                <span slot="title">{{ title }}</span>
            </el-menu-item>
        </template>
    </el-menu>
</template>

<script>
    import AsideItem from "./AsideItem";
    export default {
        name: "Aside",
        components: {AsideItem},
        props: ['data'],
        methods: {
            handleOpen(key, keyPath) {
                console.log(key, keyPath);
            },
            handleClose(key, keyPath) {
                console.log(key, keyPath);
            }
        }
    }
</script>

<style scoped lang="scss">
    .el-menu-vertical-demo{
        height: 100%;
    }
    .el-menu-item.is-active{
        border-left: 2px #1073ff solid;
        outline: 0;
        background-color: #ecf5ff;
    }
</style>