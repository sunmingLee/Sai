<template>
  <div>
    <button :class="buttonClass">{{ buttonText }}</button>
  </div>
</template>

<script>
import '@/assets/css/button.scss'
export default {
  props: {
    buttonClass: String,
    buttonText: String
  }
}
</script>

<style scoped lang="scss">
</style>
