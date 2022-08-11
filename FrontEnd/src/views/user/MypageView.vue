<template>
    <div class="view-wrap">
        <MyPageHeader />
        <MyPageContent/>
        <BottomTap></BottomTap>
    </div>
</template>
<script>
import MyPageHeader from '@/components/mypage/MyPageHeader.vue'
import MyPageContent from '@/components/mypage/MyPageContent.vue'
import BottomTap from '@/components/common/BottomTap.vue'

export default {
  name: 'MyPageView',
  components: {
    MyPageHeader,
    BottomTap,
    MyPageContent
  },
  data () {
    return {
    }
  },
  created() {
  },
  computed: {
  },
  methods: {
  }
}
</script>
<style lang="scss" scoped>
.view-wrap {
  width: 900px;
}
</style>
