export default function divide(n1, n2)
{
    
    if (!isNaN(n1) && !isNaN(n2))
    {
        if (n2 === 0) {
            return null
        }
        return (n1/n2)
    }
    else
    {
        return (null)
    
    }
    
 
}
