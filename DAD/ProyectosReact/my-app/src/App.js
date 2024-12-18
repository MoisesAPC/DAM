import logo from './logo.svg';
import Button from '@mui/material/Button';
import './App.css';

function App() {
  return (
    <div className="App">
      <header className="App-header">
        <img src={logo} className="App-logo" alt="logo" />
        <h1>Mi cabecera h1</h1>
        <p>
          Edit <code>src/App.js</code> and save to reload.
            <Button
                role="button"
                type="submit"
                variant="outlined"
            >
                Botón de enviar de prueba
            </Button>
        </p>
        <a
          className="App-link"
          href="https://reactjs.org"
          target="_blank"
          rel="noopener noreferrer"
        >
          Learn React
        </a>
      </header>
    </div>
  );
}

export default App;
