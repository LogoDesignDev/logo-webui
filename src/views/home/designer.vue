<template>
  <div id="view">
    <view-title name="Top设计师" tips="成为一名积极创作的设计师" to="/a" />
    <div id="main">
      <!-- 用户头像区域 -->
      <div id=userPicView>
        <user-pic-card
          v-for="item in userList" :key="item.rank"
          :src="item.userPicUrl" :rank="item.rank"
          :uid="item.uid" :isHover="item.isHover"
          @enter="changeDetailsInfo" />
      </div>
      <!-- 详细信息 -->
      <!-- 这里为保证reflow的时候不会因为card显示与否导致页面高度变化 -->
      <!-- 因此在外层加一个固定宽高的div -->
      <div style="width: 300px; height: 113px; margin-top: 30px;">
        <transition name="el-fade-in-linear">
          <user-details-card
            v-show="detailsCardShow"
            :username="userDetails.username"
            :rank="userDetails.rank"
            :like="userDetails.like" :collect="userDetails.collect"
            :prod="userDetails.prod" />
          </transition>
      </div>
    </div>
  </div>
</template>

<style lang="less" scoped>
#view {
  height: 350px;
}

#main {
  margin-top: 40px;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
}

#userPicView {
  width: 800px;
  display: flex;
  justify-content: space-around;
  align-content: space-between;
}
</style>

<script>
import viewTitle from './components/viewTitle-2'
import userPicCard from './components/userPicCard'
import userDetailsCard from './components/userDetailsCard'
import { getDesigner } from 'api/home'

export default {
  components: {
    'view-title': viewTitle,
    'user-pic-card': userPicCard,
    'user-details-card': userDetailsCard
  },

  data () {
    return {
      detailsCardShow: true,
      userList: [],
      userDetails: {
        username: '',
        like: 0,
        collect: 0,
        prod: 0,
        rank: 0
      }
    }
  },

  mounted () {
    this.updateUserList()
  },

  methods: {
    changeDetailsInfo (rank) {
      // 这里借助transition做个动画
      // 但是由于vue的virtual dom，直接连续修改v-show是没有用的
      // 因此做了个定时处理，但是这个办法不稳定
      // 不过吧，能实现需求就好，你说是不？
      this.detailsCardShow = false
      this.userList.forEach((item) => {
        item.isHover = 'leave'
      })
      this.userList[rank - 1].isHover = 'hover'
      setTimeout(() => {
        this.userDetails = this.userList[rank - 1]
        this.detailsCardShow = true
      }, 200)
    },

    updateUserList () {
      getDesigner().then((res) => {
        // ———— 成功回调 ————
        const data = res.data
        switch (data.code) {
          case 200:
            this.userList = data.desinger // 这里也是拼错了
            this.userList.forEach((item, index) => {
              item.isHover = 'leave'
              item.rank = index + 1
            })
            this.userList[0].isHover = 'hover'
            this.changeDetailsInfo(1)
            break
        }
      }).catch((err) => {
      }).finally(() => {
      })
    }
  }
}
</script>
