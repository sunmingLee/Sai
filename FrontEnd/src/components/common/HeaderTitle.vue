<template>
  <div class="header">
      <img :class="visibilityBack" src="@/assets/images/arrow-left-solid.svg" alt="arrow-left" style="width: 4%">
      <span class="title">{{ title }}</span>
      <img :class="visibilityIcon" src="@/assets/images/calendar-days-solid.svg" alt="calendar" style="width: 4%">
  </div>
</template>
<script>
export default {
  name: 'HeaderTitle',
  props: {
    title: String,
    hasBack: Boolean,
    hasIcon: Boolean
  },
  data () {
    return {
      visibilityBack: '',
      visibilityIcon: ''
    }
  },
  created () {
    if (!this.hasBack) {
      this.visibilityBack = 'hidden'
    }
    if (!this.hasIcon) {
      this.visibilityIcon = 'hidden'
    }
  }
}
</script>

<style scoped lang="scss">
.header{
  position: sticky;
  top: 0;
  padding-left: 5px;
  padding-right: 10px;
  display: flex;
  justify-content: space-around;
  align-items: center;
  margin-top: 5%;
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
