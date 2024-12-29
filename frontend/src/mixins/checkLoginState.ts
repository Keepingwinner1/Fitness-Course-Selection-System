import {ElNotification} from "element-plus";
import {useFitStore} from "../store";
import router from "../router/index"
import {updateToken} from "../api";
export function checkAvailable(){
    let store = useFitStore()
    let token = store.token
    if (token == null) {
        ElNotification({
            title: '提示',
            message: '请先登录',
            type: 'warning',
            duration: 2000
        })
        store.clear()
        router.push({path: '/login'}).then(r =>{
        });
        return;
    }
    updateToken().then(response=>{
       console.log("token:",response)
    })
}
