import sum from '../helper/sum'
import divide from '../helper/divide'
import titleCase from '../helper/titleCase'
import isapple from '../helper/isapple'


function MiComponente() {
    
    const ressum = sum(2,3)
    const resdiv = divide(4,2)
    const restitleCase = titleCase('Este texto con la primera letra en mayúsculas es así')
    const resisapple = isapple('manzana')
    const aString = resisapple.toString()
    
    

   
    

    return(
        <>
       <h1>Aquí se llamarían a las funciones en alguna parte del programa</h1>
       <p> La suma de 2 y 3 es {ressum}</p>
       <p> La división de 4 y 2 es {resdiv}</p>
       <p> Este texto con la primera letra en mayúsculas es así: {restitleCase}</p>
       <p> Manzana es {aString}</p>

       </>
       
       
    ) 
    }
export default MiComponente;