<template>
  <div class="container">
    <div class="sai-image">
      <img src="@/assets/images/사이logo_cut.png" alt="Logo"/>
    </div>
    <LoginInput></LoginInput>
    <div>
      <div style="margin-bottom: 50px; margin-top: 10px" class="search-wrap">
        <router-link to="/searchId">아이디 찾기</router-link>
        <span>|</span>
        <router-link to="/searchPassword">비밀번호 찾기</router-link>
      </div>
      <div class="signIn">
        <span>아직 회원이 아니신가요?</span>
        <Button buttonClass="small positive" buttonText="회원가입" @click="goJoin"></Button>
      </div>
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

<style lang="scss" scoped>
.container{
  /* text-align: center; */
  max-width: 900px;
  .sai-image {
    top: 0;
    padding: 0 5%;
    display: flex;
    align-items: center;
    margin: 5% 0 2% 0;
    justify-content: center;
    margin: 40px 0;
  }
}
::v-deep {
  .input-wrap {
    display: flex;
    flex-wrap: wrap;
    gap: 1em;
    div {
      min-width: 300px;
      min-height: auto;
      display: flex;
      justify-content: center;
      max-width: 900px;
      flex-basis: 600px;
      margin: 0 auto;
      flex-wrap: wrap;
    }
  }
}
.search-wrap {
  display: flex;
  justify-content: center;
  span {
    padding: 0 10px;
  }
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
  span {
    margin-right: 15px;
  }
}

#buttonLogin {
  margin-bottom: 20px;
}
</style>
