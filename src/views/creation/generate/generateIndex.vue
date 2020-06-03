<template>
  <div class="container">
    <!-- 品牌信息输入栏 -->
    <div class="generateBar">
      <!-- 这是一个1000px的div -->
      <div>
        <div class="generateInput">
          <input v-model="keyword">
        </div>
        <button class="generateBtn">立即生成</button>
      </div>
    </div>
    <!-- 生成结果 -->
    <div class="resultContainer">

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

.resultContainer {
  width: 1000px;
}
</style>

<script>
export default {
  data () {
    return {
      keyword: ''
    }
  }
}
</script>
