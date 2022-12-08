<template>
<div id="webContent">

  <n-page-header title="添加快递订单" @back="backPage()"></n-page-header>
  <div id="inputContent" style="margin-top: 5px;display:table">
    <p>序号</p>
    <n-input v-model:value="valPackageID" placeholder="添加则不填"></n-input>
    <p>订单 ID</p>
    <n-input v-model:value="valPackageCompany" placeholder=""></n-input>
    <p>起始地</p>
    <n-input v-model:value="valStartPlace" placeholder=""></n-input>
    <p>抵达地</p>
    <n-input v-model:value="valEndPlace" placeholder=""></n-input>
    <p>状态</p>
    <n-input v-model:value="valStatus" placeholder=""></n-input>
    <p>发件人</p>
    <n-input v-model:value="valSender" placeholder=""></n-input>
    <p>收件人</p>
    <n-input v-model:value="valReceiver" placeholder=""></n-input>
    
  </div>
  <div id="buttonContent" style="margin-top: 40px">
    <n-button type="primary" id="btnLogin" @click="registerProcess()">添加 / 更新</n-button>
  </div>
  
</div>
  
</template>

<script>
import { NButton,NInput, NPageHeader, useMessage } from 'naive-ui'
import { useRouter } from 'vue-router';
import {ref} from 'vue'
import API from '../axiosInstance.js'
export default {
  name: 'DeliveryAdd',
  components: {
    NButton, NInput, NPageHeader
  },
  setup() {
    const router = useRouter();
    window.$message = useMessage();
    return {
      valPackageID: ref(null),
      valPackageCompany: ref(null),
      valStartPlace: ref(null),
      valEndPlace: ref(null),
      valStatus: ref(null),
      valSender: ref(null),
      valReceiver: ref(null),
      backPage() {
        router.go(-1);
      },
      registerProcess() {
        console.log(this.valPackageID);
        if(this.valPackageID==null || this.valPackageID==''){
        console.log("=> Go Package Add API");
        API.post('/server/api/package/add',{
          'orderid': this.valPackageCompany,
          'startplace': this.valStartPlace,
          'endplace': this.valEndPlace,
          'status': this.valStatus,
          'sender': this.valSender,
          'receiver': this.valReceiver
        }).then((res) => {
          if(res['data']=='success'){
            window.$message.info("添加成功");
          }
        }).catch((res) => {
            console.debug(res);
            window.$message.error("Error");
        })

        } else {
          console.log("=> Go Package Update API");
          API.post('/server/api/package/update',{
          'id': this.valPackageID,
          'orderid': this.valPackageCompany,
          'startplace': this.valStartPlace,
          'endplace': this.valEndPlace,
          'status': this.valStatus,
          'sender': this.valSender,
          'receiver': this.valReceiver
        }).then((res) => {
          if(res['data']=='success'){
            window.$message.info("更新成功");
          }
        }).catch((res) => {
            console.debug(res);
            window.$message.error("更新失败");
        })
          return;
        }
        
      }
    }
  }
}
</script>

<style scoped>
#webContent {
  margin: 14px 20px 20px 50px;
}

</style>
