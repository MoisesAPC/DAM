
import titleCase from '../helper/titleCase'

describe('titleCase', () => {
    it('debe retornar un string', () =>
    {   //Compruebo si la ejecuación de la función titleCase pasándole cualquier
            //valor de string me devuelve un string
            const result = titleCase('Un valor');
            expect(typeof result).toBe('string')
    })

    it('debe retornar el string transformado', () => 
    {
            const result = titleCase('es una string chiquitita');
            expect(result).toBe('Es Una String Chiquitita');

    })


});
