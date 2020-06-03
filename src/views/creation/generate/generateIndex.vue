<template>
  <div class="container">
    <!-- 品牌信息输入栏 -->
    <div class="generateBar">
      <!-- 这是一个1000px的div -->
      <div>
        <div class="generateInput">
          <input v-model="keyword">
        </div>
        <button class="generateBtn" @click="generateLogos(6)">立即生成</button>
      </div>
    </div>
    <!-- 按钮区域 -->
    <div class="tipsContainer">
      <span>对生成结果不满意？试试</span>
      <el-button type="primary" size="mini" icon="el-icon-refresh" @click="generateLogos(6)">换一批</el-button>
    </div>
    <!-- 生成结果 -->
    <div class="resultContainer" v-if="base64List.length !== 0" :v-loading="loading">
      <showCard
        v-for="base64 in base64List"
        :key="base64"
        :src="'data:image/jpeg;base64,' + base64" />
    </div>
    <!-- 错误处理 -->
    <div class="notFoundContainer" v-else :v-loading="loading">
      <iconfont name="icon-not-found" style="font-size: 150px;" />
      <div>LOGO生成失败，换个关键词试试~</div>
    </div>
  </div>
</template>

<style scoped>
.container {
  display: flex;
  flex-direction: column;
  align-items: center;
}

.generateBar {
  height: 80px;
  width: 100%;
  display: flex;
  justify-content: center;
  background: linear-gradient(to right, rgb(236, 247, 255) , rgb(237, 254, 254));
}

.generateBar>div {
  width: 1000px;
  height: 100%;
  display: flex;
  align-items: center;
  justify-content: flex-end;
}

.generateInput {
  margin-right: 20px;
  width: 300px;
  height: 40px;
  border-radius: 20px;
  border: 1px #DCDFE6 solid;
}

.generateInput input {
  margin-left: 20px;
  width: 250px;
  height: 100%;
  border: none;
  outline: none !important;
  background: transparent;
  color: #606266;
  font-size: 14px;
}

.generateBtn {
  width: 100px;
  height: 40px;
  border: 1px solid #409EFF;
  border-radius: 20px;
  background: none;
  color: #409EFF;
  font-size: 14px;
  cursor: pointer;
}

.tipsContainer {
  margin: 40px 0 20px 0;
  color: #909399;
  font-size: 14px;
}

.tipsContainer .el-button {
  margin-left: 10px;
}

.resultContainer {
  width: 1000px;
  display: flex;
  flex-wrap: wrap;
  align-items: space-between;
  justify-content: space-between;
}

.notFoundContainer {
  width: 1000px;
  height: 544px;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  color: #909399;
  font-size: 14px;
}
</style>

<script>
import showCard from './components/showCard'
import { generate } from 'api/creation'
export default {
  components: {
    showCard
  },

  data () {
    return {
      loading: false,
      keyword: this.$route.query.keyword,
      base64List: []
    }
  },

  mounted () {
    this.generateLogos(6)
  },

  methods: {
    generateLogos (count) {
      this.loading = true

      if (this.keyword === '') {
        this.base64List = []

        return
      }

      const params = {
        count: count,
        paramsList: []
      }

      for (let i = 0; i < count; i++) {
        const temp = {
          era: Math.random(),
          maturity: Math.random(),
          weight: Math.random(),
          personality: Math.random(),
          definition: Math.random(),
          concept: Math.random(),
          text: this.keyword
        }
        params.paramsList.push(temp)
      }

      generate(params).then((res) => {
        // ————成功回调
        const data = res.data
        switch (data.code) {
          case 200:
            this.base64List = data.base64List
            break
          case 501:
            this.$message.error('生成失败，请刷新重试')
        }
      }).catch((err) => {
        // ————失败回调
        this.$message.error('用户信息拉取失败：' + err.message)
      }).finally(() => {
        this.loading = false
      })
    }
  }
}
</script>
