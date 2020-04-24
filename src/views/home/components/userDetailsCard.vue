<template>
  <div id="detailsView">
    <div class="title-3">{{ username }}</div>
    <div id="rankTitle">全站排行第{{ rank }}</div>
    <div id="detailsNumView">
      <div>
        <div class="detailsTitle">获赞</div>
        <div class="detailsNumber">{{ transition(like) }}</div>
      </div>
      <div>
        <div class="detailsTitle">收藏</div>
        <div class="detailsNumber">{{ transition(collect) }}</div>
      </div>
      <div>
        <div class="detailsTitle">作品</div>
        <div class="detailsNumber">{{ transition(prod) }}</div>
      </div>
    </div>
  </div>
</template>

<style lang="less" scoped>
#detailsView {
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
}

#rankTitle {
  margin: 10px 0;
  padding: 2px 10px;
  border-radius: 5px;
  border: 1px solid #409EFF;
  color: #409EFF;
  font-size: 12px;
  font-weight: bold;
}

#detailsNumView{
  width: 300px;
  display: flex;
  justify-content: space-between;
  align-items: center;
  text-align: center;
}

.detailsTitle {
  color: #909399;
  font-size: 18px;
  letter-spacing: 4px;
}

.detailsNumber {
  color: #909399;
  font-size: 16px;
}
</style>

<script>
export default {
  props: ['username', 'rank', 'like', 'collect', 'prod'],

  methods: {
    /**
     * 把十进制数转换成xxK的表达形式
     * 不足1000的不处理
     */
    transition (num) {
      return num < 1000 ? num : (num / 1000).toFixed(1) + 'K'
    }
  }
}
</script>
