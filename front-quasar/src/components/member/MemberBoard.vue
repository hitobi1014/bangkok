<script setup>
import { reactive, ref } from 'vue'
import { useRouter } from 'vue-router'

const categories = reactive([
  {
    path: 'notice',
    name: '공지사항'
  },
  {
    path: 'free',
    name: '자유게시판'
  },
  {
    path: 'news',
    name: 'NEWS'
  }
])
const router = useRouter()

const move = (category) => {
  selectedCategory.value = category.path

  router.push({
    name: category.path,
    state: {
      name: category.name,
      path: category.path
    }
  })
}

const selectedCategory = ref('')
</script>

<template>
  <div class="board-container">
    <!--공지사항/자유게시판/NEWS-->
    <div>
      <ul class="row items-center q-pa-none q-ma-none">
        <li v-for="category in categories" :key="category.path">
          <a
            @click="move(category)"
            class="row justify-center items-center"
            :class="{ selected: selectedCategory === category.path }"
          >
            {{ category.name }}
          </a>
        </li>
      </ul>
    </div>
    <router-view />
  </div>
</template>

<style scoped lang="scss">
@import '@/assets/css/reuse-var';

@include a-deco-none;
@include list-none;

.selected {
  border: 1px solid black;
}

.board-container {
  @include width-1000-layout;

  margin-top: 70px;

  ul {
    height: 50px;

    > li {
      flex: 1;
      height: 100%;
      border: 1px solid #d1d1d1;
    }

    > li:not(:first-child) {
      border-left: 0;
    }
  }

  li > a {
    font-size: 16px;
    color: black;
    width: 100%;
    height: 100%;
    &:hover {
      cursor: pointer;
    }
  }
}
</style>