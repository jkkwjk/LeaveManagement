<template>
    <el-table
            :data="tableData"
            :row-class-name="tableRowClassName"
            v-el-table-infinite-scroll="load"
            infinite-scroll-distance="200"
            @sort-change="willSort"
            :height="subHeight? `calc(100% - ${subHeight})`:'100%'" class="table" v-if="willSort">
        <el-table-column type="expand" fixed v-if="hiddenColumn">
            <template slot-scope="props">
                <el-form label-position="left" inline class="table-expand">
                    <el-form-item :label="title+':'" v-for="{title,prop} in column" :key="prop">
                        <span>{{ props.row[prop] }}</span>
                    </el-form-item>
                    <el-form-item :label="title+':'" v-for="{title,prop} in hiddenColumn" :key="prop">
                        <span>{{ props.row[prop] }}</span>
                    </el-form-item>
                </el-form>
            </template>
        </el-table-column>

        <el-table-column :label="title" :prop="prop"
                         v-for="{title, prop} in column"
                         sortable="custom"
                         :key="prop" min-width="110px">
        </el-table-column>

        <el-table-column fixed="right" label="操作" width="100" sortable="custom" v-if="buttonClick">
            <template slot-scope="scope">
                <div class="btn-group">
                    <el-button type="text" size="small" v-if="scope.row.showWhat === 'button'" @click="buttonClick(scope.row,'allow')">同意</el-button>
                    <el-button type="text" size="small" style="color: red" v-if="scope.row.showWhat === 'button'" @click="buttonClick(scope.row,'reject')">拒绝</el-button> <!--拒绝原因-->
                    <span v-if="scope.row.showWhat === 'reject'" style="color: red;">已拒绝</span>
                    <span v-if="scope.row.showWhat === 'allow'" style="color: darkgreen;">已同意</span>
                </div>

            </template>
        </el-table-column>
    </el-table>

    <el-table
            :data="tableData"
            :row-class-name="tableRowClassName"
            v-el-table-infinite-scroll="load"
            infinite-scroll-distance="200"
            :height="subHeight? `calc(100% - ${subHeight})`:'100%'" class="table" v-else>
        <el-table-column type="expand" fixed v-if="hiddenColumn">
            <template slot-scope="props">
                <el-form label-position="left" inline class="table-expand">
                    <el-form-item :label="title+':'" v-for="{title,prop} in column" :key="prop">
                        <span>{{ props.row[prop] }}</span>
                    </el-form-item>
                    <el-form-item :label="title+':'" v-for="{title,prop} in hiddenColumn" :key="prop">
                        <span>{{ props.row[prop] }}</span>
                    </el-form-item>
                </el-form>
            </template>
        </el-table-column>

        <el-table-column :label="title" :prop="prop"
                         v-for="{title, prop} in column"
                         :key="prop" :min-width="columnMinWidth">
        </el-table-column>

        <el-table-column fixed="right" label="操作" :width="customButtonWidth" v-if="buttonClick">
            <template slot-scope="scope">
                <div class="btn-group" v-if="!customButtonClick">
                    <el-button type="text" size="small" v-if="scope.row.showWhat === 'button'" @click="buttonClick(scope.row,'allow')">同意</el-button>
                    <el-button type="text" size="small" style="color: red" v-if="scope.row.showWhat === 'button'" @click="buttonClick(scope.row,'reject')">拒绝</el-button> <!--拒绝原因-->
                    <span v-if="scope.row.showWhat === 'reject'" style="color: red;">已拒绝</span>
                    <span v-if="scope.row.showWhat === 'allow'" style="color: darkgreen;">已同意</span>
                </div>
                <div class="btn-group" v-else>
<!--                    <template v-for="{title, style, clickStyle, clickTitle} in customButtonClick">-->
<!--                        <el-button type="text" size="small" :style="style" v-if="scope.row.showWhat === 'button' || scope.row.showWhat === undefined" @click="buttonClick(scope.row,title)">{{ title }}</el-button>-->
<!--                        <span v-if="scope.row.showWhat === clickTitle" :style="clickStyle">{{ clickTitle }}</span>-->
<!--                    </template>-->
<!--                    todo 插槽-->
                    <template slot-scope=""
                </div>
            </template>
        </el-table-column>
    </el-table>
</template>

<script>
    import elTableInfiniteScroll from 'el-table-infinite-scroll';
    export default {
        name: "LeaveTableMain",
        directives: {
            'el-table-infinite-scroll': elTableInfiniteScroll
        },
        props: {
            tableData: {type: Array, require: true},
            column: {type: Array, require: true},
            hiddenColumn: {type: Array},
            willSort: {type: Function},
            subHeight: {type: String},
            load: {type: Function, require: true},
            buttonClick: {type: Function},
            customButtonClick: {type: Array},
            customButtonWidth: {type: String, default: '100px'},
            columnMinWidth: {type: String, default: '110px'}
        },
        methods: {
            tableRowClassName({row}){
                switch (row.showWhat) {
                    case "reject":
                        return 'reject-row';
                    case "allow":
                        return 'allow-row';
                    default:
                        return '';
                }
            }
        }
    }
</script>
<style scoped>
    .el-table /deep/ .reject-row{
        background-color: #FEF0F0;
    }
    .el-table /deep/ .allow-row{
        background-color: #f0f9eb;
    }
</style>
<style scoped lang="scss">
    .el-table{

    }
    .table{
        width: 100%;
    }
    .table-expand {
        font-size: 0;
    }
    .table-expand .el-form-item {
        margin-right: 0;
        margin-bottom: 0;
        width: 50%;
    }
    .btn-group{
        button{
            padding: 0;
        }
    }
</style>