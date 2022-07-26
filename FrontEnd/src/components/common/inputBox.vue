<template>
  <div>
    <label class="label-box" for="input-box" v-if="hasLabel">{{ labelName }}</label>
    <input class="input-box" id="input-box" type="text" @keyup="inputCheck"/>
  </div>
</template>

<script>

export default {
  name: 'InputBox',
  props: {
    hasLabel: Boolean,
    labelName: String
  },
  methods: {
    // 부모 컴포넌트에게 input 값을 전송
    inputCheck (e) {
      this.$emit('inputCheck', e.target.value)
    }
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
.label-box {
  color: #AE5F40;
}
</style>
