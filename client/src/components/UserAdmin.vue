<template>
<div id="webContent">
  <n-page-header title="管理界面" >
    <template #avatar>
      <img src="../assets/Box20Filled.svg" style="width:30px; height:30px"/>
    </template>
    <template #extra>
      <div style="display: table">
      <n-button tertiary style="display: none" @click="showModalUserEdit = true">修改用户资料</n-button>
      <n-button tertiary style="margin-left: 10px;" @click="logOut()">退出登录</n-button>
    </div>
    </template>
  </n-page-header>
<p>Admin Management Platform</p>
<n-input style="max-width:300px;" id="inputPackageID" type="text" v-model:value="valPackageIDNumber" placeholder="输入快递单号" />
<div id="buttonContent" style="margin-top: 30px; display: flex">
    <n-button @click="searchPackageID()" type="primary" id="btnLogin"  >查询快递单号</n-button>
    <div v-if="boolAdminEnabled == true">
        <n-button style="margin-left: 10px" @click="goDeliveryUpdate()">提交物流信息</n-button>
        <n-button style="margin-left: 10px" @click="goDeliveryAdd()">添加 / 更新快递订单</n-button>
      </div>
  </div>

</div>
<!--- 用户资料修改显示 -->
<n-modal v-model:show="showModalUserEdit">
  <n-card
      style="width: 600px"
      title="修改用户资料"
      :bordered="false"
      size="huge"
      role="dialog"
      aria-modal="true"
    >
    <p>再次修改您的用户资料，只需填写需要修改的项目，点击保存即可。</p>
    
    <n-input style="margin-top: 10px; max-width:500px;" id="inputPasswordOld" v-model:value="valPasswdOld" type="password" placeholder="旧密码 / Old Password" />
    <n-input style="margin-top: 10px; max-width:500px;" id="inputPasswordNew" type="password" v-model:value="valPasswdNew" placeholder="新密码 / New Password" />
    <n-input style="margin-top: 10px; max-width:500px;" id="inputContact" type="text" v-model:value="valContact" placeholder="联系方式 / Phone & Email" />
    <template #footer>
      <n-button @click="updateProfile()">保存</n-button>
      <n-button type="primary" style="margin-left: 10px" @click="showModalUserEdit = false">取消</n-button>
    </template>
  </n-card>
    
</n-modal>
</template>

<script>
import { NButton,NInput,  NPageHeader, NModal, NCard, useMessage } from 'naive-ui'
import { ref } from 'vue';
import { useRouter} from 'vue-router'
import API from '@/axiosInstance.js'

const createColumns = () => {
  return [
    {title: '订单号', key: 'number'},
    {title: '公司', key: 'company'},
    {title: '起始地', key: 'startPlace'},
    {title: '抵达地', key: 'endPlace'},
    {title: '状态', key: 'status'},
    {title: '发件人', key: 'sender'},
    {title: '收件人', key: 'receiver'},
  ]
};

export default {
  name: 'UserAdmin',
  components: {
    NButton, NInput, NPageHeader, NModal, NCard
  },
  data() {
    return {
    userItems: [
    { key:0 ,number: 114514, company: '新日暮里邮政', startPlace: '秋叶原', endPlace: '新日暮里', status: '快递运输中', sender: 'XX 老师', receiver: '杰哥'},
    { key:1, number: 114514, company: '新日暮里邮政', startPlace: '秋叶原', endPlace: '新日暮里', status: '快递运输中', sender: 'XX 老师', receiver: '杰哥'},
    { key:2, number: 114514, company: '新日暮里邮政', startPlace: '秋叶原', endPlace: '新日暮里', status: '快递运输中', sender: 'XX 老师', receiver: '杰哥'},
    { key:3, number: 114514, company: '新日暮里邮政', startPlace: '秋叶原', endPlace: '新日暮里', status: '快递运输中', sender: 'XX 老师', receiver: '杰哥'},
    { key:4, number: 114514, company: '新日暮里邮政', startPlace: '秋叶原', endPlace: '新日暮里', status: '快递运输中', sender: 'XX 老师', receiver: '杰哥'},
    { key:5, number: 114514, company: '新日暮里邮政', startPlace: '秋叶原', endPlace: '新日暮里', status: '快递运输中', sender: 'XX 老师', receiver: '杰哥'},
    { key:6, number: 114514, company: '新日暮里邮政', startPlace: '秋叶原', endPlace: '新日暮里', status: '快递运输中', sender: 'XX 老师', receiver: '杰哥'},
    ]
    }
  },
  setup() {

    const router = useRouter();
    window.$message = useMessage();

    return {
      // 写成匿名函数以访问 this 变量
      
      valUserName: ref(null),
      valPasswdNew: ref(null),
      valPasswdOld: ref(null),
      valContact: ref(null),
      valPackageIDNumber: ref(null),
      boolAdminEnabled: ref(true),
      // 列表渲染函数
      columns: createColumns(),
      // 控制 修改用户资料 窗口的显示
      showModalUserEdit: ref(false),
      rowProps: (row) => {
        return {
          style: "cursor: pointer;",
          onClick: () => {
            // Router 跳转至 DeliveryLis 界面
            window.$message.info(row.number);
            const routeUrl = router.resolve({
              path: 'deliverylist',
              query: {
                number: row.number
              }
            });
            window.open(routeUrl.href,'_blank');
          }
        }
      },
      // 查询快递单号
      searchPackageID() {
        
        if(this.valPackageIDNumber == null){
          window.$message.error("请输入快递单号");
          return;
        }
        window.$message.info(this.valPackageIDNumber);
            const routeUrl = router.resolve({
              path: 'deliverylist',
              query: {
                number: this.valPackageIDNumber
              }
            });
            window.open(routeUrl.href,'_blank');
        // 查询快递单号操作 进入 DeliveryList
      },
      // 修改用户资料
      updateProfile() {
        window.$message.info("updateProfile()");

        // 修改用户资料操作
      },
      // 下线
      logOut() {
        API.get("/server/logout").then((res) => {
          window.$message.info("退出登录成功");
          console.log(res);
          router.push('login');
        }).catch((res) => {
          window.$message.info("Logout Failure");
          console.debug(res);
          
        });
        // logOut 操作
      },
      goDeliveryUpdate() {
        if(this.valPackageIDNumber == null){
          window.$message.error("请输入快递单号");
          return;
        }
        const routeUrl = router.resolve({
              path: 'deliveryupdate',
              query: {
                number: this.valPackageIDNumber
              }
            });
            window.open(routeUrl.href,'_blank');
      },
      goDeliveryAdd() {
        router.push("deliveryadd");
      }

    }
  }
}
</script>

<style scoped>
#webContent {
  margin: 30px 20px 20px 50px;
}


</style>
