import { createRouter, createWebHistory } from "vue-router";
import HomeView from "../views/HomeView.vue";
import LoginView from "../views/LoginView.vue";
import SignUpView from "../views/SignUpView.vue";
import CourseView from "../views/CourseView.vue";
import CoursesView from "../views/CoursesView.vue";
import UserProfile from "../views/UserProfile.vue";
import AdminView from "../views/AdminView.vue";
import CoursePublishView from "../views/CoursePublishView.vue";
import ShopView from "../views/ShopView.vue"
import CalendarView from "../views/CalendarView.vue"
import ShoppingCartView from "../views/ShoppingCartView.vue"
import HistoryView from "../views/HistoryView.vue";
import ClassesView from "../views/ClassesView.vue";
import ClassView from "../views/ClassView.vue";
import MyHistoryView from "../views/MyHistoryView.vue";
import MyShopView from "../views/MyShopView.vue";


const router = createRouter({
  history: createWebHistory(),
  routes: [
    //首页
    {
      //path: '/',
      path: "/",
      name: "no_login_home",
      component: HomeView,
    },
    {
      //path: '/',
      path: "/home",
      name: "home",
      component: HomeView,
    },
    //课程商店
    {
      path: "/shop",
      name: "shop",
      component: ShopView,
    },
    {
      path: "/shop/:courseID",
      name: "myshop",
      component: MyShopView,
      props: route => {
        const classData = route.query.classData ? JSON.parse(route.query.classData) : null;
        return {
          courseID: route.params.courseID,
          classData: classData
        };
      }
    },
    //课程日历
    {
      path: "/calendar",
      name: "calendar",
      component: CalendarView,
    },
    //课程列表
    {
      path: "/courses",
      name: "courses",
      component: CoursesView,
    },
    //课程界面
    {
      path: "/courses/:courseId",
      name: "course",
      component: CourseView,
    },
    //班级列表界面
    {
      path: "/classes",
      name: "classes",
      component: ClassesView,
    },
    //班级界面
    {
      path: "/classes/:classId",
      name: "class",
      component: ClassView,
      props: route => {
        const courseId = route.query.courseId
        return {
          courseId: courseId
        };
      }
    },
    {
      path:"/courses/coursePublish",
      name: "coursePublish",
      component: CoursePublishView,
    },
    {
      path: "/calendar",
      name: "calendar",
      component: CalendarView,
    },
    {
      path: "/shoppingCart",
      name: "shoppingCart",
      component: ShoppingCartView,
    },
    {
      path: "/history",
      name: "history",
      component: HistoryView,
    },
    {
      path: "/history/:classId",
      name: "myhistory",
      component: MyHistoryView,
      props: route => {
        // 获取路由参数中的 paymentID 和 query 中的 historyPayment
        const historyPayment = route.query.historyPayment ? JSON.parse(route.query.historyPayment) : null;
        return {
          classId: route.params.classId,
          historyPayment: historyPayment
        };
      }
    },
    // 登录界面
    {
      //path: '/login',
      path: "/login",
      name: "LoginView",
      component: LoginView,
    },
    // 注册界面
    {
      path: "/signup",
      name: "SignUpView",
      component: SignUpView,
    },
    // 用户信息界面
    {
      path: "/user/:userID",
      //path: '/',
      name: "UserProfile",
      component: UserProfile,
      props: true,
    },
    // 管理员界面
    {
      path: "/admin",
      //path: '/',
      name: "AdminView",
      component: AdminView,
      props: true,
    },
  ],
});

export default router;
