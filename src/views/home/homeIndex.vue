<template>
  <div class="container">
    <!-- 轮播图（todo） -->
    <img src="../../assets/home_bg.png">

    <div id="main">
      <!-- 榜单 -->
      <rank />
    </div>
  </div>
</template>

<style lang="less" scoped>
.container {
  display: flex;
  flex-direction: column;
  align-items: center;
}

#main {
  margin-top: 50px;
  width: 1000px;
}
</style>

<script>
import rank from './rank'

export default {
  name: 'Home',
  components: {
    rank: rank
  }
}
</script>
