<template>
  <div class="fansContainer">
    <!-- 空提示 -->
    <div class="notFoundView" v-if="fansList.length === 0">
      <iconfont name="icon-null" style="font-size: 150px;" />
      <span class="tips">还没有粉丝</span>
    </div>

    <!-- 列表 -->
    <div class="listView" v-if="fansList.length !== 0">
      <div v-for="(item, index) in showList" :key="index">
        <user-card :data="item" />
        <!-- 最后一个不需要分割线 -->
        <div class="line" v-if="index !== showList.length - 1"/>
      </div>
    </div>
    <el-pagination
      v-if="fansList.length !== 0"
      background
      layout="prev, pager, next"
      :page-size="pageSzie"
      :total="fansList.length"
      @current-change="switchPage" />

  </div>
</template>

<style scoped>
.line {
  width: 100%;
  height: 1px;
  transform: scaleY(0.5);
  background: #DCDFE6;
}

.fansContainer {
  min-height: 400px;
  width: 95%;
}

.notFoundView {
  height: 400px;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
}

.listView {
  background: white;
  border-radius: 10px;
  margin-top: 20px;
  text-align: center;
}

.el-pagination {
  margin-top: 40px;
  width: 100%;
  text-align: center;
}

</style>

<style>
.el-pagination.is-background .el-pager li {
  background-color: white !important;
}

.el-pagination.is-background .el-pager li.active {
  background-color: #409EFF !important;
}
</style>

<script>
import { getFansList } from 'api/personal'
import userCard from './components/userCard'

export default {
  components: {
    'user-card': userCard
  },

  data () {
    return {
      pageSzie: 5, // table每页展示数
      showList: [], // 展示出来的数据
      fansList: []
    }
  },

  mounted () {
    this.updateFansList()
    this.switchPage(1) // 更新第一页列表
  },

  methods: {
    /**
     * 更改页码回调
     */
    switchPage (currentPage) {
      const start = (currentPage - 1) * this.pageSzie
      this.showList = this.fansList.slice(start, start + this.pageSzie)
    },

    /**
     * 获取粉丝列表
     */
    updateFansList () {
      const params = {
        uid: this.$route.query.uid
      }
      getFansList(params).then((res) => {
        // ———— 成功回调 ————
        const data = res.data

        switch (data.code) {
          case 20000: // 获取成功
            this.fansList = data.fansList
            this.switchPage(1) // 更新第一页列表
            break
        }
      })
    }
  }
}
</script>
