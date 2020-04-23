<template>
  <div>
    <view-title name="推荐作品" tips="" to="/a" />
    <div id="main">

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
  justify-content: space-between;
}
</style>

<script>
import viewTitle from './components/viewTitle'

export default {
  components: {
    'view-title': viewTitle
  }
}
</script>
