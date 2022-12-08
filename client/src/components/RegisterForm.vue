<template>
<div id="webContent">

  <n-page-header title="用户注册" @back="backPage()"></n-page-header>
  <div id="inputContent" style="margin-top: 20px;display:table">
    <n-input style="margin-top: 10px; max-width:500px;" id="inputUsername" v-model:value="valUserName" type="text" placeholder="用户名 / Username" />
    <n-input style="margin-top: 10px; max-width:500px;" id="inputPassword" v-model:value="valPasswd" type="password" placeholder="密码 / Password" />
    <n-input style="margin-top: 10px; max-width:500px;" id="inputPasswordRepeat" v-model:value="valPasswdRp" type="password" placeholder="重复密码 / Repeat Password" />
    <n-input style="margin-top: 10px; max-width:500px;" id="inputContact" v-model:value="valContact" type="text" placeholder="联系方式 / Phone & Email" />
  </div>
  <p style="margin-top: 30px">用户名的最长长度为 7 位且不能重复，请设定一个 7 ~ 30 位的密码。</p>
  <div id="buttonContent" style="margin-top: 20px">
    <n-button type="primary" :disabled="boolRegisterButton" id="btnLogin" @click="registerProcess()">注册</n-button>
  </div>
  
</div>
  
</template>

<script>
import { NButton,NInput, NPageHeader, useMessage } from 'naive-ui'
import { useRouter } from 'vue-router';
import {ref} from 'vue'
import API from '../axiosInstance.js'
export default {
  name: 'RegisterForm',
  components: {
    NButton, NInput, NPageHeader
  },
  setup() {
    const router = useRouter();
    window.$message = useMessage();
    return {
      valUserName: ref(null),
      valPasswd: ref(null),
      valPasswdRp: ref(null),
      valContact: ref(null),
      boolRegisterButton: ref(false),
      backPage() {
        router.go(-1);
      },
      registerProcess() {
        console.debug(this.valUserName);
        if(this.valUserName == null || this.valPasswd == null || this.valPasswdRp == null || this.valContact == null){
          window.$message.warning("请将信息填写完整");
          return;
        }
        if(this.valPasswd != this.valPasswdRp){
          window.$message.warning("两次填写的密码不一致。");
          return;
        }
        API.post('/server/register',{
          username: this.valUserName,
          passwd: this.valPasswd,
          contact: this.valContact
        }).then((res) => {
          console.debug(res);
          if(res["data"]=="success"){
          // 注册完等待一会儿，此处降低值可以改善软件运行速度
          setTimeout(() => {
            router.push("/login");
          },3000);
          window.$message.success("注册完成");
          this.boolRegisterButton = true;
          } else if(res["data"]=="failure") {
            window.$message.warning("用户名已被使用");
          } else {
            window.$message.warning("注册失败，请稍后再试");
          }
          
        }).catch((res) => {
          console.debug(res);
          window.$message.error(res["message"]);
          window.$message.error("注册过程中遇到问题，请联系支持或稍后再试");
        });
      }
    }
  }
}
</script>

<style scoped>
#webContent {
  margin: 80px 20px 20px 50px;
}

</style>
