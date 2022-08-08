<template>
    <div class="flex-wrap">
        <HeaderTitle title="계정 관리"/>
        <div class="input-wrap">
            <InputBox inputSelect="input-box" :hasLabel="true" labelName="비밀번호" @inputCheck="checkPassword"/>
        </div>
        <Button buttonClass="small positive" buttonText="확인" @click="confirmPassword"/>
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
    ...mapState(userStore, ['userInfo'])
  },
  methods: {
    ...mapActions(userStore, ['checkPassword']),
    checkPassword (password) {
      this.password = password
    },
    // 비밀번호 확인 버튼 클릭
    confirmPassword () {
      const user = {
        userId: this.userInfo.userId,
        password: this.password
      }
      this.checkPassword(user)
      // DB에서 데이터 확인 후 오는 결과에 따라 IF/ELSE
    }
  }
}
</script>
