<template>
  <div class="header">
      <img :class="visibilityBack" src="@/assets/images/arrow-left-solid.svg" alt="arrow-left" >
      <h2>{{ title }}</h2>
      <img :class="visibilityIcon" src="@/assets/images/calendar-days-solid.svg" alt="calendar">
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

<style>
h2{
  color: #7B371C;
}
.header{
  position: sticky;
  top: 0;
  padding-left: 5px;
  padding-right: 10px;
  display: flex;
  justify-content: space-between;
  align-items: center;
}
.hidden{
  visibility: hidden;
}
</style>
