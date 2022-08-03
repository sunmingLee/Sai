<template>
    <div class="poll-wrap">
        <InputBox @inputCheck="checkName" placeholder="항목 입력"/>
    </div>
</template>
<script>
import InputBox from '@/components/common/InputBox.vue'
export default {
  name: 'FeedCreatePollView',
  components: {
    InputBox
  }
}
</script>
