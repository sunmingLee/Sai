<template>
  <div>
    <HeaderTitle>회원가입</HeaderTitle>
    <form id="join-form" @submit.prevent="onJoin">
      <InputBox v-model="name" :hasLabel="true" labelName="이름"></InputBox>
      <br>
      <InputBox v-model="id" :hasLabel="true" labelName="아이디" @inputCheck="checkId"></InputBox>
      <p v-if="validId" class="valid-id">아이디는 4글자 이상으로 입력해주세요</p>
      <ButtonSmall buttonText="중복확인" ></ButtonSmall>
      <br>
      <InputBox v-model="email" :hasLabel="true" labelName="이메일" @inputCheck="checkEmail"></InputBox>
      <p v-if="validEmail" class="valid-email">이메일 형식이 아닙니다</p>
      <br>
      <InputBox v-model="password" :hasLabel="true" labelName="비밀번호"></InputBox>
      <br>
      <InputBox v-model="passwordConfirm" :hasLabel="true" labelName="비밀번호 확인"></InputBox>
      <br>
      <div>
        <label for="privacyPolice">개인정보 수집 동의 </label>
        <input type="checkbox" id="privacyPolice" v-model="checked">
        <br>
      </div>
      <br>
      <div>
        <button type="submit" :disabled="checked === false">회원가입</button>
      </div>
    </form>
  </div>
</template>

<script>
import ButtonSmall from '@/components/common/ButtonSmall.vue'
import InputBox from '@/components/common/InputBox.vue'
import HeaderTitle from '@/components/common/HeaderTitle.vue'

export default {
  name: 'JoinView',
  components: {
    ButtonSmall,
    InputBox,
    HeaderTitle
  },
  data () {
    return {
      name: '',
      id: '',
      email: '',
      password: '',
      passwordConfirm: '',
      validId: false,
      validEmail: false
    }
  },
  methods: {
    // 아이디 유효성을 검사하고 문구를 출력 판단
    checkId (id) {
      if (!this.isValidId(id)) {
        this.validId = true
      } else {
        this.validId = false
      }
    },
    //  유효성 검사
    isValidId: function (id) {
      const re = /^[A-za-z]{4,16}/
      return re.test(id)
    },
    // 이메일 유효성을 검사하고 문구를 출력 판단
    checkEmail (email) {
      if (!this.isValidEmail(email)) {
        this.validEmail = true
      } else {
        this.validEmail = false
      }
    },
    // 이메일 유효성 검사
    isValidEmail: function (email) {
      const re = /^(([^<>()\\[\]\\.,;:\s@"]+(\.[^<>()\\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/
      return re.test(email)
    },
    onJoin () {
      if (this.checked === true) {
        console.log('가입완료')
      }
    }
  }
}
</script>

<style>
</style>
