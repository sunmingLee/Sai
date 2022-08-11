<template>
  <div class="container">
    <ModifyInformation></ModifyInformation>
  </div>
</template>

<script>
import ModifyInformation from '@/components/ModifyInformation.vue'
export default {
  components: { ModifyInformation }
}
</script>

<style>
.container{
  position: absolute;
  overflow: visible;
  height: 100vh;
  width: inherit;
}
.profile{
    margin-top: 20px;
    margin-bottom: 30px;
    width: 100px;
    height: 100px;
}
</style>
