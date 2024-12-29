import axios from "axios";
import {useFitStore} from "../store/index";

/*
用户相关api
 */
export async function Register(userName:string,password:string,userType:string,gymID:number=null,gymName:string=null,gymAddress:string=null): Promise<User | null>{
    let data={}
    if(gymID==null&&gymName==null){
        data={
            userName: userName,
            password: password,
            type: userType,
        }
    }
    else if(gymID==null&&gymName!=null){
        data={
            userName: userName,
            password: password,
            type: userType,
            gymName: gymName,
            address: gymAddress,
        }
    }
    else{
        data={
            userName: userName,
            password: password,
            type: userType,
            gymID: gymID,
        }
    }

    let config = {
        method: 'post',
        url: 'http://localhost:8080/user/register',
        headers:{
            'alg':'SH256',
            'type':'JWT',
        },
        data : data
    };
    try {
        const response = await axios.request(config)
        if(response.data&&response.data.code==200){
            console.log(response.data.data)
            return response.data.data
        }
    }
    catch (error){
        console.log(error)
    }
    return null
}

export async function userLogIn(userName:string,password:string): Promise<User | null>{
    let config = {
        method: 'post',
        url: 'http://localhost:8080/user/login',
        headers:{
            'alg':'SH256',
            'type':'JWT',
        },
        data : {
            userName: userName,
            password: password,
            type:'user',
        }
    };
    try {
        const response = await axios.request(config)
        if(response.data&&response.data.code==200){
            return response.data.data
        }
    }
    catch (error){
        console.log(error)
    }
    return null
}

export async function getUserProfile(userID:number):Promise<Profile | null>{
    let config = {
        method: 'get',
        url: `http://localhost:8080/user/profile/${userID}`,
        headers:{
            'alg':'SH256',
            'type':'JWT',
            'Authorization': `Bearer ${useFitStore().token}`
        },
    };
    try {
        const response = await axios.request(config)
        if(response.data&&response.data.code==200){
            return response.data.data
        }
    }
    catch (error){
        console.log(error)
    }
    return null
}

export async function editProfile(profile:Profile){
    let config = {
        method: 'put',
        url: 'http://localhost:8080/user/profile/edit',
        headers:{
            'alg':'SH256',
            'type':'JWT',
            'Authorization': `Bearer ${useFitStore().token}`
        },
        data : profile
    };

    try {
        const response = await axios.request(config)
        if(response.data&&response.data.code==200){
            return response.data.data
        }
    }
    catch (error){
        console.log(error)
    }
    return null
}

export async function updateToken(){
    let config = {
        method: 'post',
        url: 'http://localhost:8080/user/updateToken',
        headers:{
            'alg':'SH256',
            'type':'JWT',
            'Authorization': `Bearer ${useFitStore().token}`
        },
        data : useFitStore().token
    };
    try {
        const response = await axios.request(config)
        if(response.data&&response.data.code==200){
            return response.data.data
        }
    }
    catch (error){
        console.log(error)
    }
    return null
}
/*
教练相关api
*/
export async function coachRegister(userID:number,realName:string,number:string,phone:string,address:string,cardID:string,gymID:string){
    let config = {
        method: 'post',
        url: 'http://localhost:8080/coach/applyForGym',
        headers: {
            'Content-Type': 'application/json',
            'alg':'SH256',
            'type':'JWT',
            'Authorization': `Bearer ${useFitStore().token}`
        },
        data : {
            userID: userID,
            realName: realName,
            number:number,
            phone:phone,
            address:address,
            cardID:cardID,
            gymID:gymID
        }
    };
    try {
        const response = await axios.request(config)
        if(response.data&&response.data.code==200){
            return response.data
        }
    }
    catch (error){
        console.log(error)
    }
    return null
}

export async function coachLogIn(userName:string,password:string):Promise<Coach | null>{
    let config = {
        method: 'post',
        url: 'http://localhost:8080/coach/login',
        headers: {
            'Content-Type': 'application/json',
            'alg':'SH256',
            'type':'JWT',
        },
        data : {
            userName: userName,
            password: password,
            type:'coach',
        }
    };
    try {
        const response = await axios.request(config)
        if(response.data&&response.data.code==200){
            return response.data.data
        }
    }
    catch (error){
        console.log(error)
    }
    return null
}

export async function createCourse(courseType:string,courseName:string,courseDescription:string,coursePhotoUrl:string): Promise<Course | null>{
    let config = {
        method: 'post',
        url: 'http://localhost:8080/coach/createCourse',
        headers: {
            'Content-Type': 'application/json',
            'alg':'SH256',
            'type':'JWT',
            'Authorization': `Bearer ${useFitStore().token}`
        },
        data : {
            courseType: courseType,
            courseName: courseName,
            courseDescription: courseDescription,
            coursePhotoUrl: coursePhotoUrl,
            coachID: useFitStore().coachID,
        }
    };
    try {
        const response = await axios.request(config)
        if(response.data&&response.data.code==200){
            return response.data
        }
    }
    catch (error){
        console.log(error)
    }
    return null
}

export async function getCourses(coachID:number):Promise<Course | null>{
    let config = {
        method: 'get',
        url: `http://localhost:8080/coach/getAllCourse/${coachID}`,
        headers: {
            'Content-Type': 'application/json',
            'alg':'SH256',
            'type':'JWT',
            'Authorization': `Bearer ${useFitStore().token}`
        },
        data : {
            coachId: useFitStore().coachID,
        }
    };
    try {
        const response = await axios.request(config)
        if(response.data&&response.data.code==200){
            return response.data.data
        }
    }
    catch (error){
        console.log(error)
    }
    return null
}

export async function getClassByCourseID(courseID:number){
    let config = {
        method: 'post',
        url: `http://localhost:8080/coach/getClassByCourseID`,
        headers: {
            'Content-Type': 'application/json',
            'alg':'SH256',
            'type':'JWT',
            'Authorization': `Bearer ${useFitStore().token}`
        },
        data : {
            courseID:courseID,
            coachID: useFitStore().coachID,
        }
    };
    try {
        const response = await axios.request(config)
        if(response.data&&response.data.code==200){
            return response.data.data
        }
    }
    catch (error){
        console.log(error)
    }
    return null
}

export async function getCourseByCourseId(courseID:number){
    let config = {
        method: 'get',
        url: `http://localhost:8080/coach/getCourseByCourseID/${courseID}`,
        headers: {
            'Content-Type': 'application/json',
            'alg':'SH256',
            'type':'JWT',
            'Authorization': `Bearer ${useFitStore().token}`
        },
        data : {
            courseID: courseID,
        }
    };
    try {
        const response = await axios.request(config)
        if(response.data&&response.data.code==200){
            return response.data.data
        }
    }
    catch (error){
        console.log(error)
    }
    return null
}

export async function getClassByClassID(classID:number){
    let config = {
        method: 'get',
        url: `http://localhost:8080/courses/class/${classID}`,
        headers: {
            'Content-Type': 'application/json',
            'alg':'SH256',
            'type':'JWT',
            'Authorization': `Bearer ${useFitStore().token}`
        },
        data : {
            classID: classID,
        }
    };
    try {
        const response = await axios.request(config)
        if(response.data&&response.data.code==200){
            return response.data.data
        }
    }
    catch (error){
        console.log(error)
    }
    return null
}

export async function createClass(coachID:number,courseId:number,capacity:number,coursePrice:number,courseStartTime:Date,courseEndTime:Date,dayOfWeek:string){
    let config = {
        method: 'post',
        url: `http://localhost:8080/coach/createClass`,
        headers: {
            'Content-Type': 'application/json',
            'alg':'SH256',
            'type':'JWT',
            'Authorization': `Bearer ${useFitStore().token}`
        },
        data : {
            coachID:coachID,
            courseId: courseId,
            capacity:capacity,
            coursePrice:coursePrice,
            courseStartTime:courseStartTime,
            courseEndTime:courseEndTime,
            dayOfWeek:dayOfWeek,
        }
    };
    try {
        const response = await axios.request(config)
        if(response.data&&response.data.code==200){
            return response.data.data
        }
    }
    catch (error){
        console.log(error)
    }
    return null
}

export async function getClassStu(classID:number){
    let config = {
        method: 'get',
        url: `http://localhost:8080/coach/getClassStu/${classID}`,
        headers: {
            'Content-Type': 'application/json',
            'alg':'SH256',
            'type':'JWT',
            'Authorization': `Bearer ${useFitStore().token}`
        },
    };
    try {
        const response = await axios.request(config)
        if(response.data&&response.data.code==200){
            return response.data.data
        }
    }
    catch (error){
        console.log(error)
    }
    return null
}

export async function modifyClass(classID:number,coachID:number,courseId:number,capacity:number,coursePrice:number,courseStartTime:Date,courseEndTime:Date,dayOfWeek:string){
    let config = {
        method: 'post',
        url: `http://localhost:8080/coach/modifyClass/${classID}`,
        headers: {
            'Content-Type': 'application/json',
            'alg':'SH256',
            'type':'JWT',
            'Authorization': `Bearer ${useFitStore().token}`
        },
        data : {
            coachID:coachID,
            courseId: courseId,
            capacity:capacity,
            coursePrice:coursePrice,
            courseStartTime:courseStartTime,
            courseEndTime:courseEndTime,
            dayOfWeek:dayOfWeek,
        }
    };
    try {
        const response = await axios.request(config)
        if(response.data&&response.data.code==200){
            return response.data.data
        }
    }
    catch (error){
        console.log(error)
    }
    return null
}

export async function getTasks(classID:number){
    let config = {
        method: 'get',
        url: `http://localhost:8080/courses/getAllTasks/${classID}`,
        headers: {
            'Content-Type': 'application/json',
            'alg':'SH256',
            'type':'JWT',
            'Authorization': `Bearer ${useFitStore().token}`
        },
    };
    try {
        const response = await axios.request(config)
        if(response.data&&response.data.code==200){
            return response.data.data
        }
    }
    catch (error){
        console.log(error)
    }
    return null
}

export async function modifyTask(taskID:number,classID:number,taskName:string,description:string,publishTime:Date,endTime:Date){
    let config = {
        method: 'post',
        url: `http://localhost:8080/coach/modifyTask`,
        headers: {
            'Content-Type': 'application/json',
            'alg':'SH256',
            'type':'JWT',
            'Authorization': `Bearer ${useFitStore().token}`
        },
        data : {
            taskID:taskID,
            classID: classID,
            taskName:taskName,
            description:description,
            publishTime:publishTime,
            endTime:endTime,
        }
    };
    try {
        const response = await axios.request(config)
        if(response.data&&response.data.code==200){
            return response.data
        }
    }
    catch (error){
        console.log(error)
    }
    return null
}

export async function deleteTaskById(taskID:number){
    let config = {
        method: 'delete',
        url: `http://localhost:8080/coach/deleteTask/${taskID}`,
        headers: {
            'Content-Type': 'application/json',
            'alg':'SH256',
            'type':'JWT',
            'Authorization': `Bearer ${useFitStore().token}`
        },
    };
    try {
        const response = await axios.request(config)
        if(response.data&&response.data.code==200){
            return response.data
        }
    }
    catch (error){
        console.log(error)
    }
    return null
}

export async function createTask(classID:number,taskName:string,description:string,publishTime:Date,endTime:Date){
    let config = {
        method: 'post',
        url: `http://localhost:8080/coach/createTask`,
        headers: {
            'Content-Type': 'application/json',
            'alg':'SH256',
            'type':'JWT',
            'Authorization': `Bearer ${useFitStore().token}`
        },
        data : {
            classID: classID,
            taskName:taskName,
            description:description,
            publishTime:publishTime,
            endTime:endTime,
        }
    };
    try {
        const response = await axios.request(config)
        if(response.data&&response.data.code==200){
            return response.data
        }
    }
    catch (error){
        console.log(error)
    }
    return null
}

export async function evaluate(userId:number,classId:number,grade:number,evaluate:string){
    let config = {
        method: 'post',
        url: `http://localhost:8080/courses/evaluate`,
        headers: {
            'Content-Type': 'application/json',
            'alg':'SH256',
            'type':'JWT',
            'Authorization': `Bearer ${useFitStore().token}`
        },
        data : {
            traineeId:userId,
            classId: classId,
            grade:grade,
            evaluate:evaluate,
        }
    };
    try {
        const response = await axios.request(config)
        if(response.data&&response.data.code==200){
            return response.data
        }
    }
    catch (error){
        console.log(error)
    }
    return null
}

export async function getComments(classId:number){
    let config = {
        method: 'get',
        url: `http://localhost:8080/courses/evaluate/${classId}`,
        headers:{
            'alg':'SH256',
            'type':'JWT',
            'Authorization': `Bearer ${useFitStore().token}`
        },
    };
    try {
        const response = await axios.request(config)
        if(response.data&&response.data.code==200){
            return response.data.data
        }
    }
    catch (error){
        console.log(error)
    }
    return null
}
/*
管理员相关api
 */
export async function adminLogIn(userName:string,password:string):Promise<Admin | null>{
    let config = {
        method: 'post',
        url: 'http://localhost:8080/admin/login',
        headers: {
            'Content-Type': 'application/json',
            'alg':'SH256',
            'type':'JWT',
        },
        data : {
            userName: userName,
            password: password,
            type:'admin',
        }
    };
    try {
        const response = await axios.request(config)
        if(response.data&&response.data.code==200){
            return response.data.data
        }
    }
    catch (error){
        console.log(error)
    }
    return null
}
//获取所有申请加入的教练
export async function getAllApplyCoach(gymID:number){
    let config = {
        method:"GET",
        url:`http://localhost:8080/admin/getAllApplyCoach/${gymID}`,
        headers: {
            'Content-Type': 'application/json',
            'alg':'SH256',
            'type':'JWT',
            'Authorization': `Bearer ${useFitStore().token}`
        },
    }
    try {
        const response = await axios.request(config);
        console.log("************")
        console.log(response.data.data)
        console.log("************")
        return response.data.data;  // 返回结果
    } catch (error) {
        console.error(error);
        return null;  // 发生错误时返回 null
    }
}
//预订课程
export async function bookcourse(classId:number,traineeId:number){
    let config = {
        method:"POST",
        url:'http://localhost:8080/courses/book',
        headers: {
            'Content-Type': 'application/json',
            'alg':'SH256',
            'type':'JWT',
            'Authorization': `Bearer ${useFitStore().token}`
        },
        data : {
            classId:classId,
            traineeId:traineeId
        }
    }
    try {
        const response = await axios.request(config);
        return response.data.data;  // 返回结果
    } catch (error) {
        console.error(error);
        return null;  // 发生错误时返回 null
    }
}
//取消预订课程
export async function cancel(bookId:number){
    let config = {
        method:"DELETE",
        url:`http://localhost:8080/courses/book/${bookId}`,
        headers: {
            'Content-Type': 'application/json',
            'alg':'SH256',
            'type':'JWT',
            'Authorization': `Bearer ${useFitStore().token}`
        },
    }
    try {
        const response = await axios.request(config);
        return response.data.message;
    } catch (error) {
        console.error(error);
        return null;  // 发生错误时返回 null
    }
}
//支付所选课程费用
export async function payBookCourse(bookIdList:Array<number>, userId:number,amount:number,payMethod:string){
    let config = {
        method:"POST",
        url:'http://localhost:8080/courses/pay',
        headers: {
            'Content-Type': 'application/json',
            'alg':'SH256',
            'type':'JWT',
            'Authorization': `Bearer ${useFitStore().token}`
        },
        data : {
            bookIdList:bookIdList,
            userId:userId,
            amount:amount,
            payMethod:payMethod,
        }
    }
    try {
        const response = await axios.request(config);
        return response.data.data;  // 返回结果
    } catch (error) {
        console.error(error);
        return null;  // 发生错误时返回 null
    }
}
//修改教练个人信息
export async function modifyCoach(userID:number,realName:string,number:string,phone:string,address:string,cardID:string,gymID:number){
    let config = {
        method:"PUT",
        url:'http://localhost:8080/coach/modifyCoach',
        headers: {
            'Content-Type': 'application/json',
            'alg':'SH256',
            'type':'JWT',
            'Authorization': `Bearer ${useFitStore().token}`
        },
        data : {
            userID: userID,
            realName:realName,
            number: number,
            phone:phone,
            address:address,
            cardID:cardID,
            gymID:gymID,
        }
    }
    try {
        const response = await axios.request(config);
        return response.data.message;
    } catch (error) {
        console.error(error);
        return null;  // 发生错误时返回 null
    }
}
//通过/拒绝教练的加入申请
export async function proCoach(processId:number,handle:boolean){
    let config = {
        method:"PUT",
        url:'http://localhost:8080/admin/proCoach',
        headers: {
            'Content-Type': 'application/json',
            'alg':'SH256',
            'type':'JWT',
            'Authorization': `Bearer ${useFitStore().token}`
        },
        data : {
            processId:processId,
            handle:handle
        }
    }
    try {
        const response = await axios.request(config);
        return response.data.message;  // 返回结果
    } catch (error) {
        console.error(error);
        return null;  // 发生错误时返回 null
    }
}
//获取所有退款申请
export async function getAllRefund(gymID:number){
    let config = {
        method:"GET",
        url:`http://localhost:8080/admin/getAllRefund/${gymID}`,
        headers: {
            'Content-Type': 'application/json',
            'alg':'SH256',
            'type':'JWT',
            'Authorization': `Bearer ${useFitStore().token}`
        },
    }
    try {
        const response = await axios.request(config);
        console.log("****")
        console.log(response.data.data)
        console.log("8888")
        return response.data.data;  // 返回结果
    } catch (error) {
        console.error(error);
        return null;  // 发生错误时返回 null
    }
}
//处理退款申请
export async function solveRefund(processId:number,handle:boolean){
    let config = {
        method:"PUT",
        url:'http://localhost:8080/admin/proRefund',
        headers: {
            'Content-Type': 'application/json',
            'alg':'SH256',
            'type':'JWT',
            'Authorization': `Bearer ${useFitStore().token}`
        },
        data : {
            processId:processId,
            handle:handle
        }
    }
    try {
        const response = await axios.request(config);
        return response.data.message;  // 返回结果
    } catch (error) {
        console.error(error);
        return null;  // 发生错误时返回 null
    }
}
//获取所有待审核的课程
export async function getAllClassCreate(gymId:number){
    let config = {
        method:"GET",
        url:`http://localhost:8080/admin/getAllClassCreate/${gymId}`,
        headers: {
            'Content-Type': 'application/json',
            'alg':'SH256',
            'type':'JWT',
            'Authorization': `Bearer ${useFitStore().token}`
        },
    }
    try {
        const response = await axios.request(config);
        const myClassCreate: ClassCreate = response.data.data;
        return myClassCreate;  // 返回结果
    } catch (error) {
        console.error(error);
        return null;  // 发生错误时返回 null
    }
}
//处理课程审核
export async function proClassCreate(processId:number,handle:boolean){
    let config = {
        method:"PUT",
        url:'http://localhost:8080/admin/proClassCreate',
        headers: {
            'Content-Type': 'application/json',
            'alg':'SH256',
            'type':'JWT',
            'Authorization': `Bearer ${useFitStore().token}`
        },
        data : {
            processId:processId,
            handle:handle
        }
    }
    try {
        const response = await axios.request(config);
        return response.data.message;  // 返回结果
    } catch (error) {
        console.error(error);
        return null;  // 发生错误时返回 null
    }
}
//获取所有健身房
export async function getAllGym(){
    let config = {
        method:"GET",
        url:'http://localhost:8080/gym/getAllGym',
        headers: {
            'Content-Type': 'application/json',
            'alg':'SH256',
            'type':'JWT',
            'Authorization': `Bearer ${useFitStore().token}`
        },
    }
    try {
        const response = await axios.request(config);
        console.log(response.data.data);
        return response.data.data;  // 返回结果
    } catch (error) {
        console.error(error);
        return null;  // 发生错误时返回 null
    }
}
//获取所有可用课程班级
export async function getAllCourse(userID:number){
    let config = {
        method:"GET",
        url:`http://localhost:8080/courses/allCourse/${userID}`,
        headers: {
            'Content-Type': 'application/json',
            'alg':'SH256',
            'type':'JWT',
            'Authorization': `Bearer ${useFitStore().token}`
        },
    }
    try {
        const response = await axios.request(config);
        return response.data.data;  // 返回结果
    } catch (error) {
        console.error(error);
        return null;  // 发生错误时返回 null
    }
}
//获取已预订但未付款的课程
export async function getAllBooks(userId:number){
    let config = {
        method:"GET",
        url:`http://localhost:8080/courses/user/${userId}/booked`,
        headers: {
            'Content-Type': 'application/json',
            'alg':'SH256',
            'type':'JWT',
            'Authorization': `Bearer ${useFitStore().token}`
        },
    }
    try {
        const response = await axios.request(config);
        return response.data.data;  // 返回结果
    } catch (error) {
        console.error(error);
        return null;  // 发生错误时返回 null
    }
}
//获取已付款但未开始的课程
export async function getAllHistory(userId:number){
    let config = {
        method:"GET",
        url:`http://localhost:8080/courses/user/${userId}/paid`,
        headers: {
            'Content-Type': 'application/json',
            'alg':'SH256',
            'type':'JWT',
            'Authorization': `Bearer ${useFitStore().token}`
        },
    }
    try {
        const response = await axios.request(config);
        return response.data.data;  // 返回结果
    } catch (error) {
        console.error(error);
        return null;  // 发生错误时返回 null
    }
}
//获取已完成的课程
export async function getCompleteCourse(userId:number){
    let config = {
        method:"GET",
        url:`http://localhost:8080/courses/user/${userId}/completed`,
        headers: {
            'Content-Type': 'application/json',
            'alg':'SH256',
            'type':'JWT',
            'Authorization': `Bearer ${useFitStore().token}`
        },
    }
    try {
        const response = await axios.request(config);
        console.log(response.data.data)
        return response.data.data;  // 返回结果
    } catch (error) {
        console.error(error);
        return null;  // 发生错误时返回 null
    }
}
//获取正在参与的课程
export async function getOnGoingCourse(userId:number){
    let config = {
        method:"GET",
        url:`http://localhost:8080/courses/user/${userId}/ongoing`,
        headers: {
            'Content-Type': 'application/json',
            'alg':'SH256',
            'type':'JWT',
            'Authorization': `Bearer ${useFitStore().token}`
        },
    }
    try {
        const response = await axios.request(config);
        console.log(response.data.data)
        return response.data.data;  // 返回结果
    } catch (error) {
        console.error(error);
        return null;  // 发生错误时返回 null
    }
}
// 获取用户已取消的课程
export async function getCancelCourse(userId:number){
    let config = {
        method:"GET",
        url:`http://localhost:8080/courses/user/${userId}/canceled`,
        headers: {
            'Content-Type': 'application/json',
            'alg':'SH256',
            'type':'JWT',
            'Authorization': `Bearer ${useFitStore().token}`
        },
    }
    try {
        const response = await axios.request(config);
        console.log(response.data.data)
        return response.data.data;  // 返回结果
    } catch (error) {
        console.error(error);
        return null;  // 发生错误时返回 null
    }
}
//退款
export async function setRefund(classID:number,userID:number){
    let config = {
        method:"PUT",
        url:`http://localhost:8080/courses/quitCourse/${userID}/${classID}`,
        headers: {
            'Content-Type': 'application/json',
            'alg':'SH256',
            'type':'JWT',
            'Authorization': `Bearer ${useFitStore().token}`
        },
    }
    try {
        const response = await axios.request(config);
        console.log(response.data.data)
        return response.data.data;  // 返回结果
    } catch (error) {
        console.error(error);
        return null;  // 发生错误时返回 null
    }
}
//获得学员所有课程