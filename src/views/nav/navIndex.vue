<template>
  <div class="container">
    <!-- 导航栏 -->
    <el-menu v-if="!navBarHidden" :default-active="path" class="nav" mode="horizontal">
      <!-- 居中显示标签（hidden为true则不显示） -->
      <el-menu-item
        v-for="item in removeHiddenRoutes"
        :key="item.path"
        :index="item.path"
        @click="toPath(item.path)">
        {{item.meta.title}}
      </el-menu-item>
    </el-menu>
    <div class="nav-other">
      <!-- 导航栏左侧 -->
      <div v-if="!navBarHidden" class="leftContainer">
        <h3>网站LOGO</h3>
      </div>

      <!-- 导航栏右侧 -->
      <div v-if="!navBarHidden" class="rightContainer">
        <!-- 搜索按钮 -->
        <button
          class="el-icon-search iconButton"
          @mouseenter="showSearchCard"
          @mouseleave="hideSearchCard" />
        <!-- 个人信息 -->
        <el-divider direction="vertical" />
        <!-- 未登录 -->
        <div v-if="!isloggedIn">
          <span class="textBtn" @click="toLogin">登录</span>
          <span class="textBtn" @click="toRegister">注册</span>
        </div>
        <!-- 已登录 -->
        <div v-else>
          <personal-card />
        </div>
      </div>
    </div>

    <!-- 搜索框 -->
    <div @mouseenter="showSearchCard" @mouseleave="hideSearchCard">
      <transition name="el-zoom-in-top">
        <search-card v-show="visible.searchCardVisible" />
      </transition>
    </div>

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
  position: relative;
}

.nav-other {
  z-index: 99;
  pointer-events: none;
  width: 1000px;
  height: 60px;
  top: 0px;
  left: 50%;
  margin-left: -500px;
  display: flex;
  align-items: center;
  justify-content: space-between;
  position: absolute;
}

.footer {
  margin-top: 20px;
  height: 200px;
  display: flex;
  align-items: center;
  justify-content: center;
  background: rgb(42, 47, 53);
}

.leftContainer {
  pointer-events: all;
  font-size: 14px;
  color: #909399;
  display: flex;
  align-items: center;
  justify-content: center;
}

.rightContainer {
  pointer-events: all;
  font-size: 14px;
  height: 60px;
  color: #909399;
  display: flex;
  align-items: center;
  justify-content: center;
}

.textBtn {
  margin: 0 5px;
  font-size: 14px;
  color: #909399;
  background: none;
  border: none;
  cursor: pointer;
}

.textBtn:hover {
  color: #303133;
}

.iconButton {
  margin: 0 5px;
  height: 60px;
  width: 50px;
  font-size: 20px;
  font-weight: bolder;
  color: #909399;
  border: none;
  border-top: 3px transparent solid;
  border-bottom: 3px transparent solid;
  background: none;
  cursor: pointer;
  transition: all 0.3s;
}

.iconButton:hover {
  border-top: 3px #409EFF solid;
  color: #409EFF;
}

</style>

<script>
import store from 'store'
import personalCard from './personalCard/personalCardIndex'
import searchCard from './searchCard/searchCardIndex'

export default {
  components: {
    'personal-card': personalCard,
    'search-card': searchCard
  },

  data () {
    return {
      visible: {
        searchCardVisible: false
      },
      searchCardTimer: null // 用于悬浮显示card
    }
  },

  created () {
    store.dispatch('updateUserInfo')
  },

  computed: {
    /**
     * 过滤掉隐藏的路由（hidden为true）
     */
    removeHiddenRoutes () {
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
    navBarHidden () {
      return store.state.navBarHidden
    },

    /**
     * 是否已登录
     */
    isloggedIn () {
      return store.state.isloggedIn
    },

    userInfo () {
      return store.state.userInfo
    },

    /**
     * 当前路由
     */
    path () {
      const end = this.$route.path.indexOf('/', 1)

      if (end === -1) {
        return this.$route.path
      } else {
        return this.$route.path.slice(0, end)
      }
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
     * 显示“搜索”选项卡
     */
    showSearchCard () {
      this.visible.searchCardVisible = true
      if (this.searchCardTimer) {
        clearTimeout(this.searchCardTimer)
        this.searchCardTimer = null
      }
    },

    /**
     * 隐藏“搜索”选项卡
     * 但要做一个延时
     */
    hideSearchCard () {
      const _this = this

      this.searchCardTimer = setTimeout(function () {
        _this.visible.searchCardVisible = false
      }, 100)
    }
  }
}
</script>
