<template>
  <el-form
    ref="form"
    :rules="rules"
    :model="form">
    <el-form-item label="新邮箱" prop="newEmail">
      <span class="tips">(已绑定邮箱：{{oldEmail}})</span>
      <el-input placeholder="请输入新邮箱" prefix-icon="el-icon-message" v-model="form.newEmail"/>
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
import { changeEmail } from 'api/user'

export default {
  props: ['oldEmail'],

  data () {
    /**
     * newEmail的校验
     */
    const checkNewEmail = (rule, value, callback) => {
      if (value === this.oldEmail) {
        callback(new Error('新邮箱地址不能与原邮箱一样'))
      } else {
        callback()
      }
    }

    return {
      // 是否正在处理保存
      loading: false,
      // 提交表单
      form: {
        newEmail: ''
      },
      // 登录提交表单验证规则
      rules: {
        newEmail: [
          { required: true, message: '请输入新邮箱', trigger: 'blur' },
          { type: 'email', message: '请输入正确的邮箱地址', trigger: 'blur' },
          { validator: checkNewEmail, trigger: 'blur' }
        ]
      }
    }
  },

  methods: {
    submit (formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          this.loading = true
          this.form.token = getToken()
          changeEmail(this.form).then((res) => {
            // ———— 成功回调 ————
            const data = res.data
            switch (data.code) {
              case 200: // 修改成功
                this.$emit('changeSuccess')
                this.$message({
                  message: '邮箱修改成功',
                  type: 'success'
                })
                break
              case 501: // 修改失败
                this.$message({
                  message: '邮箱修改失败',
                  type: 'error'
                })
                break
            }
          }).catch((err) => {
            // ———— 错误处理 ————
            this.$message({
              message: '邮箱修改失败:' + err.message,
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
