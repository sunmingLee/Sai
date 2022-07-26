<template>
    <div>
        <p class="guide1">{{ guide }}</p>
        <p class="guide2">{{ guide2 }}</p>
    </div>
</template>
<script>
export default {
  name: 'SearchGuide',
  props: {
    guide: String,
    guide2: String
  }
}
</script>
<style>
.guide1, .guide2{
  font-size: 15px;
  padding: 5px;
}
.guide1 {
  margin-top: 100px;
}
.guide2 {
  margin-bottom: 100px;
}
</style>
