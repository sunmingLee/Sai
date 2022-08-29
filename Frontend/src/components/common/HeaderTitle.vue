<template>
  <div class="header">
      <img :class="data.visibilityBack" src="@/assets/images/arrow-left-solid.svg" alt="arrow-left" style="width: 20px" @click="goBack">
      <span class="title">{{ title }}</span>
      <img :class="data.visibilityIcon" src="@/assets/images/calendar-days-solid.svg" alt="calendar" style="width: 20px">
  </div>
</template>

<script>
import { reactive } from 'vue'

export default {
  name: 'HeaderTitle',
  props: {
    title: String,
    hasBack: String,
    hasIcon: String
  },
  setup (props) {
    const data = reactive({
      visibilityBack: '',
      visibilityIcon: ''
    })

    if (!props.hasBack) {
      data.visibilityBack = 'hidden'
    }
    if (!props.hasIcon) {
      data.visibilityIcon = 'hidden'
    }

    return {
      data
    }
  },
  methods: {
    // 이전 페이지로 이동
    goBack () {
      this.$router.go(-1)
    }
  }
}
</script>

<style scoped lang="scss">
.header{
  // position: sticky;
  top: 0;
  padding-left: 5%;
  padding-right: 5%;
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-top: 5%;
  margin-bottom: 5%;
}
.hidden{
  visibility: hidden;
}
.title {
  color: #7B371C;
  font-size: 20px;
  font-weight: bold;
}
</style>
