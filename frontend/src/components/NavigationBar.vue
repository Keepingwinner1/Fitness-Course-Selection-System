<template>
  <div :class="['head_con', navBarFixed ? 'navBarWrap' : '']">
    <div class="logo-container">
      <img src="../assets/images/logo.png" alt="FitFit" class="logo">
    </div>
    <div class="wrapper">
      <nav>
        <input type="radio" name="tab" id="home" :checked="$route.path === '/home' || $route.path === '/'">
        <input type="radio" name="tab" id="shop" :checked="$route.path === '/shop'|| $route.path.startsWith('/shop/')">
        <input type="radio" name="tab" id="calendar" :checked="$route.path === '/calendar'">
        <input type="radio" name="tab" id="courses" :checked="$route.path .startsWith('/courses')||$route.path.startsWith('/classes')">
        <input type="radio" name="tab" id="shoppingCart" :checked="$route.path === '/shoppingCart'">
        <input type="radio" name="tab" id="history" :checked="$route.path === '/history' || $route.path.startsWith('/history/')">

        <label for="home" class="home" @click="delayedNavigation('/home')">
          <router-link to="/home" style="font-weight: bold;">
            <el-icon>
              <House style="position: relative; top: 3px;" />
            </el-icon>
            首页
          </router-link>
        </label>
        <label for="shop" class="shop" @click="delayedNavigation('/shop')">
          <router-link to="/shop" style="font-weight: bold;">
            <el-icon>
              <Shop style="position: relative; top: 3px;" />
            </el-icon>
            课程商店
          </router-link>
        </label>
        <label for="calendar" class="calendar" @click="delayedNavigation('/calendar')">
          <router-link to="/calendar" style="font-weight: bold;">
            <el-icon>
              <Calendar style="position: relative; top: 3px;" />
            </el-icon>
            课程日历
          </router-link>
        </label>
        <template v-if="store.role==='user'">
          <label for="courses" class="courses" @click="delayedNavigation('/classes')">
            <router-link to="/classes" style="font-weight: bold;">
              <el-icon style="position: relative; top: 3px;">
                <Notebook />
              </el-icon>
              我的课程
            </router-link>
          </label>
        </template>
        <template v-else>
          <label for="courses" class="courses" @click="delayedNavigation('/courses')">
            <router-link to="/courses" style="font-weight: bold;">
              <el-icon style="position: relative; top: 3px;">
                <Notebook />
              </el-icon>
              我的课程
            </router-link>
          </label>
        </template>
        <label for="shoppingCart" class="shoppingCart" @click="delayedNavigation('/shoppingCart')">
          <router-link to="/shoppingCart" style="font-weight: bold;">
            <el-icon style="position: relative; top: 3px;">
              <ShoppingCart />
            </el-icon>
            我的购物车
          </router-link>
        </label>
        <label for="history" class="history" @click="delayedNavigation('/history')">
          <router-link to="/history" style="font-weight: bold;">
            <el-icon style="position: relative; top: 3px;">
              <Clock />
            </el-icon>
            购买记录
          </router-link>
        </label>
        <div class="tab"></div>
      </nav>
    </div>
    <div class="avatar-container">
      <template v-if="store.token">
        <el-dropdown>
          <img :src="store.iconURL || defaultAvatar" alt="User" class="dropdownlink">
          <template #dropdown>
            <el-dropdown-menu>
              <el-dropdown-item @click="navigateToUserProfile">
                <el-icon>
                  <Setting />
                </el-icon>
                个人资料
              </el-dropdown-item>
              <el-dropdown-item v-if="store.role === 'admin'" @click="navigateToAdminPanel">
                <el-icon>
                  <Tools />
                </el-icon>
                管理界面
              </el-dropdown-item>
              <el-dropdown-item @click="navigateToLoginOut">
                <el-icon>
                  <Switch />
                </el-icon>
                退出登录
              </el-dropdown-item>
            </el-dropdown-menu>
          </template>
        </el-dropdown>
      </template>
      <template v-else>
        <el-button type="primary" class="round-button" @click="navigateToLoginOut">登录/注册</el-button>
      </template>
    </div>
  </div>
</template>

<script setup>
import router from "../router/index";
import {
  Calendar,
  Clock,
  House,
  Notebook, Setting,
  Shop,
  ShoppingCart, Switch, Tools
} from "@element-plus/icons-vue";

import defaultAvatar from "../assets/images/default-avatar.png"
import {useFitStore} from "../store/index";
import {ref} from "vue";

const store = useFitStore()
let navBarFixed=ref(false)
let checkLoginInterval=ref(null)  // 定时器ID
function delayedNavigation(target) {
  setTimeout(() => {
    router.push(target);
  }, 500);
}
function navigateToUserProfile() {
  const userID = store.userID;
  router.push(`/user/${userID}`);
}
function navigateToLoginOut() {
  router.push(`/login`);
}
function checkLoginStatus() {
  // 获取当前路径
  const currentPath = $route.path;
  // // 如果当前路径是 '/home' 或 '/'，则不进行检查
  if (currentPath === '/home' || currentPath === '/') {
    return;
  }
  this.checkAvailable()
}
function navigateToAdminPanel() {
  router.push('/admin');
}
</script>

<style scoped>
* {
    margin: 0;
    padding: 0;
    box-sizing: border-box;
}

body {
    height: 100vh;
    display: flex;
    justify-content: center;
    align-items: flex-start;
    text-align: center;
    background: #fff;
}

.head_con {
    display: flex;
    align-items: center;
    text-align: center;
    position: absolute;
    top: 3%;
    left: 0;
    width: 100%;
    padding-left: 5%;
    padding-right: 5%;
}

.logo-container {
    margin-right: 0px;
}

.logo {
    height: 50px;
    display: block;
}

.wrapper,
.avatar-container {
    height: 50px;
    display: flex;
    align-items: center;
    margin-left: auto;
}

.wrapper {
    width: 60vw;
    line-height: 50px;
    background-color: white;
    box-shadow: 0px 5px 15px rgba(0, 0, 0, 0.25);
    border-radius: 50px;
}

.wrapper nav {
    display: flex;
    flex: 1;
    position: relative;
}

.wrapper nav label {
    flex: 1;
    width: 100%;
    position: relative;
    z-index: 1;
    cursor: pointer;
}

.wrapper nav label a {
    position: relative;
    z-index: -1;
    color: #333;
    font-size: 1vw;
    font-weight: 500;
    text-decoration: none;
}

.wrapper nav input {
    display: none;
}

.wrapper nav .tab {
    position: absolute;
    height: 100%;
    width: 16.67%;
    left: 0px;
    bottom: 0px;
    background: linear-gradient(to right, #f09819, #ff5858);
    border-radius: 50px;
    transition: 0.6s cubic-bezier(0.68, -0.55, 0.265, 1.55);
}

.wrapper nav #home:checked~label.home a,
.wrapper nav #shop:checked~label.shop a,
.wrapper nav #calendar:checked~label.calendar a,
.wrapper nav #courses:checked~label.courses a,
.wrapper nav #shoppingCart:checked~label.shoppingCart a,
.wrapper nav #history:checked~label.history a {
    color: #fff;
    transition: 0.6s;
}

.wrapper nav #shop:checked~.tab {
  left: 16.7%;
}

.wrapper nav #calendar:checked~.tab {
  left: 33.4%;
}

.wrapper nav #courses:checked~.tab {
  left: 50.1%;
}

.wrapper nav #shoppingCart:checked~.tab {
    left: 66.8%;
}

.wrapper nav #history:checked~.tab {
  left: 83.5%;
}

.round-button {
  width: 100px;
  height: 50px;
  padding: 0;
  display: flex;
  justify-content: center;
  align-items: center;
  font-size: 16px;
  /* 根据需要调整字体大小 */
  color: #fff;
  border: none;
  /* 去除默认边框 */
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
  /* 添加轻微的阴影效果 */
  cursor: pointer;
}

.avatar-container {
    position: relative;
    display: flex;
    align-items: center;
    height: 50px;
    border: none;
}

.dropdownlink {
    width: 50px;
    height: 50px;
    border-radius: 50%;
    cursor: pointer;
    border: 5px solid #fff;
    box-shadow: 0 0 10px rgba(0, 0, 0, 0.20);
}

.example-showcase .el-dropdown-link {
    cursor: pointer;
    color: var(--el-color-primary);
    display: flex;
    align-items: center;
}

@media (min-width: 1024px) {
    #app {
        display: flex;
        justify-content: center;
        /* 水平居中 */
        align-items: center;
        /* 垂直居中 */
        height: 100vh;
        /* 父容器的高度占满整个视口 */
    }
}

.navBarWrap {
    position: fixed;
    padding-top: 2%;
    padding-bottom: 1%;
    top: 0;
    left: 0;
    width: 100%;
    z-index: 999;
    background-color: transparent;
    /* 添加背景色防止滚动时内容透过导航栏 */
    box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
    /* 添加阴影以区别于内容 */
}

</style>
