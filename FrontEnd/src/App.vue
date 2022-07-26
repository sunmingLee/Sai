<template>
  <router-view />
</template>

<style>
#app {
  font-family: "Elice";
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  background-color: #fafafa;
  margin: 0;
  /* width: max-content; */
  width: 360px;
  height: 640px;
  /* position: absolute;
  overflow: visible; */
  /* height: 100%; */
}

@font-face {
  font-family: "Elice";
  src: url("@/assets/fonts/EliceDigitalBaeum_Regular.ttf") format("truetype");
}

nav {
  padding: 30px;
}

nav a {
  font-weight: bold;
  color: #2c3e50;
}

nav a.router-link-exact-active {
  color: #42b983;
}
</style>
