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
      <div class="rightContainer">
        <el-button class="iconBtn" icon="el-icon-search" />
        <el-divider direction="vertical" />
        <button class="textBtn" @click="toLogin">登录</button>
        <button class="textBtn" @click="toRegister">注册</button>
      </div>
      <!-- 已登录 -->
      <!-- <el-submenu class="rightContainer" show-timeout="0" index="2">
        <template slot="title">
          <img class="userPic-mini" src="https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1585913379050&di=15a41f4f8021602de11e155aada67180&imgtype=0&src=http%3A%2F%2Fimg.pconline.com.cn%2Fimages%2Fupload%2Fupc%2Ftx%2Fitbbs%2F2003%2F05%2Fc4%2F195593837_1583372967744_1024x1024it.jpg">
          <span>用户名</span>
        </template>
        <el-menu-item index="2-1">个人中心</el-menu-item>
        <el-menu-item index="2-2">退出登录</el-menu-item>
      </el-submenu> -->
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

export default {
  data () {
    return {

    }
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
