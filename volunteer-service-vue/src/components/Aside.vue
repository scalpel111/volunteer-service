<template>
  <el-menu
      background-color="#545c64"
      text-color="#fff"
      active-text-color="#ffd04b"
      default-active="/home"
      router
      style="height:99.7vh">
    <el-menu-item index="/home">
        <i class="el-icon-s-home"></i>
        <span>首页</span>
    </el-menu-item>
    <el-menu-item index="/institution">
        <i class="el-icon-location"></i>
        <span slot="title">组织审批</span>
      </el-menu-item>
      <el-menu-item index="/activity">
        <i class="el-icon-location"></i>
        <span slot="title">活动审批</span>
      </el-menu-item>
  </el-menu>
</template>

<script>
export default {
  name: 'Aside',
  data () {
    return {
      
    }
  }
}
</script>

<style scoped>

</style>
