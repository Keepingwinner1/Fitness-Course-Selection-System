<template>
    <div class="background">
        <div class="background-image"></div>
        <div class="login-container">
            <div class="content">
                <div class="title-img"></div>
                <!--                <h1 class="title">FitFit</h1>-->
                <!--                <h3 class="sub-title">你的智能健身管家</h3>-->
                <el-card class="login-card">
                    <h2 class="login-title">登录</h2>
                    <el-tabs v-model="activeName" class="tabs">
                        <el-tab-pane label="用户登录" name="user">
                            <el-form ref="form" :model="LogInForm" :rules="rules" label-position="left" label-width="70px">
                                <el-form-item label="身份" prop="role" required>
                                    <el-radio-group v-model="LogInForm.role">
                                        <el-radio label="user">普通用户</el-radio>
                                        <el-radio label="coach">教练</el-radio>
                                        <el-radio label="admin">健身房管理员</el-radio>
                                    </el-radio-group>
                                </el-form-item>
                                <el-form-item label="名称" prop="name">
                                    <el-input v-model="LogInForm.name" />
                                </el-form-item>
                                <el-form-item label="密码" prop="password">
                                    <el-input v-model="LogInForm.password" type="password" show-password />
                                </el-form-item>
                                <el-row justify="center">
                                    <el-col>
                                        <el-form-item>
                                            <el-button type="primary" class="login-button"
                                                @click="LogIn">登录</el-button>
                                        </el-form-item>
                                    </el-col>
                                </el-row>
                                <el-row justify="end">
                                    <el-col :span="10">
                                        <el-link type="primary" @click="signUp">没有账号？注册一个</el-link>
                                    </el-col>
                                </el-row>
                            </el-form>
                        </el-tab-pane>
                    </el-tabs>
                </el-card>
            </div>
        </div>
    </div>
</template>

<script setup lang="ts">
import pinia from "../store/index";
import {FormInstance, FormRules} from "element-plus";
import {reactive, ref} from "vue";
import {userLogIn, coachLogIn,adminLogIn} from "../api/index";
import {message} from "ant-design-vue";
import router from "../router/index";
import {useFitStore} from "../store/index";
let activeName = "user"
interface logInForm{
  name: '',
  password: '',
  role: 'user' // 默认值设为用户登录
}
let LogInForm = ref<logInForm>({
  name: '',
  password: '',
  role:'user'
})
let store=useFitStore()
const form = ref<FormInstance>()

const rules=reactive<FormRules<logInForm>>({
  name: [
    { required: true, message: '请输入昵称', trigger: 'blur' },
    { min: 0, max: 20, message: '昵称过长', trigger: 'blur' },
  ],
  password: [
    {required: true, message: '请输入密码', trigger: 'blur'},
    {min: 5, max: 16, message: '密码长度为5-16位', trigger: 'blur'}
  ]
})
function LogIn() {
  form.value.validate(async(valid)=>{
    if (valid) {
      if(LogInForm.value.role=='user') {
        const user = await userLogIn(LogInForm.value.name, LogInForm.value.password)
        if (user) {
          message.success("登陆成功")
          store.role='user'
          store.userID=user.userID
          store.iconURL=user.iconURL
          store.token=user.token
          await router.replace('/home')
        }
        else {
          message.error("账号或密码错误，登录失败")
        }
      }
      else if(LogInForm.value.role=='coach'){
        const coach = await coachLogIn(LogInForm.value.name, LogInForm.value.password)
        if (coach) {
          message.success("登陆成功")
          store.role='coach'
          store.userID=coach.userID
          store.coachID=coach.coachID
          store.iconURL=coach.iconURL
          store.token=coach.token
          store.gymID=coach.gymID
          await router.replace('/home')
        }
        else {
          message.error("账号或密码错误，登录失败")
        }
      }
      else{
        const admin = await adminLogIn(LogInForm.value.name, LogInForm.value.password)
        if (admin) {
          message.success("登陆成功")
          store.role='admin'
          store.userID=admin.userID
          store.iconURL=admin.iconURL
          store.token=admin.token
          store.gymID=admin.gymID
          await router.replace('/admin')
        }
        else {
          message.error("账号或密码错误，登录失败")
        }
      }
    }
    else {
      message.error("输入信息有误，请重新输入")
    }
  })
}

function signUp() {
  router.push("/signup");
}
</script>

<style scoped>
.background {
    display: flex;
    width: 100%;
    height: 100vh;
    overflow-x: auto;
}

.background-image {
    background-image: url('../assets/images/background.jpg');
    background-size: cover;
    /* 确保背景图片填充整个容器 */
    background-position: center;
    width: 70%;
    /* 背景图片区域占左边 80% */
    height: 100%;
}

.title-img {
    background-image: url('../assets/images/login_signup.jpg');
    background-size: contain;
    /* 使图片在容器内保持比例 */
    background-repeat: no-repeat;
    /* 防止图片重复 */
    background-position: center;
    /* 将图片居中显示 */
    width: 100%;
    /* 设置容器宽度 */
    height: auto;
    /* 高度自动，取决于容器内容 */
    aspect-ratio: 16 / 9;
    /* 或者你可以设置一个固定的宽高比 */
}



.login-container {
    width: 40%;
    /* 登录容器占右边 20% */
    height: 100%;
    /* 使容器占满整个视口高度 */
    display: flex;
    justify-content: center;
    align-items: center;
    background-color: white;
    /* 设置背景颜色或保持透明 */
    ;
}

.content {
    width: 70%;
    /* 设置内容区域的宽度，可以根据需求调整 */
    border-radius: 8px;
    /* 圆角 */
}

.title {
    margin-top: 20px;
    margin-bottom: 0px;
    text-align: center;
    font-size: 3.5rem;
    font-weight: bold;
    font-family: 'PingFang SC', sans-serif;
    color: rgb(68, 177, 25);
    /* 标题颜色 */
}

.sub-title {
    margin-top: 0px;
    margin-bottom: 20px;
    text-align: center;
    font-size: 2rem;
    font-weight: bold;
    font-family: 'PingFang SC', sans-serif;
    color: rgb(68, 177, 25);
    /* 标题颜色 */
}

.login-card {
    padding: 10px;
    border: none;
    /* 去除边框 */
    box-shadow: none;
    /* 去除阴影 */
    border-radius: 30px;
}

.login-title {
    text-align: center;
    margin-bottom: 20px;
    color: #333;
    /* 登录标题颜色 */
}

.tabs {
    margin-top: 20px;
}

.login-button {
    width: 100%;
    background-color: #409EFF;
    /* 设置按钮颜色 */
    color: #fff;
    border-radius: 5px;
}

.login-button:hover {
    background-color: #66b1ff;
    /* 悬停效果 */
}
</style>