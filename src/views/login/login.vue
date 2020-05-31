<template>
  <div>
    <el-form ref="loginForm" :rules="loginRules" :model="loginForm">
      <el-form-item prop="username">
        <el-input placeholder="用户名" prefix-icon="el-icon-user" v-model="loginForm.username" />
      </el-form-item>
      <el-form-item prop="password">
        <el-input placeholder="请输入密码" prefix-icon="el-icon-key" v-model="loginForm.password" show-password />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" style="width: 100%" :loading="loginLoading" @click="login('loginForm')">登录</el-button>
      </el-form-item>
    </el-form>

    <!-- 下面功能是没有用的啊哈哈哈 -->
    <div class="useless-1">
      <div>
        <el-checkbox />
        <span class="uselessBtn">&nbsp;记住我</span>
      </div>
      <span class="uselessBtn">忘记密码</span>
    </div>

    <el-divider class="tips">其他登录方式</el-divider>

    <div class="useless-2">
      <iconfont class="iconBtn" name="icon-qq" />
      <iconfont class="iconBtn" name="icon-weixin" />
      <iconfont class="iconBtn" name="icon-weibo" />
    </div>
  </div>
</template>

<style lang="less" scoped>
.useless-1 {
  font-size: 14px;
  color: #909399;
  display: flex;
  align-items: center;
  justify-content: space-between;
}

.useless-2 {
  font-size: 14px;
  color: #909399;
  display: flex;
  align-items: center;
  justify-content: space-around;
}

.iconBtn {
  font-size: 30px;
}

.iconBtn:hover {
  cursor: pointer;
}

.uselessBtn:hover {
  color: #606266;
  cursor: pointer;
}
</style>

<script>
import store from 'store'
import { login } from 'api/user'
import { setToken } from 'utils/auth'

export default {
  data () {
    return {
      // 是否正在处理登录
      loginLoading: false,
      // 登录提交表单
      loginForm: {
        username: '',
        password: ''
      },
      // 登录提交表单验证规则
      loginRules: {
        username: [
          { required: true, message: '请输入用户名', trigger: 'blur' }
        ],
        password: [
          { required: true, message: '请输入密码', trigger: 'blur' }
        ]
      }
    }
  },

  methods: {
    /**
     * 登录
     */
    login (formName) {
      // 表单验证
      this.$refs[formName].validate((valid) => {
        if (valid) {
          this.loginLoading = true
          login(this.loginForm).then((res) => {
            // ———— 成功回调 ————
            const data = res.data
            switch (data.code) {
              case 200: // 登录成功
                // 存储token
                setToken(data.token)
                store.commit('setIsloggedIn', true)
                store.dispatch('updateUserInfo')
                // 跳转到首页
                this.$router.push({
                  path: '/'
                })
                break
              case 501: // 账号密码错误
                this.$message({
                  message: '账号或密码错误',
                  type: 'error'
                })
                break
            }
          }).catch((err) => {
            // ———— 错误处理 ————
            this.$message({
              message: '登录失败:' + err.message,
              type: 'error'
            })
          }).finally(() => {
            // ———— 最终执行 ————
            // 结束按钮加载中的状态
            this.loginLoading = false
          })
        }
      })
    }
  }
}
</script>
