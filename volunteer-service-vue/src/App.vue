<template>
  <div id="app">
    <router-view/>
  </div>
</template>

<script>
export default {
  name: 'App',
  mounted () {
    if (localStorage.getItem('menu')) {
      this.$store.commit('setMenu', JSON.parse(localStorage.getItem('menu')))
    }
  }
}
</script>
<style>
 #app {
  height: 100%;
 }
</style>
