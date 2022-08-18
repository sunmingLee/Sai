<template>  
  <div style="height: 90%">
    <div class="count-wrap">
      <p>게시물 {{this.myFeedCnt}}개</p>
    </div>
    <div class="content-wrap">
      <div class="feed-content">
            <div class="list-wrap" v-for="(feed, index) in myFeedList" :key="index" @click="goDetail(feed.viewBoardResponseDto.boardId)">
                <div v-if="feed.viewBoardResponseDto.pollYn" class="align" >
                  <span>[투표]{{feed.pollResponse.question}}</span>
                </div>
                <div v-else-if="feed.viewBoardResponseDto.boardMediaYn" class="align">
                  <!-- <img src="@/assets/images/byebye.png" class="d-block w-100"> -->
                  <img :src="feed.viewBoardMediaResponseDto[0].boardMediaPath" class="d-block w-100">
                </div>
                <div v-else class="align">
                  <span>{{feed.viewBoardResponseDto.boardContent}}</span>
                </div>
            </div>
      </div>
    </div>
    <nav aria-label="Page navigation example">
      <ul class="pagination">
        <li class="page-item" ><a class="page-link" @click="pagination(this.currentPage-1)">이전</a></li>
        <li class="page-item" v-for="(feed, index) in myFeedPagenationCnt" :key="index" ><a @click="pagination(index)" class="page-link">{{index+1}}</a></li>
        <li class="page-item"><a class="page-link" @click="pagination(this.currentPage+1)">다음</a></li>
      </ul>
    </nav>
  </div>
</template>
<script>
import { mapActions, mapState } from 'vuex'
const userStore = 'userStore'
const boardStore = 'boardStore'

export default {
  name: 'MyPageContent',
  components: {
  },
  data () {
    return {
        feedCount: 0,
        pageNum: 1,
        currentPage: 0,
        pageCount: 3, //화면에 나타낼 페이지 개수
        limit: 2 //한 페이지당 나타날 데이터 개수
    }
  },
  created() {
    // 피드 조회
    const info = {
      userId: localStorage.getItem('userId'),
      page : 0
    }
    // 피드 전체 리스트 조회
    this.myFeedAllList(info)
    console.log(this.myFeedPagenationCnt)
    //지금 전체 개수 7개고 한 페이지에 2개만 나타났으면 좋겠어
    //myFeedPagenationCnt

  },
  mounted() {
  },
  computed: {
    ...mapState(boardStore, ['myFeedList', 'myFeedCnt', 'myFeedPagenationCnt'])
  },
  methods: {
    ...mapActions(boardStore, ['myFeedAllList', 'setBoardId']),
    //상세보기 이동
    goDetail (boardId) {
      this.setBoardId(boardId)
    },
    pagination(index) {
      if(index === this.myFeedPagenationCnt) {
        alert('더 이상 불러올 게시글이 없습니다')
      }
      else if(index < 0) {
        alert('이전 게시글이 없습니다')
      }
      else {
        this.currentPage = index
        const info = {
          userId: localStorage.getItem('userId'),
          page: index
        }
        this.myFeedAllList(info)
      }
    }
  }
}
</script>
<style lang="scss" scoped>
p {
  margin: 0;
}
.count-wrap {
  display: flex;
  margin-left: 30px;
}
.content-wrap {
  display: flex;
    // .flex{
    //     display: flex;
    //     justify-content: center;
    //     text-align: center;
    // }
    .feed-content {
        display: flex;
        justify-content: center;
        flex-wrap: wrap;
        margin: 0 auto;
        .list-wrap {
            width: 235px;
            height: 210px;
            text-align: center;
            margin: 20px;
            .align {
              border: 1px solid #A57966;
              display: flex;
              align-items: center;
              justify-content: center;
              height: 210px;
              .d-block {
                height: 100%;
              }
            }
        }
    }
}
.pagination {
  justify-content: center;
}
.pagination{
  margin-top: 10px;
    justify-content: center;
    --bs-pagination-color: #7b371c;
    --bs-pagination-hover-color : #7b371c;
    --bs-pagination-focus-bg : #7b371c;
    --bs-pagination-focus-color : white;
    --bs-pagination-active-bg : #7b371c;
    --bs-pagination-active-border-color : #7b371c;
}

</style>



