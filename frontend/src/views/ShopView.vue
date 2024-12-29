<template>
  <img src="../assets/images/sport.png" alt=""
    style="width: 100%;top: 0;position:absolute; z-index: -1;">
  <NavigationBar/>
  <div class="container">
    <el-row style="margin-top:30px;">
        <el-col :span="15">
            <span style="font-size:25px;font-weight:700;margin-left: 30px">
            课程商店
            </span>
        </el-col>
      </el-row>
    <el-row>
      <template v-for="Class in Classes" :key="Class.classId">
        <el-card shadow="hover" style="width: 100%; margin: 1%;" @click="gotoShop(Class)">
          <img src="../assets/images/sport.png" alt="" style="width: 100%; height: 100%">
          <template #footer>
            <h3 style="font-weight: 600">课程名：{{ Class.courseName || '未知课程名' }}</h3>
            <p>课程类型：{{ Class.courseType || '未知类型' }}</p>
            <p>课程描述：{{ Class.courseDescription || '暂无描述' }}</p>
            <p>课程评分：{{ Class.courseGrade ?? '暂无评分' }}</p>
            <p>课程容量：{{ Class.capacity ?? 0 }}</p>
            <p>课程价格：{{ Class.coursePrice ?? 0 }} 元</p>
            <p>课程开始时间：{{ formatDate(Class.courseStartTime) }}</p>
            <p>课程结束时间：{{ formatDate(Class.courseEndTime) }}</p>
            <p>每周上课时间：{{ toWeekDay(Class.dayOfWeek) }}</p>
          </template>
        </el-card>
      </template>

    </el-row>
    <el-backtop :right="100" :bottom="100"/>
  </div>
</template>

<script setup lang="ts">
import router from "../router/index";
import NavigationBar from "../components/NavigationBar.vue";
import {ref,onBeforeMount} from "vue";
import { getAllCourse } from "../api/index" ;
import {useFitStore} from "../store";

let Classes = ref<Array<myClass>>([])
function gotoShop(Class: myClass){
  console.log("跳转到课程商店，课程ID:", Class.classId);
  router.push({
    name: 'myshop',
    params: { courseID: Class.classId },  // 传递课程 ID
    query: { classData: JSON.stringify(Class) }  // 将 class 对象转为 JSON 字符串并传递
  });
}
function toWeekDay(weekDay) {
  const weekDays = ['周日', '周一', '周二', '周三', '周四', '周五', '周六'];
  const daysArray = weekDay.split('#');
  const result = daysArray.map(day => weekDays[parseInt(day)]);
  return result.join('，');
}
// 格式化日期
function formatDate(date) {
  const d = new Date(date);
  const year = d.getFullYear();
  const month = String(d.getMonth() + 1).padStart(2, '0');
  const day = String(d.getDate()).padStart(2, '0');
  const hours = String(d.getHours()).padStart(2, '0');
  const minutes = String(d.getMinutes()).padStart(2, '0');
  const seconds = String(d.getSeconds()).padStart(2, '0');
  return `${year}-${month}-${day} ${hours}:${minutes}:${seconds}`;
}
onBeforeMount(async ()=>{
  Classes.value = await getAllCourse(useFitStore().userID)
  if (!Classes) console.log("没有可购买的商品")
  else console.log(Classes)
})
</script>

<style scoped>
.container {
  justify-content: left;
  text-align: left;
  position: absolute;
  top: 25%;
  width: 80%;
  left: 10%;
  min-height: 75%;
  background-color: whitesmoke;
  border-radius: 15px;
}
</style>