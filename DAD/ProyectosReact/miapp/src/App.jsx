import { Fragment, useState } from 'react'
import reactLogo from './assets/react.svg'
import viteLogo from '/vite.svg'
import './App.css'

function App() {
  const [count, setCount] = useState(0)
  
  const datos = {
    descripcion: 'una foto del código',
    imageurl: 'src/assets/foto.png',
    tamano: 450,
  }

  return (
    <div>
      <h1>Hola. Esto es {datos.descripcion}</h1>
      <br/>
      <img className='avatar' src={datos.imageurl} alt='imagen'></img>
      <h1>Adiós</h1>
    </div>
  )
}

export default App
