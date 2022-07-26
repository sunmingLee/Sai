<template>
    <div>
        <HeaderTitle title="비밀번호 찾기"/>
        <SearchGuide guide="회원가입시 등록한 아이디와 이메일을 입력해주세요" guide2="임시 비밀번호가 해당 이메일로 전송됩니다"/>
        <InputBox :hasLabel="true" labelName="아이디" @inputCheck="idCheck"/>
        <p v-if="id" class="valid-id">아이디는 2글자 이상 입력해주세요</p>
        <InputBox :hasLabel="true" labelName="이메일" @inputCheck="emailCheck"/>
        <p v-if="email" class="valid-email">이메일 형식이 아닙니다</p>
        <ButtonBig buttonUsage="positive" buttonText="비밀번호 찾기" @click="searchId"/>
    </div>
</template>
<script>
import HeaderTitle from '@/components/common/HeaderTitle.vue'
import SearchGuide from '@/components/SearchGuide.vue'
import InputBox from '@/components/common/InputBox.vue'
import ButtonBig from '@/components/common/ButtonBig.vue'

export default {
  name: 'SearchIdView',
  components: {
    HeaderTitle,
    SearchGuide,
    InputBox,
    ButtonBig
  },
  data () {
    return {
      email: false,
      emailInput: '',
      id: false,
      idInput: ''
    }
  },
  methods: {
    emailCheck (emailInput) {
      if (!this.validEmail(emailInput)) {
        this.email = true
      } else {
        this.email = false
      }
    },
    validEmail: function (email) {
      const re = /^(([^<>()\\[\]\\.,;:\s@"]+(\.[^<>()\\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/
      return re.test(email)
    },
    idCheck (idInput) {
      if (idInput.length < 2) {
        this.id = true
      } else {
        this.id = false
      }
    }
  }
}
</script>
<style>
.valid-email, .valid-id {
  color: red;
  font-size: 10px;
}
</style>
