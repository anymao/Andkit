package com.anymore.wanandroid.route

/**
 * Created by anymore on 2020/4/10.
 */
//前缀
const val PARAMETER_PREFIX = "EXTRA_"
//是否需要登录标识
const val EXTRA_NEED_LOGIN = "${PARAMETER_PREFIX}NEED_LOGIN"
//cid => int
const val CID = "${PARAMETER_PREFIX}CID"
//url => String
const val URL_VALUE = "${PARAMETER_PREFIX}URL_VALUE"
//title => String
const val TITLE = "${PARAMETER_PREFIX}TITLE"
//FragmentActivity Fragment类路径全名 => String
const val FRAGMENT_NAME = "${PARAMETER_PREFIX}FRAGMENT_NAME"
//FragmentActivity Fragment路由名称 => String
const val FRAGMENT_ROUTE = "${PARAMETER_PREFIX}FRAGMENT_ROUTE"
//FragmentActivity 向Fragment内传参参数 => Bundle
const val PARAMS = "${PARAMETER_PREFIX}PARAMS"
//KnowledgesTabActivity界面初始参数 => Knowledge
const val KONWLEDGES = "${PARAMETER_PREFIX}KONWLEDGE"
//flutter 页面初始路由 => String
const val FLUTTER_INITIAL_ROUTE = "${PARAMETER_PREFIX}INITIAL_ROUTE"
//Todo操作类型 => int
const val TODO_OPERATION = "${PARAMETER_PREFIX}TODO_OPERATION"
//Todo数据 =>
const val TODO_DATA = "${PARAMETER_PREFIX}TODO_DATA"