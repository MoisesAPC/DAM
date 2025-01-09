import { render, screen } from "@testing-library/react";
import TextBox2 from "../components/TextBox2";
import userEvent from "@testing-library/user-event";

describe('TextBox2 componente', () => {

    let box;
    let button;
    beforeEach(() => {
        render(<TextBox2 />); //aquí generamos el virtual DOM
        //el name de Box lo definimos en su atributo title (fijarse en el código)
        box = screen.getByRole('caja', { name: 'es una caja' });
        //el name del botón es el propio título que le ponemos al botón
        button = screen.getByRole('button', { name: 'Pulsa para modificar el color del texto' });
    });

    it('la caja de texto se encuentra en el documento', () => {
        //comprobamos si la <Box> está en nuestro documento
        expect(box).toBeInTheDocument();
    });

    it('el botón se encuentra en el documento', () => {
        //comprobamos si el botón está en nuestro documento
        expect(button).toBeInTheDocument();
    });
    
    it('la caja con el texto tiene un color de texto inicial', () => {
  
        //comprobamos si el Box tiene un color de texto rosa
        expect(box).toHaveStyle({
            color: 'rgb(255,192,203)'
        });
    });
    
    it('comprobar si al pulsar el botón cambia el color del texto', async () => {
     
        //usamos el userEvent. Lo primero que debemos hacer es inicializarlo:
        const user = userEvent.setup()
        //simulamos que el usuario clica el botón               
        await user.click(button);
        //Ahora comprobamos que al pulsar en el botón se cambia
        //el color de fondo de la caja (Box) a tomato.
        expect(box).toHaveStyle({
            color: 'rgb(0,0,0)'
        });
    });
});