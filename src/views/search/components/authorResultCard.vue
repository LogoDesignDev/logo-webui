<template>
  <div class="authorResultCardContainer">
    <!-- 左边用户信息区 -->
    <div class="leftContainer">
      <div style="cursor: pointer;">
        <img
          class="userPic-middle"
          :src="serverPrx + data.userPicUrl"
          @click="toPersonal"/>
        <div
          class="title-2"
          @click="toPersonal">{{ data.username }}
        </div>
        <el-button
          v-if="!data.hasFollowed"
          type="primary"
          round size="mini"
          @click="follow">
          关注
        </el-button>
        <div
          v-if="data.hasFollowed"
          @mouseenter="unfollowBtnText = '取消关注'"
          @mouseleave="unfollowBtnText = '已关注'">
          <el-button
            type="info"
            round size="mini"
            @click="unfollow">
            {{ unfollowBtnText }}
          </el-button>
        </div>
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
      <div class="prodCard" v-if="data.logoList[0]" @click="toLogoDetail(data.logoList[0].lId)">
        <img class="prodPic" :src="serverPrx + (data.logoList[0].url[0]==='/'?'':'/') + data.logoList[0].url">
      </div>
      <div class="prodCard" v-if="data.logoList[1]" @click="toLogoDetail(data.logoList[1].lId)">
        <img class="prodPic" :src="serverPrx + (data.logoList[1].url[0]==='/'?'':'/') + data.logoList[1].url">
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
  margin-right: 10px;
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
  border: 1px solid #DCDFE6;
  cursor: pointer;
}

.prodPic {
  width: inherit;
  height: inherit;
  border-radius: 5px;
  object-fit: cover;
}

</style>

<script>
import { follow, unfollow } from 'api/personal'
import { getToken } from 'utils/auth'
import { serverPrx } from 'utils/default'

export default {
  props: ['data'],

  data () {
    return {
      serverPrx: serverPrx,
      unfollowBtnText: '已关注'
    }
  },
  methods: {
    /**
     * 把十进制数转换成xxK的表达形式
     * 不足1000的不处理
     */
    transition (num) {
      return num < 1000 ? num : (num / 1000).toFixed(1) + 'K'
    },

    toPersonal () {
      this.$router.push({
        path: '/personal',
        query: {
          uid: this.data.uId
        }
      })
    },

    toLogoDetail (lId) {
      this.$router.push({
        path: `/mylogo/logodetail/${lId}`
      })
    },

    follow () {
      const params = {
        token: getToken(),
        uid: this.data.uId
      }
      follow(params).then((res) => {
        // ———— 成功回调 ————
        const data = res.data
        switch (data.code) {
          case 200:
            this.$message.success('关注成功')
            this.data.hasFollowed = true
            this.$forceUpdate() // 这里不晓得为何设置true后不刷新，只好强制刷新了
            break
        }
      }).catch((err) => {
      }).finally(() => {
      })
    },

    unfollow () {
      const params = {
        token: getToken(),
        uid: this.data.uId
      }
      unfollow(params).then((res) => {
        // ———— 成功回调 ————
        const data = res.data
        switch (data.code) {
          case 200:
            this.$message.warning('您已取消关注')
            this.data.hasFollowed = false
            break
        }
      }).catch((err) => {
      }).finally(() => {
      })
    }
  }
}
</script>
