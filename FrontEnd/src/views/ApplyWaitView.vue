<template>
  <div class="apply-wait-wrap">
    <span>결과를 기다리는 중이에요<br/><br/>
    가족들에게 어서<br/>수락해달라고 말해보세요!</span>
    <Button class="btn-back" buttonClass="small information" buttonText="돌아가기" @click="goBack"></Button>
</div>
</template>

<script>
import Button from '@/components/common/Button.vue'
export default {
  components: { Button },
  methods: {
    goBack () {
      this.$router.push({ name: 'login' })
    }
  }
}
</script>

<style scoped lang="scss">
.apply-wait-wrap{
    width: 100vh
}
span{
    display: block;
    position: relative;
    top: 40%;
    color: #7B371C;
    text-align: center;
    font-size: 20px;
    font-weight: bold;
}
.btn-back{
    text-align: center;
    position: relative;
    top: 50%;
}
</style>
