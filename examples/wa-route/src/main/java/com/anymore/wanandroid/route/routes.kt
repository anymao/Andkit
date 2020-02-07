package com.anymore.wanandroid.route

/**
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

const val ARTICLES_KNOWLEDGE = "/$ARTICLES_GROUP/$KNOWLEDGE"

const val MINE_GROUP = "mine"
const val TODO_LIST = "todolist"
const val TODO = "todo"
const val FLUTTER = "flutter"
const val MINE_TODO = "/$MINE_GROUP/$TODO"
const val MINE_TODO_LIST = "/$MINE_GROUP/$TODO_LIST"
const val MINE_FLUTTER = "/$MINE_GROUP/$FLUTTER"

const val INTERCEPTOR_LOGIN = "login"
