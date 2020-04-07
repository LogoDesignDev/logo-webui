<template>
  <el-form
    ref="form"
    :rules="rules"
    :model="form">
    <el-form-item label="新手机" prop="newPhone">
      <span class="tips">(已绑定手机：{{oldPhone}})</span>
      <el-input placeholder="请输入新手机" prefix-icon="el-icon-phone-outline" v-model="form.newPhone"/>
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
import { changePhone } from 'api/user'

export default {
  props: ['oldPhone'],

  data () {
    /**
     * newPhone的校验
     */
    const checkNewPhone = (rule, value, callback) => {
      if (value === this.oldPhone) {
        callback(new Error('新手机号码不能与原号码一样'))
      } else {
        callback()
      }
    }

    return {
      // 是否正在处理保存
      loading: false,
      // 提交表单
      form: {
        newPhone: ''
      },
      // 登录提交表单验证规则
      rules: {
        newPhone: [
          { required: true, message: '请输入新手机号码', trigger: 'blur' },
          { min: 11, max: 11, message: '请输入正确的手机号码', trigger: 'blur' },
          { validator: checkNewPhone, trigger: 'blur' }
        ]
      }
    }
  },

  methods: {
    submit (formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          this.loading = true
          changePhone(this.form).then((res) => {
            // ———— 成功回调 ————
            const data = res.data
            switch (data.code) {
              case 20000: // 修改成功
                this.$emit('changeSuccess')
                this.$message({
                  message: '手机号码修改成功',
                  type: 'success'
                })
                break
            }
          }).catch((err) => {
            // ———— 错误处理 ————
            this.$message({
              message: '手机号码修改失败:' + err.message,
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
