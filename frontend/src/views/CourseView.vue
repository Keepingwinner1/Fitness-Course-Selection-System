<template>
  <img src="../assets/images/sport.png" alt=""
       style="width: 100%;top: 0;position:absolute; z-index: -1;">
  <NavigationBar/>
  <div class="container">
    <el-page-header style="border-radius:15px;position:absolute;top:3%;left:2%;" @back="()=>{router.back()}">
      <template #breadcrumb>
        <el-breadcrumb separator="/" style="font-size: 15px;">
          <el-breadcrumb-item :to="{path: '/courses'}">
            课程列表
          </el-breadcrumb-item>
          <el-breadcrumb-item>
            {{course.courseId}}
          </el-breadcrumb-item>
        </el-breadcrumb>
      </template>
      <template #title>
        <h3 style="font-size: 20px;font-weight: 700;">
          返回
        </h3>
      </template>
      <template #content>
        <h3 style="font-size: 20px;font-weight: 700;">
          课程界面
        </h3>
      </template>
    </el-page-header>
    <div class="courseIntroduction">
      <img class="courseImg" src="../assets/images/cycling.png" alt="">
      <div class="description">
        <h3 style="font-size: 35px;font-weight: 700;">
          {{course.courseName}}
        </h3>
        <h3 style="font-size: 30px;">
          课程类型：{{course.courseType}}
        </h3>
        <h3 style="font-size: 30px;">
          课程评分：{{course.courseGrade}}
        </h3>
        <h3 style="font-size:30px;">
          课程ID:{{course.courseId}}
        </h3>
      </div>
    </div>
    <div class="content">
      <el-row style="margin-top:30px;">
        <el-col :span="21">
        <span style="font-size:25px;font-weight:700;margin-left: 30px">
          班级列表
        </span>
        </el-col>
        <el-col :span="3">
          <el-button type="primary" @click="openClass">
            开设班级
          </el-button>
        </el-col>
      </el-row>
      <el-card class="classContainer" v-for="c in classes" shadow="hover" @click="gotoClass(c.classId)">
        <template #header>
          <div>
                <span style="font-size: 20px;background-color: pink;border-radius: 10px; align-content: center;">
                  <el-icon style="top:3px;">
                    <Document/>
                  </el-icon>
                  班级
                </span>
            <span style="font-size:30px;font-weight:700;margin-left:20px;">
                    {{c.classId}}
              </span>
          </div>
        </template>
        <p style="font-size:20px;font-weight: 500;">
          班级容量：{{c.capacity}}
        </p>
        <p style="font-size:20px;font-weight: 500;">
          价格：{{c.coursePrice}}
        </p>
        <span style="font-size:20px;font-weight: 500;">
          上课时间:
          <el-tag
              v-if="c.dayOfWeek!=null"
              v-for="d in c.dayOfWeek.split('#')"
              type="primary"
              effect="light"
              style = "margin:5px;"
          >
            <h2 v-if="d=='0'" style="margin-top: 1px;margin-bottom: 1px">周日</h2>
            <h2 v-if="d=='1'" style="margin-top: 1px;margin-bottom: 1px">周一</h2>
            <h2 v-if="d=='2'" style="margin-top: 1px;margin-bottom: 1px">周二</h2>
            <h2 v-if="d=='3'" style="margin-top: 1px;margin-bottom: 1px">周三</h2>
            <h2 v-if="d=='4'" style="margin-top: 1px;margin-bottom: 1px">周四</h2>
            <h2 v-if="d=='5'" style="margin-top: 1px;margin-bottom: 1px">周五</h2>
            <h2 v-if="d=='6'" style="margin-top: 1px;margin-bottom: 1px">周六</h2>
          </el-tag>
        </span>
        <template #footer>
          <span>
            开始日期：{{c.courseStartTime}}
          </span>
          <span style="margin-left: 200px;">
            结束日期：{{c.courseEndTime}}
          </span>
        </template>
      </el-card>
    </div>
    <el-backtop :right="100" :bottom="100"/>
    <el-dialog v-model="dialogFlag" style="width:500px;">
      <template #header>
        <h2 style="font-weight: 700">
          请输入班级信息
        </h2>
      </template>
      <el-form :model="classForm" label-position="left" label-width="80px">
        <el-form-item label="班级容量" required>
          <el-input v-model="classForm.capacity" placeholder="请输入班级容量"/>
        </el-form-item>
        <el-form-item label="价格" required>
          <el-input v-model="classForm.coursePrice" placeholder="请输入价格"/>
        </el-form-item>
        <el-form-item label="开始时间" required>
          <el-date-picker v-model="classForm.beginTime" placeholder="请选择开始时间"/>
        </el-form-item>
        <el-form-item label="结束时间" required>
          <el-date-picker v-model="classForm.endTime" placeholder="请选择结束时间"/>
        </el-form-item>
        <el-form-item label="开课时间" required>
          <el-checkbox-group v-model="classForm.dayOfWeek" size="default">
            <el-checkbox-button value="0">
              周日
            </el-checkbox-button>
            <el-checkbox-button value="1">
              周一
            </el-checkbox-button>
            <el-checkbox-button value="2">
              周二
            </el-checkbox-button>
            <el-checkbox-button value="3">
              周三
            </el-checkbox-button>
            <el-checkbox-button value="4">
              周四
            </el-checkbox-button>
            <el-checkbox-button value="5">
              周五
            </el-checkbox-button>
            <el-checkbox-button value="6">
              周六
            </el-checkbox-button>
          </el-checkbox-group>
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="dialogFlag = false">取消</el-button>
        <el-button type="primary" @click="publishClass">
          确定
        </el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup lang="ts">
import router from "../router"
import NavigationBar from "../components/NavigationBar.vue";
import {onBeforeMount, onUnmounted, ref} from "vue";
import {Document} from "@element-plus/icons-vue";
import {message} from "ant-design-vue";
import {useRoute} from "vue-router";
import {createClass, getClassByCourseID, getCourseByCourseId} from "../api";
import {useFitStore} from "../store";
import {checkAvailable} from "../mixins/checkLoginState";

const route = useRoute()
let courseId = Number(route.params["courseId"])
let course = ref()

let classForm=ref({
  capacity:null,
  coursePrice:null,
  beginTime:null,
  endTime:null,
  dayOfWeek:[],
})

let dialogFlag=ref(false)

let classes=ref()


function openClass(){
  dialogFlag.value=true
}
async function publishClass(){
  dialogFlag.value=false
  if(classForm.value.capacity>0&&classForm.value.coursePrice>0&&classForm.value.endTime>classForm.value.beginTime&&classForm.value.dayOfWeek!=null){
    let result = classForm.value.dayOfWeek.join('#');
    const data = await createClass(useFitStore().coachID,courseId,classForm.value.capacity,classForm.value.coursePrice,classForm.value.beginTime,classForm.value.endTime,result)
    if(data){
      message.success("班级已提交审核")
    }
    else{
      message.error("班级开设失败")
    }
  }
  else{
    message.error("信息填写有误")
  }
}

function gotoClass(classID:number){
  console.log("courseId=",courseId)
  router.push({path:`/classes/${classID}`,query:{courseId:courseId}})
}
onBeforeMount(async()=>{
  checkAvailable()
  course.value = await getCourseByCourseId(courseId)
  classes.value = await getClassByCourseID(courseId)
})

const refresh =setInterval(async ()=>{
  course.value = await getCourseByCourseId(courseId)
  classes.value=await getClassByCourseID(courseId)
},20000)

const checkLogIn=setInterval(()=>{
  checkAvailable()
},100000)
onUnmounted(()=>{
  clearInterval(refresh)
  clearInterval(checkLogIn)
})
</script>

<style scoped>
.courseImg{
  height: 250px;
  border-radius: 15px;
  box-shadow: 0 0 10px black;
}
.description{
  position:absolute;
  left: 130%;
  top: -1%;
  width:100%;
}
.courseIntroduction{
  position:absolute;
  left:20%;
  top:10%;
}
.container{
  justify-content: left;
  text-align: left;
  position: absolute;
  top: 10%;
  width: 80%;
  left: 10%;
  min-height:100%;
  background-color: whitesmoke;
  border-radius: 15px;
}
.content{
  position:absolute;
  top:45%;
  width:100%;
  min-height:80%;
  background-color: white;
  border-radius: 15px;
  border: 1px solid black
}
.classContainer{
  margin-left: 1.5%;
  margin-right:1.5%;
  margin-top:1%;
  border-radius: 15px;
}
:deep() .taskTitle{
  font-size:30px;
  font-weight:700;
  margin-left:20px;
  height: 40px;
}
</style>