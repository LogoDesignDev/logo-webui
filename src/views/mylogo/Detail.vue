<template>
  <div>
    <div class="header">
      <img src="../../assets/home_bg.png" class="header-img">
      <el-button type="primary" icon="el-icon-plus" circle class="button-plus"></el-button>
      <el-button type="warning" icon="el-icon-star-off" circle class="button-collect"></el-button>
      <el-button type="danger" icon="el-icon-delete" circle class="button-d"></el-button>
      <el-button type="info" icon="el-icon-back" circle class="button-back" @click="goBack"></el-button>
    </div>
    <div class = "container">
      <el-row>
        <el-col :span="4" v-for="(item, index) in imgList" :key="index" class="col">
            <div @click="goDetail(item.id)" :imgUrl="item.imgUrl">
              <el-card :body-style="{ padding: '0px'}" class="card">
                <img :src="item.imgUrl" class="image">
                <div id="hoverView">
                  <div id="infoView">
                    <div id="hoverTitle">{{item.id}}</div>
                    <div id="line" />
                    <div id="hoverTips">详情点击</div>
                  </div>
                </div>
              </el-card>
            </div>
        </el-col>
      </el-row>
    </div>
  </div>
</template>

<script>
import axios from 'axios'
export default {
  name: 'app',
  data () {
    return {
      imgList: []
    }
  },
  methods: {
    goBack () {
      this.$router.go(-1)
    },
    getDetailInfo () {
      axios.get('/api/logo.json', {
        params: {
          galleryid: this.$route.params.id
        }
      }).then(this.handleGetDetailSucc)
      console.log(this.$route.params.id)
    },
    handleGetDetailSucc (res) {
      res = res.data
      if (res.ret && res.data) {
        const data = res.data
        this.imgList = data.items
      }
    },
    goDetail (id) {
      this.$router.push({
        path: `/mylogo/detail/galleryid=${this.$route.params.id}/logoid=${id}`
      })
    }
  },
  mounted () {
    this.getDetailInfo()
  }
}
</script>

<style lang="less" scoped>
  #hoverView {
    bottom: 0;
    width: 100%;
    height: 0;
    border-radius: 5px;
    position: absolute;
    display: flex;
    justify-content: center;
    align-items: center;
    transition: all 0.3s;
  }

  .card:hover #hoverView {
    bottom: 0;
    height: 100%;
    background: rgba(0, 0, 0, 0.5);
  }

  #infoView {
    padding: 0 10px;
    color: transparent;
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
    border: 1px solid transparent;
    transition: all 0.3s;
  }

  .card:hover #infoView {
    color: white;
    width: 150px;
    height: 100px;
    border: 1px solid white;
  }

  #title {
    width: 150px;
    overflow: hidden;
    text-overflow: ellipsis;
    white-space: nowrap;
  }

  #hoverTitle {
    width: 110px;
    text-align: center;
    overflow: hidden;
    text-overflow: ellipsis;
    white-space: nowrap;
    font-size: 18px;
  }

  #hoverTips {
    width: 130px;
    text-align: center;
    overflow: hidden;
    text-overflow: ellipsis;
    white-space: nowrap;
    font-size: 14px;
  }

  #line {
    margin: 10px 0;
    width: 150px;
    height: 1px;
    transform: scaleY(0.5);
    background: white;
  }
  .header {
    position: relative;
  }

  .header-img {
    width: 100%;
  }
  .button-plus {
    position: absolute;
    z-index: 2;
    right: 400px;
    font-size: 30px;
    bottom: -20px;
  }
  .button-collect {
    position: absolute;
    z-index: 2;
    right: 300px;
    font-size: 30px;
    bottom: -20px;
  }
  .button-d {
    position: absolute;
    z-index: 2;
    right: 200px;
    bottom: -20px;
    font-size: 30px;
  }
  .button-back {
    position: absolute;
    z-index: 2;
    left: 20px;
    top: 20px;
    font-size: 30px;
  }

  .container {
    margin-top: 50px;
    margin-left: 200px;
    margin-right: 100px;
  }

  .container-header {
    margin-bottom: 10px;
    overflow: hidden;
  }

  .card {
    position: relative;
    height: 350px;
    margin-bottom: 20px;
    cursor: pointer;
  }

  .col {
    margin-left: 30px;
    margin-top: 0px;
    width: 250px;
  }

  .image {
    width: 100%;
    display: block;
  }

  .clearfix:before,
  .clearfix:after {
      display: table;
      content: "";
  }

  .clearfix:after {
      clear: both
  }
</style>
