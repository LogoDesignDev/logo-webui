<template>
  <div>
    <view-title name="推荐作品" tips="" to="/a" />
    <div id="main">
      <show-card-2
        v-for="count in 8" :key="count"
        src="https://cube.elemecdn.com/6/94/4d3ea53c084bad6931a56d5158a48jpeg.jpeg"
        title="综合榜" tips="综合各项人气指标" to="/a" />
    </div>
  </div>
</template>

<style lang="less" scoped>
.template>div {
  margin-top: 50px;
}

#main {
  margin-top: 10px;
  display: flex;
  flex-wrap: wrap;
  justify-content: space-between;
  align-content: space-between;
}
</style>

<script>
import viewTitle from './components/viewTitle'
import showCard from './components/showCard-2'

export default {
  components: {
    'view-title': viewTitle,
    'show-card-2': showCard
  }
}
</script>
