import Vue from 'vue'
import App from './App.vue'
import axios from 'axios'

/* 阻止启动生产信息 */
Vue.config.productionTip = false

/* 配置axios请求的根路径 */
axios.defaults.baseURL = 'http://127.0.0.1:3333/'

/* 将axios挂载到vue的原型上 */
Vue.prototype.$http = axios

new Vue({
  render: h => h(App)
}).$mount('#app')
