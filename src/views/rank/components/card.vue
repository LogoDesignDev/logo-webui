<template>
  <div class="card">
    <div class="content">
      <div class="left">{{ this.$props.id }}</div>
      <div class="img">
        <el-image class="smallImg" :src="src" :fit="fit" @click="goDetail(logoid, src)"></el-image>
      </div>
      <div class="right">
        <div class="name">Air Jordan 1</div>
        <div class="author">{{ this.$props.author }}</div>
      </div>
      <div class="collect">
        <iconfont class="iconFont" name="icon-collect" />{{ like }}
      </div>
    </div>
    <el-divider></el-divider>
  </div>
</template>

<script>
export default {
  props: ['src', 'id', 'like', 'collect', 'author', 'logoid'],
  data () {
    return {
      fit: 'fill'
    }
  },
  methods: {
    goDetail (id, url) {
      this.$router.push({
        path: `/mylogo/logodetail/${id}`,
        query: {
          mode: url
        }
      })
    }
  }
}
</script>

<style>
  .content {
    display: flex;
  }
  .left {
    flex: 0.2;
    font-size: 60px;
    font-family: "Helvetica Neue",Helvetica,"PingFang SC","Hiragino Sans GB","Microsoft YaHei","微软雅黑",Arial,sans-serif;
    font-weight: 400;
    color: black;
    text-align: center;
    padding-top: 10px;
  }
  .img {
    flex: 0.2;
  }
  .smallImg {
    height: 100px;
    width: 100px;
    border-radius: 5px;
    cursor: pointer;
  }
  .right {
    flex: 0.4;
    margin-top: 15px;
  }
  .name {
    font-size: 40px;
    font-family: 'Times New Roman', Times, serif;
  }
  .author {
    font-size: 20px;
    font-family: 'Times New Roman', Times, serif;
  }
  .collect {
    flex: 0.2;
    font-size: 20px;
    color: grey;
    padding-top: 40px;
  }
</style>
