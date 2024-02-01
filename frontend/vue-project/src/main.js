import { createApp } from 'vue'
import { createPinia } from 'pinia'

import App from './App.vue'
import router from './router'

export const serverURL = 'http://localhost:8080/';
export const v1_URL = '';//    api/v1/ 적용되면 바꾸기

const app = createApp(App)

app.use(createPinia())
app.use(router)

app.mount('#app')
