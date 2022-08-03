<template>
    <div class="searchPassword-wrap">
        <HeaderTitle hasBack=true title="비밀번호 찾기"/>
        <div class="flex">
          <SearchGuide guide="회원가입시 등록한 아이디와 이메일을 입력해주세요" guide2="임시 비밀번호가 해당 이메일로 전송됩니다"/>
        </div>
        <div class="flex">
          <div class="input-wrap">
            <div class="name-wrap">
              <InputBox :hasLabel="true" labelName="이름" @inputCheck="checkName"/>
            </div>
            <div class="id-wrap">
              <InputBox :hasLabel="true" labelName="아이디" @inputCheck="checkId"/>
              <p v-if="validId" class="valid-id">아이디는 4글자 이상 입력해주세요</p>
            </div>
            <div class="email-wrap">
              <InputBox :hasLabel="true" labelName="이메일" @inputCheck="checkEmail"/>
              <p v-if="validEmail" class="valid-email">이메일 형식이 맞지 않습니다</p>
            </div>
          </div>
        </div>
        <div class="flex">
          <Button buttonClass="big positive" buttonText="비밀번호 찾기" @click="searchPassword"/>
        </div>
    </div>
</template>
<script>
import HeaderTitle from '@/components/common/HeaderTitle.vue'
import SearchGuide from '@/components/SearchGuide.vue'
import InputBox from '@/components/common/InputBox.vue'
import Button from '@/components/common/Button.vue'

export default {
  name: 'SearchIdView',
  components: {
    HeaderTitle,
    SearchGuide,
    InputBox,
    Button
  },
  data () {
    return {
      validEmail: false,
      validId: false,
      id: '',
      email: '',
      name: ''
    }
  },
  methods: {
    checkName (name) {
      this.name = name
    },
    checkEmail (email) {
      if (!this.isValidEmail(email)) {
        this.validEmail = true
      } else {
        this.validEmail = false
        this.email = email
      }
    },
    isValidEmail (email) {
      const re = /^(([^<>()\\[\]\\.,;:\s@"]+(\.[^<>()\\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/
      return re.test(email)
    },
    // 4글자 이상 입력 했는지
    checkId (id) {
      if (id.length < 4) {
        this.validId = true
      } else {
        this.validId = false
        this.id = id
      }
    },
    searchPassword () {
      const userInfo = {
        userName: this.name,
        userId: this.id,
        email: this.email
      }
      if (this.validEmail) {
        alert('이메일 형식이 맞지 않습니다')
      } else if (this.validId) {
        alert('아이디는 4글자 이상 입력해주세요')
      } else if (this.name === '') {
        alert('이름을 입력해주세요')
      } else {
        this.$store.dispatch("findPassword", userInfo)
        console.log(userInfo)
      }
    }
  }
}
</script>
<style lang="scss" scoped>
p {
  margin: 0;
}
.searchPassword-wrap {
  width: 900px;
  .flex{
    display: flex;
    justify-content: center;
    text-align: center;
    .input-wrap{
      text-align: left;
      margin-bottom: 100px;
    }
    .name-wrap, .email-wrap, .id-wrap {
        margin: 10px 0 10px 0;
     }
  }
}
.valid-email, .valid-id {
  color: red;
  font-size: 12px;
}

</style>
