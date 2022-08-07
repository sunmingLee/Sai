<template>
  <div class="picture-wrap">
    <HeaderTitle :title="this.$route.params.folderName" hasBack="true"></HeaderTitle>
    <div class="button-wrap"><Button buttonClass="small information" buttonText="사진 삭제"></Button></div>
    <div class="container text-center">
        <div class="row" v-for="(item, index) in pictures" :key="index" >
            <div class="col">
            {{ item.ImagePath }}
            </div>
            <div class="col">
            {{ item.ImagePath }}
            </div>
            <div class="col">
            {{ item.ImagePath }}
            </div>
        </div>
    </div>
     <!-- Button trigger modal -->
    <button id="btn-modal" type="button" class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#exampleModal">
    <img style="width:25px;" src="@/assets/images/plus-lg.svg" alt="plus">
    </button>

    <!-- Modal -->
    <div class="modal fade" id="exampleModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
        <div class="modal-header">
            <h5 class="modal-title" id="exampleModalLabel">사진 추가</h5>
            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
        </div>
        <div class="modal-body">
            <input type="file" class="form-control" id="customFile" />
        </div>
        <div class="modal-footer">
            <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">취소</button>
            <button type="button" class="btn btn-primary" data-bs-dismiss="modal">추가</button>
        </div>
        </div>
    </div>
    </div>

  </div>
</template>

<script>
import HeaderTitle from '../common/HeaderTitle.vue'
import Button from '../common/Button.vue'
export default {
  components: { HeaderTitle, Button },
  data () {
    return {
      pictures: [
        { ImagePath: 'column' },
        { ImagePath: 'column' },
        { ImagePath: 'column' },
        { ImagePath: 'column' },
        { ImagePath: 'column' },
        { ImagePath: 'column' }
      ]
    }
  }
}
</script>

<style scoped lang="scss">
.picture-wrap{
    height: 90%;
    .container{
        height: 100%;
        .row{
            height: 30%;
            .col{
                border: 0.5px solid;
            }
        }
    }
}
.button-wrap{
    text-align: right;
    margin-bottom: 1%;
}
.btn{
    height: 40px;
    &-primary{
    --bs-btn-bg: #7b371c;
    --bs-btn-border-color: #7b371c;
    --bs-btn-hover-bg: #54210d;
    --bs-btn-hover-border-color: #54210d;
    --bs-btn-focus-shadow-rgb: none;
    }
    &#btn-modal{
        position: absolute;
        right: 10%;
        bottom: 10%;
        border-radius: 50%;
        width: 50px;
        height: 50px;
    }
}
</style>
