<template>
  <div class="form-wrap">
    <table>
      <colgroup>
        <col width=40%>
        <col width=50%>
      </colgroup>
      <tr>
        <td><label class="label-box" for="input-box" v-if="hasLabel">{{ labelName }}</label></td>
        <td><input class="input-box" :type="inputType" :disabled="validDisabled === ''" :value="inputValue" @keyup="inputCheck"/></td>
      </tr>
    </table>
  </div>
</template>

<script>
// import {emit} from 'vue'
// export default {
//   name: 'InputBox',
//   props: {
//     hasLabel: String,
//     labelName: String,
//     inputType: String,
//     validDisabled: Boolean,
//     inputValue: String
//   },
//   setup(props, context) {
//     const isInputValue = ref(props.inputValue)
//     const inputCheck = () => {
//       context.emit('inputCheck', isInputValue)
//     }
//     return {
//       isInputValue,
//       inputCheck
//     }
//   }
// }
export default {
  name: 'InputBox',
  props: {
    hasLabel: String,
    labelName: String,
    inputType: String,
    validDisabled: Boolean,
    inputValue: String
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
.input-box {
  text-align: center;
  margin-bottom: 1%;
  width: 140px;
  border-radius: 5px;
  border: 1px solid #ae5f40;
}
.label-box {
  color: #AE5F40;
}
p{
  margin: 0;
  color: red;
  font-size: 12px;
}
table {
  border-collapse: collapse;
  table-layout: fixed;
  width: 260px;
}

td {
  text-overflow: ellipsis;
  overflow: hidden;
  white-space: nowrap;
}
.form-wrap{
  display: inline-block;
}

.form-wrap > table > tr> td:nth-child(2) {
  text-align: right;
}
</style>
