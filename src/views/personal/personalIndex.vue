<template>
  <div class="container">
    <img class="bgPic" src="https://i.ui.cn/Public/project/img/new-top.png">
    <!-- 内容区域 -->
    <div class="mainContainer">
      <!-- 其他内容 -->
      <div class="other">
        <!-- 选项卡 -->
        <div class="optionsBar">
          <input
            id="personal-1" class="optRadio" v-model="mode"
            type="radio" value="prod" />
          <label class="optText" for="personal-1">创作 · {{ userInfo.prodCount }}</label><div class="line" />
          <input
            id="personal-2" class="optRadio" v-model="mode"
            type="radio" value="follow" />
          <label class="optText" for="personal-2">关注 · {{ userInfo.followCount }}</label><div class="line" />
          <input
            id="personal-3" class="optRadio" v-model="mode"
            type="radio" value="fans" />
          <label class="optText" for="personal-3">粉丝CITEST2 · {{ userInfo.fansCount }}</label>
        </div>
        <!-- 创作页 -->
        <prod v-if="mode==='prod'" :data="uid" />
        <!-- 关注页 -->
        <follow v-if="mode==='follow'"/>
        <!-- 关注页 -->
        <fans v-if="mode==='fans'"/>
      </div>
      <!-- 个人卡片 -->
      <div class="personalInfoCard">
        <img class="cardTopPic" src="https://s1.ax1x.com/2020/06/20/N1ZGUx.png">
        <img class="userPic-large userPic" :src="userInfo.userPicUrl">
        <div class="cardBody">
          <div class="title-3">{{ userInfo.username }}</div>
          <!-- 数据 -->
          <div class="numInfo">
            <div class="numCard">
              <div class="title-3">{{ transition(userInfo.followCount) }}</div>
              <div class="tips">关注</div>
            </div><div class="cardLine" />
            <div class="numCard">
              <div class="title-3">{{ transition(userInfo.fansCount) }}</div>
              <div class="tips">粉丝</div>
            </div><div class="cardLine" />
            <div class="numCard">
              <div class="title-3">{{ transition(userInfo.prodCount) }}</div>
              <div class="tips">作品</div>
            </div>
          </div>
          <el-button v-if="uid===myUserInfo.uid"
            type="primary" round @click="toAccountBase">账户设置</el-button>
          <el-button v-else type="primary" round>关注</el-button>
          <el-divider></el-divider>
          <!-- 成就 -->
          <div class="achievementCard">
            <div class="title-4">获得成就</div>
            <div>
              <div class="details">
                <iconfont name="icon-like"/>
                <span class="tips">获赞 {{ userInfo.beLikeCount }} 个</span>
              </div>
              <div class="details">
                <iconfont name="icon-collect"/>
                <span class="tips">被收藏 {{ userInfo.beCollectedCount }} 次</span>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped>
.container {
  padding-bottom: 50px;
  display: flex;
  flex-direction: column;
  align-items: center;
  background: rgb(239, 243, 245);
}

.bgPic {
  margin-top: -60px;
  width: 100%;
  height: 360px;
  object-fit: cover;
  /* position: absolute; */
}

.mainContainer {
  margin-top: -105px;
  width: 1000px;
  display: flex;
  justify-content: space-between;
}

.other {
  width: 100%;
  display: flex;
  flex-direction: column;
  align-items: center;
}

.personalInfoCard {
  width: 280px;
}

.cardTopPic {
  width: 280px;
  position: absolute;
}

.userPic {
  margin-top: 6px;
  margin-left: 100px;
  position: absolute;
}

.cardBody {
  margin-top: 105px;
  background: white;
  border-bottom-left-radius: 10px;
  border-bottom-right-radius: 10px;
  display: flex;
  flex-direction: column;
  align-items: center;
}

.optionsBar {
  height: 105px;
  display: flex;
  align-items: flex-end;
  align-self: flex-start
}

.line {
  margin-bottom: 20px;
  height: 20px;
  width: 1px;
  background: rgb(160, 192, 255);
  transform: scaleX(0.5);
}

/* 隐藏小圆点 */
.optRadio[type="radio"] {
  position: absolute;
  clip: rect(0, 0, 0, 0);
}

.optText {
  padding: 0 30px;
  height: 60px;
  line-height: 60px;
  font-size: 16px;
  color: rgb(160, 192, 255);
  cursor: pointer;
  transition: all 0.3s;
}

.optRadio[type="radio"]:checked+.optText, .optText:hover {
  color: white;
}

.numInfo {
  margin-top: 20px;
  display: flex;
  align-items: center;
  justify-content: space-around;
}

.numCard {
  margin: 0 25px;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
}

.el-button {
  margin-top: 20px;
  width: 200px;
}

.achievementCard {
  margin-top: -10px;
  padding:0 20px 20px;
  width: 240px;
}

.details {
  line-height: 45px;
  height: 35px;
}

.details .tips {
  margin-left: 10px;
}

</style>

<script>
import store from 'store'
import axios from 'axios'
import { getUserInfoByUid } from 'api/user'
import { getProdCount, getFansCount, getFollowCount, getUserBeLikeCount, getUserBeCollectedCount } from 'api/personal'
import { serverPrx, transition } from 'utils/default'
import follow from './follow'
import prod from './prod'
import fans from './fans'

export default {
  components: {
    follow,
    fans,
    prod
  },

  data () {
    return {
      mode: 'prod',
      uid: this.$route.query.uid,
      userInfo: {
        username: '',
        userPicUrl: '',
        prodCount: 0,
        fansCount: 0,
        followCount: 0,
        beLikeCount: 0,
        beCollectedCount: 0
      }
    }
  },

  computed: {
    myUserInfo () {
      return store.state.userInfo
    }
  },

  watch: {
    $route (to, from) {
      this.$router.go(0)
    }
  },

  mounted () {
    this.updateUserInfo()
  },

  methods: {
    transition,
    /**
     * 获取用户基本信息
     */
    updateUserInfo () {
      const params = {
        uid: this.uid
      }
      getUserInfoByUid(params).then((res) => {
        const data = res.data.ret
        // 继续请求其他信息
        axios.all([
          getProdCount(params),
          getFansCount(params),
          getFollowCount(params),
          getUserBeLikeCount(params),
          getUserBeCollectedCount(params)]).then(axios.spread((
          prodRes, fansRes, followRes, beLikeRes, beCollectedRes) => {
          this.userInfo = {
            username: data.username,
            userPicUrl: serverPrx + data.userPicUrl + '?' + Math.random(),
            prodCount: prodRes.data.count,
            fansCount: fansRes.data.count,
            followCount: followRes.data.count,
            beLikeCount: beLikeRes.data.count,
            beCollectedCount: beCollectedRes.data.count
          }
        })).catch((err) => {
        })
      }).catch((err) => {
      })
    },

    /**
     * 转到基本信息修改页
     */
    toAccountBase () {
      this.$router.push({
        path: '/account/base'
      })
    }
  }
}
</script>
