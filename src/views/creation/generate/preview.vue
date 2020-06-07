<template>
  <div class="previewContainer">
    <!-- 走马灯 -->
    <div class="carouselCard">
      <el-carousel height="400px" indicator-position="outside">
        <el-carousel-item><previewCard1 :logoSrc="logoSrc" /></el-carousel-item>
        <el-carousel-item><previewCard2 :logoSrc="logoSrc" /></el-carousel-item>
        <el-carousel-item><previewCard3 :logoSrc="logoSrc" /></el-carousel-item>
        <el-carousel-item><previewCard4 :logoSrc="logoSrc" /></el-carousel-item>
      </el-carousel>
    </div>

    <!-- 分割线 -->
    <div class="line" />

    <!-- 功能区 -->
    <div class="functionCard">
      <!-- 按钮 -->
      <div v-if="!choosing" class="buttonCard" :style="blurVal">
        <el-button icon="el-icon-edit" @click="toHandwork">前往编辑</el-button>
        <el-button type="primary" icon="el-icon-check" @click="choosing=true">直接保存</el-button>
        <el-button type="text" @click="toHandwork">试试自己制作>></el-button>
      </div>
      <!-- 提示 -->
      <div v-if="!isloggedIn" class="loginTipsCard">
        <span class="tips">登录以进行更多操作</span>
        <el-button type="primary" @click="toLogin">立即登录</el-button>
      </div>
      <!-- 图库 -->
      <div v-if="choosing" class="gelleryCard">
        <div class="titleView">
          <span class="gelleryCardTitle">
            保存到图库
            <i v-if="gelleryListLoading" class="el-icon-loading" />
          </span>
          <button class="refreshBtn" @click="updateGalleryList">
            <i class="el-icon-refresh" />
          </button>
        </div>
        <!-- 渲染区域 -->
        <div class="chosenView">
          <div class="chosenCard" v-for="item in galleryList" :key="item.id">
            <input
              class="chosenCardRadio"
              type="radio"
              :id="item.id"
              :value="item.id"
              v-model="galleryId"/>
            <label class="chosenCardLabel el-icon-folder-opened tips" :for="item.id">
              &nbsp;{{ item.name }}
            </label>
          </div>
        </div>
        <!-- 底部按钮区域 -->
        <div class="funcBtnView">
          <el-button type="primary" size="mini" :loading="saving" :disabled="galleryId==-1" @click="save">保存</el-button>
          <el-button size="mini" @click="choosing=false">返回</el-button>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped>
.previewContainer {
  padding-bottom: 10px;
  padding-left: 20px;
  display: flex;
}

.line {
  margin: 0 30px;
  width: 1px;
  background: #DCDFE6;
  transform: scaleX(0.5);
}

.carouselCard {
  width: 600px;
}

.functionCard {
  width: 150px;
  height: 400px;
  padding-right: 20px;
  display: flex;
  align-items: center;
  justify-content: center;
}

.buttonCard {
  height: 150px;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: space-around;
}

.buttonCard .el-button {
  margin: 0;
}

.loginTipsCard {
  height: 150px;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  position: absolute;
}

.loginTipsCard span {
  margin-bottom: -60px;
}

.gelleryCard {
  height: 100%;
  width: 200px;
}

.gelleryCardTitle {
  height: 30px;
  line-height: 30px;
  margin-left: -15px;
  color: #606266;
  font-size: 14px;
}

.chosenView {
  height: 330px;
  width: 100%;
  overflow: auto;
}

.funcBtnView {
  height: 40px;
  width: 100%;
  display: flex;
  justify-content: center;
  align-items: flex-end;
}

.chosenCardLabel {
  width: 140px;
  height: 40px;
  padding: 0 10px;
  border: 1px solid transparent;
  border-radius: 3px;
  color: #909399;
  display: flex;
  align-items: center;
  cursor: pointer;

  overflow: hidden;
  text-overflow:ellipsis;
  white-space: nowrap;
}

.chosenCardLabel:hover {
  border: 1px solid #DCDFE6;
  background: rgb(245, 245, 245);
}

.chosenCardRadio[type="radio"]:checked+.chosenCardLabel {
  color: #409EFF;
  border: 1px solid rgb(179, 216, 255);
  background: rgb(236, 245, 255);
}

/* 隐藏小圆点 */
.chosenCardRadio[type="radio"] {
  position: absolute;
  clip: rect(0, 0, 0, 0);
}

.titleView {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.refreshBtn {
  border: none;
  background: none;
  font-size: 16px;
  font-weight: bolder;
  cursor: pointer;
}

</style>

<script>
import previewCard1 from './components/previewCard-1'
import previewCard2 from './components/previewCard-2'
import previewCard3 from './components/previewCard-3'
import previewCard4 from './components/previewCard-4'

import store from 'store'
import { dataURLtoFile } from 'utils/imgUtils'
import { getToken } from 'utils/auth'
import { upload, getGalleryInfo, addlogo } from 'api/mylogo'

export default {
  props: ['logoSrc', 'keyword'],

  components: {
    previewCard1,
    previewCard2,
    previewCard3,
    previewCard4
  },

  data () {
    return {
      galleryId: -1,
      gelleryListLoading: false,
      saving: false,
      choosing: false,
      galleryList: []
    }
  },

  computed: {
    blurVal () {
      if (store.state.isloggedIn) {
        return 'filter: blur(0);'
      } else {
        return 'filter: blur(30px);'
      }
    },

    isloggedIn () {
      return store.state.isloggedIn
    }
  },

  mounted () {
    this.updateGalleryList()
  },

  methods: {
    /**
     * 跳转到制作页
     */
    toHandwork () {
      this.$message.warning('暂未开放，敬请期待')
    },

    /**
     * 跳转到登录
     */
    toLogin () {
      this.$router.push({
        path: '/login',
        query: {
          mode: 'login'
        }
      })
    },

    /**
     * 刷新图库列表
     */
    updateGalleryList () {
      this.gelleryListLoading = true

      const params = {
        token: getToken()
      }
      getGalleryInfo(params).then((res) => {
        // ————成功回调
        const data = res.data
        switch (data.code) {
          case 200:
            this.galleryList = data.logoList
            break
        }
      }).catch((err) => {
      }).finally(() => {
        this.gelleryListLoading = false
      })
    },

    /**
     * 保存到图库
     */
    save () {
      this.saving = true

      const params = new FormData()
      const file = dataURLtoFile(this.logoSrc)

      params.append('file', file)
      upload(params).then((res) => {
        // ————成功回调
        const data = res.data
        let params2
        switch (data.code) {
          case 200:
            params2 = {
              token: getToken(),
              galleryid: this.galleryId,
              name: this.keyword,
              author: store.state.userInfo.username,
              imgUrl: data.url
            }

            return addlogo(params2)
        }
      }).then((res) => {
        // ————成功回调
        const data = res.data
        switch (data.code) {
          case 200:
            this.$emit('saveSuccess')
            this.choosing = false
            this.$message.success('保存成功')
            break
        }
      }).catch((err) => {
        // ————失败回调
        this.$message.error('保存失败，请重试')
      }).finally(() => {
        this.saving = false
      })
    }
  }
}
</script>
