<template>
  <div>
    <div class="header">
      <img src="https://s1.ax1x.com/2020/06/07/t2WUmT.png" class="header-img">
      <el-button @click="addlogo" type="primary" icon="el-icon-plus" circle class="button-plus"></el-button>
      <Add-Logo :galleryid="galleryid" :AddLogoVisible="AddLogoVisible" v-if="AddLogoVisible" @Add-cancel="closeAdd" @Add-succ="addSucc"></Add-Logo>
      <el-button @click="onChange" type="warning" icon="el-icon-star-off" circle class="button-collect"></el-button>
      <el-button @click="onChange" type="danger" icon="el-icon-bell" circle class="button-d"></el-button>
      <el-button type="info" icon="el-icon-back" circle class="button-back" @click="goBack"></el-button>
    </div>
    <div v-if="isloggedIn">
      <div class = "container" v-loading="loading" :data="imgList">
        <el-row v-if="isData">
          <el-col :span="4" v-for="(item, index) in imgList" :key="index" class="col">
              <div>
                <el-card :body-style="{ padding: '0px'}" class="card">
                  <el-image :src="item.imgUrl" class="image"></el-image>
                  <div id="hoverView">
                    <div id="infoView">
                      <div id="hoverTitle">{{item.id}}</div>
                      <div id="line" />
                      <div id="hoverTips">
                        <el-button @click="goDetail(item.id, item.imgUrl)" :imgUrl="item.imgUrl" type="primary" icon="el-icon-edit" circle class="button-edit"></el-button>
                        <el-button @click="deleteLogo(item.id)" type="primary" icon="el-icon-delete" circle class="button-de"></el-button>
                      </div>
                    </div>
                  </div>
                </el-card>
              </div>
          </el-col>
        </el-row>
        <div class="notFoundView" v-else>
          <i class="iconfont icon-not-found"/>
          您还没有图片~
        </div>
      </div>
    </div>
    <div class="notFoundView" v-else>
      <i class="iconfont icon-not-found"/>
      <el-button type="primary" @click="toLogin">点击登录</el-button>
    </div>
  </div>
</template>

<script>
import { getToken } from 'utils/auth'
import { deletelogoById, getGalleryDetailInfo } from 'api/mylogo'
import store from 'store'
import AddLogo from './components/Addlogo'
export default {
  name: 'app',
  components: {
    AddLogo
  },
  data () {
    return {
      imgList: [],
      loading: false,
      AddLogoVisible: false,
      display: true,
      galleryid: this.$route.params.id
    }
  },
  computed: {
    isloggedIn: function () {
      return store.state.isloggedIn
    },
    isData: function () {
      if (this.imgList.length === 0) {
        return false
      } else {
        return true
      }
    }
  },
  methods: {
    goBack () {
      this.$router.go(-1)
    },
    setLoading () {
      this.loading = true
      setTimeout(() => {
        this.loading = false
      }, 600)
    },
    deleteLogo (id) {
      const postdata = {
        token: getToken(),
        galleryid: this.galleryid,
        logoid: id
      }
      deletelogoById(postdata).then(this.handleDeleteSucc)
    },
    handleDeleteSucc (res) {
      res = res.data
      if (res.code === 200) {
        this.$message({
          message: '删除成功',
          type: 'success'
        })
      }
      this.getDetailInfo()
    },
    addlogo () {
      this.AddLogoVisible = true
    },
    closeAdd () {
      this.AddLogoVisible = false
    },
    addSucc (code) {
      if (code === 200) {
        this.$message({
          message: '添加成功',
          type: 'success'
        })
      } else {
        this.$message.error('添加失败')
      }
      this.AddLogoVisible = false
      this.getDetailInfo()
    },
    getDetailInfo () {
      const postdata = {
        token: getToken(),
        id: this.galleryid
      }
      getGalleryDetailInfo(postdata).then(this.handleGetDetailSucc)
    },
    handleGetDetailSucc (res) {
      res = res.data
      if (res.code === 200) {
        this.imgList = res.items
      }
    },
    goDetail (id, url) {
      this.$router.push({
        path: `/mylogo/logodetail/${id}`
      })
    },
    toLogin () {
      this.$router.push({
        path: '/login',
        query: {
          mode: 'login'
        }
      })
    },
    onChange () {
      this.$message.warning('暂未开放，敬请期待')
    }
  },
  mounted () {
    this.getDetailInfo()
    this.setLoading()
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
    border-left: 0px;
    border-right: 0px;
    border-bottom: 0px;
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
    margin-top: 40px;
    margin-left: 200px;
    margin-right: 100px;
  }

  .container-header {
    margin-bottom: 10px;
    overflow: hidden;
  }

  .card {
    position: relative;
    height: 300px;
    margin-bottom: 30px;
    cursor: pointer;
  }

  .col {
    margin-left: 30px;
    margin-top: 0px;
    width: 250px;
  }

  .image {
    width: 100%;
    height: 100%;
    display: block;
  }

  .delete-img {
    display: block;
  }

  .notFoundView {
    height: 400px;
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
    color: grey;
  }

  .notFoundView i {
    font-size: 150px;
    color: #E9E9EB;
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
