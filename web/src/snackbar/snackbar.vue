<template>
  <mu-snackbar :position="position" :open.sync="open">
    {{message}}
    <mu-button flat slot="action" color="secondary" @click="open = false">关闭</mu-button>
  </mu-snackbar>
</template>

<script>
  export default {
    name: "snackbar",
  }
</script>

<style scoped>

</style>
