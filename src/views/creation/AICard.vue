<template>
  <div
    class="cardContainer"
    :isHover="isHover">
    <!-- 介绍 -->
    <div class="detail">
      <div class="detailTitle">
        只需一步即可智能生成
      </div>
      <el-input v-model="input" placeholder="请输入您的品牌关键字"></el-input>
      <!-- 按钮 -->
      <el-button type="primary" round @click="toGenerate">立即生成</el-button>
    </div>
    <!-- 常显示 -->
    <div class="mainCard">
      <iconfont class="icon" name="icon-ai" />
      <span class="cardTitle">智能生成</span>
      <span class="cardTips">输入关键字一键生成LOGO</span>
    </div>
  </div>
</template>

<style scoped>
.cardContainer {
  display: flex;
  cursor: pointer;
  align-items: center;
}

.mainCard {
  width: 300px;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  filter: grayscale(100%);
  transform: translateX(-200px);
  transition: all 0.3s;
}

.cardContainer[isHover='ai-card'] .mainCard {
  filter: grayscale(0);
  transform: translateX(-150px);
}

.detail {
  width: 400px;
  height: 200px;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: space-around;
  opacity: 0;
  transform: translateX(200px);
  transition: all 0.3s;
}

.cardContainer[isHover='ai-card'] .detail {
  transform: translateX(-200px);
  opacity: 1;
}

.icon {
  font-size: 250px;
}

.icon-2 {
  font-size: 50px;
}

.cardTitle {
  color: #606266;
  font-size: 20px;
  font-weight: bolder;
}

.cardTips {
  margin-top: 5px;
  color: #909399;
  font-size: 16px;
}

.detailTitle {
  font-size: 30px;
  font-weight: bolder;
  color: #303133;
}

.descCardsView {
  width: 400px;
  display: flex;
  flex-wrap: wrap;
  align-items: center;
  justify-content: center;
}

.descCard {
  width: 200px;
  height: 100px;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
}

.descText {
  font-size: 18px;
  color: #606266;
}

</style>

<script>
export default {
  props: ['isHover'],

  data () {
    return {
    }
  },

  methods: {
    /**
     * 跳转到生成页
     */
    toGenerate () {
      this.$router.push({
        path: '/creation/generate'
      })
    }
  }
}
</script>
