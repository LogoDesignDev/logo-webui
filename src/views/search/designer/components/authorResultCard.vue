<template>
  <div class="authorResultCardContainer">
    {{ data.username }}
  </div>
</template>

<style>
.authorResultCardContainer {
  width: 100%;
  height: 250px;
}

</style>

<script>
export default {
  props: ['data']
}
</script>
