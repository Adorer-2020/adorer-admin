import {createApp} from "vue";
import {createPinia} from "pinia";

import App from "./App.vue";
import router from "./router";
import ElementPlus from "element-plus";
import locale from "element-plus/lib/locale/lang/zh-cn";

import "element-plus/dist/index.css";
import "@/assets/icon/iconfont.css";
import "@/assets/css/main.css";

const app = createApp(App);

app.use(router);
app.use(ElementPlus, {locale});
app.use(createPinia());

app.mount("#app");
