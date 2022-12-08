<template>
  <keep-alive>
    <n-message-provider>
      <router-view></router-view>
    </n-message-provider>
    
  </keep-alive>
</template>

<script>
import { NMessageProvider } from 'naive-ui';
export default {
  name: 'App',
  components: {NMessageProvider},

}
</script>

<style>

</style>
