<template>
  <div class="container">
    <img
      style="margin-top: 30px"
      src="@/assets/images/사이logo_cut.png"
      alt="Logo"
    />
    <LoginInput></LoginInput>
    <LoginSocial></LoginSocial>
    <div style="margin-bottom: 50px; margin-top: 10px">
      <router-link to="/searchId">아이디 찾기</router-link> |
      <router-link to="/searchPassword">비밀번호 찾기</router-link>
    </div>
    <div class="signIn">
      아직 회원이 아니신가요?
      <Button buttonClass="small positive" buttonText="회원가입" @click="goJoin"></Button>
    </div>
  </div>
</template>

<script>
// window.localStorage.clear();
import Button from '@/components/common/Button.vue'
import LoginInput from '@/components/login/LoginInput.vue'
import LoginSocial from '@/components/login/LoginSocial.vue'
export default {
  name: 'LoginView',
  components: { Button, LoginInput, LoginSocial },
  methods: {
    goJoin () {
      this.$router.push({ name: 'join' })
    }
  },
  beforeCreate() {
    window.localStorage.clear();
  },
}
</script>

<style>
.container{
  text-align: center;
}

a {
  /* font-family: Elice; */
  text-decoration: none;
  color: #616161;
}
.signIn {
  display: flex;
  justify-content: center;
  align-items: center;
  margin-top: 15px;
}

#buttonLogin {
  margin-bottom: 20px;
}
</style>
