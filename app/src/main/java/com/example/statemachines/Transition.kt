package com.example.statemachines

interface Transition {
    fun onEnter()
    fun onExit()
}