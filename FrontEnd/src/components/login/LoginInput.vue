<template>
  <div>
    <div class="input-wrap">
      <div class="input-flex">
        <div class="id-div">
          <div class="id-title">
            <p class="title">아이디</p>
          </div>
          <div class="id-input">
            <input type="text" v-model="this.user.userId" placeholder="아이디를 입력하세요">
          </div>
        </div>
        <div class="pw-div">
          <div class="pw-title">
            <p class="title">비밀번호</p>
          </div>
          <div class="pw-input">
            <input type="password" v-model="this.user.password" placeholder="비밀번호를 입력하세요" @keyup.enter="onLogin">
          </div>
        </div>
      </div>
    </div>
    <div class="login-btn">
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
      },
      userId: ''
    }
  },
  computed: {
    ...mapState(userStore, ['isLogin', 'isLoginError'])
  },
  methods: {
    ...mapActions(userStore, ['login', 'getUserInfo']),
    // putId (userId) {
    //   this.user.userId = userId
    // },
    // putPassword (password) {
    //   this.user.password = password
    // },
    async onLogin () {
      console.log(this.user.userId)
      await this.login(this.user)
      // await this.userConfirm(this.user)
      // const token = sessionStorage.getItem('access-token')
      if (this.isLogin) {
        // console.log('isLogin')
        // await this.getUserInfo(this.user)
        // this.notifyVue(1, '<span> <b>로그인 완료</b> </span>')
        // this.$router.push({ name: 'about' })
      }
    }
  }
}
</script>

<style lang="scss" scoped>
.input-wrap {
  max-width: 900px;
  margin: 0 auto;
  .input-flex {
    display: flex;
    flex-wrap: wrap;
    // gap: 1em;
    .id-div, .pw-div {
      margin: 10px 0;
      min-width: 300px;
      min-height: auto;
      flex-basis: 600px;
      .id-title, .pw-title {
        flex-basis: 300px;
        justify-content: flex-start;
        .title {
          padding-left: 8px;
        }
      }
    }
  }
}
.login-btn {
  margin: 40px 0 20px 0;
  display: flex;
  justify-content: center;
  div[data-v-67cfa612] {
    width: 80px;
  }
}
.title {
  color: #AE5F40;
  font-weight: bold;
}
.id-title {
  margin-right: 242px;
}
.id-input, .pw-input {
  input {
    padding-left: 20px;
    text-align: left;
    margin-bottom: 1%;
    min-width: 300px;
    min-height: 50px;
    border-radius: 5px;
    border: 1px solid #ae5f40;
  }
}
</style>
