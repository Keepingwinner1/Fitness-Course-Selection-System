import './assets/main.css'
import { createApp } from 'vue'
import App from './App.vue'
import router from './router'
import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'
import zhCn from 'element-plus/es/locale/lang/zh-cn'
import pinia from './store/index'
import Antd from 'ant-design-vue'; // 引入 Ant Design Vue
import 'ant-design-vue/dist/reset.css'; // 全局样式

const app = createApp(App)
app.use(router)
app.use(pinia)
app.use(ElementPlus, {
  locale: zhCn,
})
app.use(Antd); // 全局注册 Ant Design Vue 的所有组件
app.mount('#app')