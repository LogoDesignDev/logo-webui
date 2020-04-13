<template>
  <div>
    <div class = "container-header">
      <el-button @click="add" class="new" type="primary" icon="el-icon-plus"> 新建图库</el-button>
      <MyLogo-Add :AddFormVisible="AddFormVisible" v-if="AddFormVisible" @Add-cancel="closeAdd"></MyLogo-Add>
    </div>
    <div class = "container">
      <el-row>
        <el-col :span="5" v-for="(item, index) in iconList" :key="index" class="col">
            <el-card :body-style="{ padding: '0px'}" class="card">
              <img :src="item.imgUrl" class="image">
              <el-tag class="name" type="success" effect="plain">汉堡包</el-tag>
              <div style="padding: 14px;">
                <time class="time">Date: {{ currentDate }}</time>
                <div class="bottom clearfix">
                  <el-button @click="getDetail(item.id)" type="success" size="medium" class="button-detail" icon="el-icon-view"> 详情</el-button>
                  <el-button @click="edit" class="button-edit" icon="el-icon-edit-outline" size="medium"> 编辑</el-button>
                  <MyLogo-Edit :dialogFormVisible="dialogFormVisible" v-if="dialogFormVisible" @dialog-cancel="closeManage"></MyLogo-Edit>
                </div>
              </div>
            </el-card>
        </el-col>
      </el-row>
    </div>
  </div>
</template>

<script>
import axios from 'axios'
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
      iconList: []
    }
  },
  computed: {
    isloggedIn: function () {
      return true
    }
  },
  methods: {
    getLogoInfo () {
      axios.get('/api/logo.json').then(this.handleGetLogoInfoSucc)
    },
    handleGetLogoInfoSucc (res) {
      res = res.data
      if (res.ret && res.data) {
        const data = res.data
        this.iconList = data.iconList
      }
    },
    edit () {
      this.dialogFormVisible = true
    },
    closeManage () {
      this.dialogFormVisible = false
    },
    add () {
      this.AddFormVisible = true
    },
    closeAdd () {
      this.AddFormVisible = false
    },
    getDetail (id) {
      this.$router.push({
        path: `/detail/${id}`
      })
    }
  },
  mounted () {
    this.getLogoInfo()
  }
}
</script>

<style>
  .container {
    margin-top: 20px;
    margin-left: 50px;
  }

  .container-header {
    margin-bottom: 10px;
    overflow: hidden;
  }

  .new {
    float: right;
    margin-top: 20px;
    padding-right: 30px;
    margin-right: 30px;
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
