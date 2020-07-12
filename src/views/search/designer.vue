<template>
  <div class="designerContainer">
    <div class="pageView">
      <div class="authorCard"
        v-for="(info, index) in pageView.pageList"
        :key="index">
        <authorResultCard :data="info" />
        <!-- 最后一个不需要分割线 -->
        <div class="line" v-if="index !== pageView.pageList.length - 1"/>
      </div>
    </div>
    <el-pagination
      v-if="list.length !== 0"
      layout="prev, pager, next"
      :total="pageView.total"
      :page-size="pageView.pageSize"
      :current-page="page"
      @current-change="switchPage">
    </el-pagination>
  </div>
</template>

<style scoped>
.designerContainer {
  width: 1000px;
}

.pageView {
  background: white;
  border-radius: 10px;
  display: flex;
  flex-direction: column;
  align-items: center;
}

.authorCard {
  width: 900px;
}

.el-pagination {
  margin-top: 20px;
  text-align: end;
}

.el-pagination /deep/ * {
  background: none !important;
}

.line {
  width: 100%;
  height: 1px;
  transform: scaleY(0.5);
  background: #DCDFE6;
}
</style>

<script>
import authorResultCard from './components/authorResultCard'
import { hasFollowed } from 'api/personal'
import store from 'store'

export default {
  props: ['list', 'page'],

  components: {
    authorResultCard
  },

  data () {
    return {
      pageView: {
        pageList: [],
        tempList: [],
        total: this.list.length,
        pageSize: 10
      }
    }
  },

  watch: {
    'list.length' (val) {
      this.pageView.total = val
      // 为什么要把下面这段放在这而不是mounted呢
      // 因为在mounted触发时，可能还没有拉取到搜索结果
      // 如果此时执行下面代码，必定是空
      const start = (this.page - 1) * this.pageView.pageSize
      this.pageView.tempList = this.list.slice(start, start + this.pageView.pageSize)
      this.updateHasFollowed()
    },

    myUserInfo () {
      this.updateHasFollowed()
    }
  },

  computed: {
    myUserInfo () {
      return store.state.userInfo
    }
  },

  mounted () {
  },

  methods: {
    switchPage (curPage) {
      this.$emit('pageChange', curPage)
    },

    updateHasFollowed () {
      const othersUidList = []
      this.pageView.tempList.forEach((item) => {
        item.hasFollowed = false
        othersUidList.push(item.uId)
      })
      const params = {
        uid: this.myUserInfo.uid || null,
        othersUidList: othersUidList.join('|')
      }
      if (!params.uid || !params.othersUidList) {
        return
      }

      hasFollowed(params).then((res) => {
        const data = res.data
        switch (data.code) {
          case 200:
            this.pageView.tempList.forEach((item) => {
              item.hasFollowed = data.ret[item.uId]
            })
            break
        }
      }).catch((err) => {
      }).finally(() => {
        this.pageView.pageList = this.pageView.tempList
      })
    }
  }
}
</script>
