<template>
    <img src="../assets/images/sport.png" alt=""
       style="width: 100%;top: 0;position:absolute;">
    <NavigationBar/>
    <div class="container">
      <el-row style="margin-top:30px;">
        <el-col :span="15">
            <span style="font-size:25px;font-weight:700;margin-left: 30px">
            购买记录
            </span>
        </el-col>
      </el-row>
      <el-row>
        <el-card v-for="historyPayment in historyPayments" shadow="hover" style="width: 100%; margin:1%;" @click="gotoHistoryCourse(historyPayment)">
            <el-row :gutter="10" type="flex" justify="start" align="middle">
                <el-col :span="4">
                <img src="../assets/images/running.png" alt="" style="width: 100%; height: auto;">
                </el-col>
                <el-col :span="20">
                    <div style="display: flex; flex-wrap: wrap;">
                        <div style="flex: 1; display: flex; margin-right: 10px; margin-bottom: 8px;">
                            <h3 style="font-weight: 600;font-size:15px; margin-right: 5px; margin-left: 20px;">课程名：</h3>
                            <p>{{ historyPayment.courseName }}</p>
                        </div>
                        <div style="flex: 1; display: flex; margin-right: 10px; margin-bottom: 8px;">
                            <h3 style="font-weight: 600;font-size:15px;  margin-right: 5px; margin-left: 10px;">课程类型：</h3>
                            <p>{{ historyPayment.courseType }}</p>
                        </div>
                        <div style="flex: 1; display: flex; margin-right: 10px; margin-bottom: 8px;">
                            <h3 style="font-weight: 600;font-size:15px;  margin-right: 5px; margin-left: 10px;">开课时间：</h3>
                            <p>{{ formatDate(historyPayment.courseStartTime) }}</p>
                        </div>
                        <div style="flex: 1; display: flex; margin-right: 10px; margin-bottom: 8px;">
                            <h3 style="font-weight: 600;font-size:15px;  margin-right: 5px; margin-left: 10px;">购买价格：</h3>
                            <p>{{ historyPayment.coursePrice }}</p>
                        </div>
                    </div>
                </el-col>
            </el-row>
        </el-card>
      </el-row>
      <el-backtop :right="100" :bottom="100"/>
    </div>
</template>

<script setup lang="ts">
import NavigationBar from "../components/NavigationBar.vue";
import router from "../router/index.ts";
import {getAllHistory,getCompleteCourse,getOnGoingCourse,getCancelCourse} from "../api/index.ts";
import {useFitStore} from "../store/index.ts";
import {onBeforeMount, ref} from "vue"

const store = useFitStore();

let historyPayments = ref([])

function gotoHistoryCourse(historyPayment) {
  console.log("打开购买课程记录", historyPayment);
  // 使用路由跳转，将 historyPayment 作为查询参数传递
  router.push({
    name: 'myhistory',
    params: { classId: historyPayment.classId },  // 传递路径参数
    query: { historyPayment: JSON.stringify(historyPayment) } // 传递查询参数
  });
}

onBeforeMount(async ()=>{
// 假设这四个数组是 async 函数的返回值
  const historyPaymentss = await getAllHistory(store.userID);
  const completeCourses = await getCompleteCourse(store.userID);
  const onGoingCourses = await getOnGoingCourse(store.userID);
  const cancelCourses = await getCancelCourse(store.userID);
  // 将这四个数组合并为一个一维数组
  historyPayments.value = historyPaymentss.concat(completeCourses, onGoingCourses, cancelCourses);
  if (!historyPayments) console.log("没有历史商品")
  else console.log(historyPayments)
})
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
</style>