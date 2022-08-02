<template>
  <div class="header">
      <img v-if="hasBack" style="float: left;" src="@/assets/images/arrow-left.png" alt="arrow-left" >
      <h2>{{ title }}</h2>
      <img v-if="hasIcon" src="@/assets/images/calendar-month.png" alt="calendar">
  </div>
</template>
<script>
export default {
  name: 'HeaderTitle',
  props: {
    title: String,
    hasBack: Boolean,
    hasIcon: Boolean
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
</style>
