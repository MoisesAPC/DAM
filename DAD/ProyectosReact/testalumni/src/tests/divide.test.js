
import divide from '../helper/divide'

describe('divide', () => {
    it('debe retornar un numero', () =>
    {
            const result = divide(12, 3);
            expect(typeof result).toBe('number')
    })

    it('debe retornar un null si sele pasan strings', () =>
    {
            const result = divide('string', 3);
            expect(result).toBe(null);
    })

    it('division 10 entre 2', () => 
    {
        const result = divide(10, 2);
        expect(result).toBe(5);
    
    })

    it('division 10 entre 4', () => 
    {
        const result = divide(10, 4);
        expect(result).toBe(2.5);
    })

    it('division por cero', () => 
    {
        const result = divide(10, 0);
        expect(result).toBe(null);
    })
});
