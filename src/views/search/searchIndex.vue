<template>
  <div class="searchContainer">
    <!-- 依旧是搜索栏 -->
    <div id="search-bar">
      <!-- 这是一个1000px的div -->
      <div>
        <!-- 搜索框 -->
        <div id="search-input">
          <input placeholder="请输入您要查找的内容" v-model="keyword">
          <button class="el-icon-search" @click="searchClicked" style="cursor: pointer;" />
        </div>
        <!-- radio -->
        <div id="search-mode">
          <input
            id="11" class="search-mode-radio" v-model="search.mode"
            type="radio" value="prod" />
          <label class="search-mode-text" for="11">作品</label>
          <input
            id="21" class="search-mode-radio" v-model="search.mode"
            type="radio" value="designer" />
          <label class="search-mode-text" for="21">设计师</label>
        </div>
      </div>
    </div>
    <!-- 选择器 -->
    <div class="selectBar">
      <!-- 作品排序方式 -->
      <el-select v-if="search.mode==='prod'" v-model="search.prodOrder" placeholder="请选择">
        <el-option
          v-for="item in options.prodOrder"
          :key="item.value"
          :label="item.label"
          :value="item.value">
        </el-option>
      </el-select>
      <!-- 作品发布时间 -->
      <el-select v-if="search.mode==='prod'" v-model="search.datetime" placeholder="请选择">
        <el-option
          v-for="item in options.prodDatetime"
          :key="item.value"
          :label="item.label"
          :value="item.value">
        </el-option>
      </el-select>
      <!-- 设计师排序方式 -->
      <el-select
        v-if="search.mode==='designer'"
        v-model="search.designerOrder"
        placeholder="请选择">
        <el-option
          v-for="item in options.designerOrder"
          :key="item.value"
          :label="item.label"
          :value="item.value">
        </el-option>
      </el-select>
    </div>
    <!-- 作品搜索结果 -->
    <prod :list="prodList" :page="search.page" @pageChange="handlePageChange" />
    <!-- 设计师搜索结果 -->
    <designer :list="authorList" :page="search.page" @pageChange="handlePageChange" />
    <!-- 没有搜索到结果 -->
    <div
      v-if="!loading && ((search.mode === 'prod' && prodList.length === 0) || (search.mode === 'designer' && authorList.length === 0))"
      class="nullView">
      <iconfont name="icon-null" style="font-size: 140px;"/>
      <div class="nullText">
        <div class="nullTitle">抱歉，没有找到相关内容~</div>
        <div class="nullTips">去看看推荐吧！</div>
      </div>
    </div>
  </div>
</template>

<style scoped>
.searchContainer {
  min-height: 700px;
  padding-bottom: 50px;
  display: flex;
  flex-direction: column;
  align-items: center;
  background: rgb(245, 247, 250);
}

#search-bar {
  height: 80px;
  width: 100%;
  background: #409EFF;
  display: flex;
  justify-content: center;
}

#search-bar>div {
  width: 1000px;
  height: 100%;
  display: flex;
  align-items: center;
  justify-content: space-between;
}

#search-input {
  width: 300px;
  height: 40px;
  border-radius: 20px;
  border: 1px white solid;
}

#search-input input {
  margin-left: 20px;
  width: 230px;
  height: 100%;
  border: none;
  outline: none !important;
  background: transparent;
  color: white;
  font-size: 14px;
}

#search-input button {
  width: 40px;
  height: 40px;
  border: none;
  background: none;
  color: white;
  font-size: 16px;
  font-weight: bolder;
  text-align: center;
}

#search-mode {
  width: 160px;
  display: flex;
  align-items: center;
  justify-content: space-between;
}

/* 隐藏小圆点 */
.search-mode-radio[type="radio"] {
  position: absolute;
  clip: rect(0, 0, 0, 0);
}

.search-mode-text {
  width: 80px;
  height: 76px;
  line-height: 76px;
  font-size: 14px;
  color: rgb(225, 225, 225);
  text-align: center;
  cursor: pointer;
  border-bottom: 4px transparent solid;
  transition: color 0.3s;
}

::-webkit-input-placeholder {/*Chrome/Safari*/
  color: rgb(225, 225, 225);
}

.search-mode-radio[type="radio"]:hover+.search-mode-text {
  color: white;
}

.search-mode-radio[type="radio"]:checked+.search-mode-text {
  color: white;
  border-bottom: 4px white solid;
}

.selectBar {
  width: 1000px;
  height: 100px;
  display: flex;
  align-items: center;
}

.selectBar .el-select {
  margin-right: 20px;
}

.selectBar /deep/ .el-input__inner {
  background: rgb(245, 247, 250);
}

.nullView {
  height: 200px;
  display: flex;
  align-items: center;
  justify-content: center;
}

.nullText {
  padding-top: 30px;
  margin-left: 30px;
}

.nullTitle {
  line-height: 40px;
  color: rgb(160, 207, 255);
  font-size: 20px;
  font-weight: bold;
}

.nullTips {
  color: #C0C4CC;
  font-size: 14px;
  font-weight: bold;
}

</style>

<script>
import prod from './prod'
import designer from './designer'
import { searchAuthor, searchProd } from 'api/search'
import { SEARCH_HISTORY_KEY, SearchHistory } from 'utils/searchHistory'

export default {
  components: {
    prod,
    designer
  },

  data () {
    return {
      searchHistoryKey: 'searchHistory', // localStorage的key
      keyword: this.$route.query.keyword,
      search: {
        mode: this.$route.query.mode,
        prodOrder: Number(this.$route.query.prodOrder),
        designerOrder: Number(this.$route.query.designerOrder),
        page: Number(this.$route.query.page),
        datetime: Number(this.$route.query.datetime)
      },
      options: {
        prodOrder: [{
          value: 1,
          label: '最新'
        }, {
          value: 2,
          label: '点赞最多'
        }, {
          value: 3,
          label: '收藏最多'
        }],
        prodDatetime: [{
          value: 1,
          label: '一天内'
        }, {
          value: 2,
          label: '一周内'
        }, {
          value: 3,
          label: '一个月内'
        }, {
          value: 4,
          label: '不限'
        }],
        designerOrder: [{
          value: 1,
          label: '最新'
        }, {
          value: 2,
          label: '粉丝最多'
        }, {
          value: 3,
          label: '获赞最多'
        }, {
          value: 4,
          label: '被收藏最多'
        }, {
          value: 5,
          label: '作品最多'
        }]
      },
      authorList: [],
      prodList: [],
      loading: false
    }
  },

  watch: {
    searchData: {
      handler (newVal, oldVal) {
        // 当变更前后的页码一样，说明并不是切换页，所以要把页码置1
        if (newVal.page === oldVal.page) {
          this.search.page = 1
        }
        this.$router.push({
          path: '/search?mode=' + this.search.mode +
            '&keyword=' + this.keyword +
            '&prodOrder=' + this.search.prodOrder +
            '&designerOrder=' + this.search.designerOrder +
            '&datetime=' + this.search.datetime +
            '&page=' + this.search.page +
            '&randomParam=5da5JR%2B8Zfnbdl41Qr49%2BLKa8sxppt5qpwn7JmqF1HAm3ZI4g7VeuEmCmfE' + Math.random()
        })
      },
      deep: true
    },

    '$route' (val) {
      this.$router.go(0)
    }
  },

  computed: {
    // 这里用上computed属性是因为直接watch直接监听对象的时候
    // handler中的newVal与oldVal是一样的
    searchData () {
      return JSON.parse(JSON.stringify(this.search))
    }
  },

  mounted () {
    switch (this.search.mode) {
      case 'prod':
        this.updateProdList()
        break
      case 'designer':
        this.updateAuthorList()
        break
    }
  },

  methods: {
    searchClicked () {
      switch (this.search.mode) {
        case 'prod':
          new SearchHistory(SEARCH_HISTORY_KEY.PROD).addHistory(this.keyword)
          break
        case 'designer':
          new SearchHistory(SEARCH_HISTORY_KEY.DESIGNER).addHistory(this.keyword)
          break
      }
      this.$router.push({
        path: '/search?mode=' + this.search.mode +
          '&keyword=' + this.keyword +
          '&prodOrder=' + this.search.prodOrder +
          '&designerOrder=' + this.search.designerOrder +
          '&datetime=' + this.search.datetime +
          '&page=' + this.search.page +
          '&randomParam=5da5JR%2B8Zfnbdl41Qr49%2BLKa8sxppt5qpwn7JmqF1HAm3ZI4g7VeuEmCmfE' + Math.random()
      })
    },

    handlePageChange (curPage) {
      this.search.page = curPage
    },

    updateAuthorList () {
      this.loading = true
      const params = {
        keyword: this.keyword,
        order: Number(this.search.designerOrder)
      }
      searchAuthor(params).then((res) => {
        // ———— 成功回调 ————
        const data = res.data
        switch (data.code) {
          case 200:
            this.authorList = data.authorList
            break
        }
      }).catch((err) => {
      }).finally(() => {
        this.loading = false
      })
    },

    updateProdList () {
      this.loading = true
      const params = {
        keyword: this.keyword,
        order: Number(this.search.designerOrder),
        datetime: Number(this.search.datetime)
      }
      searchProd(params).then((res) => {
        // ———— 成功回调 ————
        const data = res.data
        switch (data.code) {
          case 200:
            this.prodList = data.pordList // 这里接口的pord拼错了
            break
        }
      }).catch((err) => {
      }).finally(() => {
        this.loading = false
      })
    }
  }
}
</script>
