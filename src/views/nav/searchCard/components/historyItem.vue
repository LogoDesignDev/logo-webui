<template>
  <transition name="el-fade-in-linear">
    <div class="item" @click="emitItemSignal">
      <span class="tips">{{ text }}</span>
      <button class="del-btn el-icon-error" @click="emitButtonSignal" />
    </div>
  </transition>
</template>

<style scoped>
.item {
  margin: 5px 5px;
  padding: 0px 10px;
  height: 30px;
  width: fit-content;
  border-radius: 15px;
  background: rgb(245, 245, 245);
  display: flex;
  align-items: center;
  justify-content: center;
  transition: all 0.3s;
  cursor: pointer;
}

.tips {
  max-width: 100px;
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
}

.item:hover {
  background: rgb(240, 240, 240);
}

.del-btn {
  margin-left: 5px;
  padding: 0;
  width: 20px;
  height: 20px;
  border: none;
  background: none;
  font-size: 20px;
  color: rgb(225, 225, 225);
  transition: all 0.3s;
  cursor: pointer;
}

.del-btn:hover {
  color: #F56C6C;
}

</style>

<script>
export default {
  props: ['text', 'index'],

  methods: {
    emitButtonSignal () {
      this.$emit('buttonClicked', this.index)
    },

    emitItemSignal () {
      this.$emit('itemClicked', this.text, this.index)
    }
  }
}
</script>
