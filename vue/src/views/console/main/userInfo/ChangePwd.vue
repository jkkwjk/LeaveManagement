<template>
    <div class="change-pwd">
        <el-divider content-position="left" class="top-tip">密码修改</el-divider>
        <el-form :model="form" status-icon :rules="rules" ref="form" label-width="100px" style="width: 50%;">
            <el-form-item label="密码:" prop="pwd">
                <el-input type="password" v-model="form.pwd" autocomplete="off" placeholder="请输入密码"></el-input>
            </el-form-item>
            <el-form-item label="确认密码:" prop="checkPwd" style="margin-top:30px;">
                <el-input type="password" v-model="form.checkPwd" autocomplete="off" placeholder="确认密码"></el-input>
            </el-form-item>
            <el-form-item>
                <el-button type="primary" size="medium" @click="submitForm('form')" style="float: right; width: 100px;">修改密码</el-button>
            </el-form-item>
        </el-form>
    </div>

</template>

<script>
    export default {
        name: "ChangePwd",
        props: {changePwd: {type: Function, require: true}},
        data() {
            let validatePwd = (rule, value, callback) => {
                if (value === '') {
                    callback(new Error('请输入密码'));
                } else {
                    const reg = "^(?!\d{6,8}$)(?! )(?=.*[a-z])(?=.*[0-9])[a-zA-Z0-9_]{6,16}$";
                    const pwdReg = new RegExp(reg);
                    if (!pwdReg.test(value)){
                        callback(new Error('6-16位字符，区分大小写（不能是9位以下的纯数字，不含空格）'))
                    }
                    if (this.form.checkPwd !== '') {
                        this.$refs.form.validateField('checkPwd');
                    }
                    callback();
                }
            };
            let validatePwd2 = (rule, value, callback) => {
                if (value === '') {
                    callback(new Error('请再次输入密码'));
                } else if (value !== this.form.pwd) {
                    callback(new Error('两次输入密码不一致!'));
                } else {
                    callback();
                }
            };
            return {
                form: {
                    pwd: '',
                    checkPwd: ''
                },
                rules: {
                    pwd: [
                        { validator: validatePwd, trigger: 'blur' }
                    ],
                    checkPwd: [
                        { validator: validatePwd2, trigger: 'blur' }
                    ],
                }
            };
        },
        methods: {
            submitForm(formName) {
                this.$refs[formName].validate((valid) => {
                    if (valid) {
                        this.changePwd(this.form.pwd);
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