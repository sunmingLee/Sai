<template>
  <ul class="nav nav-pills mb-3" id="pills-tab" role="tablist">
    <li class="nav-item" role="presentation">
      <button class="nav-link active" id="feed" data-bs-toggle="pill" data-bs-target="#pills-home" type="button" role="tab" aria-controls="pills-home" aria-selected="true">피드</button>
    </li>
    <li class="nav-item" role="presentation">
      <button class="nav-link" id="ref" data-bs-toggle="pill" data-bs-target="#pills-profile" type="button" role="tab" aria-controls="pills-profile" aria-selected="false">냉장고</button>
    </li>
    <li class="nav-item" role="presentation">
      <button class="nav-link" id="album" data-bs-toggle="pill" data-bs-target="#pills-contact" type="button" role="tab" aria-controls="pills-contact" aria-selected="false">앨범</button>
    </li>
    <li class="nav-item" role="presentation">
      <button class="nav-link" id="calender" data-bs-toggle="pill" data-bs-target="#pills-contact" type="button" role="tab" aria-controls="pills-contact" aria-selected="false">달력</button>
    </li>
  </ul>
</template>

<style scoped>
  .nav {
    display: flex;
    flex-direction: row;
    justify-content: center;
    align-items: flex-end;
  }
  .nav-link.active{
    background-color: #7B371C;
  }
</style>
