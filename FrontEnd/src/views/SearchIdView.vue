<template>
    <div class="flex-box">
        <HeaderTitle title="아이디 찾기"/>
        <SearchGuide guide="회원가입시 등록한 이메일을 입력해주세요" guide2="회원님의 아이디가 해당 이메일로 전송됩니다"/>
        <div class="input-wrap">
          <InputBox :hasLabel="true" labelName="이름"/>
          <InputBox :hasLabel="true" labelName="이메일" @inputCheck="checkEmail"/>
        </div>
        <p v-if="validEmail" class="valid-email">이메일 형식이 맞지 않습니다</p>
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
      validEmail: false,
      email: ''
    }
  },
  methods: {
    // 이메일 유효성을 검사하고 문구를 출력 판단
    checkEmail (email) {
      if (!this.isValidEmail(email)) {
        this.validEmail = true
      } else {
        this.validEmail = false
      }
    },
    // 이메일 유효성 검사
    isValidEmail: function (validEmail) {
      const re = /^(([^<>()\\[\]\\.,;:\s@"]+(\.[^<>()\\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/
      return re.test(validEmail)
    },
    // 아이디 찾기 버튼 클릭 시
    searchId () {
      if (this.validEmail) {
        alert('이메일 형식이 맞지 않습니다')
      } else {
        // 디비에 있는 이메일인지 없는 이메일인지 확인해서 한 번 더 갈래를 나눠줘야 할 듯
        alert('회원님의 아이디를 이메일로 전송했습니다')
      }
    }
  }
}
</script>
<style scoped>
* {
    margin: 0px;
    padding: 0px;
}
body {
    background-color: #FAFAFA;
}
/* 이메일 유효성 검사 문구 */
.valid-email {
    color: red;
    font-size: 10px;
}
.flex-box {
  text-align: center;
}
.input-wrap{
  margin-top: 100px;
  margin-bottom: 100px;
}
</style>
