<template>
  <div>
    <div class="header">
      <img src="https://s1.ax1x.com/2020/06/07/t2WUmT.png" class="header-img">
      <el-button type="info" icon="el-icon-back" circle class="button-back" @click="goBack"></el-button>
    </div>
    <div class = "container">
      <el-image :src="src" :fit="fit" class="img"></el-image>
      <div class="rightcontent">
        <div class="content1">
          <el-button type="success" icon="el-icon-download" class="button-psd">下载PSD</el-button>
          <el-button type="primary" icon="el-icon-download" class="button-png">下载PNG</el-button>
        </div>
        <div class="content2">
          <el-tooltip class="item" effect="dark" content="收藏" placement="bottom">
            <el-button @click="collectChange" type="warning" :icon="icon" class="button-collect"> {{ this.collect }}</el-button>
          </el-tooltip>
          <el-tooltip class="item" effect="dark" content="点赞" placement="bottom">
            <el-button type="danger" icon="el-icon-message-solid" class="button-like"> {{ this.like }}</el-button>
          </el-tooltip>
        </div>
        <div class="content3">
          <el-card class="box-card">
            <div i class="el-icon-s-claim"> 作品名称：{{ this.name }}</div><br /><br />
            <div i class="el-icon-s-custom"> 作者：{{ this.author }}</div>
          </el-card>
          <el-button @click="editLogo" type="primary" icon="el-icon-edit" class="button-edit">在线编辑</el-button>
        </div>
      </div>
    </div>
    <el-divider class="divider"></el-divider><br>
    <recommend /><br>
  </div>
</template>

<script>
import recommend from './components/recommend'
import { getLogoDetail, collectPlus, collectLess } from 'api/mylogo'
import { getToken } from 'utils/auth'
export default {
  name: 'LogoDetail',
  components: {
    recommend: recommend
  },
  data () {
    return {
      collect: 23,
      like: 0,
      author: null,
      name: null,
      src: this.$route.query.param,
      fit: 'contain',
      galleryid: this.$route.params.galleryid,
      logoid: this.$route.params.logoid,
      icon: 'el-icon-star-off'
    }
  },
  methods: {
    goBack () {
      this.$router.go(-1)
    },
    singleLogoInfo () {
      console.log(this.galleryid)
      console.log(this.logoid)
      console.log(getToken())
      const postdata = {
        token: getToken(),
        logoid: this.logoid
      }
      getLogoDetail(postdata).then(this.handlesingleLogoInfoSucc)
    },
    handlesingleLogoInfoSucc (res) {
      res = res.data
      if (res.code === 200) {
        this.collect = res.collect
        console.log(this.collect)
        this.like = res.like
        this.name = res.name
        this.author = res.author
      } else {
        console.log(500)
      }
    },
    collectChange () {
      if (this.icon === 'el-icon-star-off') {
        this.icon = 'el-icon-star-on'
        const postdata = {
          token: getToken(),
          logoid: this.logoid,
          collect: this.collect
        }
        collectPlus(postdata).then((res) => {
          res = res.data
          if (res.code === 200) {
            this.$message({
              message: '收藏成功',
              type: 'success'
            })
          }
        })
        this.singleLogoInfo()
      } else {
        const postdata = {
          token: getToken(),
          logoid: this.logoid,
          collect: this.collect
        }
        collectLess(postdata).then((res) => {
          res = res.data
          if (res.code === 200) {
            this.$message({
              message: '取消收藏成功',
              type: 'warning'
            })
          }
        })
        this.singleLogoInfo()
      }
    },
    editLogo () {
      this.$message.warning('暂未开放，敬请期待')
    }
  },
  mounted () {
    this.singleLogoInfo()
  }
}
</script>

<style lang="less" scoped>
  .header {
    position: relative;
  }
  .header-img {
    width: 100%;
  }
  .button-edit {
    font-size: 20px;
    width: 300px;
    margin-left: 50px;
  }
  .button-collect {
    font-size: 20px;
    width: 200px;
  }
  .button-like {
    font-size: 20px;
    width: 200px;
  }
  .button-back {
    position: absolute;
    z-index: 2;
    left: 20px;
    top: 20px;
    font-size: 30px;
  }
  .button-psd {
    width: 200px;
    font-size: 20px;
    margin-top: 30px;
  }
  .button-png {
    width: 200px;
    font-size: 20px;
    margin-top: 30px;
  }
  .container {
    margin-top: 50px;
    margin-left: 200px;
    margin-right: 100px;
    margin-bottom: 200px;
    display: flex;
  }
  .img {
    flex: 1;
    width: 200px;
    height: 400px;
    margin-right: 10px;
    border-radius: 5px;
  }
  .rightcontent {
    display: flex;
    flex: 1;
    flex-direction: column;
    flex-wrap: wrap;
    align-items: center;
  }
  .content1 {
    flex: 1;
  }
  .content2 {
    flex: 1;
  }
  .content3 {
    flex: 1;
  }
  .el-icon-s-claim {
    font-size: 18px;
    color: grey;
  }
  .el-icon-s-custom {
    font-size: 18px;
    color: grey;
  }
  .box-card {
    width: 400px;
    margin-top: -30px;
    margin-bottom: 30px;
  }
  .divider {
    width: 1400px;
    margin-top: -100px;
    margin-left: 70px;
  }
</style>
