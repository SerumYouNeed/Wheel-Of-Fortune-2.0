import { useState } from 'react'
import './App.css'

function App() {

  return (
    <>
     <WelcomeHeader />
     <Menu />
     <button>Log in</button>
     <button>Log in</button>
     <button>Log in</button>
    </>
  )
}

export default App

function WelcomeHeader() {
    return <h1>Welcome to "Wheel of fortune"!</h1>
}

function Menu() {
    return
     <ul>
         <li>Log in</li>
         <li>Sign in</li>
         <li>Play as guest</li>
         <li>delete</li>
     </ul>

    }