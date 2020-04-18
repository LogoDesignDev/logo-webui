import Vue from 'vue'
import Vuex from 'vuex'
import axios from 'axios'
import { Message } from 'element-ui'
import { getToken } from '../utils/auth'
import { getUserInfo, getUserPic } from 'api/user'

Vue.use(Vuex)

export default new Vuex.Store({
  state: {
    userInfo: {
      username: '',
      userPicUrl: ''
    },
    navBarHidden: false,
    isloggedIn: Boolean(getToken())
  },
  mutations: {
    setNavBarHidden (state, flag) {
      state.navBarHidden = flag
    },
    setIsloggedIn (state, flag) {
      state.isloggedIn = flag
    },
    setUserInfo (state, data) {
      state.userInfo = data
    }
  },
  getters: {
  },
  actions: {
    /**
     * 获取用户信息（头像、用户名）
     */
    updateUserInfo (state) {
      // 因为获取用户信息和头像两个api分开了
      // 所以要并发请求
      axios.all([getUserInfo(), getUserPic()]).then(axios.spread((guiRes, gupRes) => {
        this.commit('setUserInfo', {
          username: guiRes.data.username,
          userPicUrl: gupRes.data.userPicUrl
        })
      })).catch((err) => {
        Message({
          message: '用户信息拉取失败：' + err.message,
          type: 'error'
        })
      })
    }
  },
  modules: {
  }
})
