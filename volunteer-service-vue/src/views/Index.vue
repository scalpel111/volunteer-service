<template>
  <el-container style="height: 100%; border: 1px solid #eee">
  <el-aside width="200px">
    <Aside/>
  </el-aside>
  <el-container  style="height: 100%;">
    <el-header style="text-align: right; font-size: 12px;">
      <Header/>
    </el-header>
    <el-main  style="height: 100%;">
      <router-view></router-view>
    </el-main>
  </el-container>
</el-container>
</template>

<script>
import Aside from '../components/Aside.vue'
import Header from '../components/Header.vue'
export default {
  name: 'Index',
  components: { Aside, Header }
}
</script>

<style scoped>
  .el-header {
    color: #333;
    line-height: 60px;
    border-bottom:1px solid rgb(213, 207, 207)
  }
  .el-main {
    padding: 5px;
  }
  .el-aside {
    color: #333;
  }
</style>
