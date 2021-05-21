import '@fortawesome/fontawesome-free/css/all.min.css'
import 'bootstrap-css-only/css/bootstrap.min.css'
import 'mdbvue/lib/css/mdb.min.css'
import Vue from 'vue'
import App from './App.vue'
import vuetify from './plugins/vuetify';
import { store } from "./store"
import router from './routes'
import { BootstrapVue, IconsPlugin } from 'bootstrap-vue'
import 'bootstrap/dist/css/bootstrap.css'
import 'bootstrap-vue/dist/bootstrap-vue.css'
import Delay from 'vue-delay'
import VueGeolocationApi from 'vue-geolocation-api'
import 'bootstrap-css-only/css/bootstrap.min.css'
import 'mdbvue/lib/css/mdb.min.css'
import '@fortawesome/fontawesome-free/css/all.min.css'
import VueCookies from "vue-cookies";


window.Kakao.init("c626463e9b79a84d5a8185d060679ef3");

Vue.use(Delay)
Vue.use(BootstrapVue)
Vue.use(IconsPlugin)
Vue.use(VueGeolocationApi/*, { ...options } */)
Vue.config.productionTip = false
Vue.use(VueCookies)
Vue.$cookies.config("7d")


new Vue({
  vuetify,
  store: store,
  router,
  render: h => h(App)
}).$mount('#app')
