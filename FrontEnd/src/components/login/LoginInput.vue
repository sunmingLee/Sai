<template>
  <div>
    <InputBox :hasLabel="true" labelName="ID"></InputBox>
    <InputBox :hasLabel="true" labelName="PW"></InputBox>
    <div align="right" style="margin-right: 70px">
      <ButtonSmall
        id="buttonLogin"
        buttonUsage="positive"
        buttonText="LOGIN"
      ></ButtonSmall>
    </div>
  </div>
</template>

<script>
import InputBox from '@/components/common/InputBox.vue'
import ButtonSmall from '@/components/common/ButtonSmall.vue'
export default {
  name: 'LoginInput',
  components: {
    InputBox,
    ButtonSmall
  }
}
</script>

<style></style>
