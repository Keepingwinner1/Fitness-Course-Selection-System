<template>
  <img src="../assets/images/sport.png" alt=""
       style="width: 100%;top: 0;position:absolute;">
  <NavigationBar/>
  <div class="container">
    <el-row style="margin-top:30px;">
      <el-col :span="21">
        <span style="font-size:25px;font-weight:700;margin-left: 30px">
          课程列表
        </span>
      </el-col>
      <el-col :span="3">
        <el-button color="orange" @click="gotoPublish">
          发布课程
        </el-button>
      </el-col>
    </el-row>
    <el-row>
      <template v-for="course in courses">
          <el-card shadow="hover" style="width: 23%; margin:1%;" @click="gotoCourse(course.courseId)">
            <img src="../assets/images/cycling.png" alt="" style="width: 100%;height:100%">
            <template #footer>
              <h3 style="font-weight: 600">课程名：{{course.courseName}}</h3>
              <p>课程类型：{{course.courseType}}</p>
              <p>课程评分：{{course.courseGrade}}</p >
            </template>
          </el-card>
        </template>
    </el-row>
    <el-backtop :right="100" :bottom="100"/>
  </div>
</template>

<script setup lang="ts">
  import NavigationBar from "../components/NavigationBar.vue";
  import {useFitStore} from "../store/index";
  import router from "../router/index";
  import {onUnmounted, ref} from "vue";
  import {onBeforeMount} from "vue";
  import {getCourses} from "../api/index";
  import {checkAvailable} from "../mixins/checkLoginState";
  const store = useFitStore()
  let courses = ref()
  function gotoPublish(){
    router.push("/courses/coursePublish")
  }
  function gotoCourse(courseID:number){
    console.log("进入课程",courseID)
    router.push({path:`/courses/${courseID}`})
  }
  onBeforeMount(async ()=>{
    checkAvailable()
    courses.value = await getCourses(store.coachID)
  })

  const checkLogIn=setInterval(()=>{
    checkAvailable()
  },100000)
  const refresh=setInterval(async ()=>{
    courses.value = await getCourses(store.coachID)
  },20000)
  onUnmounted(()=>{
    clearInterval(refresh)
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
