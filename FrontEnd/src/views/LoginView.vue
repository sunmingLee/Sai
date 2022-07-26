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
      <router-link to="/findID">아이디 찾기</router-link> |
      <router-link to="/findPassword">비밀번호 찾기</router-link>
    </div>
    <div class="signIn">
      아직 회원이 아니신가요?
      <ButtonSmall buttonUsage="positive" buttonText="회원가입"></ButtonSmall>
    </div>
  </div>
</template>

<script>
import ButtonSmall from '@/components/common/ButtonSmall.vue'
import LoginInput from '@/components/login/LoginInput.vue'
import LoginSocial from '@/components/login/LoginSocial.vue'
export default {
  name: 'LoginView',
  components: { ButtonSmall, LoginInput, LoginSocial }
}
</script>

<style>
/* .container{
  background-color: #FAFAFA;
} */

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
  /* position: absolute;
  left: 223px;
  top: 320px; */
}
</style>
