<template>
  <div class="apply-decline-wrap">
    <span>신청이 거부되었어요<br/><br/>
    혹시 가족 코드를 잘못 입력한건 아닐까요?<br/>다시 한번 신청해보세요</span>
    <Button class="btn-back" buttonClass="small information" buttonText="돌아가기" @click="goFamilyCode"></Button>
</div>
</template>

<script>
import Button from '@/components/common/Button.vue'
export default {
  components: { Button },
  methods: {
    goFamilyCode () {
      this.$router.push({ name: 'familyCode' })
    }
  }
}
</script>

<style scoped lang="scss">
span{
    display: block;
    position: relative;
    top: 30%;
    color: #7B371C;
    text-align: center;
    font-size: 20px;
    font-weight: bold;
}
.btn-back{
    text-align: center;
    position: relative;
    top: 40%;
}
</style>
