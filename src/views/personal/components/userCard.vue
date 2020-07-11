<template>
  <div class="userCardContainer">
    <!-- 左边用户信息区 -->
    <div class="leftContainer" @click="toPersonal">
      <div>
        <img class="userPic-middle" :src="serverPrx + data.userPicUrl" />
        <div class="title-2">{{ data.username }}</div>
      </div>
    </div>
  </div>
</template>

<style scoped>
.userCardContainer {
  /* background: tomato; */
  padding: 0 20px;
  width: 100%;
  height: 100px;
  display: flex;
  align-items: center;
  justify-content: space-between;
  cursor: pointer;
}

.leftContainer {
  height: 100%;
  display: flex;
  flex-direction: column;
  justify-content: center;
}

.leftContainer>div {
  margin: 15px 0;
  display: flex;
  align-items: center;
}

.title-2 {
  margin-left: 10px;
}

.rightContainer {
  display: flex;
  align-items: center;
  justify-content: right;
}

</style>

<script>
import { serverPrx } from 'utils/default'

export default {
  props: ['data'],

  data () {
    return {
      serverPrx
    }
  },

  methods: {
    toPersonal () {
      this.$router.push({
        path: '/personal',
        query: {
          uid: this.data.uid
        }
      })
    }
  }
}
</script>
