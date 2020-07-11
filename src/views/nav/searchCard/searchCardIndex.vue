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
        <span v-if="history.length === 0" class="tips" style="">暂无历史搜索记录</span>
        <button v-else id="del-all-btn" class="el-icon-delete" @click="delAllHistory">
          清空搜索历史
        </button>
        <div id="history-container">
          <history-item v-for='(item, index) in history' :key="index"
          :text="item" :index="index" @buttonClicked="delHistory" />
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

export default {
  components: {
    'history-item': historyItem
  },

  data () {
    return {
      searchHistoryKey: 'searchHistory', // localStorage的key
      search: {
        keyword: '',
        mode: 'prod'
      },
      history: []
    }
  },

  mounted () {
    const str = '我是历史啊1;我是历史啊2;我是历史啊33;我是历史啊4;我是历史啊5;我是历史啊21555;43534;;;;'
    window.localStorage.setItem(this.searchHistoryKey, str)

    // 读取历史记录
    this.decodeHistoryStr(window.localStorage.getItem(this.searchHistoryKey))
  },

  methods: {
    /**
     * 清空所有搜索记录
     */
    delAllHistory () {
      window.localStorage.removeItem(this.searchHistoryKey)
      this.history = []
    },

    /**
     * 删除某个记录
     */
    delHistory (keyword, index) {
      this.history.splice(index, 1)
      // 删除之后重新拼接存储
      let str = ''
      this.history.forEach((item) => {
        str = str + item + ';'
      })
      window.localStorage.setItem(this.searchHistoryKey, str)
    },

    /**
     * 解析从localstorage中取出的历史搜索记录串
     */
    decodeHistoryStr (str) {
      let temp = ''

      this.history = []
      for (let i = 0; i < str.length; i++) {
        if (str[i] === ';') {
          this.history.push(temp)
          temp = ''
        } else {
          temp = temp + str[i]
        }
      }
    },

    /**
     * 前往搜索页
     */
    toSearch () {
      this.$router.push({
        path: '/search?mode=' + this.search.mode +
          '&keyword=' + this.search.keyword +
          '&prodOrder=1' +
          '&designerOrder=1' +
          '&datetime=4' +
          '&page=1' +
          '&randomParam=5da5JR%2B8Zfnbdl41Qr49%2BLKa8sxppt5qpwn7JmqF1HAm3ZI4g7VeuEmCmfE' + Math.random()
      })
    }
  }
}
</script>
