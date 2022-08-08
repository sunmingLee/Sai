<template>
    <div class="guide-wrap">
        <p class="guide1">{{ guide }}</p>
        <p class="guide2">{{ guide2 }}</p>
    </div>
</template>
<script>
export default {
  name: 'GuideMessage',
  props: {
    guide: String,
    guide2: String
  }
}
</script>
<style scoped>
.guide1, .guide2{
  font-size: 15px;
  padding: 5px;
}
.guide-wrap {
  margin-top: 100px;
  margin-bottom: 100px;
}
</style>
