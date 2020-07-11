<template>
  <div class="authorResultCardContainer">
    <!-- 左边用户信息区 -->
    <div class="leftContainer">
      <div>
        <img class="userPic-middle" :src="serverPrx + data.userPicUrl" />
        <div class="title-2">{{ data.username }}</div>
      </div>
      <div>
        <div class="numCard">
          <div class="title-3">{{ transition(data.fansCount) }}</div>
          <div class="tips">粉丝</div>
        </div>
        <div class="cardLine" />
        <div class="numCard">
          <div class="title-3">{{ transition(data.logoList.length) }}</div>
          <div class="tips">作品</div>
        </div>
        <div class="cardLine" />
        <div class="numCard">
          <div class="title-3">{{ transition(data.focusCount) }}</div>
          <div class="tips">关注</div>
        </div>
      </div>
    </div>
    <!-- 右边作品展示区 -->
    <div class="rightContainer">
      <div class="prodCard" v-if="data.logoList[0]">
        <img class="prodPic" :src="serverPrx + data.logoList[0].url">
      </div>
      <div class="prodCard" v-if="data.logoList[1]">
        <img class="prodPic" :src="serverPrx + data.logoList[1].url">
      </div>
    </div>
  </div>
</template>

<style scoped>
.authorResultCardContainer {
  width: 100%;
  height: 200px;
  display: flex;
  align-items: center;
  justify-content: space-between;
}

.leftContainer {
  height: 100%;
  display: flex;
  flex-direction: column;
  justify-content: center;
}

.leftContainer>div {
  margin: 15px 0;
  display: flex;
  align-items: center;
}

.title-2 {
  margin-left: 10px;
}

.numCard {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
}

.cardLine {
  margin: 0 30px;
  height: 100%;
  width: 1px;
  transform: scaleY(0.5);
  background: #DCDFE6;
}

.rightContainer {
  display: flex;
  align-items: center;
  justify-content: right;
}

.prodCard {
  margin-left: 10px;
  width:  200px;
  height: 140px;
  border-radius: 5px;
  background: black;
}

.prodPic {
  width: inherit;
  height: inherit;
  border-radius: 5px;
  object-fit: cover;
}

</style>

<script>
import { serverPrx } from 'utils/default'

export default {
  props: ['data'],

  data () {
    return {
      serverPrx: serverPrx
    }
  },

  methods: {
    /**
     * 把十进制数转换成xxK的表达形式
     * 不足1000的不处理
     */
    transition (num) {
      return num < 1000 ? num : (num / 1000).toFixed(1) + 'K'
    }
  }
}
</script>
