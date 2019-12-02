<template>
    <div class="table-filter-box" :style="{'height': height}">
        <div class="left">
            <el-tag class="tag" v-for="tag in tags" :key="tag.prop+tag.content" closable type="success" @close="closeTag(tag)"
            style="font-size: 10px;" :disable-transitions="true">
                {{prop2title[tag.prop]}}: {{tag.content}}
            </el-tag>
        </div>
        <div class="right">
            <el-form inline>
                <el-form-item style="width: 105px;">
                    <el-select v-model="form.prop" placeholder="筛选项">
                        <el-option :label="title" :value="prop" v-for="{title,prop} in column" :key="prop"></el-option>
                    </el-select>
                </el-form-item>
                <el-form-item style="width: 130px;">
                    <el-select v-model="form.content" placeholder="值" v-if="showSelect">
                        <el-option :label="title" :value="prop" v-for="{title, prop} in showSelect" :key="prop"></el-option>
                    </el-select>
                    <el-input v-model="form.content" placeholder="值" v-else></el-input>
                </el-form-item>
                <el-form-item>
                    <el-button type="primary" @click="addFilter">添加</el-button>
                </el-form-item>
            </el-form>
        </div>
    </div>
</template>

<script>
    import {deepCopy} from "@/util/MemoryUtil"
    export default {
        name: "TableFilterBox",
        props: {
            height:{type: String, require: true},
            column:{type: Array, require: true},
            changeFilter: {type: Function, require: true}
        },
        methods: {
            addFilter(){
                if (this.form.prop === '' || this.form.content === '') {
                    this.$message.error("请输入或选择完整的内容");
                }else {
                    // 判断筛选项是否存在
                    let tag = deepCopy(this.form);
                    if(!this.tags.some(_=>{return _.prop===tag.prop && _.content===tag.content})){
                        this.tags.push(tag);
                    }else {
                        this.$message.error("筛选条件已经存在");
                    }
                }
            },
            closeTag(tag){
                let index = this.tags.findIndex(_=>{return _.prop===tag.prop && _.content===tag.content});
                this.tags.splice(index,1);
            }
        },
        computed:{
            prop2title(){
                let ret = {};
                for (let columnKey in this.column) {
                    if(!this.column.hasOwnProperty(columnKey)) continue;
                    ret[this.column[columnKey].prop] = this.column[columnKey].title;
                }
                return ret;
            },
            showSelect(){
                let prop = this.form.prop;
                let ret = undefined;
                this.column.forEach(v=>{
                    if (v.prop === prop){
                        ret = v.extra;
                    }
                });
                return ret;
            }
        },
        watch: {
            "form.prop"(){
                this.form.content = '';
            },
            tags: {
                handler(){
                    this.changeFilter(this.tags);
                },
                deep: true
            }
        },
        data() {
            return {
                form: {
                    prop: '',
                    content: '',
                },
                tags: []
            };
        }
    }
</script>

<style scoped lang="scss">
    .table-filter-box{
        display: flex;
        .left{
            display: flex;
            flex: 1;
            padding-bottom: 10px;
            flex-wrap: nowrap;
            .tag{
                height: 100%;
                font-size: 15px;
                overflow: hidden;
                justify-content: center;
                margin-left: 5px;
            }
        }
    }

</style>