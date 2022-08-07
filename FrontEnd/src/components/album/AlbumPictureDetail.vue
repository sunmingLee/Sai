<template>
  <div class="picture-detail-wrap">
    <HeaderTitle hasBack="true"></HeaderTitle>
    <div class="button-wrap">
        <Button buttonClass="small information" buttonText="상세정보"></Button>
        <Button style="margin-left:5px;" buttonClass="small information" buttonText="앨범이동"></Button>
        <Button style="margin-left:5px;" buttonClass="small information" buttonText="다운로드"></Button>
        <Button style="margin-left:5px;" buttonClass="small information" buttonText="삭제"></Button>
    </div>
    <img src="@/assets/images/여행1.jpg" class="img-fluid" alt="image">
  </div>
</template>

<script>
import HeaderTitle from '../common/HeaderTitle.vue'
import Button from '../common/Button.vue'
export default {
  components: { HeaderTitle, Button }
}
</script>

<style scoped lang="scss">
.picture-detail-wrap{
    height: 90%;
}
.button-wrap{
    display: flex;
    justify-content: right;
    margin-bottom: 5%;
}
img{
    height: 80%;
}
button{
    // font-weight: normal;
}
</style>
