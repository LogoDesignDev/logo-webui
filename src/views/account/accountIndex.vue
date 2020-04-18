<template>
  <div class="container">
    <!-- 主区域 -->
    <div id="mainContainer">
      <el-tabs id="mainTab" tab-position="left" v-model="activeName" @tab-click="loadActivePage">
        <el-tab-pane label="基本信息" name="base" />
        <el-tab-pane label="账户安全" name="security" />
      </el-tabs>
      <router-view />
    </div>
  </div>
</template>

<style lang="less" scoped>
.container {
  display: flex;
  justify-content: center;
}

.childContainer {
  margin-top: 18px;
  width: 90%;
}

#mainContainer {
  margin-top: 10px;
  width: 1000px;
  display: flex;
}

#mainTab {
  margin: 10px 30px;
}
</style>

<script>
export default {
  data () {
    return {
      activeName: this.$route.name
    }
  },

  methods: {
    /**
     * 加载当前激活的页面
     */
    loadActivePage (tab) {
      this.$router.push({
        path: '/account/' + tab.name
      })
    }
  }
}
</script>
