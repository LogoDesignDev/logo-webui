<template>
  <div>
    <view-title name="排行榜" tips="每天0:00更新" to="/a" />
    <div id="main">
      <show-card-1
        src="https://cube.elemecdn.com/6/94/4d3ea53c084bad6931a56d5158a48jpeg.jpeg"
        icon="icon-rank" name="综合榜" tips="综合各项人气指标" to="/a" />
      <show-card-1
        src="https://fuss10.elemecdn.com/a/3f/3302e58f9a181d2509f3dc0fa68b0jpeg.jpeg"
        icon="icon-like" name="点赞榜" tips="根据点赞数排行" to="/a" />
      <show-card-1
        src="https://fuss10.elemecdn.com/e/5d/4a731a90594a4af544c0c25941171jpeg.jpeg"
        icon="icon-collect" name="收藏榜" tips="根据收藏数排行" to="/a" />
    </div>
  </div>
</template>

<style lang="less" scoped>
#main {
  margin-top: 10px;
  display: flex;
  justify-content: space-between;
}
</style>

<script>
import viewTitle from './components/viewTitle-1'
import showCard from './components/showCard-1'

export default {
  components: {
    'view-title': viewTitle,
    'show-card-1': showCard
  }
}
</script>
