<template>
  <!-- <div class="input-group">
    <span class="input-group-text" id="addon-wrapping">{{labelName}}</span>
    <input type="text" class="form-control" placeholder="Recipient's username" aria-label="Recipient's username" aria-describedby="button-addon2">
    <Button buttonClass="small information" buttonText="확인"></Button>
  </div> -->
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
    <!-- <p v-if="hasError && !isValid">{{ errorMessage }}</p> -->
  </div>
</template>

<script>
export default {
  name: 'InputBox',
  props: {
    hasLabel: Boolean,
    labelName: String,
    inputType: String,
    validDisabled: Boolean,
    inputValue: String
    // hasError: Boolean,
    // errorMessage: String,
    // isValid: Boolean
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
