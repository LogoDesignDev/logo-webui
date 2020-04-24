<template>
  <div id="picView" @mouseenter="emitEventEnter">
    <iconfont id="champion" :name="championIcon" />
    <img id="userPic" class="userPic-middle" :src="src">
    <div id=mask />
  </div>
</template>

<style lang="less" scoped>
#picView {
  display: flex;
  flex-direction: column;
  align-items: center;
  position: relative;
  cursor: pointer;
  transition: all 0.3s;
}

#picView:hover {
  transform: scale(1.2);
}

#userPic {
  border: 2px solid rgb(245, 245, 245);
}

#picView:hover #userPic {
  border: 2px solid #909399;
}

#champion {
  top: -18px;
  left: 35px;
  transform:rotate(25deg);
  font-size: 30px;
  position: absolute;
}

#mask {
  width: 62px;
  height: 62px;
  border-radius: 32px;
  background: rgba(255, 255, 255, 0.5);
  position: absolute;
}

#picView:hover #mask {
  visibility: hidden;
}
</style>

<script>
export default {
  props: ['src', 'rank'],

  computed: {
    championIcon () {
      return 'icon-champion-' + this.rank
    }
  },

  methods: {
    emitEventEnter () {
      this.$emit('enter', this.rank)
    }
  }
}
</script>
