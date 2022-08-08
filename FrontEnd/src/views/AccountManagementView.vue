<template>
    <div class="account-wrap">
        <HeaderTitle hasBack=true title="계정 관리"/>
        <div class="flex">
          <div class="input-disabled">
            <div class="name-wrap">
              <InputBox inputSelect="input-underline" hasLabel=true labelName="이름" validDisabled="disabled" :inputValue="name"/>
            </div>
            <div class="email-wrap">
              <InputBox inputSelect="input-underline" hasLabel=true labelName="이메일" validDisabled="disabled" :inputValue="email"/>
            </div>
          </div>
        </div>
        <div class="flex">
          <div calss="input-wrap">
            <p class="password-title">비밀번호 변경</p>
            <div class="password-wrap">
              <InputBox inputSelect="input-box" hasLabel=true labelName="비밀번호" inputType="password" @inputCheck="checkPassword"/>
            </div>
            <div class="pwcheck-wrap">
              <InputBox inputSelect="input-box" hasLabel=true labelName="비밀번호 확인" inputType="password" @inputCheck="checkPasswordConfirm"/>
              <p v-if="validPasswordConfirm" class="valid-error">비밀번호가 일치하지 않습니다.</p>
            </div>
            <div class="flex">
              <Button buttonClass="small positive" buttonText="수정" @click="changePassword"/>
            </div>
          </div>
        </div>
        <div class="flex">
          <div calss="input-wrap">
            <p calss="withdrawal">회원탈퇴하기</p>
            <Button class="btn" data-bs-toggle="modal" data-bs-target="#withdraw" buttonClass="small negative" buttonText="회원탈퇴"></Button>
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
                <Button buttonClass="small negative" buttonText="회원탈퇴" class="btn" @click="withdrawal" data-bs-dismiss="modal"></Button>
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

import { mapState, mapActions } from 'vuex'
const userStore = 'userStore'

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
      passwordCurrent: '',
      passwordUpdate: ''
    }
  },
  methods: {
    ...mapActions(userStore, ['withdrawalMember', 'updatePassword']),
    // 비밀번호 유효성 검사 실행
    checkPassword (password) {
      this.passwordCurrent = password
      if (this.passwordUpdate !== '') {
        if (this.passwordUpdate === this.passwordCurrent) {
          this.validPasswordConfirm = false
        }
      }
    },
    // 비밀번호 유효성 검사
    isValidPassword (password) {
      const re = /^(?=.*[A-Za-z])(?=.*[0-9])(?=.*[#`?!@$%^&*-])[A-Za-z\d@$!%*#?&].{8,50}$/
      return re.test(password)
    },
    // 비밀번호 확인
    checkPasswordConfirm (password) {
      if (this.passwordCurrent !== password) {
        this.validPasswordConfirm = true
        this.passwordUpdate = password
      } else {
        this.validPasswordConfirm = false
        this.passwordUpdate = password
      }
    },
    // 비밀번호 변경 버튼 클릭
    changePassword () {
      if (this.passwordCurrent === this.passwordUpdate) {
        const user = {
          password: this.passwordUpdate,
          id: this.userInfo.userId
        }
        this.updatePassword(user)
      }
    },
    // 회원탈퇴
    withdrawal () {
      this.withdrawalMember(this.userInfo.userId)
    }
  },
  computed: {
    ...mapState(userStore, ['userInfo'])
  }
}
</script>
<style lang="scss" scoped>
.account-wrap {
  width: 900px;

  .flex{
    display: flex;
    justify-content: center;
    text-align: center;

    & div:nth-child(0) {
      width: 300px;
    }
    .input-wrap, .input-disabled {
      text-align: left;
      margin-bottom: 100px;

      .password-title {
        color: #AE5F40;
      }

      .name-wrap, .email-wrap, .password-wrap, .pwcheck-wrap {
        margin: 10px 0 10px 0;
      }
    }
  }
}
p {
  color: #AE5F40;
  font-weight: bold;
  padding-right: 200px;
}
</style>
