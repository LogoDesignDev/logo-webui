<template>
  <div class="logoCardContainer">
    <div class="mainContainer">
      <img class="logoPic" :src="serverPrx + (data.url[0]==='/'?'':'/') + data.url">
      <div class="titleText">{{ data.name || '精美作品' }}</div>
      <div class="tips">
        <span class="label">作品</span>
        <span><iconfont name="icon-like-2" />&nbsp;{{ transition(data.like) }}</span>
        <span><iconfont name="icon-collect-2" />&nbsp;{{ transition(data.collect) }}</span>
      </div>
      <div class="author" @click="toPersonal" style="cursor: pointer;">
        <img class="userPic-minimini" :src="serverPrx + data.authorUrl" />
        <div class="tips">{{ data.authorName }}</div>
      </div>
    </div>
  </div>
</template>

<style scoped>
.logoCardContainer {
  padding: 10px;
}

.mainContainer {
  width: 230px;
  height: 240px;
  border-radius: 10px;
  background: white;
  transition: 0.3s all;
}

.mainContainer:hover {
  transform: translateY(-5px);
  box-shadow: 5px 5px 30px rgb(225, 225, 225);
  cursor: pointer;
}

.logoPic {
  width: 200px;
  height: 150px;
  border-top-left-radius: 10px;
  border-top-right-radius: 10px;
  object-fit: cover;
}

.titleText {
  padding: 2px 10px 0px;
  color: #303133;
  font-size: 14px;
  font-weight: bolder;

  overflow: hidden;
  text-overflow:ellipsis;
  white-space: nowrap;
}

.tips {
  padding: 5px 10px;
}

.tips span {
  margin-right: 15px;
}

.author {
  padding-left: 10px;
  display: flex;
  align-items: center;
}

.author .tips {
  color: #303133;
}

.label {
  margin-right: 5px !important;
  padding: 0 8px !important;
  color: #409EFF;
  font-size: 12px;
  border-radius: 15px;
  border: 1px solid #409EFF;
}

</style>

<script>
import { serverPrx, transition } from 'utils/default'

export default {
  props: ['data'],

  data () {
    return {
      serverPrx
    }
  },

  methods: {
    transition,

    toPersonal () {
      this.$router.push({
        path: '/personal',
        query: {
          uid: this.data.authorId
        }
      })
    }
  }
}
</script>
