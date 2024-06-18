import App from './App'

// #ifndef VUE3
import Vue from 'vue'
import './uni.promisify.adaptor'
import commonAdminTabBar from "@/pages/common/commonAdminTabBar.vue"
import commonUserTabBar from "@/pages/common/commonUserTabBar.vue"
Vue.component('commonAdminTabBar', commonAdminTabBar)
Vue.component('commonUserTabBar', commonUserTabBar)

Vue.config.productionTip = false
App.mpType = 'app'
const app = new Vue({
  ...App
})
app.$mount()
// #endif

// #ifdef VUE3
import { createSSRApp } from 'vue'
export function createApp() {
  const app = createSSRApp(App)
  return {
    app
  }
}
// #endif