<template>
    <div class="content-wrap">
        <p>게시물 {{myFeedList.length}}개</p>
        <div class="flex">
            <div class="mypage-content">
                <div class="content-wrap" v-for="(feed, index) in myFeedList" :key="index">
                    <div></div>
                </div>
            </div>
        </div>
        <!-- <div class="memo-content">
            <div class="content-wrap" v-for="(memo, index) in memoAllList" :key="index">
                <div :class="memo.color">
                    <span>{{memoAllList[index].memoContent}}</span>
                </div>
                <button type="button" class="btn-close" aria-label="Close" @click="removeMemo(memo.memoId)"></button>
            </div>
        </div> -->
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
        feedCount: 0
    }
  },
  created() {
    // 피드 조회
    const userId = localStorage.getItem('userId')
    // 피드 전체 리스트 조회
    this.myFeedAllList(userId)
  },
  mounted() {
  },
  computed: {
    ...mapState(boardStore, ['myFeedList'])
  },
  methods: {
    ...mapActions(boardStore, ['myFeedAllList'])
  }
}
</script>
<style lang="scss" scoped>
p {
  margin: 0;
}
.content-wrap {
  top: 0;
  padding: 0 5%;
  display: flex;
  justify-content: center;
  align-items: center;
  margin: 5% 0 2% 0;  
}
// .mypage-wrap {
//   width: 900px;

//   .flex{
//     display: flex;
//     justify-content: center;
//     text-align: center;

//     .input-wrap{
//       text-align: left;
//       margin-bottom: 100px;

//       .name-wrap, .email-wrap {
//         margin: 10px 0 10px 0;
//       }
//     }
//   }
// }

</style>



