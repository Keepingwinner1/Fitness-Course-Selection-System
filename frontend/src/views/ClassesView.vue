<template>
  <img src="../assets/images/sport.png" alt=""
  style="width: 100%;top: 0;position:absolute;">
  <NavigationBar/>
  <div class="container">
    <el-row style="margin-top:30px;">
      <el-col :span="15">
        <span style="font-size:25px;font-weight:700;margin-left: 30px">
          班级列表
        </span>
        <el-segmented class="segmented" v-model="showType" :options="options" @change="changeType"/>
      </el-col>
    </el-row>
    <el-row>
      <template v-for="c in classes">
        <el-card shadow="hover" style="width: 23%; margin:1%;" @click="gotoClass(c.classId,c.courseID)">
          <img src="../assets/images/cycling.png" alt="" style="width: 100%;height:100%">
          <template #footer>
            <h3 style="font-weight: 600">课程名：{{c.courseName}}</h3>
            <p>课程类型：{{c.courseType}}</p>
            <p>课程评分：{{c.courseGrade}}</p>
            <p>班级ID：{{c.classId}}</p>
          </template>
        </el-card>
      </template>
    </el-row>
    <el-backtop :right="100" :bottom="100"/>
  </div>
</template>

<script setup lang="ts">
import NavigationBar from "../components/NavigationBar.vue";
import {Search} from "@element-plus/icons-vue";
import router from "../router/index";
import {onUnmounted, ref} from "vue";
import {onBeforeMount} from "vue";
import {getAllHistory, getCompleteCourse, getOnGoingCourse} from "../api";
import {useFitStore} from "../store";
import {checkAvailable} from "../mixins/checkLoginState";
const store = useFitStore()
let classes = ref([])
let options = ['当前课程','待开始课程','历史课程']
let showType = ref('当前课程')
function gotoClass(classID:number,courseId:number){
  router.push({path:`/classes/${classID}`,query:{courseId: courseId}})
}
async function changeType(){
  if(showType.value=='当前课程'){
    console.log("获取当前课程")
    classes.value = await getOnGoingCourse(store.userID)
  }
  else if(showType.value=='待开始课程'){
    console.log("获取待开始课程")
    classes.value = await getAllHistory(store.userID)
  }
  else if(showType.value=='历史课程'){
    console.log("获取历史课程")
    classes.value = await getCompleteCourse(store.userID)
  }
}
onBeforeMount(async ()=>{
  checkAvailable()
  classes.value = await getOnGoingCourse(store.userID)
})

const checkLogIn=setInterval(()=>{
  checkAvailable()
},100000)
const refresh=setInterval(async ()=>{
  if(showType.value=='当前课程'){
    console.log("获取当前课程")
    classes.value = await getOnGoingCourse(store.userID)
  }
  else if(showType.value=='待开始课程'){
    console.log("获取待开始课程")
    classes.value = await getAllHistory(store.userID)
  }
  else if(showType.value=='历史课程'){
    console.log("获取历史课程")
    classes.value = await getCompleteCourse(store.userID)
  }
},10000)

onUnmounted(()=>{
  clearInterval(refresh)
  clearInterval(checkLogIn)
})

</script>

<style scoped>
.container{
  justify-content: left;
  text-align: left;
  position: absolute;
  top: 25%;
  width: 80%;
  left: 10%;
  min-height:75%;
  background-color: whitesmoke;
  border-radius: 15px;
}

.segmented{
  font-size: 15px;
  --el-segmented-item-selected-color: var(--el-text-color-primary);
  --el-segmented-item-selected-bg-color: #ffd100;
  --el-border-radius-base: 16px;
}
</style>