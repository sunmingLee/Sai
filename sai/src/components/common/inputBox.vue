<template>
  <div>
    <label for="inputBox" v-if="hasLabel">{{ labelName }}</label>
    <input id="inputBox" type="text" />
  </div>
</template>

<script>
export default {
  name: "inputBox",
  props: {
    hasLabel: Boolean,
    labelName: String,
  },
};
</script>

<style>
#inputBox {
  margin-left: 5px;
  margin-bottom: 10px;
  width: 190px;
  border-radius: 5px;
  border: 1px solid #ae5f40;
}
</style>
