import React from 'react';
import Button from '@mui/material/Button';
import Box from '@mui/material/Box';
import Grid from '@mui/material/Grid2';

function AppGrid() {
  return (
    <Grid container spacing={2}>
    <Grid size={8}>
      <Button variant='contained' fullWidth>1</Button>
    </Grid>
    <Grid size={4}>
      <Button variant='contained' fullWidth>2</Button>
    </Grid>
    <Grid size={4}>
      <Button variant='contained' fullWidth>3</Button>
    </Grid>
    <Grid size={8}>
      <Button variant='contained' fullWidth>4</Button>
    </Grid>
  </Grid>
  );
};

export default AppGrid;