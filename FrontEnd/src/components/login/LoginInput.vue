<template>
  <div>
    <InputBox :hasLabel="true" labelName="ID"></InputBox>
    <InputBox :hasLabel="true" labelName="PW"></InputBox>
    <div align="right" style="margin-right: 70px">
      <Button
        id="buttonLogin"
        buttonUsage="positive"
        buttonText="LOGIN"
      ></Button>
    </div>
  </div>
</template>

<script>
import InputBox from '@/components/common/InputBox.vue'
import Button from '@/components/common/Button.vue'
export default {
  name: 'LoginInput',
  components: {
    InputBox,
    Button
  }
}
</script>

<style></style>
