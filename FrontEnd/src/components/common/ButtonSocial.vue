<template>
  <div>
    <button class="button-social"><img class="button-image" :src="socialName" alt="social login"></button>
  </div>
</template>

<script>
export default {
  name: 'ButtonSocial',
  props: {
    buttonType: String
  },
  data () {
    return {
      socialName: ''
    }
  },
  created () {
    switch (this.buttonType) {
      case 'kakao':
        this.socialName = require('@/assets/images/kakao_login_medium_wide.png')
        break
      case 'naver':
        this.socialName = require('@/assets/images/btnG_완성형.png')
        break
      case 'google':
        this.socialName = require('@/assets/images/btn_google_signin_dark_normal_web@2x.png')
        break
    }
  }
}
</script>

<style>
.button-image{
    width: 210px;
    height: 30px;
    object-fit: cover;
    border-radius: 10px;
}
.button-social{
    background-color: transparent;
    border: 0;
    margin-bottom: 5px;
}
</style>
