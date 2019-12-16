<template>
    <div>
        <el-divider content-position="left" class="top-tip">邮箱修改</el-divider>
        <el-form style="width: 50%;" label-width="100px" ref="form" :model="form">
            <el-form-item prop="email" label="消息邮箱:"
                    :rules="[{ type: 'email', message: '请输入正确的邮箱地址', trigger: ['blur', 'change'] }]">
                <el-input v-model="form.email" placeholder="接收提醒的邮箱"></el-input>
            </el-form-item>
            <el-button type="primary" size="medium" @click="submitForm('form')" style="float: right; width: 100px;">修改邮箱</el-button>
        </el-form>
    </div>


</template>

<script>
    export default {
        name: "EMail",
        props: {changeEmail: {type: Function, require: true}},
        data(){
            return{
                form: {
                    email: '',
                },
            }
        },
        methods: {
            submitForm(formName) {
                this.$refs[formName].validate((valid) => {
                    if (valid) {
                        this.changeEmail(this.form.email);
                    } else {
                        return false;
                    }
                });
            },
        }
    }
</script>

<style scoped>
    .top-tip{
        margin-left: 10px;
        width: 80%;
    }
</style>