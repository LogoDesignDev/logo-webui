<template>
  <div class="container" @click="toPath">
    <span class="tips">{{ text }}</span>
    <span id="number">{{ number }}</span>
  </div>
</template>

<style lang="less" scoped>
.container {
  text-align: center;
  display: flex;
  flex-direction: column;
}

.container:hover {
  color: #409EFF;
  cursor: pointer;
}

#number {
  color: #303133;
  font-size: 18px;
  font-weight: bold;
}
</style>

<script>
export default {
  props: ['text', 'number', 'to'],

  methods: {
    toPath () {
      this.$router.push({
        path: this.to
      })
    }
  }
}
</script>
