<template>
    <img src="../assets/images/sport.png" alt=""
       style="width: 100%;top: 0;position:absolute; z-index: -1;">
  <NavigationBar/>
  <div class="container">
    <el-page-header style="border-radius:15px;position:absolute;top:3%;left:2%;" @back="backToHistory">
        <template #breadcrumb>
        <el-breadcrumb separator="/" style="font-size: 15px;">
          <el-breadcrumb-item :to="{path: '/history'}">
            购买记录
          </el-breadcrumb-item>
          <el-breadcrumb-item>
            {{HistoryPayment.courseID}}
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
          我的购买记录
        </h3>
      </template>
    </el-page-header>
    <div class="courseIntroduction">
      <img class="courseImg" src="../assets/images/running.png" alt="">
      <div class="description">
        <h3 style="font-size: 30px;font-weight: 700;">
          {{HistoryPayment.courseName}}
        </h3>
        <br>
        <br>
        <h3 style="font-size: 20px;">
          付款金额：{{HistoryPayment.coursePrice}}
        </h3>
        <h3 style="font-size: 20px;">
          课程描述: {{HistoryPayment.courseDescription}}
        </h3>
        <h3 style="font-size: 20px;">
          每周上课时间: {{toWeekDay(HistoryPayment.dayofWeek)}}
        </h3>
        <h3 style="font-size: 20px;">
          课程开始时间: {{formatDate(HistoryPayment.courseStartTime)}}
        </h3>
        <button v-if="isCourseStartTimeValid(HistoryPayment.courseStartTime)"  @click="Refund" class="myButton">
        申请退款
        </button>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import router from "../router/index.ts"
import NavigationBar from "../components/NavigationBar.vue";
import {ref,defineProps} from "vue"
import {setRefund} from "../api/index";
import {useFitStore} from "../store/index.ts";

const store = useFitStore();
const props = defineProps({
  classId: {
    type: String,
    required: true
  },
  historyPayment: {
    type: Object,
    default: null
  }
});
// 使用 classData 来填充 course 对象
let HistoryPayment = ref({
  classID: props.classId,
  courseName: props.historyPayment?.courseName || '默认课程名称',
  coursePrice: props.historyPayment?.coursePrice || 0,
  courseGrade: props.historyPayment?.courseGrade || 0,
  courseStartTime: props.historyPayment?.courseStartTime || '2024-12-01T08:00:00',
  courseEndTime: props.historyPayment?.courseEndTime || '2024-12-01T10:00:00',
  capacity: props.historyPayment?.capacity || 30,
  courseDescription: props.historyPayment?.courseDescription || '没有描述',
  coursePhotoUrl: props.historyPayment?.coursePhotoUrl || 'https://example.com/default.jpg',
  dayofWeek:props.historyPayment?.dayofWeek || "1#2",
});

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
function isCourseStartTimeValid(courseStartTime) {
    const currentTime = new Date();

    const startTime = new Date(courseStartTime.replace(" ", "T"));
    
    if (startTime > currentTime && HistoryPayment.paymentStatus !== 3) {
        return true;
    } else {
        return false;
    }
}
function backToHistory(){
  router.back()
}

async function Refund(){//退款
  await setRefund(HistoryPayment.value.classID,store.userID)
  console.log(HistoryPayment.value.classID,store.userID)
  console.log("申请退款")
}
</script>

<style scoped>
.container{
justify-content: left;
text-align: left;
position: absolute;
top: 10%;
width: 80%;
left: 10%;
min-height:90%;
background-color: whitesmoke;
border-radius: 15px;
}
.courseImg{
  height: 300px;
  border-radius: 15px;
  top: 50%;
  box-shadow: 0 0 10px black;
}
.myButton {
  display: inline-block;
  padding: 12px 20px;
  background-color: orange;
  color: white;
  font-size: 18px;
  border: none;
  border-radius: 4px;
  cursor: pointer;
  transition: background-color 0.3s, transform 0.2s ease-in-out;
  margin-top: 20px;
  width: 50%;
  text-align: center;
}
.description{
  position:absolute;
  left: 130%;
  top: 10%;
  width:100%;
}
.courseIntroduction{
  position:absolute;
  left:10%;
  top:20%;
}
</style>