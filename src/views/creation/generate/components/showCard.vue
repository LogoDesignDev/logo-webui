<template>
  <div class="card">
    <img id="viewImg" :src="src" />
    <!-- 底部缩略显示 -->
    <div id="bottomView">
      <span id="title">点击预览</span>
      <span class="el-icon-caret-right" />
    </div>
    <!-- 水印 -->
    <div class="watermark">
      <span class="watermarkText" v-for="i in 20" :key="i">logo生成</span>
    </div>
  </div>
</template>

<style lang="less" scoped>
.card {
  margin: 10px 0;
  width: 320px;
  height: 250px;
  background: grey;
  border-radius: 5px;
  border: 1px solid #DCDFE6;
  cursor: pointer;
  position: relative;
}

#viewImg {
  width: 100%;
  height: 100%;
  border-radius: 5px;
  object-fit: cover;
  background: white;
}

#bottomView {
  padding: 0 15px;
  bottom: 0;
  width: 290px;
  height: 40px;
  border-bottom-left-radius: 5px;
  border-bottom-right-radius: 5px;
  background: linear-gradient(rgba(0, 0, 0, 0), rgba(0, 0, 0, 0.5));
  position: absolute;
  display: flex;
  align-items: center;
  justify-content: space-between;
  /* 字体部分 */
  color: #ffffff;
  font-size: 14px;
  letter-spacing: 2px;
  transition: all 0.3s;
}

#title {
  width: 150px;
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
}

.watermark {
  top: 0;
  width: 320px;
  height: 250px;
  border-radius: 5px;
  display: flex;
  flex-wrap: wrap;
  align-items: center;
  justify-content: space-around;
  position: absolute;
}

.watermarkText {
  transform: rotate(330deg);
  font-size: 14px;
  font-weight: bold;
  color: #C0C4CC;
  opacity: 0.2;
}

</style>

<script>
export default {
  props: ['src']
}
</script>
