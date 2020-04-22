<template>
  <div id="titleView">
    <div>
      <span class="textHover title-3" @click="toPath">{{ name }}</span>
      <span class="tips">{{ tips }}</span>
    </div>
    <span class="textHover tips" @click="toPath">查看更多</span>
  </div>
</template>

<style lang="less" scoped>
#titleView {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

#titleView>div>.tips {
  margin-left: 10px;
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
