import Vue from 'vue'
import Vuex from 'vuex'
import { getToken } from '../utils/auth'

Vue.use(Vuex)

export default new Vuex.Store({
  state: {
    navBarHidden: false
  },
  mutations: {
    setNavBarHidden (navBarHidden, flag) {
      navBarHidden.navBarHidden = flag
    }
  },
  getters: {
    /**
     * 判断是否已登录（localStorage中有token即为已登录）
     */
    isloggedIn () {
      if (getToken()) {
        return true
      } else {
        return false
      }
    }
  },
  actions: {
  },
  modules: {
  }
})
