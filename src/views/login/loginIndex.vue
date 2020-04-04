<template>
  <div id="container">
    <el-card id="main">
      <!-- 网站logo -->
      <div id="topContainer">
        <h3>网站LOGO</h3>
      </div>
      <el-tabs id="formContainer" :stretch="true" v-model="activeName">
        <!-- 登录模块 -->
        <el-tab-pane label="登录" name="login">
          <el-form ref="loginForm" :rules="loginRules" :model="loginForm">
            <el-form-item prop="account">
              <el-input placeholder="手机号/邮箱" prefix-icon="el-icon-user" v-model="loginForm.account" />
            </el-form-item>
            <el-form-item prop="password">
              <el-input placeholder="请输入密码" prefix-icon="el-icon-key" v-model="loginForm.password" show-password />
            </el-form-item>
            <el-form-item>
              <el-button type="primary" style="width: 100%" @click="login('loginForm')">登录</el-button>
            </el-form-item>
          </el-form>
        </el-tab-pane>
        <!-- 注册模块 -->
        <el-tab-pane label="注册" name="register">
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
              <el-button type="primary" style="width: 100%" @click="register('registerForm')">注册</el-button>
            </el-form-item>
          </el-form>
        </el-tab-pane>
      </el-tabs>
      <!-- 联系我们 -->
      <div id="bottomContainer">如登录、注册遇到问题，请联系我们</div>
    </el-card>
  </div>
</template>

<style lang="less" scoped>
#container {
  top: 0;
  left: 0;
  width: 100%;
  height: 100vh;
  display: flex;
  justify-content: center;
  align-items: center;
  position: absolute;
}

#main {
  width: 400px;
  height: 500px;
  display: flex;
  flex-direction: column;
  justify-content: flex-start;
  align-items: center;
}

#topContainer {
  font-size: 14px;
  height: 70px;
  color: #909399;
  display: flex;
  align-items: center;
  justify-content: center;
}

#bottomContainer{
  font-size: 14px;
  color: #909399;
  display: flex;
  align-items: center;
  justify-content: center;
}

#formContainer {
  height: 370px;
  width: 350px;
}
</style>

<script>
import store from '../../store'
import { login } from '../../api/user'
import { setToken } from '../../utils/auth'

export default {
  data () {
    return {
      // 当前激活的tab（login/register）
      activeName: this.$route.query.mode,
      // 登录提交表单
      loginForm: {
        account: '',
        password: ''
      },
      // 登录提交表单验证规则
      loginRules: {
        account: [
          { required: true, message: '请输入账号', trigger: 'blur' }
        ],
        password: [
          { required: true, message: '请输入密码', trigger: 'blur' }
        ]
      },
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

  created () {
    // 隐藏导航条
    store.commit('setNavBarHidden', true)
    localStorage.setItem('token', '1111')
  },

  destroyed () {
    // 显示导航条
    store.commit('setNavBarHidden', false)
  },

  methods: {
    /**
     * 登录
     */
    login (formName) {
      // 表单验证
      this.$refs[formName].validate((valid) => {
        if (valid) {
          login(formName).then((res) => {
            // 存储token
            setToken(res.data.token)
            // 跳转到首页
            this.$router.push({
              path: '/'
            })
          })
        }
      })
    },

    /**
     * 注册
     */
    register (formName) {
      // 表单验证
      this.$refs[formName].validate()
    }

  }
}
</script>
