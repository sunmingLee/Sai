<template>
    <div class="flex-wrap">
        <HeaderTitle title="내 정보"/>
        <div class="input-disabled">
            <InputBox inputSelect="input-underline" :hasLabel="true" labelName="이름" validDisabled="disabled" :inputValue="name"/>
            <InputBox inputSelect="input-underline" :hasLabel="true" labelName="이메일" validDisabled="disabled" :inputValue="email"/>
        </div>
        <div calss="input-wrap">
            <div class="password">
                <p>비밀번호 변경</p>
                <InputBox inputSelect="input-box" :hasLabel="true" labelName="비밀번호" @inputCheck="checkPassword"/>
                <p v-if="validPassword" class="valid-error">비밀번호는 영문,숫자,특수문자를 포함하여 8자 이상으로 입력하세요.</p>
                <InputBox inputSelect="input-box" :hasLabel="true" labelName="비밀번호 확인" @inputCheck="checkPasswordConfirm"/>
                <p v-if="validPasswordConfirm" class="valid-error">비밀번호가 일치하지 않습니다.</p>
                <ButtonSmall buttonUsage="positive" buttonText="수정" @click="updatePassword"/>
            </div>
        </div>
        <div class="withdrawal">
            <p>회원탈퇴하기</p>
            <InputBox inputSelect="input-box" :hasLabel="true" labelName="현재 비밀번호" @inputCheck="checkPassword"/>
            <ButtonSmall buttonUsage="negative" buttonText="회원 탈퇴" @click="withdrawalMember"/>
        </div>
    </div>
</template>
<script>
import HeaderTitle from '@/components/common/HeaderTitle.vue'
import InputBox from '@/components/common/InputBox.vue'
import ButtonSmall from '@/components/common/ButtonSmall.vue'

export default {
  name: 'AccountManagementView',
  components: {
    HeaderTitle,
    InputBox,
    ButtonSmall
  },
  data () {
    return {
      // 나중에 로그인한 회원의 정보로 받아오는 것으로 수정이 필요(지금은 임의의 데이터)
      name: '최수빈',
      email: 'test@naver.com',
      validPassword: false,
      validPasswordConfirm: false,
      password: ''
    }
  },
  methods: {
    // 비밀번호 유효성 검사 실행
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
    checkPasswordConfirm (password) {
      if (this.password !== password) {
        this.validPasswordConfirm = true
      } else {
        this.validPasswordConfirm = false
      }
    },
    // 비밀번호 변경 버튼 클릭
    updatePassword () {
      alert('비밀번호가 변경되었습니다.')
      const user = {
        userId: 'test01',
        passsword: this.password
      }
      console.log(user)
    },
    // 회원탈퇴
    withdrawalMember () {
      // 모달창
    }
  }
}
</script>
