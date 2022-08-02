<template>
    <div class="family-wrap">
        <HeaderTitle hasBack="true" title="가족들을 불러볼까요?"/>
        <GuideMessage guide="회원님의 가족 코드입니다." guide2="가족들과 클로저를 함께하세요."/>
        <div class="code-wrap">
            <p class="fmaily-code">394 027</p>
        </div>
        <Button buttonText="코드복사" buttonClass="small information" />
    </div>
</template>
<script>
import Button from '@/components/common/Button.vue'
import HeaderTitle from '@/components/common/HeaderTitle.vue'
import GuideMessage from '@/components/GuideMessage.vue'

export default {
    name: 'FamilyInviteView',
    components: {
        Button,
        HeaderTitle,
        GuideMessage
    }
}
</script>
<style lang="scss" scoped>
.family-wrap {
    text-align: center;
}
.fmaily-code {
    font-size: 60px;
}
</style>