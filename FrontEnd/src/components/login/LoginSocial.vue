<template>
  <div>
    <ButtonSocial buttonType="kakao"></ButtonSocial>
    <ButtonSocial buttonType="naver"></ButtonSocial>
    <ButtonSocial buttonType="google"></ButtonSocial>
  </div>
</template>

<script>
import ButtonSocial from '@/components/common/ButtonSocial.vue'

export default {
  name: 'LoginSocial',
  components: { ButtonSocial }
}
</script>

<style></style>
