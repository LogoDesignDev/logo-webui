<template>
  <div style="width: 100%">
    <div style="padding: 10px 20px;">
      <span class="title-1">基本信息</span>
      <el-divider />
    </div>

    <div
      v-loading="loading.userInfo"
      element-loading-spinner="el-icon-loading"
      element-loading-background="#ffffff">
      <!-- 用户数据加载失败 -->
      <div style="text-align: center;" v-if="!baseForm.username">
        <span class="tips">
            <i class="el-icon-warning-outline" />
            数据拉取失败,刷新一下试试~
          </span>
      </div>

      <!-- 用户数据加载成功 -->
      <div v-else>
        <!-- 头像编辑区域 -->
        <div style="padding: 0 0 40px 120px;">
          <span class="title-2">头像</span>
        </div>
        <div id="userPicView">
          <div
            id="userPicBtn"
            @mouseenter="showMask"
            @mouseleave="hideMask"
            @click="showUploadPicDialog">
            <el-image style="position: absolute;" class="userPic-normal" :src="userPicUrl" />
            <div id="picMask" class="userPic-normal" v-show="visible.picMask">
              <span>上传头像</span>
            </div>
          </div>
          <span class="tips">
            <i class="el-icon-warning-outline" />
            仅支持JPG格式，文件小于1M(方形图)
          </span>
        </div>

        <!-- 其他信息编辑区域 -->
        <div style="padding: 40px 0 40px 120px;">
          <span class="title-2">个人信息</span>
        </div>
        <el-form
          class="infoForm"
          label-width="100px"
          label-position="left"
          ref="baseForm"
          :rules="baseRules"
          :model="baseForm">
          <el-form-item prop="username" label="用户名">
            <el-input v-model="baseForm.username" />
          </el-form-item>
          <el-form-item prop="phone" label="手机号">
            <el-input v-model="baseForm.phone" :disabled="true" />
          </el-form-item>
          <el-form-item prop="email" label="联系邮箱">
            <el-input v-model="baseForm.email" :disabled="true" />
          </el-form-item>
          <el-form-item>
            <el-button type="primary" :loading="loading.save" @click="login('loginForm')">保存</el-button>
          </el-form-item>
        </el-form>
      </div>
    </div>
    <!-- 图片上传弹出框 -->
    <el-dialog title="编辑头像" :visible.sync="visible.uploadPicDialog" width="650px">
      <pic-editor v-if="visible.uploadPicDialog" @uploadSuccess="handleUploadSuccess"/>
    </el-dialog>
  </div>
</template>

<style scoped>
.infoForm {
  margin: 0 auto;
  width: 400px;
  text-align: center;
}

#userPicView {
  width: 100%;
  display: flex;
  justify-content: center;
  align-items: flex-end;
}

#picMask {
  background: rgba(0, 0, 0, 0.5);
  display: flex;
  justify-content: center;
  align-items: center;
  color: white;
  font-size: 14px;
  position: absolute;
  user-select: none;
}

#userPicBtn {
  width: 150px;
  height: 150px;
  cursor: pointer;
}
</style>

<script>
import picEditor from './picEditor'
import { getUserPic, getUserInfo } from '../../../api/user'

export default {
  components: {
    'pic-editor': picEditor
  },

  data () {
    return {
      // loding状态
      loading: {
        userInfo: false,
        save: false
      },
      // visible状态
      visible: {
        uploadPicDialog: false,
        picMask: false
      },
      userPicUrl: '',
      // 基本信息表单
      baseForm: {
        username: '',
        phone: '',
        email: ''
      },
      // 登录提交表单验证规则
      baseRules: {
        username: [
          { required: true, message: '用户名不能为空', trigger: 'blur' }
        ],
        phone: [
          { required: true, message: '手机号不能为空', trigger: 'blur' }
        ],
        email: [
          { required: true, message: '邮箱不能为空', trigger: 'blur' }
        ]
      }
    }
  },

  created () {
    this.updateUserInfo()
    this.updateUserPic()
  },

  methods: {
    /**
     * 头像展示区域的半透明mask显隐
     */
    showMask () {
      this.visible.picMask = true
    },

    hideMask () {
      this.visible.picMask = false
    },

    /**
     * 打开头像编辑框
     */
    showUploadPicDialog () {
      this.visible.uploadPicDialog = true
    },

    /**
     * 拉取用户头像
     */
    updateUserPic () {
      getUserPic({}).then((res) => {
        // ————成功回调
        const data = res.data
        switch (data.code) {
          case 20000:
            this.userPicUrl = data.userPicUrl
            break
        }
      }).catch((err) => {
        // ————失败回调
        console.log(err)
      })
    },

    /**
     * 拉取用户基本信息
     */
    updateUserInfo () {
      // 加载动画
      this.loading.userInfo = true
      // 这个字段还用于判断div是否显示,所以拉取时先置空
      this.baseForm.username = ''
      getUserInfo({}).then((res) => {
        // ————成功回调
        const data = res.data
        switch (data.code) {
          case 20000:
            this.baseForm.username = data.username
            this.baseForm.email = data.email
            this.baseForm.phone = data.phone
            break
        }
      }).catch((err) => {
        // ————失败回调
        console.log(err)
      }).finally(() => {
        this.loading.userInfo = false
      })
    },

    /**
     * 处理头像上传成功
     */
    handleUploadSuccess () {
      this.updateUserPic()
      this.visible.uploadPicDialog = false
    }
  }
}
</script>
