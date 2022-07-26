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
  }
}
</script>

<style scoped>
.positive {
  width: 80px;
  height: 30px;
  border-radius: 10px;
  border: none;
  color: white;
  background-color: #7b371c;
}
.information {
  width: 80px;
  height: 30px;
  border-radius: 10px;
  border: none;
  color: white;
  background-color: #A57966;
}
</style>
<!-- <style scoped lang="scss">
button{
  width: 80px;
  height: 30px;
  border-radius: 10px;
  border: none;
  color: white;
  .positive {
    background-color: #7b371c;
  }
  .information {
    background-color: #A57966;
  }
}
</style> -->
