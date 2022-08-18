<template>
  <div class="user-wrap">
    <img class="image-wrap" :src="userInfo.userImagePath" alt="user image">
    <!-- <img class="image-wrap" src="@/assets/images/byebye.png" alt="user image"> -->
    <span class="name-wrap">{{name}}</span>
  </div>
</template>

<script>
import { mapState } from 'vuex'
const userStore = 'userStore'

export default {
  props: {
    name: String
  },
  computed: {
    ...mapState(userStore, ['userInfo'])
  }
}
</script>

<style>
.user-wrap{
  text-align: left;
  margin-left: 10%;
}
.name-wrap{
  margin-left: 5%;
  font-weight: bold;
}
.image-wrap{
    width: 7%;
    min-width: 34px;
    border-radius: 30px;
}
</style>
