<template>
  <div class="container">
    <HeaderTitle title="내 정보"></HeaderTitle>
    <img class="profile img-thumbnail" src="@/assets/images/user-solid.svg" alt="user image">
    <!-- <label class="form-label" for="customFile">Default file input example</label> -->
    <input type="file" class="form-control" id="customFile" />
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
