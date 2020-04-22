<template>
  <div class="card" @click="toPath">
    <img :src="src">
    <div>
      <div class="title">{{ name }}</div>
      <div class="line" />
      <div class="tips">{{ tips }}</div>
    </div>
  </div>
</template>

<style lang="less" scoped>
.icon {
  color: rgb(245, 245, 245);
  font-size: 200px;
  position: relative;
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
  width: 320px;
  height: 200px;
  border-radius: 5px;
  background: linear-gradient(rgb(217, 236, 255), rgb(236, 245, 255));
  display: flex;
  transition: all 0.5s;
}

.card:hover {
  transform: scale(1.05);
  cursor: pointer;
}

img {
  margin: 10px 0 0 10px;
  width: 150px;
  height: 180px;
  border-radius: 5px;
}

.card>div {
  width: 100%;
  height: 100%;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
}
</style>

<script>
export default {
  props: ['src', 'name', 'tips', 'to'],

  methods: {
    toPath () {
      this.$router.push({
        path: this.to
      })
    }
  }
}
</script>
