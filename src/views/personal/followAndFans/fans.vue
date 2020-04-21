<template>
  <div>
    <span class="titleView title-1">我的粉丝</span>

    <!-- 空提示 -->
    <div class="notFoundView" v-if="fansList.length === 0">
      <i class="iconfont icon-not-found" />
      <span class="tips">你还没有粉丝~</span>
    </div>

    <div class="listView" v-else>
      <el-table :data="showList" :show-header="false">
        <!-- 头像 -->
        <el-table-column width="90">
          <template slot-scope="scope">
            <el-image class="userPic-middle" :src="scope.row.userPicUrl" />
          </template>
        </el-table-column>
        <!-- 用户名 -->
        <el-table-column>
          <template slot-scope="scope">
            <span class="title-2">{{scope.row.username}}</span>
          </template>
        </el-table-column>
        <!-- 操作 -->
        <el-table-column width="150">
          <template slot-scope="scope">
            <el-button
              v-if="scope.row.hasFollowed"
              type="info"
              size="mini" round plain disabled
              icon="el-icon-finished">
              相互关注
            </el-button>
            <el-button
              v-else
              type="primary"
              size="mini" round plain
              @click="handleFollowClicked(scope.$index, scope.row.uid)">
              关注用户
            </el-button>
          </template>
        </el-table-column>
      </el-table>
      <el-pagination layout="prev, pager, next"
      :page-size="pageSzie"
      :total="fansList.length"
      @current-change="switchPage" />
    </div>
  </div>
</template>

<style lang="less" scoped>
.titleView {
  position: relative;
}

.notFoundView {
  height: 400px;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
}

.notFoundView i {
  font-size: 150px;
  color: #E9E9EB;
}

.listView {
  margin-top: 20px;
  text-align: center;
}

.el-pagination {
  margin-top: 40px;
}
</style>

<script>
import { getFansList, follow } from 'api/personal'
export default {
  data () {
    return {
      pageSzie: 5, // table每页展示数
      showList: [], // 展示出来的数据
      fansList: []
    }
  },

  created () {
    this.updateFansList()
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
      getFansList().then((res) => {
        // ———— 成功回调 ————
        const data = res.data

        switch (data.code) {
          case 20000: // 获取成功
            this.fansList = data.fansList
            this.switchPage(1) // 更新第一页列表
            break
        }
      })
    },

    /**
     * 关注用户
     * index - 行号
     */
    handleFollowClicked (index, uid) {
      const params = {
        uid: uid
      }
      follow(params).then((res) => {
        // ———— 成功回调 ————
        const data = res.data

        switch (data.code) {
          case 20000: // 修改成功
            this.$message({
              message: '关注成功',
              type: 'success'
            })
            this.showList[index].hasFollowed = true
            this.showList = this.showList.splice(0) // 为了更新table用的蠢办法
            break
        }
      })
    }
  }
}
</script>
