<template>
    <div class="searchId-wrap">
        <HeaderTitle hasBack=true title="아이디 찾기"/>
        <div class="flex">
          <SearchGuide guide="회원가입시 등록한 이메일을 입력해주세요" guide2="회원님의 아이디가 해당 이메일로 전송됩니다"/>
        </div>
        <div class="flex">
          <div class="input-wrap">
            <div class="name-wrap">
              <InputBox hasLabel="true" labelName="이름" @inputCheck="checkName"/>
            </div>
            <div class="email-wrap">
              <InputBox hasLabel="true" labelName="이메일" @inputCheck="checkEmail"/>
              <p v-if="validEmail" class="valid-email">이메일 형식이 맞지 않습니다</p>
            </div>
          </div>
        </div>
        <div class="flex">
          <Button buttonClass="big positive" buttonText="아이디 찾기" @click="searchId"/>
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
      email: '',
      name: ''
    }
  },
  methods: {
    checkName (name) {
      this.name = name
    },
    // 이메일 유효성을 검사하고 문구를 출력 판단
    checkEmail (email) {
      if (!this.isValidEmail(email)) {
        this.validEmail = true
      } else {
        this.validEmail = false
        this.email = email
      }
    },
    // 이메일 유효성 검사
    isValidEmail (email) {
      const re = /^(([^<>()\\[\]\\.,;:\s@"]+(\.[^<>()\\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/
      return re.test(email)
    },
    // 아이디 찾기 버튼 클릭 시
    searchId () {
      const userInfo = {
        userName: this.name,
        email: this.email
      }
      if (this.validEmail) {
        alert('이메일 형식이 맞지 않습니다.')
      } else if (this.name === '') {
        alert('이름을 입력해주세요.')
      } else {
        this.$store.dispatch('findId', userInfo)
      }
    }
  }
}
</script>
<style lang="scss" scoped>
p {
  margin: 0;
}
.searchId-wrap {
  width: 900px;

  .flex{
    display: flex;
    justify-content: center;
    text-align: center;

    .input-wrap{
      text-align: left;
      margin-bottom: 100px;

      .name-wrap, .email-wrap {
        margin: 10px 0 10px 0;
      }
    }
  }
}
/* 이메일 유효성 검사 문구 */
.valid-email {
    color: red;
    font-size: 12px;
}

</style>
