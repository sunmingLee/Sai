<template>
  <div>
    <label for="input-box" v-if="hasLabel">{{ labelName }}</label>
    <input class="input-box" id="input-box" type="text" />
  </div>
</template>

<script>
export default {
  name: 'InputBox',
  props: {
    hasLabel: Boolean,
    labelName: String
  }
}
</script>

<style>
.input-box {
  margin-left: 5px;
  margin-bottom: 10px;
  width: 190px;
  border-radius: 5px;
  border: 1px solid #ae5f40;
}
</style>
