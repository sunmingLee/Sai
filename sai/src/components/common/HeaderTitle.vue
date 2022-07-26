<template>
    <div class="header">
        <img style="float: left;" src="@/assets/images/arrow-left.png" alt="arrow-left" >
        <h2>{{ title }}</h2>
    </div>
</template>
<script>
export default {
  name: 'HeaderTitle',
  props: {
    title: String
  }
}
</script>

<style>
h2{
  color: #7B371C;
  /* float: ; */
}
.header{
  position: relative;
}
</style>
