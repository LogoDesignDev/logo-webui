<template>
  <div class="container">
    <!-- 背景 -->
    <div id="background" />

    <!-- 内容区域 -->
    <div id="main">
      <!-- 头部 -->
      <div id="header">
        <el-image class="userPic-normal" :src="userInfo.userPicUrl" />
        <div id="middle">
          <span> {{userInfo.username}}</span>
          <!-- tab -->
          <el-tabs v-model="activeName" @tab-click="loadActivePage">
            <el-tab-pane label="关注" name="followAndFans" />
            <el-tab-pane label="我的作品" name="fourth">定时任务补偿</el-tab-pane>
          </el-tabs>
        </div>
        <div>
          <el-tooltip class="item" effect="dark" content="账户安全设置" placement="bottom">
            <el-button type="danger" icon="el-icon-setting" circle @click="toAccountSecurity" />
          </el-tooltip>
          <el-tooltip class="item" effect="dark" content="编辑个人信息" placement="bottom">
            <el-button type="info" icon="el-icon-edit" circle @click="toAccountBase" />
          </el-tooltip>
        </div>
      </div>
      <!-- router-view -->
      <router-view />
    </div>
  </div>
</template>

<style lang="less" scoped>
.container {
  display: flex;
  justify-content: center;
}

#background {
  z-index: -1;
  width: 100%;
  margin-top: 10px;
  height: 250px;
  background: #409EFF;
  position: absolute;
}

#main {
  margin-top: 185px;
  width: 1000px;
}

#header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

#middle {
  margin: -40px 20px 0 20px;
  flex-grow: 1; // 撑大中间区域
  font-size: 20px;
  color: #ffffff;
}

.el-tabs {
  top: 40px;
  height: 0;
  position: relative;
}

</style>

<script>
import store from '@/store'

export default {
  data () {
    return {
      activeName: this.$route.name
    }
  },

  computed: {
    userInfo: function () {
      return store.state.userInfo
    }
  },

  methods: {
    /**
     * 转到账户安全页
     */
    toAccountSecurity () {
      this.$router.push({
        path: '/account/security'
      })
    },

    /**
     * 转到基本信息修改页
     */
    toAccountBase () {
      this.$router.push({
        path: '/account/base'
      })
    },

    /**
     * 加载当前激活的页面
     */
    loadActivePage (tab) {
      this.$router.push({
        path: '/personal/' + tab.name
      })
    }
  }
}
</script>
