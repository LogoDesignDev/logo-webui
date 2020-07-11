<template>
  <div class="designerContainer">
    <div class="pageView">
      <prodResultCard
        v-for="(item, index) in pageView.pageList"
        :key="index"
        :data="item" />
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
  display: flex;
  flex-wrap: wrap;
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
import prodResultCard from './components/prodResultCard'

export default {
  props: ['list', 'page'],

  components: {
    prodResultCard
  },

  data () {
    return {
      pageView: {
        pageList: [],
        total: this.list.length,
        pageSize: 20
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
      this.pageView.pageList = this.list.slice(start, start + this.pageView.pageSize)
    }
  },

  mounted () {
  },

  methods: {
    switchPage (curPage) {
      this.$emit('pageChange', curPage)
    }
  }
}
</script>
