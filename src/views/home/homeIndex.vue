<template>
  <div id="container">
    <!-- 轮播图（todo） -->
    <img src="../../assets/home_bg.png">

    <div id="main">
      <!-- 榜单 -->
      <rank /><br><br>
      <!-- 推荐 -->
      <recommend />
    </div>
  </div>
</template>

<style lang="less" scoped>
#container {
  display: flex;
  flex-direction: column;
  align-items: center;
}

#container>img {
  width: 100%;
}

#main {
  margin-top: 50px;
  width: 1000px;
}
</style>

<script>
import rank from './rank'
import recommend from './recommend'

export default {
  name: 'Home',
  components: {
    rank: rank,
    recommend: recommend
  }
}
</script>
