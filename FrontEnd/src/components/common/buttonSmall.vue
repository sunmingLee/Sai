<template>
  <div>
    <button :class="buttonUsage">{{ buttonText }}</button>
  </div>
</template>

<script>
export default {
  name: 'ButtonSmall',
  props: {
    buttonUsage: String,
    buttonText: String
  },

  methods: {
  }
}
</script>

<style>
.positive {
  background-color: #7b371c;
  width: 80px;
  height: 30px;
  border-radius: 10px;
  border: none;
  color: white;
}
</style>
