<template>
  <div>
    <el-page-header class="header" @back="goBack"></el-page-header>
      <div class="container">
        <el-image
        class="img"
        v-for="(item, index) in imgList"
        :key="index" style="width: 250px; height: 250px; margin-top: 20px; "
        :src="item.imgUrl"
        :preview-src-list="srcList">
        </el-image>
      </div>
  </div>
</template>

<script>
import axios from 'axios'
export default {
  data () {
    return {
      imgList: [],
      srcList: []
    }
  },
  methods: {
    goBack () {
      this.$router.go(-1)
    },
    getDetailInfo () {
      axios.get('/api/logo.json', {
        params: {
          id: this.$route.params.id
        }
      }).then(this.handleGetDetailSucc)
      console.log(this.$route.params.id)
    },
    handleGetDetailSucc (res) {
      res = res.data
      if (res.ret && res.data) {
        const data = res.data
        this.imgList = data.items
        this.srcList = data.items
      }
    }
  },
  mounted () {
    this.getDetailInfo()
  }
}
</script>

<style>
  .container {
    margin-top: 20px;
    margin-left: 50px;
    margin-right: 50px;
  }

  .header {
    margin-left: 10px;
    margin-top: 10px;
  }
  .img {
    margin-left: 30px;
  }
</style>
