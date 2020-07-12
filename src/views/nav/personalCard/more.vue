<template>
  <el-card id="container">
    <!-- 个人信息 -->
    <div id="personal">
      <div id="left">
        <img class="userPic-middle" :src="userInfo.userPicUrl" />
        <div>普通用户</div>
      </div>
      <div id="right">
        <span class="title-2">{{ userInfo.username }}</span>
        <div>
          <number-show text="关注" :number="userInfo.followCount" :to="'/personal?uid=' + userInfo.uid + '&mode=follow'" />
          <number-show text="粉丝" :number="userInfo.fansCount" :to="'/personal?uid=' + userInfo.uid + '&mode=fans'" />
          <number-show text="作品" :number="userInfo.prodCount" :to="'/personal?uid=' + userInfo.uid + '&mode=prod'" />
        </div>
      </div>
    </div>
    <el-divider />
    <!-- 跳转 -->
    <div id="linkTo">
      <link-card icon="el-icon-user" text="个人主页" :to="'/personal?uid=' + userInfo.uid" />
      <link-card icon="el-icon-document" text="我的作品" to="/personal" />
    </div>
    <el-divider />
    <!-- 其他 -->
    <div id="other">
      <span @click="toAccount">账号管理</span>
      <span @click="logout">退出</span>
    </div>
  </el-card>
</template>

<style lang="less" scoped>
#container {
  width: 280px;
}

#personal {
   margin: -10px 0 -10px 0;
  display: flex;
  justify-content: space-around;
  align-items: flex-end;
}

#other {
  margin: -15px 0 -10px 0;
  color: #909399;
  font-size: 14px;
  display: flex;
  justify-content: space-between;
}

#other span:hover {
  color: #606266;
  cursor: pointer;
}

#linkTo {
  margin: -20px 0 -20px 0;
}

#personal span {
  margin-left: 15px;
}

#left {
  height: 90px;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: space-between;
}

#left>div {
  color: #FFFFFF;
  font-size: 12px;
  line-height: 20px;
  height: 20px;
  border-radius: 10px;
  background: #409EFF;

}

#right {
  height: 90px;
  width: 200px;
  display: flex;
  flex-direction: column;
  justify-content: space-around;
}

#personal>div>div {
  width: 100%;
  justify-content: space-around;
  display: flex;
}
</style>

<script>
import store from 'store'
import { removeToken } from 'utils/auth'
import numberShow from './components/numberShow'
import linkCard from './components/linkCard'

export default {
  components: {
    'number-show': numberShow,
    'link-card': linkCard
  },

  computed: {
    userInfo: function () {
      return store.state.userInfo
    }
  },

  methods: {
    /**
     * 转到账号管理页
     */
    toAccount () {
      this.$router.push({
        path: '/account'
      })
    },

    /**
     * 退出登录
     */
    logout () {
      removeToken()
      store.commit('setIsloggedIn', false)
      this.$router.go(0)
    }
  }
}
</script>
