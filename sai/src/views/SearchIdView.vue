<template>
    <div>
        <HeaderTitle title="아이디 찾기"/>
        <SearchGuide guide="회원가입시 등록한 이메일을 입력해주세요" guide2="회원님의 아이디가 해당 이메일로 전송됩니다"/>
        <InputBox :hasLabel="true" labelName="이메일" @inputCheck="emailCheck"/>
        <p v-if="email" class="valid-email">이메일 형식이 맞지 않습니다</p>
        <ButtonBig buttonUsage="positive" buttonText="아이디 찾기" @click="searchId"/>
    </div>
</template>
<script>
import HeaderTitle from '@/components/common/HeaderTitle.vue'
import SearchGuide from '@/components/SearchGuide.vue'
import InputBox from '@/components/common/InputBox.vue'
import ButtonBig from '@/components/common/ButtonBig.vue'

export default {
  name: 'SearchIdView',
  components: {
    HeaderTitle,
    SearchGuide,
    InputBox,
    ButtonBig
  },
  data () {
    return {
      email: false,
      emailInput: ''
    }
  },
  methods: {
    // 이메일 유효성을 검사하고 문구를 출력 판단
    emailCheck (emailInput) {
      if (!this.validEmail(emailInput)) {
        this.email = true
      } else {
        this.email = false
      }
    },
    // 이메일 유효성 검사
    validEmail: function (email) {
      const re = /^(([^<>()\\[\]\\.,;:\s@"]+(\.[^<>()\\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/
      return re.test(email)
    },
    // 아이디 찾기 버튼 클릭 시
    searchId () {
      if (this.email) {
        alert('이메일 형식이 맞지 않습니다')
      } else {
        // 디비에 있는 이메일인지 없는 이메일인지 확인해서 한 번 더 갈래를 나눠줘야 할 듯
        alert('회원님의 아이디를 이메일로 전송했습니다')
      }
    }
  }
}
</script>
<style>
* {
    margin: 0px;
    padding: 0px;
}
#app {
    background-color: #FAFAFA;
}
/* 이메일 유효성 검사 문구 */
.valid-email {
    color: red;
    font-size: 10px;
}
</style>
