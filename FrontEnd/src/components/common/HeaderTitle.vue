<template>
    <div>
        <h2 class="title">{{ title }}</h2>
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
.title {
  color: #7B371C;
  font-size: 20px;
}
</style>
