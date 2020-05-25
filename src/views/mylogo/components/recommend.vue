<template>
  <div class="main">
    <div>猜你喜欢</div>
    <div class="container">
      <el-row>
        <el-col :span="4" v-for="(item, index) in iconList" :key="index" class="col">
            <el-card :body-style="{ padding: '0px'}" class="card">
                <img :src="item.imgUrl" class="image">
            </el-card>
        </el-col>
      </el-row>
    </div>
  </div>
</template>

<script>
import axios from 'axios'
export default {
  name: 'recommend',
  data () {
    return {
      iconList: []
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
    }
  },
  mounted () {
    this.getLogoInfo()
  }
}
</script>

<style lang="less" scoped>
  .main {
    margin-left: 100px;
    margin-right: 100px;
  }
  .image {
    width: 100%;
    height: 250px;
    display: block;
  }
</style>
