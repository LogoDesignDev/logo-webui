<template>
  <el-form
    ref="form"
    :rules="rules"
    :model="form">
    <el-form-item label="旧密码" prop="oldPwd">
      <el-input placeholder="请输入旧密码" prefix-icon="el-icon-key" v-model="form.oldPwd" show-password />
    </el-form-item>
    <el-form-item  label="新密码"  prop="newPwd">
      <el-input placeholder="请输入新密码" prefix-icon="el-icon-key" v-model="form.newPwd" show-password />
    </el-form-item>
    <el-form-item  label="确认密码"  prop="confirmPwd">
      <el-input placeholder="请再次输入新密码" prefix-icon="el-icon-key" v-model="form.confirmPwd" show-password />
    </el-form-item>
    <el-form-item>
      <el-button type="primary" style="width: 100%" :loading="loading" @click="submit('form')">保存</el-button>
    </el-form-item>
  </el-form>
</template>

<style lang="less" scoped>
.el-form {
  padding: 0 50px;;
}
</style>

<script>
import { getToken } from 'utils/auth'
import { changePassword } from 'api/user'

export default {
  data () {
    /**
     * confirmPwd的校验
     */
    const checkConfirmPwd = (rule, value, callback) => {
      if (value !== this.form.newPwd) {
        callback(new Error('两次输入的密码不一样'))
      } else {
        callback()
      }
    }

    return {
      // 是否正在处理保存
      loading: false,
      // 提交表单
      form: {
        oldPwd: '',
        newPwd: '',
        confirmPwd: ''
      },
      // 登录提交表单验证规则
      rules: {
        oldPwd: [
          { required: true, message: '请输入旧密码', trigger: 'blur' }
        ],
        newPwd: [
          { required: true, message: '请输入新密码', trigger: 'blur' }
        ],
        confirmPwd: [
          { required: true, message: '请再次确认密码', trigger: 'blur' },
          { validator: checkConfirmPwd, trigger: 'blur' }
        ]
      }
    }
  },

  methods: {
    submit (formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          const params = {
            oldPwd: this.form.oldPwd,
            newPwd: this.form.newPwd,
            token: getToken()
          }
          this.loading = true
          changePassword(params).then((res) => {
            // ———— 成功回调 ————
            const data = res.data
            switch (data.code) {
              case 200: // 修改成功
                this.$emit('changeSuccess')
                this.$message({
                  message: '密码修改成功',
                  type: 'success'
                })
                break
              case 501: // 旧密码错误
                this.$message({
                  message: '密码修改失败',
                  type: 'error'
                })
                break
              case 502: // 旧密码错误
                this.$message({
                  message: '原密码错误',
                  type: 'error'
                })
                break
            }
          }).catch((err) => {
            // ———— 错误处理 ————
            this.$message({
              message: '密码修改失败:' + err.message,
              type: 'error'
            })
          }).finally(() => {
            // ———— 最终执行 ————
            // 结束按钮加载中的状态
            this.loading = false
          })
        }
      })
    }
  }
}
</script>
