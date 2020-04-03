import Vue from 'vue'
import Vuex from 'vuex'

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
  actions: {
  },
  modules: {
  }
})
