<template>
  <div id="container">
    <!-- 轮播图（todo） -->
    <img src="https://s1.ax1x.com/2020/06/07/t2WUmT.png">

    <div id="main">
      <!-- 榜单 -->
      <rank /><br><br>
      <!-- 推荐 -->
      <recommend /><br><br>
      <!-- 设计师 -->
      <designer />
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
import designer from './designer'

export default {
  name: 'Home',
  components: {
    rank: rank,
    recommend: recommend,
    designer: designer
  }
}
</script>
