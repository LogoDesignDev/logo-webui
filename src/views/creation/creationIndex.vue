<template>
  <div class="container">
    <!-- 顶部提示条 -->
    <div class="tipsContainer">
      <iconfont
        name="icon-jiankangtijianxunwenbiao"
        style="font-size: 30px; margin-right: 10px;"/>
      <span>想了解更多创作logo的技巧吗？点这里→</span>
      <span class="textBtn" @click="toIntro">
        了解详情
      </span>
    </div>
    <!-- 功能选择区 -->
    <div class="topContainer">
      <div>
        <div @mouseenter="isHover='handwork-card'" >
          <handwork-card :isHover="isHover" />
        </div>
        <div @mouseenter="isHover='ai-card'" >
          <ai-card :isHover="isHover" />
        </div>
      </div>
    </div>
    <!-- 介绍区域 -->
    <div id="introCard" class="bottomContainer">
      <introduce/>
    </div>
  </div>
</template>

<style lang="less" scoped>
.textBtn {
  color: #409EFF;
  cursor: pointer;
}

.tipsContainer {
  width: 100%;
  height: 60px;
  background: rgb(217, 236, 255);
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 14px;
  color: #909399;
}

.topContainer {
  width: 100%;
  height: 500px;
  background: rgb(250, 250, 250);
  display: flex;
  align-items: center;
  justify-content: center;
}

.topContainer>div {
  width: 1000px;
  display: flex;
  align-items: center;
  justify-content: space-around;
}

.bottomContainer {
  display: flex;
  align-items: center;
  justify-content: center;
}

</style>

<script>
import handworkCard from './handworkCard'
import AICard from './AICard'
import introduce from './introduce'

export default {
  components: {
    'handwork-card': handworkCard,
    'ai-card': AICard,
    introduce
  },

  data () {
    return {
      isHover: 'ai-card'
    }
  },

  methods: {
    /**
     * 滚动到介绍部分
     */
    toIntro () {
      document.getElementById('introCard').scrollIntoView()
    }
  }
}
</script>
