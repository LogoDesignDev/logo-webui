<template>
  <div class="container">
    <!-- 左 -->
    <div id="left">
      <a class="tips" href="http://www.w3school.com.cn">查看更多推荐作品 ></a>
      <img id="viewImg" src="https://fuss10.elemecdn.com/e/5d/4a731a90594a4af544c0c25941171jpeg.jpeg" />
    </div>
    <!-- 右 -->
    <div id="right">
      <!-- 搜索模式 -->
      <div id="search-mode">
        <input
          id="1" class="search-mode-radio" v-model="search.mode"
          type="radio" value="prod" />
        <label class="search-mode-text" for="1">作品</label>
        <input
          id="2" class="search-mode-radio" v-model="search.mode"
          type="radio" value="designer" />
        <label class="search-mode-text" for="2">设计师</label>
      </div>
      <!-- 搜索框 -->
      <div id="search-input">
        <el-input placeholder="请输入您要查找的内容" v-model="search.keyword">
          <el-button id="search-btn" slot="append" @click="toSearch">搜索</el-button>
        </el-input>
      </div>
      <!-- 搜索历史 -->
      <div id="search-history">
        <span
          v-if="history.active ? history.active.history.length === 0 : true"
          class="tips" style="">
          暂无历史搜索记录
        </span>
        <button
          v-else id="del-all-btn"
          class="el-icon-delete"
          @click="delAllHistory">
          清空搜索历史
        </button>
        <div v-if="history.active" id="history-container">
          <history-item
            v-for='(item, index) in history.active.history'
            :key="index"
            :text="item" :index="index"
            @buttonClicked="delHistory"
            @itemClicked="addSearch" />
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped>
.container {
  z-index: 99;
  top: 60px;
  left: 0;
  padding-top: 20px;
  width: 100%;
  height: 300px;
  border-radius: 0;
  border-top: 1px #E4E7ED solid;
  border-bottom: 1px #E4E7ED solid;
  background: white;
  display: flex;
  justify-content: center;
  position: absolute;
}

#left {
  margin-top: 17px;
  margin-right: 100px;
  display: flex;
  flex-direction: column;
}

#viewImg {
  margin-top: 15px;
  width: 200px;
  height: 150px;
  object-fit: cover;
}

#search-mode {
  margin-left: 20px;
  width: 90px;
  height: 50px;
  display: flex;
  align-items: center;
  justify-content: space-between;
}

#search-input {
  width: 700px;
}

#search-history {
  margin: 15px 0;
  width: 700px;
}

#search-btn {
  background: #409EFF;
  border-top-left-radius: 0;
  border-bottom-left-radius: 0;
  border: 1px #409EFF solid;
  color: white;
}

/* 隐藏小圆点 */
.search-mode-radio[type="radio"] {
  position: absolute;
  clip: rect(0, 0, 0, 0);
}

.search-mode-text {
  font-size: 14px;
  font-weight: bolder;
  cursor: pointer;
  transition: all 0.3s;
}

.search-mode-radio[type="radio"]:checked+.search-mode-text {
  color: #409EFF;
}

#history-container {
  display: flex;
  flex-wrap: wrap;
  align-items: center;
  justify-content: start;
}

#del-all-btn {
  margin-bottom: 5px;
  color: #909399;
  font-size: 14px;
  border: none;
  background: none;
  transition: all 0.3s;
  cursor: pointer;
}

#del-all-btn:hover {
  color: #409EFF;
}

</style>

<script>
import historyItem from './components/historyItem'
import { SEARCH_HISTORY_KEY, SearchHistory } from 'utils/searchHistory'

export default {
  components: {
    'history-item': historyItem
  },

  data () {
    return {
      search: {
        keyword: '',
        mode: 'prod'
      },
      history: {
        prod: null,
        designer: null,
        active: null
      }
    }
  },

  watch: {
    'search.mode' () {
      switch (this.search.mode) {
        case 'prod':
          this.history.active = this.history.prod
          break
        case 'designer':
          this.history.active = this.history.designer
          break
      }
    }
  },

  mounted () {
    // const str = '我是历史啊1;我是历史啊2;我是历史啊33;我是历史啊4;我是历史啊5;我是历史啊21555;43534;;;;'
    // window.localStorage.setItem(SEARCH_HISTORY_KEY.PROD, str)
    // const str2 = '我是历史啊1DESIGNER;DESIGNER我是历史啊2;我是历史啊33;我是历史啊4;我是历史啊5;我是历史啊21555;43534;;;;'
    // window.localStorage.setItem(SEARCH_HISTORY_KEY.DESIGNER, str2)

    this.history.prod = new SearchHistory(SEARCH_HISTORY_KEY.PROD)
    this.history.designer = new SearchHistory(SEARCH_HISTORY_KEY.DESIGNER)
    this.history.active = this.history.prod
  },

  methods: {
    /**
     * 前往搜索页
     */
    toSearch () {
      this.history.active.addHistory(this.search.keyword)
      this.$router.push({
        path: '/search?mode=' + this.search.mode +
          '&keyword=' + this.search.keyword +
          '&prodOrder=1' +
          '&designerOrder=1' +
          '&datetime=4' +
          '&page=1' +
          '&randomParam=5da5JR%2B8Zfnbdl41Qr49%2BLKa8sxppt5qpwn7JmqF1HAm3ZI4g7VeuEmCmfE' + Math.random()
      })
    },

    addSearch (text) {
      this.search.keyword = text
    },

    delAllHistory () {
      this.history.active.delAllHistory();
    },

    delHistory (index) {
      this.history.active.delHistory(index);
    }
  }
}
</script>
