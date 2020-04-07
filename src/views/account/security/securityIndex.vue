<template>
  <!-- 最外层的div的宽度一定不能超过92% -->
  <!-- 否则会挡住tab的滑动条造成不美观 -->
  <div class="childContainer">
    <span class="title-1">账户安全</span>
    <el-divider />

    <!-- 功能区域 -->
    <div class="functionView">
      <content-card
        type="success"
        title="设置密码"
        content="您已设置密码"
        buttonText="修改密码"
        @clickButton="visible.changePassword = true" /><el-divider />
      <content-card
        v-loading="loading.userInfo"
        element-loading-spinner="el-icon-loading"
        :type="state.changeEmail"
        :buttonDisable="state.changeEmail==='warning'"
        title="绑定邮箱"
        :content="userInfo.email"
        buttonText="更换邮箱"
        @clickButton="visible.changeEmail = true" /><el-divider />
      <content-card
        v-loading="loading.userInfo"
        element-loading-spinner="el-icon-loading"
        :type="state.changePhone"
        :buttonDisable="state.changeEmail==='warning'"
        title="绑定手机"
        :content="userInfo.phone"
        buttonText="更换手机"
        @clickButton="visible.changePhone = true" />
    </div>

    <!-- 修改密码卡片 -->
    <el-dialog width="500px" title="修改密码" :visible.sync="visible.changePassword">
      <change-password v-if="visible.changePassword" @changeSuccess="handleChangeSuccess"/>
    </el-dialog>
    <!-- 更换邮箱卡片 -->
    <el-dialog width="500px" title="更换邮箱" :visible.sync="visible.changeEmail">
      <change-email v-if="visible.changeEmail" :oldEmail="userInfo.email" @changeSuccess="handleChangeSuccess"/>
    </el-dialog>
    <!-- 更换手机卡片 -->
    <el-dialog width="500px" title="更换手机" :visible.sync="visible.changePhone">
      <change-phone v-if="visible.changePhone" :oldPhone="userInfo.phone" @changeSuccess="handleChangeSuccess"/>
    </el-dialog>
  </div>
</template>

<style lang="less" scoped>
.functionView {
  padding: 0 50px;
}
</style>

<script>
import contentCard from './components/contentCard'
import changePassword from './changePassword'
import changeEmail from './changeEmail'
import changePhone from './changePhone'
import { getUserInfo } from 'api/user'

export default {
  components: {
    'content-card': contentCard,
    'change-password': changePassword,
    'change-email': changeEmail,
    'change-phone': changePhone
  },

  data () {
    return {
      userInfo: {
        email: '',
        phone: ''
      },
      // 用于控制邮箱和手机功能卡的状态
      state: {
        changeEmail: 'warning',
        changePhone: 'warning'
      },
      loading: {
        userInfo: false // 是否正在拉取用户信息
      },
      // 各弹窗是否可见
      visible: {
        changePassword: false,
        changeEmail: false,
        changePhone: false
      }
    }
  },

  created () {
    this.updateEmailAndPhone()
  },

  methods: {
    handleChangeSuccess () {
      this.visible.changePassword = false
      this.visible.changeEmail = false
      this.visible.changePhone = false
      this.updateEmailAndPhone()
    },

    /**
     * 拉取用户邮箱/手机
     */
    updateEmailAndPhone () {
      // 加载动画
      this.loading.userInfo = true
      // 这个字段还用于判断div是否显示,所以拉取时先置空
      this.username = ''
      getUserInfo({}).then((res) => {
        // ————成功回调
        const data = res.data
        switch (data.code) {
          case 20000:
            this.userInfo.email = data.email
            this.userInfo.phone = data.phone
            this.state.changeEmail = 'success'
            this.state.changePhone = 'success'
            break
        }
      }).catch((err) => {
        // ————失败回调
        console.log(err)
        this.email = '数据拉取失败'
        this.phone = '数据拉取失败'
      }).finally(() => {
        this.loading.userInfo = false
      })
    }
  }
}
</script>
