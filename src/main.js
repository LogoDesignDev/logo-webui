import Vue from 'vue'
import nav from './views/nav/navIndex.vue'
import router from './router'
import store from './store'
import ElementUI from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'

// 全局样式
import './styles/reset.css'
import './styles/fontStyle.css'
import './styles/userPicStyle.css'

// iconfont
import './assets/iconfont/iconfont.css'
import './assets/iconfont/iconfont.js'
import iconfont from './components/iconfont'
Vue.component('iconfont', iconfont)

Vue.config.productionTip = false
Vue.use(ElementUI)

new Vue({
  router,
  store,
  render: h => h(nav)
}).$mount('#app')
