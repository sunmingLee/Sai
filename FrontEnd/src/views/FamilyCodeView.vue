<template>
  <div class="family-wrap">
    <HeaderTitle title="가족 코드 입력"></HeaderTitle>
    <p>가족 코드를 입력해주세요.</p>
    <div class="input-wrap">
      <input v-model="familyId" class="input-box" type="text"/>
      <Button buttonClass="small positive" buttonText="확인" @click="sendFamilyId"></Button>
    </div>
    <div class="border-bottom"></div>
    <span>사이를 이용하는 첫 가족이신가요?<br/>코드를 만든 후 가족들을 불러보세요!</span>
    <Button style="margin-top: 10%;" buttonClass="big information" buttonText="가족코드 만들기" @click="createFamilyId"></Button>
  </div>
</template>

<script>
import HeaderTitle from '@/components/common/HeaderTitle.vue'
// import InputBox from '@/components/common/InputBox.vue'
import Button from '@/components/common/Button.vue'
export default {
  components: { HeaderTitle, Button },
  data () {
    return {
      familyId: ''
    }
  },
  methods: {
    sendFamilyId () {
      const userInfo = {
        familyId: this.familyId,
        userId: localStorage.getItem('userId')
      }
      this.$store.dispatch('applyFamily', userInfo)
    },
    createFamilyId () {
      this.$store.dispatch('createFamilyId', localStorage.getItem('userId'))
    }
  }
}
</script>

<style scoped lang="scss">
.family-wrap{
    text-align: center;
}
p{
    margin-top: 40%;
    margin-bottom: 20%;
    font-size: 20px;
}
span{
    margin-top: 40%;
    margin-bottom: 20%;
    color: #AE5F40;
}
.input-wrap{
    display: inline-block;
    &.form-wrap{
        text-align: center;
    }
}
.input-box{
  text-align: center;
  margin-bottom: 5%;
  width: 140px;
  border-radius: 5px;
  border: 1px solid #ae5f40;
}
.border-bottom{
    border-bottom: 1px solid red;
    margin-top: 55%;
    margin-bottom: 10%;
}
</style>
