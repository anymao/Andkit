package com.anymore.wanandroid.route

/**
 * 路由表
 * Created by liuyuanmao on 2020/1/20.
 */
/////WanAndroid User Group
const val USER_GROUP = "user"

const val LOGIN = "login"
const val REGISTER = "register"
const val SERVICE = "service"

const val USER_LOGIN = "/$USER_GROUP/$LOGIN"
const val USER_REGISTER = "/$USER_GROUP/$REGISTER"
const val USER_SERVICE = "/$USER_GROUP/$SERVICE"

/////WanAndroid Browse Group

const val BROWSE_GROUP = "browse"
const val URL = "url"
const val FRAGMENT = "fragment"

const val BROWSE_URL = "/$BROWSE_GROUP/$URL"
const val BROWSE_FRAGMENT = "/$BROWSE_GROUP/$FRAGMENT"

/////WanAndroid Main Module
const val MAIN_GROUP = "main"
const val MAIN = "main"

const val MAIN_PAGE = "/$MAIN_GROUP/$MAIN"

/////WanAndroid Articles Module
const val ARTICLES_GROUP = "articles"
const val KNOWLEDGE = "main"
const val HOMEPAGE_FRAGMENT = "HomepageFragment"
const val DISCOVERY_FRAGMENT = "DiscoveryFragment"
const val KNOWLEDGES_ARTICLES_FRAGMENT = "KnowledgesArticlesFragment"
const val SEARCH_ACTIVITY = "ArticlesSearchActivity"

const val ARTICLES_KNOWLEDGE = "/$ARTICLES_GROUP/$KNOWLEDGE"
const val ARTICLES_HOMEPAGE_FRAGMENT = "/$ARTICLES_GROUP/$HOMEPAGE_FRAGMENT"
const val ARTICLES_DISCOVERY_FRAGMENT = "/$ARTICLES_GROUP/$DISCOVERY_FRAGMENT"
const val ARTICLES_KNOWLEDGES_ARTICLES_FRAGMENT = "/$ARTICLES_GROUP/$KNOWLEDGES_ARTICLES_FRAGMENT"
const val ARTICLES_SEARCH = "/$ARTICLES_GROUP/$SEARCH_ACTIVITY"


/////WanAndroid Mine Module
const val MINE_GROUP = "mine"
const val TODO_LIST = "todolist"
const val TODO = "todo"
const val FLUTTER = "flutter"
const val MINE_FRAGMENT = "MineFragment"
const val COLLECTED_ARTICLES_FRAGMENT = "CollectedArticlesFragment"
const val MINE_TODO = "/$MINE_GROUP/$TODO"
const val MINE_TODO_LIST = "/$MINE_GROUP/$TODO_LIST"
const val MINE_FLUTTER = "/$MINE_GROUP/$FLUTTER"
const val MINE_MINE_FRAGMENT = "/$MINE_GROUP/$MINE_FRAGMENT"
const val MINE_COLLECTED_ARTICLES_FRAGMENT = "/$MINE_GROUP/$COLLECTED_ARTICLES_FRAGMENT"

const val INTERCEPTOR_LOGIN = "login"
const val SCHEME_HTTP = "http"
const val SCHEME_HTTPS = "https"
