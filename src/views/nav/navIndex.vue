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
        <el-button class="iconBtn" icon="el-icon-search" />
        <el-divider direction="vertical" />
        <button class="textBtn" @click="toLogin">登录</button>
        <button class="textBtn" @click="toRegister">注册</button>
      </div>
      <!-- 已登录 -->
      <el-submenu v-if="isloggedIn" class="rightContainer" show-timeout="0" index="2">
        <template slot="title">
          <img class="userPic-mini" :src="navUserInfo.userPicUrl">
          <span>{{ navUserInfo.username }}</span>
        </template>
        <el-menu-item index="2-1">个人中心</el-menu-item>
        <el-menu-item index="2-2" @click="logout">退出登录</el-menu-item>
      </el-submenu>
    </el-menu>
    <router-view />
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

.leftContainer {
  font-size: 14px;
  color: #909399;
  display: flex;
  align-items: center;
  justify-content: center;
  left: 50px;
  position: absolute;
}

.rightContainer {
  font-size: 14px;
  color: #909399;
  display: flex;
  align-items: center;
  justify-content: center;
  right: 50px;
  position: absolute;
}

.iconBtn {
  font-size: 20px;
  color: #909399;
  background: none;
  border: none;
  cursor: pointer;
}

.iconBtn:hover {
  font-size: 20px;
  color: #409EFF !important;
  background: none;
  border: none;
  cursor: pointer;
}

.iconBtn:focus {
  background: none;
  color: #909399;
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

.userPic-mini {
  margin-right: 5px;
  width: 30px;
  height: 30px;
  border-radius: 15px;
}
</style>

<script>
import store from '../../store'
import { getUserInfo } from '../../api/user'
import { removeToken } from '../../utils/auth'

export default {
  data () {
    return {
      // 导航栏的用户信息
      navUserInfo: {
        username: '',
        userPicUrl: ''
      }
    }
  },

  created () {
    this.updateNavUserInfo()
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
    },

    /**
     * 更新导航栏的用户信息
     */
    updateNavUserInfo () {
      getUserInfo({}).then((res) => {
        const data = res.data

        this.navUserInfo.username = data.username
        this.navUserInfo.userPicUrl = data.userPicUrl
      })
    },

    /**
     * 退出登录
     */
    logout () {
      removeToken()
      store.commit('setIsloggedIn', false)
    }
  }
}
</script>
