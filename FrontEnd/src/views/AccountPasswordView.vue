<template>
    <div class="flex-wrap">
        <HeaderTitle title="계정 관리"/>
        <div class="input-wrap">
            <InputBox inputSelect="input-box" :hasLabel="true" labelName="비밀번호" @inputCheck="checkPassword"/>
        </div>
        <Button buttonUsage="positive" buttonText="확인" @click="confirmPassword"/>
    </div>
</template>
<script>
import HeaderTitle from '@/components/common/HeaderTitle.vue'
import InputBox from '@/components/common/InputBox.vue'
import Button from '@/components/common/Button.vue'

export default {
  name: 'AccountPasswordView',
  components: {
    HeaderTitle,
    InputBox,
    Button
  },
  data () {
    return {
      password: '',
      userId: 'test01'
    }
  },
  methods: {
    checkPassword (password) {
      this.password = password
    },
    // 비밀번호 확인 버튼 클릭
    confirmPassword () {
      const user = {
        userId: this.userId,
        password: this.password
      }
      console.log(user)
      // DB에서 데이터 확인 후 오는 결과에 따라 IF/ELSE
    }
  }
}
</script>
