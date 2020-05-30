<template>
  <div>
    <!-- 依旧是搜索栏 -->
    <div id="search-bar">
      <!-- 这是一个1000px的div -->
      <div>
        <!-- 搜索框 -->
        <div id="search-input">
          <input placeholder="请输入您要查找的内容" v-model="search.text">
          <button class="el-icon-search" />
        </div>
        <!-- radio -->
        <div id="search-mode">
          <input
            id="11" class="search-mode-radio" v-model="search.mode"
            type="radio" value="prod" />
          <label class="search-mode-text" for="11">作品</label>
          <input
            id="21" class="search-mode-radio" v-model="search.mode"
            type="radio" value="user" />
          <label class="search-mode-text" for="21">设计师</label>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped>
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

</style>

<script>
export default {
  data () {
    return {
      searchHistoryKey: 'searchHistory', // localStorage的key
      search: {
        text: '',
        mode: 'user'
      },
      history: []
    }
  }
}
</script>
