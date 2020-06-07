<template>
  <div>
    <img class="bg" src="https://s1.ax1x.com/2020/06/07/t2fQu6.png">
    <!-- logo -->
    <div><img class="logo-1" :src="logoSrc"></div>
  </div>
</template>

<style scoped>
.bg {
  width: 100%;
  height: 100%;
  object-fit: cover;
}

.logo-1 {
  top: 150px;
  left: 130px;
  width: 150px;
  height: 150px;
  position: absolute;
  /* background: coral; */
}

</style>

<script>
export default {
  props: ['logoSrc']
}
</script>
