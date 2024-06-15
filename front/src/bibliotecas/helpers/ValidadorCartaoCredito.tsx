
class ValidadorCartaoCredito
{
    public static valida(numero:string) {        
         // Remove todos os espaços em branco
        numero = numero.replace(/\s+/g, '');

        // Verifica se o número é apenas dígitos
        if (!/^\d+$/.test(numero)) {
            return false;
        }

        // Algoritmo de Luhn
        let soma = 0;
        let alternar = false;

        for (let i = numero.length - 1; i >= 0; i--) {
            let digito = parseInt(numero.charAt(i), 10);

            if (alternar) {
                digito *= 2;
                if (digito > 9) {
                    digito -= 9;
                }
            }

            soma += digito;
            alternar = !alternar;
        }

        return (soma % 10) === 0;
    }
}


export default ValidadorCartaoCredito;