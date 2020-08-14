package com.example.statemachines

class StateMachine(initialTransition: Transition) {
    var actualState: Int = 0
        private set

    val validStatesMap: MutableMap<Int, List<Int>> = mutableMapOf()
    val stateTransitions: MutableMap<Int, Transition> = mutableMapOf()

    init {
        addTransition(0, initialTransition)
    }

    fun addValidState(state: Int, destinationStates: List<Int>) {
        if (validStatesMap[state].isNullOrEmpty()) {
            validStatesMap[state] = destinationStates
        }
    }

    fun addTransition(state: Int, transition: Transition) {
        if (stateTransitions[state] == null) {
            stateTransitions[state] = transition
        }
    }

    @Throws(Exception::class)
    fun fire(state: Int): Boolean {
        if (validStatesMap[actualState]?.any { it == state } == false) {
            return false
        }
        stateTransitions[actualState]?.run {
            onExit()
            actualState = state
            stateTransitions[state]?.run { onEnter() }
            return true
        } ?: return false
    }
}