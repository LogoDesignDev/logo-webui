<!--
  type - logo的类型(success/warning)
  title - 卡片的标题
  content - 卡片中间的内容
  buttonText - 按钮的文字
  buttonDisable - 按钮是否可用
  clickButton - 点击按钮时触发的事件
-->
<template>
  <div class="card">
    <div>
      <i :class="iconClass" :style="{color: iconColor}"/>
      <span class="title-2">{{title}}</span>
    </div>
    <span class="tips">{{content}}</span>
    <el-button type="primary" size="small" :disabled="buttonDisable" round @click="emitSignal">
      {{buttonText}}
    </el-button>
  </div>
</template>

<style lang="less" scoped>
.card {
  height: 40px;
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.card i {
  width: 30px;
  text-align: center;
}
</style>

<script>
export default {
  props: ['type', 'title', 'content', 'buttonText', 'buttonDisable'],
  computed: {
    /**
     * 确定icon
     */
    iconClass () {
      return 'el-icon-' + this.type
    },

    /**
     * 确定icon颜色
     */
    iconColor () {
      return this.type === 'success' ? '#67C23A' : '#E6A23C'
    }
  },

  methods: {
    emitSignal () {
      this.$emit('clickButton')
    }
  }
}
</script>
