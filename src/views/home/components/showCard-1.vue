<template>
  <div class="card" @click="toPath">
    <img class="viewImg" :src="src">
    <div>
      <iconfont class="iconFont" :name="icon" />
      <div class="title">{{ name }}</div>
      <div class="line" />
      <div class="tips">{{ tips }}</div>
    </div>
  </div>
</template>

<style lang="less" scoped>
.iconFont {
  font-size: 50px;
}

.title {
  color: #606266;
  font-size: 18px;
}

.tips {
  color: #909399;
  font-size: 14px;
}

.line {
  margin: 5px 0;
  width: 70%;
  height: 2px;
  border-radius: 1px;
  background: #409EFF;
}

.card {
  margin: 10px 0;
  width: 320px;
  height: 200px;
  border-radius: 5px;
  background: linear-gradient(rgb(217, 236, 255), rgb(236, 245, 255));
  display: flex;
  transition: all 0.3s;
}

.card:hover {
  transform: scale(1.05);
  cursor: pointer;
}

.viewImg {
  margin: 10px 0 0 10px;
  width: 150px;
  height: 180px;
  border-radius: 5px;
  border: 1px solid #DCDFE6;
  object-fit: cover;
}

.card>div {
  width: 150px;
  height: 100%;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
}
</style>

<script>
export default {
  props: ['src', 'icon', 'name', 'tips', 'to'],

  methods: {
    toPath () {
      this.$router.push({
        path: this.to
      })
    }
  }
}
</script>
