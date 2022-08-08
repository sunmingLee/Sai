<template>
  <div>
    <div>
      <div>
      <InputBox v-model="nickname" :hasLabel="true" labelName="새로운 비밀번호" inputType="password" validDisabled="" :hasError="hasError" errorMessage="닉네임을 2글자 이상으로 입력하세요." @inputCheck="checkNickname"></InputBox>
      </div>
      <div>
      <InputBox v-model="nickname" :hasLabel="true" labelName="이름" inputType="text" validDisabled="" :hasError="hasError" errorMessage="닉네임을 2글자 이상으로 입력하세요." @inputCheck="checkNickname"></InputBox>
      </div>
    </div>
    <div class="date">
        <span>생일</span>
        <Datepicker v-model="date" format='yyyy / MM / dd' :enableTimePicker="false" :maxDate="new Date()"></Datepicker>
    </div>
    <div class="left">
        <input type="radio" id="radioSolar" value="solar" v-model="radidValues">
        <label for="radioSolar">양력</label>
        <input type="radio" id="radioLunar" value="lunar" v-model="radidValues">
        <label for="radioLunar">음력</label>
    </div>
    <div class="user-message">
        <span>상태<br/>메세지</span>
        <!-- <resize-textarea
        :rows="2"
        :cols="4"
        :maxHeight="150"
        v-model="textValue"></resize-textarea> -->
        <textarea v-model="userMessage"></textarea>
    </div>
    <div class="button">
        <Button buttonClass="small information" buttonText="건너뛰기"></Button>
        <Button buttonClass="small positive" buttonText="확인"></Button>
    </div>
  </div>
</template>

<script>
// import ResizeTextarea from 'resize-textarea-vue3'
import Datepicker from '@vuepic/vue-datepicker'
import InputBox from './common/InputBox.vue'
import Button from './common/Button.vue'
export default {
//   components: { InputBox, Datepicker, ResizeTextarea },
  components: { InputBox, Datepicker, Button },
  data () {
    return {
      nickname: '',
      validNickname: false,
      radidValues: '',
      userMessage: '',
      textValue: 'reSize',
      hasError: true,
      date: new Date()
    }
  },
  methods: {
    // 닉네임 유효성을 검사하고 문구를 출력 판단
    checkNickname (nickname) {
      if (this.isValidNickname(nickname)) {
        this.validNickname = true
      } else {
        this.validNickname = false
      }
    },
    // 이름 유효성 검사
    isValidNickname (nickname) {
      if (nickname.length < 2) {
        return false
      } else {
        return true
      }
    }

  }
}
</script>

<style scoped>
div{
    margin-bottom: 10px;
}
.left{
    text-align: left;
    padding-left: 40px;
}
.date{
    display: flex;
    justify-content: space-around;
    align-items: center;
}
/* .date-picker{
    border-radius: 5px;
    border: 1px solid #ae5f40;
} */
.user-message{
    display: flex;
    justify-content: space-around;
    /* align-items: ; */
}
.valid-error {
    color: red;
    font-size: 12px;
}
textarea{
    border-radius: 5px;
    border: 1px solid #ae5f40;
    width: 210px;
    min-height: 80px;
    resize: vertical;
    /* overflow: auto; */
}
span{
    color: #ae5f40
}
.button{
    display: flex;
    justify-content: space-between;
    margin: 40px
}
.button-wrap{
  display:inline-block;
  vertical-align: top;
  /* margin-left: 1%; */
}
</style>
