<template>
  <div class="pg">
    <div class="page">
      <div id="mallPage" class=" mallist tmall- page-not-market ">

        <!-- 头部搜索 -->
        <div id="header" class=" header-list-app">
          <div class="headerLayout">
            <div class="headerCon ">
              <!-- Logo-->
              <h1 id="mallLogo">
                <img src="./assets/images/jdlogo.png" alt="">
              </h1>

              <div class="header-extra">

                <!--搜索-->
                <div id="mallSearch" class="mall-search">
                  <form name="searchTop" class="mallSearch-form clearfix">
                    <fieldset>
                      <legend>搜索</legend>
                      <div class="mallSearch-input clearfix">
                        <div class="s-combobox" id="s-combobox-685">
                          <div class="s-combobox-input-wrap">
                            <input v-model="keywords" type="text" autocomplete="off" value="java" id="mq"
                                   class="s-combobox-input" aria-haspopup="true">
                          </div>
                        </div>
                        <button type="submit" @click.prevent="searchKeywords()" id="searchbtn">搜索</button>
                      </div>
                    </fieldset>
                  </form>
                  <ul class="relKeyTop">
                    <li><a>SpringBoot</a></li>
                    <li><a>SpringCloud</a></li>
                    <li><a>MySQL</a></li>
                    <li><a>Redis</a></li>
                    <li><a>RabbitMQ</a></li>
                  </ul>
                </div>
              </div>
            </div>
          </div>
        </div>

        <!-- 商品详情页面 -->
        <div id="content">
          <div class="main">
            <!-- 品牌分类 -->
            <form class="navAttrsForm">
              <div class="attrs j_NavAttrs" style="display:block">
                <div class="brandAttr j_nav_brand">
                  <div class="j_Brand attr">
                    <div class="attrKey">
                      品牌
                    </div>
                    <div class="attrValues">
                      <ul class="av-collapse row-2">
                        <li><a href="#"> KHighness </a></li>
                      </ul>
                    </div>
                  </div>
                </div>
              </div>
            </form>

            <!-- 排序规则 -->
            <div class="filter clearfix">
              <a class="fSort fSort-cur">综合<i class="f-ico-arrow-d"></i></a>
              <a class="fSort">人气<i class="f-ico-arrow-d"></i></a>
              <a class="fSort">新品<i class="f-ico-arrow-d"></i></a>
              <a class="fSort">销量<i class="f-ico-arrow-d"></i></a>
              <a class="fSort">价格<i class="f-ico-triangle-mt"></i><i class="f-ico-triangle-mb"></i></a>
            </div>

            <!-- 商品详情 -->
            <div class="view grid-nosku">
              <div class="product" v-for="result in results" :key="result">
                <div class="product-iWrap">
                  <!--商品封面-->
                  <div class="productImg-wrap">
                    <a class="productImg">
                      <img :src="result.img">
                    </a>
                  </div>
                  <!--价格-->
                  <p class="productPrice">
                    <em>{{result.price}}</em>
                  </p>
                  <!--标题-->
                  <p class="productTitle">
                    <a v-html="result.name"></a>
                  </p>
                  <!-- 店铺名 -->
                  <div class="productShop">
                    <span>店铺： KHighness</span>
                  </div>
                  <!-- 成交信息 -->
                  <p class="productStatus">
                    <span>月成交<em>XXX</em></span>
                    <span>评价 <a>XXX</a></span>
                  </p>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>

export default {
  name: 'App',
  data () {
    return {
      keywords: '',
      results: []
    }
  },
  methods: {
    searchKeywords () {
      var keywords = this.keywords
      this.$http.get('/search/' + keywords + '/1/10').then(response => {
        this.results = response.data
      })
    }
  }
}
</script>

<style>
  @import "assets/css/style.css";
</style>
