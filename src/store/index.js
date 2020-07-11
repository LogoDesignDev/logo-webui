import Vue from 'vue'
import Vuex from 'vuex'
import axios from 'axios'
import { getToken } from '../utils/auth'
import { getUserInfo } from 'api/user'
import { getProdCount, getFansCount, getFollowCount } from 'api/personal'
import { serverPrx } from 'utils/default'

Vue.use(Vuex)

export default new Vuex.Store({
  state: {
    userInfo: {
      uid: '',
      username: '',
      userPicUrl: '',
      prodCount: 0,
      fansCount: 0,
      followCount: 0
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
      const params = {
        token: getToken()
      }
      getUserInfo(params).then((res) => {
        const data = res.data
        const params2 = {
          uid: data.uid
        }
        // 继续请求其他信息
        axios.all([
          getProdCount(params2),
          getFansCount(params2),
          getFollowCount(params2)]).then(axios.spread((
          prodRes, fansRes, followRes, beLikeRes, beCollectedRes) => {
          this.commit('setUserInfo', {
            uid: data.uid,
            username: data.username,
            // 因为后端保存的头像图片的文件名是不会有变化的
            // 所以上传成功后再次拉取的url没有变化
            // 自然不会触发双向绑定，img也不会更新
            // 因此采取在url后加一个随机数的方式触发更新
            userPicUrl: serverPrx + data.userPicUrl + '?' + Math.random(),
            prodCount: prodRes.data.count,
            fansCount: fansRes.data.count,
            followCount: followRes.data.count
          })
        })).catch((err) => {
        })
      }).catch((err) => {
      })
    }
  },
  modules: {
  }
})
