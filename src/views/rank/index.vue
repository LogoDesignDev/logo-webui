<template>
  <div class="container">
    <img src="@/assets/rank_bg.png" class="header-img">
    <div class="content">
      <div class="left">
        <div class="category">全部分类</div>
        <el-tag class="tag" @click="select" :effect="effect">综合榜</el-tag>
        <el-tag class="tag" @click="select" :effect="effect">点赞榜</el-tag><br/>
        <el-tag class="tag" @click="select" :effect="effect">收藏榜</el-tag>
        <el-tag class="tag" @click="select" :effect="effect">设计师榜</el-tag>
      </div>
      <div class="right">
        <card :src="imgUrl"></card>
      </div>
    </div>
  </div>
</template>

<script>
import card from './components/card'
export default {
  name: 'rank',
  components: {
    card
  },
  data () {
    return {
      effect: 'plain',
      imgUrl: 'https://ss0.baidu.com/6ONWsjip0QIZ8tyhnq/it/u=488030022,1694816207&fm=173&app=25&f=JPEG?w=580&h=347&s=A08FB35A5E0616C664F5631C030010D6'
    }
  },
  methods: {
    select () {
      if (this.effect === 'plain') {
        this.effect = 'dark'
      } else {
        this.effect = 'plain'
      }
    }
  }
}
</script>

<style lang="less" scoped>
  .header-img {
    width: 100%;
  }
  .content {
    display: flex;
    margin: 30px 180px 30px 180px;
    height: 500px;
    flex-grow: 1;
  }
  .left {
    flex: 0.3;
  }
  .right {
    flex: 0.7;
  }
  .tag {
    cursor: pointer;
    width: 100px;
    margin: 10px 10px 10px 10px;
    font-size: 15px;
    text-align: center;
  }
  .category {
    font-size: 25px;
    margin-bottom: 20px;
    font-family: "Helvetica Neue",Helvetica,"PingFang SC","Hiragino Sans GB","Microsoft YaHei","微软雅黑",Arial,sans-serif;
    color: grey;
  }
</style>
