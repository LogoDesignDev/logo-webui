<template>
  <div class="card" @click="toPath">
    <img id="viewImg" :src="src" />
    <!-- 底部缩略显示 -->
    <div id="bottomView">
      <span id="title">{{ title }}</span>
      <span class="el-icon-caret-top" />
    </div>
    <!-- hover详细显示 -->
    <div id="hoverView">
      <div id="infoView">
        <div id="hoverTitle">{{ title }}</div>
        <div id="line" />
        <div id="hoverTips">{{ tips }}</div>
      </div>
    </div>
  </div>
</template>

<style lang="less" scoped>
.card {
  margin: 10px 0;
  width: 235px;
  height: 320px;
  background: grey;
  border-radius: 5px;
  cursor: pointer;
  position: relative;
}

#viewImg {
  width: 100%;
  height: 100%;
  border-radius: 5px;
  object-fit: cover;
}

#bottomView {
  padding: 0 15px;
  bottom: 0;
  width: 205px;
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

.card:hover #bottomView {
  background: transparent;
  color: transparent;
}

#hoverView {
  bottom: 0;
  width: 235px;
  height: 0;
  border-radius: 5px;
  position: absolute;
  display: flex;
  justify-content: center;
  align-items: center;
  transition: all 0.3s;
}

.card:hover #hoverView {
  bottom: 0;
  height: 320px;
  background: rgba(0, 0, 0, 0.5);
}

#infoView {
  padding: 0 10px;
  color: transparent;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  border: 1px solid transparent;
  transition: all 0.3s;
}

.card:hover #infoView {
  color: white;
  width: 150px;
  height: 100px;
  border: 1px solid white;
}

#title {
  width: 150px;
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
}

#hoverTitle {
  width: 110px;
  text-align: center;
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
  font-size: 18px;
}

#hoverTips {
  width: 130px;
  text-align: center;
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
  font-size: 14px;
}

#line {
  margin: 10px 0;
  width: 150px;
  height: 1px;
  transform: scaleY(0.5);
  background: white;
}
</style>

<script>
export default {
  props: ['src', 'title', 'tips', 'to'],

  methods: {
    toPath () {
      this.$router.push({
        path: this.to
      })
    }
  }
}
</script>
