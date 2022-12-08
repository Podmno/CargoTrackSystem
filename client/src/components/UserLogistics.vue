<template>
<div id="webContent">
  <n-page-header title="我的快递" >
    <template #avatar>
      <img src="../assets/Box20Filled.svg" style="width:30px; height:30px"/>
    </template>
    <template #extra>
      <div style="display: table">
      <n-button tertiary @click="showModalUserEdit = true">修改用户资料</n-button>
      <n-button tertiary style="margin-left: 10px;" @click="logOut()">退出登录</n-button>
    </div>
    </template>
  </n-page-header>
<p>此处显示了收件人与您关联的快递。若要查询其他快递，请输入快递单号后点击 “查询快递单号” 以获取快递物流信息。</p>
<div style="display: flex">

<n-input v-model:value="modalDataOrderID" style="width:300px" placeholder="订单 ID / 时间 检索"/>
<n-button style="margin-left: 10px" @click="updateTableSelection()">更新</n-button>

</div>
<div id="buttonContent" style="margin-top: 30px; display: flex">
  <n-input style="max-width:300px;" id="inputPackageID" type="text" v-model:value="valPackageIDNumber" placeholder="输入快递单号" />
    <n-button @click="searchPackageID()" type="primary" id="btnLogin" style="margin-left: 10px" >查询快递单号</n-button>
    <div v-if="boolAdminEnabled == true">
      </div>
  </div>
<n-data-table :columns="columns" :data="dataArray" :pagination="false" :bordered="false" :row-props="rowProps" style="margin-top: 24px"/>
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
import { NButton,NInput, NDataTable, NPageHeader, NModal, NCard ,useMessage } from 'naive-ui'
import { ref } from 'vue';
import { useRouter} from 'vue-router'
import API from '@/axiosInstance.js'
function transformTimestamp(timestamp) {
  let a = new Date(timestamp).getTime();
  const date = new Date(a);
  const Y = date.getFullYear() + '-';
  const M = (date.getMonth() + 1 < 10 ? '0' + (date.getMonth() + 1) : date.getMonth() + 1) + '-';
  const D = (date.getDate() < 10 ? '0' + date.getDate() : date.getDate()) + '  ';
  const h = (date.getHours() < 10 ? '0' + date.getHours() : date.getHours()) + ':';
  const m = (date.getMinutes() < 10 ? '0' + date.getMinutes() : date.getMinutes());
  // const s = date.getSeconds(); // 秒
  const dateString = Y + M + D + h + m;
  return dateString;
}

const createColumns = () => {
  return [
    {title: '物流 ID', key: 'id'},
    {title: '时间', key: 'time'},
    {title: '订单号', key: 'orderid'},
    {title: '起始地', key: 'startplace'},
    {title: '抵达地', key: 'endplace'},
    {title: '状态', key: 'status'},
    {title: '发件人', key: 'sender'},
    {title: '收件人', key: 'receiver'},
  ]
};

export default {
  name: 'UserCargoList',
  components: {
    NButton, NInput, NDataTable, NPageHeader, NModal, NCard
  },
  data() {
    return {
    userItems: [
    { key:0 ,number: 114514, company: '新日暮里邮政', startplace: '秋叶原', endplace: '新日暮里', status: '快递运输中', sender: 'XX 老师', receiver: '杰哥'},
    { key:1, number: 114514, company: '新日暮里邮政', startplace: '秋叶原', endPlace: '新日暮里', status: '快递运输中', sender: 'XX 老师', receiver: '杰哥'}
    ]
    }
  },
  created() {

    const query_data = useRouter().currentRoute.value.query;
    API.get("/server/api/package/getlist?uname="+query_data['uname']).then((res) => {
      console.log(res['data']);
      //var dataobj = res['data'];
      //this.userItems = dataobj;
      var reply = res['data']
      for(var i=0; i<reply.length;i++){
        reply[i]['time'] = transformTimestamp(reply[i]['time']);
      }
      this.dataArray = reply;
      
      this.dataOriginal = JSON.parse(JSON.stringify(reply));
      
      console.log(this.dataOriginal);
    }).catch((res) => {
      console.log(res);
      window.$message.error("请求数据时出现错误");
      
    });
  },
  setup() {
    const router = useRouter();
    window.$message = useMessage();
    return {
      // 写成匿名函数以访问 this 变量
      dataArray: ref([]),
      dataOriginal: ref([]),
      valUserName: ref(null),
      valPasswdNew: ref(null),
      valPasswdOld: ref(null),
      valContact: ref(null),
      valPackageIDNumber: ref(null),
      boolAdminEnabled: ref(true),
      modalDataOrderID: ref(null),
      // 列表渲染函数
      columns: createColumns(),
      // 控制 修改用户资料 窗口的显示
      showModalUserEdit: ref(false),
      rowProps: (row) => {
        return {
          style: "cursor: pointer;",
          onClick: () => {
            // Router 跳转至 DeliveryLis 界面
            //window.$message.info(row.id);
            const routeUrl = router.resolve({
              path: 'deliverylist',
              query: {
                number: row.id
              }
            });
            window.open(routeUrl.href,'_blank');
          }
        }
      },
      // 查询快递单号
      searchPackageID() {
        console.log(this.dataArray);
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
        
        const query_data = router.currentRoute.value.query;
        var editprofile = {
          'username': query_data['uname'],
          'passwd': this.valPasswdNew,
          'contact': this.valContact
        };
        API.post("/server/api//editprofile",editprofile).then((res) => {
          window.$message.info("更新资料完成");
          console.log(res)
        }).catch((res) => {
          window.$message.info("无法更新资料");
          console.log(res);
        })
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
        
      },
      updateTableSelection() {
        var readyList = JSON.parse(JSON.stringify(this.dataOriginal));
        console.log("ReadyList Restore => ",readyList);
        console.log("updateTableSelection");

        if(this.modalDataOrderID != null){
          console.log("Start sort by order id");
          for(var i=0; i<readyList.length;i++) {

            if(!readyList[i]['orderid'].includes(this.modalDataOrderID) && !readyList[i]['time'].includes(this.modalDataOrderID)){
              delete readyList[i];
            }
          }
        }
        this.dataArray = readyList;
        console.log(this.dataArray);
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
