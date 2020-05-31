<template>
  <el-form ref="registerForm" :rules="registerRules" :model="registerForm">
    <el-form-item prop="username">
      <el-input placeholder="用户名" prefix-icon="el-icon-user" v-model="registerForm.username" />
    </el-form-item>
    <el-form-item prop="phone">
      <el-input placeholder="手机号码" prefix-icon="el-icon-phone-outline" v-model="registerForm.phone" />
    </el-form-item>
    <el-form-item prop="email">
      <el-input placeholder="邮箱" prefix-icon="el-icon-message" v-model="registerForm.email" />
    </el-form-item>
    <el-form-item prop="password">
      <el-input placeholder="请输入密码" prefix-icon="el-icon-key" v-model="registerForm.password" show-password />
    </el-form-item>
    <el-form-item>
      <el-button type="primary" style="width: 100%" :loading="registerLoading" @click="register('registerForm')">注册</el-button>
    </el-form-item>
  </el-form>
</template>

<script>
import { register } from 'api/user'

export default {
  data () {
    return {
      // 是否正在处理注册
      registerLoading: false,
      // 注册提交表单
      registerForm: {
        username: '',
        phone: '',
        email: '',
        password: ''
      },
      // 注册提交表单验证规则
      registerRules: {
        username: [
          { required: true, message: '请输入用户名', trigger: 'blur' }
        ],
        phone: [
          { required: true, message: '请输入手机号码', trigger: 'blur' }
        ],
        email: [
          { required: true, message: '请输入邮箱', trigger: 'blur' }
        ],
        password: [
          { required: true, message: '请输入密码', trigger: 'blur' }
        ]
      }
    }
  },

  methods: {
    /**
     * 注册
     */
    register (formName) {
      // 表单验证
      this.$refs[formName].validate((valid) => {
        if (valid) {
          this.registerLoading = true
          register(this.registerForm).then((res) => {
            // ———— 成功回调 ————
            const data = res.data
            switch (data.code) {
              case 200: // 注册成功
                this.$message({
                  message: '注册成功',
                  type: 'success'
                })
                // 跳转到登录页
                this.$emit('changeTab', 'login')
                // 清空表单
                this.$refs.registerForm.resetFields()
                break
              case 501:
                this.$message({
                  message: '该手机号码已被注册',
                  type: 'error'
                })
                break
              case 502:
                this.$message({
                  message: '该邮箱已被注册',
                  type: 'error'
                })
                break
              case 503:
                this.$message({
                  message: '该用户名已被注册',
                  type: 'error'
                })
                break
            }
          }).catch((err) => {
            // ———— 错误处理 ————
            console.log(err)
          }).finally(() => {
            // ———— 最终执行 ————
            // 结束按钮加载中的状态
            this.registerLoading = false
          })
        }
      })
    }
  }
}
</script>
