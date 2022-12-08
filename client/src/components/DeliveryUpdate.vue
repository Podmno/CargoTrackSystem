<template>
<div id="webContent">
  <n-page-header title="管理端 - 添加物流信息" :subtitle="'快递编号 '+package_id" @back="backPage()"></n-page-header>
  <div v-if="!showPackageRoads">
    <n-result status="404" title="暂无物流信息展示" description="请稍后再来看吧"/>
  </div>
  <div v-if="showPackageRoads">
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

<n-input v-model:value="valSubmit" type="textarea" style="margin-top:30px" placeholder="提交快递物流信息，添加后不可删除"  clearable />
<p>常用快递信息描述：</p>
<div style="display: flex; gap:5px">
  <n-button size="tiny" secondary strong @click="this.valSubmit = '快递已揽收'">快递已揽收</n-button>
  <n-button size="tiny" secondary strong @click="this.valSubmit = '快递已由【】出发，抵达【】'">快递已由【】出发，抵达【】</n-button>
  <n-button size="tiny" secondary strong @click="this.valSubmit = '快递正在配送中'">快递正在配送中</n-button>
  <n-button size="tiny" secondary strong @click="this.valSubmit = '快递已抵达'">快递已抵达</n-button>
  <n-button size="tiny" secondary strong @click="this.valSubmit = '快递已签收'">快递已签收</n-button>
</div>

<n-button @click="submitChanges()" style="margin-top:10px" >提交</n-button>
</div>
  
</template>

<script>
import {NStep , NSteps, NPageHeader, NButton, NInput, NResult,useMessage} from 'naive-ui'
import {useRouter} from 'vue-router'
import {ref} from 'vue'
import API from '../axiosInstance.js'
export default {
  name: 'DeliveryUpdate',
  components: {
    NStep, NSteps, NPageHeader, NButton, NInput, NResult
  },
  created() {
    // 请求物流信息
    API.get("/server/api/delivery/get",{params: {id: this.package_id}}).then((res) => {
      console.log(res['data']);
      this.packageRoads = JSON.parse(res['data']['info']);
      if(this.packageRoads.length > 0){
        this.showPackageRoads = true;
      } else {
        this.showPackageRoads = false;
      }

    }).catch((res) => {
      console.log(res);
      window.$message.warning("快递编号不存在，提交将创建该快递编号");
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
