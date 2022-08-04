<!-- eslint-disable camelcase -->
<template>
  <div>
    <!-- <ButtonSocial buttonType="kakao"></ButtonSocial> -->
    <div id="naver_id_login"></div>
    <!-- <ButtonSocial buttonType="google"></ButtonSocial> -->
  </div>
</template>

<script>
import ButtonSocial from '@/components/common/ButtonSocial.vue'
import router from '@/router'

export default {
  name: 'LoginSocial',
  components: { ButtonSocial },
  mounted () {
    // const naverLogin = new naver.LoginWithNaverId(
    //   {
    //     clientId: 'MJAAQaHVeXbDGpCLDONE',
    //     callbackUrl: 'http://localhost:8081/login',
    //     loginButton: { color: 'green', type: 3, height: 40 }
    //   }
    // )
    // naverLogin.init() // 로그인 설정
    const naver_id_login = new window.naver_id_login('MJAAQaHVeXbDGpCLDONE', 'http://localhost:8081/login')
    const state = naver_id_login.getUniqState()
    naver_id_login.setButton('green', 3, 40) // 버튼 설정
    // naver_id_login.setState(state)
    // naver_id_login.setPopup() // popup 설정을 위한 코드
    // naver_id_login.init_naver_id_login()
  },
  methods: {
    moveNaver () {
      router.push({ name: 'naver' })
    }
  }
}
</script>

<style></style>
