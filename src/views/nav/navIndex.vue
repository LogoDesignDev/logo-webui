<template>
  <div>
    <el-menu v-if="!navBarHidden" :default-active="$route.path" class="nav" mode="horizontal">
      <div class="leftContainer">
        <h3>网站LOGO</h3>
      </div>

      <!-- 居中显示标签（hidden为true则不显示） -->
      <el-menu-item
        v-for="item in removeHiddenRoutes"
        :key="item.path"
        :index="item.path"
        @click="toPath(item.path)">
        {{item.meta.title}}
      </el-menu-item>

      <!-- 未登录 -->
      <div v-if="!isloggedIn" class="rightContainer">
        <el-divider direction="vertical" />
        <button class="textBtn" @click="toLogin">登录</button>
        <button class="textBtn" @click="toRegister">注册</button>
      </div>
      <!-- 已登录 -->
      <div v-else class="rightContainer">
        <personal-card />
      </div>
    </el-menu>

    <router-view />

    <!-- 备案号 -->
    <div class="footer" v-if="!navBarHidden">
      <a class="tips" href="www.beian.miit.gov.cn">粤ICP备19158305号</a>
    </div>
  </div>
</template>

<style lang="less" scoped>
.nav {
  height: 60px;
  font-size: 15px;
  display: flex;
  align-items: center;
  justify-content: center;
}

.footer {
  margin-top: 20px;
  height: 150px;
  display: flex;
  align-items: center;
  justify-content: center;
  background: rgb(245, 245, 245);
}

.leftContainer {
  font-size: 14px;
  color: #909399;
  display: flex;
  align-items: center;
  justify-content: center;
  left: 150px;
  position: absolute;
}

.rightContainer {
  font-size: 14px;
  width: 100px;
  color: #909399;
  display: flex;
  align-items: center;
  justify-content: center;
  right: 150px;
  position: absolute;
}

.textBtn {
  font-size: 14px;
  color: #909399;
  background: none;
  border: none;
  cursor: pointer;
}

.textBtn:hover {
  font-size: 14px;
  color: #303133;
  background: none;
  border: none;
}

</style>

<script>
import store from 'store'
import personalCard from './personalCard/personalCardIndex'

export default {
  components: {
    'personal-card': personalCard
  },

  data () {
    return {
    }
  },

  created () {
    store.dispatch('updateUserInfo')
  },

  computed: {
    /**
     * 过滤掉隐藏的路由（hidden为true）
     */
    removeHiddenRoutes: function () {
      const tempRoutes = []
      const routes = this.$router.options.routes

      for (let i = 0; i < routes.length; i++) {
        if (!routes[i].hidden) {
          tempRoutes.push(routes[i])
        }
      }

      return tempRoutes
    },

    /**
     * 是否隐藏导航栏
     */
    navBarHidden: function () {
      return store.state.navBarHidden
    },

    /**
     * 是否已登录
     */
    isloggedIn: function () {
      return store.state.isloggedIn
    },

    userInfo: function () {
      return store.state.userInfo
    }
  },

  methods: {
    /**
     * 标签路由跳转
     */
    toPath (newPath) {
      this.$router.push({
        path: newPath
      })
    },

    /**
     * 转到登录页
     */
    toLogin () {
      this.$router.push({
        path: '/login',
        query: {
          mode: 'login'
        }
      })
    },

    /**
     * 转到注册页
     */
    toRegister () {
      this.$router.push({
        path: '/login',
        query: {
          mode: 'register'
        }
      })
    }
  }
}
</script>
