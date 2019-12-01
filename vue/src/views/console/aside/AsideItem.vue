<template>
    <div>
        <template v-for="{group_title, title, children} in child">
            <el-menu-item-group :title="group_title" v-if="group_title">
                <AsideItem :child="children" v-if="children"></AsideItem>
            </el-menu-item-group>
            <template v-else>
                <el-submenu :index="title" v-if="children">
                    <template slot="title">{{ title }}</template>
                    <AsideItem :child="children"></AsideItem>
                </el-submenu>
                <el-menu-item :index="'/'+title" v-else>{{ title }}</el-menu-item>
            </template>
        </template>
    </div>
</template>

<script>
    export default {
        name: "AsideItem",
        props: ['child']
    }
</script>

<style scoped>
    .el-menu-item.is-active{
        border-left: 2px #1073ff solid;
        outline: 0;
        background-color: #ecf5ff;
    }
</style>