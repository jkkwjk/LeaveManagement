<template>
    <div class="table-filter-box" :style="{'height': height}">
        <div class="left"></div>
        <div class="right">
            <el-form inline>
                <el-form-item>
                    <el-select v-model="form.what" placeholder="筛选项">
                        <el-option :label="title" :value="prop" v-for="{title,prop} in column" :key="prop"></el-option>
                    </el-select>
                </el-form-item>
                <el-form-item>
                    <el-select v-model="form.value" placeholder="值" v-if="form.what==='team'">
                        <el-option :label="title" :value="prop" v-for="{title, prop} in column[2].extra" :key="prop"></el-option>
                    </el-select>
                    <el-input v-model="form.value" placeholder="值" v-else></el-input>
                </el-form-item>

                <el-form-item>
                    <el-button type="primary">添加</el-button>
                </el-form-item>
            </el-form>
        </div>
    </div>
</template>

<script>
    export default {
        name: "TableFilterBox",
        props: {
            height:{type: String, require: true},
            column:{type: Array, require: true},
        },
        data() {
            return {
                form: {
                    what: '',
                    value: '',
                },
                current:
            };
        }
    }
</script>

<style scoped lang="scss">
    .table-filter-box{
        display: flex;
        .left{
            flex: 1;
        }
        .right{

        }
    }

</style>