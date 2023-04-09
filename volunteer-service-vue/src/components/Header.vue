<template>
  <div class="all">
    <div class="text">
        <span>志愿服务管理系统</span>
    </div>
    <el-dropdown>
        <i class="el-icon-arrow-down" style="margin-right: 15px"></i>
        <el-dropdown-menu slot="dropdown">
          <el-dropdown-item @click.native="toUser">个人中心</el-dropdown-item>
          <el-dropdown-item @click.native="logout">退出</el-dropdown-item>
        </el-dropdown-menu>
    </el-dropdown>
  </div>
</template>

<script>
export default {
  name: 'Header',
  data () {
    return {
      
    }
  },
  methods: {
    toUser () {
      this.$router.push('/home')
    },
    logout () {
      this.$router.push('/')
      localStorage.clear()
    }
  }
}
</script>

<style scoped>
    .all {
        display: flex;
    }
    .user {
        margin-right: 20px;
    }
    .text {
        text-align: center;
        flex: 1;
        font-size: 32px;
    }
</style>
