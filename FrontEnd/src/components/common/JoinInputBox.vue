<template>
  <div class="form-wrap">
    <div class="id-title">
      <label class="label-box" for="input-box" v-if="hasLabel">{{ labelName }}</label>
    </div>
    <input class="input-box" :type="inputType" :disabled="validDisabled === 'disabled'" :value="inputValue" @keyup="inputCheck"/>
  </div>
</template>

<script>
export default {
  name: 'JoinInputBox',
  props: {
    hasLabel: String,
    labelName: String,
    inputType: String,
    validDisabled: String,
    inputValue: String,
    placeholder: String
  },
  methods: {
    // 부모 컴포넌트에게 input 값을 전송
    inputCheck (e) {
      this.$emit('inputCheck', e.target.value)
    }
  }
}
</script>

<style scoped>

.id-title {
  flex-basis: 300px;
  justify-content: flex-start;
}

.input-box {
  /* text-align: center;
  margin-bottom: 1%;
  width: 140px;
  border-radius: 5px;
  border: 1px solid #ae5f40; */
  padding-left: 20px;
  text-align: left;
  margin-bottom: 1%;
  min-width: 300px;
  min-height: 50px;
  border-radius: 5px;
  border: 1px solid #ae5f40;
}
.input-underline {
  margin-left: 5px;
  margin-bottom: 10px;
  width: 190px;
  border: none;
  border-bottom: 1px solid #ae5f40;
}

.label-box {
  /* color: #AE5F40; */
  color: #AE5F40;
  font-weight: bold;
}
p{
  margin: 0;
  color: red;
  font-size: 12px;
}
/* table {
  border-collapse: collapse;
  table-layout: fixed;
  width: 260px;
}

td {
  text-overflow: ellipsis;
  overflow: hidden;
  white-space: nowrap;
} */
.form-wrap{
  display: inline-block;
}

/* .form-wrap > table > tr> td:nth-child(2) {
  text-align: right;
} */
</style>
