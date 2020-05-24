<template>
  <div>
    <el-page-header class="header" @back="goBack"></el-page-header>
    <vue-viewer multiple
      :thumb="imgList"
      list-ul-class="image-list"
      :full="imgList"
      >
      <!--在列表中加入右上角删除按钮-->
      <template slot-scope="target">
        <span class="icon-remove" @click.stop="onRemove(target.index)" style="">&times;</span>
      </template>
    </vue-viewer>
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
      }
    },
    onRemove (index) {
      alert(index);
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
    margin-left: 20px;
    margin-top: 10px;
    color: darkgray;
  }

  .image-list{
    padding: 0;
    display: inline;
  }
  .image-list li {
    margin-left: 20px;
    margin-top: 20px;
    list-style: none;
    position: relative;
    display: inline-block;
  }
  .image-list img {
    box-shadow: 0 0 5px #333;
    width: 200px;
    height: 180px;
    margin-left: 100px;
  }
  .icon-remove{
    width: 20px; height:20px;
    text-align: center; line-height: 20px;
    background:#f33;
    position:absolute; top:-10px; right:-10px;
    border-radius: 10px;
    cursor: pointer;
    color:#fff;
  }
  a {
    color: #42b983;
  }
</style>
