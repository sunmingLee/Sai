<template>
  <div class="header">
      <img :class="data.visibilityBack" src="@/assets/images/arrow-left-solid.svg" alt="arrow-left" style="width: 7%">
      <span class="title">{{ title }}</span>
      <img :class="data.visibilityIcon" src="@/assets/images/calendar-days-solid.svg" alt="calendar" style="width: 7%">
  </div>
</template>
<script>
import { reactive } from 'vue'

export default {
  name: 'HeaderTitle',
  props: {
    title: String,
    hasBack: Boolean,
    hasIcon: Boolean
  },
  setup (props) {
    const data = reactive({
      visibilityBack: '',
      visibilityIcon: ''
    })

    console.log(data)

    if (!props.hasBack) {
      data.visibilityBack = 'hidden'
    }
    if (!props.hasIcon) {
      data.visibilityIcon = 'hidden'
    }

    return {
      data
    }
  }
}
</script>

<style scoped lang="scss">
.header{
  position: sticky;
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
  // display: none;
}
.title {
  color: #7B371C;
  font-size: 20px;
  font-weight: bold;
}
</style>
