
import isapple from '../helper/isapple'

describe('isapple', () => {
    it('debe retornar un boolean', () =>
    {
            const result = isapple('manzana');
            expect(typeof result).toBe('boolean')
    })

    it('debe retornar true', () => 
    {
            const result = isapple('manzana');
            expect(result).toBe(true);
    })

    it('debe retornar true', () => 
    {
            const result = isapple('pera');
            expect(result).toBe(false);
    })
});
