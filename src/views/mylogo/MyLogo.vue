<template>
  <div>
    <img src="https://s1.ax1x.com/2020/06/07/t2WUmT.png" class="header-img">
    <div v-if="isloggedIn">
      <div class = "container-header">
        <el-button @click="add" class="add" type="primary" icon="el-icon-plus"> 新建图库</el-button>
        <MyLogo-Add :logoList="logoList" :AddFormVisible="AddFormVisible" v-if="AddFormVisible" @Add-cancel="closeAdd" @Add-succ="succAdd"></MyLogo-Add>
      </div>
      <div class = "container" v-loading="loading" :data="logoList">
        <div class="notFoundView" v-if="isIconList">
          <i class="iconfont icon-not-found"/>
          您还没有图库~
        </div>
        <el-row v-else>
          <el-col :span="4" v-for="(item, index) in logoList" :key="index" class="col">
              <el-card :body-style="{ padding: '0px'}" class="card">
                <img :src="imgUrl" class="image">
                <el-tag class="name" type="primary" effect="plain">{{ item.name }}</el-tag>
                <div style="padding: 14px;">
                  <time class="time">Date: {{ currentDate }}</time>
                  <div class="bottom clearfix">
                    <el-button @click="getDetail(item.id)" type="primary" size="medium" class="button-detail" icon="el-icon-view"> 详情</el-button>
                    <el-button @click="edit" class="button-edit" icon="el-icon-edit-outline" size="medium"> 编辑</el-button>
                    <MyLogo-Edit :id="item.id" :dialogFormVisible="dialogFormVisible" v-if="dialogFormVisible" @dialog-cancel="closeManage" @update-succ="succUpdate" @delete-succ="succDelete"></MyLogo-Edit>
                  </div>
                </div>
              </el-card>
          </el-col>
        </el-row>
      </div>
    </div>
    <div class="notFoundView" v-else>
      <i class="iconfont icon-not-found"/>
      <el-button type="primary" @click="toLogin">点击登录</el-button>
    </div>
  </div>
</template>

<script>
import store from 'store'
import { getToken } from 'utils/auth'
import { getGalleryInfo } from 'api/mylogo'
import MyLogoEdit from './components/Edit'
import MyLogoAdd from './components/Add'
export default {
  name: 'MyLogo',
  components: {
    MyLogoEdit,
    MyLogoAdd
  },
  data () {
    return {
      dialogFormVisible: false,
      AddFormVisible: false,
      currentDate: (new Date()).toLocaleDateString(),
      logoList: [],
      imgUrl: 'https://ss0.baidu.com/6ONWsjip0QIZ8tyhnq/it/u=488030022,1694816207&fm=173&app=25&f=JPEG?w=580&h=347&s=A08FB35A5E0616C664F5631C030010D6',
      loading: false
    }
  },
  computed: {
    isloggedIn: function () {
      return store.state.isloggedIn
    },
    isIconList: function () {
      if (this.logoList.length === 0) {
        return true
      } else {
        return false
      }
    }
  },
  methods: {
    getAllGalleryInfo () {
      const postdata = {
        token: getToken()
      }
      getGalleryInfo(postdata).then(this.handleGetGalleryInfoSucc)
    },
    setLoading () {
      this.loading = true
      setTimeout(() => {
        this.loading = false
      }, 600)
    },
    handleGetGalleryInfoSucc (res) {
      res = res.data
      console.log(res.code)
      if (res.code === 200) {
        this.logoList = res.logoList
      } else {
        this.logoList.length = 0
      }
    },
    edit () {
      this.dialogFormVisible = true
    },
    succUpdate (code) {
      if (code === 200) {
        this.$message({
          message: '修改成功',
          type: 'success'
        })
      } else {
        this.$message.error('修改失败，请重试')
      }
      this.dialogFormVisible = false
      this.getAllGalleryInfo()
    },
    succDelete (code) {
      if (code === 200) {
        this.$message({
          message: '删除成功',
          type: 'success'
        })
      } else {
        this.$message.error('删除失败，请重试')
      }
      this.dialogFormVisible = false
      this.getAllGalleryInfo()
    },
    closeManage () {
      this.dialogFormVisible = false
    },
    add () {
      this.AddFormVisible = true
    },
    succAdd (code) {
      if (code === 200) {
        this.$message({
          message: '添加成功',
          type: 'success'
        })
      } else {
        this.$message.error('添加失败，请重试')
      }
      this.AddFormVisible = false
      this.getAllGalleryInfo()
    },
    closeAdd () {
      this.AddFormVisible = false
    },
    getDetail (id) {
      this.$router.push({
        path: `/mylogo/detail/${id}`
      })
    },
    toLogin () {
      this.$router.push({
        path: '/login',
        query: {
          mode: 'login'
        }
      })
    }
  },
  mounted () {
    this.getAllGalleryInfo()
    this.setLoading()
  }
}
</script>

<style lang="less" scoped>
  .header-img {
    width: 100%
  }

  .container {
    margin-top: 20px;
    margin-left: 200px;
    margin-right: 100px;
  }

  .container-header {
    margin-bottom: 10px;
    overflow: hidden;
  }

  .add {
    float: right;
    margin-top: 20px;
    padding-right: 30px;
    margin-right: 50px;
  }

  .card {
    position: relative;
    height: 350px;
    margin-bottom: 20px;
  }

  .col {
    margin-left: 30px;
    margin-top: 0px;
    width: 250px;
  }

  .name {
    position: absolute;
    top: 5px;
    right: 5px;
    border-radius: 10px;
  }

  .time {
    font-size: 14px;
    font-weight: 600;
    color: #999;
  }

  .bottom {
    margin-top: 13px;
    line-height: 12px;
  }

  .button-detail {
    /* height: 30px;
    padding: 2px;
    width: 60px; */
    font-weight: 600;
    margin-right: 10px;
  }

  .button-edit {
    /* height: 30px;
    padding: 2px;
    width: 60px; */
    font-weight: 600;
    margin-right: 10px;
  }

  .button-delete {
    height: 30px;
    padding: 2px;
    width: 60px;
    font-weight: 800;
  }

  .image {
    width: 100%;
    height: 250px;
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
