<template>
  <div class="family-info">
    <HeaderTitle title="가족정보수정"></HeaderTitle>
    <img src="@/assets/images/familyimg.png" alt="familyimg" class="rounded-circle">
    <InputBox></InputBox>
    <Button class="invitebtn" buttonText="가족 초대하기" buttonClass="big information" @click="goInvite"></Button>
    <h6>우리 가족의 애칭을 정해주세요</h6>
    <div>
      <p>{{userName1}} 님은</p>
      <InputBox :hasLabel="true" labelName="나의"></InputBox>
    </div>
    <div>
      <p>{{userName2}} 님은</p>
      <InputBox :hasLabel="true" labelName="나의"></InputBox>
    </div>
    <div>
      <p>{{userName3}} 님은</p>
      <InputBox :hasLabel="true" labelName="나의"></InputBox>
    </div>
    <div class="btns">
      <Button buttonClass="small negative" buttonText="취소" @click="cancle"></Button>
      <Button buttonClass="small positive" buttonText="저장" @click="save"></Button>
    </div>
  </div>
</template>
<script>
import HeaderTitle from '@/components/common/HeaderTitle.vue'
import InputBox from '@/components/common/InputBox.vue'
import Button from '@/components/common/Button.vue'

export default {
  name: 'familyInfoChange',
  components: { HeaderTitle, InputBox, Button },
  data () {
    return {
      familyName: '',
      userName1: '엄마',
      userName2: '아빠',
      userName3: '수빈',
      callsign1: '',
      callsign2: '',
      callsign3: ''
    }
  },
  methods: {
    goInvite () {
      this.$router.push('familyInvite')
    },
    cancle () {
      alert('취소되었습니다.')
      this.$router.push('feed')
    },
    save (familyName, callsign1, callsign2, callsign3) {
      familyName = this.familyName
      callsign1 = this.callsign1
      callsign2 = this.callsign2
      callsign3 = this.callsign3
      alert('저장되었습니다.')
    }
  }
}
</script>
<style>
  .invitebtn{
    display: flex;
    justify-content: flex-end

  }
  .btns{
    display: flex;
    justify-content: space-around;
  }
  h6 {
    display: flex;
    justify-content: center;
  }
</style>
