import 'bootstrap/dist/css/bootstrap.min.css'
import 'bootstrap'

import { createApp } from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
// import fab from 'vue-fab'

import Datepicker from '@vuepic/vue-datepicker'
import '@vuepic/vue-datepicker/dist/main.css'
import VCalendar from 'v-calendar'
import 'v-calendar/dist/style.css'

// createApp(App).use(store).use(router).mount('#app')
createApp(App).use(store).use(router).use(Datepicker).use(VCalendar).mount('#app')
