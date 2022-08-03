<template>
    <div class="create-wrap">
        <HeaderTitle hasBack="true" title="게시글 작성" hasIcon="true"/>
        <div>
        </div>
        <router-link :to="{ name: 'feedCreateAdditional' }">추가 기록</router-link> |
        <router-link :to="{ name: 'feedCreatePoll' }">투표만들기</router-link>
        <router-view/>
    </div>
</template>
<script>
import HeaderTitle from '@/components/common/HeaderTitle.vue'
import InputBox from '@/components/common/InputBox.vue'
// import Button from '@/components/common/Button.vue'

export default {
  name: 'FeedCreateView',
  components: {
    HeaderTitle,
    InputBox
    // Button
  }
}
</script>
<style lang="scss" scoped>
.create-wrap {
  width: 900px;
  .flex{
    display: flex;
    justify-content: center;
    text-align: center;
    .input-wrap{
      text-align: left;
      margin-bottom: 100px;
    }
    .name-wrap, .email-wrap, .id-wrap {
        margin: 10px 0 10px 0;
     }
  }
}
</style>
