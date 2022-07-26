<template>
  <div class="container">
    <HeaderTitle title="내 정보"></HeaderTitle>
    <img class="profile" src="@/assets/images/account-edit.png" alt="account image">
    <AddInformation></AddInformation>
  </div>
</template>

<script>
import HeaderTitle from '@/components/common/HeaderTitle.vue'
import AddInformation from '@/components/AddInformation.vue'
export default {
  components: { HeaderTitle, AddInformation }
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
