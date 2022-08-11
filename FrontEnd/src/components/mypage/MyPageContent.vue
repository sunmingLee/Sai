<template>
    <div class="mypage-wrap">
        <p>게시물 {{feedList.length}}개</p>
        <div class="flex">
            <div class="mypage-content">
                <div class="content-wrap" v-for="(feed, index) in feedList" :key="index">
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
    const info = {
      userId: localStorage.getItem('userId'),
      familyId: localStorage.getItem('familyId')
    }
    // 피드 전체 리스트 조회
    this.feedAllList(info)
    
  },
  mounted() {
  },
  computed: {
    ...mapState(boardStore, ['feedList'])
  },
  methods: {
    ...mapActions(boardStore, ['feedAllList'])
  }
}
</script>
<style lang="scss" scoped>
p {
  margin: 0;
}
.mypage-wrap {
  width: 900px;

  .flex{
    display: flex;
    justify-content: center;
    text-align: center;

    .input-wrap{
      text-align: left;
      margin-bottom: 100px;

      .name-wrap, .email-wrap {
        margin: 10px 0 10px 0;
      }
    }
  }
}

</style>



