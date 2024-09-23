import { Fragment, useState } from 'react'
import './App.css'
import Button from '@mui/material/Button'
import SendIcon from '@mui/icons-material/Send';
import { Avatar } from '@mui/material';
import { Typography } from '@mui/material';
import Stack from '@mui/material/Stack';
import '@fontsource/roboto/300.css';
import '@fontsource/roboto/400.css';
import '@fontsource/roboto/500.css';
import '@fontsource/roboto/700.css';

function App() {
  const [count, setCount] = useState(0)
  
  const datos = {
    alt: 'Un gato',
    imageurl: 'src/assets/gato.jpg',
  }

  function handleClick() {
    setCount(count + 1)
  }

  return (
    <Stack
      direction="row"
      spacing={2}
      sx={{
        justifyContent: "center",
        alignItems: "center",
      }}
    >
      <div className='divprint'>
        <Typography variant="h1" component="h2" sx={{ color: 'success.dark', fontWeight: 'bold'}}>
          Soy un gato y vivo feliz
        </Typography>

        <Avatar alt={datos.alt} src={datos.imageurl} sx={{ width: 450, height: 450, mb: 2}}></Avatar>

        <Button variant='contained' sx={{color: 'orange', backgroundColor: 'green', fontWeight: 'bold'}} endIcon={<SendIcon />} onClick={handleClick} disabled>
          Me has hecho {count} rascaditas
        </Button>
      </div>
    </Stack>
  )
}

export default App
