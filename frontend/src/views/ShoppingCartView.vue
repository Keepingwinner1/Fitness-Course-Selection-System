<template>
  <img src="../assets/images/sport.png" alt=""
    style="width: 100%;top: 0;position:absolute; z-index: -1;">
  <NavigationBar/>
  <div class="container">
    <el-row style="margin-top:30px;">
        <el-col :span="15">
            <span style="font-size:25px;font-weight:700;margin-left: 30px">
              我的购物车
            </span>
        </el-col>
        <el-col :span="7">
            <el-button color="#ffd100" style="font-size:25px;font-weight:700;margin-left: 350px" @click="openPaymentModal">
            购买
            </el-button>
          <!-- 弹窗：选择支付方式 -->
          <div v-if="isModalVisible" class="payment-modal">
            <div class="modal-content">
              <h3>选择支付方式</h3>
              <button @click="selectPaymentMethod('微信')">微信支付</button>
              <button @click="selectPaymentMethod('支付宝')">支付宝支付</button>
              <button @click="closePaymentModal">取消</button>
            </div>
          </div>
        </el-col>
    </el-row>
    <el-row>
      <template v-for="book in books">
        <el-row>
           <!-- el-card 部分 -->
          <el-col :span="22" style="display: flex; align-items: center;">
            <el-card shadow="hover" style="width: 100%; margin:1%;">
              <el-row :gutter="10" type="flex" justify="start" align="middle">
                <el-col :span="4">
                  <img src="../assets/images/cycling.png" alt="" style="width: 100%; height: 100%">
                </el-col>
                <el-col :span="20">
                  <div style="display: flex; flex-wrap: wrap;">
                    <div style="flex: 1; display: flex; margin-right: 10px; margin-bottom: 8px;">
                      <h3 style="font-weight: 600; margin-right: 5px; margin-left: 15px;">课程名：</h3>
                      <p>{{ book.courseName }}</p>
                    </div>
                    <div style="flex: 1; display: flex; margin-right: 10px; margin-bottom: 8px;">
                      <h3 style="font-weight: 600; margin-right: 5px; margin-left: 15px;">课程ID：</h3>
                      <p>{{ book.classId }}</p>
                    </div>
                    <div style="flex: 1; display: flex; margin-right: 10px; margin-bottom: 8px;">
                      <h3 style="font-weight: 600; margin-right: 5px; margin-left: 15px;">开课时间：</h3>
                      <p>{{ formatDate(book.courseStartTime) }}</p>
                    </div>
                    <div style="flex: 1; display: flex; margin-right: 10px; margin-bottom: 8px;">
                      <h3 style="font-weight: 600; margin-right: 5px; margin-left: 15px;">价格：</h3>
                      <p>{{ book.coursePrice }}</p>
                    </div>
                  </div>
                </el-col>
              </el-row>
            </el-card>
          </el-col>
          <!-- 复选框部分 -->
          <el-col :span="2" style="display: flex; justify-content: center; align-items: center;">
            <el-checkbox
              :modelValue="isBookSelected(book)"
              @update:modelValue="handleCheckboxChange(book)">
              选择课程
            </el-checkbox>
          </el-col>
        </el-row>
      </template>
    </el-row>
    <el-backtop :right="100" :bottom="100"/>
  </div>
</template>

<script setup lang ='ts'>
import NavigationBar from "../components/NavigationBar.vue";
import {Search} from "@element-plus/icons-vue";
import router from "../router/index";
import {ref,onBeforeMount} from "vue";
import {getAllBooks, payBookCourse} from "../api/index.ts";
import {useFitStore} from "../store/index.ts";

const store = useFitStore();
let books = ref([])

// 用于保存选中的课程ID
let selectedBooks = ref([]);
let isModalVisible = ref(false);
let selectedPaymentMethod = ref(null); // 存储用户选择的支付方式
// 打开支付方式选择弹窗
function openPaymentModal() {
  isModalVisible.value = true;
}

// 关闭支付方式选择弹窗
function closePaymentModal() {
  isModalVisible.value = false;
}
// 用户选择支付方式
async function selectPaymentMethod(method) {
  selectedPaymentMethod.value = method;
  closePaymentModal();
  const a = []
  let b = 0
  for (const selectedBook of selectedBooks.value){
    a.push(selectedBook.bookId)
    b = b+selectedBook.coursePrice;
  }
  console.log(a)
  await payBookCourse(a,store.userID,b,selectedPaymentMethod.value)
  console.log("购买")
  console.log(a,store.userID,b,selectedPaymentMethod.value)
  console.log("购买成功")
}
async function getBookCourse(){
  books.value = await getAllBooks(store.userID);
  console.log(books)
}
// 判断课程是否已被选中
function isBookSelected(book) {
  return selectedBooks.value.some(b => b.classId === book.classId);
}

// 复选框状态变化时的处理函数
function handleCheckboxChange(book) {
  const isSelected = isBookSelected(book);  // 判断当前书籍是否已经被选中

  if (isSelected) {
    // 如果已经选中，移除该课程
    selectedBooks.value = selectedBooks.value.filter(b => b.classId !== book.classId);
  } else {
    // 如果未选中，添加该课程
    selectedBooks.value.push(book);
  }
  console.log(selectedBooks.value);  // 打印当前选中的书籍
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
onBeforeMount(()=>{
  getBookCourse()
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