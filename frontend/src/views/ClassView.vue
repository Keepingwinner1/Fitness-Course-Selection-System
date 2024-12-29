<template>
  <img src="../assets/images/sport.png" alt=""
       style="width: 100%;top: 0;position:absolute; z-index: -1;">
  <NavigationBar/>
  <div class="container">
    <el-page-header style="border-radius:15px;position:absolute;top:3%;left:2%;" @back="backToClasses">
      <template #breadcrumb>
        <el-breadcrumb separator="/" style="font-size: 15px;">
          <el-breadcrumb-item v-if="role==='user'" :to="{path: '/classes'}">
            班级列表
          </el-breadcrumb-item>
          <el-breadcrumb-item v-if="role==='coach'" :to="{path: '/courses'}">
            课程列表
          </el-breadcrumb-item>
          <el-breadcrumb-item>
            {{courseClass.classId}}
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
          班级界面
        </h3>
      </template>
    </el-page-header>
    <div class="courseIntroduction">
      <img class="courseImg" src="../assets/images/cycling.png" alt="">
      <div class="description">
        <h3 style="font-size: 35px;font-weight: 700;">
          {{courseClass.courseName}}
        </h3>
        <h3 style="font-size: 30px;">
          课程类型：{{courseClass.courseType}}
        </h3>
        <h3 style="font-size: 30px;">
          课程评分：{{courseClass.courseGrade}}
        </h3>
        <h3 style="font-size: 30px;">
          课程ID：{{courseId}}
        </h3>
      </div>
    </div>
    <div class="buttonContainer">
      <el-button type="primary" class="evaluateBtn" v-if="role==='user'" :disabled="!isOver" style="position:absolute;top:30%;left:80%;height:50px;width:150px;" @click="Comment">
        <h2>请评价此课程</h2>
      </el-button>
    </div>

    <div style="margin-bottom: 300px">
      <el-button type="primary" style="position:absolute;top:20%;left:80%;height:50px;width:150px;" @click="viewComments">
        <h2>查看课程评价</h2>
      </el-button>
    </div>
    <el-menu class="menu" mode="horizontal" :ellipsis="false" default-active="课程任务" @select="(key)=>{pageIndex=key;editFlag=false;editArray=[]}" @change="change">
      <el-col :span="6">
        <el-menu-item index="课程任务" style="border-radius:15px;">
          <h2 class="title">
            <el-icon>
              <Reading/>
            </el-icon>
            课程任务
          </h2>
        </el-menu-item>
      </el-col>
      <el-col :span="6">
        <el-menu-item index="课程日历" style="border-radius:15px;">
          <h2 class="title">
            <el-icon>
              <Calendar/>
            </el-icon>
            课程日历
          </h2>
        </el-menu-item>
      </el-col>
      <el-col :span="6" v-if="role==='coach'">
        <el-menu-item index="课程人员" style="border-radius:15px;">
          <h2 class="title">
            <el-icon>
              <User/>
            </el-icon>
            课程人员
          </h2>
        </el-menu-item>
      </el-col>
      <el-col :span="6">
        <el-menu-item index="班级详情" style="border-radius:15px;">
          <h2 class="title">
            <el-icon>
              <View/>
            </el-icon>
            班级详情
          </h2>
        </el-menu-item>
      </el-col>
    </el-menu>
    <div class="content">

      <div v-if="pageIndex==='课程任务'">
        <el-row style="margin-top:30px;">
          <el-col :span="21">
        <span style="font-size:25px;font-weight:700;margin-left: 30px">
          任务列表
        </span>
          </el-col>
          <el-col :span="3">
            <el-button v-if="role==='coach'" :disabled="isOver" type="primary"  @click="addTask">
              <el-icon>
                <Plus/>
              </el-icon>
              发布任务
            </el-button>
          </el-col>
        </el-row>
        <el-card class="taskContainer" v-for="task in tasks" shadow="always">
          <template #header>
            <div>
                <span style="font-size: 20px;background-color: pink;border-radius: 10px; align-content: center;">
                  <el-icon style="top:3px;">
                    <Document/>
                  </el-icon>
                  任务
                </span>
              <span v-if="editArray.indexOf(task.taskID)===-1" style="font-size:30px;font-weight:700;margin-left:20px;">
                    {{task.taskName}}
              </span>
              <span v-else>
                <el-input class="taskTitle" v-model="task.taskName" placeholder="请输入任务名称"  style="width:300px;"/>
              </span>
            </div>
          </template>
          <span v-if="editArray.indexOf(task.taskID)===-1">
            {{task.description}}
          </span>
          <span v-else>
            <el-input type="textarea" :rows="10" v-model="task.description" placeholder="请输入任务内容"/>
          </span>
          <template #footer>
            <span>
                开始日期：{{task.publishTime}}
            </span>
            <span v-if="editArray.indexOf(task.taskID)===-1" style="margin-left: 50px;">
                截止日期：{{task.endTime}}
            </span>
            <span v-else style="margin-left: 50px;">
              截止日期：
              <el-date-picker v-model="task.endTime" placeholder="请选择截止日期"/>
            </span>
            <span style="float:right">
              <template v-if="!isOver&&role==='coach'">
                <template v-if="editArray.indexOf(task.taskID)===-1">
                  <el-button type="primary" @click="editTask(task.taskID)">
                    编辑
                  </el-button>
                  <el-button type="danger" @click="deleteTask(task.taskID)">
                    删除
                  </el-button>
                </template>
                <el-button v-else type="success" @click="saveTask(task.taskID)">
                  保存
                </el-button>
              </template>
            </span>
          </template>
        </el-card>
      </div>
      <div v-if="pageIndex==='课程日历'">
          <el-calendar style="border-radius:15px"></el-calendar>
      </div>
      <div v-if="pageIndex==='课程人员'">
        <el-table :data="personList" table-layout="auto" border style="border-radius: 15px;--el-table-header-text-color: black; font-size:20px;">
          <el-table-column prop="id">
            <template #header>
              <h2 style="font-size:20px;font-weight:500">
                学员ID
              </h2>
            </template>
          </el-table-column>
          <el-table-column prop="userName">
            <template #header>
              <h2 style="font-size:20px;font-weight:500">
                学员姓名
              </h2>
            </template>
          </el-table-column>
          <el-table-column prop="gender">
            <template #header>
              <h2 style="font-size:20px;font-weight:500">
                性别
              </h2>
            </template>
          </el-table-column>
          <el-table-column prop="age">
            <template #header>
              <h2 style="font-size:20px;font-weight:500">
                年龄
              </h2>
            </template>
          </el-table-column>
          <el-table-column prop="email">
            <template #header>
              <h2 style="font-size:20px;font-weight:500">
                邮箱
              </h2>
            </template>
          </el-table-column>
        </el-table>
      </div>
      <div v-if="pageIndex==='班级详情'">
        <el-descriptions size="large" style="width:96%;margin-left:2%;margin-right:2%;margin-top:10px" border>
          <template #title>
            <h2 style="font-weight: 600">
              详情列表
            </h2>
          </template>
          <template #extra>
            <el-button v-if="role==='coach'&&editFlag===false" :disabled="isOver" type="primary" @click="()=>{editFlag=!editFlag;}" style="margin-left:20px;margin-top:15px;">
              <el-icon>
                <EditPen/>
              </el-icon>
              编辑课程
            </el-button>
            <el-button v-if="role==='coach'&&editFlag===true" type="info" @click="()=>{editFlag=!editFlag;cancel()}" style="margin-left:20px;margin-top:15px;">
              <el-icon>
                <EditPen/>
              </el-icon>
              取消
            </el-button>
            <el-button v-if="role==='coach'&&editFlag===true" type="success" @click="()=>{editFlag=!editFlag;save()}" style="margin-left:20px;margin-top:15px;">
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
            <span>{{courseId}}</span>
          </el-descriptions-item>
          <el-descriptions-item width="15%">
            <template #label>
              <h2>课程名称</h2>
            </template>
            <span>{{courseClass.courseName}}</span>
          </el-descriptions-item>
          <el-descriptions-item width="15%">
            <template #label>
              <h2>课程类型</h2>
            </template>
            <span>{{courseClass.courseType}}</span>
          </el-descriptions-item>
          <el-descriptions-item width="15%">
            <template #label>
              <h2>班级ID</h2>
            </template>
            <span>{{courseClass.classId}}</span>
          </el-descriptions-item>
          <el-descriptions-item width="15%">
            <template #label>
              <h2>班级容量</h2>
            </template>
            <el-input v-if="editFlag" v-model="courseClass.capacity" placeholder="请输入容量"/>
            <span v-else>{{courseClass.capacity}}</span>
          </el-descriptions-item>
          <el-descriptions-item width="15%">
            <template #label>
              <h2>课程开始时间</h2>
            </template>
            <el-date-picker v-if="editFlag" v-model="courseClass.courseStartTime" placeholder="请选择开始时间"/>
            <span v-else>{{courseClass.courseStartTime}}</span>
          </el-descriptions-item>
          <el-descriptions-item width="15%">
            <template #label>
              <h2>课程结束时间</h2>
            </template>
            <el-date-picker v-if="editFlag" v-model="courseClass.courseEndTime" placeholder="请选择结束时间"/>
            <span v-else>{{courseClass.courseEndTime}}</span>
          </el-descriptions-item>
          <el-descriptions-item width="15%">
            <template #label>
              <h2>上课时间</h2>
            </template>
            <el-checkbox-group  v-if="editFlag" v-model="dayOfWeek" size="default">
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
            <span v-else>
              {{courseClass.dayOfWeek!=null?courseClass.dayOfWeek.split('#').join(','): null}}
            </span>
          </el-descriptions-item>
          <el-descriptions-item width="15%">
            <template #label>
              <h2>课程描述</h2>
            </template>
            <span>{{courseClass.courseDescription}}</span>
          </el-descriptions-item>
        </el-descriptions>
      </div>
    </div>
    <el-backtop :right="100" :bottom="100"/>
    <el-dialog class="commentDialog" v-model="commentFlag" style="width:500px;">
      <template #header>
        <h2 style="font-weight: 700">
          评价
        </h2>
      </template>
      <el-form :model="commentForm">
        <el-form-item>
          <el-rate v-model="commentForm.score" size="large"  allow-half/>
        </el-form-item>
        <el-form-item>
          <el-input v-model="commentForm.comment" type="textarea" :rows="5" placeholder="请输入您对此课程的评价"/>
        </el-form-item>
      </el-form>
      <template #footer>
          <el-button @click="commentFlag = false">取消</el-button>
          <el-button type="primary" @click="commitComment">
            确定
          </el-button>
      </template>
    </el-dialog>
    <el-dialog class="commentViewDialog" v-model="commentViewFlag" style="width:700px;">
      <template #header>
        <h2 style="font-weight: 700">
          评价列表
        </h2>
      </template>
      <el-scrollbar height="500px">
        <template v-for="c in comments">
          <el-card shadow="always">
            <h3 style="font-weight: 600">
              评论人ID：
              <span>
                {{c.traineeId}}
              </span>
            </h3>
            <h3 style="font-weight: 600">
              评分：
              <el-rate v-model="c.grade" size="large"  disabled show-score text-color="#ff9900" score-template="{value} points"/>
            </h3>
            <h3 style="font-weight: 600">
              评论：
              <span>
                {{c.evaluate}}
              </span>
            </h3>
          </el-card>
        </template>
      </el-scrollbar>
      <template #footer>
        <el-button type="primary" @click="commentViewFlag=false">
          确定
        </el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup lang="ts">
import router from "../router"
import NavigationBar from "../components/NavigationBar.vue";
import {defineProps, onBeforeMount, onUnmounted, ref} from "vue";
import {Calendar, Document, EditPen, Plus, Reading, User, View} from "@element-plus/icons-vue";
import {message} from "ant-design-vue";
import {useRoute} from "vue-router";
import {useFitStore} from "../store/index";
import {checkAvailable} from "../mixins/checkLoginState";
import {
  getClassByClassID,
  getClassStu,
  modifyClass,
  getTasks,
  modifyTask,
  deleteTaskById,
  createTask,
  evaluate, getComments
} from "../api";

const store=useFitStore()
const route = useRoute()
const props = defineProps({
  courseId:{
    type:Number
  }
});
let courseId= props.courseId
let classId = Number(route.params["classId"])
let role = store.role
let courseClass = ref()
let comments = ref([])
let commentForm = ref({
  score: 0,
  comment: ""
})
let dayOfWeek=ref([])
let originData = null
let isOver=ref()
let editFlag = ref(false)
let editArray=ref([])
let commentFlag=ref(false)
let commentViewFlag=ref(false)

let tasks = ref([])
let personList=ref([])
let pageIndex = ref('课程任务')

function backToClasses(){
  router.back()
}
function Comment(){
  commentFlag.value=true
}
async function commitComment(){
  commentFlag.value=false
  const data = await evaluate(store.userID,classId,commentForm.value.score,commentForm.value.comment)
  if(data){
    message.success("评价成功")
  }
  else{
    message.error("评价失败")
  }
}

async function viewComments(){
  comments=await getComments(classId)
  if(comments){
    message.success("获得评价成功")
    commentViewFlag.value=true
  }
  else{
    message.error("获得评价失败")
  }
}

function cancel(){
  courseClass.value=JSON.parse(JSON.stringify(originData));
  dayOfWeek.value=courseClass.value.dayOfWeek.split('#')
  message.info("已取消编辑")
}
async function save(){
  if(courseClass.value.capacity>0&&courseClass.value.courseEndTime>courseClass.value.courseStartTime&&dayOfWeek.value!=null){
    courseClass.value.dayOfWeek = dayOfWeek.value.join('#')
    const data = await modifyClass(courseClass.value.classId,store.coachID,courseId,courseClass.value.capacity,courseClass.value.coursePrice,courseClass.value.courseStartTime,courseClass.value.courseEndTime,courseClass.value.dayOfWeek)
    message.success("保存成功")
    originData=JSON.parse(JSON.stringify(courseClass.value));
    dayOfWeek.value=courseClass.value.dayOfWeek.split('#')
  }
else{
    courseClass.value=JSON.parse(JSON.stringify(originData));
    dayOfWeek.value=courseClass.value.dayOfWeek.split('#')
    message.warn("输入信息有误")
  }
}

async function addTask(){
  const data=await createTask(classId,"默认任务名","默认内容",new Date(),new Date())
  if(data){
    message.success("发布成功")
    tasks.value= await getTasks(classId)
  }
  else{
    message.error("发布失败")
  }
}

function editTask(taskId){
  let index=-1
  for (let i = 0; i < tasks.value.length; i++) {
    if(tasks.value[i].taskID==taskId){
      index=i
      break
    }
  }
  editArray.value.push(taskId)
}
async function saveTask(taskId){
  let index=-1
  for (let i = 0; i < tasks.value.length; i++) {
    if(tasks.value[i].taskID==taskId){
      index=i
      break
    }
  }
  if(tasks.value[index].taskName!=null&&tasks.value[index].description!=null){
    const data= await modifyTask(taskId,classId,tasks.value[index].taskName,tasks.value[index].description,tasks.value[index].publishTime,tasks.value[index].endTime)
    if(data){
      message.success("修改成功")
    }
    else{
      message.error("修改失败")
    }
  }
  else{
    message.warn("输入信息不完整")
  }
  editArray.value.splice(editArray.value.indexOf(taskId),1)
}
async function deleteTask(taskId){
  let index=-1
  for (let i = 0; i < tasks.value.length; i++) {
    if(tasks.value[i].taskID==taskId){
      index=i
      break
    }
  }
  const data=await deleteTaskById(taskId)
  if(data){
    message.success("删除成功")
    tasks.value.splice(index,1)
  }
  else{
    message.error("删除失败")
  }

}

async function change(){
  if(pageIndex.value==='课程人员'){
    personList.value = await getClassStu(classId)
  }
}
onBeforeMount(async ()=>{
  checkAvailable()
  courseClass.value = await getClassByClassID(classId)
  isOver.value=(new Date().valueOf()>Date.parse(courseClass.value.courseEndTime).valueOf())
  originData=JSON.parse(JSON.stringify(courseClass.value));
  if(courseClass.value.dayOfWeek!=null){
    dayOfWeek.value=courseClass.value.dayOfWeek.split('#')
  }
  else{
    dayOfWeek.value=[]
  }
  personList.value = await getClassStu(classId)
  tasks.value = await getTasks(classId)
})
const checkLogIn=setInterval(()=>{
  checkAvailable()
},100000)
onUnmounted(()=>{
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
.menu{
  position:absolute;
  width:100%;
  top:45%;
  border-radius:15px;
  --el-menu-hover-bg-color: transparent;
  --el-menu-hover-text-color: orange;
  --el-menu-bg-color: #dddddd;;
  --el-menu-text-color: black;
  --el-menu-active-color: orange;
  box-shadow: 0 0 2px black;
  z-index: 2;
}
.title{
  width:320px;
  font-weight:700;
  text-align: center;
}
.content{
  position:absolute;
  top:55%;
  width:100%;
  min-height:80%;
  background-color: white;
  border-radius: 15px;
  border: 1px solid black
}
.taskContainer{
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