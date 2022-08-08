<template>
  <div class="user-wrap">
    <img class="image-wrap" src="@/assets/images/user-solid.svg" alt="user image">
    <span class="name-wrap">{{name}}</span>
  </div>
</template>

<script>
export default {
  props: {
    name: String
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
}
.image-wrap{
    width: 7%;
}
</style>
