<template>
  <div class="container">
    <HeaderTitle title="내 정보"></HeaderTitle>
    <!-- <v-icon>mdi-account-edit</v-icon> -->
    <img src="@/assets/images/account-edit.png" alt="account image">
  </div>
</template>

<script>
import HeaderTitle from '@/components/common/HeaderTitle.vue'
export default {
  components: { HeaderTitle }
}
</script>

<style>
.container{
  position: absolute;
  overflow: visible;
  height: 100vh;
  width: inherit;
}
</style>
