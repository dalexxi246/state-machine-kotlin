package com.example.statemachines

import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs
import io.mockk.verify
import org.junit.Assert.*
import org.junit.Test

class StateMachineTest {

    @Test
    fun `Register state into states map`() {

        val transition = mockk<Transition>()
        val stateMachine = StateMachine(transition)
        assertEquals(0, stateMachine.actualState)
        assertEquals(0, stateMachine.validStatesMap.size)

        stateMachine.addValidState(100, listOf(200, 300))
        assertEquals(1, stateMachine.validStatesMap.size)
        assertEquals(listOf(200, 300), stateMachine.validStatesMap[100])

        stateMachine.addValidState(100, listOf(400, 500))
        assertEquals(1, stateMachine.validStatesMap.size)
        assertEquals(listOf(200, 300), stateMachine.validStatesMap[100])

    }

    @Test
    fun `Register transition into transitions map`() {
        val transition = mockk<Transition>()
        val stateMachine = StateMachine(transition)
        assertEquals(0, stateMachine.actualState)
        assertEquals(0, stateMachine.validStatesMap.size)

        val transition1 = object : Transition {
            override fun onEnter() {
                // TODO("Not yet implemented")
            }

            override fun onExit() {
                // TODO("Not yet implemented")
            }
        }

        val transition2 = object : Transition {
            override fun onEnter() {
                // TODO("Not yet implemented")
            }

            override fun onExit() {
                // TODO("Not yet implemented")
            }
        }
        stateMachine.addTransition(100, transition1)
        assertEquals(transition1, stateMachine.stateTransitions[100])
        assertEquals(1, stateMachine.stateTransitions.size)

        stateMachine.addTransition(100, transition2)
        assertEquals(transition1, stateMachine.stateTransitions[100])
        assertEquals(1, stateMachine.stateTransitions.size)

    }

    @Test
    fun `Valid transition`(){

        val transitionFor0 = mockk<Transition>()
        every { transitionFor0.onExit() } just runs

        val stateMachine = StateMachine(transitionFor0)
        assertEquals(0, stateMachine.actualState)
        assertEquals(0, stateMachine.validStatesMap.size)

        val transitionFor100 = mockk<Transition>()
        every { transitionFor100.onEnter() } just runs
        // every { transitionFor100.onExit() } just runs

        stateMachine.addValidState(0, listOf(100))
        stateMachine.addValidState(100, listOf(200, 300))

        stateMachine.addTransition(100, transitionFor100)
        stateMachine.addTransition(20000, transitionFor100)

        stateMachine.fire(20000)
        assertEquals(0, stateMachine.actualState)

        stateMachine.fire(100)
        assertEquals(100, stateMachine.actualState)

        verify {
            transitionFor0.onExit()
            transitionFor100.onEnter()
        }

    }

}