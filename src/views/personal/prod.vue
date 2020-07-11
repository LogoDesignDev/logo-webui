<template>
  <div class="prodContainer">
    <!-- 选项卡 -->
    <div class="optionsBar">
      <input
        id="prod-1" class="optRadio" v-model="mode"
        type="radio" value="logo" />
      <label class="optText" for="prod-1">作品 · {{ total }}</label>
      <input
        id="prod-2" class="optRadio" v-model="mode"
        type="radio" value="gallery" />
      <label
        v-if="data===myUserInfo.uid"
        class="optText"
        for="prod-2"
        @click="toMylogo">
        图库
      </label>
    </div>
    <!-- 作品 -->
    <div class="logoView" v-show="mode==='logo'">
      <logo-card v-for="item in prodList" :key="item.lId" :data="item" />
      <el-pagination
        background
        layout="prev, pager, next"
        :current-page="pageIndex"
        :page-size="pageSize" :total="total"
        @current-change="updateProdList" />
    </div>
  </div>
</template>

<style scoped>
.prodContainer {
  width: 95%;
}

.optionsBar {
  height: 90px;
  width: 210px;
  display: flex;
  align-items: center;
  justify-content: space-between;
  align-self: flex-start
}

/* 隐藏小圆点 */
.optRadio[type="radio"] {
  position: absolute;
  clip: rect(0, 0, 0, 0);
}

.optText {
  padding: 0 30px;
  height: 40px;
  line-height: 40px;
  font-size: 14px;
  color: #909399;
  border: 1px solid rgb(234, 238, 241);
  border-radius: 20px;
  cursor: pointer;
  transition: all 0.3s;
}

.optRadio[type="radio"]:checked+.optText, .optText:hover {
  color: #409EFF;
  background: rgb(221, 231, 246);
  border: 1px solid rgb(221, 231, 246);
}

.logoView {
  min-height: 400px;
  display: flex;
  flex-wrap: wrap;
}

.logoView .el-pagination {
  width: 100%;
  text-align: center;
}

.el-pagination {
  margin-top: 30px;
}

</style>

<style>
.el-pagination.is-background .el-pager li {
  background-color: rgb(226, 234, 250) !important;
}

.el-pagination.is-background .el-pager li.active {
  background-color: #409EFF !important;
}
</style>

<script>
import store from 'store'
import logoCard from './components/logoCard'
import { getUserProdsByUid } from 'api/personal'

export default {
  props: ['data'],

  components: {
    'logo-card': logoCard
  },

  data () {
    return {
      mode: 'logo',
      prodList: [],
      pageSize: 6,
      pageIndex: 1,
      total: 0
    }
  },

  computed: {
    myUserInfo () {
      return store.state.userInfo
    }
  },

  mounted () {
    this.updateProdList()
  },

  methods: {
    updateProdList (currentIndex) {
      const params = {
        uid: this.data,
        pageSize: this.pageSize,
        pageIndex: currentIndex || this.pageIndex
      }
      getUserProdsByUid(params).then((res) => {
        const data = res.data
        switch (data.code) {
          case 200:
            this.prodList = data.ret.logoList
            this.total = data.ret.count
        }
      }).catch((err) => {
      }).finally(() => {
      })
    },

    toMylogo () {
      this.$router.push({
        path: '/mylogo'
      })
    }
  }
}
</script>
