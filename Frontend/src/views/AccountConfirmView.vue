<template>
    <div class="account-wrap">
        <HeaderTitle :hasBack=true title="계정 관리"/>
        <div class="flex">
          <div class="input-wrap">
            <div class="flex"><p class="password-title">비밀번호를 입력해주세요.</p></div>
            <InputBox inputSelect="input-box" :hasLabel="true" labelName="비밀번호" inputType="password" @inputCheck="inputPassword"/>
          </div>
        </div>
        <div class="flex">
          <Button buttonClass="small positive" buttonText="확인" @click="confirmPassword"/>
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
  name: 'AccountConfirmView',
  components: {
    HeaderTitle,
    InputBox,
    Button
  },
  data () {
    return {
      password: ''
    }
  },
  computed: {
  },
  methods: {
    ...mapActions(userStore, ['checkPassword']),
    inputPassword (password) {
      this.password = password
    },
    // 비밀번호 확인 버튼 클릭
    confirmPassword () {
      const user = {
        userId: localStorage.getItem('userId'),
        password: this.password
      }
      this.checkPassword(user)
      // DB에서 데이터 확인 후 오는 결과에 따라 IF/ELSE
    }
  }
}
</script>
<style lang="scss" scoped>
.account-wrap {
  width: 900px;

  .flex{
    display: flex;
    justify-content: center;
    align-content: center;
    text-align: center;
    margin-bottom: 10%;
    .input-wrap {
      text-align: center;
    }
  }
}
p {
  color: #AE5F40;
  font-weight: bold;
  .password-title {
    color: #AE5F40;
    margin-bottom: 10%;
    text-align: center;
  }
}
</style>
