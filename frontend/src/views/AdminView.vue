<template>
  <div class="background">
    <div class="common-layout">
      <el-container>
        <el-header>
          <img src="../assets/images/logo.png" alt="FitFit" class="logo" />
          <span>FitFit</span>
          <div class="user">
            <el-dropdown>
              <img :src="iconUrl" alt="User" class="dropdownlink" />
              <template #dropdown>
                <el-dropdown-menu>
                  <el-dropdown-item @click="openUser(userID)">
                    <el-icon>
                      <UserFilled />
                    </el-icon>
                    个人资料
                  </el-dropdown-item>
                  <el-dropdown-item @click="navigateToLoginOut">
                    <el-icon>
                      <SwitchButton />
                    </el-icon>
                    退出登录
                  </el-dropdown-item>
                </el-dropdown-menu>
              </template>
            </el-dropdown>
          </div>
        </el-header>
        <el-container>
          <el-aside width="200px" :style="{ height: 'calc(100vh - 50px)' }">
            <el-menu default-active="1" class="el-menu-vertical-demo" @open="handleOpen"
                     @close="handleClose">
              <el-menu-item index="1" @click="active = 1">
                <el-icon>
                  <IconMenu />
                </el-icon>
                <span>教练申请审核</span>
              </el-menu-item>
              <el-menu-item index="2" @click="active = 2">
                <el-icon>
                  <IconMenu />
                </el-icon>
                <span>课程班级审核管理</span>
              </el-menu-item>
              <el-menu-item index="3" @click="active = 3">
                <el-icon>
                  <IconMenu />
                </el-icon>
                <span>费用管理</span>
              </el-menu-item>
              <el-menu-item index="4" @click="active = 4">
                <el-icon>
                  <IconMenu />
                </el-icon>
                <span>健身房信息</span>
              </el-menu-item>
            </el-menu>
          </el-aside>
          <el-main :style="{ height: 'calc(100vh - 50px)' }">

            <!-- 教练管理界面 -->
            <div v-if="active == 1">
              <!-- 教练列表表格 -->
              <el-table :data="Coachs" style="width: 100%">
                <el-table-column prop="realName" label="教练名"></el-table-column>
                <el-table-column prop="coachID" label="教练ID"></el-table-column>
                <el-table-column prop="phone" label="电话号码"></el-table-column>
                <el-table-column prop="registerTime" label="注册时间">
                  <template #default="{ row }">
                    {{ formatDate(row.registerTime) }}
                  </template>
                </el-table-column>
                <el-table-column fixed="right" label="操作" width="250">
                  <template #default="{ row }">
                    <el-button @click="passCoach(row,'approve')" size="small" type="primary">通过</el-button>
                    <el-button @click="passCoach(row,'refuse')" size="small" type="warning">不通过</el-button>
                  </template>
                </el-table-column>
              </el-table>
            </div>

            <!-- 课程审核管理界面 -->
            <div v-if="active == 2">
              <el-table :data="ClassCreates" style="width: 100%">
                <el-table-column prop="classId" label="班级ID"></el-table-column>
                <el-table-column prop="courseType" label="课程类型"></el-table-column>
                <el-table-column prop="courseName" label="课程名称"></el-table-column>
                <el-table-column prop="courseDescription" label="课程描述"></el-table-column>
                <el-table-column prop="capacity" label="课程容量"></el-table-column>
                <el-table-column prop="courseStartTime" label="开课时间">
                  <template #default="{ row }">
                    {{ formatDate(row.courseStartTime) }}
                  </template>
                </el-table-column>
                <el-table-column prop="courseEndTime" label="结束时间">
                  <template #default="{ row }">
                    {{ formatDate(row.courseEndTime) }}
                  </template>
                </el-table-column>
                <el-table-column prop="dayofWeek" label="每周上课时间">
                  <template #default="{ row }">
                    {{ toWeekDay(row.dayOfWeek) }}
                  </template>
                </el-table-column>
                <el-table-column fixed="right" label="操作" width="250">
                  <template #default="{ row }">
                    <el-button @click="handleClass(row, 'approve')" type="success" size="small">通过</el-button>
                    <el-button @click="handleClass(row, 'reject')" type="danger" size="small">不通过</el-button>
                  </template>
                </el-table-column>
              </el-table>
            </div>

            <!--费用管理界面-->
            <div v-if="active === 3">
              <el-table :data="refunds" style="width: 100%">
                <el-table-column prop="paymentId" label="支付ID"></el-table-column>
                <el-table-column prop="bookIds" label="预订ID"></el-table-column>
                <el-table-column prop="amount" label="退款金额"></el-table-column>
                <el-table-column prop="payMethod" label="付款方式"></el-table-column>
                <el-table-column prop="payTime" label="付款时间">
                  <template #default="{ row }">
                    {{ formatDate(row.payTime) }}
                  </template>
                </el-table-column>
                <el-table-column label="操作">
                  <template #default="{ row }">
                    <el-button @click="handleRefund(row, 'approve')" type="success" size="small">通过</el-button>
                    <el-button @click="handleRefund(row, 'reject')" type="danger" size="small">不通过</el-button>
                  </template>
                </el-table-column>
              </el-table>
            </div>

            <!--健身房信息-->
            <div v-if="active === 4">
              <el-table :data="gyms" style="width: 100%">
                <el-table-column prop="gymID" label="健身房ID"></el-table-column>
                <el-table-column prop="gymName" label="健身房名称"></el-table-column>
                <el-table-column prop="address" label="健身房地址"></el-table-column>
                <el-table-column prop="createTime" label="健身房创建时间">
                  <template #default="{ row }">
                    {{ formatDate(row.createTime) }}
                  </template>
                </el-table-column>
              </el-table>
            </div>

          </el-main>
        </el-container>
      </el-container>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref,  onMounted } from 'vue';
import { useRouter } from "vue-router";
import { UserFilled, SwitchButton } from '@element-plus/icons-vue';
import { IconMenu } from '@arco-design/web-vue/es/icon';
import {getAllApplyCoach, getAllClassCreate, getAllGym, getAllRefund, proClassCreate, proCoach,solveRefund} from "../api/index";
import {useFitStore} from "../store/index";

const store = useFitStore();
const iconUrl = store.iconURL
const router = useRouter();
let active = ref(1);

//教练信息
const Coachs = ref<Array<Coach>>([])

//课程信息
const ClassCreates = ref<Array<ClassCreate>>([])

//退款信息
const refunds= ref<Array<refund>>([])

//健身房信息
const gyms = ref<Array<gym>>([])

//获得数据
onMounted(async () => {
  //处理教练申请
  const coachesData = await getAllApplyCoach(store.gymID);
  console.log(store.gymID)
  console.log(coachesData)
  if (coachesData) {
    Coachs.value = coachesData;
  }
  else console.log("没有申请中的教练")
  //处理课程申请
  const createCourseData = await getAllClassCreate(store.gymID);
  if (createCourseData) {
    ClassCreates.value = createCourseData;
    console.log(ClassCreates.value)
  }
  else console.log("没有申请中的课程")
  //费用管理
  const AllRefund = await getAllRefund(store.gymID);
  if (AllRefund){
    refunds.value = AllRefund;
  }
  else console.log("没有申请退款的课程")
  //健身房信息
  const gymData = await getAllGym();
  if (gymData) {
    gyms.value = gymData;
  }
  else console.log("没有健身房")

});
/************************教练审核******************************** */
async function passCoach(row,result){
  if (result === 'approve') {
    await proCoach(row.coachID,true);
    console.log('通过:', row);
  } else if (result === 'reject') {
    await proCoach(row.coachID,false);
    console.log('不通过:', row);
  }
  console.log(`${row.coachId} 已${result}`);
}

/*************************************************** */

/*******************课程审核****************** */
// 处理审核操作
async function handleClass(row, result) {
  if (result === 'approve') {
    await proClassCreate(row.classID,true);
    console.log('通过:', row);
  } else if (result === 'reject') {
    await proClassCreate(row.classID,false);
    console.log('不通过:', row);
  }
  console.log(`${row.courseName} 已${result}`);
}
/************************************* */
/********************费用管理**********************************/
async function handleRefund(row,result){
  if (result === 'approve') {
    await solveRefund(row.paymentID,true);
    console.log('通过:', row);
  } else if (result === 'reject') {
    await solveRefund(row.paymentID,false);
    console.log('不通过:', row);
  }
  console.log(`${row.amount} 已${result}`);
}
/********************************************************* */
/***********健身房信息*****/
function toWeekDay(weekDay) {
  console.log(weekDay)
  console.log("666")
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

function navigateToLoginOut() {
  goPage('/login')

  localStorage.removeItem('token')
}

// 导航操作
const handleOpen = (key, keyPath) => {
  console.log(key, keyPath);
};

const handleClose = (key, keyPath) => {
  console.log(key, keyPath);
};

const openUser = (userID) => {
  userID = userID || store.state.userID;
  router.push({
    path: `/user/${userID}`,
  });
};

const goPage = (path) => {
  router.push({
    path,
  });
};
</script>

<style>

#app {
  max-width: 100% !important;
  margin: 0;
  padding: 0;
}

.background {
  background-image: url('../assets/images/forum-bg.jpg');
  background-size: cover;
  background-position: center;
  width: 100%;
  min-height: 101%;
  position: absolute;
  top: 0;
  left: 0;
}

.el-container,
.el-menu {
  height: 90vh;
  margin: 0;
  padding: 0;
}

.el-header {
  position: relative;
  display: flex;
  justify-content: flex-start;
  align-items: center;
  box-shadow: 2px 2px 2px #ccc;
}

.el-header span {
  font-size: 24px;
  margin-left: 10px;
  font-weight: 700;
}

.logo {
  width: 60px;
  /* 调整logo尺寸 */
  height: auto;
}

.dropdownlink {
  width: 50px;
  /* 调整用户头像尺寸 */
  height: 50px;
  border-radius: 50%;
}

.user {
  position: absolute;
  right: 2%;
}

.search-box {
  margin-bottom: 10px;
}

.el-scrollbar__view {
  display: block !important;
}

</style>
