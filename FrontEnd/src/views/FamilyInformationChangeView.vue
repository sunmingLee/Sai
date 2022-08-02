<template>
  <div class="family-info">
    <HeaderTitle title="가족정보수정"></HeaderTitle>
    <InputBox></InputBox>
    <Button buttonText="가족 초대하기" buttonClass="big information"></Button>
    <div class="p-script">
      <p>우리 가족의 애칭을 정해주세요</p>
    </div>
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
  name: 'FamilyInformationChangeView',
  components: { HeaderTitle, InputBox, Button },
  data () {
    return {
      familyName: '',
      userName1: '엄마',
      userName2: '아빠',
      userName3: '수빈'
    }
  },
  methods: {
    cancle () {
      alert('취소되었습니다.')
    },
    save (familyName, userName1, userName2, userName3) {
      familyName = this.familyName
      userName1 = this.userName1
      userName2 = this.userName2
      userName3 = this.userName3
      alert('저장되었습니다.')
    }
  }
}
</script>
<style>
  .p-script{
    justify-content: center;
  }
  .btns{
    display: flex;
    justify-content: space-between;
  }
  p {
    margin: 0;
  }
</style>
