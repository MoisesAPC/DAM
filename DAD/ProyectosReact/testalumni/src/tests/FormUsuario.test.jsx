import { render, screen } from "@testing-library/react";
import FormUsuario from "../components/FormUsuario";
import userEvent from "@testing-library/user-event";

describe('FormUsuario componente', () => {

    let textfield;
    let button;
    let cabecera_h2;
    let nombre;

    beforeEach(() => {
        render(<FormUsuario />); //aquí generamos el virtual DOM
        textfield = screen.getByRole('input');
        button = screen.getByRole('button', { name: 'Submit' });
        cabecera_h2 = screen.getByRole('heading', {level : 2})
    });

    it('se renderiza el textfield', () => {
        //comprobamos si la <Box> está en nuestro documento
        expect(textfield).toBeInTheDocument();
    });

    it('se renderiza el boton', () => {
        //comprobamos si la <Box> está en nuestro documento
        expect(button).toBeInTheDocument();
    });

    it('se renderiza la cabecera h2', () => {
        //comprobamos si la <Box> está en nuestro documento
        expect(cabecera_h2).toBeInTheDocument();
    });
    
    it('comprobar funcionamiento del formulario', async () => {
        //usamos el userEvent. Lo primero que debemos hacer es inicializarlo:
        const user = userEvent.setup()
        nombre = screen.getByLabelText('Nombre')

        //El usuario borra el contenido del campo de texto txt.
        await user.clear(nombre)

        //El usuario rellena un campo de texto con el texto
        await user.type(nombre, 'moises')

        //El usuario hace click en un botón.
        await user.click(button)

        // Comprobamos que el nombre está vacío
        expect(nombre).toHaveValue('');
    });
});