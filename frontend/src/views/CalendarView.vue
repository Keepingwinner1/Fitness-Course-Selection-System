<template>
  <img src="../assets/images/sport.png" alt=""
    style="width: 100%;top: 0;position:absolute; z-index: -1;">
  <NavigationBar/>
  <div class="container">
    <el-row style="margin-top:30px;">
        <el-col :span="15">
            <span style="font-size:25px;font-weight:700;margin-left: 30px">
              我的课程日历
            </span>
        </el-col>
    </el-row>
    <el-row>
      <!-- 日历组件 -->
      <a-calendar v-model:value="value">
        <template #dateCellRender="{ current }">
          <ul class="events">
            <li v-for="item in getListData(current)" :key="item.content">
              <a-badge :status="item.type" :text="item.content" />
            </li>
          </ul>
        </template>
      </a-calendar>
    </el-row>
    <el-backtop :right="100" :bottom="100"/>
  </div>
</template>

<script setup lang="ts">
import NavigationBar from "../components/NavigationBar.vue";
import {onBeforeMount, ref, computed} from "vue";
import { Dayjs } from 'dayjs';
import {useFitStore} from "../store/index.ts";
import {getOnGoingCourse} from "../api/index";

const store = useFitStore();

const value = ref<Dayjs>();

let onGoingCourses = ref([]);

const myCourses = ref([]);

const currentTime = new Date(); // 当前时间

// 计算所有课程的上课时间
const allClassTimes = computed(() => {
  const times = [];
  // 遍历 myCourse 中的所有课程
  myCourses.value.forEach(course => {
    let currentDate = new Date(course.courseStartTime); // 从课程开始时间开始
    // 遍历课程开始时间到结束时间之间的每一天
    while (currentDate <= course.courseEndTime) {
      // 检查当前日期是否是课程的上课日
      if (course.dayOfWeek.includes(currentDate.getDay())) {
        const dayOfMonth = currentDate.getDate();
        const month = currentDate.getMonth();
        const year = currentDate.getFullYear();

        // 将日期推送到上课时间列表
        times.push({
          year: year,
          month: month,
          day: dayOfMonth,
          courseDescription: course.courseDescription,
        });
      }
      // 跳到下一天
      currentDate.setDate(currentDate.getDate() + 1);
    }
  });

  console.log("所有课程的上课时间:", times);

  // 过滤掉当前时间已经过去的课程
  return times.filter((time) => {
    return (
        time.year > currentTime.getFullYear() ||
        (time.year === currentTime.getFullYear() &&
            (time.month > currentTime.getMonth() ||
                (time.month === currentTime.getMonth() && time.day >= currentTime.getDate())))
    );
  });
});

// 获取当前日期对应的课程列表
const getListData = (value: Dayjs) => {
  let listData: { type: string; content: string }[] = [];
  // 获取 allClassTimes 计算属性的值
  const courses = allClassTimes.value;
  // 遍历课程，检查该日期是否在课程时间范围内

  courses.forEach((course) => {
    if (
        course.year == value.year() &&
        course.month == value.month() &&
        course.day== value.date()
    ) {

      listData.push({
        type: 'success', // 课程的类型为 success
        content: course.courseDescription, // 课程描述
      });
    }
  });
  return listData || [];
};

function toWeekDay(weekDay) {
  const daysArray = weekDay.split('#'); // 将字符串按 '#' 分割成数组
  return daysArray.map(day => parseInt(day)); // 将每个分割出来的字符串转换为数字
}

// 格式化日期
function formatDate(date) {
  const d = new Date(date);
  const year = d.getFullYear();
  const month = String(d.getMonth()).padStart(2, '0');
  const day = String(d.getDate()).padStart(2, '0');
  const hours = String(d.getHours()).padStart(2, '0');
  const minutes = String(d.getMinutes()).padStart(2, '0');
  const seconds = String(d.getSeconds()).padStart(2, '0');
  return new Date(year, month, day, hours, minutes, seconds);
}

function getInfo(){
  for (const onGoingCourse of onGoingCourses.value){
    let myCourse = {}; // 在每次循环时创建一个新的空对象
    myCourse.courseStartTime = formatDate(onGoingCourse.courseStartTime)
    myCourse.courseEndTime = formatDate(onGoingCourse.courseEndTime)
    myCourse.courseDescription = onGoingCourse.courseDescription
    myCourse.dayOfWeek = toWeekDay(onGoingCourse.dayOfWeek)
    myCourses.value.push(myCourse)
  }
}
onBeforeMount(async ()=>{
  onGoingCourses.value = await getOnGoingCourse(store.userID);
  console.log(onGoingCourses.value)
  getInfo();
})
</script>

<style scoped>
.container {
  justify-content: left;
  text-align: left;
  position: absolute;
  top: 10%;
  width: 80%;
  left: 10%;
  min-height: 90%;
  background-color: whitesmoke;
  border-radius: 15px;
}
</style>
