<template>
  <div id="webContent">
    <n-page-header title="查询物流信息" :subtitle="'快递编号 '+package_id" @back="backPage()"></n-page-header>

    <div v-if="!showPackageRoads">
      <n-result status="404" title="暂无物流信息展示" description="请稍后再来看吧"/>
    </div>
    <div v-if="showPackageRoads">
      <n-descriptions label-placement="top" title="快递信息" style="margin-top: 16px">
      <n-descriptions-item>
        <template #label>
        状态
      </template>
      {{this.packageInfo['status']}}
      </n-descriptions-item>
      <n-descriptions-item>
        <template #label>
        订单 ID
      </template>
      {{this.packageInfo['orderid']}}
      </n-descriptions-item>
      <n-descriptions-item>
        <template #label>
        收件人
      </template>
      {{this.packageInfo['receiver']}}
      </n-descriptions-item>
      <n-descriptions-item>
        <template #label>
        发件人
      </template>
      {{this.packageInfo['sender']}}
      </n-descriptions-item>
      <n-descriptions-item>
        <template #label>
        起始地
      </template>
      {{this.packageInfo['startplace']}}
      </n-descriptions-item>
      <n-descriptions-item>
        <template #label>
        目的地
      </template>
      {{this.packageInfo['endplace']}}
      </n-descriptions-item>
      <n-descriptions-item>
        <template #label>
        时间
      </template>
      {{this.packageInfo['time']}}
      </n-descriptions-item>
    </n-descriptions>
  <div id="deliveryListContent" v-for="packageRoad in packageRoads" :key="packageRoad.id">
    <div v-if="packageRoad.active == false">
      <n-steps style="margin-top: 30px">
      <n-step :title="packageRoad.content" status="wait"
          :description="packageRoad.time"  >
          <template #icon>
        <img src="@/assets/CheckFilled.svg"/>
      </template>
      </n-step>
      </n-steps>
    </div>
    
    <div v-else>
      <n-steps style="margin-top: 30px">
      <n-step :title="packageRoad.content" status="process"
          :description="packageRoad.time" >
          <template #icon>
            <img src="@/assets/Box20FilledWhite.svg" />
      </template>
      </n-step>
      </n-steps>
    </div>
    
  </div>
  </div>
  </div>
    
  </template>
  
  <script>
  import {NStep , NSteps, NPageHeader,NResult,NDescriptions,NDescriptionsItem,useMessage} from 'naive-ui'
  import {useRouter} from 'vue-router'
  import {ref} from 'vue'
  import API from '../axiosInstance.js'
  export default {
    name: 'DeliveryList',
    components: {
      NStep, NSteps, NPageHeader,  NResult, NDescriptions, NDescriptionsItem
    },
    created() {
      // 请求快递信息
      API.get("/server/api/package/get?id="+this.package_id).then(res => {
        this.packageInfo = res['data'];
      }).catch(res => {
        console.log(res);
      })
      
      // 请求物流信息
      API.get("/server/api/delivery/get",{params: {id: this.package_id}}).then((res) => {
        console.log(res);
        this.packageRoads = JSON.parse(res['data']['info']);
        if(this.packageRoads.length > 0){
          this.showPackageRoads = true;
        } else {
          this.showPackageRoads = false;
        }
  
      }).catch((res) => {
        console.log(res);
        window.$message.warning("没有查询到物流信息结果");
      });
  
      this.packageRoads = [];
      this.showPackageRoads = false;
    },
    setup() {
      window.$message = useMessage();
      const router = useRouter();
      const query_data = router.currentRoute.value.query;
      
      if(query_data["number"] == undefined || query_data["number"] == null){
        window.alert("未获取到订单信息");
        window.close();
      }
      
      return {
        packageRoads: ref([]),
        packageInfo: ref(null),
        showPackageRoads: ref(false),
        valSubmit: ref(null),
        package_id: query_data["number"],
        backPage() {
          window.close();
        },
        submitChanges() {
          var mdate = new Date();
          var strtime = mdate.toLocaleString();
          
          for( var i=0; i<this.packageRoads.length ; i++) {
            this.packageRoads[i]['active'] = false;
          }
          var road_data = {'content':this.valSubmit,'time':strtime, active: true};
          this.packageRoads.push(road_data);
          this.showPackageRoads = true;
  
          // API Post
          API.post('/server/api/delivery/update',{
            id: this.package_id,
            info: JSON.stringify(this.packageRoads)
          }).then((res) => {
              console.log(res);
              window.$message.info("更新成功");
          }).catch((res) => {
              console.log(res);
              window.$message.error("更新失败");
          });
        }
      }
    },
    data() {
      return {
        
        demoPackageRoads: [
          /*
          {key:0,date:'2022-11-13', time:'12:00', content:'快递从秋叶原出发', location:'秋叶原', active: false},
          {key:1,date:'2022-11-13', time:'12:00', content:'田所浩三四五六七八九 已收取快件', location:'秋叶原', active: false},
          {key:2,date:'2022-11-13', time:'12:00', content:'快递抵达 【萌萌Q咖啡厅】', location:'秋叶原', active: false},
          {key:3,date:'2022-11-13', time:'12:00', content:'快递毁于爆炸！！！肥肠抱歉惹', location:'秋叶原', active: true},
          */
        ]
      }
    }
  }
  </script>
  
  <style scoped>
  #webContent {
    margin: 20px 20px 20px 50px;
  }
  
  
  </style>
  