import sum from '../helper/sum'

describe('sum', () => {
        it('debe retornar un número si los sumandos son números', () =>
        {  
            const result = sum(2,3);
            expect(typeof result).toBe('number')
        })

        it('debe retornar null si algún sumando no es un número', () =>
        {   
            const result = sum('hola',3);
            expect(result).toBe(null)
        })
        it('debe retornar la suma', () => 
        {
            const result = sum(-2,5);
            expect(result).toBe(3);
        })
       
    });