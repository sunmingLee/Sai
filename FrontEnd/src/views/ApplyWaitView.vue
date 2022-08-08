<template>
  <div class="apply-wait-wrap">
    <span>결과를 기다리는 중이에요<br/><br/>
    가족들에게 어서<br/>수락해달라고 말해보세요!</span>
    <Button class="btn-back" buttonClass="small information" buttonText="돌아가기"></Button>
    <!-- <div class="back-wrap">
        로그인 화면으로 돌아가기
    </div> -->
</div>
</template>

<script>
import Button from '@/components/common/Button.vue'
export default {
  components: { Button }
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
// .back-wrap{
//     position: relative;
//     top: 72%;
//     text-align: center;
//     height: 10%;
//     color: white;
//     background-color: #7B371C;
// }
</style>
