import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import ElementUI from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'
import './assets/common.css'
import axios from 'axios'

axios.defaults.baseURL = 'http://localhost:8090'

Vue.prototype.axios = axios
Vue.config.productionTip = false
Vue.use(ElementUI, { size: 'small' })
// Vue.prototype.$host = 'C:\Users\lenovo\Desktop\计算机设计大赛\图片\许可证.png'

new Vue({
  router,
  store,
  render: h => h(App)
}).$mount('#app')
