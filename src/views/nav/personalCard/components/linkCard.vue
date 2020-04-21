<template>
  <div class="container" @click="toPath">
    <i :class="icon"></i>
    <span>{{ text }}</span>
  </div>
</template>

<style lang="less" scoped>
.container {
  margin-left: -20px;
  width: 280px;
  height: 45px;
  display: flex;
  align-items: center;
  color: #606266;
}

.container:hover {
  background-color: rgb(245, 245, 245);
  cursor: pointer;
}

.container>i {
  font-size: 20px;
  margin-left: 30px;
}

.container>span {
  margin-left: 10px;
}
</style>

<script>
export default {
  props: ['icon', 'text', 'to'],

  methods: {
    toPath () {
      this.$router.push({
        path: this.to
      })
    }
  }
}
</script>
