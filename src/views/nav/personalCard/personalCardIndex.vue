<template>
  <div
    @mouseenter="showCard"
    @mouseleave="hideCard">
    <img
      class="userPic-mini"
      :src="userInfo.userPicUrl"
      @click="toPersonal" />
    <transition name="el-zoom-in-top">
      <more-card
        v-show="cardVisible"
        class="card" />
    </transition>
  </div>
</template>

<style lang="less" scoped>
  img {
    cursor: pointer;
  }

  .card {
    margin-left: -120px;
    position: absolute;
  }
</style>

<script>
import store from 'store'
import more from './more'

export default {
  components: {
    'more-card': more
  },

  data () {
    return {
      timer: null, // 用于悬浮显示card
      cardVisible: false
    }
  },

  computed: {
    userInfo: function () {
      return store.state.userInfo
    }
  },

  methods: {
    /**
     * 转到个人主页
     */
    toPersonal () {
      this.$router.push({
        path: '/personal',
        query: {
          uid: store.state.userInfo.uid
        }
      })
    },

    /**
     * 显示“更多”选项卡
     */
    showCard () {
      this.cardVisible = true
      if (this.timer) {
        clearTimeout(this.timer)
        this.timer = null
      }
    },

    /**
     * 隐藏“更多”选项卡
     * 但要做一个延时
     */
    hideCard () {
      const _this = this

      this.timer = setTimeout(function () {
        _this.cardVisible = false
      }, 100)
    }
  }
}
</script>
