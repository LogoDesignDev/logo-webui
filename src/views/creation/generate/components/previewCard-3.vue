<template>
  <div>
    <img class="bg" src="https://s1.ax1x.com/2020/06/07/t2fKjx.png">
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
  top: 245px;
  left: 260px;
  width: 100px;
  height: 100px;
  position: absolute;
  /* background: coral; */
}

</style>

<script>
export default {
  props: ['logoSrc']
}
</script>
