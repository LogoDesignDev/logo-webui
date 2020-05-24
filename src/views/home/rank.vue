<template>
  <div>
    <view-title name="排行榜" tips="每天0:00更新" to="/a" />
    <div id="main">
      <show-card-1
        :src="urlArr[0]"
        icon="icon-rank" name="综合榜" tips="综合各项人气指标" to="/a" />
      <show-card-1
        :src="urlArr[1]"
        icon="icon-like" name="点赞榜" tips="根据点赞数排行" to="/a" />
      <show-card-1
        :src="urlArr[2]"
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
import { getFirstUrl } from 'api/home'

export default {
  components: {
    'view-title': viewTitle,
    'show-card-1': showCard
  },

  data () {
    return {
      urlArr: ['', '', '']
    }
  },

  created () {
    this.updateRankPic()
  },

  methods: {
    /**
     * 刷新首页排行榜三张照片
     */
    updateRankPic () {
      getFirstUrl().then((res) => {
        // ———— 成功回调 ————
        const data = res.data
        switch (data.code) {
          case 20000: // 获取成功
            this.urlArr = data.logoUrl
            break
        }
      }).catch((err) => {
        // ———— 错误处理 ————
      }).finally(() => {
        // ———— 最终执行 ————
      })
    }
  }
}
</script>
