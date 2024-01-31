import { createApp } from 'vue'
import { createPinia } from 'pinia'

import App from './App.vue'
import router from './router'

export const serverURL = 'http://i10a108.p.ssafy.io:8080/';

const app = createApp(App)

app.use(createPinia())
app.use(router)

app.mount('#app')
