<template>
  <div class="container">
    <!-- 导航栏 -->
    <el-menu
      :default-active="mode"
      @select="(index) => mode = index">
      <el-menu-item index="follow">
        <i class="el-icon-s-opportunity" />
        <span slot="title">我关注的</span>
      </el-menu-item>
      <el-menu-item index="fans">
        <i class="el-icon-s-custom" />
        <span slot="title">我的粉丝</span>
      </el-menu-item>
    </el-menu>

    <!-- 内容 -->
    <div id="main">
      <follow-page v-if="mode === 'follow'" />
      <fans-page v-if="mode === 'fans'" />
    </div>
  </div>
</template>

<style lang="less" scoped>
.container {
  width: 100%;
  margin-top: 50px;
}

.el-menu {
  left: 0;
  width: 150px;
}

#main {
  width: 100%;
  margin: 0 20px;
}
</style>

<script>
import followPage from './follow'
import fansPage from './fans'

export default {
  components: {
    'follow-page': followPage,
    'fans-page': fansPage
  },

  data () {
    return {
      mode: this.$route.query.mode || 'follow'
    }
  },

  methods: {
  }
}
</script>
