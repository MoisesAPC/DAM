import React from 'react';
import Button from '@mui/material/Button';
import Box from '@mui/material/Box';
import Grid from '@mui/material/Grid2';

export default function AppGrid() {
  return (
    <Grid container spacing={2}>    {/* `spacing={2}` es para que los botones tengan espaciado entre ellos */}
        <Grid size={{ xs: 12, md: 4, lg: 2, xl: 6 }}>
            <Button variant='contained' fullWidth>Botón 1</Button>
        </Grid>

        <Grid size={{ xs: 12, md: 4, lg: 2, xl: 6 }}>
            <Button variant='contained' fullWidth>Botón 2</Button>
        </Grid>

        <Grid size={{ xs: 12, md: 6, lg: 2, xl: 12 }}>
            <Button variant='contained' fullWidth>Botón 3</Button>
        </Grid>
    </Grid>
  );
};