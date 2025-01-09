import { useState } from "react";
import { Box, Button } from "@mui/material";


function TextBox2() {
    const [txtColor, setTxtColor] = useState('rgb(255,192,203)');

    return (
        <div>
            <Box
                role = 'caja' 
                title='es una caja'
                style={{ color: txtColor}}
            >
               Hola mundo, cómo vamos.
            </Box>
            <Button 
                variant = 'outlined' 
                onClick={() => setTxtColor(txtColor === 'rgb(255,192,203)' ? 'rgb(0,0,0)': 'rgb(255,192,203)')}
            > 
                Pulsa para modificar el color del texto
            </Button>
        </div>
    )
}
export default TextBox2;