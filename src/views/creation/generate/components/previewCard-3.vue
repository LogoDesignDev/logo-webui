<template>
  <div>
    <img class="bg" src="../../../../assets/previewCard/preview-3.png">
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
