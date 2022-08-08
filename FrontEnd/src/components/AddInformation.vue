<template>
  <div class="add-info-wrap">
    <div class="date">
        <span>생일</span>
        <Datepicker v-model="date" format='yyyy / MM / dd' :enableTimePicker="false" :maxDate="new Date()"></Datepicker>
    </div>
    <div class="left">
        <input type="radio" id="radioSolar" value="solar" v-model="radioValues">
        <label for="radioSolar">양력</label>
        <input type="radio" id="radioLunar" value="lunar" v-model="radioValues">
        <label for="radioLunar">음력</label>
    </div>
    <div class="user-message">
        <span>상태<br/>메세지</span>
        <textarea v-model="userMessage"></textarea>
    </div>
    <div class="button">
        <Button buttonClass="small information" buttonText="건너뛰기" @click="goFamilyCode"></Button>
        <Button buttonClass="small positive" buttonText="확인"></Button>
    </div>
  </div>
</template>

<script>
import Datepicker from '@vuepic/vue-datepicker'
import Button from './common/Button.vue'
export default {
  components: { Datepicker, Button },
  data () {
    return {
      radioValues: '',
      userMessage: '',
      date: new Date()
    }
  },
  methods: {
    goFamilyCode () {
      this.$router.push({ name: 'familyCode' })
    }
  }
}
</script>

<style scoped lang="scss">
.add-info-wrap{
  margin-top: 10%;
  height: 60%;
}
div{
    margin-bottom: 10px;
}
.left{
    text-align: left;
    padding-left: 85px;
    input, label{
      margin-right: 2%;
    }
}
.date{
    display: flex;
    justify-content: space-around;
}
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
    min-height: 20vh;
    resize: vertical;
}
span{
    color: #ae5f40
}
.button{
    display: flex;
    justify-content: space-between;
    margin: 40px;
    margin-top: 30%;
}
.button-wrap{
  display:inline-block;
  vertical-align: top;
  /* margin-left: 1%; */
}
</style>
