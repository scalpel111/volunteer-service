<template>
<div calss="container">
  <img src="../imgs/home.jpg" width="100%" height="50%"/>
</div>
</template>


<script>
  export default {
  name: 'Home',
  methods: {

    init () {
      if (localStorage.getItem('admin')) {
        this.user = JSON.parse(localStorage.getItem('admin'))
      }
    },
  },
  created () {
    this.init()
  }

  }
</script>

<style scoped>
    .el-descriptions{
        width:90%;
        margin: 0 auto;
        text-align: center;
    }
</style>