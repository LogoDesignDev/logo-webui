import Vue from 'vue'
import Vuex from 'vuex'
import { getToken } from '../utils/auth'

Vue.use(Vuex)

export default new Vuex.Store({
  state: {
    navBarHidden: false,
    isloggedIn: Boolean(getToken())
  },
  mutations: {
    setNavBarHidden (state, flag) {
      state.navBarHidden = flag
    },
    setIsloggedIn (state, flag) {
      state.isloggedIn = flag
    }
  },
  getters: {
  },
  actions: {
  },
  modules: {
  }
})
