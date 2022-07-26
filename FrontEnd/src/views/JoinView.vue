<template>
  <div>
    <HeaderTitle>회원가입</HeaderTitle>
    <form id="join-form" @submit.prevent="onJoin">
      <InputBox v-model="name" :hasLabel="true" labelName="이름" @inputCheck="checkName"></InputBox>
      <p v-if="validName" class="valid-error">이름을 2자 이상으로 입력하세요.</p>
      <br>
      <InputBox v-model="id" :hasLabel="true" labelName="아이디" @inputCheck="checkId"></InputBox>
      <p v-if="validId" class="valid-error">아이디는 4자 이상으로 입력하세요.</p>
      <ButtonSmall buttonText="중복확인" ></ButtonSmall>
      <br>
      <InputBox v-model="email" :hasLabel="true" labelName="이메일" @inputCheck="checkEmail"></InputBox>
      <p v-if="validEmail" class="valid-error">이메일 형식으로 입력하세요.</p>
      <ButtonSmall buttonText="중복확인" ></ButtonSmall>
      <br>
      <InputBox v-model="password" :hasLabel="true" labelName="비밀번호" @inputCheck="checkPassword"></InputBox>
      <p v-if="validPassword" class="valid-error">비밀번호는 영문,숫자,특수문자를 포함하여 8자 이상으로 입력하세요.</p>
      <br>
      <InputBox v-model="passwordConfirm" :hasLabel="true" labelName="비밀번호 확인" @inputCheck="checkPasswordConfirm"></InputBox>
      <p v-if="validPasswordConfirm" class="valid-error">비밀번호가 일치하지 않습니다.</p>
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
      checked: false,
      validName: false,
      validId: false,
      validEmail: false,
      validPassword: false,
      validPasswordConfirm: false
    }
  },
  methods: {
    // 이름 유효성을 검사하고 문구를 출력 판단
    checkName (name) {
      if (!this.isValidName(name)) {
        this.validName = true
      } else {
        this.validName = false
      }
    },
    // 이름 유효성 검사
    isValidName (name) {
      const re = /^[가-힣]{2,}/
      return re.test(name)
    },
    // 아이디 유효성을 검사하고 문구를 출력 판단
    checkId (id) {
      if (!this.isValidId(id)) {
        this.validId = true
      } else {
        this.validId = false
      }
    },
    // 아이디 유효성 검사
    isValidId: function (id) {
      const re = /^[A-Za-z0-9]{4,16}/
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
    isValidEmail (email) {
      const re = /^(([^<>()\\[\]\\.,;:\s@"]+(\.[^<>()\\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/
      return re.test(email)
    },
    // 비밀번호 유효성을 검사하고 문구를 출력 판단
    checkPassword (password) {
      if (!this.isValidPassword(password)) {
        this.validPassword = true
      } else {
        this.validPassword = false
        this.password = password
      }
    },
    // 비밀번호 유효성 검사
    isValidPassword (password) {
      const re = /^(?=.*[A-Za-z])(?=.*[0-9])(?=.*[#`?!@$%^&*-])[A-Za-z\d@$!%*#?&].{8,50}$/
      return re.test(password)
    },
    // 비밀번호 확인
    checkPasswordConfirm (passwordConfirm) {
      console.log(this.password)
      if (passwordConfirm !== this.password) {
        this.validPasswordConfirm = true
        console.log('true')
      } else {
        this.validPasswordConfirm = false
        console.log('false')
      }
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
.valid-error {
    color: red;
    font-size: 12px;
}
</style>
