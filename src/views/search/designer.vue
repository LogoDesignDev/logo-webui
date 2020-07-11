<template>
  <div class="designerContainer">
    <div class="pageView">
      <div class="authorCard" v-for="(info, index) in pageView.pageList" :key="index">
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

export default {
  props: ['list', 'page'],

  components: {
    authorResultCard
  },

  data () {
    return {
      pageView: {
        pageList: [],
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
