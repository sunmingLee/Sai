<template>
  <nav>
    <!-- <router-link to="/searchId">아이디 찾기</router-link> | -->
    <!-- <router-link to="/searchPassword">비밀번호 찾기</router-link> -->
    <router-view />
  </nav>
</template>

<style>
#app {
  font-family: "Elice";
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  /* background-color: #fafafa; */
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
