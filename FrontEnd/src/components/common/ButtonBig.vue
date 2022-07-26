<template>
  <div class="button-wrap">
    <button :class="buttonUsage">{{ buttonText }}</button>
  </div>
</template>

<script>
export default {
  name: 'ButtonBig',
  props: {
    buttonUsage: String,
    buttonText: String
  },

  methods: {
  }
}
</script>

<style scoped>
.positive {
  background-color: #7b371c;
  width: 110px;
  height: 30px;
  border-radius: 10px;
  border: none;
  color: white;
}
.button-wrap {
  width: 110px;
  height: 30px;
  margin: 0 auto;
}
</style>
