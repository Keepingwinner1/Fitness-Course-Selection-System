import{createPinia,defineStore} from'pinia'
import {ref} from "vue";
const pinia = createPinia()
export default pinia

export const useFitStore = defineStore('store',()=>{
  const token=ref()
  const role=ref()
  const userID=ref()
  const gymID=ref()
  const iconURL=ref()
  const coachID=ref()
  function clear(){
    token.value=null
    role.value=null
    userID.value=null
    gymID.value=null
    iconURL.value=null
    coachID.value=null
  }
  return{
    token,role,userID,gymID,iconURL,coachID,
    clear
  }
}
);
