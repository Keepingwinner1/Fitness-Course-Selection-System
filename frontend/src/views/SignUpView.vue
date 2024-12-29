<template>
    <div class="background">
        <div class="background-image"></div>
        <div class="login-container">
            <div class="gradient-background"></div>
            <div class="content">
                <div class="title-img"></div>
                <el-card class="login-card">
                    <h2 class="login-title">用户注册</h2>
                    <el-form ref="form" :model="SignUpForm" :rules="rules" label-position="left" label-width="80px">
                      <el-form-item label="用户昵称" prop="name">
                        <el-input v-model="SignUpForm.name"/>
                      </el-form-item>
                      <el-form-item label="用户类型" prop="userType">
                        <el-radio-group v-model="SignUpForm.userType">
                          <el-radio label="user">普通用户</el-radio>
                          <el-radio label="coach">教练</el-radio>
                          <el-radio label="admin">管理员</el-radio>
                        </el-radio-group>
                      </el-form-item>
                      <el-form-item v-if="SignUpForm.userType==='admin'" label="注册方式" prop="adminType">
                        <el-radio-group v-model="SignUpForm.adminType">
                          <el-radio label="joinGYM">加入已有健身房</el-radio>
                          <el-radio label="createGYM">创建新健身房</el-radio>
                        </el-radio-group>
                      </el-form-item>
                      <el-form-item v-if="SignUpForm.userType==='admin'&&SignUpForm.adminType==='joinGYM'" label="健身房ID" prop="gymId">
                        <el-input v-model="SignUpForm.gymId"/>
                      </el-form-item>
                      <el-form-item v-if="SignUpForm.userType==='admin'&&SignUpForm.adminType==='createGYM'" label="健身房名" prop="gymName">
                        <el-input v-model="SignUpForm.gymName"/>
                      </el-form-item>
                      <el-form-item v-if="SignUpForm.userType==='admin'&&SignUpForm.adminType==='createGYM'" label="地址" prop="gymAddress">
                        <el-input v-model="SignUpForm.gymAddress"/>
                      </el-form-item>
                      <el-form-item label="密码" prop="password">
                        <el-input v-model="SignUpForm.password" show-password/>
                      </el-form-item>
                      <el-form-item label="确认密码" prop="verifyPwd">
                        <el-input v-model="SignUpForm.verifyPwd" show-password/>
                      </el-form-item>
                    </el-form>
                      <el-form-item>
                        <el-button type="primary" class="return-button" @click="returnLoginView">返回</el-button>
                        <el-button type="primary" class="login-button" @click="SignUp">完成注册</el-button>
                      </el-form-item>

                </el-card>
            </div>
        </div>
    </div>
</template>

<script setup lang="ts">
import {Register} from "../api/index";
import {message} from "ant-design-vue";
import {reactive, ref} from "vue";
import {FormInstance} from "element-plus";
import {FormRules} from "element-plus";
import router from "../router/index"
interface signUpForm{
  name:string,
  password:string,
  verifyPwd:string,
  userType:string,
  adminType:string,
  gymId:number,
  gymName:string,
  gymAddress:string
}
const SignUpForm = ref<signUpForm>({
  name: '',
  password: '',
  verifyPwd: '',
  userType: 'user',
  adminType: 'joinGYM',
  gymId: null,
  gymName: '',
  gymAddress: ''
})
const form = ref<FormInstance>()

const rules=reactive<FormRules<signUpForm>>({
  password: [
    { required: true, message: '请输入密码', trigger: 'blur' },
    { min: 6, max: 16, message: '密码长度为6-16位', trigger: 'blur' },
  ],
  verifyPwd: [
    { required: true, message: '请确认密码', trigger: 'blur' },
    {
      trigger: 'blur',
      validator: (rule, value, callback) => {
        if (value === '') callback(new Error("请确认密码"));
        else {
          if (value !== SignUpForm.value.password) callback(new Error("密码不一致"));
          else callback();
        }
      }
    }
  ],
  name: [
    { required: true, message: '请输入昵称', trigger: 'blur' },
    { min: 0, max: 20, message: '昵称过长', trigger: 'blur' },
  ],
  userType: [
    { required: true, message: '请选择用户类型', trigger: 'change' }
  ],
  adminType:[
    {required:true,message:'请选择管理员注册方式',trigger: 'change'}
  ],
  gymId:[
    {required:true,message:'请输入待加入健身房的ID',trigger:'blur'},
  ],
  gymName:[
    {required:true,message:'请输入待创建健身房名称',trigger:'blur'},
    { min: 0, max: 20, message: '名称过长', trigger: 'blur' },
  ],
  gymAddress:[
    {required:true,message:'请输入待创建健身房地址',trigger:'blur'}
  ]
})

function SignUp() {
  form.value.validate(async(valid)=>{
    let user={}
    if (valid) {
      if (SignUpForm.value.userType === 'user'||SignUpForm.value.userType === 'coach') {
        user = await Register(SignUpForm.value.name, SignUpForm.value.password,SignUpForm.value.userType)
      }
      else{
        if(SignUpForm.value.adminType=="joinGYM"){
          user = await Register(SignUpForm.value.name, SignUpForm.value.password,SignUpForm.value.userType,SignUpForm.value.gymId)
        }
        else{
          user = await Register(SignUpForm.value.name, SignUpForm.value.password,SignUpForm.value.userType,null,SignUpForm.value.gymName,SignUpForm.value.gymAddress)
        }
      }
      if (user) {
        message.success("注册成功")
        router.back()
      } else {
        message.error("注册失败")
        resetForm()
      }
    }
    else {
      message.error("输入信息有误，请重新输入")
      resetForm()
    }
  })
}
function resetForm() {
  SignUpForm.value={
    name: '',
    password: '',
    verifyPwd: '',
    userType: 'user',
    adminType: 'joinGYM',
    gymId: null,
    gymName: '',
    gymAddress: ''
  }
}

function returnLoginView() {
  router.go(-1);
}
</script>

<style scoped>
.background {
    display: flex;
    width: 100%;
    height: 100vh;
}

.background-image {
    background-image: url('../assets/images/background.jpg');
    background-size: cover;
    background-position: center;
    width: 70%;
    height: 100%;
}

.title-img {
    background-image: url('../assets/images/login_signup.jpg');
    background-size: contain;
    background-repeat: no-repeat;
    background-position: center;
    width: 100%;
    height: auto;
    aspect-ratio: 16 / 9;
}

.login-container {
    width: 40%;
    height: 100%;
    display: flex;
    justify-content: center;
    align-items: center;
    background-color: white;
}

.content {
    width: 80%;
    border-radius: 8px;
}

.title {
    margin-top: 20px;
    margin-bottom: 20px;
    text-align: center;
    font-size: 3.5rem;
    font-weight: bold;
    font-family: 'PingFang SC', sans-serif;
    color: rgb(44, 225, 44);
}

.login-card {
    border: none;
    box-shadow: none;
    padding: 20px;
    border-radius: 30px;
}

.login-title {
    text-align: center;
    margin-bottom: 20px;
    color: #333;
}

.tabs {
    margin-top: 20px;
}

.code-button-container {
    width: 100px;
    /* 确保容器宽度与父元素一致 */
    display: flex;
    justify-content: center;
}

.code-button {
    width: 100px;
    /* 确保按钮宽度与容器一致 */
}

.return-button {
    width: 16%;
    background-color: #409EFF;
    margin-left: 0;
    margin-right: 5px;
}

.login-button {
    width: 79%;
    background-color: #409EFF;
    color: #fff;
    border-radius: 5px;
    margin-right: 0;
}

.login-button:hover {
    background-color: #66b1ff;
}
</style>
