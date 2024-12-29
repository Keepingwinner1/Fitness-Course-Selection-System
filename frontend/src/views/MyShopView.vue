<template>
    <img src="../assets/images/sport.png" alt=""
       style="width: 100%;top: 0;position:absolute; z-index: -1;">
  <NavigationBar/>
  <div class="container">
    <el-page-header style="border-radius:15px;position:absolute;top:3%;left:2%;" @back="backToShop">
        <template #breadcrumb>
        <el-breadcrumb separator="/" style="font-size: 15px;">
          <el-breadcrumb-item :to="{path: '/shop'}">
            课程详情
          </el-breadcrumb-item>
          <el-breadcrumb-item>
            {{course.classID}}
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
          课程详情
        </h3>
      </template>
    </el-page-header>
    <div class="courseIntroduction">
      <img class="courseImg" src="../assets/images/running.png" alt="">
      <div class="description">
        <h3 style="font-size: 30px;font-weight: 700;">
          {{course.courseName}}
        </h3>
        <br>
        <br>
        <h3 style="font-size: 20px;">
          课程类型：{{course.courseType}}
        </h3>
        <h3 style="font-size: 20px;">
          价格：{{course.coursePrice}}
        </h3>
        <h3 style="font-size: 20px;">
          课程容量: {{course.capacity}}
        </h3>
        <h3 style="font-size: 20px;">
          课程开始时间: {{formatDate(course.courseStartTime) }}
        </h3>
        <h3 style="font-size: 20px;">
          课程结束时间: {{formatDate(course.courseEndTime)}}
        </h3>
        <h3 style="font-size: 20px;">
          课程评分: {{course.courseGrade}}
        </h3>
        <h3 style="font-size: 20px;">
          课程简介: {{course.courseDescription}}
        </h3>
        <h3 style="font-size: 20px;">
          每周上课时间: {{toWeekDay(course.dayofWeek)}}
        </h3>
        <!-- 购买按钮 -->
        <button class = "myButton" @click="openPaymentModal">购买</button>

        <!-- 弹窗：选择支付方式 -->
        <div v-if="isModalVisible" class="payment-modal">
          <div class="modal-content">
            <h3>选择支付方式</h3>
            <button @click="selectPaymentMethod('微信')">微信支付</button>
            <button @click="selectPaymentMethod('支付宝')">支付宝支付</button>
            <button @click="closePaymentModal">取消</button>
          </div>
        </div>
        <button v-if="!inCart" @click="AddShoppingCar" class="myButton">
          加入购物车
        </button>
        <button v-if="inCart" @click="RemoveFromCart" class="myButton inCart">
          移出购物车
        </button>
      </div>
    </div>
  </div>
</template>

<script setup>
import router from "../router/index.ts"
import NavigationBar from "../components/NavigationBar.vue";
import {message} from "ant-design-vue";
import {ref,defineProps} from "vue"
import {bookcourse,cancel,payBookCourse} from "../api/index"
import {useFitStore} from "../store/index.ts";

const store = useFitStore();
let isModalVisible = ref(false);
let selectedPaymentMethod = ref(null); // 存储用户选择的支付方式
const props = defineProps({
  courseID: {
    type: String,
    required: true
  },
  classData: {
    type: Object,
    default: null
  }
});
// 使用 classData 来填充 course 对象
let course = ref({
  classID: props.courseID,
  courseName: props.classData?.courseName || '默认课程名称',
  coursePrice: props.classData?.coursePrice || 0,
  courseGrade: props.classData?.courseGrade || 0,
  courseStartTime: props.classData?.courseStartTime || '2024-12-01T08:00:00',
  courseEndTime: props.classData?.courseEndTime || '2024-12-01T10:00:00',
  capacity: props.classData?.capacity || 30,
  courseDescription: props.classData?.courseDescription || '没有描述',
  coursePhotoUrl: props.classData?.coursePhotoUrl || 'https://example.com/default.jpg',
  dayofWeek:props.classData?.dayofWeek || "1#2",
});
// 打开支付方式选择弹窗
function openPaymentModal() {
  isModalVisible.value = true;
}

// 关闭支付方式选择弹窗
function closePaymentModal() {
  isModalVisible.value = false;
}
// 用户选择支付方式
function selectPaymentMethod(method) {
  selectedPaymentMethod.value = method;
  closePaymentModal();
  console.log("购买成功")
  Buy(); // 调用购买函数
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
function backToShop(){
  router.back()
}

let inCart = ref(false);  // 新增变量来跟踪是否在购物车中

async function Buy(){//退款
  const myBook = []
  myBook.push(Number(props.courseID))
  const a = await payBookCourse(myBook,store.userID,course.value.coursePrice,selectedPaymentMethod.value)
  console.log("购买")
  console.log(myBook,store.userID,course.value.coursePrice,selectedPaymentMethod.value)
  router.back()
}
let bbb = ref()
async function AddShoppingCar(){
  const a = await bookcourse(Number(props.courseID),store.userID)
  inCart.value = true; // 更新状态为已加入购物车
  bbb = a.bookId;
  console.log("加入购物车");
}

async function RemoveFromCart(){
  const a=await cancel(bbb)
  inCart.value = false; // 更新状态为未加入购物车
  console.log("移出购物车");
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
  margin-right: 10px;
  font-size: 20px;
  border: none;
  border-radius: 4px;
  cursor: pointer;
  transition: background-color 0.3s, transform 0.2s ease-in-out;
  margin-top: 20px;
  width: 50%;
  text-align: center;
}
.myButton.inCart {
  background-color: lightgray; /* 浅色背景 */
  color: black; /* 文字颜色变为黑色 */
}
.description{
  position:absolute;
  left: 120%;
  top: -20%;
  width:100%;
}
.courseIntroduction{
  position:absolute;
  left:10%;
  top:20%;
}
</style>