<template>
  <svg class="icon" aria-hidden="true">
    <use :xlink:href="iconName" />
  </svg>
</template>

<script>
export default {
  props: ['name'],

  computed: {
    iconName () {
      return '#' + this.name
    }
  }
}
</script>
