// Más información
// https://jestjs.io/es-ES/docs/expect
// https://jestjs.io/es-ES/docs/expect#matchers
// https://www.npmjs.com/package/@testing-library/jest-dom
// https://testing-library.com/docs/queries/about
// https://testing-library.com/docs/queries/about/#priority
// https://testing-library.com/docs/user-event/intro/

// importamos render y screen: sirven para renderizar un componente y para obtener objetos del mismo
import { render, screen } from "@testing-library/react";
// importamos los componentes para poder testearlos
import TextBox from "../components/TextBox";
import FormUsuario from "../components/FormUsuario";
// importamos userEvent que sirve para simular interacciones del usuario con la aplicación
import userEvent from "@testing-library/user-event";

/**
 * Ejemplos de queries según el elemento a buscar
 * 
    Imagen o avatar getByRole(): <img src=’/perro.png’ /> screen.getByRole(‘img’)
                                <Avatar src=’/gato.png’ /> screen.getByRole(‘img’)

    Campo de texto getByLabelText(): <TextField label=’Nombre’ /> screen.getByLabelText(‘Nombre’)

    Cabecera (h1, h2, h3, …): getByRole() <h3> Cabecera h3 </h3> screen.getByRole(‘heading’, {level : 3})

    Botón getByRole(): <Button > Enviar </Button> screen.getByRole(‘button’, name: ‘Enviar’)
 */

describe('TextBox componente', () => {

    let box;
    let button;

    let txt;
    let button_formulario;

    /**
     *  Aquí vemos que por cada test estamos renderizando varias veces lo mismo. Podemos poner la parte
        del render y de obtener los elementos de la pantalla dentro del beforeEach(() => {}) tal y como
        vemos en el código de ejemplo que les he proporcionado.

        BeforeEach es un hook en Jest que se ejecuta antes de cada prueba individual dentro de un bloque de pruebas.
        Su función principal es preparar el entorno para cada prueba, garantizando que cada una se ejecute en un estado limpio y controlado.
     */
    beforeEach(() => {
        //Primero renderizamos el componente: se crea el DOM Virtual
        render(<TextBox />);
        //Buscamos en la pantalla del DOM Virtual un elemento según su Role.
        //Queremos buscar el role que sea ‘caja’ (role = ‘caja’ en el componente TextBox.jsx)
        //y que además su name sea ‘es una caja’. El name se busca en el atributo title de
        //<Box>
        //el name de Box lo definimos en su atributo title (fijarse en el código)
        box = screen.getByRole('caja', { name: 'es una caja' });
        //el name del botón es el propio título que le ponemos al botón
        button = screen.getByRole('button', { name: 'Pulsa para modificar el color' });


        // Prueba con el formulario "FormUsuario.jsx"
        render (<FormUsuario />);
        txt = screen.getByLabelText('Nombre');
        button_formulario = screen.getByRole('button', { name: 'Enviar' });
    });

    test('la caja de texto se encuentra en el documento', () => {
        // Finalmente comprobamos si la caja está en el documento con el matcher
        // toBeInTheDocument()
        //comprobamos si la <Box> está en nuestro documento
        expect(box).toBeInTheDocument();
    });
    
    test('la caja con el texto tiene un color inicial', () => {
  
        //comprobamos si el Box tiene un color de fondo indigo
        expect(box).toHaveStyle({
            backgroundColor: 'indigo'
        });
    });
    
    test('comprobar si al pulsar el botón cambia el botón de fondo', async () => {
        //usamos el userEvent. Lo primero que debemos hacer es inicializarlo:
        const user = userEvent.setup()

        //simulamos que el usuario clica el botón               
        await user.click(button);
        
        //Ahora comprobamos que al pulsar en el botón se cambia
        //el color de fondo de la caja (Box) a tomato.
        expect(box).toHaveStyle({
            backgroundColor: 'tomato'
        });

        /**
         *  Tabla resumen de algunas acciones que podemos simular con userEvent
         * 
            - Inicialización del usuario:         const user =userEvent.setup()
            - Borrar un campo de texto:           user.clear(txt)
            - Escribir algo en un campo de texto: user.type(txt, ‘hola’)
            - Clicar en un botón:                 user.click(button)
         */
    });

     /**
     *  Para usarlo dentro de un test debemos usar una función asíncrona. Vemos a continuación un
        ejemplo de test asíncrono con el userEvent en el que se simula que un usuario rellena un formulario
        que tiene un campo de texto y luego pica en el botón Enviar.
     */
    test('prueba de formulario', async () => {
        //usamos el userEvent. Lo primero que debemos hacer es inicializarlo:
        const user = userEvent.setup()

        // Después se simulan acciones del usuario. Ejemplo de acciones (hay que poner el await):

        //El usuario borra el contenido del campo de texto txt.
        await user.clear(txt)

        //El usuario rellena un campo de texto con el texto ‘Me muero de sueño en clase’
        await user.type(txt, 'Me muero de sueño en clase')

        //simulamos que el usuario clica el botón (formulario)
        await user.click(button_formulario);

        //AQUÍ VENDRÍA LA PARTE DE TEST. Vamos a suponer que al picar en el botón se
        //muestra un heading de nivel 3 en la interfaz.
        expect(screen.getByRole('heading', { level: 3 })).toBeInTheDocument()

        /**
         *  Tabla resumen de algunas acciones que podemos simular con userEvent
         * 
            - Inicialización del usuario:         const user =userEvent.setup()
            - Borrar un campo de texto:           user.clear(txt)
            - Escribir algo en un campo de texto: user.type(txt, ‘hola’)
            - Clicar en un botón:                 user.click(button)
         */
    });
});