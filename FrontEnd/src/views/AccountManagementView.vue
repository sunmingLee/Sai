<template>
    <div class="flex-wrap">
        <HeaderTitle title="계정 관리"/>
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
                <Button buttonClass="small positive" buttonText="수정" @click="updatePassword"></Button>
            </div>
        </div>
        <div class="withdrawal">
          <p>회원탈퇴하기</p>
          <InputBox inputSelect="input-box" :hasLabel="true" labelName="현재 비밀번호" @inputCheck="checkPassword"/>
          <div>
            <Button class="btn" data-bs-toggle="modal" data-bs-target="#withdraw" buttonClass="negative" buttonText="회원탈퇴"></Button>
          </div>
        </div>
        <div class="modal fade" id="withdraw" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
          <div class="modal-dialog">
            <div class="modal-content">
              <div class="modal-header">
                <h5 class="modal-title" id="exampleModalLabel">회원탈퇴</h5>
              </div>
              <div class="modal-body">
                정말로 탈퇴하시겠습니까? 정말? 진짜로?
              </div>
              <div class="modal-footer">
                <Button buttonClass="small positive" buttonText= "취소" class="btn" data-bs-dismiss="modal"></Button>
                <Button buttonClass="small negative" buttonText="회원탈퇴" class="btn" @click="withdrawalMember" data-bs-dismiss="modal"></Button>
              </div>
            </div>
          </div>
        </div>
    </div>
</template>
<script>
import HeaderTitle from '@/components/common/HeaderTitle.vue'
import InputBox from '@/components/common/InputBox.vue'
import Button from '@/components/common/Button.vue'

export default {
  name: 'AccountManagementView',
  components: {
    HeaderTitle,
    InputBox,
    Button
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
      alert('탈퇴되었습니다.')
      // 모달창
      // 회원탈퇴 누르면 로그인창으로!
    }
  }
}
</script>

<style>
.modal {
  margin: 0 auto;
  left: 0;
  right: 0;
}
.modal-title {
  justify-content: center
}
</style>
