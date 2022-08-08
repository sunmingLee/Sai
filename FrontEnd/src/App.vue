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
  height: 100vh;
  overflow-y: scroll;
}
</style>
