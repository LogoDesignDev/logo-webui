<template>
  <div class="container">
    <!-- 主区域 -->
    <div id="mainArea">
      <el-tabs id="mainTab" tab-position="left" v-model="activeName" @tab-click="loadActivePage">
        <!-- 基本信息编辑Tab -->
        <el-tab-pane label="基本信息" name="base">
          <base-page  v-if="active.base" />
        </el-tab-pane>

        <!-- 账户设置功能Tab -->
        <el-tab-pane label="账户设置" name="account">
          <div v-if="active.account">
            配置管理
          </div>
        </el-tab-pane>
      </el-tabs>
    </div>
  </div>
</template>

<style lang="less" scoped>
.container {
  width: 100;
  display: flex;
  justify-content: center;
}

#mainArea {
  margin-top: 10px;
  width: 1000px;
}

#mainTab {
  margin: 10px 30px;
}
</style>

<script>
import basePage from './base/baseIndex.vue'

export default {
  components: {
    'base-page': basePage
  },

  data () {
    return {
      activeName: 'base',
      active: {
        base: true,
        account: false
      }
    }
  },

  methods: {
    /**
     * 加载当前激活的页面
     */
    loadActivePage (tab) {
      for (const key in this.active) {
        if (key === tab.name) {
          this.active[key] = true
        } else {
          this.active[key] = false
        }
      }
    }
  }
}
</script>/>
