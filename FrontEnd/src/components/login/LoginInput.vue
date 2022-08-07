<template>
  <div>
    <InputBox :hasLabel="true" labelName="ID" inputType="text" @inputCheck="putId"></InputBox>
    <InputBox :hasLabel="true" labelName="PW" @inputCheck="putPassword" @keyup.enter="onLogin"></InputBox>
    <div>
      <Button buttonClass="small positive" buttonText="LOGIN" @click="onLogin"></Button>
    </div>
  </div>
</template>

<script>
import { mapActions, mapState } from 'vuex'
import InputBox from '@/components/common/InputBox.vue'
import Button from '@/components/common/Button.vue'

const userStore = 'userStore'
export default {
  name: 'LoginInput',
  components: {
    InputBox,
    Button
  },
  data () {
    return {
      user: {
        userId: '',
        password: ''
      }
    }
  },
  computed: {
    ...mapState(userStore, ['isLogin', 'isLoginError'])
  },
  methods: {
    ...mapActions(userStore, ['login', 'getUserInfo']),
    putId (userId) {
      this.user.userId = userId
    },
    putPassword (password) {
      this.user.password = password
    },
    async onLogin () {
      this.login(this.user)
      // await this.userConfirm(this.user)
      // const token = sessionStorage.getItem('access-token')
      if (this.isLogin) {
        // console.log('isLogin')
        this.getUserInfo(localStorage.getItem('userId'))
        // this.notifyVue(1, '<span> <b>로그인 완료</b> </span>')
        // this.$router.push({ name: 'about' })
      }
    }

  }
}
</script>

<style></style>
