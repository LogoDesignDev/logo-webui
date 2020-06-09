<template>
  <div class="container">
    <img src="https://i.loli.net/2020/06/08/WCFra8U9fjzHPnh.png" class="header-img">
    <div class="content">
      <div class="left">
        <div class="category">全部分类</div>
        <el-tag class="tag" @click="selectComplex" :effect="effect1">综合榜</el-tag>
        <el-tag class="tag" @click="selectLike" :effect="effect2">点赞榜</el-tag><br/>
        <el-tag class="tag" @click="selectCollect" :effect="effect3">收藏榜</el-tag>
        <el-tag class="tag" @click="selectDesign" :effect="effect4">设计师榜</el-tag>
      </div>
      <div class="right">
        <card  v-for="(item, index) in rankList" :key="index"
        :src="item.imgUrl" :id="item.id" :like="item.like" :collect="item.collect"></card>
        <div></div>
      </div>
    </div>
  </div>
</template>

<script>
import card from './components/card'
import axios from 'axios'
export default {
  name: 'rank',
  components: {
    card
  },
  data () {
    return {
      effect1: 'plain',
      effect2: 'plain',
      effect3: 'plain',
      effect4: 'plain',
      rankList: [{
        id: 1,
        imgUrl: 'https://ss0.baidu.com/6ONWsjip0QIZ8tyhnq/it/u=488030022,1694816207&fm=173&app=25&f=JPEG?w=580&h=347&s=A08FB35A5E0616C664F5631C030010D6',
        like: 123,
        collect: 235
      }, {
        id: 2,
        imgUrl: 'https://ss0.baidu.com/6ONWsjip0QIZ8tyhnq/it/u=488030022,1694816207&fm=173&app=25&f=JPEG?w=580&h=347&s=A08FB35A5E0616C664F5631C030010D6',
        like: 123,
        collect: 235
      }, {
        id: 3,
        imgUrl: 'https://ss0.baidu.com/6ONWsjip0QIZ8tyhnq/it/u=488030022,1694816207&fm=173&app=25&f=JPEG?w=580&h=347&s=A08FB35A5E0616C664F5631C030010D6',
        like: 123,
        collect: 235
      }, {
        id: 4,
        imgUrl: 'https://ss0.baidu.com/6ONWsjip0QIZ8tyhnq/it/u=488030022,1694816207&fm=173&app=25&f=JPEG?w=580&h=347&s=A08FB35A5E0616C664F5631C030010D6',
        like: 123,
        collect: 235
      }, {
        id: 4,
        imgUrl: 'https://ss0.baidu.com/6ONWsjip0QIZ8tyhnq/it/u=488030022,1694816207&fm=173&app=25&f=JPEG?w=580&h=347&s=A08FB35A5E0616C664F5631C030010D6',
        like: 123,
        collect: 235
      }, {
        id: 4,
        imgUrl: 'https://ss0.baidu.com/6ONWsjip0QIZ8tyhnq/it/u=488030022,1694816207&fm=173&app=25&f=JPEG?w=580&h=347&s=A08FB35A5E0616C664F5631C030010D6',
        like: 123,
        collect: 235
      }, {
        id: 4,
        imgUrl: 'https://ss0.baidu.com/6ONWsjip0QIZ8tyhnq/it/u=488030022,1694816207&fm=173&app=25&f=JPEG?w=580&h=347&s=A08FB35A5E0616C664F5631C030010D6',
        like: 123,
        collect: 235
      }, {
        id: 4,
        imgUrl: 'https://ss0.baidu.com/6ONWsjip0QIZ8tyhnq/it/u=488030022,1694816207&fm=173&app=25&f=JPEG?w=580&h=347&s=A08FB35A5E0616C664F5631C030010D6',
        like: 123,
        collect: 235
      }, {
        id: 4,
        imgUrl: 'https://ss0.baidu.com/6ONWsjip0QIZ8tyhnq/it/u=488030022,1694816207&fm=173&app=25&f=JPEG?w=580&h=347&s=A08FB35A5E0616C664F5631C030010D6',
        like: 123,
        collect: 235
      }, {
        id: 4,
        imgUrl: 'https://ss0.baidu.com/6ONWsjip0QIZ8tyhnq/it/u=488030022,1694816207&fm=173&app=25&f=JPEG?w=580&h=347&s=A08FB35A5E0616C664F5631C030010D6',
        like: 123,
        collect: 235
      }, {
        id: 4,
        imgUrl: 'https://ss0.baidu.com/6ONWsjip0QIZ8tyhnq/it/u=488030022,1694816207&fm=173&app=25&f=JPEG?w=580&h=347&s=A08FB35A5E0616C664F5631C030010D6',
        like: 123,
        collect: 235
      }]
    }
  },
  methods: {
    // 很傻的做法
    selectComplex () {
      if (this.effect1 === 'plain') {
        this.effect1 = 'dark'
        this.effect2 = 'plain'
        this.effect3 = 'plain'
        this.effect4 = 'plain'
        axios.get('/api/logo.json').then(this.selectComplexSucc)
      }
    },
    selectComplexSucc (res) {
      res = res.data
      if (res.ret && res.data) {
        this.rankList = res.data.items
      }
    },
    selectLike () {
      if (this.effect2 === 'plain') {
        this.effect1 = 'plain'
        this.effect2 = 'dark'
        this.effect3 = 'plain'
        this.effect4 = 'plain'
        axios.get('/api/logo.json').then(this.selectLikeSucc)
      }
    },
    selectLikeSucc (res) {
      res = res.data
      if (res.ret && res.data) {
        this.rankList = res.data.items
      }
    },
    selectCollect () {
      if (this.effect3 === 'plain') {
        this.effect1 = 'plain'
        this.effect2 = 'plain'
        this.effect3 = 'dark'
        this.effect4 = 'plain'
        axios.get('/api/logo.json').then(this.selectCollectSucc)
      }
    },
    selectCollectSucc (res) {
      res = res.data
      if (res.ret && res.data) {
        this.rankList = res.data.items
      }
    },
    selectDesign () {
      if (this.effect4 === 'plain') {
        this.effect1 = 'plain'
        this.effect2 = 'plain'
        this.effect3 = 'plain'
        this.effect4 = 'dark'
        axios.get('/api/logo.json').then(this.selectDesignSucc)
      }
    },
    selectDesignSucc (res) {
      res = res.data
      if (res.ret && res.data) {
        this.rankList = res.data.items
      }
    }
  },
  mounted () {
    this.selectComplex()
  }
}
</script>

<style lang="less" scoped>
  .container {
    padding-bottom: 50px;
    display: flex;
    flex-direction: column;
  }
  .header-img {
    width: 100%;
  }
  .content {
    display: flex;
    flex: 1;
    margin: 30px 180px 100px 180px;
    height: 500px;
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
