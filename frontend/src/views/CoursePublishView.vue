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
            发布课程
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
          发布课程
        </h3>
      </template>
    </el-page-header>
    <div class="courseIntroduction">
      <el-upload class="courseImg" :on-change="handleSuccess">
<!--        <img v-if="newCourse.coursePhotoUrl" :src="newCourse.coursePhotoUrl" alt="">-->
        <el-icon class="uploadIcon"><Plus /></el-icon>
      </el-upload>
      <div class="description">
        <h3 style="font-size: 35px;font-weight: 700;">
          课程名称：{{newCourse.courseName}}
        </h3>
        <h3 style="font-size: 30px;">
          课程类型:{{newCourse.courseType}}
        </h3>
        <h3 style="font-size: 30px;">
          课程评分:0
        </h3>
      </div>
    </div>
    <div class="content">
        <el-descriptions size="large" style="width:96%;margin-left:2%;margin-right:2%;margin-top:10px" border>
          <template #title>
            <h2 style="font-weight: 600">
              详情列表
            </h2>
          </template>
          <template #extra>
            <el-button v-if="editFlag===false" type="primary" @click="()=>{editFlag=!editFlag;}" style="margin-left:20px;margin-top:15px;">
              <el-icon>
                <EditPen/>
              </el-icon>
              编辑课程
            </el-button>
            <el-button  v-if="editFlag===false" type="primary" @click="publish" style="margin-left:20px;margin-top:15px;">
              发布课程
            </el-button>
            <el-button v-if="editFlag===true" type="success" @click="()=>{editFlag=!editFlag;}" style="margin-left:20px;margin-top:15px;">
              <el-icon>
                <EditPen/>
              </el-icon>
              保存
            </el-button>
          </template>
          <el-descriptions-item width="15%">
            <template #label>
              <h2>课程ID</h2>
            </template>
            待定
          </el-descriptions-item>
          <el-descriptions-item width="15%">
            <template #label>
              <h2>课程名称</h2>
            </template>
            <el-input v-if="editFlag" v-model="newCourse.courseName" placeholder="请输入课程名称"/>
            <span v-else>{{newCourse.courseName}}</span>
          </el-descriptions-item>
          <el-descriptions-item width="15%">
            <template #label>
              <h2>课程类型</h2>
            </template>
            <el-select v-if="editFlag" v-model="newCourse.courseType" placeholder="请选择类型">
              <el-option label="增肌" value="增肌"/>
              <el-option label="减脂" value="减脂"/>
              <el-option label="塑形" value="塑形"/>
              <el-option label="体能提升" value="体能提升"/>
            </el-select>
            <span v-else>{{newCourse.courseType}}</span>
          </el-descriptions-item>
          <el-descriptions-item width="15%">
            <template #label>
              <h2>课程描述</h2>
            </template>
            <el-input v-if="editFlag" v-model="newCourse.courseDescription" type='textarea' autosize placeholder="请输入介绍"/>
            <span v-else>{{newCourse.courseDescription}}</span>
          </el-descriptions-item>
        </el-descriptions>
      </div>
    </div>
    <el-backtop :right="100" :bottom="100"/>
</template>

<script setup lang="ts">
import NavigationBar from "../components/NavigationBar.vue";
import {EditPen,Plus} from "@element-plus/icons-vue";
import router from "../router";
import {onBeforeMount, onUnmounted, ref} from 'vue'
import {message} from "ant-design-vue";
import {createCourse, getCourses} from "../api/index";
import {checkAvailable} from "../mixins/checkLoginState";

let newFile=ref([])
let editFlag=ref(false)
let newCourse=ref<Course>({
  courseId: -1,
  courseType: "",
  courseName: "",
  coursePhotoUrl: "",
  courseDescription:"",
  courseGrade:0,
})

async function handleSuccess(file:any,filelist){
  try {
    // 使用 FileReader 将图片文件转换为 Base64 格式
    const reader = new FileReader();
    reader.readAsDataURL(file.raw);
  }catch (error) {
      console.error('Error handling image upload:', error);
  }
}
async function publish(){
  if(newCourse.value.courseName==""){
    message.warn("请输入课程名称")
    return
  }
  if(newCourse.value.courseType==""){
    message.warn("请选择课程类型")
    return
  }
  if(newCourse.value.courseDescription==""){
    message.warn("请输入课程介绍")
    return
  }
  const course:Course = await createCourse(newCourse.value.courseType,newCourse.value.courseName,newCourse.value.courseDescription,newCourse.value.coursePhotoUrl)
  if(course){
    message.success("课程发布成功")
    router.back()
  }
  else{
    message.error("课程发布失败")
  }
}

onBeforeMount(()=>{
  checkAvailable()
})
const refresh=setInterval(async ()=>{
  checkAvailable()
},10000)
onUnmounted(()=>{
  clearInterval(refresh)
})
</script>

<style scoped>
.courseImg{
  height: 250px;
  width:350px;
  border-radius: 15px;
  cursor: pointer;
  border: 3px dashed var(--el-border-color);
  overflow: hidden;
}
.courseImg:hover{
  border-color: var(--el-color-primary);
}
.uploadIcon{
  font-size: 28px;
  color: #8c939d;
  width: 350px;
  height: 250px;
  text-align: center;
}
.description{
  position:absolute;
  left: 120%;
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
  top:40%;
  width:100%;
  min-height:80%;
  background-color: white;
  border-radius: 15px;
  border: 1px solid black
}
.buttonContainer{
  position:absolute;
  top:30%;
  left:80%;
  height:50px;
  width:150px;
}
</style>