// Más ejemplos:
// https://jestjs.io/es-ES/docs/expect
// https://jestjs.io/es-ES/docs/expect#matchers

import {titleCase, divide, sum, isapple} from '../components/MiComponente'

/**
 * // La estructura de un test sencillo es la siguiente:
 
    describe(‘comentario’, () => {
        test(‘comentario’, () => {
            // Aquí va el test
        });
    });
 */


describe('funciones dentro del componente MiComponente', () => {
    describe('titleCase', () => {
        test('debe retornar un string', () =>
        {   //Compruebo si la ejecuación de la función titleCase pasándole cualquier
            //valor de string me devuelve un string
            const result = titleCase('Un valor');
            expect(typeof result).toBe('string')
        })

        test('debe retornar el string transformado', () => 
        {
            const result = titleCase('es una string chiquitita');
            expect(result).toBe('Es Una String Chiquitita');

        })


    });

    /**
     *  El describe() se usa para agrupar diferentes tests dentro de él. Podemos también usar describe()
        dentro de otro describe().
        Ejemplo: en MiComponente.jsx tengo la función sum() y la función divide(). Una posible
        estructura para los tests sería:
     */
    describe('sum', () => {
        test('debe retornar un número si los sumandos son números', () =>
        {   //Compruebo si la ejecuaciónd e la función titleCase pasándole cualquier
            //valor de string me devuelve un string
            const result = sum(2,3);
            expect(typeof result).toBe('number')
        })

        test('debe retornar null si algún sumando no es un número', () =>
        {   
            const result = sum('hola',3);
            expect(result).toBe(null)
        })
        test('debe retornar la suma', () => 
        {
            //Llamada a la función que estamos testeando
            const result = sum(-2,5);

            //El resultado que esperamos obtener
            expect(result).toBe(3);
        })

        /**
         *  // Podemos simplificarlo y hacer la llamada dentro de expect().
         * 
            test(‘la suma de -2 y 5 debe dar 3’, () => {
                expect(sum(-2,5)).toBe(3)
            });
         */
       
    });

});