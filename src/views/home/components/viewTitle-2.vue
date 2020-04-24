<template>
  <div id="titleView">
    <el-divider>
      <div>
        <div class="textHover title-3" @click="toPath">{{ name }}</div>
        <div class="tips">{{ tips }}</div>
      </div>
    </el-divider>
  </div>
</template>

<style lang="less" scoped>
#titleView {
  display: flex;
  justify-content: center;
  align-items: center;
  text-align: center;
}

.textHover:hover {
  color: #409EFF;
  cursor: pointer;
}
</style>

<script>
export default {
  props: ['name', 'tips', 'to'],

  methods: {
    toPath () {
      this.$router.push({
        path: this.to
      })
    }
  }
}
</script>
