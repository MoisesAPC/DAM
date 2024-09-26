import Button from '@mui/material/Button';
import SendIcon from '@mui/icons-material/Send';
import { Avatar } from '@mui/material';
import { Typography } from '@mui/material';
import { Stack } from '@mui/material';
import { ThemeProvider } from '@mui/material/styles';
import { createTheme } from '@mui/material/styles';
import { useState } from 'react';
import '@fontsource/roboto/300.css';
import '@fontsource/roboto/400.css';
import '@fontsource/roboto/500.css';
import '@fontsource/roboto/700.css';

function Ficha() {
  const [count, setCount] = useState(0)
  
  const datos = {
    alt: 'Un gato',
    imageurl: 'src/assets/gato.jpg',
  }

  function handleClick() {
    setCount(count + 1)
  }

  // Breakpoints
  const theme = createTheme({
    breakpoints: {
      values: {
        xs: 0,
        sm: 10,
        md: 20
      },
    },
  });

  return (
    <Stack
      direction={{ xs: 'column', sm: 'row', md: 'column' }}
      spacing={{ xs: 1, sm: 2, md: 4 }}
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

export default Ficha
