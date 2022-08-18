<template>
  <div class="join-wrap">
    <HeaderTitle hasBack="true" class="header" title="회원가입"/>
    <div class="flex">
      <form id="join-form" @submit.prevent="onJoin">
        <div class="input-wrap">
          <InputBox :hasLabel="true" labelName="아이디" @inputCheck="checkId"></InputBox>
          <p v-if="validId" class="valid-error">아이디는 4자 이상 16자 이하로 입력하세요.</p>
          <Button buttonText="중복확인" buttonClass="small information" @click.prevent="duplicateId"></Button>
          <br>
        </div>
        <div class="input-wrap">
          <InputBox :hasLabel="true" labelName="이름" @inputCheck="checkName"></InputBox>
          <p v-if="validName" class="valid-error">이름을 2자 이상으로 입력하세요.</p>
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
        <!-- Button trigger modal -->
        <div class="button-title" type="butoon" data-bs-toggle="modal" data-bs-target="#staticBackdrop">
          [개인정보 수집 및 이용 약관] 
        </div>

        <!-- Modal -->
        <div class="modal fade" id="staticBackdrop" data-bs-backdrop="static" data-bs-keyboard="false" tabindex="-1" aria-labelledby="staticBackdropLabel" aria-hidden="true">
          <div class="modal-dialog modal-dialog-centered">
            <div class="modal-content">
              <div class="modal-header">
                <h5 class="modal-title" id="staticBackdropLabel" >사이 서비스 이용 약관</h5>
              </div>
              <div class="modal-body">
                <p>안녕하세요!</p>
                <p>가족과 함께하는 '가족을 위한 SNS 사이(SAI)' (이하 '사이')입니다. 사이는 회원님께 회원님의 가족들과 가깝게 소통할 수 있는 서비스를 제공하기 위해 노력합니다.</p>
                <br>
                <p>본 약관은 사이 서비스의 이용과 관련하여 회원님께서 보다 편리하고 자유롭게 사이 서비스를 제공하는데 목적으로 개인정보를 수집 및 이용합니다. 서비스 이용 과정에서 수집하는 개인정보는 다음과 같습니다.</p>
                <p>회원가입 시 '아이디, 비밀번호, 이름, 이메일'을 필수항목으로 수집합니다. 회원정보 또는 개별 서비스에서 프로필 정보(프로필 사진, 생일, 애칭) 을 설정할 수 있습니다. 또한 이미지 및 동영상 등 게시글로 올라오는 파일들이
                  수집될 수 있습니다.</p>
                <p>개인정보 보유 및 이용기간은 회원 탈퇴 시까지입니다.</p>
                <p>개인정보는 서비스 제공 목적 외 다른 이유로 사용하지 않습니다.</p>
            </div>
              <div class="modal-footer">
                <label>개인정보 수집에 동의하시겠습니까?</label>
                <input type="checkbox" id="privacyPolice" v-model="checked">
                <button type="button" class="btn btn-primary" data-bs-dismiss="modal" >확인</button>
              </div>
            </div>
          </div>
        </div>
          <!-- <label for="privacyPolice">개인정보 수집 동의 </label> -->
          <br>
        </div>
        <br>
        <div>
          <Button buttonText="회원가입" buttonClass="small disabled" v-if="checked === false"></Button>
          <Button type="submit" buttonText="회원가입" buttonClass="small positive" v-else @click.prevent="onJoin"></Button>
        </div>
      </form>
    </div>
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
      dupcheck1: false,
      dupcheck2: false,
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
    // 이름 유효성 검사
    isValidName (userName) {
      const re = /^[가-힣a-zA-Z]{2,}/
      return re.test(userName)
    },
    // 아이디 유효성을 검사하고 문구를 출력 판단
    checkId (userId) {
      this.dupcheck1 = false
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
      this.dupcheck2 = false
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
      // 유효성 검사 통과된 아이디면 => 중복검사의 무조건 하게.
      // 유효성 검사 통과가 안된 아이디이면 store못가게
      if (!this.validId) {
        this.checkDuplicateId(this.userJoin.userId)
        this.dupcheck1 = true
      } else {
        alert('아이디는 4자 이상 16자 이하로 입력하세요.')
      }
    },
    duplicateEmail () {
      if (!this.validEmail) {
        this.checkDuplicateEmail(this.userJoin.email)
        this.dupcheck2 = true
      } else {
        alert('이메일 형식으로 입력하세요.')
      }
    },
    onJoin () {
      console.log(this.checked)
      if (this.checked === true) {
        if (!this.validName && !this.validId && !this.validEmail && !this.validPassword && !this.validPasswordConfirm 
        && this.dupcheck1 && this.dupcheck2) {
          this.checkJoin(this.userJoin)
          //this.$store.dispatch('join', this.userJoin)
        } else if (!this.dupcheck1) {
          alert('아이디 중복확인은 필수입니다.')
        } else if (!this.dupcheck2) {
          alert('이메일 중복확인은 필수입니다.')
        }
        else {
          alert('회원가입란을 다시 한번 확인해주세요.')
        }
      } else {
        alert('개인정보 수집에 동의에 체크해주세요.')
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
.button-title{
  text-decoration: underline;
}
</style>
