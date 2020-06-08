<template>
  <div class="searchContainer">
    <!-- 依旧是搜索栏 -->
    <div id="search-bar">
      <!-- 这是一个1000px的div -->
      <div>
        <!-- 搜索框 -->
        <div id="search-input">
          <input placeholder="请输入您要查找的内容" v-model="search.keyword">
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
    <!-- 设计师搜索结果 -->
    <designer :list="authorList" :page="search.page" @pageChange="handlePageChange" />
  </div>
</template>

<style scoped>
.searchContainer {
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

</style>

<script>
import designer from './designer/designerIndex'
import { searchAuthor, searchProd } from 'api/search'

export default {
  components: {
    designer
  },

  data () {
    return {
      searchHistoryKey: 'searchHistory', // localStorage的key
      search: {
        keyword: this.$route.query.keyword,
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
      prodList: []
    }
  },

  watch: {
    'search.mode' (val) {
      this.$router.push({
        path: '/search?mode=' + this.search.mode +
          '&keyword=' + this.search.keyword +
          '&prodOrder=' + this.search.prodOrder +
          '&designerOrder=' + this.search.designerOrder +
          '&datetime=' + this.search.datetime +
          '&page=' + this.search.page +
          '&randomParam=5da5JR%2B8Zfnbdl41Qr49%2BLKa8sxppt5qpwn7JmqF1HAm3ZI4g7VeuEmCmfE' + Math.random()
      })
    },

    'search.page' (val) {
      this.$router.push({
        path: '/search?mode=' + this.search.mode +
          '&keyword=' + this.search.keyword +
          '&prodOrder=' + this.search.prodOrder +
          '&designerOrder=' + this.search.designerOrder +
          '&datetime=' + this.search.datetime +
          '&page=' + this.search.page +
          '&randomParam=5da5JR%2B8Zfnbdl41Qr49%2BLKa8sxppt5qpwn7JmqF1HAm3ZI4g7VeuEmCmfE' + Math.random()
      })
    },

    '$route' (val) {
      this.$router.go(0)
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
      this.$router.push({
        path: '/search?mode=' + this.search.mode +
          '&keyword=' + this.search.keyword +
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
      const params = {
        keyword: this.search.keyword,
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
      })
    },

    updateProdList () {
      searchProd()
    }
  }
}
</script>
