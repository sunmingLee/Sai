<template>
  <div class="flex-wrap">
    <router-view />
  </div>
</template>

<style lang="scss">
body {
  background-color: #fafafa;
}

* {
  margin: 0;
  padding: 0;
}

#app {
  font-family: "Elice";
}

@font-face {
  font-family: "Elice";
  src: url("@/assets/fonts/EliceDigitalBaeum_Regular.ttf") format("truetype");
}

.flex-wrap {
  display: flex;
  justify-content: center;
<<<<<<< HEAD
=======
  height: 100vh;
  overflow-y: scroll;
>>>>>>> 7d7418f60fe6ec9d1457adec63fe2221b572dbaf
}
</style>
