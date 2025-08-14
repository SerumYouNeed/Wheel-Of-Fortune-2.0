import { useState } from 'react'
import reactLogo from './assets/react.svg'
import viteLogo from '/vite.svg'
import './App.css'

function App() {
  const [count, setCount] = useState(0)

  return (
    <>
     <WelcomeHeader />
     <ul>
         <li>Log in</li>
         <li>Sign in</li>
         <li>Play as guest</li>
     </ul>

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