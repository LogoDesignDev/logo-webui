<template>
  <div id="picView"
    @mouseenter="emitEventEnter"
    @mouseleave="emitEventLeave"
    @click="toPersonal"
    :isHover="isHover">
    <iconfont id="champion" :name="championIcon" />
    <img id="userPic" class="userPic-middle" :src="serverPrx + src">
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

#picView:hover, #picView[isHover='hover'] {
  transform: scale(1.2);
}

#userPic {
  border: 2px solid rgb(245, 245, 245);
}

#picView:hover #userPic, #picView[isHover='hover'] #userPic {
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

#picView:hover #mask, #picView[isHover='hover'] #mask {
  visibility: hidden;
}
</style>

<script>
import { serverPrx } from 'utils/default'

export default {
  props: ['uid', 'src', 'rank', 'isHover'],

  data () {
    return {
      serverPrx
    }
  },

  computed: {
    championIcon () {
      return 'icon-champion-' + this.rank
    }
  },

  methods: {
    emitEventEnter () {
      this.$emit('enter', this.rank)
    },

    emitEventLeave () {
      this.$emit('leave', this.rank)
    },

    toPersonal () {
      this.$router.push({
        path: '/personal',
        query: {
          uid: this.uid
        }
      })
    }
  }
}
</script>
