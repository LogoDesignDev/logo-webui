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
          <login-page />
        </el-tab-pane>
        <!-- 注册模块 -->
        <el-tab-pane label="注册" name="register">
          <register-page @changeTab="changeTab"/>
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
import store from 'store'
import loginPage from './login'
import registerPage from './register'

export default {
  components: {
    'login-page': loginPage,
    'register-page': registerPage
  },
  data () {
    return {
      // 当前激活的tab（login/register）
      activeName: this.$route.query.mode
    }
  },

  created () {
    // 隐藏导航条
    store.commit('setNavBarHidden', true)
  },

  destroyed () {
    // 显示导航条
    store.commit('setNavBarHidden', false)
  },

  methods: {
    changeTab (activeName) {
      this.activeName = activeName
    }
  }
}
</script>
