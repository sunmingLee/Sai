<template>
  <div class="apply-wait-wrap">
    <div class="family-id-wrap" v-if="familyId">신청한 가족 코드<br/>{{ familyId }}</div>
    <span>결과를 기다리는 중이에요<br/><br/>
    가족들에게 어서<br/>수락해달라고 말해보세요!</span>
    <Button class="btn-back" buttonClass="big negative" buttonText="신청 취소하기" @click="goFamilyCode"></Button>
    <br>
    <Button class="btn-go" buttonClass="big positive" buttonText="신청 결과보기" @click="goResult"></Button>
</div>
</template>

<script>
import Button from '@/components/common/Button.vue'
import { mapActions, mapState } from 'vuex'
const familyStore = 'familyStore'
const userStore = 'userStore'
export default {
  components: { Button },
  methods: {
    ...mapActions(familyStore, ['deleteFamilyApply']),
    ...mapActions(userStore, ['getUserInfo']),
    goFamilyCode () {
      this.deleteFamilyApply(localStorage.getItem('userId'))
    },
    goResult () {
      this.getUserInfo(localStorage.getItem('userId'))
    }
  },
  computed: {
    ...mapState(familyStore, ['familyId'])
  }
}
</script>

<style scoped lang="scss">
.apply-wait-wrap{
    width: 100vh
}
.family-id-wrap{
    display: block;
    position: relative;
    top: 20%;
    color: #7B371C;
    text-align: center;
    font-size: 40px;
    font-weight: bold;
}
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
.btn-go{
    text-align: center;
    position: relative;
    top: 40%;
}
</style>
