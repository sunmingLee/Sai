<template>
  <div class="join-wrap">
    <HeaderTitle class="header" title="회원가입"/>
    <br>
    <form id="join-form">
      <div class="input-wrap">
        <InputBox :hasLabel="true" labelName="이름" @inputCheck="checkName"></InputBox>
        <p v-if="validName" class="valid-error">이름을 2자 이상으로 입력하세요.</p>
        <br>
      </div>
      <div class="input-wrap">
        <InputBox :hasLabel="true" labelName="아이디" @inputCheck="checkId"></InputBox>
        <p v-if="validId" class="valid-error">아이디는 4자 이상 16자 이하로 입력하세요.</p>
        <Button buttonText="중복확인" buttonClass="small information" @click.prevent="duplicateId"></Button>
        <br>
      </div>
      <div class="input-wrap">
        <InputBox :hasLabel="true" labelName="이메일" @inputCheck="checkEmail"></InputBox>
      <p v-if="validEmail" class="valid-error">이메일 형식으로 입력하세요.</p>
      <Button buttonText="중복확인" buttonClass="small information" @click.prevent="duplicateEmail"></Button>
      <br>
      </div>
      <div class="input-wrap">
        <InputBox :hasLabel="true" labelName="비밀번호" inputType="password" @inputCheck="checkPassword"></InputBox>
        <p v-if="validPassword" class="valid-error">비밀번호는 영문,숫자,특수문자를 포함하여 8자 이상으로 입력하세요.</p>
        <br>
      </div>
      <div class="input-wrap">
        <InputBox :hasLabel="true" labelName="비밀번호 확인" inputType="password" @inputCheck="checkPasswordConfirm"></InputBox>
        <p v-if="validPasswordConfirm" class="valid-error">비밀번호가 일치하지 않습니다.</p>
        <br>
      </div>
      <div>
        <label for="privacyPolice">개인정보 수집 동의 </label>
        <input type="checkbox" id="privacyPolice" v-model="checked">
        <br>
      </div>
      <br>
      <div>
        <Button type="submit" buttonText="회원가입" buttonClass="small disabled" v-if="checked === false"></Button>
        <Button type="submit" buttonText="회원가입" buttonClass="small positive" v-else @click.prevent="onJoin"></Button>
      </div>
    </form>
  </div>
</template>

<script>
import Button from '@/components/common/Button.vue'
import InputBox from '@/components/common/InputBox.vue'
import HeaderTitle from '@/components/common/HeaderTitle.vue'
import { mapState, mapActions } from 'vuex'
//import joinStore from '@/store/modules/joinStore'
const joinStore = 'joinStore'

export default {
  name: 'JoinView',
  components: {
    Button,
    InputBox,
    HeaderTitle
  },
  data () {
    return {
      userJoin: {
        userName: '',
        userId: '',
        email: '',
        password: ''
      },
      passwordConfirm: '',
      checked: false,
      validName: true,
      validId: true,
      validEmail: true,
      validPassword: true,
      validPasswordConfirm: true,
      isSubmit: false
    }
  },
  methods: {
    // 이름 유효성을 검사하고 문구를 출력 판단
    ...mapActions(joinStore, ['checkDuplicateId','checkDuplicateEmail','checkJoin']),
    checkName (userName) {
      if (this.userJoin.userName.length >= 0 && !this.isValidName(userName)) {
        this.validName = true
      } else {
        this.validName = false
        this.userJoin.userName = userName
      }
    },
    // 이름 유효성 검사sdf
    isValidName (userName) {
      const re = /^[가-힣a-zA-Z]{2,}/
      return re.test(userName)
    },
    // 아이디 유효성을 검사하고 문구를 출력 판단
    checkId (userId) {
      if (this.userJoin.userId.length >= 0 && !this.isValidId(userId)) {
        this.validId = true
      } else {
        this.validId = false
        this.userJoin.userId = userId
      }
    },
    // 아이디 유효성 검사
    isValidId (userId) {
      const re = /^[A-Za-z0-9]{4,16}$/
      return re.test(userId)
    },
    // 이메일 유효성을 검사하고 문구를 출력 판단
    checkEmail (email) {
      if (this.userJoin.email.length >= 0 && !this.isValidEmail(email)) {
        this.validEmail = true
      } else {
        this.validEmail = false
        this.userJoin.email = email
      }
    },
    // 이메일 유효성 검사
    isValidEmail (email) {
      const re = /^(([^<>()\\[\]\\.,;:\s@"]+(\.[^<>()\\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/
      return re.test(email)
    },
    // 비밀번호 유효성을 검사하고 문구를 출력 판단
    checkPassword (password) {
      if (this.userJoin.password.length >= 0 && !this.isValidPassword(password)) {
        this.validPassword = true
      } else {
        this.validPassword = false
        this.userJoin.password = password
      }
    },
    // 비밀번호 유효성 검사
    isValidPassword (password) {
      const re = /^(?=.*[A-Za-z])(?=.*[0-9])(?=.*[#`?!@$%^&*-])[A-Za-z\d@$!%*#?&].{8,50}$/
      return re.test(password)
    },
    // 비밀번호 확인
    checkPasswordConfirm (passwordConfirm) {
      if (passwordConfirm !== this.userJoin.password) {
        this.validPasswordConfirm = true
      } else {
        this.validPasswordConfirm = false
        this.passwordConfirm = passwordConfirm
      }
    },
    // 중복검사
    duplicateId () {
      // 유효성 검사 통과된 아이디면
      // 유효성 검사 통과가 안된 아이디이면 store못가게
      if (!this.validId) {
        this.checkDuplicateId(this.userJoin.userId)
        // mapActions(joinStore, ['checkDuplicateId'])
      } else {
        alert('아이디는 4자 이상 16자 이하로 입력하세요.')
      }
    },
    duplicateEmail () {
      if (!this.validEmail) {
        this.checkDuplicateEmail(this.userJoin.email)
        //this.$store.dispatch('checkDuplicateEmail', this.userJoin.email)
      } else {
        alert('이메일 형식으로 입력하세요.')
      }
    },
    onJoin () {
      if (this.checked === true) {
        if (!this.validName && !this.validId && !this.validEmail && !this.validPassword && !this.validPasswordConfirm) {
          this.checkJoin(this.userJoin)
          //this.$store.dispatch('join', this.userJoin)
        } else {
          alert('회원가입란은 다시 한번 확인해주세요.')
        }
      // 이름, 아이디, 이메일, 비밀번호, 비밀번호 확인 모두 작성되고
      // 유효성 검사 모두 통과되고
      }
    // 중복확인 모두 통과되면
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
