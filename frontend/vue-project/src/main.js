import { createApp } from 'vue'
import { createPinia } from 'pinia'

import App from './App.vue'
import router from './router'

export const serverURL = 'http://i10a108.p.ssafy.io:8081/';
export const v1_URL = 'api/v1/';//    api/v1/ 적용되면 바꾸기  

const app = createApp(App)

app.use(createPinia())
app.use(router)

app.mount('#app')
