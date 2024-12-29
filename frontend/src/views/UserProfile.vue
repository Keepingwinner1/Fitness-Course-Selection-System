<template>
  <div class="bg">
    <div class="user-profile">
      <div class="profile-container">
        <div class="back-button-container">
          <el-button
            @click="goBack"
            circle
            style="font-size: 24px; width: 50px; height: 50px"
          >
            <el-icon>
              <arrow-left />
            </el-icon>
          </el-button>
        </div>
        <aside class="profile-sidebar">
          <div class="avatar-wrapper" @click="showLargeImage">
            <img
              :src="imagePreview || profile.iconURL || defaultAvatar"
              alt="avatar"
              class="avatar"
            />
          </div>
          <h2>{{ profile.userName }}</h2>
        </aside>

        <main class="profile-main">
          <!-- 第一行：用户ID、邮箱、注册时间 -->
          <section class="profile-info">
            <div class="info-row">
              <div class="profile-viewer">
                <label>用户ID</label>
                <p>{{ profile.userID }}</p>
              </div>
              <div class="profile-viewer">
                <label>用户名</label>
                <p>{{ profile.userName }}</p>
              </div>
              <EditableField
                  label="邮箱"
                  :value="profile.email"
                  type="input"
                  @save="profile.email = $event"
              />
            </div>
          </section>

          <!-- 第三行：昵称、年龄、性别 -->
          <section class="profile-info">
            <div class="info-row uniform-row">
              <EditableField
                label="年龄"
                :value="profile.age"
                type="input"
                @save="profile.age = $event"
              />
              <div class="profile-editor">
                <label>性别</label>
                <select v-model="profile.gender">
                  <option value="男">男</option>
                  <option value="女">女</option>
                </select>
              </div>
            </div>
          </section>

          <!-- 第四行：类型、体重 -->
          <section class="profile-info">
            <div class="info-row uniform-row">
              <EditableField
                label="健身目标类型"
                :value="profile.goalType"
                type="input"
                @save="profile.goalType = $event"
              />
              <EditableField
                label="体重 / kg"
                :value="profile.goalWeight"
                type="input"
                @save="profile.goalWeight = $event"
              />
            </div>
          </section>

          <!-- 第五行：会员状态 -->
          <section v-if="role==='coach'" class="profile-info">
            <div>
              <br>
              <label>所属健身房:</label>
                <el-button v-if="!store.gymID" type="primary" style="margin-left:15px;" @click="applyForGYM">申请加入健身房</el-button>
<!--                <el-button v-else type="danger" style="margin-left:15px;" @click="quitGYM">退出健身房</el-button>-->
              <p style="width: 200px;">{{ store.gymID}}</p>
              <br>
            </div>
          </section>

          <!-- 第六行：个性签名 -->
          <section class="profile-info">
            <div class="signature-editor signature-textarea">
              <label>个性签名</label>
              <textarea
                v-model="profile.introduction"
                @blur="emitSave('introduction')"
              ></textarea>
            </div>
          </section>

          <!-- 保存和取消按钮 -->
          <div class="profile-actions">
            <button @click="cancelEdit" class="cancel-button">取消</button>
            <button @click="saveProfile" class="save-button">保存</button>
          </div>

        </main>
      </div>

      <!-- 模态框显示大图 -->
      <div v-if="isLargeImageVisible" class="modal" @click="hideLargeImage">
        <img
          :src="imagePreview || profile.iconURL || defaultAvatar"
          alt="Large avatar"
          class="large-avatar"
        />
      </div>
    </div>
  </div>

  <el-dialog v-model="flag" style="width:500px;">
    <template #header>
      <h2 style="font-weight: 700">
        健身房申请
      </h2>
    </template>
    <el-form :model="form" label-position="left" label-width="80px">
      <el-form-item label="真实姓名" required>
        <el-input v-model="form.realName" placeholder="请输入姓名"/>
      </el-form-item>
      <el-form-item label="身份证" required>
        <el-input v-model="form.number" placeholder="请输入身份证"/>
      </el-form-item>
      <el-form-item label="电话号码" required>
        <el-input v-model="form.phone" placeholder="请输入电话号码"/>
      </el-form-item>
      <el-form-item label="地址" required>
        <el-input v-model="form.address" placeholder="请输入地址"/>
      </el-form-item>
      <el-form-item label="银行卡号" required>
        <el-input v-model="form.cardID" placeholder="请输入银行卡号"/>
      </el-form-item>
      <el-form-item label="健身房ID" required>
        <el-input v-model="form.gymID" placeholder="请输入健身房ID"/>
      </el-form-item>
    </el-form>
    <template #footer>
      <el-button @click="flag = false">取消</el-button>
      <el-button type="primary" @click="register">
        确定
      </el-button>
    </template>
  </el-dialog>
</template>

<script setup lang="ts">
import defaultAvatar from "../assets/images/default-avatar.png";
import EditableField from "../views/EditableField.vue";
import {ElNotification} from "element-plus";
import {useFitStore} from "../store";
import {ArrowLeft} from "@element-plus/icons-vue";
import {onBeforeMount, onUnmounted, ref} from 'vue'
import router from "../router/index"
import {message} from "ant-design-vue";
import {coachRegister, editProfile, getUserProfile} from "../api";
import {checkAvailable} from "../mixins/checkLoginState";

let form = ref({
  realName:"",
  phone:"",
  number:"",
  address:"",
  cardID:"",
  gymID:null,
})
let flag=ref(false)
const store=useFitStore()
let role= store.role
let profile = ref<Profile>()
let originalProfile = null

let addingTag= false
let newTag= ""
let imagePreview= null
let  isLargeImageVisible= false

function goBack() {
  router.back();
}

function emitSave(field) {
  console.log(`${field} updated:`, profile[field]);
}
function showLargeImage() {
  isLargeImageVisible = true;
}
function hideLargeImage() {
  isLargeImageVisible = false;
}
function cancelEdit() {
  profile.value = JSON.parse(JSON.stringify(originalProfile));
  ElNotification({
    title: "提示",
    message: "编辑已取消",
    type: "info",
  });
}
async function saveProfile() {
  const data = await editProfile(profile.value)
  if(data){
    message.success("保存成功")
    originalProfile = JSON.parse(JSON.stringify(profile.value));
  }
  else{
    message.error("保存失败")
  }
}
function   applyForGYM(){
  flag.value=true
}

async function register(){
  flag.value=false
  if(form.value.realName==""||form.value.phone==""||form.value.number==""||form.value.address==""||form.value.cardID==""||form.value.gymID==null){
    message.warn("请输入完整信息")
    return
  }
  const data = await coachRegister(store.userID,form.value.realName,form.value.phone,form.value.number,form.value.address,form.value.cardID,form.value.gymID)
  if(data){
    message.success("申请成功,请等待审核")
  }
  else{
    message.error("申请失败")
  }
}
onBeforeMount(async()=>{
  profile.value=await getUserProfile(store.userID)
  originalProfile = profile.value
})

const checkLogIn=setInterval(()=>{
  checkAvailable()
},100000)
onUnmounted(()=>{
  clearInterval(checkLogIn)
})
</script>




<style scoped>
.bg {
  background-image: url("../assets/images/forum-bg.jpg");
  background-size: cover;
  background-position: center;
  background-attachment: fixed;
  width: 100%;
  min-height: 100%;
  position: absolute;
  top: 0;
  left: 0;
  padding-top: 30px;
  padding-bottom: 30px;
}

.user-profile {
  display: flex;
  justify-content: center;
  align-items: center;
  height: fit-content;
  width: 100%;
  background-color: transparent;
}

.profile-container {
  display: flex;
  width: 100%;
  max-width: 80vw;
  margin: auto;
  background-color: rgba(253, 248, 248, 0.6);
  /* 半透明的背景 */
  backdrop-filter: blur(10px);
  /* 添加模糊效果，模拟磨砂感 */
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1),
    /* 主阴影 */ 0 6px 20px rgba(0, 0, 0, 0.1);
  /* 次阴影，增强立体感 */
  border-radius: 15px;
  /* 圆角半径，增加平滑感 */
  justify-content: space-between;
  /* 避免子元素溢出边界 */
  overflow: hidden;
  /*height: 100vh;*/
  /*transform: scale(0.5); !* 缩放比例 0.8 即缩小为原尺寸的 80% *!*/
  /*transform-origin: top left; !* 以左上角为缩放中心，或选择其他位置 *!*/
}

.profile-sidebar {
  width: 20%;
  padding: 20px;
  text-align: center;
  display: flex;
  flex-direction: column;
  position: relative;
  /*justify-content: center;*/
  top: 150px;
  background-color: transparent;
  border: none;
}

.profile-main {
  width: 80%;
  margin-left: 0px;
  padding: 20px;
  display: flex;
  flex-direction: column;
  justify-content: space-between;
  background-color: transparent;
}

.avatar-wrapper {
  position: relative;
  cursor: pointer;
}

.avatar {
  width: 200px;
  height: 200px;
  border-radius: 50%;
  position: relative;
}

.edit-icon {
  position: absolute;
  bottom: 0;
  right: 0;
  background-color: #42b983;
  color: white;
  border-radius: 50%;
  cursor: pointer;
  font-size: 14px;
  padding: 5px 10px;
  transform: translate(1%, 1%);
  /* 调整 transform，使图标更加贴近头像的右下角 */
}

.file-input {
  display: none;
}

.tags {
  display: flex;
  flex-wrap: wrap;
  gap: 5px;
  margin-top: 10px;
  justify-content: center;
}

.tag {
  border-radius: 15px;
  padding: 5px 10px;
  font-size: 14px;
  color: white;
  margin: 3px;
  display: inline-block;
  position: relative;
}

.remove-tag {
  margin-left: 8px;
  cursor: pointer;
}

.tag-input input {
  padding: 5px;
  border-radius: 15px;
  border: 1px solid #ccc;
}

.add-tag {
  background-color: #42b983;
  color: white;
  border-radius: 50%;
  padding: 5px 10px;
  cursor: pointer;
  font-size: 18px;
  line-height: 1;
  display: flex;
  align-items: center;
}

.profile-info {
  display: flex;
  flex-direction: column;
  gap: 20px;
  font-size: 20px;
  color: #333;
  text-align: left;
}

.info-row {
  display: flex;
  justify-content: space-between;
  gap: 20px;
}

.uniform-row > .editable-field,
.uniform-row > .profile-editor {
  flex: 1;
}

p {
  height: 50px;
  margin: 5px 0;
  padding: 8px;
  border: 1px solid #ccc;
  border-radius: 4px;
  background-color: transparent;
  /*font-size: 20px;*/
  text-align: left;
  color: #333;
  /*line-height: 24px;*/
  overflow: auto;
}

.profile-editor,
.profile-viewer {
  display: flex;
  flex-direction: column;
  width: 32%;
  margin-bottom: 10px;
  font-size: 20px;
  color: #333;
  text-align: left;
}

input,
select {
  padding: 8px;
  border: 1px solid #ccc;
  border-radius: 4px;
  background-color: transparent;
  height: 50px;
  /* 增加输入框和选择框的高度 */
  font-size: 16px;
  width: 100%;
}

.modal {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background-color: rgba(0, 0, 0, 0.7);
  display: flex;
  justify-content: center;
  align-items: center;
  z-index: 1000;
}

.large-avatar {
  max-width: 90%;
  max-height: 90%;
  border-radius: 10px;
}

.signature-editor {
  display: flex;
  flex-direction: column;
  width: 100%;
  margin-bottom: 10px;
}

.signature-textarea textarea {
  height: 50px;
  font-size: 20px;
  line-height: 1.5;
  width: 100%;
  border-radius: 4px;
  background-color: transparent;
  resize: none;
  border: 1px solid #ccc;
  padding: 10px;
}

.profile-actions {
  display: flex;
  justify-content: flex-end;
  margin-top: 20px;
  font-size: 20px;
}

.cancel-button,
.save-button {
  padding: 10px 20px;
  margin-left: 10px;
  border-radius: 4px;
  cursor: pointer;
}

.cancel-button {
  background-color: #d9534f;
  color: white;
}

.save-button {
  background-color: #5cb85c;
  color: white;
}

.balance-display {
  display: flex;
  align-items: center;
  /* 垂直居中 */
}

.balance-amount {
  font-size: 24px;
  font-weight: bold;
  color: #333;
  margin-bottom: 10px;
  /* 移除默认的外边距 */
  text-align: center;
  /* 文本居中 */
  line-height: 1.5;
  /* 调整行高 */
  background: none;
  /* 移除背景 */
  border: none;
  /* 移除边框 */
}

.balance-records-container {
  width: 100%;
  max-height: 60vh;
  overflow-y: auto;
  /* 垂直滚动条 */
  margin-top: 20px;
}

.balance-records {
  width: 100%;
  border-collapse: collapse;
}

.balance-records th,
.balance-records td {
  border: 1px solid #dbd7d7;
  padding: 8px;
  text-align: center;
  font-size: 18px;
}

.balance-records tr:nth-child(even) {
  background-color: #f2f2f2;
}

.balance-records tr:hover {
  background-color: #ddd;
}

.positive {
  color: #005800;
  font-size: 16px !important;
}

.negative {
  color: #910000;
  font-size: 16px !important;
}

h3 {
  font-size: 20px;
  color: #333;
  text-align: left;
}

/* 针对 input 输入框 */
input[type="text"],
input[type="number"],
select {
  font-size: 20px;
  padding: 8px;
  border-radius: 4px;
  border: 1px solid #ccc;
  width: 100%;
  box-sizing: border-box;
}

/* 强制应用样式到 EditableField 组件内部 */
::v-deep .editable-field input {
  font-size: 20px;
  /* 设置字体大小 */
  padding: 8px;
  /* 添加内边距 */
  border-radius: 4px;
  border: 1px solid #ccc;
  width: 100%;
  box-sizing: border-box;
}

.back-button-container {
  position: absolute;
  top: 10px;
  /* 调整为你需要的上边距 */
  left: 10px;
  /* 调整为你需要的左边距 */
  z-index: 1000;
}

.button-container {
  text-align: left;
  /* 确保按钮靠左对齐 */
}

.toggle-button {
  background-color: #43b984;
  /* 按钮背景颜色 */
  color: #ffffff;
  /* 按钮文字颜色 */
  border: none;
  padding: 10px 20px;
  margin-bottom: 10px;
  border-radius: 5px;
  cursor: pointer;
  font-size: 16px;
  transition: background-color 0.3s ease, transform 0.2s ease;
}

.toggle-button:hover {
  background-color: #296446;
  /* 悬停时的背景颜色 */
}

.toggle-button:active {
  transform: scale(0.98);
  /* 点击时的缩小效果 */
}

.post-list,
.vitality-balance,
.achievement-section {
  margin-bottom: 20px;
  /* 为两个部分添加一些下边距 */
  max-height: 500px;
  overflow-y: auto;
}

.achievement-container {
  margin-top: 30px;
  display: flex;
  flex-wrap: wrap;
  gap: 10px;
  justify-content: center;
}

.achievement-item-wrapper {
  position: relative;
  /* 确保 tooltip 能正确定位 */
  width: 20%;
  /* 宽度为父容器的20% */
  aspect-ratio: 1 / 1;
  /* 保持宽高比为1:1，确保容器为正方形 */
}

.achievement-item {
  position: relative;
  width: 80%;
  /* 宽度为父容器的100% */
  aspect-ratio: 1 / 1;
  /* 保持宽高比为1:1，确保容器为正方形 */
  display: flex;
  justify-content: center;
  align-items: center;
  overflow: hidden;
  /* 确保遮罩层不会超出图片边界 */
  border-radius: 50%;
  /* 保持圆形 */
  background-color: transparent;
  cursor: pointer;
}

.achievement-item img {
  width: 100%;
  /* 宽度占满容器 */
  height: 100%;
  /* 高度占满容器 */
  object-fit: cover;
  /* 确保图片填满容器且不失真 */
  border-radius: 50%;
  position: relative;
  z-index: 1;
}

.achievement-item::before {
  content: "";
  position: absolute;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background: rgba(0, 0, 0, 0.5);
  /* 半透明黑色遮罩 */
  border-radius: 50%;
  /* 保持与图片一致的圆形 */
  z-index: 2;
  transform-origin: bottom;
  transform: scaleY(calc(1 - var(--progress)));
  /* 根据进度动态调整 */
  transition: transform 0.3s ease;
  /* 添加动画效果 */
}

.achievement-item:hover::before {
  transform: scaleY(0);
  /* 悬停时显示完整图片 */
}

.tooltip {
  position: absolute;
  bottom: 0%;
  /* 确保 tooltip 在 achievement-item 的上方 */
  left: 100%;
  transform: translateX(-50%);
  background-color: rgba(0, 0, 0, 0.8);
  color: #fff;
  padding: 10px;
  border-radius: 4px;
  white-space: nowrap;
  z-index: 1000;
  display: block;
}

.achievement-content {
  height: 40px;
  margin: 0px 0;
  padding: 8px;
  border: 1px solid #ccc;
  border-radius: 4px;
  background-color: transparent;
  text-align: left;
}

.post-item {
  background-color: rgba(255, 255, 255, 0.6);
  color: #000;
  padding: 20px;
  margin-bottom: 20px;
  border-radius: 5px;
  border: 2px solid #ddd;
}

.post-content {
  text-align: left;
}

.post-title {
  font-size: 18px;
  color: #007bff;
  margin-bottom: 10px;
  cursor: pointer;
}

.post-title .category-tag {
  background-color: #f0f0f0;
  border-radius: 50px;
  padding: 3px 8px;
  font-size: 12px;
  color: #555;
  margin-left: 10px;
}

.post-snippet {
  font-size: 16px;
  color: #666;
}

.post-footer {
  margin-top: 15px;
  display: flex;
  justify-content: space-between;
  align-items: center;
  font-size: 14px;
  color: #888;
  gap: 20px;
}

.post-actions {
  color: blue;
  display: flex;
  gap: 15px;
  align-items: center;
}

.icon-with-text {
  display: flex;
  align-items: center;
  gap: 5px;
}

.icon-with-text.no-click {
  pointer-events: none;
  cursor: default;
}

.icon-with-text-delete {
  display: flex;
  align-items: center;
  gap: 5px;
  cursor: pointer;
}

.post-image {
  text-align: left;
  margin: 10px 0;
}

.post-image .image {
  width: 100px;
  /* 固定宽度 */
  height: auto;
  /* 高度自动调整以保持比例 */
  border-radius: 5px;
  display: inline-block;
}
.popup-overlay {
  position: fixed;
  top: 50%; /* 使弹窗垂直居中 */
  left: 50%; /* 使弹窗水平居中 */
  transform: translate(-50%, -50%); /* 将弹窗偏移，确保它在网页的中间 */
  width: auto;
  height: auto;
  background: rgba(0, 0, 0, 0y); /* 半透明背景 */
  display: flex;
  justify-content: center;
  align-items: center;
  opacity: 0; /* 初始透明度 */
  animation: fadeIn 0.3s forwards ease-in-out; /* 背景淡入动画 */
  z-index: 10000; /* 确保弹窗内容在弹窗背景之上 */
}

.popup-content {
  background: #fff;
  padding: 20px;
  border-radius: 8px;
  position: relative;
  width: auto;
  max-width: 1000px; /* 设置最大宽度 */
  height: auto;
  max-height: 1000px; /* 设置最大高度 */
  overflow-y: auto;
  transform: scale(0.3); /* 初始缩放比例 */
  opacity: 0; /* 初始透明度 */
  animation: popupZoomIn 1s forwards ease-in-out; /* 弹窗缩放动画 */
  z-index: 10000; /* 确保弹窗内容在弹窗背景之上 */
}

/* 弹窗的缩放动画 */
@keyframes popupZoomIn {
  0% {
    transform: scale(0.8); /* 初始缩小到80% */
    opacity: 0; /* 初始透明 */
  }
  100% {
    transform: scale(1); /* 最终放大至正常比例 */
    opacity: 1; /* 完全显示 */
  }
}

/* 背景淡入动画 */
@keyframes fadeIn {
  0% {
    opacity: 0; /* 初始透明 */
  }
  100% {
    opacity: 1; /* 完全显示 */
  }
}
</style>