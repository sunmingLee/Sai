<template>
    <div class="family-wrap">
        <HeaderTitle title="가족들을 불러볼까요?"/>
        <div class="flex">
            <GuideMessage guide="회원님의 가족 코드입니다." guide2="가족들과 사이를 함께하세요."/>
        </div>
        <div class="flex">
            <div class="code-wrap">
                <p id="family-code" class="family-code">{{familyId}}</p>
            </div>
        </div>
        <div class="flex" style="margin: 30px 0 10px 0">
            <Button buttonText="코드복사" buttonClass="small information" @click="copyCode"/>
        </div>
          <div class="flex" style="margin-top: 30px">
              <Button buttonText="메인으로" buttonClass="small positive" @click="goMain"/>
          </div>
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
  },
  data() {
    return {
      familyId: localStorage.getItem('familyId')
    }
  },
  methods: {
    copyCode () {
      const obj = document.getElementById('family-code')
      const range = document.createRange()
      range.selectNode(obj.childNodes[0])
      const sel = window.getSelection()
      sel.removeAllRanges()
      sel.addRange(range)

      document.execCommand('copy')
      sel.removeRange(range)
      alert('가족 코드가 복사되었습니다')
    },
    goMain () {
      this.$router.push({ name: 'feed' })
    }
  },
  computed: {
  }
}
</script>
<style lang="scss" scoped>
.family-wrap {
  width: 900px;

  .flex{
    display: flex;
    justify-content: center;
    text-align: center;
  }
}
.family-code {
    font-size: 60px;
}
</style>
