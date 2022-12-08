<template>
<div id="webContent">
  <h1 id="titleLogin">用户登录</h1>
  <p>用户端物流查询系统</p>
  <div id="inputContent" style="display: table">
      <n-input style="margin-top: 10px; max-width:500px;" id="inputUsername" v-model:value="valUserName" type="text" placeholder="用户名 / Username" />
      <n-input style="margin-top: 10px; max-width:500px;" id="inputPassword" v-model:value="valPasswd" type="password" placeholder="密码 / Password" />
      <div style="display: none">
        <n-input style="margin-top: 10px; max-width:200px;" id="inputCaptcha" v-model:value="valCaptcha" type="text" placeholder="验证码 / Captcha Code" />
        <img style="margin-left: 20px; margin-top: 13px; width: 50px; height: 30px;" src="@/assets/captchaDemo.png"/>
      </div>
  </div>

  <div id="buttonContent" style="margin-top: 40px">
    <n-button @click="btnLoginClicked()" type="primary" id="btnLogin">登录</n-button>
    <n-button @click="btnRegisterClicked()" style="margin-left: 10px;" id="btnRegister">注册</n-button>

  </div>
  
</div>
<p style="margin: 30px 20px 20px 50px">© 2022 - 2023 ▲ TRIStudio, Powered by Naive-UI / Vue3</p>
</template>

<script>
import {NButton,NInput, useMessage} from 'naive-ui';
import {useRouter} from 'vue-router'
import {ref} from 'vue'
import API from '../axiosInstance.js'
export default {
  name: 'LoginForm',
  components: {
    NButton, NInput
  },

  data() {
  },
  setup() {
    
    // setup 处挂载至 window
    window.$message = useMessage();
    // Vue3 router 引入跳转
    const router = useRouter();


    return {
      // 用户名
      valUserName: ref(null),
      // 密码
      valPasswd: ref(null),
      // 验证码
      valCaptcha: ref(null),
      btnLoginClicked() {
        // 登录过程
        //window.$message.info("loginProcessStarted");
        //window.alert(this.valUserName+"/"+this.valPasswd+"/"+this.valCaptcha);
        
        if(this.valUserName=="" || this.valPasswd==""){
          window.$message.info("请将表单填写完整");
          return;
        }
        
        API.post('/server/login',{'username': this.valUserName, 'passwd': this.valPasswd}
        ).then((res) => {
          if(res.data == "notfound"){
            window.$message.error("没有找到用户");
          } else if(res.data == "failure"){
            window.$message.error("用户名或密码错误");
          } else if(res.data == "success"){
            router.push({path: '/userlogistics', query: { uname: this.valUserName }});
          } else if(res.data == "admin"){
            router.push('useradmin');
          }
        }).catch((res) => {
          window.$message.error(res['data']);
          console.debug(res);
        }); 
      },
      btnRegisterClicked() {
        // 注册跳转
        //window.$message.info("registerProcessStarted");
        router.push('register');
      },

    }
  }
}
</script>

<style scoped>
#webContent {
  margin: 80px 20px 20px 50px;
}


</style>
