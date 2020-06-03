<template>
  <div
    class="cardContainer"
    :isHover="isHover">
    <!-- 常显示 -->
    <div class="mainCard">
      <iconfont class="icon" name="icon-handwork" />
      <span class="cardTitle">自己制作</span>
      <span class="cardTips">通过在线编辑器制作LOGO</span>
    </div>
    <!-- 介绍 -->
    <div class="detail">
      <div class="detailTitle">
        强大的在线编辑器
      </div>
      <div class="descCardsView">
        <div class="descCard">
          <iconfont class="icon-2" name="icon-pic" />
          <div class="descText">自定义图片上传</div>
        </div>
        <div class="descCard">
          <iconfont class="icon-2" name="icon-move" />
          <div class="descText">元素自由拖拽</div>
        </div>
        <div class="descCard">
          <iconfont class="icon-2" name="icon-edit" />
          <div class="descText">支持二次创作</div>
        </div>
        <div class="descCard">
          <iconfont class="icon-2" name="icon-text" />
          <div class="descText">提供多种常用字体</div>
        </div>
      </div>
      <!-- 按钮 -->
      <el-button type="success" round @click="toHandwork">立即前往制作</el-button>
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
  transform: translateX(200px);
  transition: all 0.3s;
}

.cardContainer[isHover='handwork-card'] .mainCard {
  filter: grayscale(0);
  transform: translateX(150px);
}

.detail {
  width: 400px;
  height: 400px;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: space-around;
  opacity: 0;
  transform: translateX(-200px);
  transition: all 0.3s;
}

.cardContainer[isHover='handwork-card'] .detail {
  transform: translateX(200px);
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
     * 跳转到制作页
     */
    toHandwork () {
      this.$message.warning('暂未开放，敬请期待')
    }
  }
}
</script>
