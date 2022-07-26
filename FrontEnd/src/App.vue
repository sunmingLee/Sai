<template>
  <div class="flex-wrap">
    <!-- <router-link to="/searchId">아이디 찾기</router-link> | -->
    <!-- <router-link to="/searchPassword">비밀번호 찾기</router-link> -->
    <router-view />
  </div>
</template>

<style>
#app {
  font-family: "Elice";
  /* background-color: #fafafa; */
}

@font-face {
  font-family: "Elice";
  src: url("@/assets/fonts/EliceDigitalBaeum_Regular.ttf") format("truetype");
}

.flex-wrap {
  display: flex;
  justify-content: center;
}

</style>
