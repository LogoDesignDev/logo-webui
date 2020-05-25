<template>
  <div>
    <div class="header">
      <img src="../../assets/home_bg.png" class="header-img">
      <el-button type="info" icon="el-icon-back" circle class="button-back" @click="goBack"></el-button>
    </div>
    <div class = "container">
      <img src="https://ss0.baidu.com/6ONWsjip0QIZ8tyhnq/it/u=488030022,1694816207&fm=173&app=25&f=JPEG?w=580&h=347&s=A08FB35A5E0616C664F5631C030010D6" class="img">
      <div class="rightcontent">
        <div class="content1">
          <el-button type="success" icon="el-icon-download" class="button-psd">下载PSD</el-button>
          <el-button type="primary" icon="el-icon-download" class="button-png">下载PNG</el-button>
        </div>
        <div class="content2">
          <el-button type="warning" icon="el-icon-star-on" class="button-collect"> 1.1k</el-button>
          <el-button type="danger" icon="el-icon-message-solid" class="button-like"> 1.1k</el-button>
        </div>
        <div class="content3">
          <el-card class="box-card">
            <div i class="el-icon-s-claim"> 作品名称：篮球鞋</div><br /><br />
            <div i class="el-icon-s-custom"> 作者：篮球鞋</div>
          </el-card>
          <el-button type="primary" icon="el-icon-edit" class="button-edit">在线编辑</el-button>
        </div>
      </div>
    </div>
    <el-divider class="divider"></el-divider><br>
    <recommend /><br>
  </div>
</template>

<script>
import recommend from './components/recommend'
export default {
  name: 'LogoDetail',
  components: {
    recommend: recommend
  },
  data () {
    return {
      List: []
    }
  },
  methods: {
    goBack () {
      this.$router.go(-1)
    }
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
