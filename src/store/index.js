import Vue from 'vue'
import Vuex from 'vuex'
import { getToken } from '../utils/auth'
import { getUserInfo } from 'api/user'
import { serverPrx } from 'utils/default'

Vue.use(Vuex)

export default new Vuex.Store({
  state: {
    userInfo: {
      uid: '',
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
      // // 因为获取用户信息和头像两个api分开了
      // // 所以要并发请求
      // axios.all([getUserInfo(), getUserPic()]).then(axios.spread((guiRes, gupRes) => {
      //   this.commit('setUserInfo', {
      //     username: guiRes.data.username,
      //     userPicUrl: gupRes.data.userPicUrl
      //   })
      // })).catch((err) => {
      //   Message({
      //     message: '用户信息拉取失败：' + err.message,
      //     type: 'error'
      //   })
      // })
      const params = {
        token: getToken()
      }
      getUserInfo(params).then((res) => {
        const data = res.data
        this.commit('setUserInfo', {
          uid: data.uid,
          username: data.username,
          // 因为后端保存的头像图片的文件名是不会有变化的
          // 所以上传成功后再次拉取的url没有变化
          // 自然不会触发双向绑定，img也不会更新
          // 因此采取在url后加一个随机数的方式触发更新
          userPicUrl: serverPrx + data.userPicUrl + '?' + Math.random()
        })
      }).catch((err) => {
        // Message({
        //   message: '用户信息拉取失败：' + err.message,
        //   type: 'error'
        // })
      })
    }
  },
  modules: {
  }
})
